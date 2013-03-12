package com.contento3.web.content.document;

public enum StorageTypeEnum {
	FILE ("FILE"),
	DATABASE("DATABASE"),
	AMAZON_S3("AMAZON S3");
	
	private String fileType;
	
	private StorageTypeEnum(String fileType){
		this.fileType = fileType;
	}
	
	public String getFileType(){
		return fileType;
	}
}
