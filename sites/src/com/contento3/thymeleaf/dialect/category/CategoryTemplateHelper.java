package com.contento3.thymeleaf.dialect.category;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionEnhancingDialect;
import org.thymeleaf.processor.IProcessor;

import com.amazonaws.http.HttpRequest;
import com.contento3.cms.page.category.dao.impl.CategoryDaoHibernateImpl;
import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.service.CategoryService;
import com.contento3.cms.page.category.service.impl.CategoryServiceImpl;
import com.contento3.cms.page.dao.impl.PageDaoHibernateImplTest;
import com.contento3.site.template.model.TemplateModelMap;
import com.contento3.site.template.model.TemplateModelMapImpl;

public class CategoryTemplateHelper {
    /**
     * Format a Joda DateTime using the given pattern.
     *
     * @param datetime
     * @param pattern
     * @return Formatted date string.
     */
	CategoryService categoryService ;
    public ArrayList<CategoryDto> showCategoryListing(final int catId,final int accountId) {
            final Collection<CategoryDto> temp = this.categoryService.findChildCategories(catId,accountId);
            ArrayList<CategoryDto> categoryList = new ArrayList<CategoryDto>();
//            String html ="<ul>";
            for (final CategoryDto categoryDto : temp) {
//				html += "<li>" + categoryDto.getName() + "</li>";
				categoryList.add(categoryDto);
			}
//            html += "</ul>";
            return categoryList;
    }
	public CategoryTemplateHelper() {
		ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		this.categoryService = (CategoryService) ctx.getBean("categoryService");
	}
    
}