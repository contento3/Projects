package com.olive.web;

import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.Component;

public interface UIManager {
	/**
	 * Used to render the ui manager based on the selection from the navigation.
	 */
	void render();
	
	/**
	 * Used to render the screen according to the command passed.
	 * The component that is being built is returned so that it can 
	 * be added to the parent component
	 * @param command
	 */
	Component render(String command);

	/**
	 * renders the component according to the command
	 * @param command
	 * @param entityFilterId
	 * @return
	 */
	Component render(String command,Integer entityFilterId);

	/**
	 * renders the component according to the command
	 * @param command
	 * @param entityFilterId
	 * @return
	 */
	Component render(String command,HierarchicalContainer treeItemContainer);

}
