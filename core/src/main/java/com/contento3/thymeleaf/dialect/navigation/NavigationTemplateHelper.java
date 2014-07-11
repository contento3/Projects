package com.contento3.thymeleaf.dialect.navigation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.service.CategoryService;
import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.service.PageService;
import com.contento3.cms.page.service.impl.PageServiceImpl;
import com.contento3.common.exception.EntityNotFoundException;


public class NavigationTemplateHelper {
	
	private static final Logger LOGGER = Logger.getLogger(NavigationTemplateHelper.class);

	CategoryService categoryService ;
	PageService pageService ;
	
	public NavigationTemplateHelper() {
		super();
		ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		this.categoryService = (CategoryService) ctx.getBean("categoryService");
		this.pageService = (PageService) ctx.getBean("pageService");
	}
	
	Collection<String> findSiteNavigation(final Integer siteId, final Integer accountId, final Integer menuCategoryId){
		// in progress
		String[][] navigationArray = new String[][]{};
		Collection<CategoryDto> childCategories;
		try {
			childCategories = categoryService.findChildCategories(menuCategoryId, accountId);
	
			Integer arrayLenght = 0;
			for (CategoryDto categoryDto : childCategories) {
				Collection<PageDto> pages = pageService.findPagesByCategory(new ArrayList<Integer>(Arrays.asList(categoryDto.getId())), siteId, accountId);
				if(! CollectionUtils.isEmpty(pages)){
					for (PageDto pageDto : pages) {
						navigationArray[arrayLenght++] =  new String[]{categoryDto.getName(), pageDto.getUri()};
					} 
				}
			}
		} catch (EntityNotFoundException e) {
			LOGGER.debug(e.getMessage());
		}
		pageService.findPagesByCategory(new ArrayList<Integer>(), siteId, accountId);
		return new ArrayList();
	}
}
