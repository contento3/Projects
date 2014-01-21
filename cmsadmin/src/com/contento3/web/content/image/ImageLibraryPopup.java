package com.contento3.web.content.image;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountService;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.dam.content.storage.exception.InvalidStorageException;
import com.contento3.dam.image.library.dto.ImageLibraryDto;
import com.contento3.dam.image.library.service.ImageLibraryService;
import com.contento3.dam.storagetype.service.StorageTypeService;
import com.contento3.web.common.helper.ComboDataLoader;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

public class ImageLibraryPopup extends CustomComponent implements Window.CloseListener,Button.ClickListener{

	private static final Logger LOGGER = Logger.getLogger(ImageLibraryPopup.class);

	private static final long serialVersionUID = 1L;


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
	
	private StorageTypeService storageTypeService;
	
	boolean isModalWindowClosable = true;
	
	/**
	 * Constructor
	 * @param main
	 * @param helper
	 */
	public ImageLibraryPopup(final SpringContextHelper helper) {
		this.helper = helper;
		this.imageLibraryService = (ImageLibraryService) helper.getBean("imageLibraryService");
		this.storageTypeService = (StorageTypeService) helper.getBean("storageTypeService");
		
		 // The component contains a button that opens the window.
        final VerticalLayout layout = new VerticalLayout();
        openbutton = new Button("Add Group");
        openbutton.addClickListener(this);
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

		popupWindow.setHeight(60,Unit.PERCENTAGE);
    	popupWindow.setWidth(30,Unit.PERCENTAGE);
       
    	/* Add the window inside the main window. */
        UI.getCurrent().addWindow(popupWindow);
        
        /* Listen for close events for the window. */
        popupWindow.addCloseListener(this);
        popupWindow.setModal(true);
        
        final VerticalLayout popupMainLayout = new VerticalLayout();
        popupMainLayout.setSpacing(true);
        popupMainLayout.setMargin(true);
        
        //name field
        final HorizontalLayout nameDataLayout = new HorizontalLayout();
        nameDataLayout.setSpacing(true);
        final TextField nameTextField  = new TextField();
        nameTextField.setCaption("Name");
        nameDataLayout.setSpacing(true);
        nameDataLayout.addComponent(nameTextField);
        nameDataLayout.setComponentAlignment(nameTextField, Alignment.BOTTOM_RIGHT);
        popupMainLayout.addComponent(nameDataLayout);
        
        //description field
        final HorizontalLayout descriptionLayout = new HorizontalLayout();
        final TextArea descriptionTextField  = new TextArea();
        descriptionTextField.setCaption("Descrption");
        descriptionTextField.setRows(5);        
        descriptionTextField.setColumns(20);
        descriptionLayout.setSpacing(true);
        descriptionLayout.addComponent(descriptionTextField);
        descriptionLayout.setComponentAlignment(descriptionTextField, Alignment.BOTTOM_RIGHT);
        popupMainLayout.addComponent(descriptionLayout);
        
        final HorizontalLayout storageTypeLayout = new HorizontalLayout();
        storageTypeLayout.setSizeFull();
        storageTypeLayout.setSpacing(true);
        
		final ComboDataLoader comboDataLoader = new ComboDataLoader();

		final ComboBox storageTypeCombo = new ComboBox("Select Storage Type",comboDataLoader.loadDataInContainer((Collection)storageTypeService.findAll()));
		storageTypeLayout.addComponent(storageTypeCombo);
		popupMainLayout.addComponent(storageTypeLayout);
		storageTypeCombo.setItemCaptionMode(ItemCaptionMode.EXPLICIT);
		storageTypeCombo.setItemCaptionPropertyId("name");

		if (event.getButton().getCaption().equals("Add Library")){
		    
			nameTextField.setValue("");
			descriptionTextField.setValue("");
			
			librarybutton.setCaption("Add");
			librarybutton.addClickListener(new ClickListener() {
				private static final long serialVersionUID = 1L;

				public void buttonClick(ClickEvent event) {
					

		            if(!( nameTextField.getValue().toString().isEmpty() 
		            		&& descriptionTextField.getValue().toString().isEmpty() && StringUtils.isEmpty((String)storageTypeCombo.getValue())   ) )
		            {
		            	ImageLibraryDto library=null;
						try {
							library = new ImageLibraryDto();
							library.setName(nameTextField.getValue().toString());
							library.setDescription(descriptionTextField.getValue().toString());

							 //Get accountId from the session
				            final Integer accountId = (Integer)SessionHelper.loadAttribute("accountId");
				            final AccountService accountService = (AccountService)helper.getBean("accountService");
				            final AccountDto accountDto = accountService.findAccountById(accountId);
				            library.setAccountDto(accountDto);
				            library.setContentType("IMAGE");

				            library.setStorageTypeId(Integer.parseInt(storageTypeCombo.getValue().toString()));
							imageLibraryService.create(library);
							Notification.show(library.getName() +" added succesfully");
						} catch (final EntityAlreadyFoundException e) {
							LOGGER.debug("Unable to create library with name" + library.getName());
							Notification.show("Library with name "+library.getName()+ " already exists.Please use other library name.",Notification.Type.WARNING_MESSAGE);
						}
						catch (final EntityNotCreatedException e) {
							LOGGER.debug("Unable to create new library with name" + library.getName());
							Notification.show("Library with name "+library.getName()+ " cannot be created.Please contact support team.",Notification.Type.WARNING_MESSAGE);
						}
						catch (final InvalidStorageException e) {
							LOGGER.debug("Unable to create new library with name" + library.getName());
							Notification.show("Storage configuration for this storage type is not configured for this account.Please contact support team for new configuration" ,Notification.Type.WARNING_MESSAGE);
						}

			    		UI.getCurrent().removeWindow(popupWindow);
				        openbutton.setEnabled(true);
		            }else{
		            	Notification.show("Add Failed","One or more field is empty",Notification.Type.WARNING_MESSAGE);
		            }
					
				}
			});
			
		}//end if
	
		final HorizontalLayout addButtonLayout = new HorizontalLayout();
		addButtonLayout.setSpacing(true);
		popupMainLayout.addComponent(addButtonLayout);
		addButtonLayout.addComponent(librarybutton);
		addButtonLayout.setComponentAlignment(librarybutton,
				Alignment.BOTTOM_RIGHT);
		addButtonLayout.setWidth(100, Unit.PERCENTAGE);
		popupWindow.setCaption("Add Library");
		popupWindow.setContent(popupMainLayout);
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
			UI.getCurrent().removeWindow(popupWindow);

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

	@Override
	public void buttonClick(ClickEvent event) {
		try {
			this.openButtonClick(event);
		} catch (EntityAlreadyFoundException e) {
			e.printStackTrace();
		}		
	}
}
