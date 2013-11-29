package com.contento3.cms.page.template.model;

public enum SystemTemplateNameEnum {

	SYSTEM_ERROR_SIMPLE("errorSimple"),
	SYSTEM_REGISTER("registerForm"),
	SYSTEM_REGISTER_SUCCESS("registerSuccess"),
	SYSTEM_ARTICLE("article");
		
	private String value;
		
	private SystemTemplateNameEnum(final String value){
		this.value = value;	
	}
		
	public String getValue(){
		return this.value;
	}

	public void setValue(final String value){
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
