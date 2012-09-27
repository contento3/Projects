package com.contento3.web.site.listener;

import com.contento3.cms.page.template.dto.PageTemplateDto;
import com.contento3.cms.page.template.service.PageTemplateService;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class PageTemplateDeleteListner implements ClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Helper to get the spring bean
	 */
	final SpringContextHelper contextHelper;
	
	 /**
     * Represents the parent window of the template ui
     */
	final Window mainWindow;
	
	/**
	 * Associated Template 
	 */
	final Table table;
	
	/**
	 * Template dto to be  delete
	 */
	final PageTemplateDto templateDto;
	
	/**
	 * Constructor
	 * @param helper
	 * @param window
	 * @param table
	 * @param templateDto
	 */
	public PageTemplateDeleteListner(final SpringContextHelper helper,final Window window,final Table table, final PageTemplateDto templateDto) {
		this.contextHelper = helper;
		this.mainWindow = window;
		this.table = table;
		this.templateDto = templateDto;
	}
	
	/**
	 * Button Click Listener for deleting template
	 */
	@Override
	public void buttonClick(final ClickEvent event) {
		PageTemplateService pageTemplateService = (PageTemplateService) this.contextHelper.getBean("pageTemplateService");
		final Object id = ((Button)event.getSource()).getData();
		if(templateDto.getTemplateId()== Integer.parseInt(id.toString())){
			pageTemplateService.delete(templateDto);
			table.removeItem(id);
			table.setPageLength(table.getPageLength()-1);
			mainWindow.showNotification(templateDto.getTemplateName()+" deleted succesfully");
		}
		
	}

}
