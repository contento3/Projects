package com.contento3.web.content.article.listener;

import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;

public class AddArticleButtonListener implements ClickListener {

	private Table articleTable;
	private TabSheet tabSheet;
	private SpringContextHelper contextHelper;

	public AddArticleButtonListener(SpringContextHelper contextHelper,
			TabSheet tabSheet, Table articleTable) {
		this.contextHelper = contextHelper;
		this.tabSheet = tabSheet;
		this.articleTable = articleTable;
	}

	@Override
	public void click(ClickEvent event) {
		ArticleFormBuilderListner temp = new ArticleFormBuilderListner(this.contextHelper,this.tabSheet,this.articleTable);
		Button b = new Button("Add");
		temp.buttonClick(new com.vaadin.ui.Button.ClickEvent(b));
	}

}
