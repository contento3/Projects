 package com.contento3.web.content.article.listener;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.AuthorizationException;

import com.contento3.cms.article.service.ArticleService;
import com.contento3.util.CachedTypedProperties;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.EntityListener;
import com.contento3.web.content.article.AssociatedCategoryTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

public class AssociatedCategoryClickListener extends EntityListener implements Window.CloseListener, com.vaadin.event.MouseEvents.ClickListener {

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
	private Integer articleId;
	
	/**
	 * Vertical layout to add components
	 */
	private VerticalLayout vLayout;
	
	boolean isModalWindowClosable = true;
	
	/**
	 * Table for associated category
	 */
	private Table table;
	
	private ArticleService articleService;
	
	/**
	 * Constructor
	 * @param parentWindow
	 * @param article
	 */
	public AssociatedCategoryClickListener(final Integer articleId,final SpringContextHelper contextHelper) {
		this.articleId = articleId;
		articleService = (ArticleService)contextHelper.getBean("articleService");
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
		try
		{
		this.vLayout = new VerticalLayout();
		this.table = new Table();
		AbstractTableBuilder categoryTable = new AssociatedCategoryTableBuilder(table);
		categoryTable.build((Collection)articleService.findById(articleId).getCategoryDtos());
		vLayout.addComponent(table);
		renderPopUp();}
		catch(AuthorizationException ex){Notification.show("You are not permitted to view categories of articles");}
	}

}
