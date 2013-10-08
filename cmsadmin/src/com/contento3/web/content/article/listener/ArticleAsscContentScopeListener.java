package com.contento3.web.content.article.listener;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.dto.ArticleImageDto;
import com.contento3.cms.article.service.ArticleImageService;
import com.contento3.cms.content.dto.AssociatedContentScopeDto;
import com.contento3.dam.image.dto.ImageDto;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.content.article.AsscContentScopeTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

public class ArticleAsscContentScopeListener extends CustomComponent implements Window.CloseListener,Button.ClickListener{

	private static final long serialVersionUID = 1L;
	
	/**
	 *  Reference to main window
	 */
	private Window mainWindow; 
	
	/**
	 * The window to be opened
	 */
	private Window popupWindow; 
	
	/**
	 * Button for opening the window
	 */
	private Button openbutton; 
	
	/**
	 * Vertical layout of pop-up
	 */
	private VerticalLayout popupMainLayout;
	
	/**
	 * Used to get service beans from spring context.
	 */
	private SpringContextHelper helper;
	
	/**
	 * Article that contain associated content scope
	 */
	private final ArticleDto article;
	
	/**
	 *Image use to find related content scope
	 */
	private final ImageDto image;
	
	boolean isModalWindowClosable = true;
	
	/**
	 * Constructor
	 * @param mainWindow
	 * @param helper
	 * @param article
	 * @param image
	 */
	public ArticleAsscContentScopeListener(final Window mainWindow,final SpringContextHelper helper,final ArticleDto article,final ImageDto image) {
		this.mainWindow = mainWindow;
		this.helper = helper;
		this.article = article;
		this.image = image;
		
		// The component contains a button that opens the window.
        final VerticalLayout layout = new VerticalLayout();
        openbutton = new Button("Add Group");
        openbutton.addClickListener(this);
        layout.addComponent(openbutton);
        setCompositionRoot(layout);
	}
	
	 /** 
	   * Handle the click event.
	   */
	public void openButtonClick(Button.ClickEvent event) {
		popupWindow = new Window();
		popupWindow.setPositionX(200);
    	popupWindow.setPositionY(100);
    	popupWindow.setHeight(41,Unit.PERCENTAGE);
    	popupWindow.setWidth(20,Unit.PERCENTAGE);
       
    	/* Add the window inside the main window. */
    	popupMainLayout.addComponent(popupWindow);
        
        /* Listen for close events for the window. */
        popupWindow.addCloseListener(this);
        popupWindow.setModal(true);
        popupWindow.setCaption("Content scope");
        popupMainLayout = new VerticalLayout();
        popupWindow.setContent(popupMainLayout);
        popupWindow.setResizable(false);
        
        /* Allow opening only one window at a time. */
        openbutton.setEnabled(false);
        buildAssociateContentScopeTable();
	}
	
	/**
	 * Build Table of associated content scope
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void buildAssociateContentScopeTable(){
		ArticleImageService service = (ArticleImageService) this.helper.getBean("articleImageService");
		Collection<ArticleImageDto> artImage = service.findAsscArticleImageById(this.article.getId(), image.getId());
		Collection<AssociatedContentScopeDto> contentScope = new ArrayList<AssociatedContentScopeDto>();
		for(ArticleImageDto dto:artImage){
			contentScope.add(dto.getContentScope());
		}
		Table table = new Table();
		AbstractTableBuilder tableBuilder = new AsscContentScopeTableBuilder(table);
		tableBuilder.build((Collection)contentScope);
		this.popupMainLayout.addComponent(table);	
	}
	
	  /**
	   *  Handle Close button click and close the window.
	   */
	    public void closeButtonClick(Button.ClickEvent event) {
	    	if (!isModalWindowClosable){
	        /* Windows are managed by the application object. */
	    	popupMainLayout.addComponent(popupWindow);
	        
	        /* Return to initial state. */
	        openbutton.setEnabled(true);
	    	}
	    }

	    /**
	     * Handle window close event
	     */
		@Override
		public void windowClose(CloseEvent e) {
			  /* Return to initial state. */
	        openbutton.setEnabled(true);
		}

		@Override
		public void buttonClick(ClickEvent event) {
			this.openButtonClick(event);			
		}

}
