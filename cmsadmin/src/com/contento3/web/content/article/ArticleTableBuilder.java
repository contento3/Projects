package com.contento3.web.content.article;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.common.dto.Dto;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.content.article.listener.ArticleDeleteClickListner;
import com.contento3.web.content.article.listener.ArticleFormBuilderListner;
import com.contento3.web.content.article.listener.AssociatedCategoryClickListener;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

public class ArticleTableBuilder extends AbstractTableBuilder {


	/**
	 * Helper to get the spring bean
	 */
	final SpringContextHelper contextHelper;

	 /**
     * Represents the parent window of the ui
     */
	final Window window;

	/**
	 * TabSheet serves as the parent container for the article manager
	 */
	private TabSheet tabSheet;

	/**
	 * Article service used for article related operations
	 */
	final ArticleService articleService;

	/**
	 * Constructor
	 * @param window
	 * @param helper
	 * @param table
	 */
	
	

	
	
	public ArticleTableBuilder(final Window window,final SpringContextHelper helper,final TabSheet tabSheet,final Table table) {
		super(table);
		this.contextHelper = helper;
		this.window = window;
		this.tabSheet = tabSheet;
		this.articleService = (ArticleService) contextHelper.getBean("articleService");
	}

	
    
     
	/**
	 * Assign data to table
	 * @param dto
	 * @param articleTable
	 * @param articleContainer
	 */
	@Override
	public void assignDataToTable(final Dto dto,final Table articleTable,final Container articleContainer) {
		ArticleDto article = (ArticleDto) dto;
		Item item = articleContainer.addItem(article.getId());
		item.getItemProperty("articles").setValue(article.getHead());
		String DATE_FORMAT = "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		item.getItemProperty("date created").setValue(sdf.format(article.getDateCreated()));
		item.getItemProperty("date posted").setValue(sdf.format(article.getDatePosted()));

		Date expiryDate = article.getExpiryDate();
		if (null!=expiryDate){
			item.getItemProperty("expiry date").setValue(sdf.format(expiryDate));
		}
		
		Button editLink = new Button();
		editLink.setCaption("Edit");
		editLink.setData(article.getId());
		editLink.addStyleName("edit");
		editLink.setStyleName(BaseTheme.BUTTON_LINK);
		editLink.addClickListener(new ArticleFormBuilderListner(this.contextHelper,this.tabSheet,articleTable));
		item.getItemProperty("edit").setValue(editLink);
		
		Button deleteLink = new Button();
		deleteLink.setCaption("Delete");
		deleteLink.setData(article.getId());
		deleteLink.addStyleName("delete");
		deleteLink.setStyleName(BaseTheme.BUTTON_LINK);
		item.getItemProperty("delete").setValue(deleteLink);
		deleteLink.addClickListener(new ArticleDeleteClickListner(article, articleService, deleteLink, articleTable));
	}

	/**
	 * Build header for table
	 */
	@Override
	public void buildHeader(final Table articleTable,final Container articleContainer) {
		articleContainer.addContainerProperty("articles", String.class, null);
		articleTable.setColumnExpandRatio("articles", 30);
		articleContainer.addContainerProperty("date created", String.class, null);
		articleContainer.addContainerProperty("date posted", String.class, null);
		articleContainer.addContainerProperty("expiry date", String.class, null);
		articleContainer.addContainerProperty("edit", Button.class, null);
		articleContainer.addContainerProperty("delete", Button.class, null);
		articleTable.setWidth(100, Unit.PERCENTAGE);
		articleTable.setContainerDataSource(articleContainer);
	}

	/**
	 * Create empty table
	 */
	@Override
	public void buildEmptyTable(final Container articleContainer) {
		Item item = articleContainer.addItem("-1");
		item.getItemProperty("articles").setValue("No record found.");
	}

}