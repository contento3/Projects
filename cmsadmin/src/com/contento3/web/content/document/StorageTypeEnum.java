package com.contento3.web.content.document;

public enum StorageTypeEnum {
	FILE ("FILE"),
	DATABASE("DATABASE"),
	S3("S3");
	
	private String type;
	
	private StorageTypeEnum(String type){
		this.type = type;
	}
	
	public String getType(){
		return type;
	}
}
