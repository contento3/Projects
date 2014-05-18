package com.contento3.web.common.helper;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.util.CollectionUtils;

import com.contento3.common.dto.Dto;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class GenricEntityTableBuilder  extends AbstractTableBuilder {
	
	public final static String COLUNM_VIEW 	= "view";

	/**
	 * Used to add or delete item from table
	 */
	public final Button addButton;
	
	/**
	 * Dtos to be listed in table
	 */
	private final Collection<Dto> dtos;
	
	private Collection<Dto> assignedDtos;
	
	/**
	 * Vertical layout to add components
	 */
	private VerticalLayout vLayout;
	
	/**
	 * Contains list of columns to be generated
	 */
	private Collection<String> listOfColumns;
	
	/**
	 * Contains list of selected items
	 */
	final Collection<String> selectedItems= new ArrayList<String>();
	
	final EntityListener entityListener;
	
	final GenricEntityPicker entityPicker;
	/**
	 * Constructor
	 * @param dtos
	 * @param listOfColumns
	 * @param vLayout
	 */
	public GenricEntityTableBuilder(final EntityListener entityListener,final GenricEntityPicker entityPicker, final Collection<Dto> dtos,final Collection<Dto> assignedDtos,final Collection<String> listOfColumns,final VerticalLayout vLayout) {
		super(new Table());
		this.listOfColumns = listOfColumns;
		this.addButton = new Button();
		this.dtos = dtos;
		this.vLayout = vLayout;
		this.assignedDtos = assignedDtos;
		this.entityListener = entityListener;
		this.entityPicker = entityPicker;
	}
	
	
	/**
	 * build table and add button click listener
	 */
	public void build() {
		table.setPageLength(15);
		build(dtos);
		Object[] obj = new Object[1];
		obj[0] = listOfColumns.iterator().next().toString();
		((IndexedContainer) container).sort(obj, new boolean[] { true });
		final HorizontalLayout addButtonLayout = new HorizontalLayout();
		this.vLayout.addComponent(table);
		this.vLayout.setSpacing(true);
		addButton.setCaption("Save");
		this.vLayout.addComponent(addButtonLayout);
	    addButtonLayout.addComponent(addButton);
	    addButtonLayout.setComponentAlignment(addButton, Alignment.BOTTOM_RIGHT);
	    addButtonLayout.setWidth(100, Unit.PERCENTAGE);
		buttonlistner();
	}
	

	/**
	 * Add  button click listener
	 * @param table
	 * @param button
	 */
	public void buttonlistner(){
		addButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(ClickEvent event) {
	            for (Object id : table.getItemIds()) {
                	
                     // Get the check-box of this item (row)
                     CheckBox checkBox = (CheckBox) table.getContainerProperty(id, "select").getValue();
                     if (checkBox.getValue()!=null && checkBox.getValue()) {
                    	 selectedItems.add(id.toString());
                     }
                }//end for
                vLayout.setData(selectedItems);//adding selected item into vLayout
                entityListener.updateList();
			}
		});
	}

	/**
	 * Insert item into table
	 */
	@Override
	public void assignDataToTable(final Dto dto, final Table table,final Container container) {
		Item item = container.addItem(dto.getId());
		CheckBox checkBox = new CheckBox();
		
		if(!CollectionUtils.isEmpty(assignedDtos) && assignedDtos.contains(dto)){
			checkBox.setValue(true);
		}
		else {
			checkBox.setValue(false);
		}
		
		item.getItemProperty("select").setValue(checkBox);
		Object value = null;
		
		for(String column:listOfColumns){
			
			if (dto.getHashMap().containsKey(column)) {
				value = dto.getHashMap().get(column);
			} else {
				value = dto.getName();
			}
			item.getItemProperty(column).setValue(value);

		}
	}

	
	
	/**
	 * Build header for table
	 */
	@Override
	public void buildHeader(final Table table, final Container container) {
		container.addContainerProperty("select", CheckBox.class, null);
	
		for(String column:listOfColumns){
			
			if( column.toLowerCase().equals(COLUNM_VIEW) ){
				container.addContainerProperty(column, Button.class, null);
			} else {
				container.addContainerProperty(column, String.class, null);
			}
		}
		table.setWidth(100, Unit.PERCENTAGE);
		table.setColumnWidth("select", 40);
		table.setContainerDataSource(container);
	}
	
	/**
	 * Build empty table
	 */
	@Override
	public void buildEmptyTable(final Container container) {
		final Item item = container.addItem("-1");
		item.getItemProperty(listOfColumns.iterator().next()).setValue("No record found.");
	}

	public void setAssignedDto (final Collection <Dto> assignedDto){
		this.assignedDtos = assignedDto;
	}
}
