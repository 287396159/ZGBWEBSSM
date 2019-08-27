package com.dmatek.zgb.db.pojo.setting;

import java.util.Date;

import com.dmatek.zgb.import_.excel.ColumnType;
import com.dmatek.zgb.import_.excel.ExcelColumn;
/**
 * 公司资料
 * @author zf
 * @data 2018年12月7日 下午3:30:28
 * @Description
 */
public class Company extends BaseSettingPojo{
	private static final long serialVersionUID = 1L;
	@ExcelColumn(name="公司編號", number=0, type=ColumnType.STRING)
	private String no;
	@ExcelColumn(name="公司名稱", number=1, type=ColumnType.STRING)
	private String name;
	@ExcelColumn(name="電話", number=2, type=ColumnType.STRING)
	private String phone;
	@ExcelColumn(name="地址", number=3, type=ColumnType.STRING)
	private String address;
	public Company() {
		super();
	}
	public Company(Date createTime, Date updateTime, String createName,
			String updateName, String no, String name, String phone, String address) {
		super(createTime, updateTime, createName, updateName);
		this.no = no;
		this.name = name;
		this.phone = phone;
		this.address = address;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "[編號 : " + no + ", 名稱: " + name + ", 電話號碼: " + phone
				+ ", 地址: " + address + "]";
	}
}
