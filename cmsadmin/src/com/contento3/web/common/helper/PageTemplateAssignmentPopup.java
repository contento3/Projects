package com.contento3.web.common.helper;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.contento3.cms.page.template.dto.PageTemplateDto;
import com.contento3.cms.page.template.dto.TemplateDirectoryDto;
import com.contento3.cms.page.template.service.PageTemplateService;
import com.contento3.cms.page.template.service.TemplateDirectoryService;
import com.contento3.cms.page.template.service.TemplateService;
import com.contento3.common.exception.EnitiyAlreadyFoundException;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.template.helper.TemplateListingHelper;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.Notification;

/** Component contains a button that allows opening a window. */
public class PageTemplateAssignmentPopup extends CustomComponent
                          implements Window.CloseListener {

	private static final Logger LOGGER = Logger.getLogger(PageTemplateAssignmentPopup.class);

    Window mainwindow;  // Reference to main window
    Window mywindow;    // The window to be opened
    Button openbutton;  // Button for opening the window
    Button closebutton; // A button in the window


    PageTemplateService pageTemplateService;
    boolean isModalWindowClosable = true;
    SpringContextHelper helper;
    public PageTemplateAssignmentPopup(String label, Window main,SpringContextHelper helper) {
        mainwindow = main;
        this.helper = helper;
        // The component contains a button that opens the window.
        final VerticalLayout layout = new VerticalLayout();
        
        openbutton = new Button("Add template", this,
                                "openButtonClick");
        layout.addComponent(openbutton);
        
        setCompositionRoot(layout);
    }

    /** Handle the clicks for the two buttons. */
    public void openButtonClick(Button.ClickEvent event) {
        /* Create a new window. */
        mywindow = new Window("My Dialog");
        mywindow.setPositionX(200);
        mywindow.setPositionY(100);

        mywindow.setHeight(50,Sizeable.UNITS_PERCENTAGE);
        mywindow.setWidth(50,Sizeable.UNITS_PERCENTAGE);
        /* Add the window inside the main window. */
        mainwindow.addWindow(mywindow);
        
        /* Listen for close events for the window. */
        mywindow.addListener(this);

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

		Collection <TemplateDirectoryDto> templateDirectoryList =  templateDirectoryService.findRootDirectories(false);

		final TemplateListingHelper templateListingHelper = new TemplateListingHelper();
		
		//Add the tree to the vertical layout for template list.
		templateListLayout.addComponent(templateListingHelper.populateTemplateList(templateDirectoryList,templateService,templateDirectoryService));

		
        /* Add components in the window. */
        mywindow.addComponent(accordion);
        closebutton = new Button("Add template", this, "closeButtonClick");
        mywindow.addComponent(closebutton);
        closebutton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				PageTemplateDto dto = (PageTemplateDto)mainwindow.getData();
				Integer templateId = templateListingHelper.getSelectedItemId();
				
				//If it is greater than zero then a template is selected 
				//otherwise nothing is selected or directory is selected.
				if (templateId>0){
					dto.setTemplateId(templateId);
					pageTemplateService = (PageTemplateService)helper.getBean("pageTemplateService");
					try {
						pageTemplateService.create(dto);
						mainwindow.showNotification("Template associated to page successfully");
						isModalWindowClosable = true;
					} catch (EnitiyAlreadyFoundException e) {
						LOGGER.warn(String.format("PageTemplate with templateId [%d],pageId [%d], sectionTypeId[%d]",dto.getTemplateId(),dto.getPageId(),dto.getSectionTypeId()));
						mainwindow.showNotification("Selected template is already associated to the page)",Notification.TYPE_WARNING_MESSAGE);
						isModalWindowClosable = false;
					}
				}	
				else {
					mainwindow.showNotification("No template selected",Notification.TYPE_WARNING_MESSAGE);
				}
			}	
		});
        
		mywindow.setModal(true);

		/* Allow opening only one window at a time. */
        openbutton.setEnabled(false);


    }

    /** Handle Close button click and close the window. */
    public void closeButtonClick(Button.ClickEvent event) {
    	
    	if (!isModalWindowClosable){
        /* Windows are managed by the application object. */
        mainwindow.removeWindow(mywindow);

        /* Return to initial state. */
        openbutton.setEnabled(true);


        
        if (null!=pageTemplateService){
			PageTemplateDto dto = (PageTemplateDto)mainwindow.getData();
       		Collection<PageTemplateDto> newPageTemplates = pageTemplateService.findByPageAndPageSectionType(dto.getPageId(), dto.getSectionTypeId());
       		//mainwindow.setData(newPageTemplates);
       		
       	}
   	}
}

    /** In case the window is closed otherwise. */
    public void windowClose(CloseEvent e) {
        /* Return to initial state. */
        openbutton.setEnabled(true);


    }
    

}