package com.contento3.web.common.helper;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.contento3.cms.page.template.dto.PageTemplateDto;
import com.contento3.cms.page.template.dto.TemplateDirectoryDto;
import com.contento3.cms.page.template.service.PageTemplateService;
import com.contento3.cms.page.template.service.TemplateDirectoryService;
import com.contento3.cms.page.template.service.TemplateService;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.template.helper.TemplateListingHelper;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

/** Component contains a button that allows opening a window. */
public class PageTemplateAssignmentPopup extends CustomComponent
                          implements Window.CloseListener,Button.ClickListener {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger(PageTemplateAssignmentPopup.class);

    Window mywindow;    // The window to be opened
    Button openbutton;  // Button for opening the window
    Button closebutton; // A button in the window


    PageTemplateService pageTemplateService;
    boolean isModalWindowClosable = true;
    SpringContextHelper helper;
	final AbstractTableBuilder templateTableBuilder;
    public PageTemplateAssignmentPopup(final String label,final SpringContextHelper helper,final AbstractTableBuilder templateTableBuilder) {
        this.helper = helper;
        this.templateTableBuilder = templateTableBuilder;
        // The component contains a button that opens the window.
        final VerticalLayout layout = new VerticalLayout();
        
        openbutton = new Button("Add template");
        openbutton.addClickListener(this);
        layout.addComponent(openbutton);
        setCompositionRoot(layout);
    }

    /** Handle the clicks for the two buttons. */
    public void openButtonClick(Button.ClickEvent event) {
        /* Create a new window. */
        mywindow = new Window("Template page assignment");
        mywindow.setPositionX(200);
        mywindow.setPositionY(100);

        mywindow.setHeight(75,Unit.PERCENTAGE);
        mywindow.setWidth(50,Unit.PERCENTAGE);
        /* Add the window inside the main window. */
        UI.getCurrent().addWindow(mywindow);
        
        /* Listen for close events for the window. */
        mywindow.addCloseListener(this);

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
		TemplateService templateService = (TemplateService)helper.getBean("templateService");

		Collection <TemplateDirectoryDto> templateDirectoryList =  templateDirectoryService.findRootDirectories(false,(Integer)SessionHelper.loadAttribute("accountId"));

		final TemplateListingHelper templateListingHelper = new TemplateListingHelper();
		
		//Add the tree to the vertical layout for template list.
		templateListLayout.addComponent(templateListingHelper.populateTemplateList(templateDirectoryList,templateService,templateDirectoryService));

		
        /* Add components in the window. */
		VerticalLayout contentLayout = new VerticalLayout();
		contentLayout.addComponent(accordion);
        mywindow.setContent(contentLayout);
        closebutton = new Button("Add template");
        
        
        final TextField templateOrdrTxtFld = new TextField();
        templateOrdrTxtFld.setInputPrompt("Order");
        templateOrdrTxtFld.setColumns(3);
        templateOrdrTxtFld.setRequired(true);
        templateOrdrTxtFld.setNullRepresentation("");
        templateOrdrTxtFld.addValidator(new RegexpValidator("[0-9]+", "Input should be at least 5 characters long."));
        templateOrdrTxtFld.setValidationVisible(true);
        templateOrdrTxtFld.setImmediate(true);

        
        HorizontalLayout horizontal = new HorizontalLayout();
        horizontal.setSpacing(true);
        horizontal.addComponent(templateOrdrTxtFld);
        horizontal.addComponent(closebutton);
		contentLayout.addComponent(horizontal);
        contentLayout.setMargin(true);
        closebutton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public void buttonClick(ClickEvent event) {
				PageTemplateDto dto = (PageTemplateDto)UI.getCurrent().getData();
				Integer templateId = templateListingHelper.getSelectedItemId();
				
				//If it is greater than zero then a template is selected '/
				//otherwise nothing is selected or directory is selected.
				if (templateId>0){
					dto.setTemplateId(templateId);
					
					Object order = templateOrdrTxtFld.getValue();
					
					if (!order.toString().equals("")){
						dto.setOrder(Integer.parseInt((String)order));
					} else {
						Notification.show("Please enter an order value.",Notification.Type.WARNING_MESSAGE);
						return;
					}
					
					pageTemplateService = (PageTemplateService)helper.getBean("pageTemplateService");
					try {
						pageTemplateService.create(dto);
						templateTableBuilder.rebuild((Collection)pageTemplateService.findByPageId(dto.getPageId()));
						Notification.show("Template associated to page successfully");
						isModalWindowClosable = true;
					} catch (EntityAlreadyFoundException e) {
						LOGGER.warn(String.format("PageTemplate with templateId [%d],pageId [%d], sectionTypeId[%d]",dto.getTemplateId(),dto.getPageId(),dto.getSectionTypeId()));
						Notification.show("Selected template is already associated to the page)",Notification.TYPE_WARNING_MESSAGE);
						isModalWindowClosable = false;
					}
				}	
				else {
					Notification.show("No template selected",Notification.Type.WARNING_MESSAGE);
				}
			}	
		});
        
		mywindow.setModal(true);

		/* Allow opening only one window at a time. */
        openbutton.setEnabled(false);
    }

    /** Handle Close button click and close the window. */
    public void closeButtonClick(Button.ClickEvent event) {

        //Paint the page template listing for the page section

    	if (!isModalWindowClosable){
        /* Windows are managed by the application object. */
        UI.getCurrent().removeWindow(mywindow);

        /* Return to initial state. */
        openbutton.setEnabled(true);
        
        if (null!=pageTemplateService){
			PageTemplateDto dto = (PageTemplateDto)UI.getCurrent().getData();
       		Collection<PageTemplateDto> newPageTemplates = pageTemplateService.findByPageAndPageSectionType(dto.getPageId(), dto.getSectionTypeId());
       		UI.getCurrent().setData(newPageTemplates);
       	}
   	}
}

    /** In case the window is closed otherwise. */
    public void windowClose(CloseEvent e) {
        /* Return to initial state. */
        openbutton.setEnabled(true);
    }

	@Override
	public void buttonClick(ClickEvent event) {
		this.openButtonClick(event);
	}
    

}