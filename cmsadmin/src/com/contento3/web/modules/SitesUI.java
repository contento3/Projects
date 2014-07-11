package com.contento3.web.modules;

import java.util.Collection;

import org.apache.shiro.SecurityUtils;

import com.contento3.cms.constant.NavigationConstant;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.web.Manager;
import com.contento3.web.UIManager;
import com.contento3.web.UIManagerCreator;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.common.helper.TabSheetHelper;
import com.contento3.web.common.listener.TabSheetDetachListener;
import com.contento3.web.content.SearchUI;
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

public class SitesUI implements Button.ClickListener, Action.Handler {

	private static final long serialVersionUID = 1L;

	/**
	 * Horizontal split panel that contains the navigation 
	 * tree on one end and the main working area on the other hand
	 */
	HorizontalSplitPanel horiz;

    Tree root;

    // Actions for the context menu
    private static final Action ACTION_ADD_SITE = new Action("Add new site");
    
    private static final Action[] SITE_ACTIONS = new Action[] { ACTION_ADD_SITE };
    
    private static final Action ACTION_ADD_CONTENT_TYPE = new Action("Add Content type");

    private static final Action ACTION_ADD_CONTENT = new Action("Add new content");

    private static final Action[] CONTENT_ACTIONS = new Action[] { ACTION_ADD_CONTENT_TYPE,ACTION_ADD_CONTENT };

    private static final String[] NAVIGATION_CONSTANT = new String[] {NavigationConstant.DASHBOARD,NavigationConstant.SITES,NavigationConstant.CATEGORY_MGMT,NavigationConstant.TEMPLATE,NavigationConstant.MODULES,NavigationConstant.CONTENT_MANAGER,NavigationConstant.SECURITY,NavigationConstant.TEMPLATE};

    HorizontalLayout l = new HorizontalLayout();

    SiteMainAreaRenderer siteMainRenderer;
	
    LayoutManagerRenderer layoutManagerRenderer;
	
    SpringContextHelper helper;
	
    UIManager uiMgr;

    final TabSheet uiTabsheet = new TabSheet();

	UIManager siteUIMgr;

	final VerticalSplitPanel parentLayout;
	
	public SitesUI(final SpringContextHelper helper,final VerticalSplitPanel parentLayout){
		this.helper = helper;
		this.parentLayout = parentLayout;
	}

