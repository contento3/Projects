package com.contento3.web.site.listener;

import java.util.ArrayList;
import java.util.List;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountService;
import com.contento3.cms.site.structure.domain.dto.SiteDomainDto;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.security.group.service.GroupService;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

	public class SiteCreatePopup extends CustomComponent implements Window.CloseListener,Button.ClickListener {
		
		/**
		 * Service layer that use to provide functionality related to site.
		 */
		private SiteService siteService;

		/**
		 *  Reference to main window
		 */
		Window mainwindow; 
		
		/**
		 * The window to be opened
		 */
		Window popupWindow; 
		
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
		
		/**
		 * Group service used for group related operations
		 */
		GroupService groupService;
		
		/**
		 * AccountService used for account manipulation
		 */
		AccountService accountService;
		

		boolean isModalWindowClosable = true;
		
		/**
		 * Constructor
		 * @param main
		 * @param helper
		 * @param table
		 */
		public SiteCreatePopup(final SpringContextHelper helper) {
			this.helper = helper;
			this.siteService = (SiteService) helper.getBean("siteService");
			this.accountService = (AccountService) helper.getBean("accountService");

			// The component contains a button that opens the window.
	        final VerticalLayout layout = new VerticalLayout();
	        openbutton = new Button("Add Group", this);
	        openbutton.addClickListener(this);
	        layout.addComponent(openbutton);

	        setCompositionRoot(layout);
		}
		
		  /** 
		   * Handle the clicks for the two buttons.
		   */
	    public void openButtonClick(Button.ClickEvent event) {
	        /* Create a new window. */
	        final Button siteButton = new Button();
			popupWindow = new Window();
	    	
			popupWindow.setPositionX(200);
	    	popupWindow.setPositionY(100);

	    	popupWindow.setHeight(41,Unit.PERCENTAGE);
	    	popupWindow.setWidth(20,Unit.PERCENTAGE);
	       
	    	/* Add the window inside the main window. */
	        UI.getCurrent().addWindow(popupWindow);
	        
	        /* Listen for close events for the window. */
	        popupWindow.addCloseListener(this);
	        popupWindow.setModal(true);
	        
	        UI.getCurrent().addWindow(popupWindow);
	        final VerticalLayout popupMainLayout = new VerticalLayout();
	        final Label label = new Label("Site Name");
	        label.setWidth(100,Unit.PERCENTAGE);
	        final HorizontalLayout inputDataLayout = new HorizontalLayout();
	        final TextField textField = new TextField("Site Name");
	        textField.setInputPrompt("Enter site name");
	        textField.setWidth(100,Unit.PERCENTAGE);
	        textField.setColumns(20);
	        
	        inputDataLayout.setSizeFull();
	        inputDataLayout.setSpacing(true);
	        inputDataLayout.addComponent(textField);
	        inputDataLayout.setComponentAlignment(textField, Alignment.TOP_LEFT);
	        
	        popupMainLayout.setSpacing(true);
	        popupMainLayout.addComponent(inputDataLayout);
	       
	        /* adding description area */
	        final HorizontalLayout addDescriptionLayout = new HorizontalLayout();
	       
	        final TextField siteDomain = new TextField("Site Domain");
	        siteDomain.setInputPrompt("Enter site domain");
	        siteDomain.setWidth(100,Unit.PERCENTAGE);
	     	
	     	addDescriptionLayout.setSizeFull();
	     	addDescriptionLayout.setSpacing(true);
	     	addDescriptionLayout.addComponent(siteDomain);
	    	addDescriptionLayout.setComponentAlignment(siteDomain, Alignment.TOP_LEFT);
	     	popupMainLayout.addComponent(addDescriptionLayout);
	     
	        final HorizontalLayout addButtonLayout = new HorizontalLayout();
	        popupMainLayout.addComponent(addButtonLayout);

	        Button siteAddButton = new Button();
	        addButtonLayout.addComponent(siteAddButton);
	        addButtonLayout.setComponentAlignment(siteAddButton, Alignment.BOTTOM_RIGHT);
	        addButtonLayout.setWidth(100, Unit.PERCENTAGE);
	        
	        popupWindow.setContent(popupMainLayout);
	        popupWindow.setResizable(false);
	        /* Allow opening only one window at a time. */
	        openbutton.setEnabled(false);

	        siteAddButton.setCaption("Add");
		    popupWindow.setCaption("Add new group");
		    siteAddButton.addClickListener(new ClickListener() {
					private static final long serialVersionUID = 1L;
					public void buttonClick(ClickEvent event) {
						handleNewSite(textField,siteDomain);
					}	
				});
	    	}

	    /**
	     * Handles adding new Group
	     * @param textField
	     */
		private void handleNewSite(final TextField siteName,final TextField siteDomain){
			SiteDto siteDto = new SiteDto();
			siteDto.setSiteName((String)siteName.getValue());
			final AccountDto account = accountService.findAccountById((Integer)SessionHelper.loadAttribute("accountId"));
			siteDto.setAccountDto(account);
			final SiteDomainDto siteDomainDto = new SiteDomainDto();
			siteDomainDto.setDomainName((String) siteDomain.getValue());
			final List <SiteDomainDto> siteDomains = new ArrayList<SiteDomainDto>();
			siteDomains.add(siteDomainDto);
			siteDto.setSiteDomainDto(siteDomains);
			siteService.create(siteDto);
			Notification.show(siteDto.getSiteName()+" site created succesfully");
	    }

	    
		  /**
		   *  Handle Close button click and close the window.
		   */
		    public void closeButtonClick(Button.ClickEvent event) {
		    	if (!isModalWindowClosable){
		        /* Windows are managed by the application object. */
		        UI.getCurrent().removeWindow(popupWindow);
		        
		        /* Return to initial state. */
		        openbutton.setEnabled(true);
		    	}
		    }

		    /**
		     * Handle window close event
		     */
			@Override
			public void windowClose(CloseEvent e) {
				  /* Return to initial state. */
		        openbutton.setEnabled(true);
			}

			@Override
			public void buttonClick(ClickEvent event) {
				this.openButtonClick(event);
			}

	}

