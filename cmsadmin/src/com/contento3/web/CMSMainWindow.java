package com.contento3.web;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.contento3.cms.constant.NavigationConstant;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.security.user.service.SaltedHibernateUserService;
import com.contento3.web.account.AccountSettingsUIManager;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.common.helper.TabSheetHelper;
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
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
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

    private Button logoutButton;
    public Button accountButton;
    
	TextField textField = new TextField();
    Tree root;
    HorizontalLayout l = new HorizontalLayout();
	SiteMainAreaRenderer siteMainRenderer;
	LayoutManagerRenderer layoutManagerRenderer;
	SpringContextHelper helper;
	//UriFragmentUtility uri;
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

	CMSMainWindow(final SpringContextHelper helper){ //change
		this.helper = helper;
		this.logoutButton = new Button("Log Out");
		logoutButton.addStyleName("link");
        this.userService = (SaltedHibernateUserService)helper.getBean("saltedHibernateUserService");
		buildLogin();
	}
	
	@SuppressWarnings("deprecation")
	public void buildLogin(){
		final VerticalLayout appRootLayout = new VerticalLayout();
		LoginForm login = new LoginForm();
		login.setHeight(180,Unit.PIXELS);
		login.setWidth(145,Unit.PIXELS);
		
		
		
	    ImageLoader imageLoader = new ImageLoader();
	    Embedded embedded = imageLoader.loadEmbeddedImageByPath("images/logo.png");
	    embedded.setHeight(75,Unit.PIXELS);
	    embedded.setWidth(200,Unit.PIXELS);
	    appRootLayout.addComponent(embedded);
	    appRootLayout.setComponentAlignment(embedded,Alignment.BOTTOM_CENTER);

	    appRootLayout.setSpacing(true);
	    
	    appRootLayout.addComponent(login);
	    appRootLayout.setComponentAlignment(login,Alignment.MIDDLE_CENTER);

		appRootLayout.setSizeFull();
		this.addComponent(appRootLayout);
		this.setComponentAlignment(appRootLayout, Alignment.MIDDLE_CENTER);
		
		
		login.addLoginListener(new LoginForm.LoginListener() {
            private static final long serialVersionUID = 1L;

			public void onLogin(LoginEvent event) {
            	final String username = event.getLoginParameter("username");
            	final String password = event.getLoginParameter("password");
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
				catch(IncorrectCredentialsException ice){
					LOGGER.error("Username or password for username ["+username+"] is not valid");
					Notification.show("Invalid username or password.",Type.WARNING_MESSAGE);
				}
				catch(CredentialsException ice){
					LOGGER.error("CredentialsException,Error occured while authentication user with username: "+username);
					Notification.show("Invalid username or password.",Type.WARNING_MESSAGE);
				}
				catch(AuthenticationException ae){
					LOGGER.error("AuthenticationException,Error occured while authentication user with username: "+username);
					Notification.show("Invalid username or password.",Type.WARNING_MESSAGE);
				}
				catch(Exception e){
					LOGGER.error("Error occured while authenticating user",e);
					Notification.show("Something wrong with the server while you tried login.",Type.ERROR_MESSAGE);
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
	    
	    HorizontalLayout horizTop = new HorizontalLayout();
	    horizTop.setStyleName(Reindeer.LAYOUT_WHITE);
	    appRootLayout.addComponent(vert);

	    
	    ImageLoader imageLoader = new ImageLoader();
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
        mainAndContentSplitter.setSplitPosition(75,Unit.PERCENTAGE);
        
        mainAndContentSplitter.addSplitterClickListener(new SplitterClickListener(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void splitterClick(SplitterClickEvent event){
				float splitPosition = mainAndContentSplitter.getSplitPosition();
				
		        if (splitPosition==96)
		        	mainAndContentSplitter.setSplitPosition(75,Unit.PERCENTAGE);
		        else
		        	mainAndContentSplitter.setSplitPosition(96,Unit.PERCENTAGE);
			}
    	});

        // Add a horizontal SplitPanel to the lower area
        horiz.setLocked(true);
        horiz.setSplitPosition(15);
        
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

        
        Item dashboard = hwContainer.addItem(NavigationConstant.DASHBOARD);
        dashboard.getItemProperty("name").setValue(NavigationConstant.DASHBOARD);
        root.setItemIcon(dashboard, new ExternalResource("images/home-icon.png"));
        dashboard.getItemProperty("icon").setValue(new ExternalResource("images/home-icon.png"));

        Item item0 = hwContainer.addItem("Sites");
        root.setItemIcon(item0, new ExternalResource("images/site.png"));
        item0.getItemProperty("name").setValue("Sites");
        item0.getItemProperty("id").setValue(new Integer(-1));
        item0.getItemProperty("icon").setValue(new ExternalResource("images/site.png"));

        Item contentMgmt = hwContainer.addItem(NavigationConstant.CONTENT_MANAGER);
        contentMgmt.getItemProperty("name").setValue(NavigationConstant.CONTENT_MANAGER);
        root.setItemIcon(contentMgmt, new ExternalResource("images/content.png"));
        contentMgmt.getItemProperty("icon").setValue(new ExternalResource("images/content.png"));

        Item category = hwContainer.addItem(NavigationConstant.CATEGORY_MGMT);
        category.getItemProperty("name").setValue(NavigationConstant.CATEGORY_MGMT);
        root.setItemIcon(category, new ExternalResource("images/category.png"));
        category.getItemProperty("icon").setValue(new ExternalResource("images/category.png"));
        
        Item globalConfig = hwContainer.addItem(NavigationConstant.GLOBAL_CONFIG);
        globalConfig.getItemProperty("name").setValue(NavigationConstant.GLOBAL_CONFIG);
        root.setItemIcon(globalConfig, new ExternalResource("images/configuration.png"));
        globalConfig.getItemProperty("icon").setValue(new ExternalResource("images/configuration.png"));
        
        Item template = hwContainer.addItem("Template");
        template.getItemProperty("name").setValue(NavigationConstant.TEMPLATE);
        root.setItemIcon(template, new ExternalResource("images/template.png"));
        template.getItemProperty("icon").setValue(new ExternalResource("images/template.png"));
        
        
        Item modules = hwContainer.addItem("Modules");
        modules.getItemProperty("name").setValue(NavigationConstant.MODULES);
        root.setItemIcon(modules, new ExternalResource("images/module-icon.png"));
        modules.getItemProperty("icon").setValue(new ExternalResource("images/module-icon.png"));

        // DO NOT REMOVE THIS -- The functionality regarding layout management will be developed soon        
        // Item layoutManager = hwContainer.addItem(NavigationConstant.LAYOUT_MANAGER);
        // layoutManager.getItemProperty("name").setValue(NavigationConstant.LAYOUT_MANAGER);

        Item userMgmtItem = hwContainer.addItem(NavigationConstant.SECURITY);
        userMgmtItem.getItemProperty("name").setValue(NavigationConstant.SECURITY);
        root.setItemIcon(userMgmtItem, new ExternalResource("images/security.png"));
        userMgmtItem.getItemProperty("icon").setValue(new ExternalResource("images/security.png"));

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
	    	    		UIManager contentUIMgr = UIManagerCreator.createUIManager(uiTabsheet,Manager.Content,helper);
	    	    		
	    	    	//	Component tabSheet = contentUIMgr.render(itemSelected,hwContainer);
	    	    	//	if (null!=tabSheet){
	    	    			horiz.setSecondComponent(contentUIMgr.render(itemSelected,hwContainer));
	    	    	//	}
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
	    	    		UIManager categoryUIMgr = UIManagerCreator.createUIManager(uiTabsheet,Manager.Category,helper);
	    	    		horiz.setSecondComponent(categoryUIMgr.render(null));
	        		}
                	else if (null!=itemSelected && itemSelected.equals("Sites")) {
	            		hwContainer.setChildrenAllowed("Sites", true);	
	            		
	            		//TODO no need to go to fetch sites if they are not null
	            		// but need to handle the situation where a new site is added 
	            		// so that this new site is added and hence displayed to the tree 
	            		//if (CollectionUtils.isEmpty(sites)){
	            			SiteService siteService = (SiteService) helper.getBean("siteService");
	            			sites = siteService.findSitesByAccountId((Integer)SessionHelper.loadAttribute("accountId"));
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

    /*
     * Used to handle events after the user clicks the 
     * context menu from the left navigation menu.
     */
    public void handleAction(Action action, Object sender, Object target) {
    	//If the user right clicks the 'Site' and then click 'Create new site'
    	//Then a new site creation screen needs to be rendered
    	if (action.equals(ACTION_ADD_SITE)) {
		//	UIManager siteUIMgr = UIManagerCreator.createUIManager(uiTabsheet,Manager.Site,helper,getWindow());

    		horiz.setSecondComponent(siteUIMgr.render(SiteUIManager.NEWSITE));
    		//SiteService siteService = (SiteService) helper.getBean("siteService");
    	}
    }

    @Override
    public void attach() {
        super.attach(); // Must call.
//CHAGNED          WebApplicationContext ctx = ((WebApplicationContext) this.getApplication().getContext());
//CHANGED          HttpSession session = ctx.getHttpSession();
          //if (!session.isNew()){
        //	  session.setMaxInactiveInterval(50000*60);
         // }
         // else {
        //	  session.setMaxInactiveInterval(0);
        //  }
    	 //CHANGED session.setAttribute("accountId", new Integer("1"));
    }

}