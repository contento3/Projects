package com.contento3.web.common.helper;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.util.CollectionUtils;

import com.contento3.common.dto.Dto;
import com.contento3.common.dto.TreeDto;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MultiSelectMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;

/**
 *Construct Generic Tree Table
 */
public class GenericTreeTableBuilder extends AbstractTreeTableBuilder {

	/**
	 * Dtos to be listed in table
	 */
	private final Collection<Dto> dtos;
	
	/**
	 * Vertical layout to add components
	 */
	private VerticalLayout vLayout;
	
	/**
	 * Contains list of columns to be generated
	 */
	private Collection<String> listOfColumns;

	/**
	 * Used to add or delete item from table
	 */
	public final Button saveButton;
	
	/**
	 * Contains list of selected items
	 */
	final Collection<String> selectedItems= new ArrayList<String>();
	
	final EntityListener entityListener;

	final Collection<Dto> assignedDtos;
	
	/**
	 * Constructor
	 * @param dtos
	 * @param listOfColumns
	 * @param vLayout
	 */
	public GenericTreeTableBuilder(final EntityListener entityListener,final Collection<Dto> dtos,final Collection <Dto> assignedDtos,final Collection<String> listOfColumns,final VerticalLayout vLayout) {
		super(new TreeTable());
		this.listOfColumns = listOfColumns;
		this.saveButton = new Button();
		this.dtos = dtos;
		this.vLayout = vLayout;
		this.entityListener = entityListener;
		this.assignedDtos = assignedDtos;
	}

	/**
	 * build table and add button click listener
	 */
	public void build() {
	
		build(dtos);
		Object[] obj = new Object[1];
		obj[0] = listOfColumns.iterator().next().toString();
		container.sort(obj, new boolean[] { true });
		final HorizontalLayout addButtonLayout = new HorizontalLayout();
		this.vLayout.addComponent(treeTable);
		this.vLayout.setSpacing(true);
		saveButton.setCaption("Save");
		this.vLayout.addComponent(addButtonLayout);
	    addButtonLayout.addComponent(saveButton);
	    addButtonLayout.setComponentAlignment(saveButton, Alignment.BOTTOM_RIGHT);
	    addButtonLayout.setWidth(100, Unit.PERCENTAGE);
		treeTable.setSelectable(true);
		treeTable.setMultiSelect(true);
		treeTable.setMultiSelectMode(MultiSelectMode.SIMPLE);
	    buttonlistner();
	}
	
	/**
	 * Assign date to tree table
	 */
	@Override
	public void assignDataToTable(final Dto dto,final TreeTable treeTable,final HierarchicalContainer container) {
		final TreeDto treeDto = (TreeDto) dto;
		addItem(container,treeDto,null,treeTable);
	}

	/**
	 * Add item in tree table
	 * @param container
	 * @param dto
	 * @param parentItem
	 * @param treeTable
	 */
	private void addItem(final HierarchicalContainer container,final TreeDto dto,final TreeDto parentItem,final TreeTable treeTable){
		final Integer id = dto.getId();
		addNewItem(container,dto,treeTable);

		if (null!=parentItem){
			container.setParent(id,parentItem.getId());
			container.setChildrenAllowed(parentItem.getId(), true);
		}

		final Collection <TreeDto> children =  dto.getChildDto();
		if (!CollectionUtils.isEmpty(children)){
			for(TreeDto childDto : children){
				addItem(container,childDto,dto,treeTable);
			}
		}
	}

	/**
	 * Add new item to tree table
	 * @param container
	 * @param dto
	 * @param treeTable
	 */
	private void addNewItem(final HierarchicalContainer container,final TreeDto dto,final TreeTable treeTable){
		
		final Integer id = dto.getId();
		Item item = container.addItem(id);

		item.getItemProperty(listOfColumns.iterator().next()).setValue(dto.getName());

		if(null!=assignedDtos && assignedDtos.contains(dto)){
			this.treeTable.select(id);
		}
		else {
			this.treeTable.unselect(id);
		}
		
		for(String column:listOfColumns){
			item.getItemProperty(column).setValue(dto.getName());
		}
	}

	/**
	 * Handle Save event
	 */
	public void buttonlistner() {
		saveButton.addClickListener(new ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {	
				 for (Object id : treeTable.getItemIds()) {
	                	
                     // Get the check-box of this item (row)
                     if (treeTable.isSelected(id)) {
                    	 selectedItems.add(id.toString());
                     }
                }//end for
				 vLayout.setData(selectedItems);//adding selected item into vLayout
	                entityListener.updateList();
			}
		});
		
	}

	/**
	 * Build header for tree table
	 */
	@Override
	public void buildHeader(TreeTable treeTable, HierarchicalContainer container) {
		for(String column:listOfColumns){
			container.addContainerProperty(column, String.class, null);
		}
		treeTable.setWidth(100, Unit.PERCENTAGE);
		treeTable.setContainerDataSource(container);
		treeTable.setSelectable(true);
		treeTable.setMultiSelect(false);
	}

	/**
	 * Build empty table
	 */
	@Override
	public void buildEmptyTable(HierarchicalContainer container) {
		final Item item = container.addItem("-1");
		item.getItemProperty(listOfColumns.iterator().next()).setValue("No record found.");
	}

}
