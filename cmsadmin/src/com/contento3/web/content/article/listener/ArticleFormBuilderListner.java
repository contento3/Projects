package com.contento3.web.content.article.listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.vaadin.openesignforms.ckeditor.CKEditorConfig;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountService;
import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.ScreenHeader;
import com.contento3.web.common.helper.ScreenToolbarBuilder;
import com.contento3.web.content.article.ArticleForm;
import com.contento3.web.content.article.ArticleMgmtUIManager;
import com.contento3.web.content.article.ArticleTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ArticleFormBuilderListner implements ClickListener{
	private static final long serialVersionUID = 1L;

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
	 * layout when adding or editing article
	 */
	private VerticalLayout formLayout;
	
	/**
	 * Article service for article related operations
	 */
	private ArticleService articleService;
	
	/**
	 * Account service for account related activities
	 */
	private AccountService accountService;
	
	/**
	 * Article table which shows articles
	 */
	private Table articleTable;
	
	
	/**
	 * Articles accountid 
	 */
	private Integer accountId;
	
	/**
	 * Screen Header
	 */
	private ScreenHeader screenHeader; 
	
	/**
	 * Layout thats holds all the component
	 */
	private HorizontalLayout parentLayout;
	
	/**
	 * Form that contains all the fields that are 
	 * required to be displayed on article screen.
	 */
	private ArticleForm articleForm;

	Tab articleTab;

	/**
	 * Constructor
	 * @param helper
	 * @param parentWindow
	 * @param tabSheet
	 * @param articleTable
	 */
	public ArticleFormBuilderListner(final SpringContextHelper helper,final Window parentWindow,final TabSheet tabSheet,final Table articleTable) {
		this.contextHelper= helper;
		this.parentWindow = parentWindow;
		this.tabSheet = tabSheet;
		this.articleService = (ArticleService) this.contextHelper.getBean("articleService");
		this.accountService = (AccountService) this.contextHelper.getBean("accountService");
		this.articleTable = articleTable;
		//Get accountId from the session
        final WebApplicationContext ctx = ((WebApplicationContext) parentWindow.getApplication().getContext());
        final HttpSession session = ctx.getHttpSession();
        this.accountId =(Integer)session.getAttribute("accountId");
        articleForm = new ArticleForm();
        articleForm.setContextHelper(helper);
        articleForm.setParentWindow(parentWindow);
        articleForm.setTabSheet(tabSheet);
	}
	
	
	/**
	 * render screen for adding article
	 * @return
	 */
	public void renderAddScreen(){
        buildArticleUI("Add",null);

		articleForm.getPostedDatefield().setValue(new Date());

        final Button saveButton = new Button("Save");
        saveButton.addListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				final ArticleDto article = new ArticleDto();
				article.setHead(articleForm.getArticleHeading().getValue().toString());
				article.setTeaser(articleForm.getArticleTeaser().getValue().toString());
				article.setBody(articleForm.getBodyTextField().getValue().toString());
				articleForm.getPostedDatefield().getValue();
				article.setDatePosted((Date)articleForm.getPostedDatefield().getValue());
				
				final Date createdDate= new Date();
				article.setDateCreated(createdDate);
				article.setLastUpdated(createdDate);
				article.setExpiryDate((Date)articleForm.getExpiryDatefield().getValue());
				article.setIsVisible(1);
				
				final AccountDto account = accountService.findAccountById(accountId);
				article.setAccount(account);
				article.setSite(new ArrayList<SiteDto>());
				articleService.create(article);
				
				final String notification ="Article added successfully"; 
				parentWindow.showNotification(notification);
				resetTable();
				tabSheet.removeTab(articleTab);
			}
		});
      }
	
	/**
	 * render screen for editing article
	 * @param article
	 * @return
	 */
	public Component renderEditScreen(final Integer editId){
        buildArticleUI("Edit",editId);
		
        final ArticleDto article = this.articleService.findById(editId);
		articleForm.getArticleHeading().setValue(article.getHead());
		articleForm.getArticleTeaser().setValue(article.getTeaser());
		articleForm.getBodyTextField().setValue(article.getBody());
		articleForm.getPostedDatefield().setValue(article.getDatePosted());
		articleForm.getExpiryDatefield().setValue(article.getExpiryDate());
		final Button editButton = new Button("Save");
		editButton.addListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				article.setHead(articleForm.getArticleHeading().getValue().toString());
				article.setTeaser(articleForm.getArticleTeaser().getValue().toString());
				article.setBody(articleForm.getBodyTextField().getValue().toString());
				Date date = (Date) articleForm.getPostedDatefield().getValue();
				article.setDatePosted(date);
				article.setLastUpdated(new Date());
				article.setExpiryDate((Date)articleForm.getExpiryDatefield().getValue());
				article.setIsVisible(1);
				AccountDto account = accountService.findAccountById(accountId);
				article.setAccount(account);
				article.setSite(new ArrayList());
				articleService.update(article);
				String notification =article.getHead()+" updated successfully"; 
				parentWindow.showNotification(notification);
				tabSheet.removeTab(articleTab);
				resetTable();
				tabSheet.removeTab(articleTab);
			}
		});
		return formLayout;
	}
	
	/**
	 * setting formLayout for showing edit & add article screen
	 */
	private void buildArticleUI(final String command,final Integer articleId){
		formLayout = new VerticalLayout();
        screenHeader = new ScreenHeader(formLayout,"Article");

		parentLayout = new HorizontalLayout();
		parentLayout.setSizeFull();
		parentLayout.addComponent(formLayout);
		
		articleTab = this.tabSheet.addTab(parentLayout,command+" Article");
		articleTab.setClosable(true);

		GridLayout toolbarGridLayout = new GridLayout(1,3);
		List<com.vaadin.event.MouseEvents.ClickListener> listeners = new ArrayList<com.vaadin.event.MouseEvents.ClickListener>();
		listeners.add(new ArticleSaveListener(articleTab, articleForm,articleTable,articleId,accountId));
		listeners.add(new ArticleAssignCategoryListener(parentWindow,contextHelper,articleId,accountId));
		listeners.add(new ArticleAttachContentListener());
		
		ScreenToolbarBuilder builder = new ScreenToolbarBuilder(toolbarGridLayout,"article",listeners);
		builder.build();

		parentLayout.addComponent(toolbarGridLayout);
		parentLayout.setExpandRatio(toolbarGridLayout, 1);
		parentLayout.setExpandRatio(formLayout, 10);
		parentLayout.setComponentAlignment(toolbarGridLayout, Alignment.TOP_RIGHT);
		tabSheet.setSelectedTab(parentLayout);

		formLayout.setHeight("100%");
		formLayout.setWidth("100%");
		
		articleForm.getArticleHeading().setCaption(ArticleMgmtUIManager.ARTICLE_HEADING_LBL);
		articleForm.getArticleHeading().setColumns(65);
		articleForm.getArticleTeaser().setCaption(ArticleMgmtUIManager.ARTICLE_TEASER_LBL);
		articleForm.getArticleTeaser().setColumns(65);
		articleForm.getArticleTeaser().setRows(3);
		
		articleForm.getConfig().useCompactTags();
		articleForm.getConfig().disableElementsPath();
		articleForm.getConfig().setResizeDir(CKEditorConfig.RESIZE_DIR.HORIZONTAL);
		articleForm.getConfig().disableSpellChecker();
		articleForm.getConfig().setToolbarCanCollapse(false);
		articleForm.getConfig().disableElementsPath();       
		articleForm.getConfig().disableSpellChecker();
		articleForm.getConfig().addCustomToolbarLine("['Bold', 'Italic', '-', 'NumberedList', 'BulletedList', '-', 'Link', 'Unlink','-','About']");
		articleForm.getConfig().setWidth("95%");
		articleForm.getConfig().setHeight("95%");
        
        articleForm.getPostedDatefield().setInputPrompt("Insert Date");
        
        // Set the correct resolution only date
        articleForm.getPostedDatefield().setResolution(PopupDateField.RESOLUTION_DAY);
        articleForm.getExpiryDatefield().setInputPrompt("Insert Date");
        articleForm.getExpiryDatefield().setResolution(PopupDateField.RESOLUTION_DAY);
        
        formLayout.setSpacing(true);
        formLayout.setMargin(true);
        formLayout.addComponent(articleForm.getArticleHeading());
	    formLayout.addComponent(articleForm.getArticleTeaser());
	    
	    //Layout for article related dates
	    final HorizontalLayout datesLayout = new HorizontalLayout();
	    datesLayout.setSpacing(true);
	    
	    datesLayout.addComponent(articleForm.getPostedDatefield());
	    
	    //Space layout for spacing date controls
	    final HorizontalLayout spaceLayout = new HorizontalLayout();
	    spaceLayout.setWidth("50px");
	    datesLayout.addComponent(spaceLayout);
	    datesLayout.addComponent(articleForm.getExpiryDatefield());
	    formLayout.addComponent(datesLayout);
	    articleForm.getBodyTextField().setConfig(articleForm.getConfig());
	    formLayout.addComponent(articleForm.getBodyTextField());
	}

	/**
	 * Handle edit and add article operations
	 */
	@Override
	public void buttonClick(ClickEvent event) {
		String buttonCaption = event.getButton().getCaption();
		if(buttonCaption.equals("Edit")){
			Object id = event.getButton().getData();
			renderEditScreen(Integer.parseInt(id.toString()));
		}else{
			//article screen
			renderAddScreen();
		}
	}
	
	/**
	 * Reset table
	 */
	 @SuppressWarnings("rawtypes")
	 private void resetTable(){
		final AbstractTableBuilder tableBuilder = new ArticleTableBuilder(this.parentWindow,this.contextHelper,this.tabSheet,this.articleTable);
		final Collection<ArticleDto> articles=this.articleService.findByAccountId(accountId);
		tableBuilder.rebuild((Collection)articles);
	}

}
