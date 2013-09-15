package com.contento3.web.content.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.vaadin.pagingcomponent.PagingComponent;
import org.vaadin.pagingcomponent.listener.impl.LazyPagingComponentListener;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.dam.image.dto.ImageDto;
import com.contento3.dam.image.library.dto.ImageLibraryDto;
import com.contento3.dam.image.library.service.ImageLibraryService;
import com.contento3.dam.image.service.ImageService;
import com.contento3.util.CachedTypedProperties;
import com.contento3.web.UIManager;
import com.contento3.web.common.helper.ComboDataLoader;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

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
	
	/**
	 * Constructor
	 * @param uiTabSheet
	 * @param helper
	 * @param parentWindow
	 */
    public ImageMgmtUIManager(final TabSheet uiTabSheet,final SpringContextHelper helper){
		this.helper = helper;
		this.tabSheet = uiTabSheet;
		this.imageService = (ImageService)helper.getBean("imageService");
		this.imageLibraryService = (ImageLibraryService) helper.getBean("imageLibraryService");
		
	}
    
    
    @Override
	public void render() {
	}
    
    /**
     * Render image manager screen
     */
	@Override
	public Component render(String command) {
		this.tabSheet.setHeight(100, Unit.PERCENTAGE);
		final Tab articleTab = tabSheet.addTab(mainLayout, "Image Management",new ExternalResource("images/content-mgmt.png"));
		articleTab.setClosable(true);
		this.mainLayout.setSpacing(true);
		this.mainLayout.setWidth(100,Unit.PERCENTAGE);
		renderImageMgmntComponenets();
		return this.tabSheet;
	}

	@Override
	public Component render(String command, Integer entityFilterId) {
		return null;
	}

	@Override
	public Component render(String command,
			HierarchicalContainer treeItemContainer) {
		return null;
	}
    
	/**
	 * Render image management related components
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void renderImageMgmntComponenets() {
		/* heading */
		Label imageHeading = new Label("Image Manager");
		imageHeading.setStyleName("screenHeading");
		mainLayout.addComponent(imageHeading);
		mainLayout.addComponent(new HorizontalRuler());
		mainLayout.setMargin(true);
		
		/* Button to add new images */
		Button addImageButton = new Button();
		mainLayout.addComponent(addImageButton);
		HorizontalLayout horizLayout = new HorizontalLayout();
		horizLayout.setSpacing(true);
	
		addImageButton.setCaption("Add image");
		
		/*Add Image button listener*/
		addImageButton.addClickListener(new ClickListener(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event){
				VerticalLayout newArticleLayout = new VerticalLayout();
				Tab createNew = tabSheet.addTab(newArticleLayout, String.format("Create new image"),new ExternalResource("images/content-mgmt.png"));
				createNew.setClosable(true);
				tabSheet.setSelectedTab(newArticleLayout);
				newArticleLayout.addComponent(renderAddEditScreen("Add",null));
				//newArticleLayout.setHeight("100%");
			}
		});
		horizLayout.addComponent(addImageButton);
		
		/* Button to add library */
		Button addLibraryButton = new Button("Add Library",new ImageLibraryPopup(helper));
		horizLayout.addComponent(addLibraryButton);
		mainLayout.addComponent(horizLayout);
		mainLayout.addComponent(new HorizontalRuler());
		
		/* image library combo*/
		//Get accountId from the session
        final Integer accountId = (Integer)SessionHelper.loadAttribute("accountId");
		ImageLibraryService imageLibraryService = (ImageLibraryService) this.helper.getBean("imageLibraryService");
	    Collection<ImageLibraryDto> imageLibraryDto = imageLibraryService.findImageLibraryByAccountId(accountId);
		final ComboDataLoader comboDataLoader = new ComboDataLoader();
	    final ComboBox imageLibrayCombo = new ComboBox("Select library",
				comboDataLoader.loadDataInContainer((Collection)imageLibraryDto ));
	    imageLibrayCombo.setItemCaptionMode(ComboBox.ItemCaptionMode.PROPERTY);
		imageLibrayCombo.setItemCaptionPropertyId("name");
		HorizontalLayout horiz = new HorizontalLayout();
		horiz.setSpacing(true);
		horiz.addComponent(imageLibrayCombo);
	    

	    /* search button */
	    Button searchButton = new Button("Search");
		/*Search Image button listener*/
	    searchButton.addClickListener(new ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(final ClickEvent event) {
				imagePanlelayout.removeAllComponents(); // remove items from CSSlayout which contains panels of image
				Object id = imageLibrayCombo.getValue();
				if(id != null){
					int libraryId = Integer.parseInt(id.toString());
					Collection<ImageDto> images = imageService.findImagesByLibrary(libraryId);
					displayImages(images);
				}else{
					Notification.show("Search Failed", "Select library first",Notification.Type.ERROR_MESSAGE);
				}
			}

			
		});
	    
	    horiz.addComponent(searchButton);
	    horiz.setComponentAlignment(searchButton, Alignment.BOTTOM_LEFT);
	    mainLayout.addComponent(horiz);
	    mainLayout.addComponent(imagePanlelayout);
	}
	
	/**
	 * Display images associated to library
	 * @param images
	 */
	@SuppressWarnings({ "deprecation"})
	private void displayImages(final Collection<ImageDto> images) {
		
		
		final List<ImageDto> list = new ArrayList<ImageDto>();
		for(ImageDto dto: images){
			list.add(dto);
		}
		
        // Layout where we will display items (changing when we click next page).
        final CssLayout itemsArea = new CssLayout();
        itemsArea.setSizeUndefined();
        
        try {

			final CachedTypedProperties languageProperties = CachedTypedProperties.getInstance("paging.properties");
			int NmbrOfImagesOnPage = languageProperties.getIntProperty("NumberOfImages");
			
			 // Visual controls (First, Previous, 1 2 ..., Next, Last)
	        // We use here a LazyPagingComponentListener to fetch the list of items to display from the DB
	        final PagingComponent<ImageDto> pagingComponent = new PagingComponent<ImageDto>(NmbrOfImagesOnPage, images, new LazyPagingComponentListener<ImageDto>(itemsArea) {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				protected Collection<ImageDto> getItemsList(int startIndex, int endIndex) {
					return list.subList(startIndex, endIndex);
				}

				@Override
				protected Component displayItem(int index, ImageDto item) {
					return addImagesToPanel(item);
				}

	        });
	        imagePanlelayout.addComponent(itemsArea);
	        imagePanlelayout.addComponent(pagingComponent);
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
        
        
       
	}
	
	/**
	 * Add images to panel
	 * @param dto
	 * @return
	 */
	private Component addImagesToPanel(final ImageDto dto){
		Panel imagePanel = new Panel();
    	imagePanel.setContent(loadImage(dto));
    	
    	VerticalLayout imageLayout = new VerticalLayout();
    	imageLayout.addComponent(imagePanel);
    	imagePanel.setHeight("165");
    	imagePanel.setWidth("160");
    	
    	final VerticalLayout imageInfoLayout = new VerticalLayout();

    	final Button viewImageDetail = new Button("View Image");
    	viewImageDetail.setStyleName("link");

    	final Button editImageDetail = new Button("Edit Image");
    	editImageDetail.setStyleName("link");
    	editImageDetail.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(final ClickEvent event) {
				VerticalLayout newArticleLayout = new VerticalLayout();
				Tab createNew = tabSheet.addTab(newArticleLayout, String.format("Edit "+ dto.getName()));
				createNew.setClosable(true);
				tabSheet.setSelectedTab(newArticleLayout);
				newArticleLayout.addComponent(renderAddEditScreen("Edit",dto));
			}
		});
    	
    	imageInfoLayout.setSpacing(true);
    	
    	imageInfoLayout.addComponent(viewImageDetail);
    	imageInfoLayout.setComponentAlignment(viewImageDetail, Alignment.MIDDLE_CENTER);

    	imageInfoLayout.addComponent(editImageDetail);
    	imageInfoLayout.setComponentAlignment(editImageDetail, Alignment.MIDDLE_CENTER);

    	final Panel mainPanel = new Panel();
    	final VerticalLayout mainPanelLayout = new VerticalLayout();
    	
    	mainPanelLayout.addComponent(imageLayout);
    	mainPanelLayout.addComponent(imageInfoLayout);

    	mainPanel.setContent(mainPanelLayout);
    	mainPanel.setHeight("245");
    	mainPanel.setWidth("200");
    	//mainPanel.setScrollable(false);
    	mainPanel.addStyleName("csslayoutinnercomponent");
    	return mainPanel;
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
	    	 	imagePanelContent.setHeight("170");
	    	 	imagePanelContent.setWidth("160");
	    	 	imagePanelContent.addComponent(loadImage(imageDto));
				altTextField.setValue(this.imageDto.getAltText());
				imageNameField.setValue(this.imageDto.getName());
				imageLibrayCombo.setValue(this.imageDto.getImageLibraryDto().getId());
				/* Save button */
				Button saveButton = new Button("Save");
				saveButton.addClickListener(new ClickListener() {
				
					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(ClickEvent event) {
						
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
    
    /**
     * List all the images associated to this account
     * TODO This should be filtered by library and ideally paged.
     * @param accountId
     * @return
     */
    @SuppressWarnings("unused")
	private Component listImage(final Collection<ImageDto> imageList){
 
    	for (ImageDto dto:imageList){
	    	final Panel imagePanel = new Panel();
	    	final VerticalLayout imagePanelContent = new VerticalLayout();
	    	imagePanelContent.addComponent(loadImage(dto));
	    	imagePanel.setContent(imagePanelContent);
	    	
	    	final VerticalLayout imageLayout = new VerticalLayout();
	    	imageLayout.addComponent(imagePanel);
	    	imagePanel.setHeight("165");
	    	imagePanel.setWidth("160");
	    	
	    	final VerticalLayout imageInfoLayout = new VerticalLayout();
	    	final Button viewImageDetail = new Button("View Image");
	    	viewImageDetail.setStyleName("link");

	    	final Button editImageDetail = new Button("Edit Image",new ImageEditListner(helper,dto));
	    	editImageDetail.setStyleName("link");

	    	imageInfoLayout.setSpacing(true);
	    	
	    	imageInfoLayout.addComponent(viewImageDetail);
	    	imageInfoLayout.setComponentAlignment(viewImageDetail, Alignment.MIDDLE_CENTER);

	    	imageInfoLayout.addComponent(editImageDetail);
	    	imageInfoLayout.setComponentAlignment(editImageDetail, Alignment.MIDDLE_CENTER);

	    	final Panel mainPanel = new Panel();
	    	mainPanel.setContent(imageLayout);
	    	mainPanel.setContent(imageInfoLayout);

	    	mainPanel.setHeight("245");
	    	mainPanel.setWidth("200");
	    	mainPanel.addStyleName("csslayoutinnercomponent");
	    	imagePanlelayout.addComponent(mainPanel);
	 	}
    	
    	imagePanlelayout.setSizeUndefined();
    	return imagePanlelayout;
    } 
    
    /**
     * Loads the image using the Image loader class based on width and height provided.
     * @param imageDto
     * @return
     */
	public Embedded loadImage(final ImageDto imageDto) {
		final ImageLoader imageLoader = new ImageLoader();
		final Embedded embedded = imageLoader.loadImage(imageDto.getImage(), 125, 125);
		return embedded;
	}

	

}
