package com.contento3.web.user.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.authz.AuthorizationException;

import com.contento3.security.group.service.GroupService;
import com.contento3.web.UIManager;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.common.helper.ScreenToolbarBuilder;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.user.listner.AddGroupClickListener;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class GroupUIManager implements UIManager {

	/**
	 * Used to get service beans from spring context.
	 */
    private SpringContextHelper contextHelper;
    
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
	public GroupUIManager(final TabSheet uiTabSheet,final SpringContextHelper helper) {
		this.contextHelper = helper;
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
	
		this.uiTabSheet.setHeight(100, Unit.PERCENTAGE);
		Tab groupTab = uiTabSheet.addTab(verticalLayout, "User Groups",new ExternalResource("images/security.png"));
		groupTab.setClosable(true);
		this.verticalLayout.setSpacing(true);
		this.verticalLayout.setWidth(100,Unit.PERCENTAGE);
		
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
		HorizontalLayout horizon = new HorizontalLayout();
		VerticalLayout verticl = new VerticalLayout();
		horizon.addComponent(verticl);
		this.verticalLayout.addComponent(horizon);
		verticl.addComponent(groupHeading);
		verticl.addComponent(new HorizontalRuler());
		verticl.setMargin(true);
		addGroupButton();
		GridLayout toolbarGridLayout = new GridLayout(1,1);
		List<com.vaadin.event.MouseEvents.ClickListener> listeners = new ArrayList<com.vaadin.event.MouseEvents.ClickListener>();
		listeners.add(new AddGroupClickListener(contextHelper, groupTable));
		
		ScreenToolbarBuilder builder = new ScreenToolbarBuilder(toolbarGridLayout,"group",listeners);
		builder.build();
		renderGroupTable(verticl);
		horizon.addComponent(toolbarGridLayout);
		horizon.setWidth(100,Unit.PERCENTAGE);	
		horizon.setExpandRatio(toolbarGridLayout, 1);
		horizon.setExpandRatio(verticl, 9);
	}
	
	/**
	 * display "Add Group" button on the top of tab sheet
	 */
	private void addGroupButton(){
//		Button addButton = new Button("Add Group", new GroupPopup(contextHelper,groupTable));
//		this.verticalLayout.addComponent(addButton);
	}

	/**
	 * Render group table to screen
	 */
	private void renderGroupTable(final VerticalLayout verticl) {
		try {
			final AbstractTableBuilder tableBuilder = new GroupTableBuilder(contextHelper,groupTable);
			tableBuilder.build((Collection)groupService.findByAccountId((Integer)SessionHelper.loadAttribute("accountId")));
			verticl.addComponent(groupTable);
		}
		catch(final AuthorizationException ex){
			
		}
	}
	

	
}
