package com.contento3.web.content.document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;

import com.contento3.common.dto.Dto;
import com.contento3.dam.document.dto.DocumentDto;
import com.contento3.dam.document.service.DocumentService;
import com.contento3.security.permission.dao.PermissionDao;
import com.contento3.web.UIManager;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.ScreenToolbarBuilder;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.content.document.listener.AddDocumentButtonListener;
import com.contento3.web.content.document.listener.DocumentFormBuilderListner;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class DocumentMgmtUIManager implements UIManager, ClickListener {
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(DocumentMgmtUIManager.class);
	private final static String BUTTON_NAME_SEARCH = "Search";

	
	/**
	 * Used to get service beans from spring context.
	 */
	private final SpringContextHelper contextHelper;
	private transient PermissionDao permissionDao;
	
	/**
	 * TabSheet serves as the parent container for the document manager
	 */

	private final TabSheet tabSheet;

	/**
	 * main layout for document manager screen
	 */
	private VerticalLayout verticalLayout;
	
	/**
	 * Article table which shows documents
	 */
	private final Table documentTable =  new Table();

	/**
	 * Document service for document related operations
	 */
	private final DocumentService documentService;
	
	/**
	 * Account id
	 */
	private final Integer accountId;
	
	
	/**
	 * Document table builder
	 */
	private AbstractTableBuilder tableBuilder;
	
	/**
	 * Documents collection
	 */
	private Collection<DocumentDto> documents;

	/**
	 * Document search field
	 */
	private TextField searchField;

	
	/**
	 * Constructor
	 * @param TabSheet
	 * @param contextHelper
	 * @param parentWindow
	 */
	public DocumentMgmtUIManager(final TabSheet tabSheet, final SpringContextHelper contextHelper) {
		this.tabSheet = tabSheet;
		this.contextHelper = contextHelper;
		this.documentService = (DocumentService) this.contextHelper.getBean("documentService");
		
		//get account if from session
		//WebApplicationContext webContext = (WebApplicationContext) VaadinServlet.getCurrent().getServletContext();
		this.accountId = (Integer) UI.getCurrent().getSession().getAttribute(("accountId"));
	}
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Component render(String command) {
		this.tabSheet.setHeight(100, Unit.PERCENTAGE);
		
		if (null==tabSheet.getTab(verticalLayout)){
			verticalLayout = new VerticalLayout();
			final Tab docTab = tabSheet.addTab(verticalLayout, "Document Management",new ExternalResource("images/document.png"));
			docTab.setClosable(true);
			this.verticalLayout.setSpacing(true);
			this.verticalLayout.setWidth(100,Unit.PERCENTAGE);
			renderDocumentComponent();
		}
		
		tabSheet.setSelectedTab(verticalLayout);
		return this.tabSheet;
	}

	@Override
	public Component render(String command, Integer entityFilterId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component render(String command,
			HierarchicalContainer treeItemContainer) {
		// TODO Auto-generated method stub
		return null;
	}
	private void renderDocumentComponent(){
		final Label documentHeading = new Label("Document Manager");
		documentHeading.setStyleName("screenHeading");
		HorizontalLayout horizontal = new HorizontalLayout();
		VerticalLayout innerLayout = new VerticalLayout();
		horizontal.addComponent(innerLayout);
		this.verticalLayout.addComponent(horizontal);
		innerLayout.addComponent(documentHeading);
		innerLayout.setMargin(true);
		innerLayout.setSpacing(true);
		
		GridLayout toolbarGridLayout = new GridLayout(1,1);
		List<com.vaadin.event.MouseEvents.ClickListener> listeners = new ArrayList<com.vaadin.event.MouseEvents.ClickListener>();
		try
		{
			listeners.add(new AddDocumentButtonListener(this.contextHelper,this.tabSheet,this.documentTable));
		}
		catch(AuthorizationException ex){Notification.show("You are not permitted to add documents");}
		//listeners.add(new PageViewCategoryListener(pageId,contextHelper));
		//listeners.add(new PageViewCategoryListener(pageId,contextHelper));
		ScreenToolbarBuilder builder = new ScreenToolbarBuilder(toolbarGridLayout,"document",listeners);
		builder.build();
		horizontal.addComponent(toolbarGridLayout);
		horizontal.setWidth(100,Unit.PERCENTAGE);
		horizontal.setExpandRatio(innerLayout, 8);
		horizontal.setExpandRatio(toolbarGridLayout, 1);
		//addDocumentButton();
		renderSearchPanel(innerLayout);
		renderDocumentTable(innerLayout);

	}
		
	/**
	 * Render search panel
	 * @param layout
	 */
	public void renderSearchPanel(final VerticalLayout layout) {
		
		final FormLayout txtFieldLayout = new FormLayout();
		searchField = new TextField("Document");
		searchField.setInputPrompt("Document name");
		searchField.addStyleName("horizontalForm");
		txtFieldLayout.addComponent(searchField);
		
	    final Button searchButton = new Button(BUTTON_NAME_SEARCH);
	    searchButton.addClickListener(this);
	    
		final GridLayout searchBar = new GridLayout(3,1);
		searchBar.setSizeFull();
	    searchBar.setMargin(true);
		searchBar.setSpacing(true);
		searchBar.addStyleName("horizontalForm");
		searchBar.addComponent(txtFieldLayout);
		searchBar.addComponent(searchButton);
		searchBar.setComponentAlignment(searchButton, Alignment.MIDDLE_CENTER);
		searchBar.setWidth(800, Unit.PIXELS);

		final Panel searchPanel = new Panel();
		searchPanel.setSizeUndefined(); 
		searchPanel.setContent(searchBar);
		layout.addComponent(searchPanel);
		searchPanel.setWidth(100,Unit.PERCENTAGE);

	}
		
	/**
	 * Render document table
	 */
	@SuppressWarnings("unchecked")
	private void renderDocumentTable(final VerticalLayout innerLayout) {
		tableBuilder = new DocumentTableBuilder(this.contextHelper, this.tabSheet, this.documentTable);
		try {
		documents = this.documentService.findByAccountId((Integer)SessionHelper.loadAttribute("accountId"));
		tableBuilder.build((Collection)documents);}
		catch(AuthorizationException ex){Notification.show("You are not permitted to view documents");}
		innerLayout.addComponent(this.documentTable);
	}

	/**
	 * Display "Add Document" button on the top of tab 
	 */
	
	private void addDocumentButton(){
		com.contento3.security.permission.model.Permission permission =  permissionDao.findById(17);
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isPermitted("document:add"))
		{
		final Button addButton = new Button("Add Document");
		addButton.addClickListener(new DocumentFormBuilderListner(this.contextHelper,this.tabSheet,this.documentTable));
		this.verticalLayout.addComponent(addButton);
		}
		
	}
	
	/**
	 * Prepare search result and add to table
	 * @param searchTxt
	 */
	private void prepareSearchResult(String searchTxt) {
		
		if(!searchTxt.equals("")) {
			
			Collection<Dto> searchedDcmnt = new ArrayList<Dto>();
			
			for (DocumentDto dto : this.documents) {
				
				if(dto.getDocumentTitle().toLowerCase().contains(searchTxt)) {
					searchedDcmnt.add(dto);
				}
			}
			tableBuilder.rebuild(searchedDcmnt);
		} else {
			tableBuilder.rebuild((Collection)this.documents);
		}
		
	}

	/**
	 * Button click handler
	 */
	@Override
	public void buttonClick(ClickEvent event) {

		String searchTxt = searchField.getValue();
		prepareSearchResult(searchTxt.toLowerCase());
	}
	
}
