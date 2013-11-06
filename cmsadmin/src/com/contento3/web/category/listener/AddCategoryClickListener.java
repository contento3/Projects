package com.contento3.web.category.listener;

import com.contento3.web.category.CategoryPopup;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TreeTable;

public class AddCategoryClickListener implements ClickListener {

	private SpringContextHelper contextHelper;
	private TreeTable categoryTable;
	private TabSheet tabSheet;

	public AddCategoryClickListener(SpringContextHelper contextHelper,
			TreeTable categoryTable, TabSheet tabSheet) {
		this.contextHelper	=	contextHelper;
		this.categoryTable	=	categoryTable;
		this.tabSheet		=	tabSheet;	
	}

	@Override
	public void click(ClickEvent event) {
		CategoryPopup temp = new CategoryPopup(contextHelper,categoryTable,tabSheet);
		temp.buttonClick(new com.vaadin.ui.Button.ClickEvent(new Button("Add Category")));
	}

}
