package com.contento3.web.common.helper;

import java.util.Collection;

import org.springframework.util.CollectionUtils;

import com.contento3.common.dto.Dto;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.TreeTable;

public abstract class AbstractTreeTableBuilder implements TableBuilder<TreeTable,Dto> {

	/**
	 * Table to be build
	 */
	final TreeTable treeTable;

	/**
	 * Container to contain the table data
	 */
	HierarchicalContainer container = new HierarchicalContainer();

	public AbstractTreeTableBuilder (final TreeTable treeTable){
		this.treeTable = treeTable;
	}
	
	/**
	 * Builds the table using the Collection and then returns the table.
	 */
	@Override
	public TreeTable build(final Collection<Dto> dtos) {
		buildHeader(treeTable,container);
		if (!CollectionUtils.isEmpty(dtos)) {
			for (Dto dto : dtos) {
				assignDataToTable(dto, treeTable,container);
			}
			treeTable.setPageLength(dtos.size());
		}
		else{
			//tree.setPageLength(1);
			buildEmptyTable(container);
		}
		return treeTable;
	}

	/**
	 * Rebuilds the tables by first removing the items 
	 * and then add the data passed and then returns the table
	 */
	@Override
	public TreeTable rebuild(final Collection<Dto> dtos){
		treeTable.getContainerDataSource().removeAllItems();
		container = new HierarchicalContainer();
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
	public abstract void assignDataToTable(Dto dto,TreeTable treeTable,HierarchicalContainer container);

	/**
	 * Build the header of the table
	 * @param siteDomainTable
	 * @param domainsContainer
	 */
	public abstract void buildHeader(TreeTable treeTable,HierarchicalContainer container);

	/**
	 * Used to set a message in a table for empty table
	 * @param container
	 */
	public abstract void buildEmptyTable(HierarchicalContainer container);


}
