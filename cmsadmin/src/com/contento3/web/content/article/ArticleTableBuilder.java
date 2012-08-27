package com.contento3.web.content.article;

import java.text.SimpleDateFormat;
import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.common.dto.Dto;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.content.article.listner.ArticleDeleteClickListner;
import com.contento3.web.content.article.listner.ArticleFormBuilderListner;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.Sizeable;
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
	public void assignDataToTable(final Dto dto,final Table articleTable,
			final IndexedContainer articleContainer) {
		ArticleDto article = (ArticleDto) dto;
		Item item = articleContainer.addItem(article.getArticleId());
		item.getItemProperty("articles").setValue(article.getHead());
		String DATE_FORMAT = "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		item.getItemProperty("date created").setValue(sdf.format(article.getDateCreated()));
		item.getItemProperty("date posted").setValue(sdf.format(article.getDatePosted()));
		item.getItemProperty("expiry date").setValue(sdf.format(article.getExpiryDate()));
		
		Button editLink = new Button();
		editLink.setCaption("Edit");
		editLink.setData(article.getArticleId());
		editLink.addStyleName("edit");
		editLink.setStyleName(BaseTheme.BUTTON_LINK);
		editLink.addListener(new ArticleFormBuilderListner(this.contextHelper, this.window,this.tabSheet,articleTable));
		item.getItemProperty("edit").setValue(editLink);
		Button deleteLink = new Button();
		deleteLink.setCaption("Delete");
		deleteLink.setData(article.getArticleId());
		deleteLink.addStyleName("delete");
		deleteLink.setStyleName(BaseTheme.BUTTON_LINK);
		item.getItemProperty("delete").setValue(deleteLink);
		deleteLink.addListener(new ArticleDeleteClickListner(article, window, contextHelper, deleteLink, articleTable));

	}
	/**
	 * Build header for table
	 */
	@Override
	public void buildHeader(final Table articleTable,final IndexedContainer articleContainer) {
		articleContainer.addContainerProperty("articles", String.class, null);
		articleContainer.addContainerProperty("date created", String.class, null);
		articleContainer.addContainerProperty("date posted", String.class, null);
		articleContainer.addContainerProperty("expiry date", String.class, null);
		articleContainer.addContainerProperty("edit", Button.class, null);
		articleContainer.addContainerProperty("delete", Button.class, null);
		

		articleTable.setWidth(100, Sizeable.UNITS_PERCENTAGE);
		articleTable.setContainerDataSource(articleContainer);

	}

	/**
	 * Create empty table
	 */
	@Override
	public void buildEmptyTable(final IndexedContainer articleContainer) {
		Item item = articleContainer.addItem("-1");
		item.getItemProperty("articles").setValue("No record found.");
	}

}
