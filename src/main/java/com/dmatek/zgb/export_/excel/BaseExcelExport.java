package com.dmatek.zgb.export_.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import com.dmatek.zgb.db.pojo.setting.BaseSettingPojo;
import com.dmatek.zgb.file.update.TempUploadProperties;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
/***
 * Excel 導出的抽象類
 * @author Administrator
 * @data 2019年8月23日 上午9:24:53
 * @Description
 */
public abstract class BaseExcelExport<T extends BaseSettingPojo> implements IExcelExport<T> {
	private BaseResultFactory<Result> viewResultFactory;
	private TempUploadProperties tempUploadProperties;
	public BaseExcelExport(BaseResultFactory<Result> viewResultFactory, 
			TempUploadProperties tempUploadProperties) {
		super();
		this.viewResultFactory = viewResultFactory;
		this.tempUploadProperties = tempUploadProperties;
	}
	@Override
	public Result exportExcel() {
		@SuppressWarnings("resource")
		// WorkBook
		HSSFWorkbook workBook = new HSSFWorkbook();
		// 創建一個Sheet
		HSSFSheet sheet = workBook.createSheet(getSheetName());
		// 初始化sheet标题
		initTitle(sheet, workBook.createCellStyle(), workBook.createFont());
		// 初始化列标题
		initColumn(sheet, getAllColumns());
		setColumnStyle(sheet, workBook.createCellStyle(), workBook.createFont());
		// 判断临时目录是否存在,不存在时进行创建
		File tempDir = new File(getTempUploadProperties().getUploadFolder());
		if (!tempDir.exists()) {
			tempDir.mkdirs();
		}
		// 保存到临时文件中
		String path = getTempUploadProperties().getUploadFolder()
				+ File.separator + getFileName();
		// 判断文件是否存在
		File tempFile = new File(path);
		if (!tempFile.exists()) {// 说明文件已经存在
			FileOutputStream out = null;
			try {
				out = new FileOutputStream(path);
				workBook.write(out);
			} catch (FileNotFoundException e) {
				return getViewResultFactory().errorResult(
						"導出模板文件路徑不存在或正在被操作【目錄："
								+ getTempUploadProperties().getUploadFolder()
								+ ", 文件名: " + getFileName() + "】...");
			} catch (IOException e) {
				return getViewResultFactory().errorResult("導出模板出現IO異常...");
			} finally {
				try {
					if (null != out) {
						out.close();
					}
				} catch (IOException e) {
				}
			}
		}
		return getViewResultFactory().successResult(
				"服務器生成模板【name: " + getFileName() + "】文件成功, 正在下載模板文件...");
	}
	/**
	 * 下载excel文件
	 * @param response
	 * @param request
	 */
	@Override
	public void uploadExcel(HttpServletResponse response, HttpServletRequest request) throws Exception{
		OutputStream excelOut = null;
		response.addHeader("content-disposition", "attachment;filename="
				+ java.net.URLEncoder.encode(getFileName(), "utf-8"));
		excelOut = response.getOutputStream();
		String path = getTempUploadProperties().getUploadFolder()
				+ File.separator + getFileName();
		InputStream excelIs = new FileInputStream(path);
		byte[] b = new byte[4096];
		int size = excelIs.read(b);
		while (size > 0) {
			excelOut.write(b, 0, size);
			size = excelIs.read(b);
		}
		excelOut.close();
		excelIs.close();
	}
	/**
	 * 初始化列
	 * @param sheet
	 */
	private void initColumn(HSSFSheet sheet, String[] columns) {
		HSSFRow row = sheet.createRow(1);
		for (int i = 0; i < columns.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(columns[i]);
		}
	}
	/**
	 * 初始化标题
	 * @param sheet
	 */
	private void initTitle(HSSFSheet sheet,HSSFCellStyle style, HSSFFont font) {
		HSSFRow row = sheet.createRow(0);
		HSSFCell titleCell = row.createCell(0);
		// 设置表格标题
		titleCell.setCellValue(getSheetTitle());
		style.setAlignment(HorizontalAlignment.CENTER);
		font.setFontName("黑体");  
		font.setFontHeightInPoints((short) 16);//设置字体大小 
		style.setFont(font);
		titleCell.setCellStyle(style);
	}
	public BaseResultFactory<Result> getViewResultFactory() {
		return viewResultFactory;
	}
	public void setViewResultFactory(BaseResultFactory<Result> viewResultFactory) {
		this.viewResultFactory = viewResultFactory;
	}
	public TempUploadProperties getTempUploadProperties() {
		return tempUploadProperties;
	}
	public void setTempUploadProperties(TempUploadProperties tempUploadProperties) {
		this.tempUploadProperties = tempUploadProperties;
	}
	protected abstract String getSheetName();
	protected abstract String getSheetTitle();
	protected abstract String[] getAllColumns();
	protected abstract void setColumnStyle(HSSFSheet sheet,HSSFCellStyle style, HSSFFont font);
	protected abstract String getFileName();
}
