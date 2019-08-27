package com.dmatek.zgb.export_.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import com.dmatek.zgb.db.pojo.person.Person;
import com.dmatek.zgb.file.update.TempUploadProperties;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

public class PersonExcelExport extends BaseExcelExport<Person> {
	public PersonExcelExport(BaseResultFactory<Result> viewResultFactory, 
			TempUploadProperties tempUploadProperties) {
		super(viewResultFactory, tempUploadProperties);
	}
	// Excel 表名稱
	@Override
	protected String getSheetName() {
		return "人員資料";
	}
	// 模板標題
	@Override
	protected String getSheetTitle() {
		return "ZigBee智能定位管理系統*人員資料模板";
	}
	// 所有的列名稱
	@Override
	protected String[] getAllColumns() {
		String[] columns = { "頭像", "工號", "姓名", "卡號", "公司名稱", "工地名稱", "工種名稱",
				"血型", "出生日期", "休息時間", "滯留時間", "備註" };
		return columns;
	}

	@Override
	protected void setColumnStyle(HSSFSheet sheet, HSSFCellStyle style,
			HSSFFont font) {
		sheet.setColumnWidth(0, 25 * 256);
		sheet.setColumnWidth(1, 25 * 256);
		sheet.setColumnWidth(2, 25 * 256);
		
		sheet.setColumnWidth(3, 25 * 256);
		sheet.setColumnWidth(4, 25 * 256);
		sheet.setColumnWidth(5, 25 * 256);
		
		sheet.setColumnWidth(6, 25 * 256);
		sheet.setColumnWidth(7, 25 * 256);
		sheet.setColumnWidth(8, 25 * 256);
		
		sheet.setColumnWidth(9, 25 * 256);
		sheet.setColumnWidth(10, 25 * 256);
		sheet.setColumnWidth(11, 25 * 256);
		// 設置標題合併
		CellRangeAddress titleAddress = new CellRangeAddress(0, 0, 0, 11);
		sheet.addMergedRegion(titleAddress);
		// 設置列樣式
		style.setAlignment(HorizontalAlignment.CENTER);
		font.setFontHeightInPoints((short) 14);
		style.setFont(font);
		HSSFRow row = sheet.getRow(1);
		int cellTotal = row.getPhysicalNumberOfCells();
		for (int i = 0; i < cellTotal; i++) {
			row.getCell(i).setCellStyle(style);
		}
	}
	@Override
	protected String getFileName() {
		return "人員資料模板.xls";
	}
}
