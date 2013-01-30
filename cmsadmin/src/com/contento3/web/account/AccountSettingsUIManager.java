package com.contento3.web.account;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.contento3.account.service.AccountService;
import com.contento3.security.user.service.SaltedHibernateUserService;
import com.contento3.web.CMSMainWindow;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

public class AccountSettingsUIManager extends CustomComponent
implements Window.CloseListener,Button.ClickListener {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = Logger.getLogger(AccountSettingsUIManager.class);
	
    Window mainwindow;  // Reference to main window
    Window popupWindow;    // The window to be opened
    Button openbutton;  // Button for opening the window
    Button closebutton; // A button in the window
    Button editButton;
    
    private final AccountService accountService;

    private final SaltedHibernateUserService userService;
    
    private final AccountForm accountForm;
    
    boolean isModalWindowClosable = true;
    
    SpringContextHelper contextHelper;
    
    private Integer categoryId;

    /**
     * Renders the tree with categories for Parent category selection.
     */
    final Tree tree;
    
    Integer selectedParentCategory = -1;
    
    public AccountSettingsUIManager(final Window main,final SpringContextHelper contextHelper) {
        mainwindow = main;
        this.contextHelper = contextHelper;
        this.accountService = (AccountService)contextHelper.getBean("accountService");
        this.userService = (SaltedHibernateUserService)contextHelper.getBean("saltedHibernateUserService");

        tree = new Tree();
        // The component contains a button that opens the window./////
        final VerticalLayout layout = new VerticalLayout();
        openbutton = new Button("Add Category", this, "openButtonClick");
        layout.addComponent(openbutton);
        
        accountForm = new AccountForm();
    	accountForm.getFirstName().setCaption("First Name");
    	accountForm.getLastName().setCaption("Last Name");
    	accountForm.getEmail().setCaption("Email");
    	accountForm.getNewPassword().setCaption("New Password");
    	accountForm.getConfirmNewPassword().setCaption("Confirm Password");
    	accountForm.getSubmitButton().addListener(new AccountSettingsUpdateListener(main, userService, accountService, accountForm, contextHelper));
    	//accountForm.getSubmitButton().addListener(this);
    	
        setCompositionRoot(layout);
    }

    /** Handle the clicks for the two buttons. */
    public void openButtonClick(Button.ClickEvent event) {
        /* Create a new window. */
        final Button categoryButton = new Button();
		popupWindow = new Window("Account Settings");
    	
		popupWindow.setPositionX(200);
    	popupWindow.setPositionY(100);

    	popupWindow.setHeight(56,Sizeable.UNITS_PERCENTAGE);
    	popupWindow.setWidth(30,Sizeable.UNITS_PERCENTAGE);
        
    	VerticalLayout formLayout = new VerticalLayout();
    	formLayout.addComponent(accountForm.getFirstName());
    	formLayout.addComponent(accountForm.getLastName());
    	formLayout.addComponent(accountForm.getEmail());
    	formLayout.addComponent(accountForm.getNewPassword());
    	formLayout.addComponent(accountForm.getConfirmNewPassword());
    	formLayout.addComponent(accountForm.getSubmitButton());
    	
    	formLayout.setMargin(true);
    	popupWindow.setContent(formLayout);
    	
    	/* Add the window inside the main window. */
        mainwindow.addWindow(popupWindow);
        
        /* Listen for close events for the window. */
        popupWindow.addListener(this);
        popupWindow.setModal(true);
    }
    
    /** Handle Close button click and close the window. */
    public void closeButtonClick(Button.ClickEvent event) {
    	if (!isModalWindowClosable){
        /* Windows are managed by the application object. */
        mainwindow.removeWindow(popupWindow);
        
        /* Return to initial state. */
        openbutton.setEnabled(true);
    	}
    }

	@Override
	public void windowClose(CloseEvent e) {
        /* Return to initial state. */
        openbutton.setEnabled(true);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		openButtonClick(event);
	}
}

