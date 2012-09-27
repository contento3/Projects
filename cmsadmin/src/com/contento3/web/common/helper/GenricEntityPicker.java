package com.contento3.web.common.helper;


import java.util.Collection;
import com.contento3.common.dto.Dto;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Table;

/**
 * Abstract GenricEntityPicker class
 */
public abstract class GenricEntityPicker extends AbstractTableBuilder  {

	/**
	 * used to add or delete item from table
	 */
	private final Button addDeleteButton;
	
	/**
	 * contain Collection
	 */
	private final Collection<Dto> dtos;

	/**
	 * Constructor
	 * @param table
	 * @param button
	 * @param dtos
	 */
	public GenricEntityPicker(final Table table,final Button button,final Collection<Dto> dtos) {
		super(table);
		this.addDeleteButton = button;
		this.dtos = dtos;
	}

	/**
	 * build table and add button click listener
	 */
	public void build() {
		build(dtos);
		((IndexedContainer) container).sort(new Object[] { "name" }, new boolean[] { true });
		buttonlistner(table, addDeleteButton);
	}

	/**
	 * Insert item into table
	 */
	@Override
	public void assignDataToTable(final Dto dto, final Table table,final Container container) {
		Item item = container.addItem(dto.getName());
		item.getItemProperty("name").setValue(dto.getName());
		item.getItemProperty("select").setValue(new CheckBox());

	}

	/**
	 * Build header for table
	 */
	@Override
	public void buildHeader(final Table table, final Container container) {

		container.addContainerProperty("select", CheckBox.class, null);
		container.addContainerProperty("name", String.class, null);
		table.setWidth(100, Sizeable.UNITS_PERCENTAGE);
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

	/**
	 * Add or delete buttonc click listner
	 * @param table
	 * @param button
	 */
	public abstract void buttonlistner(final Table table, final Button button);

}
	
