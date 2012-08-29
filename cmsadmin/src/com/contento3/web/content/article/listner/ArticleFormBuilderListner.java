package com.contento3.web.content.article.listner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.vaadin.openesignforms.ckeditor.CKEditorConfig;
import org.vaadin.openesignforms.ckeditor.CKEditorTextField;
import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountService;
import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.content.article.ArticleTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TabSheet.Tab;

public class ArticleFormBuilderListner implements ClickListener{
	
	/**
	 * 
	 */
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
	
	private final TextField articleHeading = new TextField();
	private final TextArea articleTeaser = new TextArea();
	private final CKEditorConfig config = new CKEditorConfig();
	private final CKEditorTextField bodyTextField = new CKEditorTextField(config);
	private final PopupDateField postedDatefield = new PopupDateField("Article Posted Date");
	private final PopupDateField expiryDatefield = new PopupDateField("Article Expiry Date");
	private Integer accountId;
	
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
        WebApplicationContext ctx = ((WebApplicationContext) parentWindow.getApplication().getContext());
        HttpSession session = ctx.getHttpSession();
        this.accountId =(Integer)session.getAttribute("accountId");
	}
	
	
	/**
	 * render screen for adding article
	 * @return
	 */
	public void renderAddScreen(){
		formlayoutSettings();
		final Tab addArticleTab = this.tabSheet.addTab(formLayout,"Add Article");
		this.tabSheet.setSelectedTab(formLayout);
		addArticleTab.setClosable(true);
		
        final String editorInitialValue = 
                  "<p>Thanks TinyMCEEditor for getting us started on the CKEditor integration.</p><h1>Like TinyMCEEditor said, &quot;Vaadin rocks!&quot;</h1><h1>And CKEditor is no slouch either.</h1>";
        bodyTextField.setValue(editorInitialValue);
        postedDatefield.setValue(new Date());

        final Button saveButton = new Button("Save");
        saveButton.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				ArticleDto article = new ArticleDto();
				article.setHead(articleHeading.getValue().toString());
				article.setTeaser(articleTeaser.getValue().toString());
				article.setBody(bodyTextField.getValue().toString());
				postedDatefield.getValue();
				article.setDatePosted((Date)postedDatefield.getValue());
				Date createdDate= new Date();
				article.setDateCreated(createdDate);
				article.setLastUpdated(createdDate);
				article.setExpiryDate((Date)expiryDatefield.getValue());
				article.setIsVisible(1);
				AccountDto account = accountService.findAccountById(accountId);
				article.setAccount(account);
				article.setSite(new ArrayList());
				articleService.create(article);
				String notification ="Article added successfully"; 
				parentWindow.showNotification(notification);
				resetTable();
				tabSheet.removeTab(addArticleTab);
			}
		});

        formLayout.addComponent(saveButton);
      }
	
	/**
	 * render screen for editing article
	 * @param article
	 * @return
	 */
	public Component renderEditScreen(final Integer editId){
		formlayoutSettings();
		final ArticleDto article = this.articleService.findById(editId);
		final Tab editArticleTab = this.tabSheet.addTab(formLayout,"Edit Article");
		this.tabSheet.setSelectedTab(formLayout);
		editArticleTab.setClosable(true);
	
		articleHeading.setValue(article.getHead());
		articleTeaser.setValue(article.getTeaser());
		bodyTextField.setValue(article.getBody());
		postedDatefield.setValue(article.getDatePosted());
		expiryDatefield.setValue(article.getExpiryDate());
		final Button editButton = new Button("Save");
		editButton.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				article.setHead(articleHeading.getValue().toString());
				article.setTeaser(articleTeaser.getValue().toString());
				article.setBody(bodyTextField.getValue().toString());
				Date date = (Date) postedDatefield.getValue();
				article.setDatePosted(date);
				article.setLastUpdated(new Date());
				article.setExpiryDate((Date)expiryDatefield.getValue());
				article.setIsVisible(1);
				AccountDto account = accountService.findAccountById(accountId);
				article.setAccount(account);
				article.setSite(new ArrayList());
				articleService.update(article);
				String notification =article.getHead()+" updated successfully"; 
				parentWindow.showNotification(notification);
				tabSheet.removeTab(editArticleTab);
				resetTable();
				tabSheet.removeTab(editArticleTab);
			}
		});
		
	    formLayout.addComponent(editButton);
		return formLayout;
	}
	
	/**
	 * setting formLayout for showing edit & add article screen
	 */
	public void formlayoutSettings(){
		formLayout = new VerticalLayout();
		formLayout.setHeight("100%");
		articleHeading.setCaption("Header");

		articleTeaser.setCaption("Teaser");
		articleTeaser.setColumns(50);
		articleTeaser.setRows(2);
		
        config.useCompactTags();
        config.disableElementsPath();
        config.setResizeDir(CKEditorConfig.RESIZE_DIR.BOTH);
        config.disableSpellChecker();
        config.setToolbarCanCollapse(true);
        config.setWidth("75%");
        config.setHeight("100%");
        bodyTextField.setCaption("Article Body");
        
        postedDatefield.setInputPrompt("Insert Date");
        // Set the correct resolution only date
        postedDatefield.setResolution(PopupDateField.RESOLUTION_DAY);
        expiryDatefield.setInputPrompt("Insert Date");
        expiryDatefield.setResolution(PopupDateField.RESOLUTION_DAY);
        formLayout.addComponent(articleHeading);
	    formLayout.addComponent(articleTeaser);
	    formLayout.addComponent(postedDatefield);
	    formLayout.addComponent(expiryDatefield);
	    formLayout.addComponent(bodyTextField);
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
