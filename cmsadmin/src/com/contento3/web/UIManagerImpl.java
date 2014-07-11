package com.contento3.web;

import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.Component;

public class UIManagerImpl implements UIManager {

	private UIManagerContext uiContext;
	
	public UIManagerContext getUiContext() {
		return uiContext;
	}

	public void setUiContext(final UIManagerContext uiContext) {
		this.uiContext = uiContext;
	}

	@Override
	public void render() {
	}

	@Override
	public Component render(String command) {
		return null;
	}

	@Override
	public Component render(String command, Integer entityFilterId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component render(String command,
			HierarchicalContainer treeItemContainer) {
		// TODO Auto-generated method stub
		return null;
	}

}
