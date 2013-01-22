package com.contento3.web.content.document;

import java.util.ArrayList;

import com.contento3.dam.document.dto.DocumentTypeDto;
import com.contento3.web.common.UIContext;
import com.contento3.web.content.document.listener.FileUploadListener;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Window;

public class DocumentForm extends UIContext {
	
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
	private Select selectDocumentType = new Select();
	
	private FileUploadListener documentUploadListener;
	
	/**
	 * Constructor
	 * */
	public DocumentForm(FileUploadListener fileUploadListener){
		documentUploadListener = fileUploadListener;
		
		uploadDocument.addListener((Upload.SucceededListener) documentUploadListener);
		uploadDocument.addListener((Upload.FailedListener) documentUploadListener);
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
	public Select getSelectDocumentType() {
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

	public void fillDocumentTypeList(ArrayList<DocumentTypeDto> documentTypeList) {
		for(DocumentTypeDto documentTypeDto : documentTypeList){
			selectDocumentType.addItem(documentTypeDto.getName());
			selectDocumentType.addItem("foo test");
		}
	}
}
