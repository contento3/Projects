package com.contento3.web.site.seo;

import com.contento3.cms.seo.dto.MetaTagDto;
import com.contento3.common.dto.Dto;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;

public class SEOTableBuilder extends AbstractTableBuilder {

	private final static String TABLE_COLUMN_ATTRIBUTE = "Attribute" ;
	private final static String TABLE_COLUMN_VALUE = "Value";
	private final static String TABLE_COLUMN_CONTENT_VALUE = "Content Value";
	private final static String TABLE_COLUMN_EDIT = "Edit";
	private final static String TABLE_COLUMN_DELETE = "Delete";
	
	public SEOTableBuilder(Table table) {
		super(table);
	
	}

	@Override
	public void assignDataToTable(Dto dto, Table table, Container container) {
		
		MetaTagDto tag = (MetaTagDto) dto;
		addItem(tag);
	}
	
	@SuppressWarnings("unchecked")
	public void addItem(final MetaTagDto tagDto){

		Item item = container.addItem(tagDto.getMetaTagId());
		item.getItemProperty(TABLE_COLUMN_ATTRIBUTE).setValue(tagDto.getAttribute());
		item.getItemProperty(TABLE_COLUMN_VALUE).setValue(tagDto.getAttributeValue());
		item.getItemProperty(TABLE_COLUMN_CONTENT_VALUE).setValue(tagDto.getAttributeContent());
		
		Button edit = new Button(TABLE_COLUMN_EDIT);
		edit.setData(tagDto.getMetaTagId());
		edit.addStyleName("link");
		item.getItemProperty(TABLE_COLUMN_EDIT).setValue(edit);
		
		Button delete = new Button(TABLE_COLUMN_DELETE);
		delete.setData(tagDto.getMetaTagId());
		delete.addStyleName("link");
		item.getItemProperty(TABLE_COLUMN_DELETE).setValue(delete);
	}

	@Override
	public void buildHeader(Table table, Container container) {

		container.addContainerProperty(TABLE_COLUMN_ATTRIBUTE, String.class, null);
		container.addContainerProperty(TABLE_COLUMN_VALUE, String.class, null);
		container.addContainerProperty(TABLE_COLUMN_CONTENT_VALUE, String.class, null);
		container.addContainerProperty(TABLE_COLUMN_EDIT, Button.class, null);
		container.addContainerProperty(TABLE_COLUMN_DELETE, Button.class, null);
		
		table.setWidth(80, Unit.PERCENTAGE);
		table.setContainerDataSource(container);
		table.setSelectable(true);
		table.setMultiSelect(false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void buildEmptyTable(Container container) {

		super.buildEmptyTable(container);
		Item item = container.addItem("-1");
		item.getItemProperty(TABLE_COLUMN_ATTRIBUTE).setValue("No record found.");
	}
	
	public void increaseTablePageSize() {
		super.table.setPageLength(table.getPageLength() + 1);
	}
}
