package com.contento3.web;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import com.contento3.cms.constant.NavigationConstant;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.layout.LayoutManagerRenderer;
import com.contento3.web.site.SiteMainAreaRenderer;
import com.contento3.web.site.SiteUIManager;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.terminal.FileResource;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.AbstractSplitPanel.SplitterClickEvent;
import com.vaadin.ui.AbstractSplitPanel.SplitterClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component.Event;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UriFragmentUtility;
import com.vaadin.ui.UriFragmentUtility.FragmentChangedEvent;
import com.vaadin.ui.UriFragmentUtility.FragmentChangedListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

public class CMSMainWindow extends Window implements Action.Handler,FragmentChangedListener {
	public static String brownFox = "Welcome to Olive Admin"; 

    // Actions for the context menu
    private static final Action ACTION_ADD_SITE = new Action("Add new site");
    private static final Action[] SITE_ACTIONS = new Action[] { ACTION_ADD_SITE };
    private static final Action ACTION_ADD_CONTENT_TYPE = new Action("Add Content type");
    private static final Action ACTION_ADD_CONTENT = new Action("Add new content");

    private static final Action[] CONTENT_ACTIONS = new Action[] { ACTION_ADD_CONTENT_TYPE,ACTION_ADD_CONTENT };

    private Button logoutButton;

    
	TextField textField = new TextField();
    Tree root;
    HorizontalLayout l = new HorizontalLayout();
	SiteMainAreaRenderer siteMainRenderer;
	LayoutManagerRenderer layoutManagerRenderer;
	SpringContextHelper helper;
	UriFragmentUtility uri;
	UIManager uiMgr;
	
	/**
	 * Horizontal split panel that containts the navigation 
	 * tree on one end and the main working area on the other hand
	 */
	HorizontalSplitPanel horiz;
	
