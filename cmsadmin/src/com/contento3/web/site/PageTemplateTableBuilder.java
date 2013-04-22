/**
 * 
 */
package com.contento3.web.site;

import com.contento3.cms.page.template.dto.PageTemplateDto;
import com.contento3.cms.page.template.dto.TemplateDirectoryDto;
import com.contento3.cms.page.template.service.PageTemplateService;
import com.contento3.cms.page.template.service.TemplateService;
import com.contento3.common.dto.Dto;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.site.listener.EntityDeleteClickListener;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

/**
 *Used to create table for pagetemplates
 * @author XINEX
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
	
	final TemplateService templateService;
	
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
		this.templateService = (TemplateService)helper.getBean("templateService");
	}

	/**
	 *Assign associated template to table
	 */
	@Override
	public void assignDataToTable(final Dto dto,final  Table table,final Container container) {
		PageTemplateDto templateDto = (PageTemplateDto) dto;
		TemplateDirectoryDto templateDirDto = templateService.findTemplateById(templateDto.getTemplateId()).getTemplateDirectoryDto();
		Item item = container.addItem(templateDto.getTemplateId());
		item.getItemProperty("associated templates").setValue(templateDto.getTemplateName());
		item.getItemProperty("url").setValue(buildPath(templateDirDto, templateDto.getTemplateName()));
		item.getItemProperty("order").setValue(templateDto.getOrder());
		
		// adding delete button item into list
		final Button deleteLink = new Button();
		deleteLink.setCaption("Delete");
		deleteLink.setData(templateDto.getTemplateId());
		deleteLink.addStyleName("delete");
		deleteLink.setStyleName(BaseTheme.BUTTON_LINK);
		item.getItemProperty("delete").setValue(deleteLink);
		PageTemplateService service = (PageTemplateService) this.contextHelper.getBean("pageTemplateService");
		deleteLink.addListener(new EntityDeleteClickListener<PageTemplateDto>(templateDto,service,deleteLink,table));
		
		((IndexedContainer) container).sort(new Object[] { "order" }, new boolean[] { true });
	}

	/**
	 * Create associated template table header
	 */
	@Override
	public void buildHeader(final Table table,final Container container) {
		container.addContainerProperty("associated templates", String.class, null);
		container.addContainerProperty("url", String.class, null);
		container.addContainerProperty("order", String.class, null);
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
	
	/**
	 * Build template path
	 */
	private String buildPath(TemplateDirectoryDto templateDirDto, String path) {
		if (templateDirDto.getParent() == null) {
			return templateDirDto.getDirectoryName() + "/" + path;
		} else {
			return buildPath(templateDirDto.getParent(),
					templateDirDto.getDirectoryName() + "/" + path);
		}
	}
}
