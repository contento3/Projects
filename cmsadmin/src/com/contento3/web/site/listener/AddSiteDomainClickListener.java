package com.contento3.web.site.listener;

import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.site.SiteDomainPopup;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Table;

public class AddSiteDomainClickListener implements ClickListener {

	private SpringContextHelper contextHelper;
	private Table siteDomainTable;
	private Integer siteId;

	public AddSiteDomainClickListener(SpringContextHelper contextHelper,
			Integer siteId, Table siteDomainTable) {
		// TODO Auto-generated constructor stub
		this.contextHelper = contextHelper;
		this.siteId = siteId;
		this.siteDomainTable = siteDomainTable;
	}

	@Override
	public void click(ClickEvent event) {
		// TODO Auto-generated method stub
		SiteDomainPopup temp = new SiteDomainPopup(contextHelper,siteId,siteDomainTable);
		temp.openButtonClick();
	}

}
