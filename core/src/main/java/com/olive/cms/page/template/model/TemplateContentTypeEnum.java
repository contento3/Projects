package com.olive.cms.page.template.model;

public enum TemplateContentTypeEnum {

	TEXT_HTML("text/html"),
	TEXT_CSS("text/css"),
	TEXT_XML("text/xml"),
	TEXT_FREEMARKER("text/freemarker");
	
	private String value;
	
	private TemplateContentTypeEnum(String value){
		this.value = value;	
	}
	
	public String getValue(){
		return this.value;
	}
	
	public String toString() {
		return value;
	}
}
