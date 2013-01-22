package com.contento3.web.content.document.listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import com.vaadin.terminal.FileResource;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Window;

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
        fos = null; // Output stream to write to
        file = new File(filename);
        try {
            // Open the file for writing.
            fos = new FileOutputStream(file);
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
		} catch (final java.io.FileNotFoundException e) {
			LOGGER.error("Unable to create the file.",e);
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
    
    public byte[] getUploadedFile(){
    	return bFile;
    }
}
