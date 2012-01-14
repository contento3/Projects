package com.contento3.web.content;

import org.vaadin.openesignforms.ckeditor.CKEditorConfig;
import org.vaadin.openesignforms.ckeditor.CKEditorTextField;

import com.vaadin.ui.Component;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ArticleMgmtUIManager {

	public Component renderAddScreen(){
		VerticalLayout formLayout = new VerticalLayout();
		formLayout.setHeight("100%");
		
		TextField articleHeading = new TextField();
		articleHeading.setCaption("Header");

		TextArea articleTeaser = new TextArea();
		articleTeaser.setCaption("Teaser");
		articleTeaser.setColumns(50);
		articleTeaser.setRows(2);
		
		CKEditorConfig config = new CKEditorConfig();
        config.useCompactTags();
        config.disableElementsPath();
        config.setResizeDir(CKEditorConfig.RESIZE_DIR.BOTH);
        config.disableSpellChecker();
        config.setToolbarCanCollapse(true);
        config.setWidth("75%");
        config.setHeight("100%");
        
        final CKEditorTextField bodyTextField = new CKEditorTextField(config);
        bodyTextField.setCaption("Article Body");
        
        final String editorInitialValue = 
                  "<p>Thanks TinyMCEEditor for getting us started on the CKEditor integration.</p><h1>Like TinyMCEEditor said, &quot;Vaadin rocks!&quot;</h1><h1>And CKEditor is no slouch either.</h1>";

        bodyTextField.setValue(editorInitialValue);
        
          //ckEditorTextField.setReadOnly(true);
          //ckEditorTextField.addListener(new Property.ValueChangeListener() {

//                  public void valueChange(ValueChangeEvent event) {
//                          getMainWindow().showNotification("CKEditor contents: " + event.getProperty().toString().replaceAll("<", "&lt;"));
//                  }
       //   });
        formLayout.addComponent(articleHeading);
        formLayout.addComponent(articleTeaser);
        formLayout.addComponent(bodyTextField);
        
        return formLayout;
      }

}
