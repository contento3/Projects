package com.contento3.web.site.listener;

import com.contento3.cms.site.structure.domain.dto.SiteDomainDto;
import com.contento3.cms.site.structure.domain.service.SiteDomainService;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.site.SiteDomainPopup;
import com.vaadin.data.Item;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;

public class DomainDeleteClickListener implements ClickListener {

	private static final long serialVersionUID = 3126526402867446357L;

	private final SiteDomainDto siteDomain;
	
	private final SpringContextHelper contextHelper;
	
	private final Button deleteLink;
	
	private final Table table;
	
	public DomainDeleteClickListener(final SiteDomainDto siteDomain,final SpringContextHelper helper,final Button deleteLink,final Table table){
		this.siteDomain = siteDomain;
		this.contextHelper = helper;
		this.table = table;
		this.deleteLink = deleteLink;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		final SiteDomainService siteDomainService = (SiteDomainService) contextHelper.getBean("siteDomainService");
		final String caption =(String)((Button)event.getSource()).getCaption();

		if (caption.equals("Edit")){
			
		}
		else {
		final Object id = deleteLink.getData();
		final String domainName = (String) table.getContainerProperty(id,"Domains").getValue();
			if(siteDomain.getDomainName().equals(domainName)){
				siteDomainService.delete(siteDomain);
				table.removeItem(id);
				table.setPageLength(table.getPageLength()-1);
			}
		}	
	}
}	