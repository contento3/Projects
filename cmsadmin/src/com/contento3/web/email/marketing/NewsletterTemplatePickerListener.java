package com.contento3.web.email.marketing;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.contento3.cms.page.template.dto.TemplateDirectoryDto;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.service.PageTemplateService;
import com.contento3.cms.page.template.service.TemplateDirectoryService;
import com.contento3.cms.page.template.service.TemplateService;
import com.contento3.web.common.helper.AbstractTableBuilder;
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
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;


public class NewsletterTemplatePickerListener extends Window implements Window.CloseListener,Button.ClickListener {

	private static final long serialVersionUID = 1L;
		
	private static final Logger LOGGER = Logger.getLogger(NewsletterTemplatePickerListener.class);

	Window mywindow;    // The window to be opened

	Integer templateId;
	
	Button closebutton; // A button in the window

	PageTemplateService pageTemplateService;
	
	boolean isModalWindowClosable = true;
	
	SpringContextHelper helper;
	
	final AbstractTableBuilder templateTableBuilder;
	
	final TextField templateTextField;
	
	public NewsletterTemplatePickerListener(final String label,final SpringContextHelper helper,final AbstractTableBuilder templateTableBuilder, final TextField textField) {
	    this.helper = helper;	
		this.templateTableBuilder = templateTableBuilder;
		this.templateTextField = textField;
	}
	
	/** Handle the clicks for the two buttons. */
	public void buildUI() {	
		/* Create a new window. */
		mywindow = new Window("Select template for newsletter");
	    mywindow.setPositionX(200);
	    mywindow.setPositionY(100);
	    mywindow.setHeight(75,Unit.PERCENTAGE);
	    mywindow.setWidth(50,Unit.PERCENTAGE);
	    
	    /* Add the window inside the main window. */
	    UI.getCurrent().addWindow(mywindow);
	        
	    /* Listen for close events for the window. */
	    mywindow.addCloseListener(this);

	    
	    // Create the Accordion.
	    final Accordion accordion = new Accordion();

	    // Have it take all space available in the layout.
		accordion.setSizeFull();
		
		final VerticalLayout globalTemplateListLayout = new VerticalLayout();
		accordion.addTab(globalTemplateListLayout, "Global Templates", null);
		
		// Some components to put in the Accordion.
		final VerticalLayout templateListLayout = new VerticalLayout();
		
		// Add the components as tabs in the Accordion.
		final Tab tab2 = accordion.addTab(templateListLayout, "Templates", null);
		
		final TemplateDirectoryService templateDirectoryService = (TemplateDirectoryService)helper.getBean("templateDirectoryService");
		final Collection <TemplateDirectoryDto> templateDirectoryList =  templateDirectoryService.findRootDirectories(false,(Integer)SessionHelper.loadAttribute("accountId"));
		final Collection <TemplateDirectoryDto> globalTemplateDirectoryList =  templateDirectoryService.findRootDirectories(true,1);
		final TemplateListingHelper templateListingHelper = new TemplateListingHelper(helper);
		
		//Add the tree to the vertical layout for template list.
		templateListLayout.addComponent(templateListingHelper.populateTemplateDirectoryList(templateDirectoryList,templateDirectoryService,true));

		//Add the tree to the vertical layout for global template list.
		globalTemplateListLayout.addComponent(templateListingHelper.populateTemplateDirectoryList(globalTemplateDirectoryList,templateDirectoryService,true));
		
		/* Add components in the window. */
		final VerticalLayout contentLayout = new VerticalLayout();
		contentLayout.addComponent(accordion);
		closebutton = new Button("Select template");
		
		HorizontalLayout horizontal = new HorizontalLayout();
		horizontal.setSpacing(true);
		horizontal.addComponent(closebutton);
		contentLayout.addComponent(horizontal);
		contentLayout.setMargin(true);
		
		mywindow.setContent(contentLayout);
		
		closebutton.addClickListener(new ClickListener() {
		
	    	private static final long serialVersionUID = 1L;
			
	    	@SuppressWarnings({ "unchecked", "rawtypes" })
			
	    	public void buttonClick(final ClickEvent event) {
	    		    templateId = templateListingHelper.getSelectedItemId();
		    		//If it is greater than zero then a template is selected '/
		    		//otherwise nothing is selected or directory is selected.
		    		if (templateId<=0 && templateListingHelper.isDirectorySelected()){
		    			Notification.show("No template selected",Notification.Type.TRAY_NOTIFICATION);
	    			}	
				}	
			});
	        
			mywindow.setModal(true);

	    }

	    /** Handle Close button click and close the window. */
	    public void closeButtonClick(Button.ClickEvent event) {
	        //Paint the page template listing for the page section

    		/* Windows are managed by the application object. */
    		UI.getCurrent().removeWindow(mywindow);

        	final TemplateService templateService = (TemplateService) helper.getBean("templateService");
        	final TemplateDto templateDto = templateService.findTemplateById(templateId);
        	UI.getCurrent().setData(templateDto);
	}

	    /** In case the window is closed otherwise. */
	    public void windowClose(CloseEvent e) {
	        //Paint the page template listing for the page section

    		/* Windows are managed by the application object. */
    		UI.getCurrent().removeWindow(mywindow);

        	final TemplateService templateService = (TemplateService) helper.getBean("templateService");
        	final TemplateDto templateDto = templateService.findTemplateById(templateId);
        	UI.getCurrent().setData(templateDto);
        	templateTextField.setReadOnly(false);
        	templateTextField.setValue(templateDto.getTemplatePath()+"/"+templateDto.getTemplateName());
        	templateTextField.setReadOnly(true);
	    }


		@Override
		public void buttonClick(ClickEvent event) {
			buildUI();
		}

	}
