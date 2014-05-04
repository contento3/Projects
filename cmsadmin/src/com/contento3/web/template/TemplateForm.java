package com.contento3.web.template;


import java.util.ArrayList;
import java.util.Collection;

import org.springframework.util.CollectionUtils;
import org.vaadin.aceeditor.AceEditor;
import org.vaadin.aceeditor.AceMode;
import org.vaadin.aceeditor.AceTheme;

import com.contento3.cms.page.templatecategory.service.TemplateCategoryService;
import com.contento3.cms.page.templatecategoryDto.TemplateCategoryDto;
import com.contento3.web.common.helper.ComboDataLoader;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;

public class TemplateForm {
	ComboBox templateCategoryCombo = new ComboBox();
	
	TemplateCategoryService templateCategoryService;
	
	final Integer accountId = (Integer)SessionHelper.loadAttribute("accountId");
    
	private final TextField templateNameTxtFld = new TextField("Template Name");
    
    private final TextField templatePathTxtFld = new TextField("Template Path");
    
    private final TextField templateKeyTxtFld = new TextField("Template Key");
    
    final ComboDataLoader comboDataLoader = new ComboDataLoader();
    
    private final AceEditor editor = new AceEditor();

    private  final CheckBox isGlobal = new CheckBox();
    
    private  final TextField directoryId = new TextField();
    
    public TemplateForm (final SpringContextHelper helper, TemplateCategoryService templateCategoryService){
    	Collection<TemplateCategoryDto> templateCategoryDto = (Collection<TemplateCategoryDto>) templateCategoryService.findAll();
    	
    	if (CollectionUtils.isEmpty(templateCategoryDto)){
    		templateCategoryDto = new ArrayList<TemplateCategoryDto>();
    	}
    	
    	templateCategoryCombo = new ComboBox("Template Category",comboDataLoader.loadDataInContainer((Collection)templateCategoryDto));
    	
    	templateNameTxtFld.setCaption("Template name");
        templatePathTxtFld.setCaption("Template path");
        templateKeyTxtFld.setCaption("Template key");
        
        templateNameTxtFld.setInputPrompt("Enter template name");
        templatePathTxtFld.setInputPrompt("Enter template path");	
        templateKeyTxtFld.setInputPrompt("Enter template key");
        
        templateCategoryCombo.setItemCaptionMode(ComboBox.ItemCaptionMode.PROPERTY);
        templateCategoryCombo.setItemCaptionPropertyId("name");
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
