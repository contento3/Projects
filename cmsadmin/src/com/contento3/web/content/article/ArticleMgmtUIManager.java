package com.contento3.web.content.article;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.web.UIManager;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.content.article.listener.ArticleFormBuilderListner;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ArticleMgmtUIManager implements UIManager {
	
	public final static String ARTICLE_HEADING_LBL = "Header";
	public final static String ARTICLE_TEASER_LBL = "Teaser";
	public final static String ARTICLE_BODY_LBL = "Body";
	public final static String ARTICLE_POSTED_DATE = "Article Posted Date";
	public final static String ARTICLE_EXPIRY_DATE = "Article Expiry Date";

	/**
	 * Used to get service beans from spring context.
	 */
	private SpringContextHelper contextHelper;
	 /**
     * Represents the parent window of the ui
     */
	private Window parentWindow;
	
	/**
	 * TabSheet serves as the parent container for the article manager
	 */

	private TabSheet tabSheet;

	/**
	 * main layout for article manager screen
	 */
	private VerticalLayout verticalLayout = new VerticalLayout();
	
	/**
	 * Article table which shows articles
	 */
	private final Table articleTable =  new Table("Articles");

	/**
	 * Article service for article related operations
	 */
	private ArticleService articleService;
	
	/**
	 * Account id
	 */
	private Integer accountId;
	

	/**
	 * constructor
	 * @param helper
	 * @param parentWindow
	 */
	public ArticleMgmtUIManager(final TabSheet uiTabSheet,final SpringContextHelper helper,final Window parentWindow) {
		this.contextHelper= helper;
		this.parentWindow = parentWindow;
		this.articleService = (ArticleService) this.contextHelper.getBean("articleService");
		this.tabSheet = uiTabSheet;
		//Get accountId from the session
        WebApplicationContext ctx = ((WebApplicationContext) parentWindow.getApplication().getContext());
        HttpSession session = ctx.getHttpSession();
        this.accountId =(Integer)session.getAttribute("accountId");
	}

	@Override
	public void render() {

		
	}
	/**
	 * Return tab sheet
	 */
	@Override
	public Component render(String command) {
		this.tabSheet.setHeight(100, Sizeable.UNITS_PERCENTAGE);
		final Tab articleTab = tabSheet.addTab(verticalLayout, "Article Management");
		articleTab.setClosable(true);
		this.verticalLayout.setSpacing(true);
		this.verticalLayout.setWidth(100,Sizeable.UNITS_PERCENTAGE);
		renderArticleComponent();
		return this.tabSheet;
	}
	

	@Override
	public Component render(String command, Integer entityFilterId) {
	
		return null;
	}
	@Override
	public Component render(String command,
			HierarchicalContainer treeItemContainer) {
	
		return null;
	}
	
	private void renderArticleComponent() {
		Label articleHeading = new Label("Article Manager");
		articleHeading.setStyleName("screenHeading");
		this.verticalLayout.addComponent(articleHeading);
		this.verticalLayout.addComponent(new HorizontalRuler());
		this.verticalLayout.setMargin(true);
		addArticleButton();
		this.verticalLayout.addComponent(new HorizontalRuler());
		renderArticleTable();
		
		
	}
	/**
	 * Render article table
	 */
	@SuppressWarnings("unchecked")
	private void renderArticleTable() {

		final AbstractTableBuilder tableBuilder = new ArticleTableBuilder(this.parentWindow,this.contextHelper,this.tabSheet,this.articleTable);
		Collection<ArticleDto> articles=this.articleService.findByAccountId(accountId);
		tableBuilder.build((Collection)articles);
		this.verticalLayout.addComponent(this.articleTable);
		
	}

	/**
	 * Display "Add Article" button on the top of tab 
	 */
	private void addArticleButton(){
		Button addButton = new Button("Add Article");
		addButton.addListener(new ArticleFormBuilderListner(this.contextHelper, this.parentWindow,this.tabSheet,this.articleTable));
		this.verticalLayout.addComponent(addButton);
	}

	

}
