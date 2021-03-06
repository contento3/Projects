package com.contento3.web.content.document;

import java.util.ArrayList;

import com.contento3.dam.document.dto.DocumentTypeDto;
import com.contento3.web.common.UIContext;
import com.contento3.web.content.document.listener.FileUploadListener;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;

public class DocumentForm extends UIContext implements ValueChangeListener {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Textfield for document heading
	 */
	private TextField documentTitle = new TextField();
	
	/**
	 * Upload field for document
	 */
	private Upload uploadDocument = new Upload("Upload document...", null);

	/**
	 * Select for DocumentType
	 */
	private ComboBox selectDocumentType = new ComboBox();

	/**
	 * Listener for the Upload Field
	 */
	private FileUploadListener documentUploadListener;

	/**
	 * String to hold the document type selected by the user from dropdown
	 */
	private String selectedDocumentType;
	
	/**
	 * Constructor
	 * */
	public DocumentForm(FileUploadListener fileUploadListener){
		documentUploadListener = fileUploadListener;
		
		uploadDocument.setReceiver((Upload.Receiver) documentUploadListener);
		uploadDocument.addSucceededListener((Upload.SucceededListener) documentUploadListener);
		uploadDocument.addFailedListener((Upload.FailedListener) documentUploadListener);
		
		selectDocumentType.addValueChangeListener(this);
	}
	
	/**
	 * Get the document title
	 * @param none
	 * @return TextField
	 */
	public TextField getDocumentTitle() {
		return documentTitle;
	}

	/**
	 * Set the document title
	 * @param TextField
	 * @return void
	 */
	public void setDocumentTitle(final TextField documentTitle) {
		this.documentTitle = documentTitle;
	}

	/**
	 * Get the Upload field
	 * @param none
	 * @return Upload
	 */
	public Upload getUploadDocument() {
		return uploadDocument;
	}

	/**
	 * Set the upload field
	 * @param Upload
	 * @return void
	 */
	public void setUploadDocument(Upload uploadDocument) {
		this.uploadDocument = uploadDocument;
	}
	
	/**
	 * Get the Select for DocumentType
	 * @param none
	 * @return Select
	 */
	public ComboBox getSelectDocumentType() {
		return selectDocumentType;
	}
	
	/**
	 * Set the Select for DocumentType
	 * @param Select
	 * @return none
	 */
	public void setSelectDocumentType(Select selectDocumentType) {
		this.selectDocumentType = selectDocumentType;
	}
	
	public byte[] getUploadedDocument(){
		return documentUploadListener.getUploadedFile();
	}
	
	public void setUploadedDocument(byte[] uploadedFile){
		documentUploadListener.setUploadedFile(uploadedFile);
	}
	
	public String getSelectedDocumentType(){
		return selectedDocumentType;
	}
	
	public void fillDocumentTypeList(ArrayList<DocumentTypeDto> documentTypeList) {
		for(DocumentTypeDto documentTypeDto : documentTypeList)
			selectDocumentType.addItem(documentTypeDto.getName());
	}
	
	/* This method is responsible for listening to the changes made
	 * by the user in the document type Select list.
	 * */
	@Override
	public void valueChange(ValueChangeEvent event) {
		this.selectedDocumentType = event.getProperty().toString();
	}
}
