package com.contento3.web.content.document;

import com.contento3.common.dto.Dto;
import com.contento3.dam.document.dto.DocumentDto;
import com.contento3.dam.document.service.DocumentService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.content.document.listener.DocumentDeleteListener;
import com.contento3.web.content.document.listener.DocumentFormBuilderListner;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

public class DocumentTableBuilder extends AbstractTableBuilder {

	/**
	 * Helper to get the spring bean
	 */
	final SpringContextHelper contextHelper;

	/**
    * Represents the parent window of the ui
    */
	final Window window;

	/**
	 * TabSheet serves as the parent container for the document manager
	 */
	private TabSheet tabSheet;

	/**
	 * Document service used for document related operations
	 */
	final DocumentService documentService;

	/**
	 * Constructor
	 * @param window
	 * @param helper
	 * @param tabSheet
	 * @param table
	 */
	public DocumentTableBuilder(final Window window, final SpringContextHelper contextHelper, final TabSheet tabSheet, final Table table) {
		super(table);
		
		this.contextHelper = contextHelper;
		this.tabSheet = tabSheet;
		this.window = window;
		this.documentService = (DocumentService) contextHelper.getBean("documentService");
	}

	/**
	 * Assign data to table
	 * @param dto
	 * @param documentTable
	 * @param documentContainer
	 */
	@Override
	public void assignDataToTable(final Dto dto, final Table documentTable, final Container documentContainer) {
		DocumentDto documentDto = (DocumentDto) dto;
		Item item = documentContainer.addItem(documentDto.getDocumentId());
		item.getItemProperty("documents").setValue(documentDto.getDocumentTitle());
		

		Button editLink = new Button();
		editLink.setCaption("Edit");
		editLink.setData(documentDto.getDocumentId());
		editLink.addStyleName("edit");
		editLink.setStyleName(BaseTheme.BUTTON_LINK);
		editLink.addListener(new DocumentFormBuilderListner(this.contextHelper, this.window,this.tabSheet,documentTable));
		item.getItemProperty("edit").setValue(editLink);
		
		Button deleteLink = new Button();
		deleteLink.setCaption("Delete");
		deleteLink.setData(documentDto.getDocumentId());
		deleteLink.addStyleName("delete");
		deleteLink.setStyleName(BaseTheme.BUTTON_LINK);
		item.getItemProperty("delete").setValue(deleteLink);
		deleteLink.addListener(new DocumentDeleteListener(documentDto, window, documentService, deleteLink, documentTable));
	}

	/**
	 * Build header for table
	 */
	@Override
	public void buildHeader(final Table documentTable, final Container documentContainer) {
		documentContainer.addContainerProperty("documents", String.class, null);
		documentContainer.addContainerProperty("edit", Button.class, null);
		documentContainer.addContainerProperty("delete", Button.class, null);
		documentTable.setWidth(100, Sizeable.UNITS_PERCENTAGE);
		documentTable.setContainerDataSource(documentContainer);
	}

	/**
	 * Create empty table
	 */
	@Override
	public void buildEmptyTable(final Container documentContainer) {
		Item item = documentContainer.addItem("-1");
		item.getItemProperty("documents").setValue("No record found.");
	}
	
}
