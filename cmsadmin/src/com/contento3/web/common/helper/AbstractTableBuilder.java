package com.contento3.web.common.helper;

import java.util.Collection;

import org.springframework.util.CollectionUtils;

import com.contento3.common.dto.Dto;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Table;

public abstract class AbstractTableBuilder implements TableBuilder<Table,Dto> {
	
	/**
	 * Table to be build
	 */
	final Table table;

	/**
	 * Container to contain the table data
	 */
	Container container = new IndexedContainer();

	public AbstractTableBuilder (final Table table){
		this.table = table;
	}
	
	/**
	 * Builds the table using the Collection and then returns the table.
	 */
	public Table build(final Collection<Dto> dtos) {
		buildHeader(table,container);
		if (!CollectionUtils.isEmpty(dtos)) {
			for (Dto dto : dtos) {
				assignDataToTable(dto, table,container);
			}
			table.setPageLength(dtos.size());
		}
		else{
			table.setPageLength(1);
			buildEmptyTable(container);
		}
		
		return table;
	}
	
	/**
	 * Rebuilds the tables by first removing the items 
	 * and then add the data passed and then returns the table
	 */
	public Table rebuild(final Collection<Dto> dtos){
		table.getContainerDataSource().removeAllItems();
		container = new IndexedContainer();
		return build(dtos);
	}
	
	/**
	 * Assign data to table.This is left to be implemented 
	 * to the actual implementation so that the use can 
	 * customize based on the requirement of the table.
	 * @param dto
	 * @param table
	 * @param container
	 */
	public abstract void assignDataToTable(Dto dto,Table table,Container container);

	/**
	 * Build the header of the table
	 * @param siteDomainTable
	 * @param domainsContainer
	 */
	public abstract void buildHeader(Table table,Container container);

	/**
	 * Create empty table
	 * @param userContainer
	 */
	public void buildEmptyTable(final Container container){
		final Item item = container.addItem("-1");
	}

}
