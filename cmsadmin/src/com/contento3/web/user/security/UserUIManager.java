package com.contento3.web.user.security;

import org.aspectj.ajde.ui.swing.GoToLineThread;

import com.contento3.cms.constant.NavigationConstant;
import com.contento3.web.UIManager;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;
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
	
	private String[] navigationItems = {NavigationConstant.USER_GRP_MGMT};

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

	/**
	 * Renders all the navigation items in the User Manager section
	 * @param hwContainer
	 */
	@Override
	public Component render(String command,
			HierarchicalContainer hwContainer) {
		
		if (command.equals(NavigationConstant.USER_MANAGER)){
			//Add the group screen tab and also add the child items
			userMgmtTabSheet = new TabSheet();
			renderUserNavigationItem(hwContainer);
		}
		else if (command.equals(NavigationConstant.USER_GRP_MGMT)){
			
			userMgmtTabSheet = (TabSheet) renderGroupUI();
		}
		
		return userMgmtTabSheet;
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
	
	private Component renderUserElementUI(String element){
		TabSheet elementTab = null;
		if(element.equals("Group")){
			GroupUIManager groupManager = new GroupUIManager(helper, parentWindow);
			elementTab = (TabSheet) groupManager.render(null);
			
		}
		
	
		return elementTab;
	}

}
