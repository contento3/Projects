package com.contento3.web.user.security;

import com.contento3.cms.constant.NavigationConstant;
import com.contento3.web.UIManager;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Item;
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
	TabSheet userMgmtTabSheet = null;
	/**
	 * Navigation item for user manager
	 */
	private String[] navigationItems = {NavigationConstant.USER_GRP_MGMT};

	/**
	 * 
	 */
	private TabSheet uiTabSheet;
	
	public UserUIManager(final TabSheet uiTabSheet,final SpringContextHelper helper,final Window parentWindow) {
		this.helper = helper;
		this.parentWindow = parentWindow;
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
	public Component render(final String command,
			HierarchicalContainer hwContainer) {
		
		if (command.equals(NavigationConstant.USER_MANAGER)){
			//Add the group screen tab and also add the child items
			renderUserNavigationItem(hwContainer);
		}
		else if (command.equals(NavigationConstant.USER_GRP_MGMT)){
			
			uiTabSheet = (TabSheet) renderGroupUI();
		}
		
		return uiTabSheet;
	}
	
	public void renderUserNavigationItem(final HierarchicalContainer hwContainer){
		for (String navigationItem : navigationItems){
			Item item = hwContainer.addItem(navigationItem);
			if (null != item){
				item.getItemProperty("name").setValue(navigationItem);
				hwContainer.setParent(navigationItem, NavigationConstant.USER_MANAGER);
				hwContainer.setChildrenAllowed(navigationItem, false);
			}
		}
	}
	
	public Component renderGroupUI(){
		return renderUserElementUI("Group");
	}
	
	private Component renderUserElementUI(final String element){
		TabSheet elementTab = null;
		if(element.equals("Group")){
			GroupUIManager groupManager = new GroupUIManager(uiTabSheet,helper, parentWindow);
			elementTab = (TabSheet) groupManager.render(null);
		}
		
	
		return elementTab;
	}

}
