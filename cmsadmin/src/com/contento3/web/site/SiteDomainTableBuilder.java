package com.contento3.web.site;

import com.contento3.cms.site.structure.domain.dto.SiteDomainDto;
import com.contento3.cms.site.structure.domain.model.SiteDomain;
import com.contento3.cms.site.structure.domain.service.SiteDomainService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.common.dto.Dto;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.site.listener.EntityDeleteClickListener;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

/**
 * Implementation of TableBuilder for 
 * SiteDomains table
 * @author HAMMAD
 *
 */
public class SiteDomainTableBuilder extends AbstractTableBuilder {
	
	/**
	 * Helper to get the spring bean
	 */
	final SpringContextHelper contextHelper;
	
	final Window window;
	
	final SiteService siteService;
	
	public SiteDomainTableBuilder(final Window window,final SpringContextHelper helper,final Table table){
		super(table);
		this.contextHelper = helper;
		this.window = window;
		this.siteService = (SiteService)contextHelper.getBean("siteService");
	}
	
	@Override
	public void assignDataToTable(final Dto dto,final Table siteDomainTable,final Container domainsContainer) {
		final SiteDomainDto siteDomainDto = (SiteDomainDto)dto;
		final Item item = domainsContainer.addItem(siteDomainDto.getDomainId());
		item.getItemProperty("Domains").setValue(siteDomainDto.getDomainName());

		SiteDomainService siteDomainService = (SiteDomainService)contextHelper.getBean("siteDomainService");
		final Button deleteLink = new Button();
		deleteLink.setCaption("Delete");
		deleteLink.setData(siteDomainDto.getDomainId());
		deleteLink.setStyleName(BaseTheme.BUTTON_LINK);
		item.getItemProperty("Delete").setValue(deleteLink);
		deleteLink.addListener(new EntityDeleteClickListener<SiteDomainDto>(siteDomainDto,siteDomainService,deleteLink,siteDomainTable));

		SiteDto siteDto = siteService.findSiteByDomain(siteDomainDto.getDomainName());
		final Button editLink = new Button("Edit Domain", new SiteDomainPopup(window, contextHelper,siteDto.getSiteId(),siteDomainTable), "openButtonClick");

		editLink.setCaption("Edit");
		editLink.setData(siteDomainDto.getDomainId());
		editLink.setStyleName(BaseTheme.BUTTON_LINK);
		item.getItemProperty("Edit").setValue(editLink);
		editLink.addListener(new EntityDeleteClickListener<SiteDomainDto>(siteDomainDto,siteDomainService,deleteLink,siteDomainTable));
	}

	@Override
	public void buildHeader(final Table siteDomainTable,final Container domainsContainer) {
		domainsContainer.addContainerProperty("Domains", String.class, null);
		domainsContainer.addContainerProperty("Delete", Button.class, null);
		domainsContainer.addContainerProperty("Edit", Button.class, null);

		siteDomainTable.setWidth(100, Sizeable.UNITS_PERCENTAGE);
		siteDomainTable.setContainerDataSource(domainsContainer);
	}

	@Override
	public void buildEmptyTable(final Container domainsContainer){
		final Item item = domainsContainer.addItem("-1");
		item.getItemProperty("Domains").setValue("No record found.");
	}
		

}
