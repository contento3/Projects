package com.contento3.common.keywords;

public enum SystemKeywordsEnum {

	PAGE("/page"), ARTICLE("/article"), REGISTER("/register"), SECURE("/secure"), USER("/user");
	
	private String value;
	
	private SystemKeywordsEnum(final String value) {
        this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	public static boolean contains(String s) {
		
		for (SystemKeywordsEnum keyword : SystemKeywordsEnum.values()) {
			if(s.equals(keyword.value)) {
				return true;
			}
		}
		return false;
	}
}
