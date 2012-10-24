package com.contento3.web.content.article.listener;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.cms.page.category.service.CategoryService;
import com.contento3.common.dto.Dto;
import com.contento3.web.common.helper.GenricEntityPicker;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.VerticalLayout;

public class ArticleAssignCategoryListener implements ClickListener {

	private static final long serialVersionUID = 1L;

	private final VerticalLayout mainLayout;
	
	private Integer articleId;
	
	private Integer accountId;
	
	private SpringContextHelper helper;
	
	private CategoryService categoryService;
	
	public ArticleAssignCategoryListener(final SpringContextHelper helper,final Integer articleId,final Integer accountId){
		this.accountId = accountId;
		this.articleId = articleId;
		this.helper = helper;
		categoryService = (CategoryService)helper.getBean("categoryService");
		mainLayout = new VerticalLayout();
	}
	
	@Override
	public void click(ClickEvent event) {
		Collection<String> listOfColumns = new ArrayList<String>();
		listOfColumns.add("name");
		GenricEntityPicker categoryPicker;
		Collection<Dto> dtos = null;
		dtos = (Collection) categoryService.findNullParentIdCategory(accountId);
		categoryPicker = new GenricEntityPicker(dtos,listOfColumns,mainLayout);
		categoryPicker.build();
	}

}
