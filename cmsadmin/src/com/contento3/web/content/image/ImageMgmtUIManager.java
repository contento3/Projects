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

import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.dam.image.dto.ImageDto;
import com.contento3.dam.image.library.dto.ImageLibraryDto;
import com.contento3.dam.image.library.service.ImageLibraryService;
import com.contento3.dam.image.service.ImageService;
import com.contento3.web.UIManager;
import com.contento3.web.common.helper.ComboDataLoader;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.Application;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.terminal.FileResource;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Select;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TabSheet.Tab;

public class ImageMgmtUIManager extends CustomComponent 
			implements Upload.SucceededListener,
											Upload.FailedListener,
											Upload.Receiver,UIManager{

	private static final Logger LOGGER = Logger.getLogger(ImageMgmtUIManager.class);
	
	private static final long serialVersionUID = 5131819177752243660L;
	
	/**
	 * Helper to load the spring context
	 */
    private SpringContextHelper helper;
    
    /**
     * ParentWindow that holds this screen
     */
	private Window parentWindow;

	/**
	 * Vaadin Application instance.
	 */
	private Application application;
	
	/**
	 * Root element for contained components.
	 */
    Panel root;         
    
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
	 * TabSheet serves as the parent container for the article manager
	 */

	private TabSheet tabSheet;

	/**
	 * main layout for article manager screen
	 */
	private VerticalLayout verticalLayout = new VerticalLayout();

	private CssLayout layout = new CssLayout();

    public ImageMgmtUIManager(final TabSheet uiTabSheet,final SpringContextHelper helper,final Window parentWindow){
		this.helper = helper;
		this.parentWindow = parentWindow;
		this.tabSheet = uiTabSheet;
		this.imageService = (ImageService)helper.getBean("imageService");
		this.imageLibraryService = (ImageLibraryService) helper.getBean("imageLibraryService");
		
	}
    
    
    @Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Component render(String command) {
		this.tabSheet.setHeight(100, Sizeable.UNITS_PERCENTAGE);
		final Tab articleTab = tabSheet.addTab(verticalLayout, "Image Management");
		articleTab.setClosable(true);
		this.verticalLayout.setSpacing(true);
		this.verticalLayout.setWidth(100,Sizeable.UNITS_PERCENTAGE);
		renderImageMgmntComponenets();
		return this.tabSheet;
	}

	@Override
	public Component render(String command, Integer entityFilterId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component render(String command,
			HierarchicalContainer treeItemContainer) {
		// TODO Auto-generated method stub
		return null;
	}
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void renderImageMgmntComponenets() {
		/* heading */
		Label imageHeading = new Label("Image Manager");
		imageHeading.setStyleName("screenHeading");
		verticalLayout.addComponent(imageHeading);
		verticalLayout.addComponent(new HorizontalRuler());
		verticalLayout.setMargin(true);
		//verticalLayout.setSizeFull();
		
		/* Button to add new images */
		Button addImageButton = new Button();
		verticalLayout.addComponent(addImageButton);
		HorizontalLayout horizLayout = new HorizontalLayout();
		horizLayout.setSpacing(true);
		addImageButton.addListener(new ClickListener(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event){
				VerticalLayout newArticleLayout = new VerticalLayout();
				Tab createNew = tabSheet.addTab(newArticleLayout, String.format("Create new image"));
				createNew.setClosable(true);
				tabSheet.setSelectedTab(newArticleLayout);
				newArticleLayout.addComponent(renderAddScreen());
				newArticleLayout.setHeight("100%");
			}
		});
		addImageButton.setCaption("Add image");
		horizLayout.addComponent(addImageButton);
		
		/* Button to add library */
		Button addLibraryButton = new Button("Add Library",new ImageLibraryPopup(parentWindow, helper),"openButtonClick");
		horizLayout.addComponent(addLibraryButton);
		verticalLayout.addComponent(horizLayout);
		verticalLayout.addComponent(new HorizontalRuler());
		
		/* image library combo*/
		//Get accountId from the session
        final Integer accountId = (Integer)SessionHelper.loadAttribute(parentWindow, "accountId");
		ImageLibraryService imageLibraryService = (ImageLibraryService) this.helper.getBean("imageLibraryService");
	    Collection<ImageLibraryDto> imageLibraryDto = imageLibraryService.findImageLibraryByAccountId(accountId);
		final ComboDataLoader comboDataLoader = new ComboDataLoader();
	    final ComboBox imageLibrayCombo = new ComboBox("Select library",
				comboDataLoader.loadDataInContainer((Collection)imageLibraryDto ));
	    imageLibrayCombo.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
		imageLibrayCombo.setItemCaptionPropertyId("name");
		HorizontalLayout horiz = new HorizontalLayout();
		horiz.setSpacing(true);
		horiz.addComponent(imageLibrayCombo);
	    
	    /* search button */
	    Button searchButton = new Button("Search");
	    
	    searchButton.addListener(new ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				layout.removeAllComponents(); // remove CSSlayout which contains panel of images
				int libraryId = Integer.parseInt(imageLibrayCombo.getValue().toString());
				Collection<ImageDto> images = imageService.findImagesByLibrary(libraryId);
				verticalLayout.addComponent(listImage(images));
			}
		});
	    
	    horiz.addComponent(searchButton);
	    horiz.setComponentAlignment(searchButton, Alignment.BOTTOM_LEFT);
	    verticalLayout.addComponent(horiz);

	}
	    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private Component renderAddScreen(){
		root = new Panel("Upload image");

		VerticalLayout imageLayout = new VerticalLayout();
		imageLayout.setSpacing(true);
		altTextField = new TextField();
		altTextField.setCaption("Alt text");
	
		imageLayout.addComponent(altTextField);
		
		imageNameField = new TextField();
		imageNameField.setCaption("Image name");
	
		imageLayout.addComponent(imageNameField);
		
		//Get accountId from the session
        final Integer accountId = (Integer)SessionHelper.loadAttribute(parentWindow, "accountId");
        Collection<ImageLibraryDto> imageLibraryDto = this.imageLibraryService.findImageLibraryByAccountId(accountId);
		final ComboDataLoader comboDataLoader = new ComboDataLoader();
		imageLibrayCombo = new ComboBox("Select library",
				comboDataLoader.loadDataInContainer((Collection)imageLibraryDto ));
		
		imageLayout.addComponent(imageLibrayCombo);
		
		imageLibrayCombo.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
		imageLibrayCombo.setItemCaptionPropertyId("name");
		
		// Create the Upload component.
		Upload upload = new Upload("Upload Image", this);
		// Listen for events regarding the success of upload.
        upload.addListener((Upload.SucceededListener) this);
        upload.addListener((Upload.FailedListener) this);
        imageLayout.addComponent(upload);
        
        root.addComponent(upload);
        root.addComponent(new Label("Click 'Browse' to "+
        "select a file and then click 'Upload'."));

        // Create a panel for displaying the uploaded image.
        imagePanel = new Panel("Uploaded image");
        imagePanel.addComponent(new Label("No image uploaded yet"));
        root.addComponent(imagePanel);
        imageLayout.addComponent(root);
        return imageLayout;
	}
	   
    
    /**
     * Callback method to begin receiving the upload.
     * @param filename
     * @param MIMEType
     * @return
     */
    public OutputStream receiveUpload(final String filename,final String MIMEType) {
        fos = null; // Output stream to write to
        file = new File(filename);
        try {
            // Open the file for writing.
            fos = new FileOutputStream(file);
        } catch(Exception e){
        	LOGGER.error("Unable to upload the image",e);
        } 
        
        return fos; // Return the output stream to write to
    }
    
    // This is called if the upload is finished.
    /**
     * 
     */
    public void uploadSucceeded(Upload.SucceededEvent event) {
        // Log the upload on screen.
        root.addComponent(new Label(String.format("File %s of type ' %s ' uploaded.",event.getFilename(),event.getMIMEType())));
        imageResource = new FileResource(file, parentWindow.getApplication());
        imagePanel.addComponent(new Embedded("", imageResource));
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
 			
            ImageDto imageDto = new ImageDto();
            imageDto.setAltText(altTextField.getValue().toString());
            imageDto.setImage(bFile);
            imageDto.setName(imageNameField.getValue().toString());
            
            //Get accountId from the session
            final Integer accountId = (Integer)SessionHelper.loadAttribute(parentWindow, "accountId");
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
			} catch (EntityAlreadyFoundException e) {
				LOGGER.error("Unable to create the image as it is already created",e);
			}
        catch (final java.io.FileNotFoundException e) {
			LOGGER.error("Unable to create the image.",e);
        }
        catch(IOException ioe){
			LOGGER.error("Unable to create the image.",ioe);
		}

	    imagePanel.removeAllComponents();
	    imagePanel.setSizeFull();
	    imageResource = new FileResource(file, parentWindow.getApplication());
	    imagePanel.addComponent(new Embedded("", imageResource));
	    imageResource.setCacheTime(0);
	}

    /**
     * This is called if the upload fails.
     */
    public void uploadFailed(Upload.FailedEvent event) {
        // Log the failure on screen.
        root.addComponent(new Label(String.format("Uploading of %s of type %s failed.",event.getFilename(),event.getMIMEType())));
    }
    
    @Override
    public void attach() {
        super.attach(); // Must call.
        application = this.getApplication();
        
        // Display the uploaded file in the image panel.
        imageResource = new FileResource(file, application);
        imagePanel.addComponent(new Embedded("", imageResource));
        imageResource.setCacheTime(0);
    }
    
    /**
     * List all the images associated to this account
     * TODO This should be filtered by library and ideally paged.
     * @param accountId
     * @return
     */
    @SuppressWarnings("rawtypes")
	private Component listImage(final Collection<ImageDto> imageList){
 
    	for (ImageDto dto:imageList){
	    	Panel imagePanel = new Panel();
	    	imagePanel.addComponent(loadImage(dto));
	    	imagePanel.setScrollable(false);
	    	
	    	VerticalLayout imageLayout = new VerticalLayout();
	    	imageLayout.addComponent(imagePanel);
	    	imagePanel.setHeight("165");
	    	imagePanel.setWidth("160");
	    	
	    	VerticalLayout imageInfoLayout = new VerticalLayout();

	    	Button viewImageDetail = new Button("View Image");
	    	viewImageDetail.setStyleName("link");

	    	Button editImageDetail = new Button("Edit Image",new ImageEditListner(helper,parentWindow,dto),"openButtonClick");
	    	editImageDetail.setStyleName("link");

	    	imageInfoLayout.setSpacing(true);
	    	
	    	imageInfoLayout.addComponent(viewImageDetail);
	    	imageInfoLayout.setComponentAlignment(viewImageDetail, Alignment.MIDDLE_CENTER);

	    	imageInfoLayout.addComponent(editImageDetail);
	    	imageInfoLayout.setComponentAlignment(editImageDetail, Alignment.MIDDLE_CENTER);

	    	Panel mainPanel = new Panel();
	    	mainPanel.addComponent(imageLayout);
	    	mainPanel.addComponent(imageInfoLayout);

	    	mainPanel.setHeight("245");
	    	mainPanel.setWidth("200");
	    	mainPanel.setScrollable(false);
	    	mainPanel.addStyleName("csslayoutinnercomponent");
	    	layout.addComponent(mainPanel);
	 	}
    	
    	layout.setSizeUndefined();
    	layout.setMargin(true);
    	return layout;
    } 
    
    /**
     * Loads the image using the Image loader class based on width and height provided.
     * @param imageDto
     * @return
     */
	public Embedded loadImage(final ImageDto imageDto) {
		final ImageLoader imageLoader = new ImageLoader();
		final Embedded embedded = imageLoader.loadImage(parentWindow.getApplication(), imageDto.getImage(), 125, 125);
		return embedded;
	}

	

}
