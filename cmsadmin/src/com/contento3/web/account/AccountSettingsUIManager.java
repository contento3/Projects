package com.contento3.web.account;

import org.apache.log4j.Logger;

import com.contento3.account.service.AccountService;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.security.user.service.SaltedHibernateUserService;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

public class AccountSettingsUIManager extends CustomComponent
implements Window.CloseListener,Button.ClickListener {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = Logger.getLogger(AccountSettingsUIManager.class);
	
    VerticalLayout mainwindow;  // Reference to main window
    Window popupWindow;    // The window to be opened
    Button openbutton;  // Button for opening the window
    Button closebutton; // A button in the window
    Button editButton;
    
    private final AccountService accountService;

    private final SaltedHibernateUserService userService;
    
    private final AccountForm accountForm;
    
    boolean isModalWindowClosable = true;
    
    SpringContextHelper contextHelper;
    

    /**
     * Renders the tree with categories for Parent category selection.
     */
    final Tree tree;
    
    Integer selectedParentCategory = -1;
    
    public AccountSettingsUIManager(final VerticalLayout main,final SpringContextHelper contextHelper) {
        mainwindow = main;
        this.contextHelper = contextHelper;
        this.accountService = (AccountService)contextHelper.getBean("accountService");
        this.userService = (SaltedHibernateUserService)contextHelper.getBean("saltedHibernateUserService");

        tree = new Tree();
        // The component contains a button that opens the window./////
        final VerticalLayout layout = new VerticalLayout();
       
        //CHANGED openbutton = new Button("Add Category", this, "openButtonClick");
        openbutton = new Button("Add Category");
        openbutton.addClickListener(this);
        layout.addComponent(openbutton);
        
        accountForm = new AccountForm();
    	accountForm.getFirstName().setCaption("First Name");
    	accountForm.getLastName().setCaption("Last Name");
    	accountForm.getEmail().setCaption("Email");
    	accountForm.getNewPassword().setCaption("New Password");
    	accountForm.getConfirmNewPassword().setCaption("Confirm Password");
    	accountForm.getSubmitButton().addClickListener(new AccountSettingsUpdateListener(main, userService, accountService, accountForm, contextHelper));
    	
        setCompositionRoot(layout);
    }

    /** Handle the clicks for the two buttons. */
    public void openButtonClick(Button.ClickEvent event) {
        /* Create a new window. */
		popupWindow = new Window("Account Settings");
    	
		popupWindow.setPositionX(200);
    	popupWindow.setPositionY(100);

    	popupWindow.setHeight(56,Unit.PERCENTAGE);
    	popupWindow.setWidth(30,Unit.PERCENTAGE);
        
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
        UI.getCurrent().addWindow(popupWindow);
        
        /* Listen for close events for the window. */
        popupWindow.addCloseListener(this);
        popupWindow.setModal(true);
        populateAccountForm();
    }
    
    /** Handle Close button click and close the window. */
    public void closeButtonClick(Button.ClickEvent event) {
    	if (!isModalWindowClosable){
        /* Windows are managed by the application object. */
        mainwindow.removeComponent(popupWindow);
        
        /* Return to initial state. */
        openbutton.setEnabled(true);
    	}
    }

	@Override
	public void windowClose(final CloseEvent e) {
        /* Return to initial state. */
        openbutton.setEnabled(true);
	}

	@Override
	public void buttonClick(final ClickEvent event) {
		openButtonClick(event);
	}
	
	private void populateAccountForm(){
		final SaltedHibernateUserDto user = userService.findUserByUsername((String)SessionHelper.loadAttribute("userName"));
		accountForm.getFirstName().setValue(user.getFirstName());
		accountForm.getLastName().setValue(user.getLastName());
		
		if (null!=user.getEmail())
			accountForm.getEmail().setValue(user.getEmail());
	}
}

