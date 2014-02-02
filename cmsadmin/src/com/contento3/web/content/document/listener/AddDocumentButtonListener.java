package com.contento3.web.content.document.listener;

import org.apache.shiro.authz.AuthorizationException;
import org.vaadin.openesignforms.ckeditor.VaadinCKEditorUI;

import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
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
		try
		{
		
		DocumentFormBuilderListner temp = new DocumentFormBuilderListner(this.contextHelper,this.tabSheet,this.documentTable);
		Button b = new Button("Add");
		temp.buttonClick(new com.vaadin.ui.Button.ClickEvent(b));
		}
		catch(AuthorizationException ex){Notification.show("You are not permitted to add documents");}
	}

}
