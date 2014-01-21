package com.contento3.dam.content.storage;

import java.io.File;

import com.contento3.dam.image.library.model.ImageLibrary;

public interface Storable {

	/**
	 * Storable must link to a library
	 * @return ImageLibrary
	 */
	ImageLibrary getLibrary();
	
	/**
	 * Gets the file to be stored
	 * @return
	 */
	File getFile();
	
	/**
	 * Sets the file to be stored
	 * @param file
	 */
	void setFile(File file);
}
