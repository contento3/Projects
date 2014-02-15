package com.contento3.web.user.listner;

import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.user.security.RolePopup;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Table;

public class AddRoleClickListener implements ClickListener {

	private SpringContextHelper contextHelper;
	private Table roleTable;

	public AddRoleClickListener(SpringContextHelper contextHelper,Table roleTable) {
			this.contextHelper	=	contextHelper;
			this.roleTable		=	roleTable;
	}

	@Override
	public void click(ClickEvent event) {
		
		RolePopup temp = new RolePopup(contextHelper,roleTable);
		temp.openButtonClick(null);
	}

}
