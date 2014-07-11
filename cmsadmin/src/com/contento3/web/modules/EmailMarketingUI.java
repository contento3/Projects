package com.contento3.web.modules;

import java.util.Collection;

import com.contento3.cms.constant.NavigationConstant;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.web.Manager;
import com.contento3.web.UIManager;
import com.contento3.web.UIManagerCreator;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.common.helper.TabSheetHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.layout.LayoutManagerRenderer;
import com.contento3.web.site.SiteMainAreaRenderer;
import com.contento3.web.site.SiteUIManager;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractSplitPanel.SplitterClickEvent;
import com.vaadin.ui.AbstractSplitPanel.SplitterClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.themes.BaseTheme;

public class EmailMarketingUI implements Button.ClickListener, Action.Handler {


	private static final long serialVersionUID = 1L;

	/**
	 * Horizontal split panel that contains the navigation 
	 * tree on one end and the main working area on the other hand
	 */
	HorizontalSplitPanel horiz;

    Tree root;

    private static final String[] NAVIGATION_CONSTANT = new String[] {NavigationConstant.DASHBOARD,NavigationConstant.SITES,NavigationConstant.CATEGORY_MGMT,NavigationConstant.TEMPLATE,NavigationConstant.MODULES,NavigationConstant.CONTENT_MANAGER,NavigationConstant.SECURITY,NavigationConstant.TEMPLATE};

    HorizontalLayout l = new HorizontalLayout();

    SiteMainAreaRenderer siteMainRenderer;
	
    LayoutManagerRenderer layoutManagerRenderer;
	
    SpringContextHelper helper;
	
    UIManager uiMgr;

    final TabSheet uiTabsheet = new TabSheet();

	UIManager siteUIMgr;

	final VerticalSplitPanel parentLayout;
	
	public EmailMarketingUI(final SpringContextHelper helper,final VerticalSplitPanel parentLayout){
		this.helper = helper;
		this.parentLayout = parentLayout;
	}
	
	@Override
	public void buttonClick(final com.vaadin.ui.Button.ClickEvent event) {
		this.parentLayout.replaceComponent(this.parentLayout.getSecondComponent(),buildUI());
	}

	
	public HorizontalSplitPanel buildUI(){
		horiz = new HorizontalSplitPanel();
        
        // Add a horizontal SplitPanel to the lower area
        horiz.setLocked(true);
        horiz.setSplitPosition(12);
        
        horiz.addSplitterClickListener(new SplitterClickListener(){
			private static final long serialVersionUID = 1L;

			public void splitterClick(SplitterClickEvent event){
				float splitPosition = horiz.getSplitPosition();
				
		        if (splitPosition==2)
		        	horiz.setSplitPosition(15);
		        else
					horiz.setSplitPosition(2);
			}
    	});
    	
        HorizontalLayout hLayout = new HorizontalLayout();
        
        final HierarchicalContainer hwContainer = new HierarchicalContainer();
        hwContainer.addContainerProperty("name", String.class, null);
        hwContainer.addContainerProperty("id", Integer.class, null);
        
        root = new Tree("",hwContainer);
        root.setStyleName(BaseTheme.TREE_CONNECTORS);
        root.addActionHandler(this);
        
        root.addContainerProperty("icon", Resource.class, null);
        root.setItemIconPropertyId("icon");
        createNavigation(hwContainer);
        Item childItem = null;

        hLayout.addComponent(root);
       // hLayout.addComponent(uri);
        hLayout.setWidth(100,Unit.PERCENTAGE);
        horiz.addComponent(hLayout);
        horiz.setWidth(100, Unit.PERCENTAGE);
        horiz.addComponent(l);
    	l.setWidth(100, Unit.PERCENTAGE);

    	root.setImmediate(true);

	   //When the item from the navigation is clicked then the 
       //below code will handle what is required to be done
        root.addItemClickListener(new ItemClickListener() {
			private static final long serialVersionUID = -4607219466099528006L;

			public void itemClick(ItemClickEvent event) {
        		root.expandItem(event.getItemId());
                final String itemSelected = event.getItem().getItemProperty("name").getValue().toString();

                if (null!=itemSelected && itemSelected.equals(NavigationConstant.NEWSLETTER)){
                	final UIManager newsletterUI = UIManagerCreator.createUIManager(Manager.Newsletter,helper);
                	horiz.setSecondComponent(newsletterUI.render(null));
                }
                else if (null!=itemSelected && itemSelected.equals(NavigationConstant.SUBSCRIPTION_LIST)){
                	final UIManager subscriptionUI = UIManagerCreator.createUIManager(Manager.Subscription,helper);
	                horiz.setSecondComponent(subscriptionUI.render(null));
                }
        	}
        });
        
        return horiz;
	}

    /*
     * Used to handle events after the user clicks the 
     * context menu from the left navigation menu.
     */
    public void handleAction(Action action, Object sender, Object target) {
    }

    private void createNavigation(final HierarchicalContainer hwContainer){
    	
    	//TODO create navigation permissions
    	//if (SecurityUtils.getSubject().isPermitted("DASHBOARD:NAVIGATION")){
    		createNavigationItem(hwContainer,NavigationConstant.NEWSLETTER,"images/email-icon.png");
    	//}
    	
//        if (SecurityUtils.getSubject().isPermitted("SITE:NAVIGATION")){
        	createNavigationItem(hwContainer,NavigationConstant.SUBSCRIPTION_LIST,"images/group.png");
//}
        
    }
    
    private void createNavigationItem(final HierarchicalContainer hwContainer,final String navigationConstant,final String imagePath){
        final Item item = hwContainer.addItem(navigationConstant);
        item.getItemProperty("name").setValue(navigationConstant);
        root.setItemIcon(item, new ExternalResource(imagePath));
        item.getItemProperty("icon").setValue(new ExternalResource(imagePath));
    }


    /*
     * Returns the set of available actions
     */
    public Action[] getActions(Object target, Object sender) {
    	return null;
    }

}
