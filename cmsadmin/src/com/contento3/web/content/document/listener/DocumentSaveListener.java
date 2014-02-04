package com.contento3.web.content.document.listener;

import java.util.Collection;

import org.apache.shiro.authz.AuthorizationException;

import com.contento3.account.service.AccountService;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.dam.document.dto.DocumentDto;
import com.contento3.dam.document.service.DocumentService;
import com.contento3.dam.document.service.DocumentTypeService;
import com.contento3.dam.storagetype.dto.StorageTypeDto;
import com.contento3.dam.storagetype.service.StorageTypeService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.content.document.DocumentForm;
import com.contento3.web.content.document.DocumentTableBuilder;
import com.contento3.web.content.document.StorageTypeEnum;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;

public class DocumentSaveListener implements ClickListener {
	private static final long serialVersionUID = 1L;

	/**
	 * Document Service for document related operations
	 */
	private DocumentService documentService;
	
	/**
	 * Account Service for account related operations
	 */
	private AccountService accountService;
	
	/**
	 * Document Type Service for document type related operations
	 */
	private DocumentTypeService documentTypeService;
	
	/**
	 * Storage Type Service for storage type related operations
	 */
	private StorageTypeService storageTypeService;
	
	/**
	 * Content helper for keeping track of context
	 */
	private SpringContextHelper contextHelper;
	
	/**
     * Represents the parent window of the ui
     */
	private Window parentWindow;

	/**
	 * TabSheet serves as the parent container for the document manager
	 */
	private TabSheet tabSheet;
	
	/**
	 * Represents the document tab inside the tabsheet
	 */
	private Tab documentTab;
	
	/**
	 * DocumentForm represents the form for document 
	 */
	private DocumentForm documentForm;
	
	/**
	 * Represents the table ui of the document
	 */
	private Table documentTable;
	
	/**
	 * Holds the document id of the document being saved
	 */
	private Integer documentId;
	
	/**
	 * Textfield for document heading
	 */
	private Integer accountId;
	
	public DocumentSaveListener(final Tab documentTab, final DocumentForm documentForm, 
								final Table documentTable, final Integer documentId){
		this.documentTab = documentTab;
		this.documentForm = documentForm;
		this.documentTable = documentTable;
		this.documentId = documentId;
		
		this.tabSheet = documentForm.getTabSheet();
		this.parentWindow = documentForm.getParentWindow();
		this.contextHelper = documentForm.getContextHelper();
		this.documentService = (DocumentService) contextHelper.getBean("documentService");
		this.accountService = (AccountService) contextHelper.getBean("accountService");
		this.documentTypeService = (DocumentTypeService) contextHelper.getBean("documentTypeService");
		this.storageTypeService = (StorageTypeService) contextHelper.getBean("storageTypeService");
		
		//get account if from session
		this.accountId = (Integer) SessionHelper.loadAttribute("accountId");
	}
	
	@Override
	public void click(ClickEvent event) {
		if(documentForm.getUploadedDocument() == null){
			Notification.show("You must upload a document to proceed.");
			return;
		}
		
		DocumentDto documentDto;
		final StorageTypeDto storageTypeDto = (StorageTypeDto) storageTypeService.findByName(StorageTypeEnum.DATABASE.getType());
		
		if(documentId == null)
			documentDto = new DocumentDto();
		else
			documentDto = this.documentService.findById(documentId);
		
		documentDto.setDocumentTitle( documentForm.getDocumentTitle().getValue().toString() );
		documentDto.setDocumentTypeDto( documentTypeService.findByName(documentForm.getSelectedDocumentType()) );
		documentDto.setAccount( accountService.findAccountById(accountId) );
		documentDto.setDocumentContent( documentForm.getUploadedDocument() );
		documentDto.setStorageTypeDto(storageTypeDto);
		
		try{
			if(documentId == null){
				documentService.create(documentDto);
			} else {
				documentService.update(documentDto);
			}
		} catch (EntityAlreadyFoundException e) {
			Notification.show("The document was not created, because a document " +
										"with the same title already exists in the database");
			//e.printStackTrace();
			return;
		} catch (EntityNotCreatedException e) {
			Notification.show("The document was not created due to errors.");
			//e.printStackTrace();
			return;
		}
		catch(AuthorizationException ex){Notification.show("You are not permitted to Add or Update the Document");}
		
		/* At this point we can assume that the document has been created.
		 * Otherwise, Java would've thrown an exception by now.
		 * */
		final String notification = documentDto.getDocumentTitle() + " updated successfully"; 
		Notification.show(notification);
		tabSheet.removeTab(documentTab);
		resetTable();
		tabSheet.removeTab(documentTab);
	}

	/**
	 * Reset table
	 */
	 @SuppressWarnings("rawtypes")
	 private void resetTable(){
		final AbstractTableBuilder tableBuilder = new DocumentTableBuilder(this.contextHelper,this.tabSheet,this.documentTable);
		final Collection<DocumentDto> document = this.documentService.findByAccountId(accountId);
		tableBuilder.rebuild((Collection) document);
	}

}
