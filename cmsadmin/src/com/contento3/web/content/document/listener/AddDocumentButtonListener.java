package com.contento3.web.content.document.listener;

import org.apache.shiro.authz.AuthorizationException;

import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;

public class AddDocumentButtonListener implements ClickListener {


	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private SpringContextHelper contextHelper;
	
	/**
	 * 
	 */
	private TabSheet tabSheet;
	
	/**
	 * 
	 */
	private Table documentTable;

	public AddDocumentButtonListener(final SpringContextHelper contextHelper, final TabSheet tabSheet,final Table documentTable) {
		this.contextHelper = contextHelper;
		this.tabSheet = tabSheet;
		this.documentTable = documentTable;
	}

	@Override
	public void click(final ClickEvent event) {
		try
		{
			final DocumentFormBuilderListner docListener = new DocumentFormBuilderListner(this.contextHelper,this.tabSheet,this.documentTable);
			Button b = new Button("Add");
			docListener.buttonClick(new com.vaadin.ui.Button.ClickEvent(b));
		}
		catch(final AuthorizationException ex){
			Notification.show("You are not permitted to add documents",Notification.Type.TRAY_NOTIFICATION);
		}
	}

}
