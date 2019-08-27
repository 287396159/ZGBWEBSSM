package com.dmatek.zgb.db.pojo.person;

import java.util.Date;

import com.dmatek.zgb.db.pojo.setting.BaseSettingPojo;
import com.dmatek.zgb.import_.excel.ColumnType;
import com.dmatek.zgb.import_.excel.ExcelColumn;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Person extends BaseSettingPojo{
	private static final long serialVersionUID = 1L;
	@ExcelColumn(name="頭像",number=0,type=ColumnType.IMAGE)
	private String imgPath;
	@ExcelColumn(name="工號",number=1,type=ColumnType.STRING)
	private String no;
	@ExcelColumn(name="工地名稱",number=5,type=ColumnType.STRING)
	private String group_Name;
	@ExcelColumn(name="姓名",number=2,type=ColumnType.STRING)
	private String name;
	@ExcelColumn(name="卡號",number=3,type=ColumnType.STRING)
	private String tagId;
	@ExcelColumn(name="公司名稱",number=4,type=ColumnType.STRING)
	private String company_Name;
	@ExcelColumn(name="血型",number=7,type=ColumnType.STRING)
	private String bloodType;
	@ExcelColumn(name="工種名稱",number=6,type=ColumnType.STRING)
	private String jobType_Name;
	@ExcelColumn(name="出生日期",number=8,type=ColumnType.DATE)
	private Date birthDay;
	@ExcelColumn(name="休息時間",number=9,type=ColumnType.FLOAT)
	private float resTime;
	@ExcelColumn(name="滯留時間",number=10,type=ColumnType.FLOAT)
	private float stopTime;
	@ExcelColumn(name="備註",number=11,type=ColumnType.STRING)
	private String des;
	private String company_No, jobType_No;
	private int serialNumber;
	private int group_id;
	public Person() {
		super();
	}
	public Person(Date createTime, Date updateTime, String createName,
			String updateName, String no, String name, String tagId,
			String imgPath, String company_No, String company_Name,
			String jobType_No, String jobType_Name, String des, Date birthDay,
			float resTime, float stopTime) {
		super(createTime, updateTime, createName, updateName);
		this.no = no;
		this.name = name;
		this.tagId = tagId;
		this.imgPath = imgPath;
		this.company_No = company_No;
		this.company_Name = company_Name;
		this.jobType_No = jobType_No;
		this.jobType_Name = jobType_Name;
		this.des = des;
		this.birthDay = birthDay;
		this.resTime = resTime;
		this.stopTime = stopTime;
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
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getCompany_No() {
		return company_No;
	}
	public void setCompany_No(String company_No) {
		this.company_No = company_No;
	}
	public String getCompany_Name() {
		return company_Name;
	}
	public void setCompany_Name(String company_Name) {
		this.company_Name = company_Name;
	}
	public String getJobType_No() {
		return jobType_No;
	}
	public void setJobType_No(String jobType_No) {
		this.jobType_No = jobType_No;
	}
	public String getJobType_Name() {
		return jobType_Name;
	}
	public void setJobType_Name(String jobType_Name) {
		this.jobType_Name = jobType_Name;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getBirthDay() {
		return birthDay;
	}
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	public float getResTime() {
		return resTime;
	}
	public void setResTime(float resTime) {
		this.resTime = resTime;
	}
	public float getStopTime() {
		return stopTime;
	}
	public void setStopTime(float stopTime) {
		this.stopTime = stopTime;
	}
	public int getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getGroup_Name() {
		return group_Name;
	}
	public void setGroup_Name(String group_Name) {
		this.group_Name = group_Name;
	}
	public int getGroup_id() {
		return group_id;
	}
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	public String getBloodType() {
		return bloodType;
	}
	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "[編號: " + no + ", 姓名: " + name + ", 卡片ID: " + tagId
				+ ", 圖像路徑: " + imgPath + ", 公司編號: " + company_No
				+ ", 公司名稱: " + company_Name + ", 工種編號: "
				+ jobType_No + ", 工種名稱: " + jobType_Name + ", 血型: " + bloodType + ", 描述訊息: "
				+ des + ", 出生日期: " + birthDay + ", 休息時間: " + resTime
				+ ", 滯留時間: " + stopTime + "]";
	}
}
