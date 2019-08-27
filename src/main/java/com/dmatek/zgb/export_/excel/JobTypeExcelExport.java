package com.dmatek.zgb.export_.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import com.dmatek.zgb.db.pojo.setting.JobType;
import com.dmatek.zgb.file.update.TempUploadProperties;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

public class JobTypeExcelExport extends BaseExcelExport<JobType>{
	public JobTypeExcelExport(BaseResultFactory<Result> viewResultFactory,
			TempUploadProperties tempUploadProperties) {
		super(viewResultFactory, tempUploadProperties);
	}
	@Override
	protected String getSheetName() {
		return "工種資料";
	}
	@Override
	protected String getSheetTitle() {
		return "ZigBee智能定位管理系統*工種資料模板";
	}
	@Override
	protected String[] getAllColumns() {
		String[] columns = { "工種代號", "工種名稱", "工種顏色" };
		return columns;
	}
	@Override
	protected void setColumnStyle(HSSFSheet sheet,HSSFCellStyle style, HSSFFont font) {
		sheet.setColumnWidth(0, 25 * 256);
		sheet.setColumnWidth(1, 25 * 256);
		sheet.setColumnWidth(2, 25 * 256);
		// 設置標題合併
		CellRangeAddress titleAddress = new CellRangeAddress(0, 0, 0, 2);
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
		return "工種資料模板.xls";
	}
}
