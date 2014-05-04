package com.contento3.web.template;

import org.apache.commons.lang.StringUtils;

import com.contento3.cms.page.templatecategory.service.TemplateCategoryService;
import com.contento3.cms.page.templatecategoryDto.TemplateCategoryDto;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.dam.content.storage.exception.InvalidStorageException;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class NewTemplateCategoryPopup extends Window implements Window.CloseListener,Button.ClickListener{
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
		
	
	final TemplateCategoryService templateCategoryService;
	boolean isModalWindowClosable = true;

	final TextField textField = new TextField();
	final TextArea  textField2 = new TextArea();
	
	/**
	 * Constructor
	 * @param main
	 * @param helper
	 * @param table
	 */
		public NewTemplateCategoryPopup(final SpringContextHelper helper) {
			this.helper = helper;
			this.templateCategoryService = (TemplateCategoryService) this.helper.getBean("templateCategoryService");
			buildWindow();
		}
		
		  /** 
		   * Handle the clicks for the two buttons.
		   */
	    public void buildWindow() {
	        /* Create a new window. */
	        final Button updateButton = new Button("Add",this);
	    	
			this.setPositionX(200);
			this.setPositionY(100);

			this.setHeight(30,Unit.PERCENTAGE);
			this.setWidth(23,Unit.PERCENTAGE);
	        
	        /* Listen for close events for the window. */
			this.addCloseListener(this);
			
			this.setModal(true);
	        this.setCaption("Create new category");
	        
	       
	        final HorizontalLayout horiLayout = new HorizontalLayout();
	        final VerticalLayout vertiLayout = new VerticalLayout();
	        vertiLayout.setSpacing(true);
	        vertiLayout.setMargin(true);
	        textField.setCaption("Category Name:");
	        textField.setInputPrompt("Enter category name");
	       
	        textField2.setCaption("Category Description:");
	        textField2.setInputPrompt("Enter category description");
	        
	        
	        vertiLayout.addComponent(textField);
	        vertiLayout.addComponent(textField2);
	        horiLayout.addComponent(vertiLayout);
	        horiLayout.addComponent(updateButton);
	        this.setContent(horiLayout);
	        
	        
	        updateButton.addClickListener(new com.vaadin.ui.Button.ClickListener() {
																
				@Override
				public void buttonClick(ClickEvent event) {
					String categoryName = textField.getValue().toString();
					String categoryDesc  = textField2.getValue().toString();	
					if (StringUtils.isEmpty(categoryName)) {
                        Notification.show("Category name can not be empty");
					
					}
					else
					{
						TemplateCategoryDto categoryDto  = new TemplateCategoryDto();
						categoryDto.setTemplateCategoryName(categoryName);
						categoryDto.setTemplateCategoryDescription(categoryDesc);
						try {
							templateCategoryService.create(categoryDto);
							 Notification.show("fields succesfully inserted");
						} catch (EntityAlreadyFoundException e) {
							
							e.printStackTrace();
						} catch (EntityNotCreatedException e) {
							
							e.printStackTrace();
						} catch (InvalidStorageException e) {
							
							e.printStackTrace();
						}
					}
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
