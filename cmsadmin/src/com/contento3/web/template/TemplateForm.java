package com.contento3.web.template;

import org.vaadin.aceeditor.AceEditor;
import org.vaadin.aceeditor.AceMode;
import org.vaadin.aceeditor.AceTheme;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;

public class TemplateForm {

    private final TextField templateNameTxtFld = new TextField("Template Name");
    
    private final TextField templatePathTxtFld = new TextField("Template Path");
    
    private final TextField templateKeyTxtFld = new TextField("Template Key");

    private final ComboBox templateCategoryCombo = new ComboBox("Template Category");
    
    private final AceEditor editor = new AceEditor();

    private  final CheckBox isGlobal = new CheckBox();
    
    private  final TextField directoryId = new TextField();
    
    public TemplateForm (){
        templateNameTxtFld.setCaption("Template name");
        templatePathTxtFld.setCaption("Template path");
        templateKeyTxtFld.setCaption("Template key");
        
        templateNameTxtFld.setInputPrompt("Enter template name");
        templatePathTxtFld.setInputPrompt("Enter template path");
        templateKeyTxtFld.setInputPrompt("Enter template key");
        
        templateCategoryCombo.setCaption("Template Category");
        templateCategoryCombo.setInputPrompt("Select template category");

		editor.setCursorRowCol(1,1);
		editor.setHighlightActiveLine(true);
		editor.setWordWrap(true);
		editor.setMode(AceMode.html);
		editor.setTheme(AceTheme.dreamweaver);

		isGlobal.setCaption("Is Global");

    	directoryId.setVisible(false);
    }
    
	public TextField getTemplateNameTxtFld() {
		return templateNameTxtFld;
	}

	public TextField getTemplatePathTxtFld() {
		return templatePathTxtFld;
	}

	public TextField getTemplateKeyTxtFld() {
		return templateKeyTxtFld;
	}

	public AceEditor getEditor() {
		return editor;
	}

	public CheckBox getIsGlobal() {
		return isGlobal;
	}
    
    public TextField getDirectoryId(){
    	return this.directoryId;
    }

	public ComboBox getTemplateCategoryCombo() {
		return templateCategoryCombo;
	}
}
