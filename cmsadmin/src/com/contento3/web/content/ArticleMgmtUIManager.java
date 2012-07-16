package com.contento3.web.content;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.vaadin.openesignforms.ckeditor.CKEditorConfig;
import org.vaadin.openesignforms.ckeditor.CKEditorTextField;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountService;
import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ArticleMgmtUIManager {
	
	private SpringContextHelper helper=null;
	private Window parentWindow=null;
	private Integer accountId=null;
	private ArticleService articleService;
	private AccountService accountService;
	private final TextField articleHeading = new TextField();
	private final TextArea articleTeaser = new TextArea();
	private final CKEditorConfig config = new CKEditorConfig();
	private final CKEditorTextField bodyTextField = new CKEditorTextField(config);
	private final PopupDateField postedDatefield = new PopupDateField("Article Posted Date");
	private final PopupDateField lastUpdatedDatefield = new PopupDateField("Last Updated Date");
	private final PopupDateField expiryDatefield = new PopupDateField("Article Expiry Date");
	private VerticalLayout formLayout = null;
	/**
	 * default constructor
	 */
	public ArticleMgmtUIManager() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * constructor
	 * @param helper
	 * @param parentWindow
	 */
	public ArticleMgmtUIManager(final SpringContextHelper helper,final Window parentWindow) {
		this.helper= helper;
		this.parentWindow = parentWindow;
		this.articleService = (ArticleService) helper.getBean("articleService");
		this.accountService = (AccountService) helper.getBean("accountService");
		final PopupDateField datefield = new PopupDateField("Article Posted Date");
		//Get accountId from the session
        WebApplicationContext ctx = ((WebApplicationContext) parentWindow.getApplication().getContext());
        HttpSession session = ctx.getHttpSession();
        accountId =(Integer)session.getAttribute("accountId");
	}

	/**
	 * render screen for adding article
	 * @return
	 */
	public Component renderAddScreen(){
		formLayout = new VerticalLayout();
		formlayoutSettings();
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
				AccountDto account = accountService.findAccountById(accountId);
				article.setAccount(account);
				article.setSite(new ArrayList());
				articleService.create(article);
				String notification ="Artilce added successfully"; 
				parentWindow.showNotification(notification);
				
			}
		});

        formLayout.addComponent(saveButton);
        return formLayout;
      }
	/**
	 * render screen for editing article
	 * @param article
	 * @return
	 */
	public Component renderEditScreen(final ArticleDto article){
		
		formlayoutSettings();
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
				AccountDto account = accountService.findAccountById(accountId);
				article.setAccount(account);
				article.setSite(new ArrayList());
				articleService.update(article);
				String notification ="Artilce updated successfully"; 
				parentWindow.showNotification(notification);
				
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
	   // formLayout.addComponent(lastUpdatedDatefield);
	    formLayout.addComponent(expiryDatefield);
	    formLayout.addComponent(bodyTextField);
	}
}
