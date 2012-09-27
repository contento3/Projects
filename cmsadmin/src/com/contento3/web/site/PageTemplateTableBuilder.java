/**
 * 
 */
package com.contento3.web.site;

import com.contento3.cms.page.template.dto.PageTemplateDto;
import com.contento3.common.dto.Dto;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.site.listener.PageTemplateDeleteListner;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

/**
 * @author XINEX
 *
 */
public class PageTemplateTableBuilder extends AbstractTableBuilder {

	/**
	 * Helper to get the spring bean
	 */
	final SpringContextHelper contextHelper;
	
	 /**
     * Represents the parent window of the template ui
     */
	final Window mainWindow;
	
	/**
	 * Constructor
	 * @param helper
	 * @param window
	 * @param table
	 */
	public PageTemplateTableBuilder(final SpringContextHelper helper,final Window window,final Table table) {
		super(table);
		this.contextHelper = helper;
		this.mainWindow = window;
		
	}

	/**
	 *Assign associated template to table
	 */
	@Override
	public void assignDataToTable(final Dto dto,final  Table table,final Container container) {
		PageTemplateDto templateDto = (PageTemplateDto) dto;
		Item item = container.addItem(templateDto.getTemplateId());
		item.getItemProperty("associated templates").setValue(templateDto.getTemplateName());
		// adding delete button item into list
		final Button deleteLink = new Button();
		deleteLink.setCaption("Delete");
		deleteLink.setData(templateDto.getTemplateId());
		deleteLink.addStyleName("delete");
		deleteLink.setStyleName(BaseTheme.BUTTON_LINK);
		item.getItemProperty("delete").setValue(deleteLink);
		deleteLink.addListener(new PageTemplateDeleteListner(this.contextHelper, this.mainWindow, table, templateDto));
	}

	/**
	 * Create associated template table header
	 */
	@Override
	public void buildHeader(final Table table,final Container container) {
		container.addContainerProperty("associated templates", String.class, null);
		container.addContainerProperty("delete", Button.class, null);
		table.setWidth(100, Sizeable.UNITS_PERCENTAGE);
		table.setContainerDataSource(container);

	}

	/**
	 * Create empty associated template table 
	 */
	@Override
	public void buildEmptyTable(final Container container) {
		final Item item = container.addItem("-1");
		item.getItemProperty("associated templates").setValue("No template associated.");

	}

}
