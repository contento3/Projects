package com.contento3.web.content.document.listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.terminal.FileResource;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Window;

/**
 * @author Syed Muhammad Ali
 * Generic class to facilitate uploads through the
 * upload field.
 */
public class FileUploadListener
	implements Upload.SucceededListener,
				Upload.FailedListener,
				Upload.Receiver
{
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger(FileUploadListener.class);

	/**
     * Represents the parent window of the ui
     */
	private Window parentWindow;
	
    /**
     * FileOutputStream used in file upload.
     */
    FileOutputStream fos ;

    /**
     * File to write to.
     */
    File  file;

    /**
     * File Resource for file
     */
    FileResource fileResource;
    
    /**
     * Document stored as a byte array
     */
    byte[] bFile;
    
    Upload upload;
    
   //Constructor
    public FileUploadListener(Window parentWindow){
    	this.parentWindow = parentWindow;
    }
    
    /**
     * Callback method to begin receiving the upload.
     * @param filename
     * @param MIMEType
     * @return
     */
    public OutputStream receiveUpload(final String filename,final String MIMEType) {
    	if(bFile != null){
    		/**
    		 * Interrupt upload is necessary, otherwise the execution of receriveUpload
    		 * will continue because the web is stateless. The server can not wait for a
    		 * response from the client. Therefore, if the user confirms, the upload event
    		 * will be re fired.
    		 * 
    		 * - Syed Muhammad Ali
    		 */
    		
    		ConfirmDialog.show(parentWindow, "Please Confirm", "This is overwrite the previously uploaded file. Are you sure you want to proceed?", "Yes", "No", new ConfirmDialog.Listener() {
				
				@Override
				public void onClose(ConfirmDialog dialog) {
					if(dialog.isConfirmed()){
						//Refire the event
						bFile = null;
						receiveUpload(filename, MIMEType);
					} else {
						//ignore
					}
				}
			});
    		
    		upload.interruptUpload();
    	}
    	
    	if(upload != null && upload.getUploadSize() > 10000000){
    		parentWindow.showNotification("The file size must be small than 10MB");
    		upload.interruptUpload();
    	}
    	
    	System.err.println("Passing through.");
    	
        fos = null; // Output stream to write to
        file = new File(filename);
        try {
            fos = new FileOutputStream(file); // Open the file for writing.
        } catch(Exception e){
        	LOGGER.error("Unable to upload the file", e);
        }
        
        return fos; // Return the output stream to write to
    }
    
    /**
     * This is called if the upload is finished.
     */
    public void uploadSucceeded(Upload.SucceededEvent event) {
        // Log the upload on screen.
        parentWindow.showNotification(String.format("File %s of type ' %s ' uploaded.",event.getFilename(),event.getMIMEType()));
        fileResource = new FileResource(file, parentWindow.getApplication());
        
        bFile = new byte[(int) file.length()];
        FileInputStream fis = null;
    	
 		try {
 			fis = new FileInputStream(file);
 		} catch (FileNotFoundException e) {
 			LOGGER.error("Unable to upload the file.",e);
 		}
 		
        try {
 			fis.read(bFile); //success
		} catch(IOException ioe) {
			LOGGER.error("Unable to create the file.",ioe);
		}
	}

    /**
     * This is called if the upload fails.
     */
    public void uploadFailed(Upload.FailedEvent event) {
        parentWindow.showNotification( String.format("Uploading of %s of type %s failed.",event.getFilename(),event.getMIMEType()) );
    }
    
    /**
     * Return the buffer that holds the contents
     * of the file that was uploaded by the user.
     */
    public byte[] getUploadedFile(){
    	return bFile;
    }
    /**
     * This allows setting the internal file buffer to a file
     * in case a upload is being staged, and has not happened
     * actually.
     */
    public void setUploadedFile(byte[] uploadedFile){
    	bFile = uploadedFile;
    }
    
    public void setUpload(Upload upload){ this.upload = upload; }
}
