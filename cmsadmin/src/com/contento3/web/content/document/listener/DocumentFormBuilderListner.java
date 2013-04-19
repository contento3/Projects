package com.contento3.web.content.document.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.contento3.account.service.AccountService;
import com.contento3.dam.document.dto.DocumentDto;
import com.contento3.dam.document.dto.DocumentTypeDto;
import com.contento3.dam.document.service.DocumentService;
import com.contento3.dam.document.service.DocumentTypeService;
import com.contento3.web.common.helper.ScreenHeader;
import com.contento3.web.common.helper.ScreenToolbarBuilder;
import com.contento3.web.content.document.DocumentForm;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TabSheet.Tab;

public class DocumentFormBuilderListner implements ClickListener {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Used to get service beans from spring context.
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
	 * layout when adding or editing document
	 */
	private VerticalLayout formLayout;
	
	/**
	 * Document service for article related operations
	 */
	private DocumentService documentService;
	
	/**
	 * Account service for account related activities
	 */
	private AccountService accountService;
	
	/**
	 * Document table which shows articles
	 */
	private Table documentTable;
	
	
	/**
	 * Document accountid 
	 */
	private Integer accountId;
	
	/**
	 * Screen Header
	 */
	private ScreenHeader screenHeader; 
	
	/**
	 * Layout thats holds all the component
	 */
	private HorizontalLayout parentLayout;
	
	/**
	 * Form that contains all the fields that are 
	 * required to be displayed on document screen.
	 */
	private DocumentForm documentForm;

	private Tab documentTab;
	
	public DocumentFormBuilderListner(final SpringContextHelper helper,final Window parentWindow,final TabSheet tabSheet,final Table documentTable) {
		this.contextHelper= helper;
		this.parentWindow = parentWindow;
		this.tabSheet = tabSheet;
		this.documentService = (DocumentService) this.contextHelper.getBean("documentService");
		this.accountService = (AccountService) this.contextHelper.getBean("accountService");
		this.documentTable = documentTable;
		
        final WebApplicationContext webAppContext = ((WebApplicationContext) parentWindow.getApplication().getContext());
        final HttpSession session = webAppContext.getHttpSession();
        this.accountId =(Integer)session.getAttribute("accountId");
        
        /**
         * The fileUploadListener must know the Upload that it's listening to.
         * Otherwise, it would not be able to check the restriction on max file
         * size. If the user uploads a file size greater than the max size, it'll
         * throw an exception.
         * - Ali.
         */
        final FileUploadListener fileUploadListener = new FileUploadListener(parentWindow);
        documentForm = new DocumentForm(fileUploadListener);
        documentForm.setContextHelper(helper);
        documentForm.setParentWindow(parentWindow);
        documentForm.setTabSheet(tabSheet);
        
        fileUploadListener.setUpload(documentForm.getUploadDocument());
        
        documentForm.fillDocumentTypeList(this.getDocumentTypeList());
	}
	
	private ArrayList<DocumentTypeDto> getDocumentTypeList(){
		DocumentTypeService documentTypeService = (DocumentTypeService) this.contextHelper.getBean("documentTypeService");
		
		ArrayList<DocumentTypeDto> documentTypeList = (ArrayList<DocumentTypeDto>) documentTypeService.findAllTypes();
		
		return documentTypeList;
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		String buttonCaption = event.getButton().getCaption();
		
		if(buttonCaption.equals("Edit")){
			Object id = event.getButton().getData();
			renderEditDocumentScreen( Integer.parseInt( id.toString() ) );
		} else {
			renderAddDocumentScreen();
		}
	}

	private void renderAddDocumentScreen() {
		buildDocumentUI("Add", null);
		
		//Redundant code below - no longer used
		final Button saveButton = new Button("Save");
		saveButton.addListener( new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				final DocumentDto document = new DocumentDto();
				//Save the document to db, and perform house keeping
			}
		});
	}

	private void renderEditDocumentScreen(int documentId) {
		buildDocumentUI("Edit", documentId);
		
		final DocumentDto document = this.documentService.findById(documentId);
		documentForm.getDocumentTitle().setValue(document.getDocumentTitle());
		documentForm.getSelectDocumentType().setValue(document.getDocumentTypeDto().getName());
		documentForm.setUploadedDocument(document.getDocumentContent());
		
		//Redundant code below - no longer used
		final Button editButton = new Button("Edit");
		
		editButton.addListener( new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				//save edited doc
			}
		});
	}
	
	private void buildDocumentUI(final String command, final Integer documentId) {
		formLayout = new VerticalLayout();
        screenHeader = new ScreenHeader(formLayout,"Documents");

		parentLayout = new HorizontalLayout();
		parentLayout.setSizeFull();
		parentLayout.addComponent(formLayout);
		
		documentTab = this.tabSheet.addTab(parentLayout,command+" Document",new ExternalResource("images/content-mgmt.png"));
		documentTab.setClosable(true);

		GridLayout toolbarGridLayout = new GridLayout(1,1);
		List<com.vaadin.event.MouseEvents.ClickListener> listeners = new ArrayList<com.vaadin.event.MouseEvents.ClickListener>();
		listeners.add(new DocumentSaveListener(documentTab, documentForm, documentTable, documentId));
		//listeners.add(new DocumentEditListener(documentTab, documentForm, documentTable, documentId));
		
		ScreenToolbarBuilder builder = new ScreenToolbarBuilder(toolbarGridLayout,"article",listeners);
		builder.build();
		
		parentLayout.addComponent(toolbarGridLayout);
		parentLayout.setExpandRatio(toolbarGridLayout, 1);
		parentLayout.setExpandRatio(formLayout, 10);
		parentLayout.setComponentAlignment(toolbarGridLayout, Alignment.TOP_RIGHT);
		tabSheet.setSelectedTab(parentLayout);
		
		documentForm.getDocumentTitle().setCaption("Document Name");
		documentForm.getDocumentTitle().setColumns(20);
		documentForm.getUploadDocument().setCaption("Upload Document");
		documentForm.getSelectDocumentType().setCaption("Document Type");
		
        formLayout.addComponent(documentForm.getDocumentTitle());
        formLayout.addComponent(documentForm.getSelectDocumentType());
        formLayout.addComponent(documentForm.getUploadDocument());
	    
	    formLayout.setMargin(true);
	}
}
