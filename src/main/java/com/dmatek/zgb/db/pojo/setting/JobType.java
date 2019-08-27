package com.dmatek.zgb.db.pojo.setting;

import java.util.Date;

import com.dmatek.zgb.import_.excel.ColumnType;
import com.dmatek.zgb.import_.excel.ExcelColumn;
/**
 * 工种资料
 * @author zf
 * @data 2018年12月7日 下午3:30:17
 * @Description
 */
public class JobType extends BaseSettingPojo{
	private static final long serialVersionUID = 1L;
	// 工种编号
	@ExcelColumn(name="工種代號", number=0, type=ColumnType.STRING)
	private String no;
	// 工种名称
	@ExcelColumn(name="工種名稱", number=1, type=ColumnType.STRING)
	private String name;
	// 工种颜色
	@ExcelColumn(name="工種顏色", number=2, type=ColumnType.STRING)
	private String color;
	public JobType() {
		super();
	}
	public JobType(Date createTime, Date updateTime, String createName,
			String updateName, String no, String name, String color) {
		super(createTime, updateTime, createName, updateName);
		this.no = no;
		this.name = name;
		this.color = color;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	@Override
	public String toString() {
		return "[主鍵: " + no + ", 名稱: " + name + ", 顏色: " + color
				+ "]";
	}
}
