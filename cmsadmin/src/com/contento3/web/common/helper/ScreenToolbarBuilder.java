package com.contento3.web.common.helper;

import java.util.List;

import org.apache.log4j.Logger;

import com.contento3.util.CachedTypedProperties;
import com.contento3.web.content.image.ImageLoader;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;

/**
 * Builds the toolbar that is displayed on different screen.
 * The toolbar is built according to the information proivded.
 * @author HAMMAD
 *
 */
public class ScreenToolbarBuilder {
	
	private static final Logger LOGGER = Logger.getLogger(ScreenToolbarBuilder.class);

	/**
	 * Grid Layout that is the parent 
	 * container for the toolbar.
	 */
	private GridLayout gridLayout;
	
	/**
	 * Properties to read information 
	 * from the properties file
	 */
	private CachedTypedProperties properties;
	
	/**
	 * Identification of the toolbar so that we can 
	 * get the right values from the properties file.
	 */
	private final String toolbarName;
	
	/**
	 * Collection of listeners that will be 
	 * attached to each of the action in the toolbar
	 */
	private final List<ClickListener> listeners;
	
	/**
	 * Constructor
	 * @param toolbarGridLayout
	 * @param toolbarName
	 * @param listeners
	 */
	public ScreenToolbarBuilder(final GridLayout toolbarGridLayout,final String toolbarName,final List<ClickListener> listeners){
		this.gridLayout = toolbarGridLayout;
		this.toolbarName = toolbarName;
		this.listeners = listeners;
		
		try {
			properties = CachedTypedProperties.getInstance("screenToolbar.properties");
		} catch (ClassNotFoundException e) {
			LOGGER.error("Unable to read languages.properties,Reason:"+e);
		}
	}

	/**
	 * Builds the toolbar.
	 */
	public void build(){
		gridLayout.addStyleName("bordertest");
		gridLayout.setWidth(35,Unit.PIXELS);
		
		final int totalRows = gridLayout.getRows();
		int count = 0;

		final List<String> imagePaths = properties.getDelimetedProperty(toolbarName+".path", ",");
		final List<String> tooltips = properties.getDelimetedProperty(toolbarName+".tooltip", ",");

		while (count<totalRows && count<listeners.size()){
			ClickListener listener = listeners.get(count);
			buildCell("images/"+imagePaths.get(count),tooltips.get(count),listener);
			count++;
		}
	}
	
	/**
	 * Builds an individual 
	 * action/command/cell 
	 * in the toolbar.
	 * @param path Icon image path.
	 * @param tooltip Tooltip text for this command
	 * @param listener Listener that handles the action when the image is clicked
	 */
	private void buildCell(final String path,final String tooltip,final ClickListener listener){
		final ImageLoader imageLoader = new ImageLoader();
	    final Embedded icon = imageLoader.loadEmbeddedImageByPath(path);
	    
	    if (null!=listener){
	    	icon.addClickListener(listener);
	    }
	    
	    icon.setDescription(tooltip);
	    icon.addStyleName("subComponent");
		gridLayout.addComponent(icon);
		gridLayout.setComponentAlignment(icon, Alignment.TOP_CENTER);
	}
}