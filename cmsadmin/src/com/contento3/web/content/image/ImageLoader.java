package com.contento3.web.content.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.imgscalr.Scalr;

import com.vaadin.Application;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.StreamResource;
import com.vaadin.ui.Embedded;

/**
 * Generic ImageLoader class to load images.
 * @author HAMMAD
 *
 */
public class ImageLoader {

	/**
	 * Load the page based on the path value 
	 * passed and returned as an embedded object
	 * @return Embedded
	 */
	public Embedded loadEmbeddedImageByPath(final String path){
	    ExternalResource externalResource = new ExternalResource(path);
		Embedded embedded = new Embedded("",externalResource);
		embedded.setImmediate(true);
		embedded.requestRepaint();
		return embedded;
	}
	
	/**
	 * Loads image based on width and height provided.
	 * @param application
	 * @param imageBytes
	 * @param width
	 * @param height
	 * @return
	 */
	public Embedded loadImage(final Application application,final byte[] imageBytes,final Integer width,final Integer height) {
		StreamResource.StreamSource imageSource = new StreamResource.StreamSource() {
			@Override
			public InputStream getStream() {
				final BufferedImage thumbnail;
				InputStream bigInputStream = null;
				try {
					final ByteArrayInputStream in = new ByteArrayInputStream(imageBytes);
					final ImageReader imageReader;
			        final Object source = in; // File or InputStream, it seems file is OK
			        final ImageInputStream iis = ImageIO.createImageInputStream(source);
					final Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);

					if ( imageReaders.hasNext() ) {
					    imageReader = (ImageReader)imageReaders.next();
					    imageReader.setInput(iis, true);
					    final ImageReadParam param = imageReader.getDefaultReadParam();
						final BufferedImage bImageFromConvert = imageReader.read(0, param);
						thumbnail = Scalr.resize(bImageFromConvert,width,height);
						final ByteArrayOutputStream os = new ByteArrayOutputStream();
						ImageIO.write(thumbnail, "gif", os);
						bigInputStream = new ByteArrayInputStream(os.toByteArray());
					}
				}
				catch(IOException ioe){
				}
			return bigInputStream;
			}
			};
		
		final StreamResource imageResource = new StreamResource(imageSource, "abc.png", application);
		imageResource.setCacheTime(0);
		final Embedded embeded = new Embedded("",imageResource);
		embeded.setImmediate(true);
		embeded.requestRepaint();
		return embeded;
	}

}
