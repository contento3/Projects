package com.contento3.web.template;


import java.util.Collection;

import org.vaadin.aceeditor.AceEditor;
import org.vaadin.aceeditor.AceMode;
import org.vaadin.aceeditor.AceTheme;

import com.contento3.account.service.AccountService;
import com.contento3.cms.page.templatecategory.service.TemplateCategoryService;
import com.contento3.cms.page.templatecategoryDto.TemplateCategoryDto;
import com.contento3.dam.image.library.dto.ImageLibraryDto;
import com.contento3.dam.image.library.service.ImageLibraryService;
import com.contento3.web.common.helper.ComboDataLoader;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
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
    	final Integer accountId = (Integer)SessionHelper.loadAttribute("temlateCategoryId");
    	Collection<TemplateCategoryDto> templateCategoryDto = (Collection<TemplateCategoryDto>) templateCategoryService.findById(accountId);
    //	final TemplateCategoryService templateCategoryService = (TemplateCategoryService) helper.getBean("templateCategoryService"); 	
    	templateCategoryCombo = new ComboBox("Template Category",comboDataLoader.loadDataInContainer((Collection)templateCategoryDto));
    	
    	templateNameTxtFld.setCaption("Template name");
        templatePathTxtFld.setCaption("Template path");
        templateKeyTxtFld.setCaption("Template key");
        
        templateNameTxtFld.setInputPrompt("Enter temp name");
        templatePathTxtFld.setInputPrompt("Enter temp path");	
        templateKeyTxtFld.setInputPrompt("Enter temp key");
        
        templateCategoryCombo.setItemCaptionMode(ComboBox.ItemCaptionMode.PROPERTY);
        templateCategoryCombo.setItemCaptionPropertyId("templateCategoryName");
        templateCategoryCombo.setCaption("Temp Cat");
        templateCategoryCombo.setInputPrompt("Select templa categg");

/////////////*********************************/////////////////////////////////////////
//        final Integer accountId = (Integer)SessionHelper.loadAttribute("accountId");
//        Collection<ImageLibraryDto> imageLibraryDto = this.imageLibraryService.findImageLibraryByAccountId(accountId);
//		final ComboDataLoader comboDataLoader = new ComboDataLoader();
//		imageLibrayCombo = new ComboBox("Select library",
//				comboDataLoader.loadDataInContainer((Collection)imageLibraryDto ));
//		
//		imageLibrayCombo.setItemCaptionMode(ComboBox.ItemCaptionMode.PROPERTY);
//		imageLibrayCombo.setItemCaptionPropertyId("name");
		
//       final Integer accountId = (Integer)SessionHelper.loadAttribute("accountId");
//		Collection<ImageLibraryDto> imageLibraryDto = imageLibraryService.findImageLibraryByAccountId(accountId);
//		final ComboDataLoader comboDataLoader = new ComboDataLoader();
//	    final ComboBox imageLibrayCombo = new ComboBox("Select library",
//				comboDataLoader.loadDataInContainer((Collection)imageLibraryDto ));	
//	    imageLibrayCombo.setItemCaptionMode(ComboBox.ItemCaptionMode.PROPERTY);
//		imageLibrayCombo.setItemCaptionPropertyId("name");
//		HorizontalLayout horiz = new HorizontalLayout();
//		horiz.setSpacing(true);
//		horiz.addComponent(imageLibrayCombo);
        ////////////////****************************//////////////////////////////////////////
        
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
