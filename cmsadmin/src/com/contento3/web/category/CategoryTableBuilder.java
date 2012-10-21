package com.contento3.web.category;

import java.util.Collection;

import org.springframework.util.CollectionUtils;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.service.CategoryService;
import com.contento3.cms.site.structure.domain.dto.SiteDomainDto;
import com.contento3.common.dto.Dto;
import com.contento3.web.common.helper.AbstractTreeTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.site.listener.EntityDeleteClickListener;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

public class CategoryTableBuilder extends AbstractTreeTableBuilder {

	/**
	 * Helper to get the spring bean
	 */
	final SpringContextHelper contextHelper;
	
	 /**
     * Represents the parent window of the ui
     */
	final Window window;
	
	/**
	 * TabSheet serves as the parent container for the article manager
	 */
	private TabSheet tabSheet;
			
	/**
	 * Article service used for article related operations
	 */
	final CategoryService categoryService;
	
	
	public CategoryTableBuilder(final Window window,final SpringContextHelper helper,final TabSheet tabSheet,final TreeTable treeTable) {
		super(treeTable);
		this.contextHelper = helper;
		this.window = window;
		this.tabSheet = tabSheet;
		this.categoryService = (CategoryService) contextHelper.getBean("categoryService");
	}

	@Override
	public void assignDataToTable(final Dto dto,final TreeTable treeTable,final HierarchicalContainer container) {
		final CategoryDto category = (CategoryDto) dto;
		addItem(container,category,null,treeTable);
	}

	private void addItem(final HierarchicalContainer container,final CategoryDto category,final CategoryDto parentCategory,final TreeTable treeTable){
		final Integer categoryId = category.getCategoryId();
		addNewItem(container,category,treeTable);
			
		if (null!=parentCategory){
			container.setParent(categoryId,parentCategory.getCategoryId());
			container.setChildrenAllowed(parentCategory.getCategoryId(), true);
		}
			
		final Collection <CategoryDto> children = category.getChild();
		if (!CollectionUtils.isEmpty(children)){
			for(CategoryDto categoryChild : children){
				addItem(container,categoryChild,category,treeTable);
			}
		}
	}
		
	private void addNewItem(final HierarchicalContainer container,final CategoryDto category,final TreeTable treeTable){
		final Integer categoryId = category.getCategoryId();
		Item item = container.addItem(categoryId);
		item.getItemProperty("category").setValue(category.getName());
		
		Button editButton = new Button("Edit", new CategoryPopup(window, contextHelper,(TreeTable)treeTable,tabSheet), "openButtonClick");
		editButton.setStyleName(BaseTheme.BUTTON_LINK);
		editButton.setData(categoryId);

		Button deleteButton = new Button("Delete");
		deleteButton.setStyleName(BaseTheme.BUTTON_LINK);
		deleteButton.setData(categoryId);
		deleteButton.addListener(new EntityDeleteClickListener<CategoryDto>(category,categoryService,deleteButton,treeTable));
		
		item.getItemProperty("Edit").setValue(editButton);
		item.getItemProperty("Delete").setValue(deleteButton);
	}
	
	
	@Override
	public void buildHeader(final TreeTable treeTable,final HierarchicalContainer container) {
		container.addContainerProperty("category", String.class, null);
		container.addContainerProperty("Edit", Button.class, null);
		container.addContainerProperty("Delete", Button.class, null);

		treeTable.setWidth(100, Sizeable.UNITS_PERCENTAGE);
		treeTable.setContainerDataSource(container);
		treeTable.setSelectable(true);
		treeTable.setMultiSelect(false);
	}

	@Override
	public void buildEmptyTable(final HierarchicalContainer container) {
		Item item = container.addItem("-1");
		item.getItemProperty("category").setValue("No record found.");
	}

}
