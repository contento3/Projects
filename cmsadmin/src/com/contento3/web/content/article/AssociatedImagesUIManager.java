package com.contento3.web.content.article;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.dto.ArticleImageDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.dam.image.dto.ImageDto;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.Notification;

public class AssociatedImagesUIManager extends CustomComponent  implements ClickListener, Window.CloseListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Vertical layout for pop-up
	 */
	private VerticalLayout mainLayout;
	
	/**
	 * ArticleId for article related activity
	 */
	private Integer articleId;
		
	 /**
     * Represents the main window of the ui
     */
	private Window mainWindow;
	
	/**
	 * Used to get service beans from spring context.
	 */
	private SpringContextHelper helper;
	
	/**
	 * The window to be opened
	 */
	private Window popupWindow; 
	
	boolean isModalWindowClosable = true;
	/**
	 * ArticleDto use to get images
	 */
	private ArticleDto article;

	
	public AssociatedImagesUIManager(final Window mainWindow,final SpringContextHelper helper,final Integer articleId) {
	
		this.articleId = articleId;
		this.helper = helper;
		this.mainWindow = mainWindow;
		
	}

	@Override
	public void click(ClickEvent event) {
		ArticleService articleService = (ArticleService) this.helper.getBean("articleService");
		if (articleId == null) {
			mainWindow.showNotification("Opening failed","create article first", Notification.TYPE_WARNING_MESSAGE);
		} else {
			article = articleService.findById(this.articleId);
			if (article.getAssociateImagesDtos().isEmpty()) {
				mainWindow.showNotification("Opening failed", "no images assigned",Notification.TYPE_WARNING_MESSAGE);
			} else {
				renderAssocImagesPopup();
				renderAssociatedImageTable();
			}
		}
	}
	
	private void renderAssocImagesPopup() {
		 /* Create a new window. */
		this.mainLayout = new VerticalLayout();
		popupWindow = new Window();
		popupWindow.setPositionX(200);
    	popupWindow.setPositionY(100);
    	popupWindow.setHeight(60,Sizeable.UNITS_PERCENTAGE);
    	popupWindow.setWidth(35,Sizeable.UNITS_PERCENTAGE);

    	/* Add the window inside the main window. */
        mainWindow.addWindow(popupWindow);
        popupWindow.setModal(true);
        popupWindow.setCaption("Associate Images");
        mainLayout.setSpacing(true);
        popupWindow.addComponent(mainLayout);
        popupWindow.setResizable(false);
	}
	
	/**
	 * Build table for associated images and associated content scope
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void renderAssociatedImageTable(){
		Table table = new Table();
		AbstractTableBuilder associatesImagesTableBuilder = new AssociatedImageTableBuilder(this.mainWindow,this.helper,article,table);
		Collection<ArticleImageDto> articleImages = (Collection) article.getAssociateImagesDtos();
		Collection<ImageDto> associateImages = new ArrayList<ImageDto>();
		
		for(ArticleImageDto artImage:articleImages){
			if(!associateImages.contains(artImage.getImage()))
					associateImages.add(artImage.getImage());		
		}
		
		associatesImagesTableBuilder.build((Collection)associateImages);
		this.mainLayout.addComponent(table);
	}

	/**
	 * Handle Close button click and close the window.
	 */
	public void closeButtonClick(Button.ClickEvent event) {

		if (!isModalWindowClosable) {
			/* Windows are managed by the application object. */
			mainWindow.removeWindow(popupWindow);
		}
	}

	/**
	 * Window close event
	 */
	@Override
	public void windowClose(CloseEvent e) {
		// TODO Auto-generated method stub
	}

	
}
