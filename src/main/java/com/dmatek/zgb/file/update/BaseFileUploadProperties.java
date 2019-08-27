package com.dmatek.zgb.file.update;

public class BaseFileUploadProperties {
	//静态资源对外暴露的访问路径
    private String staticAccessPath;
    //文件上传目录
    private String uploadFolder ;
	public BaseFileUploadProperties() {
		super();
	}
	public BaseFileUploadProperties(String staticAccessPath, String uploadFolder) {
		super();
		this.staticAccessPath = staticAccessPath;
		this.uploadFolder = uploadFolder;
	}
	public String getStaticAccessPath() {
		return staticAccessPath;
	}
	public void setStaticAccessPath(String staticAccessPath) {
		this.staticAccessPath = staticAccessPath;
	}
	public String getUploadFolder() {
		return uploadFolder;
	}
	public void setUploadFolder(String uploadFolder) {
		this.uploadFolder = uploadFolder;
	}
}
