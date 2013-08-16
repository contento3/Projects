 package com.contento3.web.content.article.listener;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.util.CachedTypedProperties;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.content.article.AssociatedCategoryTableBuilder;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

public class AssociatedCategoryClickListener extends CustomComponent implements Window.CloseListener, ClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = Logger.getLogger(AssociatedCategoryClickListener.class);

	/**
	 * The window to be opened
	 */
	Window popupWindow; 

	/**
	 * Button for opening the window
	 */
	Button openbutton; 

	/**
	 *  A button in the window
	 */
	Button closebutton; 
	
	/**
	 * Article Dto 
	 */
	private ArticleDto article;
	
	/**
	 * Vertical layout to add components
	 */
	private VerticalLayout vLayout;
	
	boolean isModalWindowClosable = true;
	
	/**
	 * Table for associated category
	 */
	private Table table;
	
	/**
	 * Constructor
	 * @param parentWindow
	 * @param article
	 */
	public AssociatedCategoryClickListener(final ArticleDto article) {
		this.article = article;
	}
	
	/**
	 * Button click handler
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void buttonClick(ClickEvent event) {
		this.vLayout = new VerticalLayout();
		this.table = new Table();
		AbstractTableBuilder categoryTable = new AssociatedCategoryTableBuilder(table);
		categoryTable.build((Collection)article.getCategoryDtos());
		vLayout.addComponent(table);
		renderPopUp();
	}
	
	/**
	 * Render pop-up screen
	 */
	public void renderPopUp() {
		
		String pageLength = "10"; //default
		String width = "43"; //default
		String height = "60"; //default
		try {
			final CachedTypedProperties languageProperties = CachedTypedProperties.getInstance("entityPickerConfigure.properties");
			width = languageProperties.getProperty("width");
			height = languageProperties.getProperty("height");
			pageLength = languageProperties.getProperty("tablePageLength");
		} catch (ClassNotFoundException e) {
			LOGGER.error("Unable to read entityPickerConfigure.properties,Reason:"+e);
		}
		
			table.setPageLength(Integer.parseInt(pageLength));
	        /* Create a new window. */
			popupWindow = new Window();
			popupWindow.setCaption("Associated Category");
			popupWindow.setPositionX(200);
	    	popupWindow.setPositionY(100);
	    	popupWindow.setHeight(Integer.parseInt(height)-10,Unit.PERCENTAGE);
	    	popupWindow.setWidth(Integer.parseInt(width),Unit.PERCENTAGE);

	    	/* Add the window inside the main window. */
	        vLayout.addComponent(popupWindow);

	        /* Listen for close events for the window. */
	        popupWindow.addCloseListener(this);
	        popupWindow.setModal(true);
	        vLayout.setSpacing(true);
	        popupWindow.setContent(vLayout);
	        popupWindow.setResizable(false);
	    }

	/**
	 * Handle Close button click and close the window.
	 */
	public void closeButtonClick(Button.ClickEvent event) {

		if (!isModalWindowClosable) {
			/* Windows are managed by the application object. */
			vLayout.removeComponent(popupWindow);
		}
	}

	/**
	 * Window close handler
	 */
	@Override
	public void windowClose(CloseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
