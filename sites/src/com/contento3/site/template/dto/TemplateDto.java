package com.contento3.site.template.dto;

public class TemplateDto {

	private String content;

	public TemplateDto () {
		this.content = "${pet} does ${number} little hops.";	
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	
	
}
