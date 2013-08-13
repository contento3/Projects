package com.contento3.cms.content.model;

public enum AssociatedContentScopeTypeEnum {

	IMAGE("IMAGE");
		
	private String scopeType;
		
	private AssociatedContentScopeTypeEnum(String scopeType){
		this.scopeType = scopeType;
	}
		
	public String getScopeType(){
		return scopeType;
	}

}
