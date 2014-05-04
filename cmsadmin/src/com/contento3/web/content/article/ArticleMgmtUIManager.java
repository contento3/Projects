package com.contento3.web.content.article;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.AuthorizationException;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.web.UIManager;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.ScreenToolbarBuilder;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.content.article.listener.AddArticleButtonListener;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;



public class ArticleMgmtUIManager implements UIManager {
	
	private static final Logger LOGGER = Logger.getLogger(ArticleMgmtUIManager.class);

	public final static String ARTICLE_HEADING_LBL = "Header";
	public final static String ARTICLE_TEASER_LBL = "Teaser";
	public final static String ARTICLE_BODY_LBL = "Body";
	public final static String ARTICLE_POSTED_DATE = "Article Posted Date";
	public final static String ARTICLE_EXPIRY_DATE = "Article Expiry Date";
	public final static String ARTICLE_SEO_LBL = "SEO Friendly Url";

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
	private HorizontalLayout mainLayout;
	
	/**
	 * Article table which shows articles
	 */
	private final Table articleTable =  new Table();

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
		
		if (null==tabSheet.getTab(mainLayout)){
			mainLayout = new HorizontalLayout();
			tabSheet.addTab(mainLayout, "Article Management",new ExternalResource("images/article.png"));
			articleTab = tabSheet.getTab(mainLayout);
			articleTab.setClosable(true);
			this.mainLayout.setSpacing(true);
			this.mainLayout.setWidth(100,Unit.PERCENTAGE);
			renderArticleComponent();
		}
		
		tabSheet.setSelectedTab(mainLayout);
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
		
//		final VerticalLayout verticalLayout = new VerticalLayout();
//		mainLayout.addComponent(verticalLayout);
//		verticalLayout.setWidth(95,Unit.PERCENTAGE);
//		verticalLayout.setHeight(400,Unit.PIXELS);

		final VerticalLayout contentLayout = new VerticalLayout();
		final Label articleHeading = new Label("Article Manager");
		articleHeading.setStyleName("screenHeading");
		articleHeading.setSizeUndefined();
		contentLayout.addComponent(articleHeading);
		contentLayout.setMargin(true);
		contentLayout.setSizeUndefined();
		contentLayout.setWidth(100, Unit.PERCENTAGE);

		mainLayout.addComponent(contentLayout);
		mainLayout.setWidth(100, Unit.PERCENTAGE);
		mainLayout.setHeight(400, Unit.PIXELS);

		mainLayout.setExpandRatio(contentLayout, 100);
		final GridLayout toolbarGridLayout = new GridLayout(1,2);
		mainLayout.addComponent(toolbarGridLayout);
		final List<com.vaadin.event.MouseEvents.ClickListener> listeners = new ArrayList<com.vaadin.event.MouseEvents.ClickListener>();
		listeners.add(new AddArticleButtonListener(this.contextHelper,this.tabSheet,this.articleTable));

		final ScreenToolbarBuilder builder = new ScreenToolbarBuilder(toolbarGridLayout,"article",listeners);
		builder.build();
		VerticalLayout emptySpaceLayout = new VerticalLayout();
		contentLayout.addComponent(emptySpaceLayout);

		final TextField searchHeader = new TextField("Article Head");
		searchHeader.setInputPrompt("Article Header name");
		final TextField searchCategory = new TextField("Category");
		searchCategory.setInputPrompt("Article Category");
		Button searchBtn = new Button("Search");

		FormLayout searchBar = new FormLayout();
		searchBar.addStyleName("horizontalForm");
		searchBar.setSizeFull();
		searchBar.addComponent(searchHeader);
		searchBar.addComponent(searchCategory);
		searchBar.addComponent(searchBtn);
		searchBar.setComponentAlignment(searchBtn, Alignment.BOTTOM_RIGHT);	
		searchBar.setWidth(500,Unit.PIXELS);

		final Panel searchPanel = new Panel();
		searchPanel.setSizeUndefined(); // Shrink to fit content
		searchPanel.setContent(searchBar);
		contentLayout.addComponent(searchPanel);
		searchPanel.setWidth(100,Unit.PERCENTAGE);
		searchPanel.setHeight(50,Unit.PIXELS);
		
		contentLayout.setSpacing(true);
		renderArticleTable(contentLayout);
		
		/**
		 * search button listener .. 		
		 */
		searchBtn.addClickListener(new Button.ClickListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				final String itemHeader = (String)searchHeader.getValue();
				final String itemCatagory = (String)searchCategory.getValue();
				
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
		try
		{
			Collection<ArticleDto> articles=this.articleService.findByAccountId(accountId, false);
			tableBuilder.build((Collection)articles);
		} 
		catch(AuthorizationException ex)
		{
			LOGGER.debug("you are not permitted to see article", ex);
		}
		contentLayout.setSpacing(true);
		contentLayout.setMargin(true);
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
		try
		{
			Collection<ArticleDto> articles = this.articleService.findBySearch(header, catagory, false);
			reBuiltTable.rebuild((Collection)articles);
		}
		catch(final AuthorizationException ex){
			LOGGER.debug("you are not permitted to see article", ex);
		}
	}
	
	/**
	 * Display "Add Article" button on the top of tab 
	 */
	private void addArticleButton(){
//		Button addButton = new Button("Add Article");
//		try
//		{
//			addButton.addClickListener(new ArticleFormBuilderListner(this.contextHelper,this.tabSheet,this.articleTable));
//		}
//		catch(final AuthorizationException ex){
//			LOGGER.debug("you are not permitted to add article", ex);
//		}
//		this.verticalLayout.addComponent(addButton);
	}

	

}
