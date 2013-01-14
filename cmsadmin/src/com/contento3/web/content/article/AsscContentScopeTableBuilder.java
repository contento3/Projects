package com.contento3.web.content.article;

import com.contento3.cms.content.dto.AssociatedContentScopeDto;
import com.contento3.common.dto.Dto;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Table;

public class AsscContentScopeTableBuilder extends AbstractTableBuilder {
	/**
	 * Constructor
	 * @param table
	 */
	public AsscContentScopeTableBuilder(Table table) {
		super(table);
		
	}

	/**
	 * Assign contet scope to table
	 */
	@Override
	public void assignDataToTable(Dto dto, Table scopeTable, Container scopeContainer) {
		AssociatedContentScopeDto contentScope = (AssociatedContentScopeDto) dto;
		Item item = scopeContainer.addItem(contentScope.getId());
		item.getItemProperty("content scope").setValue(contentScope.getName());
	}

	/**
	 * Build header of table
	 */
	@Override
	public void buildHeader(Table scopeTable, Container scopeContainer) {
		scopeContainer.addContainerProperty("content scope", String.class, null);
		scopeTable.setWidth(100, Sizeable.UNITS_PERCENTAGE);
		scopeTable.setContainerDataSource(scopeContainer);

	}

	/**
	 * Build empty table
	 */
	@Override
	public void buildEmptyTable(Container scopeContainer) {

		Item item = scopeContainer.addItem("-1");
		item.getItemProperty("content scope").setValue("No record found.");

	}

}
