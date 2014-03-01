package com.contento3.web;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.vaadin.aceeditor.AceEditor;

import com.contento3.cms.constant.NavigationConstant;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.security.user.service.SaltedHibernateUserService;
import com.contento3.web.account.AccountSettingsUIManager;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.common.helper.TabSheetHelper;
import com.contento3.web.common.listener.TabSheetDetachListener;
import com.contento3.web.content.SearchUI;
import com.contento3.web.content.image.ImageLoader;
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
import com.vaadin.server.Page;
import com.vaadin.server.Page.UriFragmentChangedEvent;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractSplitPanel.SplitterClickEvent;
import com.vaadin.ui.AbstractSplitPanel.SplitterClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.CloseHandler;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.Reindeer;

public class CMSMainWindow extends VerticalLayout implements Action.Handler {

	private static final Logger LOGGER = Logger.getLogger(CMSMainWindow.class);

    // Actions for the context menu
    private static final Action ACTION_ADD_SITE = new Action("Add new site");
    private static final Action[] SITE_ACTIONS = new Action[] { ACTION_ADD_SITE };
    private static final Action ACTION_ADD_CONTENT_TYPE = new Action("Add Content type");
    private static final Action ACTION_ADD_CONTENT = new Action("Add new content");

    private static final Action[] CONTENT_ACTIONS = new Action[] { ACTION_ADD_CONTENT_TYPE,ACTION_ADD_CONTENT };

    private static final String[] NAVIGATION_CONSTANT = new String[] {NavigationConstant.DASHBOARD,NavigationConstant.SITES,NavigationConstant.CATEGORY_MGMT,NavigationConstant.TEMPLATE,NavigationConstant.MODULES,NavigationConstant.CONTENT_MANAGER,NavigationConstant.SECURITY,NavigationConstant.TEMPLATE};
    private Button logoutButton;
    public Button accountButton;
    
	TextField textField = new TextField();
    Tree root;
    HorizontalLayout l = new HorizontalLayout();
	SiteMainAreaRenderer siteMainRenderer;
	LayoutManagerRenderer layoutManagerRenderer;
	SpringContextHelper helper;
	UIManager uiMgr;
	
	Subject subject;
	SaltedHibernateUserService userService;
	
	final SaltedHibernateUserDto user = null;
	
	/**
	 * Horizontal split panel that contains the navigation 
	 * tree on one end and the main working area on the other hand
	 */
	HorizontalSplitPanel horiz;
	
	UIManager siteUIMgr;
	
    final TabSheet uiTabsheet = new TabSheet();

    Boolean isDemo=false;
    
	CMSMainWindow(final SpringContextHelper helper,final Boolean isDemo){ //change
		this.helper = helper;
		this.logoutButton = new Button("Log Out");
		logoutButton.addStyleName("link");
        this.userService = (SaltedHibernateUserService)helper.getBean("saltedHibernateUserService");
        this.isDemo = isDemo;
		buildLogin();
	}
	
