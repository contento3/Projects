package com.contento3.web.site.listener;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.contento3.cms.page.exception.PageNotFoundException;
import com.contento3.cms.page.service.PageService;
import com.contento3.util.CachedTypedProperties;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.EntityListener;
import com.contento3.web.content.article.AssociatedCategoryTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

/*
 * Listener used on the Page edit/add screen and 
 * displays the associated categories to the page
 */
public class PageViewCategoryListener extends EntityListener implements Window.CloseListener, com.vaadin.event.MouseEvents.ClickListener {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = Logger.getLogger(PageViewCategoryListener.class);

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
	 * Page Id 
	 */
	private Integer pageId;
	
	/**
	 * Vertical layout to add components
	 */
	private VerticalLayout vLayout;
	
	boolean isModalWindowClosable = true;
	
	/**
	 * Table for associated category
	 */
	private Table table;
	
	private PageService pageService;
	
	/**
	 * Constructor
	 * @param parentWindow
	 * @param article
	 */
	public PageViewCategoryListener(final Integer pageId,final SpringContextHelper contextHelper) {
		this.pageId = pageId;
		pageService = (PageService)contextHelper.getBean("pageService");
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

	        UI.getCurrent().addWindow(popupWindow);

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
	public void closeButtonClick(ClickEvent event) {

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

	@Override
	public void click(ClickEvent event) {
		this.vLayout = new VerticalLayout();
		this.table = new Table();
		AbstractTableBuilder categoryTable = new AssociatedCategoryTableBuilder(table);
		try {
			categoryTable.build((Collection)pageService.findById(pageId).getCategories());
		} catch (PageNotFoundException e) {
			LOGGER.error("Something went wrong while we fetch categories for page with id"+pageId);
		}
		vLayout.addComponent(table);
		renderPopUp();
	}


}

