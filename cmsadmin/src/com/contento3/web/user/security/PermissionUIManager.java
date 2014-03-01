package com.contento3.web.user.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.authz.AuthorizationException;

import com.contento3.security.permission.service.PermissionService;
import com.contento3.web.UIManager;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.common.helper.ScreenToolbarBuilder;
import com.contento3.web.helper.SpringContextHelper;
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

public class PermissionUIManager implements UIManager{
	/**
	 * Used to get service beans from spring context.
	 */
    private SpringContextHelper contextHelper;
    
    /**
     * Represents the parent window of the template ui
     */
	//private Window parentWindow;
	
	/**
	 * layout for group manager screen
	 */
	private VerticalLayout verticalLayout = new VerticalLayout();

	/**
	 * TabSheet serves as the parent container for the group manager
	 */
	private TabSheet uiTabSheet;
	private TabSheet TabSheet;
	/**
	 * Table contain permission items
	 */
	Table permissionTable = new Table();

	/**
	 * PermissionService
	 */
	PermissionService permissionService;
	PermissionUIManager(final TabSheet uiTabSheet,final SpringContextHelper helper)
	{
		this.contextHelper = helper;
		this.uiTabSheet = uiTabSheet;
		this.permissionService = (PermissionService) this.contextHelper.getBean("permissionService");
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
	}

	@Override
	public Component render(String command) {
		// TODO Auto-generated method stub
		this.uiTabSheet.setHeight(100, Unit.PERCENTAGE);
		Tab userTab = uiTabSheet.addTab(verticalLayout, "Permission Management",new ExternalResource("images/security.png"));
		userTab.setClosable(true);
		this.verticalLayout.setSpacing(true);
		this.verticalLayout.setWidth(100,Unit.PERCENTAGE);

		renderPermissionContent();
		return this.uiTabSheet;
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
	
	private void renderPermissionContent() {
		Label roleHeading = new Label("Permission Manager");
		roleHeading.setStyleName("screenHeading");
		
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		VerticalLayout innerLayout = new VerticalLayout();
		horizontalLayout.addComponent(innerLayout);
		innerLayout.addComponent(roleHeading);
		verticalLayout.addComponent(horizontalLayout);
		innerLayout.addComponent(new HorizontalRuler());
		innerLayout.setMargin(true);
		addPermissionButton(horizontalLayout);
		renderPermissionTable(innerLayout); 
		horizontalLayout.setExpandRatio(innerLayout, 9);
		horizontalLayout.setWidth(100, Unit.PERCENTAGE);
	}
	
	private void addPermissionButton(HorizontalLayout horizontl){
		GridLayout toolbarGridLayout = new GridLayout(1,1);
		List<com.vaadin.event.MouseEvents.ClickListener> listeners = new ArrayList<com.vaadin.event.MouseEvents.ClickListener>();
		listeners.add(new AddPermissionClickListener(contextHelper, permissionTable ));
		
		ScreenToolbarBuilder builder = new ScreenToolbarBuilder(toolbarGridLayout,"permission",listeners);
		builder.build();
		horizontl.addComponent(toolbarGridLayout);
		horizontl.setExpandRatio(toolbarGridLayout, 1);
		
	}

	private void renderPermissionTable(final VerticalLayout innerLayout) {
		try
		{
		final AbstractTableBuilder tableBuilder = new PermissionTableBuilder(contextHelper,permissionTable);
		tableBuilder.build((Collection)permissionService.findAllPermissions());
        
		innerLayout.addComponent(permissionTable);
		}catch(AuthorizationException ex){
			
		}
	}

}
