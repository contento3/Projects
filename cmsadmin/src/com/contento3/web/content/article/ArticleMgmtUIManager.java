package com.contento3.web.content.article;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.web.UIManager;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.common.helper.ScreenToolbarBuilder;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.content.article.listener.AddArticleButtonListener;
import com.contento3.web.content.article.listener.ArticleAttachContentListener;
import com.contento3.web.content.article.listener.ArticleFormBuilderListner;
import com.contento3.web.content.image.listener.AddImageButtonListener;
import com.contento3.web.content.image.listener.AddLibraryButtonListener;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.GridLayout;
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
		tabSheet.addTab(verticalLayout, "Article Management",new ExternalResource("images/content-mgmt.png"));

		articleTab = tabSheet.getTab(verticalLayout);
		articleTab.setClosable(true);
		this.verticalLayout.setSpacing(true);
		this.verticalLayout.setWidth(100,Unit.PERCENTAGE);
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
		
//		addArticleButton();
		
		GridLayout toolbarGridLayout = new GridLayout(1,2);
		List<com.vaadin.event.MouseEvents.ClickListener> listeners = new ArrayList<com.vaadin.event.MouseEvents.ClickListener>();
		listeners.add(new AddArticleButtonListener(this.contextHelper,this.tabSheet,this.articleTable));
		//listeners.add(new AddLibraryButtonListener(helper));
		//listeners.add(new DocumentEditListener(documentTab, documentForm, documentTable, documentId));
		//ArticleFormBuilderListner(this.contextHelper,this.tabSheet,this.articleTable)
		ScreenToolbarBuilder builder = new ScreenToolbarBuilder(toolbarGridLayout,"article",listeners);
		builder.build();
		HorizontalLayout horizon = new HorizontalLayout();
		VerticalLayout verticl = new VerticalLayout();
		horizon.addComponent(verticl);
		this.verticalLayout.addComponent(horizon);
		horizon.addComponent(toolbarGridLayout);
		horizon.setWidth(100,Unit.PERCENTAGE);
		horizon.setExpandRatio(toolbarGridLayout, 1);
		horizon.setExpandRatio(verticl, 9);
		horizon.setComponentAlignment(toolbarGridLayout, Alignment.TOP_CENTER);	
		
		final TextField searchHeader = new TextField("header name");
		searchHeader.setInputPrompt("Article Header name");
		final TextField searchCatagory = new TextField("catagory name");
		searchCatagory.setInputPrompt("Article Category");
		Button searchItem = new Button("search");
		verticl.addComponent(searchHeader);
		verticl.addComponent(searchCatagory);
		verticl.addComponent(searchItem);
		
		this.verticalLayout.addComponent(new HorizontalRuler());
		
		renderArticleTable();
		/**
		 * search button listener .. 		
		 */
		searchItem.addClickListener(new Button.ClickListener() {
			
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
	private void renderArticleTable() {
		final AbstractTableBuilder tableBuilder = new ArticleTableBuilder(this.parentWindow,this.contextHelper,this.tabSheet,this.articleTable);
		Collection<ArticleDto> articles=this.articleService.findByAccountId(accountId);
		tableBuilder.build((Collection)articles);
		this.verticalLayout.addComponent(this.articleTable);
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
	private void addArticleButton(){
		Button addButton = new Button("Add Article");
		addButton.addClickListener(new ArticleFormBuilderListner(this.contextHelper,this.tabSheet,this.articleTable));
		this.verticalLayout.addComponent(addButton);
	}

	

}
