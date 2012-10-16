package com.contento3.web.common;

import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Window;

public class UIContext {

	/**
	 * Used to get service beans from spring context.
	 */
	private SpringContextHelper contextHelper;
	
	 /**
     * Represents the parent window of the ui
     */
	private Window parentWindow;
	
	/**
	 * TabSheet serves as the parent container for the article manager
	 */
	private TabSheet tabSheet;


	public SpringContextHelper getContextHelper() {
		return contextHelper;
	}

	public void setContextHelper(final SpringContextHelper contextHelper) {
		this.contextHelper = contextHelper;
	}

	public Window getParentWindow() {
		return parentWindow;
	}

	public void setParentWindow(final Window parentWindow) {
		this.parentWindow = parentWindow;
	}

	public TabSheet getTabSheet() {
		return tabSheet;
	}

	public void setTabSheet(final TabSheet tabSheet) {
		this.tabSheet = tabSheet;
	}

	
}
