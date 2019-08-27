package com.dmatek.zgb.export_.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import com.dmatek.zgb.db.pojo.setting.Company;
import com.dmatek.zgb.file.update.TempUploadProperties;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

public class CompanyExcelExport extends BaseExcelExport<Company> {
	public CompanyExcelExport(BaseResultFactory<Result> viewResultFactory,
			TempUploadProperties tempUploadProperties) {
		super(viewResultFactory, tempUploadProperties);
	}
	@Override
	protected String getSheetName() {
		return "公司資料";
	}
	@Override
	protected String getSheetTitle() {
		return "ZigBee智能定位管理系統*公司資料模板";
	}
	@Override
	protected String[] getAllColumns() {
		String[] columns = { "公司編號", "公司名稱", "電話", "地址" };
		return columns;
	}
	@Override
	protected void setColumnStyle(HSSFSheet sheet, HSSFCellStyle style,
			HSSFFont font) {
		sheet.setColumnWidth(0, 25 * 256);
		sheet.setColumnWidth(1, 25 * 256);
		sheet.setColumnWidth(2, 25 * 256);
		sheet.setColumnWidth(3, 25 * 256);
		// 設置標題合併
		CellRangeAddress titleAddress = new CellRangeAddress(0, 0, 0, 3);
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
		return "公司資料模板.xls";
	}
}