	@Override
	public void buttonClick(final com.vaadin.ui.Button.ClickEvent event) {
		this.parentLayout.replaceComponent(this.parentLayout.getSecondComponent(),buildUI());
	}

	
	public HorizontalSplitPanel buildUI(){
		horiz = new HorizontalSplitPanel();
        
        //Add the splitter to split main ui and content ui
        // First add the main ui component
        final HorizontalSplitPanel mainAndContentSplitter = new HorizontalSplitPanel();
        mainAndContentSplitter.addComponent(horiz);
        
        //Then add the content SearchUI object that renders the content search ui
        final SearchUI searchUI = new SearchUI();
        mainAndContentSplitter.addComponent(searchUI.render());
        mainAndContentSplitter.setSplitPosition(85,Unit.PERCENTAGE);
        
        mainAndContentSplitter.addSplitterClickListener(new SplitterClickListener(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void splitterClick(SplitterClickEvent event){
				float splitPosition = mainAndContentSplitter.getSplitPosition();
				
		        if (splitPosition==96)
		        	mainAndContentSplitter.setSplitPosition(85,Unit.PERCENTAGE);
		        else
		        	mainAndContentSplitter.setSplitPosition(96,Unit.PERCENTAGE);
			}
    	});

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

    	//After the login, dashboard must be displayed as a first screen
		UIManager sitesDashboard = UIManagerCreator.createUIManager(uiTabsheet,Manager.Dashboard,helper);
		horiz.setSecondComponent(sitesDashboard.render(null));

		uiTabsheet.addDetachListener(new TabSheetDetachListener());
		uiTabsheet.addComponentDetachListener(new TabSheetDetachListener());

		
	   //When the item from the navigation is clicked then the 
        //below code will handle what is required to be done
        root.addItemClickListener(new ItemClickListener() {
			private static final long serialVersionUID = -4607219466099528006L;
			Collection<SiteDto> sites;
        	public void itemClick(ItemClickEvent event) {
        		root.expandItem(event.getItemId());
                String itemSelected = event.getItem().getItemProperty("name").getValue().toString();
                Object parentOfSelectedItem = hwContainer.getParent(itemSelected);

            	if (!TabSheetHelper.isTabLocked(uiTabsheet)){
	                if (null!=itemSelected && itemSelected.equals("Layout Manager")){
	                		UIManager layoutUIMgr = UIManagerCreator.createUIManager(uiTabsheet,Manager.Layout,helper);
	                		horiz.setSecondComponent(layoutUIMgr.render(null));
	        		}
	                else if (null!=itemSelected && itemSelected.equals(NavigationConstant.DASHBOARD)){
	                		UIManager sitesDashboard = UIManagerCreator.createUIManager(uiTabsheet,Manager.Dashboard,helper);
	                		horiz.setSecondComponent(sitesDashboard.render(null));
	                }
	        		else if (null!=itemSelected  && (itemSelected.equals(NavigationConstant.CONTENT_MANAGER) || 
	        				(null!=parentOfSelectedItem && parentOfSelectedItem.equals(NavigationConstant.CONTENT_MANAGER)))){
	        				final TabSheet tabsheet = new TabSheet();
	        				UIManager contentUIMgr = UIManagerCreator.createUIManager(tabsheet,Manager.Content,helper);
	    	    			horiz.setSecondComponent(contentUIMgr.render(itemSelected,hwContainer));
	        		}
	        		else if (null!=itemSelected  && (itemSelected.equals(NavigationConstant.SECURITY) || 
	        				(null!=parentOfSelectedItem && parentOfSelectedItem.equals(NavigationConstant.SECURITY)))){
	    	    		UIManager userUIMgr = UIManagerCreator.createUIManager(uiTabsheet,Manager.User,helper);
	    	    		horiz.setSecondComponent(userUIMgr.render(itemSelected,hwContainer));
	        		}
	        		else if (null!=itemSelected && itemSelected.equals(NavigationConstant.TEMPLATE)){
	    	    		UIManager templateUIMgr = UIManagerCreator.createUIManager(uiTabsheet,Manager.Template,helper);
	    	    		horiz.setSecondComponent(templateUIMgr.render(null));
	        		}
	        		else if (null!=itemSelected && itemSelected.equals(NavigationConstant.CATEGORY_MGMT)){
	        			TabSheet tabsheet = new TabSheet();
	    	    		UIManager categoryUIMgr = UIManagerCreator.createUIManager(tabsheet,Manager.Category,helper);
	    	    		horiz.setSecondComponent(categoryUIMgr.render(null));
	        		}
                	else if (null!=itemSelected && itemSelected.equals("Sites")) {
	            		hwContainer.setChildrenAllowed("Sites", true);	
	            		
	            		//TODO no need to go to fetch sites if they are not null
	            		// but need to handle the situation where a new site is added 
	            		// so that this new site is added and hence displayed to the tree 
	            		//if (CollectionUtils.isEmpty(sites)){
	            			SiteService siteService = (SiteService) helper.getBean("siteService");
	            			System.out.println("account id:" + (Integer)SessionHelper.loadAttribute("accountId"));
	            			sites = siteService.findSitesByAccountId((Integer)SessionHelper.loadAttribute("accountId"), false);
	            		//}
	            	//	Log.debug(String.format("Found %d sites for this account", sites.size()));
	            			
	            			UIManager siteUIMgr = UIManagerCreator.createUIManager(uiTabsheet,Manager.Site,helper);
	            			horiz.setSecondComponent(siteUIMgr.render(null));
	            			
	            			
	            		for (SiteDto site: sites){
	           				Item item = hwContainer.addItem(site.getSiteName());
	           				if (null != item){	
	          					item.getItemProperty("name").setValue(site.getSiteName());
	           					item.getItemProperty("id").setValue(site.getSiteId());
	            				hwContainer.setParent(site.getSiteName(), "Sites");
	            				hwContainer.setChildrenAllowed(site.getSiteName(), false);
	            			}
	            		}
	            	}
	            	
	            	// get pages for this site.
	            	else if (((null!=parentOfSelectedItem && parentOfSelectedItem.equals(NavigationConstant.SITES)) || itemSelected.equals(NavigationConstant.SITES)) && null!=itemSelected) {
	            		Integer selectedSiteId = (Integer)event.getItem().getItemProperty("id").getValue();
	            		for (SiteDto siteDto : sites){
	            			if (selectedSiteId == siteDto.getSiteId()){
	            	    		siteUIMgr = UIManagerCreator.createUIManager(uiTabsheet,Manager.Site,helper);
	            	    		horiz.setSecondComponent(siteUIMgr.render(SiteUIManager.SITEDASHBOARD,selectedSiteId));
	            			}
	            		}
	            	}
	
            		}	
        	   	else {
        	   		Notification.show("You cannot add more than 10 tab at a time.Please close the currently opened tab first.");
        	   	}
        	}
        });
        
        return mainAndContentSplitter;
	}

