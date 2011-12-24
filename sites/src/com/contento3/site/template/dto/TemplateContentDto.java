package com.contento3.site.template.dto;

public class TemplateContentDto {

	private String content;

	public TemplateContentDto () {
		this.content = 
				" ${templateService.findTemplateById(1).templateName}";
//				"<list templateService.findTemplateById(1) as dto>" +
//				"${dto.templateName}"+
//				"</list>";	
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	
	
}
