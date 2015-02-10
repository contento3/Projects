package com.contento3.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.contento3.cms.constant.NavigationConstant;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.security.user.service.SaltedHibernateUserService;
import com.contento3.web.account.AccountSettingsUIManager;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.content.image.ImageLoader;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.layout.LayoutManagerRenderer;
import com.contento3.web.site.SiteMainAreaRenderer;
import com.contento3.web.site.SiteUIManager;
import com.vaadin.event.Action;
import com.vaadin.server.Page;
import com.vaadin.server.Page.UriFragmentChangedEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
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
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.CloseHandler;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
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
    
    HorizontalLayout horizTop;
    
	CMSMainWindow(final SpringContextHelper helper,final Boolean isDemo){ //change
		this.helper = helper;
		this.logoutButton = new Button("Log Out");
		logoutButton.addStyleName("link");
        this.userService = (SaltedHibernateUserService)helper.getBean("saltedHibernateUserService");
        this.isDemo = isDemo;
        this.horizTop = new HorizontalLayout();
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
		usernameTxtFld.focus();
		
		final PasswordField passwordTxtFld = new PasswordField();
		passwordTxtFld.setCaption("Password");
		passwordTxtFld.setTabIndex(2);

		final ImageLoader imageLoader = new ImageLoader();
	    final Embedded embedded = imageLoader.loadEmbeddedImageByPath("images/logo.png");
	    embedded.setHeight(75,Unit.PIXELS);
	    embedded.setWidth(200,Unit.PIXELS);
	    loginLayout.addComponent(embedded);
	    loginLayout.setComponentAlignment(embedded,Alignment.TOP_CENTER);

		loginLayout.addComponent(usernameTxtFld);
		loginLayout.addComponent(passwordTxtFld);
		loginLayout.setComponentAlignment(usernameTxtFld, Alignment.BOTTOM_CENTER);
		loginLayout.setComponentAlignment(passwordTxtFld, Alignment.BOTTOM_CENTER);
		loginLayout.setSizeFull();
		loginLayout.setHeight(300,Unit.PIXELS);
		
		final Panel panel = new Panel();
		panel.setWidth(375,Unit.PIXELS);
		panel.setContent(loginLayout);
		
		//If its the demo account
		//Set the demo username and password
		if (isDemo){
			usernameTxtFld.setValue("guest123");
			passwordTxtFld.setValue("guest123");
		}
		
	    appRootLayout.setSpacing(true);
	    
	    final HorizontalLayout emptyHeadLayout = new HorizontalLayout();
	    emptyHeadLayout.setHeight(65,Unit.PIXELS);
	    appRootLayout.addComponent(emptyHeadLayout);
	    
	    appRootLayout.addComponent(panel);
	    appRootLayout.setComponentAlignment(panel,Alignment.BOTTOM_CENTER);

	    final HorizontalLayout newAccountLayout = new HorizontalLayout();
	    newAccountLayout.setSpacing(true);
	    final Label dontHaveAccountLabel = new Label("Don't have an account?");
	    newAccountLayout.addComponent(dontHaveAccountLabel);
	    newAccountLayout.setComponentAlignment(dontHaveAccountLabel,Alignment.MIDDLE_CENTER);
	    
	    final Button createNewAccountButton = new Button("Create Account");
	    createNewAccountButton.setStyleName("link");
	    
	    newAccountLayout.addComponent(createNewAccountButton);
	    newAccountLayout.setComponentAlignment(createNewAccountButton,Alignment.TOP_CENTER);
	    		
	    appRootLayout.addComponent(newAccountLayout);
	    appRootLayout.setComponentAlignment(newAccountLayout,Alignment.MIDDLE_CENTER);
	    
		this.addComponent(appRootLayout);
		this.setWidth(700, Unit.PIXELS);
		this.setComponentAlignment(appRootLayout, Alignment.TOP_CENTER);
		
		final Button loginButton = new Button();
		loginButton.setCaption("Login");
		loginButton.focus();
		loginButton.setTabIndex(3);
		loginLayout.addComponent(loginButton);
		
		final Button forgotPassword = new Button("Forgot your password?");
		forgotPassword.setStyleName("link");
		loginLayout.addComponent(forgotPassword);
		loginLayout.setComponentAlignment(forgotPassword, Alignment.MIDDLE_CENTER);

		loginLayout.setComponentAlignment(loginButton, Alignment.BOTTOM_CENTER);
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
						VaadinSession.getCurrent().getSession().setAttribute("loggedInUser", user);
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
						LOGGER.error("AuthenticationException,Error occured while authentication user with username: "+username,ae);
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
 
    VerticalSplitPanel vert;
	
	private void buildUI(){
		//Parent Layout that holds the ui of the application
        final VerticalLayout appRootLayout = new VerticalLayout();
        
        this.removeAllComponents();
        this.addComponent(appRootLayout);
        UI.getCurrent().setContent(appRootLayout);
        appRootLayout.setSizeFull();

	    vert = new VerticalSplitPanel();
	    
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
		});

	    appRootLayout.addComponent(vert);
	   
	    //Build the header and add it to the VerticalSplitPanel
	    final HorizontalLayout headerLayout = buildHeader();
	    vert.addComponent(headerLayout);   

	    //BUild all the modules and display after the login
	    final ModuleDashboardBuilder builder = new ModuleDashboardBuilder(helper,vert);
	    final VerticalLayout moduleDashboardUI = builder.buildModuleDashboardUI(horizTop);
	    final VerticalLayout rootDashboardLayout = new VerticalLayout();
	    rootDashboardLayout.addComponent(moduleDashboardUI);
	    rootDashboardLayout.setComponentAlignment(moduleDashboardUI,Alignment.MIDDLE_CENTER);
	    rootDashboardLayout.setSizeFull();
	    
	    vert.addComponent(rootDashboardLayout);	   
	
	    appRootLayout.setComponentAlignment(vert,Alignment.MIDDLE_CENTER);

	    //Build site ui 
    	//vert.addComponent(buildSiteUI()); 
	}
	
	/**
	 * Build the header for the app
	 * @return
	 */
	private HorizontalLayout buildHeader(){
	    horizTop.setStyleName(Reindeer.LAYOUT_WHITE);

	    final ImageLoader imageLoader = new ImageLoader();
	    final Embedded embedded = imageLoader.loadEmbeddedImageByPath("images/logo.png");
		embedded.setHeight(21, Unit.PIXELS);
		embedded.setWidth(70, Unit.PIXELS);

		final HorizontalLayout logoLayout = new HorizontalLayout();
		logoLayout.addComponent(embedded);
		logoLayout.setWidth(70,Unit.PERCENTAGE);
		
		horizTop.addComponent(logoLayout);
		horizTop.setExpandRatio(logoLayout, 3);

		horizTop.setComponentAlignment(logoLayout, Alignment.TOP_LEFT);
	    horizTop.setHeight(94,Unit.PERCENTAGE);
	    horizTop.setWidth(100,Unit.PERCENTAGE);

	    // Add Buttons layout for AccountSettings and Logout..   
		HorizontalLayout buttonsLayout = new HorizontalLayout();
		horizTop.addComponent(buttonsLayout);
		horizTop.setComponentAlignment(buttonsLayout, Alignment.TOP_RIGHT);
		horizTop.setExpandRatio(buttonsLayout, 40);

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

		return horizTop;
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
    		horiz.setSecondComponent(siteUIMgr.render(SiteUIManager.NEWSITE));
    	}
    }

    @Override
    public void attach() {
        super.attach(); // Must call.
    }

}