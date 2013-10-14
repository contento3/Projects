package com.contento3.web.template;

import com.contento3.cms.page.template.dto.TemplateDirectoryDto;
import com.contento3.cms.page.template.service.TemplateDirectoryService;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Item;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class RenameDirectoryPopup extends Window implements Window.CloseListener,Button.ClickListener{

	private static final long serialVersionUID = 1L;

	/**
	 * Button for opening the window
	 */
	Button openbutton; 
		
	/**
	 *  A button in the window
	 */
	Button closebutton; 
		
	/**
	 * Used to get service beans from spring context.
	 */
	SpringContextHelper helper;
		
	final TemplateDirectoryService templateDirectoryService;
	
	boolean isModalWindowClosable = true;

	final TextField textField = new TextField();
	
	final TemplateDirectoryDto dirToUpdate;
	
	/**
	 * Constructor
	 * @param main
	 * @param helper
	 * @param table
	 */
		public RenameDirectoryPopup(final SpringContextHelper helper,final TemplateDirectoryDto directoryToUpdate) {
			this.helper = helper;
			this.templateDirectoryService = (TemplateDirectoryService) this.helper.getBean("templateDirectoryService");
			dirToUpdate = directoryToUpdate;
			buildWindow();
		}
		
		  /** 
		   * Handle the clicks for the two buttons.
		   */
	    public void buildWindow() {
	        /* Create a new window. */
	        final Button updateButton = new Button("Save",this);
	    	
			this.setPositionX(200);
			this.setPositionY(100);

			this.setHeight(10,Unit.PERCENTAGE);
			this.setWidth(23,Unit.PERCENTAGE);
	        
	        /* Listen for close events for the window. */
			this.addCloseListener(this);
			this.setModal(true);
	        this.setCaption("Rename directory");
	        
	        final VerticalLayout popupMainLayout = new VerticalLayout();
	        textField.setCaption("Directory Name:");
	        textField.setValue(dirToUpdate.getDirectoryName());
	        popupMainLayout.addComponent(textField);
	        popupMainLayout.addComponent(updateButton);
	        this.setContent(popupMainLayout);
	    }

		 
		/**
		 *  Handle Close button click and close the window.
		 */
		public void closeButtonClick(Button.ClickEvent event) {
		  	if (!isModalWindowClosable){
		       /* Windows are managed by the application object. */
		  		UI.getCurrent().removeWindow(this);
		        
		        /* Return to initial state. */
		  	//	openbutton.setEnabled(true);
		   	}
	    }

	    /**
	     * Handle window close event
	     */
		@Override
		public void windowClose(CloseEvent e) {
			  /* Return to initial state. */
	        //openbutton.setEnabled(true);
		}

		@Override
		public void buttonClick(ClickEvent event) {
			dirToUpdate.setDirectoryName(textField.getValue());
			templateDirectoryService.update(dirToUpdate);
		}
}

