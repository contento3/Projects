package com.contento3.web.content.document;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;

import com.contento3.dam.document.dto.DocumentDto;
import com.contento3.dam.document.service.DocumentService;
import com.contento3.security.model.Permission;
import com.contento3.security.permission.dao.PermissionDao;
import com.contento3.security.permission.service.PermissionAssembler;
import com.contento3.security.user.dao.SaltedHibernateUserDao;
import com.contento3.web.UIManager;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.content.document.listener.DocumentFormBuilderListner;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class DocumentMgmtUIManager implements UIManager {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(DocumentMgmtUIManager.class);
	
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
	private final VerticalLayout verticalLayout = new VerticalLayout();
	
	/**
	 * Article table which shows documents
	 */
	private final Table documentTable =  new Table("Documents");

	/**
	 * Document service for document related operations
	 */
	private final DocumentService documentService;
	
	/**
	 * Account id
	 */
	private final Integer accountId;
	
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
		final Tab articleTab = tabSheet.addTab(verticalLayout, "Document Management",new ExternalResource("images/content-mgmt.png"));
		articleTab.setClosable(true);
		this.verticalLayout.setSpacing(true);
		this.verticalLayout.setWidth(100,Unit.PERCENTAGE);
		renderDocumentComponent();
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
		this.verticalLayout.addComponent(documentHeading);
		this.verticalLayout.addComponent(new HorizontalRuler());
		this.verticalLayout.setMargin(true);
		addDocumentButton();
		this.verticalLayout.addComponent(new HorizontalRuler());
		renderDocumentTable();
	}
	
	/**
	 * Render document table
	 */
	@SuppressWarnings("unchecked")
	private void renderDocumentTable() {
		final AbstractTableBuilder tableBuilder = new DocumentTableBuilder(this.contextHelper, this.tabSheet, this.documentTable);
		Collection<DocumentDto> documents = this.documentService.findByAccountId(accountId);
		tableBuilder.build((Collection)documents);
		this.verticalLayout.addComponent(this.documentTable);
		
	}

	/**
	 * Display "Add Document" button on the top of tab 
	 */
	
	private void addDocumentButton(){
		com.contento3.security.permission.model.Permission permission =  permissionDao.findById(17);
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isPermitted(permission))
		{
		final Button addButton = new Button("Add Document");
		addButton.addClickListener(new DocumentFormBuilderListner(this.contextHelper,this.tabSheet,this.documentTable));
		this.verticalLayout.addComponent(addButton);
		}
		
	}
	
}
