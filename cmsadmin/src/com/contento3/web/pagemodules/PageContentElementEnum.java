package com.contento3.web.pagemodules;

public enum PageContentElementEnum {

	//Header elements
	SOCIAL("social"),
	NAVIGATION("navigation"),
	TELEPHONE("telephone"),
	EMAIL("email"),
	MENU("menu"),
	LOGO("logo"),
	SEARCH("search"),
	
	//Content 
	SLIDER("slider"),
	CONTENT("content"),
	ARTICLE("article"),
	IMAGE("image"),
	PRODUCT("product"),
	VIDEO("video"),
	
	//Navigation Types
	PAGEBASED("pagebased"),
	CATEGORYBASED("categorybased");
	
	private String value;
	
	private PageContentElementEnum(final String value){
		this.value = value;
	}
}
