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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.contento3.account.dto.AccountDto;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.dam.image.dto.ImageDto;
import com.contento3.dam.image.library.dto.ImageLibraryDto;
import com.contento3.dam.image.library.service.ImageLibraryService;
import com.contento3.dam.image.service.ImageService;
import com.contento3.util.CachedTypedProperties;
import com.contento3.web.UIManager;
import com.contento3.web.common.helper.ComboDataLoader;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.common.helper.ScreenHeader;
import com.contento3.web.common.helper.ScreenToolbarBuilder;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.content.image.listener.AddImageButtonListener;
import com.contento3.web.content.image.listener.AddLibraryButtonListener;
import com.contento3.web.content.image.listener.DeleteListener;
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
import com.vaadin.ui.GridLayout;
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
											Upload.Receiver,UIManager, DeleteListener{

	private static final Logger LOGGER = Logger.getLogger(ImageMgmtUIManager.class);
	private static final long serialVersionUID = 5131819177752243660L;
	private final static String MSG_FILE_TYPE_NOT_SUPPORTED = "File type not supported.";
	
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

    byte[] bFile;
    
    /**
     * FileOutputStream used in image upload.
     */
    FileOutputStream fos ;
    
    final private ImageLibraryService imageLibraryService;
    
    private ComboBox imageLibrayCombo;
    
	/**
	 * TabSheet serves as the parent container for the image manager
	 */
	private TabSheet tabSheet;

	/**
	 * main layout for image manager screen
	 */
	private VerticalLayout mainLayout;
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
		
		if (null==tabSheet.getTab(mainLayout)){
			mainLayout = new VerticalLayout();
			final Tab articleTab = tabSheet.addTab(mainLayout, "Image Management",new ExternalResource("images/image-multi.png"));
			articleTab.setClosable(true);
			this.mainLayout.setSpacing(true);
			this.mainLayout.setWidth(100,Unit.PERCENTAGE);
			renderImageMgmntComponenets();
		}
		
		tabSheet.setSelectedTab(mainLayout);
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
		VerticalLayout verticall= new VerticalLayout();

		/* Button to add new images */
		HorizontalLayout horizLayout = new HorizontalLayout();
		horizLayout.setSpacing(true);
	
		final GridLayout toolbarGridLayout = new GridLayout(1,2);
		List<com.vaadin.event.MouseEvents.ClickListener> listeners = new ArrayList<com.vaadin.event.MouseEvents.ClickListener>();
		listeners.add(new AddImageButtonListener(tabSheet, this));
		listeners.add(new AddLibraryButtonListener(helper));
		//listeners.add(new DocumentEditListener(documentTab, documentForm, documentTable, documentId));
		
		final ScreenToolbarBuilder builder = new ScreenToolbarBuilder(toolbarGridLayout,"imgMgmt",listeners);
		builder.build();
		
		horizLayout.addComponent(verticall);
		horizLayout.setWidth(100,Unit.PERCENTAGE);
		
		/* Button to add library */
		
		mainLayout.addComponent(horizLayout);
		horizLayout.setSpacing(true);
		
		/* image library combo*/
		//Get accountId from the session
        final Integer accountId = (Integer)SessionHelper.loadAttribute("accountId");
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
					Notification.show("Please select library from the list to search.",Notification.Type.TRAY_NOTIFICATION);
				}
			}
		});

	    final Button deleteLibraryButton = new Button("Delete Library");
	    deleteLibraryButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(final ClickEvent event) {
				Object id = imageLibrayCombo.getValue();
				if(id != null){
					int libraryId = Integer.parseInt(id.toString());
					final ImageLibraryDto imageLibrary = imageLibraryService.findImageLibraryById(libraryId);
					try {
						imageLibraryService.delete(imageLibrary);

						final Collection<ImageLibraryDto> imageLibraryDto = imageLibraryService.findImageLibraryByAccountId(accountId);
					    imageLibrayCombo.setContainerDataSource(comboDataLoader.loadDataInContainer((Collection)imageLibraryDto ));

						Notification.show("Library " + imageLibrary.getName()+ " deleted successfully.",Notification.Type.TRAY_NOTIFICATION);
					} catch (final EntityCannotBeDeletedException e) {
						Notification.show("Library " + imageLibrary.getName()+" cannot be deleted.",Notification.Type.TRAY_NOTIFICATION);
					}
				}else{
					Notification.show("Please select library to delete.",Notification.Type.TRAY_NOTIFICATION);
				}
			}
		});

	    horiz.addComponent(searchButton);
	    horiz.addComponent(deleteLibraryButton);

	    horiz.setComponentAlignment(deleteLibraryButton, Alignment.BOTTOM_LEFT);
	    horiz.setComponentAlignment(searchButton, Alignment.BOTTOM_LEFT);
	    verticall.addComponent(horiz);
	    
	    HorizontalLayout subHeadingLayout = new HorizontalLayout();
	    subHeadingLayout.addComponent(new Label());
	    verticall.addComponent(subHeadingLayout);
	    verticall.setMargin(true);
	    verticall.setSpacing(true);
	    verticall.addComponent(imagePanlelayout);
	    horizLayout.addComponent(toolbarGridLayout);
	    horizLayout.setExpandRatio(toolbarGridLayout, 1);
	    horizLayout.setExpandRatio(verticall, 8);
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
//        final CssLayout itemsArea = new CssLayout();
		imagePanlelayout.setSizeUndefined();
        
        try {

			final CachedTypedProperties languageProperties = CachedTypedProperties.getInstance("paging.properties");
			int NmbrOfImagesOnPage = languageProperties.getIntProperty("NumberOfImages");
			
			for(ImageDto dto: images){
				imagePanlelayout.addComponent(addImagesToPanel(dto));
			}
//	        imagePanlelayout.addComponent(itemsArea);
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

		VerticalLayout imageLayout = new VerticalLayout();
    	Embedded embedded = loadImage(dto,75,75);

    	embedded.setDescription(dto.getName());
    	imageLayout.addComponent(embedded);
    	imageLayout.setComponentAlignment(embedded, Alignment.MIDDLE_CENTER);

    	final VerticalLayout imageInfoLayout = new VerticalLayout();

    	//Edit image button
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
    	imageInfoLayout.addComponent(editImageDetail);
    	imageInfoLayout.setComponentAlignment(editImageDetail, Alignment.MIDDLE_CENTER);
    	
    	//Delete image button
    	Button btnDelete = new Button("Delete Image");
    	btnDelete.setStyleName("link");
    	btnDelete.addClickListener(new ImageDeleteListner(helper, dto, this));
    	
    	imageInfoLayout.addComponent(btnDelete);
    	imageInfoLayout.setComponentAlignment(btnDelete, Alignment.MIDDLE_CENTER);
    	
    	final Panel mainPanel = new Panel();
    	final VerticalLayout mainPanelLayout = new VerticalLayout();
    	
    	mainPanelLayout.addComponent(imageLayout);
    	mainPanelLayout.addComponent(imageInfoLayout);
    	mainPanel.setContent(mainPanelLayout);
    	mainPanel.setHeight("150");
    	mainPanel.setWidth("150");
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
	public Component renderAddEditScreen(final String command,final ImageDto dto){
		root = new Panel("");
		rootContentLayout = new VerticalLayout();
		root.setContent(rootContentLayout);
		rootContentLayout.setSizeFull();
		rootContentLayout.setHeight(100,Unit.PERCENTAGE);
		
		this.caption = command;
		this.imageDto = dto;
		VerticalLayout imageLayout = new VerticalLayout();
		imageLayout.setSpacing(true);
		altTextField = new TextField();
		altTextField.setColumns(25);
		altTextField.setCaption("Alt text");
	
		imageNameField = new TextField();
		imageNameField.setColumns(25);
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

        // Create a panel for displaying the uploaded image.
        imagePanel = new Panel("Uploaded image");
        imagePanelContent = new VerticalLayout();
        
        imagePanel.setContent(imagePanelContent);
        final Label noImgLable = new Label("No image uploaded yet");
        imagePanelContent.addComponent(noImgLable);
        
        rootContentLayout.addComponent(imagePanel);

        final ScreenHeader screenHeader = new ScreenHeader(imageLayout,"Image");
        
        imageLayout.setSpacing(true);
        imageLayout.setMargin(true);
		final HorizontalLayout imageInfoLayout = new HorizontalLayout();
		imageInfoLayout.setSpacing(true);

		imageInfoLayout.addComponent(imageNameField);
		imageInfoLayout.addComponent(altTextField);
		
		final HorizontalLayout imageUploadLayout = new HorizontalLayout();
		imageUploadLayout.addComponent(imageLibrayCombo);
		imageUploadLayout.addComponent(upload);
		imageUploadLayout.setSpacing(true);
		
		imageLayout.addComponent(imageInfoLayout);
		imageLayout.addComponent(imageUploadLayout);

		imageLayout.addComponent(root);
		final Button saveButton = new Button("Save");

	     if(this.caption.equals("Edit")){
	         	imagePanelContent.removeComponent(noImgLable);
	         	imagePanelContent.setWidth("160");
	    	 	imagePanelContent.addComponent(loadImage(imageDto,null,null));
				altTextField.setValue(this.imageDto.getAltText());
				imageNameField.setValue(this.imageDto.getName());
				imageLibrayCombo.setValue(this.imageDto.getImageLibraryDto().getId());
	     }
	     
	     saveButton.addClickListener(new ClickListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(final ClickEvent event) {
						boolean isValidated = true;
						if (StringUtils.isEmpty(altTextField.getValue().toString())){
				        	Notification.show("Alt text cannot be empty",Notification.Type.TRAY_NOTIFICATION);
				        	isValidated = false;
						}
						if (StringUtils.isEmpty(imageNameField.getValue().toString())){
							Notification.show("Image name cannot be empty",Notification.Type.TRAY_NOTIFICATION);
							isValidated = false;
						}
						if (imageLibrayCombo.getValue()==null){
							Notification.show("Please select library ",Notification.Type.TRAY_NOTIFICATION);
							isValidated = false;
						}
						
						if (isValidated){
					     if(imageDto!=null && imageDto.getId()!=null){
	
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
							imageDto = null;
						}
					    else {
					    	
					    	if (bFile==null){
								Notification.show("No file is uploaded.Please select file to upload ",Notification.Type.TRAY_NOTIFICATION);
								isValidated = false;
							}
					    	else if (imageDto!=null){
					    	 imageDto.setAltText(altTextField.getValue().toString());
					         imageDto.setImage(bFile);
					       
					         imageDto.setName(imageNameField.getValue().toString());
					
					         //Get accountId from the session
					         final AccountDto accountDto = new AccountDto();
					         accountDto.setAccountId(accountId);
					         imageDto.setAccountDto(accountDto);
					         imageDto.setFile(file);
					       
					         final ImageService imageService = (ImageService)helper.getBean("imageService");
					         
					         //set imageLibrary to imageDto
					         if(imageLibrayCombo.getValue()!=null){
					         	imageDto.setImageLibraryDto(imageLibraryService
					         			.findImageLibraryById(Integer
					         					.parseInt(imageLibrayCombo.getValue()
					         							.toString())));
					         }
					         
					         imageDto.setSiteDto(new ArrayList<SiteDto>());
						        Boolean isImageSave;
								try {
									isImageSave = imageService.create(imageDto);
									
							        if (isImageSave){
							        	Notification.show("Image uploaded successfully",Notification.Type.TRAY_NOTIFICATION);
							        }
							        else {
							        	Notification.show("Image not uploaded successfully.If this persist please contact support team.",Notification.Type.WARNING_MESSAGE);
							        }

								} catch (EntityAlreadyFoundException e) {
						        	Notification.show("Image with name already exist",Notification.Type.WARNING_MESSAGE);
								}
					     }
						    imageDto = null;
					    }	
					}
				}	
			});
	     
	    file = null; 
		imageLayout.addComponent(saveButton);       
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
    
    /**
     * Type validation On image uploading
     * @param mimeType
     * @return
     */
    private boolean validateImage(String mimeType) {
    	boolean isSupported = false;
    	try {
			final CachedTypedProperties typeProperties = CachedTypedProperties.getInstance("imageType.properties");
			isSupported = typeProperties.containsValue(mimeType);
  
    	} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	return isSupported;
    }
     
    // This is called if the upload is finished.
    public void uploadSucceeded(Upload.SucceededEvent event) {
       
    	if(!validateImage(event.getMIMEType())) {
        	Notification.show(MSG_FILE_TYPE_NOT_SUPPORTED, Notification.Type.TRAY_NOTIFICATION);
    	} else {
    	// Log the upload on screen.
        root.setContent(new Label(String.format("File %s of type ' %s ' uploaded.",event.getFilename(),event.getMIMEType())));
        imageResource = new FileResource(file);
        
        imagePanel.setContent(new Embedded("", imageResource));
        imageResource.setCacheTime(0);
        
        FileInputStream fis = null;
    	bFile = new byte[(int) file.length()];
 		try {
 			fis = new FileInputStream(file);
 			
 			fis.read(bFile);
 			if(caption.equals("Add")){
 		        imageDto = new ImageDto();
 			}
 				if(bFile != null)
 					this.imageDto.setImage(bFile);
 		} 
 		catch (final FileNotFoundException e) {
 			LOGGER.error("Unable to upload the image.",e);
 		} 
        catch(final IOException ioe){
			LOGGER.error("Unable to create the image.",ioe);
		}
 		finally{
 			try {
				fis.close();
			} catch (IOException e) {
	 			LOGGER.fatal("Unable to clost the fileinput stream in finally while uploading an image.",e);
			}
 		}

        imagePanelContent.removeAllComponents();
	    imagePanel.setSizeFull();
	    imageResource = new FileResource(file);
	    imagePanel.setContent(new Embedded("", imageResource));
	    imageResource.setCacheTime(0);
    	}
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
	    	imagePanelContent.addComponent(loadImage(dto,null,null));
	    	imagePanel.setContent(imagePanelContent);
	    	
	    	final VerticalLayout imageLayout = new VerticalLayout();
	    	imageLayout.addComponent(imagePanel);
	    	imagePanel.setHeight("25");
	    	imagePanel.setWidth("25");
	    	imagePanel.setSizeUndefined();
	    	final VerticalLayout imageInfoLayout = new VerticalLayout();

	    	final Button editImageDetail = new Button("Edit Image",new ImageEditListner(helper,dto));
	    	editImageDetail.setStyleName("link");

	    	final Button imageDelete = new Button("Delete Image",new ImageDeleteListner(helper, dto, this));
	    	imageDelete.setStyleName("link");

	    	imageInfoLayout.setSpacing(true);
	    	
	    	imageInfoLayout.addComponent(editImageDetail);
	    	imageInfoLayout.setComponentAlignment(editImageDetail, Alignment.MIDDLE_CENTER);

	    	final Panel mainPanel = new Panel();
	    	mainPanel.setContent(imageLayout);
	    	mainPanel.setContent(imageInfoLayout);

	    	mainPanel.setHeight("150");
	    	mainPanel.setWidth("180");
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
	public Embedded loadImage(final ImageDto imageDto,final Integer height,final Integer width) {
		final ImageLoader imageLoader = new ImageLoader();
		final Embedded embedded = imageLoader.loadImage(imageDto.getImage(), height, width);
		return embedded;
	}

	/**
	 * Refresh Image panel
	 */
	private void refreshImagePanel() {
		
		imagePanlelayout.removeAllComponents(); // remove items from CSSlayout which contains panels of image
		Object id = imageLibrayCombo.getValue();
		if(id != null){
			int libraryId = Integer.parseInt(id.toString());
			Collection<ImageDto> images = imageService.findImagesByLibrary(libraryId);
			displayImages(images);
		}
	}
	
	/**
	 *On Delete handler
	 */
	@Override
	public void onDelete() {
		
		refreshImagePanel();
	}


}
