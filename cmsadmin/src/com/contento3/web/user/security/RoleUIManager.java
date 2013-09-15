package com.contento3.web.user.security;

import java.util.Collection;

import com.contento3.security.role.service.RoleService;
import com.contento3.web.UIManager;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class RoleUIManager implements UIManager {

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
	private TabSheet uiTabSheet;
	private TabSheet TabSheet;
	/**
	 * Table contain group items
	 */
	Table roleTable = new Table();

	/**
	 * UserService
	 */
	RoleService roleService;
	
	public RoleUIManager(final TabSheet uiTabSheet,final SpringContextHelper helper) {
		this.contextHelper = helper;
		this.uiTabSheet = uiTabSheet;
		this.roleService = (RoleService) this.contextHelper.getBean("roleService");
	}
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Component render(String command) {
		// TODO Auto-generated method stub
		this.uiTabSheet.setHeight(100, Unit.PERCENTAGE);
		Tab userTab = uiTabSheet.addTab(verticalLayout, "Role Management",new ExternalResource("images/security.png"));
		userTab.setClosable(true);
		this.verticalLayout.setSpacing(true);
		this.verticalLayout.setWidth(100,Unit.PERCENTAGE);

		renderRoleContent();
		return this.uiTabSheet;
	}

	@Override
	public Component render(String command, Integer entityFilterId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Renders user listing 
	 */
	private void renderRoleContent() {
		Label roleHeading = new Label("Role Manager");
		roleHeading.setStyleName("screenHeading");
		this.verticalLayout.addComponent(roleHeading);
		this.verticalLayout.addComponent(new HorizontalRuler());
		this.verticalLayout.setMargin(true);
		addRoleButton();
		renderRoleTable();
	}
	@Override
	public Component render(String command,
			HierarchicalContainer treeItemContainer) {
		// TODO Auto-generated method stub
		return null;
	}
	private void addRoleButton(){
		Button addButton = new Button("Add Role", new RolePopup(contextHelper,roleTable));
		this.verticalLayout.addComponent(addButton);
	}

	private void renderRoleTable() {
		final AbstractTableBuilder tableBuilder = new RoleTableBuilder(contextHelper,roleTable);
		//tableBuilder.build((Collection)roleService.findAllRoles());
		tableBuilder.build((Collection)roleService.findRolesByAccountId((Integer)SessionHelper.loadAttribute("accountId")));
        
		this.verticalLayout.addComponent(roleTable);
		
	}
}
