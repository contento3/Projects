package com.contento3.web.content.document.listener;

import com.contento3.account.service.AccountService;
import com.contento3.dam.document.service.DocumentService;
import com.contento3.web.content.document.DocumentForm;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.TabSheet.Tab;

public class DocumentEditListener implements ClickListener {
	private static final long serialVersionUID = 1L;
	
	private DocumentService documentService;
	private AccountService accountService;
	private SpringContextHelper contextHelper;
	private Window parentWindow;
	
	private TabSheet tabSheet;
	private Tab documentTab;
	private DocumentForm documentForm;
	private Table documentTable;
	private Integer documentId;
	private Integer accountId;
	
	public DocumentEditListener(final Tab documentTab, final DocumentForm documentForm,final Table documentTable, 
			final Integer documentId,final Integer accountId){
		this.documentTab = documentTab;
		this.documentForm = documentForm;
		this.documentTable = documentTable;
		this.documentId = documentId;
		this.accountId = accountId;
		
		this.tabSheet = documentForm.getTabSheet();
		this.parentWindow = documentForm.getParentWindow();
		this.contextHelper = documentForm.getContextHelper();
		this.documentService = (DocumentService) contextHelper.getBean("documentService");
		this.accountService = (AccountService) contextHelper.getBean("accountService");
	}
	
	@Override
	public void click(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}

}
