package com.contento3.widget.template.editor;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

@JavaScript({ "codemirror.js","templateEditor-connector.js"})
public class TemplateEditor extends AbstractJavaScriptComponent {

	@Override
	  protected TemplateEditorState getState() {
	    return (TemplateEditorState) super.getState();
	  }
	
	
}