	public void buildLogin(){
		final VerticalLayout appRootLayout = new VerticalLayout();
		
		final VerticalLayout loginLayout = new VerticalLayout();
		loginLayout.setHeight(175,Unit.PIXELS);
		loginLayout.setWidth(145,Unit.PIXELS);
		
		final TextField usernameTxtFld = new TextField();
		usernameTxtFld.setCaption("Username");
		usernameTxtFld.setTabIndex(1);
		
		final PasswordField passwordTxtFld = new PasswordField();
		passwordTxtFld.setCaption("Password");
		passwordTxtFld.setTabIndex(2);
		
		loginLayout.addComponent(usernameTxtFld);
		loginLayout.addComponent(passwordTxtFld);

		//If its the demo account
		//Set the demo username and password
		if (isDemo){
			usernameTxtFld.setValue("demo");
			passwordTxtFld.setValue("guest123");
		}
		
		ImageLoader imageLoader = new ImageLoader();
	    Embedded embedded = imageLoader.loadEmbeddedImageByPath("images/logo.png");
	    embedded.setHeight(75,Unit.PIXELS);
	    embedded.setWidth(200,Unit.PIXELS);
	    appRootLayout.addComponent(embedded);
	    appRootLayout.setComponentAlignment(embedded,Alignment.BOTTOM_CENTER);

	    appRootLayout.setSpacing(true);
	    
	    appRootLayout.addComponent(loginLayout);
	    appRootLayout.setComponentAlignment(loginLayout,Alignment.MIDDLE_CENTER);

		appRootLayout.setSizeFull();
		this.addComponent(appRootLayout);
		this.setComponentAlignment(appRootLayout, Alignment.MIDDLE_CENTER);
		
		final Button loginButton = new Button();
		loginButton.setCaption("Login");
		loginButton.focus();
		loginButton.setTabIndex(3);
		loginLayout.addComponent(loginButton);

		loginButton.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
            	final String username = usernameTxtFld.getValue();
            	final String password = passwordTxtFld.getValue();
            	
            	if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
        			Notification.show("Login failed","Username or password fields are empty",Type.TRAY_NOTIFICATION);
            	}
            	else {
	            	final UsernamePasswordToken token = new UsernamePasswordToken(username,password);
	        		subject = SecurityUtils.getSubject();
	
	        		try{
						subject.login(token);
						subject.getSession().setAttribute("userName", username);
						LOGGER.info("User with username ["+username+"] logged in successfully");
						
						final SaltedHibernateUserDto user = userService.findUserByUsername(username);
						VaadinSession.getCurrent().getSession().setAttribute("accountId",user.getAccount().getAccountId());
						buildUI();
					}
					catch(final IncorrectCredentialsException ice){
						LOGGER.error("Username or password for username ["+username+"] is not valid");
						Notification.show("Login failed","Invalid username or password.",Type.TRAY_NOTIFICATION);
					}
					catch(final CredentialsException ice){
						LOGGER.error("CredentialsException,Error occured while authentication user with username: "+username);
						Notification.show("Login failed","Invalid username or password.",Type.TRAY_NOTIFICATION);
					}
					catch(final AuthenticationException ae){
						LOGGER.error("AuthenticationException,Error occured while authentication user with username: "+username);
						Notification.show("Login failed","Invalid username or password.",Type.TRAY_NOTIFICATION);
					}
					catch(final Exception e){
						LOGGER.error("Error occured while authenticating user",e);
						Notification.show("Something wrong with the server while you tried login.",Type.ERROR_MESSAGE);
					}
	            }
			}	
        });
		
		logoutButton.addClickListener(new Button.ClickListener()
		{
            	private static final long serialVersionUID = 1L;
				@Override
				public void buttonClick(ClickEvent event)
				{
					subject = SecurityUtils.getSubject();
					subject.logout();

					Page.getCurrent().setLocation(Page.getCurrent().getLocation());
				}
			});

	}

	private static final long serialVersionUID = 1L;
	
	public void fragmentChanged(UriFragmentChangedEvent source) {
            String frag = source.getUriFragment();
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
 
	
	private void buildUI(){
		//Parent Layout that holds the ui of the application
        final VerticalLayout appRootLayout = new VerticalLayout();
        
        this.removeAllComponents();
        this.addComponent(appRootLayout);
        UI.getCurrent().setContent(appRootLayout);
        appRootLayout.setSizeFull();

	    final VerticalSplitPanel vert = new VerticalSplitPanel();
	    
	    vert.setSplitPosition(6, Unit.PERCENTAGE);
	    vert.setLocked(true);
	    this.setCaption("CONTENTO3 CMS");
	    vert.setStyleName(Reindeer.SPLITPANEL_SMALL);

	    uiTabsheet.setWidth(100,Unit.PERCENTAGE);
	    uiTabsheet.setHeight(100,Unit.PERCENTAGE);
	    uiTabsheet.setCloseHandler(new CloseHandler() {
			
			@Override
			public void onTabClose(TabSheet tabsheet, Component tabContent) {
				Tab currentTab= uiTabsheet.getTab(tabContent);
				int currentPosition = uiTabsheet.getTabPosition(currentTab);
				if(currentPosition > 0) uiTabsheet.setSelectedTab(currentPosition-1);
				uiTabsheet.removeTab(currentTab);
			}
		} );
	    
	    final HorizontalLayout horizTop = new HorizontalLayout();
	    horizTop.setStyleName(Reindeer.LAYOUT_WHITE);
	    appRootLayout.addComponent(vert);

	    
	    final ImageLoader imageLoader = new ImageLoader();
	    Embedded embedded = imageLoader.loadEmbeddedImageByPath("images/logo.png");
		embedded.setHeight(21, Unit.PIXELS);
		embedded.setWidth(70, Unit.PIXELS);

		horizTop.addComponent(embedded);
	    horizTop.setComponentAlignment(embedded, Alignment.TOP_LEFT);
	    horizTop.setHeight(94,Unit.PERCENTAGE);
	    horizTop.setWidth(100,Unit.PERCENTAGE);
	    

	    vert.addComponent(horizTop);   
        // Add Buttons layout for AccountSettings and Logout..   
		HorizontalLayout buttonsLayout = new HorizontalLayout();
		horizTop.addComponent(buttonsLayout);
		horizTop.setComponentAlignment(buttonsLayout, Alignment.TOP_RIGHT);
		final Button accountButton = new Button ("Account Settings");
		accountButton.addClickListener(new AccountSettingsUIManager(this,helper));
		
		final SaltedHibernateUserDto user = userService.findUserByUsername((String)SessionHelper.loadAttribute("userName"));
		final String welcomeUsrMsg = "<b>Welcome "+ user.getFirstName() + "!</b>"; 
		final Label welcomeUserLbl = new Label(welcomeUsrMsg);
		welcomeUserLbl.setContentMode(ContentMode.HTML);
		buttonsLayout.addComponent(welcomeUserLbl);
		buttonsLayout.addComponent(accountButton);
		accountButton.addStyleName("link");
		
		buttonsLayout.addComponent(logoutButton);
		buttonsLayout.setSpacing(true);     
     
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
			/**
			 * 
			 */
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
    	vert.addComponent(mainAndContentSplitter); 

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

    @Override
    public void attach() {
        super.attach(); // Must call.
    }

}