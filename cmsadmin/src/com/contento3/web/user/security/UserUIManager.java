package com.contento3.web.user.security;

import com.contento3.web.UIManager;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Window;

public class UserUIManager implements UIManager {

	/**
	 * Used to get service beans from spring context.
	 */
    private SpringContextHelper helper;
    
    /**
     * Represents the parent window of the template ui
     */
	private Window parentWindow;

	/**
	 * Tab sheet to display user management ui
	 */
	TabSheet userMgmtTabSheet;

	public UserUIManager(SpringContextHelper helper, Window parentWindow) {
		this.helper = helper;
		this.parentWindow = parentWindow;
	}

	@Override
	public void render() {
		userMgmtTabSheet = new TabSheet();
	}

	@Override
	public Component render(String command) {
		userMgmtTabSheet = new TabSheet();
		return userMgmtTabSheet;
	}

	@Override
	public Component render(String command, Integer entityFilterId) {
		userMgmtTabSheet = new TabSheet();
		return userMgmtTabSheet;
	}

	@Override
	public Component render(String command,
			HierarchicalContainer treeItemContainer) {
		userMgmtTabSheet = new TabSheet();
		return userMgmtTabSheet;
	}

}
