package com.contento3.web.content.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.imgscalr.Scalr;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Embedded;

/**
 * Generic ImageLoader class to load images.
 * @author HAMMAD
 *
 */
public class ImageLoader {

	private static final Logger LOGGER = Logger.getLogger(ImageLoader.class);

	/**
	 * Load the page based on the path value 
	 * passed and returned as an embedded object
	 * @return Embedded
	 */
	public Embedded loadEmbeddedImageByPath(final String path){
	    ExternalResource externalResource = new ExternalResource(path);
		Embedded embedded = new Embedded("",externalResource);
		embedded.setImmediate(true);
		embedded.markAsDirty();
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
	public Embedded loadImage(final byte[] imageBytes,final Integer height,final Integer width) {
		StreamResource.StreamSource imageSource = new StreamResource.StreamSource() {
			private static final long serialVersionUID = 1L;

			@Override
			public InputStream getStream() {
				BufferedImage thumbnail;
				InputStream bigInputStream = null;
				try {
					final ByteArrayInputStream in = new ByteArrayInputStream(imageBytes);
					thumbnail = ImageIO.read(in);

					if (null!=width && null!=height)
					{	
						thumbnail = Scalr.resize(thumbnail,width,height);
					}
					
					final ByteArrayOutputStream os = new ByteArrayOutputStream();
					ImageIO.write(thumbnail, "gif", os);
					bigInputStream = new ByteArrayInputStream(os.toByteArray());
				}
				catch(final IOException ioe){
					LOGGER.fatal("Unable to load an image for display in cms",ioe);
				}
			return bigInputStream;
			}
			};
		
		final StreamResource imageResource = new StreamResource(imageSource, "abc.png");
		imageResource.setCacheTime(0);
		final Embedded embeded = new Embedded("",imageResource);
		embeded.setImmediate(true);
		embeded.markAsDirty();
		return embeded;
	}

}
