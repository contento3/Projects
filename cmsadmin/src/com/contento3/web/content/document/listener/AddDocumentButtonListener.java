package com.contento3.web.content.document.listener;

import org.vaadin.openesignforms.ckeditor.VaadinCKEditorUI;

import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;

public class AddDocumentButtonListener implements ClickListener {

	private SpringContextHelper contextHelper;
	private TabSheet tabSheet;
	private Table documentTable;

	public AddDocumentButtonListener(SpringContextHelper contextHelper, TabSheet tabSheet, Table documentTable) {
		this.contextHelper = contextHelper;
		this.tabSheet = tabSheet;
		this.documentTable = documentTable;
	}

	@Override
	public void click(ClickEvent event) {
		
		DocumentFormBuilderListner temp = new DocumentFormBuilderListner(this.contextHelper,this.tabSheet,this.documentTable);
		Button b = new Button("Add");
		temp.buttonClick(new com.vaadin.ui.Button.ClickEvent(b));
	}

}
