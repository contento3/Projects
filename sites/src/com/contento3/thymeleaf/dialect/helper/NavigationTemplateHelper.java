package com.contento3.thymeleaf.dialect.helper;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.catalina.ha.backend.CollectedInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.service.CategoryService;
import com.contento3.cms.page.service.PageService;



public class NavigationTemplateHelper {
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
		Collection<CategoryDto> childCategories = categoryService.findChildCategories(menuCategoryId, accountId);
		pageService.findPagesByCategory(new ArrayList<Integer>(), siteId, accountId);
		return new ArrayList();
	}
}
