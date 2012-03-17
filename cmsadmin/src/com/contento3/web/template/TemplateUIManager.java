package com.contento3.web.template;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import javax.servlet.http.HttpSession;

import com.contento3.cms.page.template.dto.TemplateDirectoryDto;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.service.TemplateDirectoryService;
import com.contento3.cms.page.template.service.TemplateService;
import com.contento3.web.UIManager;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

public class TemplateUIManager implements UIManager{

	/**
	 * Used to get service beans from spring context.
	 */
    private SpringContextHelper helper;
    
    /**
     * Template Service to access template related services.
     */
    private TemplateService templateService;
    
    /**
     * Template Directory Service to access template 
     * directory related services.
     */
    private TemplateDirectoryService templateDirectoryService;
    
    /**
     * Represents the root object of a tree that contains template and template directories.
     */
    private Tree root;
    
    /**
     * Represents the parent window of the template ui
     */
	private Window parentWindow;

	/**
	 * Used to hold the id of currently selected
	 * directory from the tree.
	 */
	private Integer selectedDirectoryId;

	TabSheet templateTab;

	/**
	 * Constructor 
	 * @param helper
	 * @param parentWindow
	 */
	public TemplateUIManager(final SpringContextHelper helper,final Window parentWindow){
		this.helper = helper;
		this.parentWindow = parentWindow;
	    this.templateService = (TemplateService)helper.getBean("templateService");
	    this.templateDirectoryService = (TemplateDirectoryService)helper.getBean("templateDirectoryService"); 
	}
	

	@Override
	public void render() {
	}

	@Override
	public Component render(String command) {

		if (null==templateTab){ 
			templateTab = new TabSheet();
			templateTab.setHeight("585");
			templateTab.setWidth(100,Sizeable.UNITS_PERCENTAGE);
	    	
	    	VerticalLayout layout = new VerticalLayout();
	    	layout.setWidth(100,Sizeable.UNITS_PERCENTAGE);

	    	Tab tab2 = templateTab.addTab(layout,"Template",null);
	    	renderTemplateListTab(layout);
		}
			return templateTab;
	}

