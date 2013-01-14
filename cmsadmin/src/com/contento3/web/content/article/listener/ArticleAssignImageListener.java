package com.contento3.web.content.article.listener;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountService;
import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.dto.ArticleImageDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.cms.content.dto.AssociatedContentScopeDto;
import com.contento3.cms.content.service.AssociatedContentScopeService;
import com.contento3.common.dto.Dto;
import com.contento3.common.exception.EntityNotFoundException;
import com.contento3.dam.image.dto.ImageDto;
import com.contento3.dam.image.service.ImageService;
import com.contento3.web.common.helper.ComboDataLoader;
import com.contento3.web.common.helper.EntityListener;
import com.contento3.web.common.helper.GenricEntityPicker;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Select;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

public class ArticleAssignImageListener extends EntityListener implements ClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Vertical layout for pop-up
	 */
	private final VerticalLayout mainLayout;
	
	/**
	 * ArticleId for article related activity
	 */
	private Integer articleId;
	
	/**
	 * AccountId to find related account
	 */
	private Integer accountId;
	
	 /**
     * Represents the main window of the ui
     */
	private Window mainWindow;
	
	/**
	 * ImageService to find related images
	 */
	private ImageService imageService;
	
	/**
	 * Used to get service beans from spring context.
	 */
	private SpringContextHelper helper;
	
	/**
	 * ComboBox for content scope
	 */
	private ComboBox contentScopeCombo;
	
	/**
	 * ArticleService for article related activities
	 */
	private ArticleService articleService;
	
	/**
	 * ArticleDto use to set or get associatedImages
	 */
	private ArticleDto article;
	
	/**
	 * Constructor
	 * @param mainWindow
	 * @param helper
	 * @param articleId
	 * @param accountId
	 */
	public ArticleAssignImageListener(final Window mainWindow,final SpringContextHelper helper,final Integer articleId,final Integer accountId) {
		this.articleId = articleId;
		this.helper = helper;
		this.mainLayout = new VerticalLayout();
		this.mainWindow = mainWindow;
		this.imageService = (ImageService) this.helper.getBean("imageService");
		this.accountId = accountId;
		this.articleService = (ArticleService) this.helper.getBean("articleService");
	}
	
	/**
	 * Click handler
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void click(ClickEvent event) {
		//validation article exist
		if(articleId != null){
			AssociatedContentScopeService contentScopeService = (AssociatedContentScopeService) this.helper.getBean("associatedContentScopeService");
			final ComboDataLoader comboDataLoader = new ComboDataLoader();
			contentScopeCombo = new ComboBox("Select Content Scope",comboDataLoader.loadDataInContainer((Collection)contentScopeService.allContentScope()));
			contentScopeCombo.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
			contentScopeCombo.setItemCaptionPropertyId("name");
			contentScopeCombo.setValue(1); //default value
			Collection<String> listOfColumns = new ArrayList<String>();
			listOfColumns.add("Images");
			setCaption("Add images");
			Collection<Dto> dtos = (Collection) imageService.findImageByAccountId(this.accountId);
			GenricEntityPicker imagePicker = new GenricEntityPicker(dtos, listOfColumns, this.mainLayout, mainWindow, this, false);
			imagePicker.build();
			imagePicker.setTableCaption("Select Images");
			this.mainLayout.addComponentAsFirst(contentScopeCombo);
		}else{
			//warning message
			mainWindow.showNotification("Opening failed", "create article first", Notification.TYPE_WARNING_MESSAGE);
		}
	}

	/**
	 * Add images to article
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateList() {
		Collection<String> selectedItems =(Collection<String>) this.mainLayout.getData();
		if(selectedItems != null){
			article = articleService.findById(articleId);
			AccountService accountService = (AccountService) this.helper.getBean("accountService");
			AccountDto account = accountService.findAccountById(accountId);
			AssociatedContentScopeService scopeService = (AssociatedContentScopeService) this.helper.getBean("associatedContentScopeService");
			Integer scope = Integer.parseInt(this.contentScopeCombo.getValue().toString());
			AssociatedContentScopeDto contentscope = scopeService.findById(scope);
			try {
				for(String name : selectedItems ){
					final ImageDto image = imageService.findImageByNameAndAccountId(name, accountId);
					final ArticleImageDto dto = new ArticleImageDto();
					dto.setArticle(article);
					dto.setImage(image);
					dto.setContentScope(contentscope);
					dto.setAccount(account);
					if (!article.getAssociateImagesDtos().contains(dto))
						article.getAssociateImagesDtos().add(dto);
				}//end outer for	
			} catch (EntityNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			articleService.updateAssociateImages(article);
		}//end if
	}//end updateList()
}
