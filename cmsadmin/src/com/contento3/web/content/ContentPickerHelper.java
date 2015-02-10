package com.contento3.web.content;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.service.CategoryService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.common.dto.Dto;
import com.contento3.common.dto.TreeDto;
import com.contento3.common.exception.EntityNotFoundException;
import com.contento3.dam.image.dto.ImageDto;
import com.contento3.dam.image.service.ImageService;
import com.contento3.web.common.helper.EntityListener;
import com.contento3.web.common.helper.GenricEntityPicker;
import com.contento3.web.common.helper.GenricEntityTableBuilder;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.content.image.ImageViewPopup;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

public class ContentPickerHelper extends EntityListener {

	private final static String COLUNM_ARTICLE				= "article";
	private final static String COLUNM_IMAGES 				= "image";
	private final static String COLUNM_LIBRARY 				= "library";
	private final static String COLUNM_CATEGORY 			= "category";
	private final static String COLUNM_VIEW 				= "view";
	private final static String CONTENT_TYPE_ARTICLE 		= "Article";
	private final static String CONTENT_TYPE_IMAGE			= "Image";
	private final static String CONTENT_TYPE_VIDEO 			= "Video";
	private final static String CONTENT_TYPE_DOCUMENT 		= "Document";
	private final static String CONTENT_TYPE_PRODUCT		= "Product";
	private final static String CONTENT_TYPE_CATEGORY		= "Category";
	private final static String CONTENT_TYPE_PAGES			= "Pages";
	private final static String PICKER_TITLE_FOR_IMAGE 		= "Image assignment";
	private final static String PICKER_TITLE_FOR_ARTICLE 	= "Article assignment";

	private SiteService siteService;
	
	private ArticleService articleService;
	
	private ImageService imageService;

	private CategoryService categoryService; 
	
	private String selectedType;

	private Collection<Dto> assignedDtos;

	private HashMap<Integer, ImageDto> images;

	private String selectedData;
	
	private VerticalLayout layoutForPopup;
	
	public ContentPickerHelper(final SpringContextHelper contextHelper){
		this.siteService = (SiteService) contextHelper.getBean("siteService");
		this.articleService = (ArticleService) contextHelper.getBean("articleService");
		this.imageService = (ImageService) contextHelper.getBean("imageService");
		this.categoryService = (CategoryService) contextHelper.getBean("categoryService");
	}
	