	CMSMainWindow(final SpringContextHelper helper,final Button logoutButton){ //change
	//	super(TM.get("app.title"));
		this.helper = helper;
		this.logoutButton = logoutButton;
		
 		unitUI();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void fragmentChanged(FragmentChangedEvent source) {
            String frag = source.getUriFragmentUtility().getFragment();
            String selectedUIMgr = "layout";
            
            if (frag.equals(NavigationConstant.LAYOUT_MANAGER)){
            	selectedUIMgr = Manager.Layout.getManagerName(); 
            }
            else if (frag.equals(NavigationConstant.CONTENT_MANAGER)){
            	selectedUIMgr = Manager.Content.getManagerName(); 
            }
            else if (frag.equals(NavigationConstant.TEMPLATE)){
            	selectedUIMgr = Manager.Template.getManagerName(); 
            }
            
            if (frag != null) {  
            	uiMgr =  (UIManager) helper.getBean(selectedUIMgr);
                uiMgr.render();
            }
        }
 
	private void unitUI(){
    	uri = new UriFragmentUtility();
        uri.addListener(this);
        uri.setImmediate(true);

        VerticalLayout vLayout = new VerticalLayout();
        setContent(vLayout);
        vLayout.setSizeFull();

	    final VerticalSplitPanel vert = new VerticalSplitPanel();
	    vert.setSplitPosition(8, Sizeable.UNITS_PERCENTAGE);
	    vert.setLocked(true);
	   
      
	   // vert.addComponent(logoutButton);
	    HorizontalLayout horizTop = new HorizontalLayout();
       
	    horizTop.addComponent(logoutButton);
	    horizTop.setSizeFull();
	    horizTop.setComponentAlignment(logoutButton, Alignment.TOP_RIGHT);
	    horizTop.setSpacing(isEnabled());
	    horizTop.setMargin(false, true, false, true);
	    vert.addComponent(horizTop);
       
	    vLayout.addComponent(vert);
       

     
        horiz = new HorizontalSplitPanel();

        
        //Add the splitter to split main ui and content ui
        // First add the main ui component
        final HorizontalSplitPanel mainAndContentSplitter = new HorizontalSplitPanel();
        mainAndContentSplitter.addComponent(horiz);
        
        //Then add the content ui verticall layout
        VerticalLayout contentLayout = new VerticalLayout();
        mainAndContentSplitter.addComponent(contentLayout);
        mainAndContentSplitter.setSplitPosition(96,Sizeable.UNITS_PERCENTAGE);
        
        mainAndContentSplitter.addListener(new SplitterClickListener(){
			public void splitterClick(SplitterClickEvent event){
				int splitPosition = mainAndContentSplitter.getSplitPosition();
				
		        if (splitPosition==96)
		        	mainAndContentSplitter.setSplitPosition(75,Sizeable.UNITS_PERCENTAGE);
		        else
		        	mainAndContentSplitter.setSplitPosition(96,Sizeable.UNITS_PERCENTAGE);
			}
    	});

        // Add a horizontal SplitPanel to the lower area
        horiz.setLocked(true);
        horiz.setSplitPosition(15);
        
        horiz.addListener(new SplitterClickListener(){
			public void splitterClick(SplitterClickEvent event){
				int splitPosition = horiz.getSplitPosition();
				
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
        root.addActionHandler(this);
        Item item0 = hwContainer.addItem("Sites");
        item0.getItemProperty("name").setValue("Sites");
        item0.getItemProperty("id").setValue(new Integer(-1));

        Item contentMgmt = hwContainer.addItem(NavigationConstant.CONTENT_MANAGER);
        contentMgmt.getItemProperty("name").setValue(NavigationConstant.CONTENT_MANAGER);

        Item globalConfig = hwContainer.addItem(NavigationConstant.GLOBAL_CONFIG);
        globalConfig.getItemProperty("name").setValue(NavigationConstant.GLOBAL_CONFIG);

        Item template = hwContainer.addItem("Template");
        template.getItemProperty("name").setValue(NavigationConstant.TEMPLATE);

        Item modules = hwContainer.addItem("Modules");
        modules.getItemProperty("name").setValue(NavigationConstant.MODULES);

        Item layoutManager = hwContainer.addItem(NavigationConstant.LAYOUT_MANAGER);
        layoutManager.getItemProperty("name").setValue(NavigationConstant.LAYOUT_MANAGER);

        Item userMgmtItem = hwContainer.addItem(NavigationConstant.USER_MGMT);
        userMgmtItem.getItemProperty("name").setValue(NavigationConstant.USER_MGMT);

        Item childItem = null;

        hLayout.addComponent(root);

        hLayout.addComponent(uri);
        hLayout.setWidth(100,Sizeable.UNITS_PERCENTAGE);
        horiz.addComponent(hLayout);
        horiz.setWidth(100, Sizeable.UNITS_PERCENTAGE);
        Window window = new Window();
        window.addComponent(new Label(brownFox));
        horiz.addComponent(l);
    	l.setWidth(100, Sizeable.UNITS_PERCENTAGE);

       root.setImmediate(true);
       vert.addComponent(mainAndContentSplitter); 
    
        //When the item from the navigation is clicked then the 
        //below code will handle what is required to be done
        root.addListener(new ItemClickListener() {
			private static final long serialVersionUID = -4607219466099528006L;
			Collection<SiteDto> sites;
        	public void itemClick(ItemClickEvent event) {
        		root.expandItem(event.getItemId());
                String itemSelected = event.getItem().getItemProperty("name").getValue().toString();
                Object parentOfSelectedItem = hwContainer.getParent(itemSelected);
        		
                if (null!=itemSelected && itemSelected.equals("Layout Manager")){
    	    		UIManager layoutUIMgr = UIManagerCreator.createUIManager(Manager.Layout,helper,getWindow());
    	    		horiz.setSecondComponent(layoutUIMgr.render(null));
        		}
        		else if (null!=itemSelected  && (itemSelected.equals(NavigationConstant.CONTENT_MANAGER) || 
        				(null!=parentOfSelectedItem && parentOfSelectedItem.equals(NavigationConstant.CONTENT_MANAGER)))){
    	    		UIManager contentUIMgr = UIManagerCreator.createUIManager(Manager.Content,helper,getWindow());
    	    		horiz.setSecondComponent(contentUIMgr.render(itemSelected,hwContainer));
        		}
        		else if (null!=itemSelected  && (itemSelected.equals(NavigationConstant.USER_MGMT) || 
        				(null!=parentOfSelectedItem && parentOfSelectedItem.equals(NavigationConstant.USER_MGMT)))){
    	    		UIManager userUIMgr = UIManagerCreator.createUIManager(Manager.User,helper,getWindow());
    	    		horiz.setSecondComponent(userUIMgr.render(itemSelected,hwContainer));
        		}
        		else if (null!=itemSelected && itemSelected.equals("Template")){
    	    		UIManager templateUIMgr = UIManagerCreator.createUIManager(Manager.Template,helper,getWindow());
    	    		horiz.setSecondComponent(templateUIMgr.render(null));
        		}
        		else if (null!=itemSelected && itemSelected.equals("Sites")) {
            		hwContainer.setChildrenAllowed("Sites", true);	
            		
            		//TODO no need to go to fetch sites if they are not null
            		// but need to handle the situation where a new site is added 
            		// so that this new site is added and hence displayed to the tree 
            		//if (CollectionUtils.isEmpty(sites)){
            			SiteService siteService = (SiteService) helper.getBean("siteService");
            			sites = siteService.findSiteByAccountId(1);
            		//}
            	//	Log.debug(String.format("Found %d sites for this account", sites.size()));
            			
            			UIManager siteUIMgr = UIManagerCreator.createUIManager(Manager.Site,helper,getWindow());
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
            	    		UIManager siteUIMgr = UIManagerCreator.createUIManager(Manager.Site,helper,getWindow());
            	    		horiz.setSecondComponent(siteUIMgr.render(SiteUIManager.SITEDASHBOARD,selectedSiteId));
            			}
            		}
            	}
           	}	
        });
                
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

    /**
     * Used to handle events after the user clicks the 
     * context menu from the left navigation menu.
     */
    public void handleAction(Action action, Object sender, Object target) {
    	//If the user right clicks the 'Site' and then click 'Create new site'
    	//Then a new site creation screen needs to be rendered
    	if (action.equals(ACTION_ADD_SITE)) {
    		SiteUIManager siteUIMgr = new SiteUIManager(helper,getWindow());
    		horiz.setSecondComponent(siteUIMgr.render(SiteUIManager.NEWSITE));
    		//SiteService siteService = (SiteService) helper.getBean("siteService");
    	}
    }

    @Override
    public void attach() {
        super.attach(); // Must call.
        WebApplicationContext ctx = ((WebApplicationContext) this.getApplication().getContext());
        HttpSession session = ctx.getHttpSession();
        session.setAttribute("accountId", new Integer("1"));
    }

}
