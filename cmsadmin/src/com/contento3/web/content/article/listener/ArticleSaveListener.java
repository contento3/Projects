package com.contento3.web.content.article.listener;

import java.util.Collection;
import java.util.Date;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.contento3.account.service.AccountService;
import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.content.article.ArticleForm;
import com.contento3.web.content.article.ArticleTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;

public class ArticleSaveListener implements ClickListener{

	private static final long serialVersionUID = 1L;

	/**
	 * Article service for article related operations
	 */
	private ArticleService articleService;

	/**
	 * Account service for account related activities
	 */
	private AccountService accountService;

	private Integer articleId;

	private Integer accountId;

	private ArticleForm articleForm;

	final SpringContextHelper helper;

	final Window parentWindow;

	final TabSheet tabSheet;

	final Table table;

	final Tab articleTab;

	
	public ArticleSaveListener(final Tab articleTab, final ArticleForm articleForm,final Table articleTable, 
			final Integer articleId,final Integer accountId){
		this.accountId = accountId;
		this.articleId = articleId;
		this.articleForm = articleForm;
		this.tabSheet = articleForm.getTabSheet();
		this.parentWindow = articleForm.getParentWindow();
		this.helper = articleForm.getContextHelper();
		this.table = articleTable;
		this.articleTab = articleTab;
		this.accountService = (AccountService)helper.getBean("accountService");
		this.articleService = (ArticleService)helper.getBean("articleService");
	}

	 
	@Override
	public void click(ClickEvent event) {
		ArticleDto articleDto = new ArticleDto();

		if (null!= articleId){
			articleDto = articleService.findById(articleId);
		}
			
			articleDto.setHead(articleForm.getArticleHeading().getValue().toString());
			articleDto.setTeaser(articleForm.getArticleTeaser().getValue().toString());
			articleDto.setBody(articleForm.getBodyTextField().getValue().toString());
		
		final String unsaveNotification ="cannot add article";
		final String saveNotification =articleDto.getHead()+" saved successfully";
		final String updateNotification =articleDto.getHead()+" updated successfully";
		
		// header or body cannot be null
		if(articleDto.getHead().isEmpty() || articleDto.getBody().isEmpty()){
			
			Notification.show(unsaveNotification);
		}
		else{
			final Date createdDate= new Date();
			articleDto.setDateCreated(createdDate);
			articleDto.setLastUpdated(createdDate);
			
			Date date = (Date) articleForm.getPostedDatefield().getValue();
			articleDto.setDatePosted(date);
			articleDto.setLastUpdated(new Date());
			articleDto.setExpiryDate((Date)articleForm.getExpiryDatefield().getValue());
			articleDto.setIsVisible(1);
			articleForm.getPostedDatefield().getValue();
			articleDto.setDatePosted((Date)articleForm.getPostedDatefield().getValue());
		
		//new article is creating is article id == null
		if (null==articleId){
			articleDto.setDateCreated(new Date());
			articleDto.setAccount(accountService.findAccountById(accountId));
			articleService.create(articleDto);
			Notification.show(saveNotification);
			//tabSheet.removeTab(articleTab);
			resetTable();
			//tabSheet.removeTab(articleTab);
		}
		else{
			articleService.update(articleDto);
			Notification.show(updateNotification);
			//tabSheet.removeTab(articleTab);
			resetTable();
			//tabSheet.removeTab(articleTab);
		}
		   
	  }
		
	}
	
	/**
	 * Reset table
	 */
	 @SuppressWarnings("rawtypes")
	 private void resetTable(){
		final AbstractTableBuilder tableBuilder = new ArticleTableBuilder(this.parentWindow,this.helper,this.tabSheet,this.table);
		final Collection<ArticleDto> articles=this.articleService.findByAccountId(accountId);
		tableBuilder.rebuild((Collection)articles);
	}

}