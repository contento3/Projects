package com.contento3.web.category;

import com.contento3.cms.page.category.service.CategoryService;
import com.contento3.web.UIManager;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

public class CategoryUIManager implements UIManager {

	/**
	 * Used to get service beans from spring context.
	 */
    private SpringContextHelper helper;
    
    /**
     * Category Service to access category related services.
     */
	private CategoryService categoryService;
	
	/**
     * Represents the parent window of the category ui
     */
	private Window parentWindow;
	
	public CategoryUIManager(final SpringContextHelper helper,final Window parentWindow) {
		this.helper = helper;
		this.parentWindow = parentWindow;
	    this.categoryService = (CategoryService)helper.getBean("categoryService");
	}
	
	@Override
	public void render() {
		// TODO Auto-generated method stub

	}

	@Override
	public Component render(String command) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component render(String command, Integer entityFilterId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component render(String command,
			HierarchicalContainer treeItemContainer) {
		// TODO Auto-generated method stub
		return null;
	}

}
