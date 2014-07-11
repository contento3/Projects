package com.contento3.web.template;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.contento3.cms.page.template.dto.TemplateDirectoryDto;
import com.contento3.cms.page.template.service.TemplateDirectoryService;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.template.helper.TemplateListingHelper;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class TemplateDirectoryPopup extends Window implements Window.CloseListener {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = Logger.getLogger(TemplateDirectoryPopup.class);
	
	Button closebutton; // A button in the window
	
	
	boolean isModalWindowClosable = true;

	SpringContextHelper helper;
	
	public TemplateDirectoryPopup(final SpringContextHelper helper,final String itemToMoveId) {
		this.helper = helper;
		buildUI(itemToMoveId);
	}
	
	public void buildUI(final String itemToMoveId) {
		/* Create a new window. */
		this.setCaption("Select template directory");
		this.setPositionX(200);
		this.setPositionY(100);
		
		this.setHeight(75,Unit.PERCENTAGE);
		this.setWidth(50,Unit.PERCENTAGE);
		
		/* Listen for close events for the window. */
		this.addCloseListener(this);
		
		// Create the Accordion.
		Accordion accordion = new Accordion();
		
		// Have it take all space available in the layout.
		accordion.setSizeFull();
		
		final VerticalLayout globalTemplateListLayout = new VerticalLayout();
		accordion.addTab(globalTemplateListLayout, "Global Templates", null);
		
		// Some components to put in the Accordion.
		VerticalLayout templateListLayout = new VerticalLayout();
		
		// Add the components as tabs in the Accordion.
		Tab tab2 = accordion.addTab(templateListLayout, "Templates", null);
		
		TemplateDirectoryService templateDirectoryService = (TemplateDirectoryService)helper.getBean("templateDirectoryService");
		
		final Collection <TemplateDirectoryDto> templateDirectoryList =  templateDirectoryService.findRootDirectories(false,(Integer)SessionHelper.loadAttribute("accountId"));
		final Collection <TemplateDirectoryDto> globalTemplateDirectoryList =  templateDirectoryService.findRootDirectories(true,1);
		final TemplateListingHelper templateListingHelper = new TemplateListingHelper(helper);
		
		//Add the tree to the vertical layout for template list.
		templateListLayout.addComponent(templateListingHelper.populateTemplateDirectoryList(templateDirectoryList,templateDirectoryService,false));

		//Add the tree to the vertical layout for global template list.
		globalTemplateListLayout.addComponent(templateListingHelper.populateTemplateDirectoryList(globalTemplateDirectoryList,templateDirectoryService,false));
		
		/* Add components in the window. */
		final VerticalLayout contentLayout = new VerticalLayout();
		contentLayout.addComponent(accordion);
		this.setContent(contentLayout);
		closebutton = new Button("Move template");
		
		HorizontalLayout horizontal = new HorizontalLayout();
		horizontal.setSpacing(true);
		horizontal.addComponent(closebutton);
		contentLayout.addComponent(horizontal);
		contentLayout.setMargin(true);
		
		closebutton.addClickListener(new ClickListener() {
		
			private static final long serialVersionUID = 1L;
			@SuppressWarnings({ "unchecked", "rawtypes" })
		
			public void buttonClick(ClickEvent event) {
		
				final Integer directoryParentId = templateListingHelper.getSelectedItemId();
		
				if (directoryParentId==null || directoryParentId<=0){
					Notification.show("No parent directory selected for move",Notification.Type.TRAY_NOTIFICATION);
				}
				else {
				
				final TemplateDirectoryService templateDirectoryService = (TemplateDirectoryService)helper.getBean("templateDirectoryService");
				
				try {
					templateDirectoryService.move(Integer.parseInt(itemToMoveId),directoryParentId);
					Notification.show("Directory with id "+itemToMoveId+" is moved as "+ " child of directory with "+directoryParentId,Notification.Type.TRAY_NOTIFICATION);
					isModalWindowClosable = true;
				} catch (final Exception e) {
					isModalWindowClosable = false;
				}
			}	
		}
	});
		this.setResizable(false);
		this.setModal(true);
	}
	
	/** Handle Close button click and close the window. */
	public void closeButtonClick(Button.ClickEvent event) {
	
		if (!isModalWindowClosable){
		
		}
	}
	
	/** In case the window is closed otherwise. */
	public void windowClose(CloseEvent e) {
		/* Return to initial state. */
	}
	
	
}