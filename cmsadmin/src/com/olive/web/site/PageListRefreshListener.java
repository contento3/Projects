package com.olive.web.site;

import com.github.wolfie.refresher.Refresher;
import com.github.wolfie.refresher.Refresher.RefreshListener;
import com.olive.cms.page.dto.PageDto;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;

public class PageListRefreshListener implements RefreshListener{

	private IndexedContainer container;
	private PageDto pageDtoWithLayout;
	private boolean flag;
	
	public PageListRefreshListener(final IndexedContainer container,final PageDto pageDtoWithLayout,final boolean flag){
		this.container = container;
		this.pageDtoWithLayout = pageDtoWithLayout;
		this.flag = flag;
	}
	
	private static final long serialVersionUID = 1L;

	@Override
	public void refresh(Refresher source) {
		if (flag){
			Item item = container.addItem(pageDtoWithLayout.getPageId());
			item.getItemProperty("Title").setValue(pageDtoWithLayout.getTitle());
			item.getItemProperty("Uri").setValue(pageDtoWithLayout.getUri());
			Button link = new Button();
			link.setCaption("Edit");
			link.setData(pageDtoWithLayout.getPageId());
			link.addStyleName("link");
			item.getItemProperty("Edit").setValue(link);
		}
	}
}