	@Override
	public Component render(String command, Integer entityFilterId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void renderTemplateListTab(VerticalLayout vLayout){
		// Create the Accordion.
		Accordion accordion = new Accordion();
		
		// Have it take all space available in the layout.
		accordion.setSizeFull();

		// Some components to put in the Accordion.
		Label l1 = new Label("There are no previously saved actions.");
		VerticalLayout templateListLayout = new VerticalLayout();

		// Add the components as tabs in the Accordion.
		Tab tab2 = accordion.addTab(templateListLayout, "Templates", null);
		accordion.addTab(l1, "Global Templates", null);
		TemplateDirectoryService templateDirectoryService = (TemplateDirectoryService)helper.getBean("templateDirectoryService");
		Collection <TemplateDirectoryDto> templateDirectoryList =  templateDirectoryService.findRootDirectories(false);

		//Add the tree to the vertical layout for template list.
		templateListLayout.addComponent(populateTemplateList(templateDirectoryList,templateDirectoryService));

		// A container for the Accordion.
		Panel panel = new Panel();
		panel.setWidth("600px");
		panel.setHeight("500px");
		panel.addComponent(accordion);

		Button newTemplate = new Button();
		newTemplate.setCaption("Create template");
		newTemplate.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				renderTemplate(null);
			}
		});

		Button newDirectory = new Button();
		newDirectory.setCaption("Create folder");
		newDirectory.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				try {
				renderFolderTab(new Integer (root.getValue().toString()));
				}
				catch(Exception e){
					parentWindow.showNotification(String.format("Please select parent folder to add new folder"),Notification.TYPE_WARNING_MESSAGE);
				}
			}
		});
		
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.addComponent(newTemplate);
		buttonLayout.addComponent(newDirectory);
		buttonLayout.setSpacing(true);
		//Add the accordion
		vLayout.addComponent(panel);
		panel.addComponent(buttonLayout);
	}
	
	public Tree populateTemplateList(final Collection<TemplateDirectoryDto> directoryDtos,final TemplateDirectoryService templateDirectoryService){

		final HierarchicalContainer templateContainer = new HierarchicalContainer();
        root = new Tree("",templateContainer);
        root.setImmediate(true);
    	root.setItemCaptionPropertyId("name");

        templateContainer.addContainerProperty("id", Integer.class, null);
        templateContainer.addContainerProperty("fileid", String.class, null);
        templateContainer.addContainerProperty("name", String.class, null);
        
        Item item;
        for (TemplateDirectoryDto directoryDto : directoryDtos){
        	item = templateContainer.addItem(directoryDto.getId());
        	item.getItemProperty("id").setValue(directoryDto.getId());
        	item.getItemProperty("name").setValue(directoryDto.getDirectoryName());
        	templateContainer.setChildrenAllowed(directoryDto.getId(), true);
        }

        root.expandItem(new Integer (1));
        root.addListener(new ItemClickListener() {
			private static final long serialVersionUID = -4607219466099528006L;
        	public void itemClick(ItemClickEvent event) {
        		
        		root.expandItem(event.getItemId());
        		String itemId = event.getItemId().toString();
        		//Check if the itemId is for a directory
        		if (!itemId.startsWith("file:")){
        			Item parentItem = event.getItem();
        			addChildrenToSelectedDirectory(parentItem,templateDirectoryService,templateContainer);
        		}
        		else {
        			renderTemplate(new Integer(itemId.substring(5)));
        		}
        	}	
        });
        return root;
	}
	
	private void addChildrenToSelectedDirectory(final Item parentItem,
			final TemplateDirectoryService templateDirectoryService,final HierarchicalContainer templateContainer){
		selectedDirectoryId = Integer.parseInt(parentItem.getItemProperty("id").getValue().toString());
		String name = parentItem.getItemProperty("name").getValue().toString();

		Collection <TemplateDirectoryDto> templateDirectoryDtoList = templateDirectoryService.findChildDirectories(selectedDirectoryId);

		for (TemplateDirectoryDto templateDirectoryDto: templateDirectoryDtoList){
				Integer itemToAdd = templateDirectoryDto.getId();
				if (null==templateContainer.getItem(itemToAdd)) {
					Item item = templateContainer.addItem(itemToAdd);
					item.getItemProperty("id").setValue(itemToAdd);
					item.getItemProperty("name").setValue(templateDirectoryDto.getDirectoryName());
					templateContainer.setParent(templateDirectoryDto.getId(), selectedDirectoryId);
					templateContainer.setChildrenAllowed(templateDirectoryDto.getId(), true);
				}
			}
		
		Collection <TemplateDto> templateDtoList = templateService.findTemplateByDirectoryName(name);
		
		for (TemplateDto templateDto: templateDtoList){
			String templateItemId = String.format("file:%d",templateDto.getTemplateId());
			if (null==templateContainer.getItem(templateItemId)){
				Item item = templateContainer.addItem(templateItemId);
				item.getItemProperty("fileid").setValue(templateItemId);
				item.getItemProperty("name").setValue(templateDto.getTemplateName());
				templateContainer.setParent(String.format("file:%d",templateDto.getTemplateId()), selectedDirectoryId);
				templateContainer.setChildrenAllowed(String.format("file:%d",templateDto.getTemplateId()), true);
			}
		}
 	}
	
	private void renderTemplate(Integer templateId){
		final VerticalLayout createNewTemplate = new VerticalLayout();
		URL url = null;

    	try {
        	StringBuffer urlStr = new StringBuffer("http://localhost:8080/cms/jsp/codemirror");

            //Get accountId from the session
            WebApplicationContext ctx = ((WebApplicationContext) parentWindow.getApplication().getContext());
            HttpSession session = ctx.getHttpSession();
            Integer accountId = (Integer)session.getAttribute("accountId");

        	if (null != templateId){
        		TemplateDto templateDto = templateService.findTemplateById(templateId);
        		urlStr.append("?templateId=")
        			  .append(templateId)
        			  .append("&templateName=")
        			  .append(templateDto.getTemplateName())
        			  .append("&directoryId=")
  			          .append(templateDto.getTemplateDirectoryDto().getId())
        			  .append("&templateTypeId=")
		              .append(templateDto.getTemplateType().getTemplateTypeId())
		        	  .append("&accountId=")
				      .append(accountId);

            	url = new URL(urlStr.toString());
        	}
        	else {
        		//This is a new template to be created we need to get the directory which was selected.
        		if (null == selectedDirectoryId){
					parentWindow.showNotification(String.format("Please select template directory to create a new template."),Notification.TYPE_WARNING_MESSAGE);
        		}
        		else {
        			TemplateDirectoryDto directoryDto = templateDirectoryService.findById(selectedDirectoryId);
        			  urlStr.append("?accountId=")
  			  	      .append(session.getAttribute("accountId"))
  			  	      .append("&directoryId=")
  			  	      .append(directoryDto.getId())
  			  	      .append("&templateName=")
  			  	      .append("")
        			  .append("&directoryPath=")
  			          .append(String.format("/%s",buildPath(selectedDirectoryId,directoryDto.getDirectoryName())));
                  	url = new URL(urlStr.toString());
        		}
        	}
		} 
		catch (MalformedURLException exception) {
			exception.printStackTrace();
		}

		if (null!=url){
			Embedded browser = new Embedded("", new ExternalResource(url));
			browser.setType(Embedded.TYPE_BROWSER);
			browser.setSizeFull();

			createNewTemplate.setWidth(100,Sizeable.UNITS_PERCENTAGE);
			createNewTemplate.setHeight(100,Sizeable.UNITS_PERCENTAGE);
			createNewTemplate.addComponent(browser);

			Tab tab2= templateTab.addTab(createNewTemplate,"Create template",null);
			tab2.setClosable(true);
			templateTab.setSelectedTab(createNewTemplate);
		}
	}

	private void renderFolderTab(Integer folderId){
		final VerticalLayout createNewFolder = new VerticalLayout();

		if (null != folderId){
			TemplateDirectoryDto templateDirectory = templateDirectoryService.findById(folderId);	
		
			final FormLayout formLayout = new FormLayout();
			TextField name = new TextField();
			name.setCaption("Name");
			formLayout.addComponent(name);

			TextField parentPath = new TextField();
			parentPath.setCaption("Parent");
			parentPath.setEnabled(false);
			parentPath.setValue(String.format("/%s",buildPath(folderId,templateDirectory.getDirectoryName())));
			formLayout.addComponent(parentPath);

			Button addButton = new Button();
			addButton.setCaption("Add Folder");
			formLayout.addComponent(addButton);
	   			
			createNewFolder.setWidth(100,Sizeable.UNITS_PERCENTAGE);
			createNewFolder.setHeight(100,Sizeable.UNITS_PERCENTAGE);
			createNewFolder.addComponent(formLayout);
    	
			Tab tab2= templateTab.addTab(createNewFolder,"Create directory",null);
			tab2.setClosable(true);
			templateTab.setSelectedTab(createNewFolder);
		}
	}

	private String buildPath(Integer id,String path){
		if (null!=root.getParent(id)){
		Integer itemId = (Integer) root.getParent(id);
		return buildPath(itemId,root.getItemCaption(itemId) +"/"+ path); 
		}
		else return path;
	}

	@Override
	public Component render(String command,
			HierarchicalContainer treeItemContainer) {
		// TODO Auto-generated method stub
		return null;
	}
}
