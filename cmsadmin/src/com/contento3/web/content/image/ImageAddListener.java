package com.contento3.web.content.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.AuthorizationException;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.dam.image.dto.ImageDto;
import com.contento3.dam.image.library.dto.ImageLibraryDto;
import com.contento3.dam.image.library.service.ImageLibraryService;
import com.contento3.dam.image.service.ImageService;
import com.contento3.web.common.helper.ComboDataLoader;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

public class ImageAddListener extends CustomComponent implements ClickListener,
										Upload.Receiver,Upload.SucceededListener,Upload.FailedListener {
	
	private static final Logger LOGGER = Logger.getLogger(ImageAddListener.class);

	/**
	 * Helper to load the spring context
	 */
    private SpringContextHelper helper;
    
	/**
	 * Root element for contained components.
	 */
    Panel root;         
    
    /**
     * Content layout for root panel
     */
    VerticalLayout rootContentLayout;
    
    /**
     * Panel that contains the uploaded image.
     */
    Panel imagePanel;   
    
    /**
     * File to write to.
     */
    File  file;         
    
    /**
     * File Resource for image
     */
    FileResource imageResource;

    /**
     * Alt text text-field to display
     */
    TextField altTextField;
    
    /**
     * Image name text field to display
     */
    TextField imageNameField;
    
    /**
     * Service layer for image
     */
    ImageService imageService;

    /**
     * FileOutputStream used in image upload.
     */
    FileOutputStream fos ;
    
    private ImageLibraryService imageLibraryService;
    
    private ComboBox imageLibrayCombo;
    
	/**
	 * TabSheet serves as the parent container for the image manager
	 */
	private TabSheet tabSheet;

	/**
	 * main layout for image manager screen
	 */
	private VerticalLayout mainLayout = new VerticalLayout();
	/**
	 * Layout contain images
	 */
	private CssLayout imagePanlelayout = new CssLayout();
	
	private String caption = null;
	
	private ImageDto imageDto;
	
	private VerticalLayout imagePanelContent;
	
	public ImageAddListener (final TabSheet tabSheet,final SpringContextHelper helper){
		this.helper = helper;
		this.tabSheet = tabSheet;
		this.imageService = (ImageService)helper.getBean("imageService");
		this.imageLibraryService = (ImageLibraryService) helper.getBean("imageLibraryService");
	}

	@Override
	public void click(ClickEvent event) {
		VerticalLayout newImageLayout = new VerticalLayout();
		Tab createNew = tabSheet.addTab(newImageLayout, String.format("Create new image"),new ExternalResource("images/content-mgmt.png"));
		createNew.setClosable(true);
		tabSheet.setSelectedTab(newImageLayout);
		newImageLayout.addComponent(renderAddEditScreen("Add",null));
	}
	
	/**
	 * Render screen for add and edit
	 * @param command
	 * @param image
	 * @return
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private Component renderAddEditScreen(final String command,final ImageDto dto){
		root = new Panel("Upload image");
		rootContentLayout = new VerticalLayout();
		root.setContent(rootContentLayout);
		
		this.caption = command;
		this.imageDto = dto;
		VerticalLayout imageLayout = new VerticalLayout();
		imageLayout.setSpacing(true);
		altTextField = new TextField();
		altTextField.setCaption("Alt text");
	
		imageNameField = new TextField();
		imageNameField.setCaption("Image name");

		//Get accountId from the session
        final Integer accountId = (Integer)SessionHelper.loadAttribute("accountId");
        Collection<ImageLibraryDto> imageLibraryDto = this.imageLibraryService.findImageLibraryByAccountId(accountId);
		final ComboDataLoader comboDataLoader = new ComboDataLoader();
		imageLibrayCombo = new ComboBox("Select library",
				comboDataLoader.loadDataInContainer((Collection)imageLibraryDto ));
		
		imageLibrayCombo.setItemCaptionMode(ComboBox.ItemCaptionMode.PROPERTY);
		imageLibrayCombo.setItemCaptionPropertyId("name");
		
		// Create the Upload component.
		Upload upload = new Upload("Upload Image", this);
		// Listen for events regarding the success of upload.
        upload.addSucceededListener((Upload.SucceededListener) this);
        upload.addFailedListener((Upload.FailedListener) this);
    	
        
        rootContentLayout.addComponent(upload);
        rootContentLayout.addComponent(new Label("Click 'Browse' to "+
        "select a file and then click 'Upload'."));

        // Create a panel for displaying the uploaded image.
        imagePanel = new Panel("Uploaded image");
        imagePanelContent = new VerticalLayout();
        
        imagePanel.setContent(imagePanelContent);
        imagePanelContent.addComponent(new Label("No image uploaded yet"));
        rootContentLayout.addComponent(imagePanel);

        imageLayout.addComponent(altTextField);
		imageLayout.addComponent(imageNameField);
		imageLayout.addComponent(imageLibrayCombo);
		imageLayout.addComponent(upload);
	    imageLayout.addComponent(root);
	    
	     if(this.caption.equals("Edit")){
//	    	 	imagePanelContent.setHeight("170");
//	    	 	imagePanelContent.setWidth("160");
	    	 	imagePanelContent.addComponent(loadImage(imageDto,null,null));
				altTextField.setValue(this.imageDto.getAltText());
				imageNameField.setValue(this.imageDto.getName());
				imageLibrayCombo.setValue(this.imageDto.getImageLibraryDto().getId());
				/* Save button */
				Button saveButton = new Button("Save");
				saveButton.addClickListener(new com.vaadin.ui.Button.ClickListener() {
				
					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(Button.ClickEvent event) {
						
						imageDto.setAltText(altTextField.getValue().toString());
						imageDto.setName(imageNameField.getValue().toString());
						 //set imageLibrary to imageDto
			            if(imageLibrayCombo.getValue()!=null){
			            	imageDto.setImageLibraryDto(imageLibraryService
			            			.findImageLibraryById(Integer
			            					.parseInt(imageLibrayCombo.getValue()
			            							.toString())));
			            }
						 //Get accountId from the session
			            final Integer accountId = (Integer)SessionHelper.loadAttribute("accountId");
			            final AccountDto accountDto = new AccountDto();
			            accountDto.setAccountId(accountId);
						imageDto.setAccountDto(accountDto);
						imageService.update(imageDto);
						Notification.show(imageDto.getName()+" edit successfully");
					}
				});
				imageLayout.addComponent(saveButton);
			}
       
        return imageLayout;
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		// TODO Auto-generated method stub
		return null;
	}
	   
    /**
     * Loads the image using the Image loader class based on width and height provided.
     * @param imageDto
     * @return
     */
	public Embedded loadImage(final ImageDto imageDto,final Integer width,final Integer height) {
		final ImageLoader imageLoader = new ImageLoader();
		final Embedded embedded = imageLoader.loadImage(imageDto.getImage(), width, height);
		return embedded;
	}
	
	   // This is called if the upload is finished.
    /**
     * 
     */
    public void uploadSucceeded(Upload.SucceededEvent event) {
        // Log the upload on screen.
        root.setContent(new Label(String.format("File %s of type ' %s ' uploaded.",event.getFilename(),event.getMIMEType())));
        imageResource = new FileResource(file);
        imagePanel.setContent(new Embedded("", imageResource));
        imageResource.setCacheTime(0);
        
        FileInputStream fis = null;
    	byte[] bFile = new byte[(int) file.length()];
 		try {
 			fis = new FileInputStream(file);
 		} 
 		catch (FileNotFoundException e) {
 			LOGGER.error("Unable to upload the image.",e);
 		}
        try {
        	
 			fis.read(bFile);
 			
 			if(caption.equals("Add")){
 				
	            ImageDto imageDto = new ImageDto();
	            imageDto.setAltText(altTextField.getValue().toString());
	            imageDto.setImage(bFile);
	            imageDto.setName(imageNameField.getValue().toString());
	            //Get accountId from the session
	            final Integer accountId = (Integer)SessionHelper.loadAttribute("accountId");
	            final AccountService accountService = (AccountService)helper.getBean("accountService");
	            final AccountDto accountDto = new AccountDto();
	            accountDto.setAccountId(accountId);
	            imageDto.setAccountDto(accountDto);
	            final ImageService imageService = (ImageService)helper.getBean("imageService");
	            
	            //set imageLibrary to imageDto
	            if(imageLibrayCombo.getValue()!=null){
	            	imageDto.setImageLibraryDto(imageLibraryService
	            			.findImageLibraryById(Integer
	            					.parseInt(imageLibrayCombo.getValue()
	            							.toString())));
	            }
	            imageDto.setSiteDto(new ArrayList<SiteDto>());
	 	        fis.close();
	 	        imageService.create(imageDto);
 			}else{ //else edit
 				if(bFile != null)
 					this.imageDto.setImage(bFile);
 				
 			}
		} catch (EntityAlreadyFoundException e) {
			LOGGER.error("Unable to create the image as it is already created",e);
		}
        catch (final java.io.FileNotFoundException e) {
			LOGGER.error("Unable to create the image.",e);
        }
        catch(IOException ioe){
			LOGGER.error("Unable to create the image.",ioe);
		}
        catch(AuthorizationException ex){}

        imagePanelContent.removeAllComponents();
	    imagePanel.setSizeFull();
	    imageResource = new FileResource(file);
	    imagePanel.setContent(new Embedded("", imageResource));
	    imageResource.setCacheTime(0);
	}

    /**
     * This is called if the upload fails.
     */
    public void uploadFailed(Upload.FailedEvent event) {
        // Log the failure on screen.
        rootContentLayout.addComponent(new Label(String.format("Uploading of %s of type %s failed.",event.getFilename(),event.getMIMEType())));
    }
    
    @Override
    public void attach() {
        super.attach(); // Must call.
        
        // Display the uploaded file in the image panel.
        imageResource = new FileResource(file);
        imagePanel.setContent(new Embedded("", imageResource));
        imageResource.setCacheTime(0);
    }
    

}
