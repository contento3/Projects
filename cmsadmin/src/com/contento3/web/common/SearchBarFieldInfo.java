package com.contento3.web.common;

public class SearchBarFieldInfo {

	
	private String textFieldLabel;
	
	private String textFieldPrompt;

	public SearchBarFieldInfo(final String textFieldLabel,final String textFieldPrompt){
		this.textFieldLabel = textFieldLabel;
		this.textFieldPrompt = textFieldPrompt;
	}
	
	public String getTextFieldLabel() {
		return textFieldLabel;
	}

	public void setTextFieldLabel(final String textFieldLabel) {
		this.textFieldLabel = textFieldLabel;
	}

	public String getTextFieldPrompt() {
		return textFieldPrompt;
	}

	public void setTextFieldPrompt(final String textFieldPrompt) {
		this.textFieldPrompt = textFieldPrompt;
	}
	
	
}
