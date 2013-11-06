package com.contento3.web.user.listner;

import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.user.security.GroupPopup;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Table;

public class AddGroupClickListener implements ClickListener {

	private SpringContextHelper contextHelper;
	private Table groupTable;

	public AddGroupClickListener(SpringContextHelper contextHelper,
			Table groupTable) {
		this.contextHelper=contextHelper;
		this.groupTable=groupTable;
	}

	@Override
	public void click(ClickEvent event) {
		GroupPopup temp = new GroupPopup(contextHelper,groupTable);
		temp.openButtonClick(null);
	}

}
