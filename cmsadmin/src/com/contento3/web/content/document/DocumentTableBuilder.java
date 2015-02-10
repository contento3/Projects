package com.contento3.web.content.document;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.shiro.SecurityUtils;

import com.contento3.common.dto.Dto;
import com.contento3.dam.document.dto.DocumentDto;
import com.contento3.dam.document.service.DocumentService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.content.document.listener.DocumentDeleteListener;
import com.contento3.web.content.document.listener.DocumentFormBuilderListner;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Button;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.BaseTheme;

public class DocumentTableBuilder extends AbstractTableBuilder {

	private final static String BUTTON_NAME_DOWNLOAD = "Download";

	/**
	 * Helper to get the spring bean
	 */
	final SpringContextHelper contextHelper;

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
	public DocumentTableBuilder(final SpringContextHelper contextHelper, final TabSheet tabSheet, final Table table) {
		super(table);
		
		this.contextHelper = contextHelper;
		this.tabSheet = tabSheet;
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
		
		final DocumentDto documentDto = (DocumentDto) dto;
		Integer id = documentDto.getDocumentId();
		Item item = documentContainer.addItem(id);
		item.getItemProperty("documents").setValue(documentDto.getDocumentTitle());
		
		if (SecurityUtils.getSubject().isPermitted("DOCUMENT:EDIT")) {
			final Button editLink = createLinkButton("Edit", id);
			editLink.addClickListener(new DocumentFormBuilderListner(this.contextHelper,this.tabSheet,documentTable));
			item.getItemProperty("edit").setValue(editLink);
		}
		
		if (SecurityUtils.getSubject().isPermitted("DOCUMENT:DELETE")) {
			final Button deleteLink = createLinkButton("Delete", id);
			item.getItemProperty("delete").setValue(deleteLink);
			deleteLink.addClickListener(new DocumentDeleteListener(documentDto, documentService, deleteLink, documentTable));
		}
		
		if (SecurityUtils.getSubject().isPermitted("DOCUMENT:DOWNLOAD")) {
			final Button btnDownload = createLinkButton(BUTTON_NAME_DOWNLOAD, id);
		
			//Download document
			final StreamResource res =  createResource(documentDto);
			final FileDownloader fd = new FileDownloader(res);
			fd.extend(btnDownload);
			
			item.getItemProperty(BUTTON_NAME_DOWNLOAD).setValue(btnDownload);
		}
	}
		
	private Button createLinkButton(final String caption, final Integer id) {
		
		Button linkButton = new Button();
		linkButton.setCaption(caption);
		linkButton.setData(id);
		linkButton.addStyleName(caption.toLowerCase());
		linkButton.setStyleName(BaseTheme.BUTTON_LINK);
		return linkButton;
	}

	/**
	 * Build header for table
	 */
	@Override
	public void buildHeader(final Table documentTable, final Container documentContainer) {
		documentContainer.addContainerProperty("documents", String.class, null);
		
		if (SecurityUtils.getSubject().isPermitted("DOCUMENT:EDIT")){
			documentContainer.addContainerProperty("edit", Button.class, null);
		}
		
		if (SecurityUtils.getSubject().isPermitted("DOCUMENT:DELETE")){
			documentContainer.addContainerProperty("delete", Button.class, null);
		}
		
		if (SecurityUtils.getSubject().isPermitted("DOCUMENT:DOWNLOAD")){
			documentContainer.addContainerProperty(BUTTON_NAME_DOWNLOAD, Button.class, null);
		}
		
		documentTable.setWidth(100, Unit.PERCENTAGE);
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
	
	/**
	 * Get full file name
	 * @param dto
	 * @return
	 */
	private String getFileNameWithExtension(final DocumentDto dto) {
		
		return dto.getDocumentTitle()+ "." + dto.getDocumentTypeDto().getName().toLowerCase();
	}
	
	/**
	 * Prepare resource to download.
	 * @param dto
	 * @return
	 */
	private StreamResource createResource(final DocumentDto dto) {
        return new StreamResource(new StreamSource() {
            @Override
            public InputStream getStream() {

                try {
                	return new ByteArrayInputStream(dto.getDocumentContent());
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }, getFileNameWithExtension(dto));
    }
}