	public GenricEntityPicker prepareContentPickerData(final String contentType,final VerticalLayout layoutForPopup,EntityListener listener,final Boolean isSiteSpecific,final SiteDto siteDto,final String selectedData){
		if (listener==null){
			listener = this;
		}
		
		this.selectedData = selectedData;
		this.layoutForPopup = layoutForPopup;
		
		GenricEntityPicker contentPicker = null;
		Collection<Dto> dtos = null;
		final Collection<String> listOfColumns = new ArrayList<String>();
		String title = "";
		
		if( contentType.toString().equalsIgnoreCase(CONTENT_TYPE_ARTICLE) ) {
			selectedType = CONTENT_TYPE_ARTICLE;
			listOfColumns.add(COLUNM_ARTICLE);
			dtos = populateGenericDtoFromArticleDto(articleService.findByAccountId((Integer)SessionHelper.loadAttribute("accountId"), false));
			
			if (isSiteSpecific)
			assignedDtos = populateGenericDtoFromArticleDto(articleService.findLatestArticleBySiteId(siteDto.getSiteId(),null,null, false));
//			else 
//			assignedDtos = populateGenericDtoFromArticleDto(articleService.findByAccountId((Integer)SessionHelper.loadAttribute("accountId"), true));

			title = PICKER_TITLE_FOR_ARTICLE;
			contentPicker = new ContentPicker(dtos,assignedDtos,listOfColumns,layoutForPopup,listener,false);
		} else if( contentType.toString().equalsIgnoreCase(CONTENT_TYPE_IMAGE)) {
			selectedType = CONTENT_TYPE_IMAGE;
			listOfColumns.add(COLUNM_IMAGES);
			listOfColumns.add(COLUNM_LIBRARY);
			listOfColumns.add(GenricEntityTableBuilder.COLUNM_VIEW);
			
			final Collection<ImageDto> imageList = imageService.findImageByAccountId((Integer)SessionHelper.loadAttribute("accountId"));
			dtos = populateGenericDtoFromImageDto(imageList);
			
			if (isSiteSpecific)
				assignedDtos = populateGenericDtoFromImageDto(imageService.findLatestImagesBySiteId(siteDto.getSiteId(), 10));
			
			images = new HashMap<Integer, ImageDto>(imageList.size());
			
			for (ImageDto dto : imageList) {
				images.put(dto.getId(), dto);
			}
			title = PICKER_TITLE_FOR_IMAGE;
			contentPicker = new ContentPicker(dtos,assignedDtos,listOfColumns,layoutForPopup,listener,false);
		}
		else if(contentType.toString().equalsIgnoreCase(CONTENT_TYPE_CATEGORY) ) {
			selectedType = CONTENT_TYPE_CATEGORY;
			listOfColumns.add(COLUNM_CATEGORY);
			//listOfColumns.add(COLUNM_LIBRARY);
			//listOfColumns.add(GenricEntityTableBuilder.COLUNM_VIEW);
			
			Collection<CategoryDto> categoryList;
			try {
				categoryList = categoryService.findByAccountId((Integer)SessionHelper.loadAttribute("accountId"));
				dtos = populateGenericDtoFromCategoryDto(categoryList);
				title = "Categories";
			} catch (final EntityNotFoundException e) {
				dtos = new ArrayList<Dto>();
			}
			contentPicker = new ContentPicker(dtos,assignedDtos,listOfColumns,layoutForPopup,listener,true);	
		}
			
		return contentPicker;
	}
	
	private Collection<Dto> populateGenericDtoFromArticleDto(final Collection <ArticleDto> articleDtos){
		final Collection <Dto> dtos = new ArrayList<Dto>();
		for (ArticleDto articleDto : articleDtos){
			Dto dto = new Dto(articleDto.getId(),articleDto.getHead());
			dtos.add(dto);
		}
		return dtos;
	}
	
	private Collection<Dto> populateGenericDtoFromCategoryDto(final Collection <CategoryDto> categoryDtos){
		final Collection <Dto> dtos = new ArrayList<Dto>();
		for (CategoryDto categoryDto : categoryDtos){
			Dto dto = new TreeDto(categoryDto.getId(),categoryDto.getName());
			dtos.add(dto);
		}
		return dtos;
	}
	
	private Collection<Dto> populateGenericDtoFromImageDto(final Collection <ImageDto> imageDtos){
		final Collection <Dto> dtos = new ArrayList<Dto>();
		for (ImageDto imageDto : imageDtos){
			Dto dto = new Dto(imageDto.getId(),imageDto.getName());
			dto.getHashMap().put(COLUNM_LIBRARY, imageDto.getImageLibraryDto().getName());
			dto.getHashMap().put(COLUNM_VIEW, createButton(COLUNM_VIEW, dto));
			dtos.add(dto);
		}
		return dtos;
	}
	
	private Button createButton(final String name,final Dto dto) {
		final Button btn = new Button();
		btn.setCaption(name);
		btn.setData(dto.getId());
		btn.setStyleName("link");
		
		btn.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Object id =  event.getButton().getData();
				ImageDto dto = images.get(id);
				ImageViewPopup popup = ImageViewPopup.getInstance();
				popup.show(dto);
			}
		});
		return btn;
	}

	@Override
	public void updateList(){
		final Collection<String> selectedItems =(Collection<String>) this.layoutForPopup.getData();

		String ids = "";
		for (final String item : selectedItems){
			ids = item+",";
		}
		this.selectedData = selectedData.replaceAll("@ids", ids);
	}

}
