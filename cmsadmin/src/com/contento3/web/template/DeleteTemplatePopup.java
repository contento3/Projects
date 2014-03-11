package com.contento3.web.template;

import org.apache.commons.lang.StringUtils;

import com.contento3.cms.page.template.dto.TemplateDirectoryDto;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.service.TemplateDirectoryService;
import com.contento3.cms.page.template.service.TemplateService;
import com.contento3.cms.page.templatecategory.service.TemplateCategoryService;
import com.contento3.cms.page.templatecategoryDto.TemplateCategoryDto;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.dam.content.storage.exception.InvalidStorageException;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.CloseEvent;

public class DeleteTemplatePopup extends Window implements Window.CloseListener,Button.ClickListener{
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
		
	
	final TemplateDto templateDto;
	final TemplateService templateService;
	boolean isModalWindowClosable = true;

	final Label label= new Label();
	//final TemplateDirectoryDto dirToUpdate;
	
	/**
	 * Constructor
	 * @param main
	 * @param helper
	 * @param table
	 */
		public DeleteTemplatePopup(final SpringContextHelper helper, TemplateDto dtoToDelete) {
			this.helper = helper;
			this.templateService = (TemplateService) this.helper.getBean("templateService");
			this.templateDto = dtoToDelete;
			buildWindow();
		}
		
		  /** 
		   * Handle the clicks for the two buttons.
		   */
	    public void buildWindow() {
	        /* Create a new window. */
	        final Button button = new Button("Delete",this);
	        final Button cancel = new Button("Cancel",this);
			this.setPositionX(200);
			this.setPositionY(100);

			this.setHeight(30,Unit.PERCENTAGE);
			this.setWidth(23,Unit.PERCENTAGE);
	        
	        /* Listen for close events for the window. */
			this.addCloseListener(this);
			
			this.setModal(true);
	        this.setCaption("Delete Template");
	        
	       
	        final HorizontalLayout horiLayout = new HorizontalLayout();
	        final VerticalLayout vertiLayout = new VerticalLayout();
	        label.setValue("Deleting any template may " +
	        		"cause some page not working properly due to nested calling between templates, Are sure");
	       	        
	        vertiLayout.addComponent(label);
	        
//	        vertiLayout.addComponent(textField2);
	        horiLayout.addComponent(vertiLayout);
	        horiLayout.addComponent(button);
	        horiLayout.addComponent(cancel);
	        this.setContent(horiLayout);
	        
	        button.addClickListener(new com.vaadin.ui.Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					    Notification.show("Category name can not be empty");
				}
			});
	        
	        cancel.addClickListener(new com.vaadin.ui.Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					    Notification.show("cancel Category name can not be empty");
				}
			});
	        
	        
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
			
		}
}
