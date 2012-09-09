package com.contento3.web.user.security;

import java.util.Collection;
import com.contento3.security.group.dto.GroupDto;
import com.contento3.security.group.service.GroupService;
import com.contento3.web.UIManager;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.site.SiteDomainTableBuilder;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.BaseTheme;

public class GroupUIManager implements UIManager {

	/**
	 * Used to get service beans from spring context.
	 */
    private SpringContextHelper contextHelper;
    
    /**
     * Represents the parent window of the template ui
     */
	private Window parentWindow;
	/**
	 * layout for group manager screen
	 */
	private VerticalLayout verticalLayout = new VerticalLayout();
	/**
	 * TabSheet serves as the parent container for the group manager
	 */
	private TabSheet tabSheet;
	/**
	 * Group service used for group related operations
	 */
	private GroupService groupService;
	/**
	 * Table contain group items
	 */
	Table groupTable = new Table();
	
	private TabSheet uiTabSheet;
	
	/**
	 * Constructor
	 * @param helper
	 * @param parentWindow
	 */
	public GroupUIManager(final TabSheet uiTabSheet,final SpringContextHelper helper,final Window parentWindow) {
		this.contextHelper = helper;
		this.parentWindow = parentWindow;
		this.groupService = (GroupService) this.contextHelper.getBean("groupService");
		this.uiTabSheet = uiTabSheet;
	}
	
	@Override
	public void render() {
	

	}
	/**
	 * Return tab sheet  
	 */
	@Override
	public Component render(final String command) {
	
		this.uiTabSheet.setHeight(100, Sizeable.UNITS_PERCENTAGE);
		Tab groupTab = uiTabSheet.addTab(verticalLayout, "Group Management");
		groupTab.setClosable(true);
		this.verticalLayout.setSpacing(true);
		this.verticalLayout.setWidth(100,Sizeable.UNITS_PERCENTAGE);
		
		renderGroupContent();
		
		return this.uiTabSheet;
	}

	

	@Override
	public Component render(String command, Integer entityFilterId) {

		return null;
	}

	@Override
	public Component render(String command,
			HierarchicalContainer treeItemContainer) {
		
		return null;
	}
	/**
	 * Render group content U.I 
	 * this includes add group button
	 * and group table
	 */
	private void renderGroupContent() {
		Label groupHeading = new Label("Group Manager");
		groupHeading.setStyleName("screenHeading");
		this.verticalLayout.addComponent(groupHeading);
		this.verticalLayout.addComponent(new HorizontalRuler());
		this.verticalLayout.setMargin(true);
		addGroupButton();
		renderGroupTable();
	}
	/**
	 * display "Add Group" button on the top of tab sheet
	 */
	private void addGroupButton(){
		Button addButton = new Button("Add Group", new GroupPopup(parentWindow, contextHelper,groupTable), "openButtonClick");
		this.verticalLayout.addComponent(addButton);
	}
		/**
	 * Render group table to screen
	 */
	private void renderGroupTable() {
		
		final AbstractTableBuilder tableBuilder = new GroupTableBuilder(parentWindow,contextHelper,groupTable);
		
		tableBuilder.build((Collection)groupService.findAllGroups());
		
		this.verticalLayout.addComponent(groupTable);
	}
	

	
}
