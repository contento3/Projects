package com.contento3.web.content.article.listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.shiro.authz.AuthorizationException;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountService;
import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.dto.ArticleImageDto;
import com.contento3.cms.article.service.ArticleImageService;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.cms.content.dto.AssociatedContentScopeDto;
import com.contento3.cms.content.model.AssociatedContentScope;
import com.contento3.cms.content.model.AssociatedContentScopeTypeEnum;
import com.contento3.cms.content.service.AssociatedContentScopeService;
import com.contento3.common.dto.Dto;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.dam.image.dto.ImageDto;
import com.contento3.dam.image.service.ImageService;
import com.contento3.web.common.helper.ComboDataLoader;
import com.contento3.web.common.helper.EntityListener;
import com.contento3.web.common.helper.GenricEntityPicker;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

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
	 * ArticleImageService for article related activities
	 */
	private ArticleImageService articleImageService;
	
	/**
	 * ArticleDto use to set or get associatedImages
	 */
	private ArticleDto article;
	
	private Collection<Dto> assignedDtos;
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
		this.articleImageService = (ArticleImageService) this.helper.getBean("articleImageService");;
	}
	
	/**
	 * Click handler
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void click(ClickEvent event) {
		//validation article exist
		if(articleId != null){
			try
			{
			AssociatedContentScopeService contentScopeService = (AssociatedContentScopeService) this.helper.getBean("associatedContentScopeService");
			final ComboDataLoader comboDataLoader = new ComboDataLoader();
			final Collection<AssociatedContentScope> contentScopeForImage = (Collection) contentScopeService.getContentScopeForType(AssociatedContentScopeTypeEnum.IMAGE);
			contentScopeCombo = new ComboBox("Select Content Scope",comboDataLoader.loadDataInContainer((Collection)contentScopeForImage));
			contentScopeCombo.setItemCaptionMode(ComboBox.ItemCaptionMode.PROPERTY);
			contentScopeCombo.setItemCaptionPropertyId("name");
			contentScopeCombo.setImmediate(true);
			Collection<String> listOfColumns = new ArrayList<String>();
			listOfColumns.add("Images");
			setCaption("Add images");
			
			//We need empty collection no need to get the dtos as this
			//is required based on the change in the vale selected in combo
			Collection<Dto> dtos = new ArrayList<Dto>();

			assignedDtos = new ArrayList<Dto>();
			
			GenricEntityPicker imagePicker = new GenricEntityPicker(dtos,assignedDtos, listOfColumns, this.mainLayout, this, false);
			imagePicker.build();
			imagePicker.setTableCaption("Select Images");

			contentScopeCombo.addValueChangeListener(new ContentScopeChangeListener(articleId,accountId,helper,imagePicker));

			this.mainLayout.addComponentAsFirst(contentScopeCombo);}
			catch(AuthorizationException ex){Notification.show("You are not permitted to assign image to articles");}
		}else{
			//warning message
			Notification.show("Unable to assign image to article before creating the article itself", "please create the article first", Notification.TYPE_WARNING_MESSAGE);
		}
	}

	@SuppressWarnings("unused")
	private Collection <Dto> populateImageDtos(Collection <ArticleImageDto> articleImageDtos){
		Collection <Dto> dtos = new ArrayList<Dto>();
		Dto dto;
		for (ArticleImageDto articleImageDto : articleImageDtos){
			dto = articleImageDto.getImage();
			dtos.add(dto);
		}
		return dtos;
	}

	/**
	 * Add images to article
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateList() {
		Collection<String> selectedItems =(Collection<String>) this.mainLayout.getData();
		article = articleService.findById(articleId);
		Collection <ArticleImageDto> articleImages = article.getAssociateImagesDtos();

		//if(!CollectionUtils.isEmpty(selectedItems)){
			AccountService accountService = (AccountService) this.helper.getBean("accountService");
			AccountDto account = accountService.findAccountById(accountId);
			AssociatedContentScopeService scopeService = (AssociatedContentScopeService) this.helper.getBean("associatedContentScopeService");
			Integer scopeId = Integer.parseInt(this.contentScopeCombo.getValue().toString());
			AssociatedContentScopeDto contentscope = scopeService.findById(scopeId);

			try {
				for(String id : selectedItems ){
						
					final ImageDto image = imageService.findById(Integer.parseInt(id));
					if (!alreadyAssignedToArticle (image,article,scopeId)){
					final ArticleImageDto dto = new ArticleImageDto();
					dto.setArticle(article);
					dto.setImage(image);
					dto.setContentScope(contentscope);
					dto.setAccount(account);
					article.getAssociateImagesDtos().add(dto);
					}	
				}//end outer for	
		}//end outer for	
			catch (Exception e) {
				Notification.show("Error occured");
	}
			Collection<ArticleImageDto> articleImageDtoToDelete  = new ArrayList<ArticleImageDto>();
			Collection<ArticleImageDto> articleImagesLatest  = article.getAssociateImagesDtos();
			
			ArticleImageDto toDelete =null;
			for (ArticleImageDto aiDto:articleImagesLatest){
				boolean flag = false;
				if (aiDto.getContentScope().getId()==scopeId){
					for (String id:selectedItems){
						if (aiDto.getImage().getId().equals(Integer.parseInt(id)))
						{
							flag = true;
							continue;
						}
						else {
							toDelete = aiDto;
						}
					}
					if (!flag && null!=toDelete){
						articleImageDtoToDelete.add(toDelete);
					}
					else if (!flag && null==toDelete){
						articleImageDtoToDelete.add(aiDto);
					}
				}	
			}
				
			

			try {
				articleImageService.deleteAll(articleImageDtoToDelete);
			} catch (EntityCannotBeDeletedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			article.getAssociateImagesDtos().removeAll(articleImageDtoToDelete);
			articleService.updateAssociateImages(article);
//		}//end if
//		else {
//			try {
//				articleImageService.deleteAll(articleImages);
//			} catch (EntityCannotBeDeletedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			article.getAssociateImagesDtos().clear();
//			articleService.updateAssociateImages(article);
//		}
		mainLayout.setData(null);

	}//end updateList()
	

	public boolean alreadyAssignedToArticle(final ImageDto imageDto,final ArticleDto articleDto,final Integer scopeId){
		final Iterator<ArticleImageDto> articleImageIterator = articleDto.getAssociateImagesDtos().iterator();
		while (articleImageIterator.hasNext()){
			ArticleImageDto articleImageDto = articleImageIterator.next();
			if (articleImageDto.getImage().equals(imageDto) && scopeId.equals(articleImageDto.getContentScope().getId()))
				return true;
		}
		return false;
	}
}
