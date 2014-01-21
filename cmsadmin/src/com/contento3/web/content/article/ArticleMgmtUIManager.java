package com.contento3.web.content.article;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.web.UIManager;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.common.helper.ScreenToolbarBuilder;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.content.article.listener.AddArticleButtonListener;
import com.contento3.web.content.article.listener.ArticleFormBuilderListner;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
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
	private VerticalLayout verticalLayout;
	
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
	
	//change
	private String header;
	

	/**
	 * constructor
	 * @param helper
	 * @param parentWindow
	 */
	public ArticleMgmtUIManager(final TabSheet uiTabSheet,final SpringContextHelper helper) {
		this.contextHelper= helper;
		this.articleService = (ArticleService) this.contextHelper.getBean("articleService");
		this.tabSheet = uiTabSheet;
        this.accountId = (Integer)SessionHelper.loadAttribute("accountId");
	}

	@Override
	public void render() {
	}

	/**
	 * Return tab sheet
	 */
	@Override
	public Component render(String command) {
		this.tabSheet.setHeight(100, Unit.PERCENTAGE);
		final Tab articleTab;
		
		if (null==tabSheet.getTab(verticalLayout)){
			verticalLayout = new VerticalLayout();
			tabSheet.addTab(verticalLayout, "Article Management",new ExternalResource("images/article.png"));
			articleTab = tabSheet.getTab(verticalLayout);
			articleTab.setClosable(true);
			this.verticalLayout.setSpacing(true);
			this.verticalLayout.setWidth(100,Unit.PERCENTAGE);
			renderArticleComponent();
		}
		
		tabSheet.setSelectedTab(verticalLayout);
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
		
		final HorizontalLayout mainLayout = new HorizontalLayout();
		verticalLayout.addComponent(mainLayout);
		verticalLayout.setWidth(95,Unit.PERCENTAGE);
		verticalLayout.setHeight(400,Unit.PIXELS);
		
		final VerticalLayout contentLayout = new VerticalLayout();
		final Label articleHeading = new Label("Article Manager");
		articleHeading.setStyleName("screenHeading");
		contentLayout.addComponent(articleHeading);
		contentLayout.addComponent(new HorizontalRuler());
		contentLayout.setMargin(true);
		contentLayout.setSizeFull();
		contentLayout.setHeight(250, Unit.PIXELS);

		mainLayout.addComponent(contentLayout);
		mainLayout.setWidth(100, Unit.PERCENTAGE);
		mainLayout.setHeight(400, Unit.PIXELS);

		mainLayout.setExpandRatio(contentLayout, 100);
		final GridLayout toolbarGridLayout = new GridLayout(1,2);
		mainLayout.addComponent(toolbarGridLayout);
		mainLayout.setExpandRatio(toolbarGridLayout, 1);
		final List<com.vaadin.event.MouseEvents.ClickListener> listeners = new ArrayList<com.vaadin.event.MouseEvents.ClickListener>();
		listeners.add(new AddArticleButtonListener(this.contextHelper,this.tabSheet,this.articleTable));

		final ScreenToolbarBuilder builder = new ScreenToolbarBuilder(toolbarGridLayout,"article",listeners);
		builder.build();
		HorizontalLayout searchBar = new HorizontalLayout();
		searchBar.setWidth(45,Unit.PERCENTAGE);
		contentLayout.addComponent(searchBar);
		//searchBar.addComponent(toolbarGridLayout);
//		searchBar.setExpandRatio(toolbarGridLayout, 1);
//		searchBar.setComponentAlignment(toolbarGridLayout, Alignment.TOP_CENTER);	
		
		final TextField searchHeader = new TextField("header name");
		searchHeader.setInputPrompt("Article Header name");
		final TextField searchCatagory = new TextField("catagory name");
		searchCatagory.setInputPrompt("Article Category");
		Button searchBtn = new Button("search");
		searchBar.addComponent(searchHeader);
		searchBar.addComponent(searchCatagory);
		searchBar.addComponent(searchBtn);
		searchBar.setComponentAlignment(searchBtn, Alignment.BOTTOM_RIGHT);	
		
		contentLayout.addComponent(new HorizontalRuler());
		
		renderArticleTable(contentLayout);
		/**
		 * search button listener .. 		
		 */
		searchBtn.addClickListener(new Button.ClickListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				final String itemHeader = (String)searchHeader.getValue();
				final String itemCatagory = (String)searchCatagory.getValue();
				
				if(!itemHeader.isEmpty() || !itemCatagory.isEmpty()){
					renderArticTableBySearch(itemHeader,itemCatagory);
				}
			}
		});
			
	}
	
	/**
	 * Render article table
	 */
	@SuppressWarnings("unchecked")
	private void renderArticleTable(final VerticalLayout contentLayout) {
		final AbstractTableBuilder tableBuilder = new ArticleTableBuilder(this.parentWindow,this.contextHelper,this.tabSheet,this.articleTable);
		Collection<ArticleDto> articles=this.articleService.findByAccountId(accountId);
		tableBuilder.build((Collection)articles);
		contentLayout.addComponent(this.articleTable);
	}
	
	/**
	 * Render article table by using header name
	 */
	
	/**private void renderArticleTableByHeader(String header){
		
		final AbstractTableBuilder reBuiltTable = new ArticleTableBuilder(this.parentWindow,this.contextHelper,
				this.tabSheet,this.articleTable);
		Collection<ArticleDto> articles = this.articleService.findByHeaderName(header);
		reBuiltTable.rebuild((Collection)articles);
		
	}***/
	
	private void renderArticTableBySearch(String header, String catagory){
		
		final AbstractTableBuilder reBuiltTable = new ArticleTableBuilder(this.parentWindow,this.contextHelper,
				this.tabSheet,this.articleTable);
		Collection<ArticleDto> articles = this.articleService.findBySearch(header, catagory);
		reBuiltTable.rebuild((Collection)articles);
	}
	
	/**
	 * Display "Add Article" button on the top of tab 
	 */
	@RequiresPermissions("article:add")
	private void addArticleButton(){
		Button addButton = new Button("Add Article");
		addButton.addClickListener(new ArticleFormBuilderListner(this.contextHelper,this.tabSheet,this.articleTable));
		this.verticalLayout.addComponent(addButton);
	}

	

}
