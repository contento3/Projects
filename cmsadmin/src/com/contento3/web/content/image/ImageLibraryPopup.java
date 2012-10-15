package com.contento3.web.content.image;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountService;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.dam.image.library.dto.ImageLibraryDto;
import com.contento3.dam.image.library.service.ImageLibraryService;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.Notification;

public class ImageLibraryPopup extends CustomComponent implements Window.CloseListener{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  Reference to main window
	 */
	Window mainWindow; 
	
	/**
	 * Used to get service beans from spring context.
	 */
	SpringContextHelper helper;
	
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
	 * Image Library Service used for library related operations
	 */
	private ImageLibraryService imageLibraryService; 
	
	boolean isModalWindowClosable = true;
	
	/**
	 * Constructor
	 * @param main
	 * @param helper
	 */
	public ImageLibraryPopup(final Window main,final SpringContextHelper helper) {
		this.mainWindow = main;
		this.helper = helper;
		this.imageLibraryService = (ImageLibraryService) helper.getBean("imageLibraryService");
		
		 // The component contains a button that opens the window.
        final VerticalLayout layout = new VerticalLayout();
        openbutton = new Button("Add Group", this, "openButtonClick");
        layout.addComponent(openbutton);

        setCompositionRoot(layout);
		
	}
	
	/**
	 * Handle the clicks for the two buttons.
	 * @throws EntityAlreadyFoundException 
	 */
	public void openButtonClick(final Button.ClickEvent event) throws EntityAlreadyFoundException {
		
		final Button librarybutton = new Button();
		
		popupWindow = new Window();
		popupWindow.setPositionX(200);
		popupWindow.setPositionY(100);
		
		popupWindow.setHeight(27,Sizeable.UNITS_PERCENTAGE);
    	popupWindow.setWidth(26,Sizeable.UNITS_PERCENTAGE);
       
    	/* Add the window inside the main window. */
        mainWindow.addWindow(popupWindow);
        
        /* Listen for close events for the window. */
        popupWindow.addListener(this);
        popupWindow.setModal(true);
        
        final VerticalLayout popupMainLayout = new VerticalLayout();
        popupMainLayout.setSpacing(true);
        
        //name field
        final HorizontalLayout nameDataLayout = new HorizontalLayout();
        nameDataLayout.setSizeFull();
        final Label nameLabel = new Label("Name");
        final TextField nameTextField  = new TextField();
        nameDataLayout.setSpacing(true);
        nameDataLayout.addComponent(nameLabel);
        nameDataLayout.addComponent(nameTextField);
        nameDataLayout.setComponentAlignment(nameLabel, Alignment.BOTTOM_LEFT);
        nameDataLayout.setComponentAlignment(nameTextField, Alignment.BOTTOM_RIGHT);
        popupMainLayout.addComponent(nameDataLayout);
        
        //description field
        final HorizontalLayout descriptionLayout = new HorizontalLayout();
        descriptionLayout.setSizeFull();
        final Label descriptionLabel = new Label("Description");
        final TextField descriptionTextField  = new TextField();
        descriptionLayout.setSpacing(true);
        descriptionLayout.addComponent(descriptionLabel);
        descriptionLayout.addComponent(descriptionTextField);
        descriptionLayout.setComponentAlignment(descriptionLabel, Alignment.BOTTOM_LEFT);
        descriptionLayout.setComponentAlignment(descriptionTextField, Alignment.BOTTOM_RIGHT);
        popupMainLayout.addComponent(descriptionLayout);
        
		
		if (event.getButton().getCaption().equals("Add Library")){
		    
			nameTextField.setValue("");
			descriptionTextField.setValue("");
			
			librarybutton.setCaption("Add");
			librarybutton.addListener(new ClickListener() {
				public void buttonClick(ClickEvent event) {
					
					ImageLibraryDto library = new ImageLibraryDto();
					library.setName(nameTextField.getValue().toString());
					library.setDescription(descriptionTextField.getValue().toString());
					
					 //Get accountId from the session
		            final Integer accountId = (Integer)SessionHelper.loadAttribute(mainWindow, "accountId");
		            final AccountService accountService = (AccountService)helper.getBean("accountService");
		            final AccountDto accountDto = accountService.findAccountById(accountId);
		            library.setAccountDto(accountDto);
		            
		            if(!( nameTextField.getValue().toString().isEmpty() 
		            		&& descriptionTextField.getValue().toString().isEmpty() ))
		            {
						try {
							imageLibraryService.create(library);
							mainWindow.showNotification(library.getName() +" added succesfully");
						} catch (EntityAlreadyFoundException e) {
							
							e.printStackTrace();
						}

			    		mainWindow.removeWindow(popupWindow);
				        openbutton.setEnabled(true);
		            }else{
		            	mainWindow.showNotification("Add Failed","One or more field is empty",Notification.TYPE_WARNING_MESSAGE);
		            }
					
				}
			});
			
		}//end if
	
		final HorizontalLayout addButtonLayout = new HorizontalLayout();
		popupMainLayout.addComponent(addButtonLayout);
		addButtonLayout.addComponent(librarybutton);
		addButtonLayout.setComponentAlignment(librarybutton,
				Alignment.BOTTOM_RIGHT);
		addButtonLayout.setWidth(100, Sizeable.UNITS_PERCENTAGE);
		popupWindow.setCaption("Add Library");
		popupWindow.addComponent(popupMainLayout);
		popupWindow.setResizable(false);
		/* Allow opening only one window at a time. */
		openbutton.setEnabled(false);
        
	}

	/**
	 * Handle Close button click and close the window.
	 */
	public void closeButtonClick(final Button.ClickEvent event) {
		if (!isModalWindowClosable) {
			/* Windows are managed by the application object. */
			mainWindow.removeWindow(popupWindow);

			/* Return to initial state. */
			openbutton.setEnabled(true);
		}//end if
	}

	/**
	 * Handle window close event
	 */
	@Override
	public void windowClose(final CloseEvent e) {
		/* Return to initial state. */
		openbutton.setEnabled(true);
	}
}
