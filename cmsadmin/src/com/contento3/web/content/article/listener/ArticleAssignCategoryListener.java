package com.contento3.web.content.article.listener;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.AuthorizationException;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.service.CategoryService;
import com.contento3.common.dto.Dto;
import com.contento3.common.exception.EntityNotFoundException;
import com.contento3.web.common.helper.EntityListener;
import com.contento3.web.common.helper.GenricEntityPicker;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ArticleAssignCategoryListener extends EntityListener implements ClickListener {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = Logger.getLogger(ArticleAssignCategoryListener.class);

	private final VerticalLayout mainLayout;
	
	private Integer articleId;
	
	private Integer accountId;
	
	private SpringContextHelper helper;
	
	private CategoryService categoryService;
	
	private Window mainWindow;
	
	/**
	 * Constructor
	 * @param mainWindow
	 * @param helper
	 * @param articleId
	 * @param accountId
	 */
	public ArticleAssignCategoryListener(final Window mainWindow,final SpringContextHelper helper,final Integer articleId,final Integer accountId){
		this.accountId = accountId;
		this.articleId = articleId;
		this.helper = helper;
		categoryService = (CategoryService)helper.getBean("categoryService");
		mainLayout = new VerticalLayout();
		this.mainWindow = mainWindow;
	}
	
	/**
	 * Button Click Event
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void click(ClickEvent event) {
		//validation article exist
		if(articleId != null){
			try
			{
				final Collection<String> listOfColumns = new ArrayList<String>();
				listOfColumns.add("Categories");
				GenricEntityPicker categoryPicker;
				final Collection<Dto> dtos = (Collection) categoryService.findNullParentIdCategory(accountId);
				setCaption("Add Category");
				categoryPicker = new GenricEntityPicker(dtos,null,listOfColumns,mainLayout,this,true);
				categoryPicker.build(null);
			}
			catch(final AuthorizationException ex) {
				Notification.show("Unauthorized","You are not permitted to assign category to articles",Notification.Type.WARNING_MESSAGE);
			} catch (EntityNotFoundException e) {
				LOGGER.debug("Unable to find entity.");
			} 
		}else{
			//warning message
			Notification.show("Opening failed", "create article first", Notification.Type.WARNING_MESSAGE);
		}
	}
	
	/**
	 * Assign selected category to article
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateList() {
		/* update article */
		
		Collection<String> selectedItems =(Collection<String>) this.mainLayout.getData();
		if(selectedItems != null){
			
			ArticleService articleService = (ArticleService) helper.getBean("articleService");
			ArticleDto article = articleService.findById(articleId);
			for(String name : selectedItems ){
				CategoryDto category;
				try {
					category = categoryService.findById(Integer.parseInt(name));

					// validation
					boolean isAddable = true;
					for (CategoryDto dto : article.getCategoryDtos()) {
						if (dto.getName().equals(category.getName()))
							isAddable = false;
					}
					if (isAddable) {
						article.getCategoryDtos().add(category);

					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (EntityNotFoundException e) {
					LOGGER.debug(e.getMessage());
				}
			}
			
			articleService.update(article);
			Notification.show("Assigned"," successfully assigned to "+article.getHead(),
				Notification.Type.HUMANIZED_MESSAGE);
			
		}
		
	}

}
