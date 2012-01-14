package com.contento3.web.content;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpSession;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountService;
import com.contento3.common.exception.EnitiyAlreadyFoundException;
import com.contento3.dam.image.dto.ImageDto;
import com.contento3.dam.image.service.ImageService;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.terminal.FileResource;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ImageMgmtUIManager extends CustomComponent implements Upload.SucceededListener,
											Upload.FailedListener,
											Upload.Receiver{

	private static final long serialVersionUID = 5131819177752243660L;

    private SpringContextHelper helper;
	private Window parentWindow;

    Panel root;         // Root element for contained components.
    Panel imagePanel;   // Panel that contains the uploaded image.
    File  file;         // File to write to.
    FileResource imageResource;

    TextField altTextField;
    
    public ImageMgmtUIManager(final SpringContextHelper helper,final Window parentWindow){
		this.helper = helper;
		this.parentWindow = parentWindow;
	}
    
    public Component renderAddScreen(){
		root = new Panel("Upload image");

		VerticalLayout imageLayout = new VerticalLayout();
		
		altTextField = new TextField();
		altTextField.setCaption("Alt text");
	
		imageLayout.addComponent(altTextField);
		
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
        imagePanel.addComponent(
                         new Label("No image uploaded yet"));
     //   root.addComponent(imagePanel);

        imageLayout.addComponent(root);
        return imageLayout;
	}
	
	// Callback method to begin receiving the upload.
    public OutputStream receiveUpload(String filename,
                                      String MIMEType) {
        FileOutputStream fos = null; // Output stream to write to
        file = new File(filename);
        try {
            // Open the file for writing.
            fos = new FileOutputStream(file);
        	byte[] bFile = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);
            fis.read();
            fis.close();
            
            ImageDto imageDto = new ImageDto();
            imageDto.setAltText(altTextField.getValue().toString());
            imageDto.setImage(bFile);
            imageDto.setName(filename);
            
            //Get accountId from the session
            WebApplicationContext ctx = ((WebApplicationContext) this.getApplication().getContext());
            HttpSession session = ctx.getHttpSession();
            Integer accountId = (Integer)session.getAttribute("accountId");

            AccountService accountService = (AccountService)helper.getBean("accountService");
            AccountDto accountDto = accountService.findAccountById(accountId);
            
            ImageService imageService = (ImageService)helper.getBean("imageService");
            imageDto.setAccountDto(accountDto);
            try {//TODO
				imageService.create(imageDto);
			} catch (EnitiyAlreadyFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        catch (final java.io.FileNotFoundException e) {
            // Error while opening the file. Not reported here.
            e.printStackTrace();
            return null;
        }
        catch(IOException ioe){
            ioe.printStackTrace();
            return null;
        }

        return fos; // Return the output stream to write to
    }
    
    // This is called if the upload is finished.
    public void uploadSucceeded(Upload.SucceededEvent event) {
        // Log the upload on screen.
        root.addComponent(new Label("File " + event.getFilename()
                + " of type '" + event.getMIMEType()
                + "' uploaded."));

    imagePanel.removeAllComponents();
    imagePanel.setSizeFull();
    }

    // This is called if the upload fails.
    public void uploadFailed(Upload.FailedEvent event) {
        // Log the failure on screen.
        root.addComponent(new Label("Uploading "
                + event.getFilename() + " of type '"
                + event.getMIMEType() + "' failed."));
    }
    
    @Override
    public void attach() {
        super.attach(); // Must call.

        // Display the uploaded file in the image panel.
       imageResource =
                new FileResource(file, this.getApplication());
    //   imagePanel.addComponent(new Embedded("", imageResource));
       imageResource.setCacheTime(0);
    }

}
