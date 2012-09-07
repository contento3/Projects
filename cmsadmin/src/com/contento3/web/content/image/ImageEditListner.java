package com.contento3.web.content.image;

import java.util.Collection;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountService;
import com.contento3.dam.image.dto.ImageDto;
import com.contento3.dam.image.library.dto.ImageLibraryDto;
import com.contento3.dam.image.library.service.ImageLibraryService;
import com.contento3.dam.image.service.ImageService;
import com.contento3.web.common.helper.ComboDataLoader;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.CloseEvent;

public class ImageEditListner extends  CustomComponent
implements Window.CloseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Helper to load the spring context
	 */
    private SpringContextHelper helper;
    
    /**
     * ParentWindow that holds this screen
     */
	private Window mainWindow;
	
	/**
	 * The window to be opened
	 */
	private Window popupWindow;

	/**
	 * ImageService for image related operations
	 */
	private ImageService imageService;
	  
	/**
	 * ImageLibraryService for imageLibrary related operations
	 */
    private ImageLibraryService imageLibraryService;
    
	/**
	 * image to be edit
	 */
	private ImageDto imageDto;
	
	/**
	 * Button for opening the window
	 */
	private Button openbutton; 
	
	boolean isModalWindowClosable = true;
	 
	public ImageEditListner(final SpringContextHelper helper,final Window parentWindow,final ImageDto imageDto ) {
		this.helper = helper;
		this.mainWindow = parentWindow;
		this.imageService = (ImageService)helper.getBean("imageService");
		this.imageLibraryService = (ImageLibraryService) helper.getBean("imageLibraryService");
		this.imageDto = imageDto;
		
		 // The component contains a button that opens the window.
        final VerticalLayout layout = new VerticalLayout();
        openbutton = new Button("Edit Image", this, "openButtonClick");
        layout.addComponent(openbutton);
        setCompositionRoot(layout);
	}

    /** Handle the clicks for the two buttons. */
    public void openButtonClick(Button.ClickEvent event) {
    	   /* Create a new window. */
        final Button saveButton = new Button();
        saveButton.setCaption("Save");
		popupWindow = new Window();
    	
		popupWindow.setPositionX(200);
    	popupWindow.setPositionY(100);

    	popupWindow.setHeight(30,Sizeable.UNITS_PERCENTAGE);
    	popupWindow.setWidth(22,Sizeable.UNITS_PERCENTAGE);
       
    	/* Add the window inside the main window. */
        mainWindow.addWindow(popupWindow);
        
        /* Listen for close events for the window. */
        popupWindow.addListener(this);
        popupWindow.setModal(true);
        
        final VerticalLayout popupMainLayout = new VerticalLayout();
        popupMainLayout.setSpacing(true);
        final HorizontalLayout altDataLayout = new HorizontalLayout();
        altDataLayout.setSizeFull();
        final Label altLabel = new Label("Alt text");
        final TextField altTextField  = new TextField();
        altTextField.setValue(imageDto.getAltText());
        altDataLayout.setSpacing(true);
        altDataLayout.addComponent(altLabel);
        altDataLayout.addComponent(altTextField);
        altDataLayout.setComponentAlignment(altLabel, Alignment.BOTTOM_RIGHT);
        altDataLayout.setComponentAlignment(altTextField, Alignment.MIDDLE_CENTER);
        popupMainLayout.addComponent(altDataLayout);
        
        final HorizontalLayout nameDataLayout = new HorizontalLayout();
        nameDataLayout.setSizeFull();
        final Label nameLabel = new Label("Image name");
        final TextField nameTextField  = new TextField();
        nameTextField.setValue(imageDto.getName());
        nameDataLayout.setSpacing(true);
        nameDataLayout.addComponent(nameLabel);
        nameDataLayout.addComponent(nameTextField);
        nameDataLayout.setComponentAlignment(nameLabel, Alignment.BOTTOM_RIGHT);
        nameDataLayout.setComponentAlignment(nameTextField, Alignment.MIDDLE_CENTER);
        popupMainLayout.addComponent(nameDataLayout);
     
        final HorizontalLayout imageLibraryDataLayout = new HorizontalLayout();
        imageLibraryDataLayout.setSpacing(true);
        final Label libraryLabel = new Label("Select library");
        
        //Get accountId from the session
        final Integer accountId = (Integer)SessionHelper.loadAttribute(mainWindow, "accountId");
        Collection<ImageLibraryDto> imageLibraryDto = this.imageLibraryService.findImageLibraryByAccountId(accountId);
		final ComboDataLoader comboDataLoader = new ComboDataLoader();
		final ComboBox imageLibrayCombo = new ComboBox();
		imageLibrayCombo.setContainerDataSource(comboDataLoader.loadDataInContainer((Collection)imageLibraryDto ));
		imageLibrayCombo.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
		imageLibrayCombo.setItemCaptionPropertyId("name");
		imageLibrayCombo.setValue(imageDto.getImageLibraryDto().getId());
		imageLibraryDataLayout.setSpacing(true);
		imageLibraryDataLayout.addComponent(libraryLabel);
		imageLibraryDataLayout.addComponent(imageLibrayCombo);
		imageLibraryDataLayout.setComponentAlignment(libraryLabel, Alignment.BOTTOM_RIGHT);
		popupMainLayout.addComponent(imageLibraryDataLayout);
		
		saveButton.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				imageDto.setAltText(altTextField.getValue().toString());
				imageDto.setName(nameTextField.getValue().toString());
				//set imageLibrary to imageDto
	            if(imageLibrayCombo.getValue()!=null){
	            	imageDto.setImageLibraryDto(imageLibraryService
	            			.findImageLibraryById(Integer
	            					.parseInt(imageLibrayCombo.getValue()
	            							.toString())));
	            }

	            final AccountDto accountDto = new AccountDto();
	            accountDto.setAccountId(accountId);
				imageDto.setAccountDto(accountDto);
	            imageService.update(imageDto);
	    		mainWindow.showNotification(imageDto.getName() +" updated succesfully");
	    		mainWindow.removeWindow(popupWindow);
		        openbutton.setEnabled(true);
			}
		});
		
        final HorizontalLayout addButtonLayout = new HorizontalLayout();
        popupMainLayout.addComponent(addButtonLayout);
        addButtonLayout.addComponent(saveButton);
        addButtonLayout.setComponentAlignment(saveButton, Alignment.BOTTOM_RIGHT);
        addButtonLayout.setWidth(100, Sizeable.UNITS_PERCENTAGE);
        popupWindow.setCaption("Edit image");
        popupWindow.addComponent(popupMainLayout);
        popupWindow.setResizable(false);
        /* Allow opening only one window at a time. */
        openbutton.setEnabled(false);
    }
    
	  /** Handle Close button click and close the window. */
    public void closeButtonClick(Button.ClickEvent event) {
    	if (!isModalWindowClosable){
        /* Windows are managed by the application object. */
        mainWindow.removeWindow(popupWindow);
        
        /* Return to initial state. */
        openbutton.setEnabled(true);
    	}
    }

    /** In case the window is closed otherwise. */
    public void windowClose(CloseEvent e) {
        /* Return to initial state. */
        openbutton.setEnabled(true);
    }

}
