package com.contento3.web.user.listner;

import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.user.security.UserPopup;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Table;

public class AddUserClickListener implements ClickListener {

	private Table userTable;
	private SpringContextHelper contextHelper;

	public AddUserClickListener(SpringContextHelper contextHelper,
			Table userTable) {
		this.contextHelper	=	contextHelper;
		this.userTable		=	userTable;
	}

	@Override
	public void click(ClickEvent event) {
		UserPopup temp = new UserPopup(contextHelper,userTable);
		temp.openButtonClick(null);
	}

}
