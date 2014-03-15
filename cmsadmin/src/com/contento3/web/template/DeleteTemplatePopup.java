package com.contento3.web.template;

import java.util.Collection;

import org.springframework.util.CollectionUtils;

import com.contento3.cms.page.template.dto.PageTemplateDto;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.service.PageTemplateService;
import com.contento3.cms.page.template.service.TemplateService;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * 
 * @author Yawar
 *
 */
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
	final SpringContextHelper helper;
		
	/**
	 * 
	 */
	final TemplateDto templateDto;
	
	/**
	 * 
	 */
	final PageTemplateService pageTemplateService;
	
	/**
	 * 
	 */
	final TemplateService templateService;
	
	/**
	 * 
	 */
	boolean isModalWindowClosable = true;

	/**
	 * Constructor
	 * @param main
	 * @param helper
	 * @param table
	 */
	public DeleteTemplatePopup(final SpringContextHelper helper, final TemplateDto dtoToDelete) {
		this.helper = helper;
		this.pageTemplateService = (PageTemplateService) this.helper.getBean("pageTemplateService");
		this.templateService = (TemplateService) this.helper.getBean("templateService");
		this.templateDto = dtoToDelete;
		buildWindow();
	}
		
		  /** 
		   * Handle the clicks for the two buttons.
		   */
	    @SuppressWarnings("deprecation")
		public void buildWindow() {

	        /* Create a new window. */
	        final Button button = new Button("Delete",this);
	        
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
	        
	        final Label deleteNotificationLbl = new Label("Deleting any template may " + 
	        		"cause some page not <br /> working properly due " +
	        		"to nested calling between <br /> templates.", ContentMode.HTML);
	        
	       	        
	        horiLayout.addComponent(vertiLayout);
	        vertiLayout.addComponent(deleteNotificationLbl);
	        vertiLayout.addComponent(button);
	        this.setContent(horiLayout);
	        
	        button.addClickListener(new com.vaadin.ui.Button.ClickListener() {
			
				private static final long serialVersionUID = -3028553763241375774L;

				@Override
				public void buttonClick(ClickEvent event) {
					Collection<PageTemplateDto> pageTemplateResult = pageTemplateService.findByTemplateId(templateDto.getTemplateId());
					if(!CollectionUtils.isEmpty(pageTemplateResult)){
						Notification.show("Error in deleting Template", "This template is assigned to one or more pages, please release it before deleting", Notification.Type.TRAY_NOTIFICATION);
					}
					else{	
						try {
							templateService.delete(templateDto);
							Notification.show("Template Deleted Successfully", "", Notification.Type.TRAY_NOTIFICATION);
						} catch (EntityCannotBeDeletedException e) {
							Notification.show("Error in deleting Template", "some error occured during deleting template, try some later time", Notification.Type.TRAY_NOTIFICATION);
						}
					}
					close();
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