    /*
     * Used to handle events after the user clicks the 
     * context menu from the left navigation menu.
     */
    public void handleAction(Action action, Object sender, Object target) {
    	//If the user right clicks the 'Site' and then click 'Create new site'
    	//Then a new site creation screen needs to be rendered
    	if (action.equals(ACTION_ADD_SITE)) {
    		horiz.setSecondComponent(siteUIMgr.render(SiteUIManager.NEWSITE));
    	}
    }

    private void createNavigation(final HierarchicalContainer hwContainer){
    	
    	if (SecurityUtils.getSubject().isPermitted("DASHBOARD:NAVIGATION")){
    		createNavigationItem(hwContainer,NavigationConstant.DASHBOARD,"images/home-icon.png");
    	}
    	
        if (SecurityUtils.getSubject().isPermitted("SITE:NAVIGATION")){
        	createNavigationItem(hwContainer,NavigationConstant.SITES,"images/site.png");
        }
        
        if (SecurityUtils.getSubject().isPermitted("CATEGORY:NAVIGATION")){
        	createNavigationItem(hwContainer,NavigationConstant.CATEGORY_MGMT,"images/category.png");
        }
        
        // DO NOT REMOVE THIS
        //TOBE USED LATER      
        //if (SecurityUtils.getSubject().isPermitted("GLOBAL_CONFIG:NAVIGATION")){
        //createNavigationItem(hwContainer,NavigationConstant.GLOBAL_CONFIG,"images/configuration.png");
    	//}
    
        if (SecurityUtils.getSubject().isPermitted("CONTENT:NAVIGATION")){
        	createNavigationItem(hwContainer,NavigationConstant.CONTENT_MANAGER,"images/content-icon-16.png");
        }
        
        if (SecurityUtils.getSubject().isPermitted("TEMPLATE:NAVIGATION")){
        	createNavigationItem(hwContainer,NavigationConstant.TEMPLATE,"images/add-template-16.png");
        }
        
       if (SecurityUtils.getSubject().isPermitted("MODULE:NAVIGATION")){
        	createNavigationItem(hwContainer,NavigationConstant.MODULES,"images/module-icon.png");
       }
      
        // DO NOT REMOVE THIS -- The functionality regarding layout management will be developed soon        
        // Item layoutManager = hwContainer.addItem(NavigationConstant.LAYOUT_MANAGER);
        // layoutManager.getItemProperty("name").setValue(NavigationConstant.LAYOUT_MANAGER);
        
        if (SecurityUtils.getSubject().isPermitted("SECURITY:NAVIGATION")){
        	createNavigationItem(hwContainer,NavigationConstant.SECURITY,"images/security.png");
        }
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
    	if (null!=target){
    	if (target.equals("Sites")){
        return SITE_ACTIONS;
    	}
    	else if (target.equals("Content Manager")){
    	        return CONTENT_ACTIONS;
    	}
    	}
    	return null;
    }



}
