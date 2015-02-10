package com.contento3.web.common.helper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.util.CollectionUtils;

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
	private final Map<String, ClickListener> listenersMap;
	
	/**
	 * Constructor
	 * @param toolbarGridLayout
	 * @param toolbarName
	 * @param listeners
	 */
	public ScreenToolbarBuilder(final GridLayout toolbarGridLayout,final String toolbarName,final Map<String, ClickListener> listenersMap){
		this.gridLayout = toolbarGridLayout;
		this.toolbarName = toolbarName;
		this.listenersMap = listenersMap;
		
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
		if (!CollectionUtils.isEmpty(listenersMap)){
			final int totalRows = gridLayout.getRows();
			int count = 0;
	
			final List<String> imagePaths = properties.getDelimetedProperty(toolbarName+".path", ",");
			final List<String> tooltips = properties.getDelimetedProperty(toolbarName+".tooltip", ",");
			
			final Set<String> listenerPermissions = listenersMap.keySet();
			boolean anyItemPermitted = false;
			for (String permission : listenerPermissions){
				if (count<totalRows && count<listenerPermissions.size()){
					if (SecurityUtils.getSubject().isPermitted(permission)){
						buildCell(imagePaths,tooltips,count,listenersMap.get(permission));
						anyItemPermitted = true;
					}
				}
				count++;
			}
			if (anyItemPermitted){
				gridLayout.addStyleName("bordertest");
				gridLayout.setWidth(35,Unit.PIXELS);
			} 
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
	private void buildCell(final List<String> imagePaths,final List<String> tooltips,final Integer count,final ClickListener listener){
		final ImageLoader imageLoader = new ImageLoader();
		final String path = "images/"+imagePaths.get(count);
		final String tooltip = tooltips.get(count);
		
	    final Embedded icon = imageLoader.loadEmbeddedImageByPath(path);
	    
	    if (null!=listener){
	    	icon.addClickListener(listener);
	    }
	    
	    icon.setDescription(tooltip);
	    if (count!=imagePaths.size()-1)
	    	icon.addStyleName("subComponent");
		
	    gridLayout.addComponent(icon);
		gridLayout.setComponentAlignment(icon, Alignment.TOP_CENTER);
	}
}