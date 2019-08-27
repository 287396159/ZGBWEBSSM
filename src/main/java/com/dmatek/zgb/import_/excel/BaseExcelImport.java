package com.dmatek.zgb.import_.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dmatek.zgb.controller.setting.validation.BaseSettingValidation;
import com.dmatek.zgb.db.pojo.setting.BaseSettingPojo;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
/****
 * 
 * @author zhangfu
 * @data 2019年8月23日 下午2:44:13
 * @Description 
 *  導出Excel的抽象類
 */
@SuppressWarnings("hiding")
public abstract class BaseExcelImport<T extends BaseSettingPojo> implements IExcelImport<T> {
	// 数据开始行从第2行开始
	private final static int DATA_FIRST_ROW = 2;
	private BaseResultFactory<Result> viewResultFactory;
	// 校验添加的数据是否正确
	private BaseSettingValidation<String, T> baseSettingValidation;
	// 进行导入的用户名，添加数据时会用到
	private String userName;
	public BaseExcelImport(BaseResultFactory<Result> viewResultFactory,
		BaseSettingValidation<String, T> baseSettingValidation) {
		super();
		this.viewResultFactory = viewResultFactory;
		this.baseSettingValidation = baseSettingValidation;
	}
	@Override
	public Result importExcel(MultipartFile file) throws Exception {
		Workbook workbook = null;
		try {
			workbook = createWorkBook(file);
		} catch (Exception e) {
			return viewResultFactory.errorResult(e.getLocalizedMessage());
		}
		Sheet sheet = null;
		String sheetName = getSheetName();
		if (!StringUtils.isEmpty(sheetName)) {
			sheet = workbook.getSheet(sheetName);
		} else {
			return viewResultFactory.errorResult("實現類沒有定義sheet名稱...");
		}
		if (null == sheet) {
			return viewResultFactory.errorResult("導入的excel沒有【sheetName : "
					+ sheetName + "】的sheet...");
		}
		// 获取泛型对象的Class对象，需要我们每一个实现类继承并实现这一个方法
		Class<T> clazz = getTClazz();
		// 匹配类 的字段列数与表格中是否匹配
		if (null == clazz) {
			return viewResultFactory.errorResult("獲取匹配的泛型失敗...");
		}
		// 說明此時列名驗證成功
		Result result = verifyColumn(sheet, clazz);
		if (null != result) {
			return result;
		}
		// 需要我们进行对应数据插入的操作
		result = scanAllCells(sheet, clazz);
		if (null != result) {
			return result;
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	protected Result scanAllCells(Sheet sheet, Class<T> clazz) throws Exception  {
		int totalRows = sheet.getPhysicalNumberOfRows();
		if(totalRows < 3) {
			return getViewResultFactory().errorResult("表【sheet: "
					+ sheet.getSheetName() + "】不存在任何数据...");
		}
		// 獲取Excel中所有的圖片訊息
		Map<String, Picture> picturesMap = isIncludedPicture() ? getAllPicturesMap(sheet)
				: null;
		Field[] allFields = clazz.getDeclaredFields();
		// 从数据开始行开始遍历
		for (int i = getDataFirstRow(); i < totalRows; i++) {
			Row row = sheet.getRow(i);
			Map<Integer, Object> columnValMap = new HashMap<Integer, Object>();
			for (Field field : allFields) {
				ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
				if (null == excelColumn) {
					continue;
				}
				//1. 获取当前属性的 列编号和列名
				int columnIndex = excelColumn.number();
				//2. 获取当前行的指定列的单元格
				Cell cell = row.getCell(columnIndex);
				//3. 查看当前单元格的内容和属性类型是否匹配
				if(null == cell) {
					continue;
				}
				// 設置單元格類型
				setCellType(cell, excelColumn.type());
				// 获取单元格内容
				Object obj = getCellValue(cell);
				// 获取属性类型名
				String fieldType = field.getGenericType().getTypeName();
				if(null != obj) {
					if(!isMatchType(obj, fieldType)) {
						return getViewResultFactory().errorResult("單元格【sheet: " + 
							   sheet.getSheetName() + ", row: " + i + ", column: " + 
							   columnIndex + "】類型與屬性類型不匹配...");
					}
				} 
				columnValMap.put(columnIndex, obj);
			}
			Result instanceResult = newInstance(columnValMap, picturesMap, i);
			if(null != instanceResult && instanceResult.getCode() == 600) {
				((T)instanceResult.getData()).setCreateName(getUserName());
				((T)instanceResult.getData()).setCreateTime(new Date());
				Result result = getBaseSettingValidation().saveValidate(((T)instanceResult.getData()));
				if(result == null) {
					// 插入數據到數據庫中
					addData(((T)instanceResult.getData()));
				} else {
					return result;
				}
			} else {
				return instanceResult;
			}
		}
		return null;
	}
	protected abstract boolean isIncludedPicture();
	/**
	 * 验证列
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private Result verifyColumn(Sheet sheet, Class<T> clazz) {
		// 第0行是标题，第1列是列名
		int totalRows = sheet.getPhysicalNumberOfRows();
		if(totalRows < 2) {
			return viewResultFactory.errorResult("表【sheet: "
					+ sheet.getSheetName() + "】小於2行...");
		}
		// 獲取列名行
		Row hssFRow = sheet.getRow(1);
		if(null == hssFRow) {
			return viewResultFactory.errorResult("列名的行【sheet: "
					+ sheet.getSheetName() + ", row: 1】不存在...");
		}
		// 获取所有的成員變量
		Field[] allFields = clazz.getDeclaredFields();
		for (Field field : allFields) {
			ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
			if (null == excelColumn) {
				continue;
			}
			// 获取当前属性对应的列名
			String columnName = excelColumn.name();
			// 获取当前属性对应的列序号
			int columnNumber = excelColumn.number();
			//  获取列名对应的单元格
			Cell hssfCell = hssFRow.getCell(columnNumber);
			if (null == hssfCell || hssfCell.getCellType() != HSSFCell.CELL_TYPE_STRING) {
				return viewResultFactory.errorResult("列【sheet: "
					 + sheet.getSheetName() + ", row: 1, columnIndex:"
					 + columnNumber + " 】不存在对应的属性...");
			}
			String cellVal = hssfCell.getStringCellValue();
			if(StringUtils.isEmpty(cellVal)) {
				return viewResultFactory.errorResult("列【sheet: "
					 + sheet.getSheetName() + ", row: 1, columnIndex:"
					 + columnNumber + " 】不能為空...");
			}
			if(!cellVal.equalsIgnoreCase(columnName)) {
				return viewResultFactory.errorResult("列【sheet: "
					 + sheet.getSheetName() + ", row: 1, columnIndex:"
					 + columnNumber + " 】屬性不匹配...");
			}
		}
		return null;
	}
	protected boolean isMatchType(Object cell, String typeStr) {
		// 判断单元格中的 值的类型
		if ((cell instanceof String) && typeStr.endsWith("String")) {// 说明单元格中是String类型
			return true;
		} else if (cell instanceof Double) {// 说明单元格中的值是数值类型
			if (typeStr.endsWith("Integer") || typeStr.endsWith("int")
			   || typeStr.endsWith("double") || typeStr.endsWith("Double") 
			   || typeStr.endsWith("Float") || typeStr.endsWith("float")
			   || typeStr.endsWith("Byte")  || typeStr.endsWith("byte")
			   || typeStr.endsWith("Short") || typeStr.endsWith("short")) {
				return true;
			}
		} else if ((cell instanceof Boolean) && typeStr.endsWith("Boolean")) {// 单元格中的值是布尔类型
			return true;
		} else if ((cell instanceof Date) && typeStr.endsWith("Date")) {// 单元格中的值是日期类型
			return true;
		}
		return false;
	}
	@SuppressWarnings("deprecation")
	protected Object getCellValue(Cell hssfCell) {
		int type = hssfCell.getCellType();
		switch(type) {
		case HSSFCell.CELL_TYPE_STRING:
			return hssfCell.getStringCellValue();
		case HSSFCell.CELL_TYPE_BOOLEAN:
			return hssfCell.getBooleanCellValue();
		case HSSFCell.CELL_TYPE_NUMERIC:
			if(HSSFDateUtil.isCellDateFormatted(hssfCell)) {
				return hssfCell.getDateCellValue();
			} else {
				return hssfCell.getNumericCellValue();
			}
		case HSSFCell.CELL_TYPE_ERROR:
			return hssfCell.getErrorCellValue();
		}
		return null;
	}
	/***
	 * 獲取所有圖片的Map訊息
	 * @return
	 */
	private Map<String, Picture> getAllPicturesMap(Sheet sheet) {
		Map<String, Picture> picturesMap = new HashMap<String, Picture>();
		int maxRow = sheet.getPhysicalNumberOfRows();
		int maxCol = sheet.getRow(0).getPhysicalNumberOfCells();
		Drawing<?> drawing = sheet.getDrawingPatriarch();
		if(null != drawing) {
			Iterator<?> iterator = sheet.getDrawingPatriarch().iterator();
			while (iterator.hasNext()) {
				Object obj = iterator.next();
				if (obj instanceof Picture) {
					Picture picture = (Picture) obj;
					ClientAnchor anchor = (ClientAnchor) picture.getAnchor();
					// 都是以圖片開始位置的行列為准
					int row = anchor.getRow1();
					short col = anchor.getCol1();
					if(row < 0 || row > maxRow || col < 0 || col > maxCol) {
						continue;
					}
					String key = row + "_" + col;
					picturesMap.put(key, picture);
				}
			}
		}
		return picturesMap;
	}
	/**
	 * 创建WorkBook对象
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private Workbook createWorkBook(MultipartFile file) throws IOException {
		String fileName = file.getOriginalFilename();
		Workbook workBook = null;
		if(fileName.endsWith(".xls")) {
			workBook = new HSSFWorkbook(new POIFSFileSystem(file.getInputStream()));
		} else if(fileName.endsWith(".xlsx")) {
			workBook = new XSSFWorkbook(file.getInputStream());
		}
		return workBook;
	}
	protected String savePicture(Picture picture, String dir) throws FileNotFoundException {
		PictureData pictureData = picture.getPictureData();
		String suffix = pictureData.suggestFileExtension();
		String name = UUID.randomUUID().toString() + "." + suffix;
		String filePath = dir + "//" + name;
		FileOutputStream out = new FileOutputStream(filePath);
		try {
			out.write(pictureData.getData());
		} catch (IOException e) {
			return null;
		} finally {
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {

				}
			}
		}
		return name;
	}
	protected abstract String getSheetName();
	protected abstract Class<T> getTClazz();
	/***
	 * @param map 列屬性映射表
	 * @param picturesMap sheet圖片映射表
	 * @param row 所屬的行
	 * @return 
	 * @throws Exception
	 */
	protected abstract Result newInstance(Map<Integer, Object> map, Map<String, Picture> picturesMap, int row) throws Exception;
	protected abstract void addData(T t) throws Exception;
	protected void setCellType(Cell cell, int columnType) throws Exception {
		if(columnType == ColumnType.NUMBER || columnType == ColumnType.DATE || 
		   columnType == ColumnType.FLOAT) {
			cell.setCellType(CellType.NUMERIC);
		} else {
			cell.setCellType(CellType.STRING);
		}
	}
	public BaseResultFactory<Result> getViewResultFactory() {
		return viewResultFactory;
	}
	public void setViewResultFactory(BaseResultFactory<Result> viewResultFactory) {
		this.viewResultFactory = viewResultFactory;
	}
	public static int getDataFirstRow() {
		return DATA_FIRST_ROW;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public BaseSettingValidation<String, T> getBaseSettingValidation() {
		return baseSettingValidation;
	}
	public void setBaseSettingValidation(
			BaseSettingValidation<String, T> baseSettingValidation) {
		this.baseSettingValidation = baseSettingValidation;
	}
}
