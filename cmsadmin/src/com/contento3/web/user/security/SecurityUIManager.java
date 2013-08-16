package com.contento3.web.user.security;

import com.contento3.cms.constant.NavigationConstant;
import com.contento3.web.UIManager;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Window;

public class SecurityUIManager implements UIManager {

	/**
	 * Used to get service beans from spring context.
	 */
    private SpringContextHelper helper;
    
	/**
	 * Tab sheet to display user management ui
	 */
	TabSheet userMgmtTabSheet = null;
	/**
	 * Navigation item for user manager
	 */
	private String[] navigationItems = {NavigationConstant.USER_GRP_MGMT,NavigationConstant.USER_MANAGER,NavigationConstant.USR_ROLE_MGMT};

	/**
	 * 
	 */
	private TabSheet uiTabSheet;
	
	public SecurityUIManager(final TabSheet uiTabSheet,final SpringContextHelper helper) {
		this.helper = helper;
		this.uiTabSheet = uiTabSheet;
	}

	@Override
	public void render() {
	}

	@Override
	public Component render(final String command) {
		return uiTabSheet;
	}

	@Override
	public Component render(final String command,final Integer entityFilterId) {
		return uiTabSheet;
	}

	/**
	 * Renders all the navigation items in the User Manager section
	 * @param hwContainer
	 */
	@Override
	public Component render(final String command,final HierarchicalContainer hwContainer) {
		
		if (command.equals(NavigationConstant.SECURITY)){
			//Add the group screen tab and also add the child items
			renderUserNavigationItem(hwContainer);
		}
		else if (command.equals(NavigationConstant.USER_GRP_MGMT)){
			uiTabSheet = (TabSheet) renderElementUI("Group");
		}
		else if (command.equals(NavigationConstant.USER_MANAGER)){
			uiTabSheet = (TabSheet) renderElementUI("User");
		}
		else if (command.equals(NavigationConstant.USR_ROLE_MGMT)){
			uiTabSheet = (TabSheet) renderElementUI("Role");
		}
		return uiTabSheet;
	}
	
	public void renderUserNavigationItem(final HierarchicalContainer hwContainer){
		for (String navigationItem : navigationItems){
			Item item = hwContainer.addItem(navigationItem);
			if (null != item){
				item.getItemProperty("name").setValue(navigationItem);
				hwContainer.setParent(navigationItem, NavigationConstant.SECURITY);
				hwContainer.setChildrenAllowed(navigationItem, false);
			}
		}
	}
	
	private Component renderElementUI(final String element){
		TabSheet elementTab = null;
		if(element.equals("Group")){
			GroupUIManager groupManager = new GroupUIManager(uiTabSheet,helper);
			elementTab = (TabSheet) groupManager.render(null);
		}
		else if(element.equals("User")){
			UserUIManager userManager = new UserUIManager(uiTabSheet,helper);
			elementTab = (TabSheet) userManager.render(null);
		}		
		else if(element.equals("Role")){
			RoleUIManager roleManager = new RoleUIManager(uiTabSheet,helper);
			elementTab = (TabSheet) roleManager.render(null);
		}
		return elementTab;
	}
}
