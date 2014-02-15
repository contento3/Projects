package com.contento3.web.user.security;

import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Table;

public class AddPermissionClickListener implements ClickListener {

	private SpringContextHelper contextHelper;
	private Table roleTable;

	public AddPermissionClickListener(SpringContextHelper contextHelper,Table roleTable) {
		this.contextHelper	=	contextHelper;
		this.roleTable		=	roleTable;
	}

	@Override
	public void click(ClickEvent event) {
		PermissionPopup popup = new PermissionPopup(contextHelper,roleTable);
		popup.openButtonClick(null);
	}


}
