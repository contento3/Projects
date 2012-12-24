package com.contento3.web.content.article;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.common.dto.Dto;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Table;

public class AssociatedCategoryTableBuilder extends AbstractTableBuilder {

	/**
	 * Constructor
	 * @param categoryTable
	 */
	public AssociatedCategoryTableBuilder(final Table categoryTable) {
		super(categoryTable);		
	}

	/**
	 * Assign associated category to table
	 */
	@Override
	public void assignDataToTable(final Dto dto,final Table categoryTable,final Container categoryContainer) {
		CategoryDto category = (CategoryDto) dto;
		Item item = categoryContainer.addItem(category.getCategoryId());
		item.getItemProperty("category").setValue(category.getName());

	}

	/**
	 * Build header of associates category table
	 */
	@Override
	public void buildHeader(final Table categoryTable,final Container categoryContainer) {
		categoryContainer.addContainerProperty("category", String.class, null);
		categoryTable.setWidth(100, Sizeable.UNITS_PERCENTAGE);
		categoryTable.setContainerDataSource(categoryContainer);
		
	}

	/**
	 * Build empty table
	 */
	@Override
	public void buildEmptyTable(final Container categoryContainer) {
		Item item = categoryContainer.addItem("-1");
		item.getItemProperty("category").setValue("No record found.");
	}

}
