package com.contento3.web.common.helper;

import java.util.ArrayList;
import java.util.Collection;
import com.contento3.common.dto.Dto;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * Abstract GenricEntityPicker class
 */
public  class GenricEntityPicker extends AbstractTableBuilder  {

	/**
	 * Used to add or delete item from table
	 */
	private final Button addButton;
	
	/**
	 * Dtos to be listed in table
	 */
	private final Collection<Dto> dtos;
	
	/**
	 * Vertical layout to add components
	 */
	private VerticalLayout vLayout;
	
	/**
	 * Table to add or delete
	 */
	//private static Table table = new Table();
	
	/**
	 * Contains list of columns to be generated
	 */
	private Collection<String> listOfColumns;
	
	/**
	 * Contains list of selected items
	 */
	final Collection<String> selectedItems= new ArrayList<String>();
	
	public GenricEntityPicker(final Collection<Dto> dtos,final Collection<String> listOfColumns,final VerticalLayout vLayout) {
		super(new Table());
		this.listOfColumns = listOfColumns;
		this.addButton = new Button();
		this.dtos = dtos;
		this.vLayout = vLayout;
	}

	/**
	 * build table and add button click listener
	 */
	public void build() {
		table.setPageLength(25);
		build(dtos);
		
		((IndexedContainer) container).sort(new Object[] { "name" }, new boolean[] { true });
		
		final HorizontalLayout addButtonLayout = new HorizontalLayout();
		if(vLayout.getComponentCount()>0){
			vLayout.removeAllComponents();
		}

		this.vLayout.addComponent(table);
		String caption = vLayout.getDescription();
		if( caption == null){
			caption="add";
		}
		
		addButton.setCaption(caption);
		this.vLayout.addComponent(addButtonLayout);
	    addButtonLayout.addComponent(addButton);
	    addButtonLayout.setComponentAlignment(addButton, Alignment.BOTTOM_RIGHT);
	    addButtonLayout.setWidth(100, Sizeable.UNITS_PERCENTAGE);
		buttonlistner();
	}
	

	/**
	 * Add  button click listener
	 * @param table
	 * @param button
	 */
	public void buttonlistner(){
		addButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(ClickEvent event) {
	            for (Object id : table.getItemIds()) {
                	
                     // Get the check-box of this item (row)
                     CheckBox checkBox = (CheckBox) table.getContainerProperty(id, "select").getValue();
                     if (checkBox.booleanValue()) {
                    	 selectedItems.add(table.getContainerProperty(id, "name").getValue().toString());
                     }
                }//end for
                vLayout.setData(selectedItems);
			}
		});
	}

	/**
	 * Insert item into table
	 */
	@Override
	public void assignDataToTable(final Dto dto, final Table table,final Container container) {
		Item item = container.addItem(dto.getName());
		item.getItemProperty("select").setValue(new CheckBox());
		for(String column:listOfColumns){
			item.getItemProperty(column).setValue(dto.getName());
		}
	}

	/**
	 * Build header for table
	 */
	@Override
	public void buildHeader(final Table table, final Container container) {
		container.addContainerProperty("select", CheckBox.class, null);
		for(String column:listOfColumns){
			container.addContainerProperty(column, String.class, null);
		}
		table.setWidth(100, Sizeable.UNITS_PERCENTAGE);
		table.setColumnWidth("select", 40);
		table.setContainerDataSource(container);
	}
	
	/**
	 * Build empty table
	 */
	@Override
	public void buildEmptyTable(final Container container) {
		final Item item = container.addItem("-1");
		item.getItemProperty("name").setValue("No record found.");
	}

}
	
