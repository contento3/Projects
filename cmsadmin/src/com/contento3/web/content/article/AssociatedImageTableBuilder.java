package com.contento3.web.content.article;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.common.dto.Dto;
import com.contento3.dam.image.dto.ImageDto;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.ImageViewer;
import com.contento3.web.content.article.listener.ArticleAsscContentScopeListener;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

public class AssociatedImageTableBuilder extends AbstractTableBuilder  {

	/**
	 *  Reference to main window
	 */
	private final Window mainWindow;
	/**
	 * Used to get service beans from spring context.
	 */
	private SpringContextHelper helper;
	/**
	 * Article that contain related images and content scope
	 */
	private final ArticleDto article;
	
	/**
	 * Constructor
	 * @param mainWindow
	 * @param helper
	 * @param article
	 * @param table
	 */
	public AssociatedImageTableBuilder(final Window mainWindow,final SpringContextHelper helper,final ArticleDto article,Table table) {
		super(table);
		this.mainWindow = mainWindow;
		this.helper = helper;
		this.article = article;
	}

	/**
	 * Assign images to table
	 */
	@Override
	public void assignDataToTable(Dto dto, Table assImagetable, Container imageContainer) {
		ImageDto image = (ImageDto) dto;
		Item item = imageContainer.addItem(image.getId());
		
		/*Content scope link button*/
		Button imageLink = new Button("View Image",new ImageViewer(this.mainWindow,image));
		imageLink.setCaption(image.getName());
		imageLink.setData(image.getId());
		imageLink.addStyleName("view");
		imageLink.setStyleName(BaseTheme.BUTTON_LINK);
		item.getItemProperty("images").setValue(imageLink);
		
		/*Content scope link button*/
		Button contentScopeLink = new Button("Scope link",new ArticleAsscContentScopeListener(this.mainWindow,this.helper,this.article,image));
		contentScopeLink.setCaption("view");
		contentScopeLink.setData(image.getId());
		contentScopeLink.addStyleName("view");
		contentScopeLink.setStyleName(BaseTheme.BUTTON_LINK);
		item.getItemProperty("content scope").setValue(contentScopeLink);
	}

	/**
	 * Build header of table
	 */
	@Override
	public void buildHeader(Table imageTable, Container imageContainer) {
		imageContainer.addContainerProperty("images", Button.class, null);
		imageContainer.addContainerProperty("content scope", Button.class, null);
		//imageContainer.addContainerProperty("view", Button.class, null);
		imageTable.setWidth(100, Unit.PERCENTAGE);
		imageTable.setContainerDataSource(imageContainer);
	}

	/**
	 * Build empty table
	 */
	@Override
	public void buildEmptyTable(Container imageContainer) {
		Item item = imageContainer.addItem("-1");
		item.getItemProperty("images").setValue("No record found.");
	}

}
