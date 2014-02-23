package com.contento3.web.content.image.listener;

import com.contento3.web.content.image.ImageMgmtUIManager;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.TabSheet.Tab;

public class AddImageButtonListener implements ClickListener {

	private TabSheet tabSheet;
	private ImageMgmtUIManager imageMgmtUIManager;

	public AddImageButtonListener(TabSheet tabSheet,
			ImageMgmtUIManager imageMgmtUIManager) {
		this.tabSheet = tabSheet;
		this.imageMgmtUIManager = imageMgmtUIManager;
	}

	@Override
	public void click(ClickEvent event) {
		VerticalLayout newArticleLayout = new VerticalLayout();
		Tab createNew = tabSheet.addTab(newArticleLayout, String.format("Create new image"),new ExternalResource("images/content-mgmt.png"));
		createNew.setClosable(true);
		tabSheet.setSelectedTab(newArticleLayout);
		newArticleLayout.addComponent(imageMgmtUIManager.renderAddEditScreen("Add",null));
	}

}
