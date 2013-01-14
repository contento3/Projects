package com.contento3.web.account;

import com.contento3.account.service.AccountService;
import com.contento3.security.user.service.SaltedHibernateUserService;
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
	
    Window mainwindow;  // Reference to main window
    Window popupWindow;    // The window to be opened
    Button openbutton;  // Button for opening the window
    Button closebutton; // A button in the window
    Button editButton;
    
    private final AccountService accountService;

    private final SaltedHibernateUserService userService;
    
    boolean isModalWindowClosable = true;
    
    SpringContextHelper helper;
    
    private Integer categoryId;

    /**
     * Renders the tree with categories for Parent category selection.
     */
    final Tree tree;
    
    Integer selectedParentCategory = -1;
    
    public AccountSettingsUIManager(final Window main,final SpringContextHelper helper) {
        mainwindow = main;
        this.helper = helper;
        this.accountService = (AccountService)helper.getBean("accountService");
        this.userService = (SaltedHibernateUserService)helper.getBean("saltedHibernateUserService");

        tree = new Tree();
        // The component contains a button that opens the window./////
        final VerticalLayout layout = new VerticalLayout();
        openbutton = new Button("Add Category", this, "openButtonClick");
        layout.addComponent(openbutton);

        setCompositionRoot(layout);
    }

    /** Handle the clicks for the two buttons. */
    public void openButtonClick(Button.ClickEvent event) {
        /* Create a new window. */
        final Button categoryButton = new Button();
		popupWindow = new Window();
    	
		popupWindow.setPositionX(200);
    	popupWindow.setPositionY(100);

    	popupWindow.setHeight(56,Sizeable.UNITS_PERCENTAGE);
    	popupWindow.setWidth(30,Sizeable.UNITS_PERCENTAGE);
       
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
		// TODO Auto-generated method stub
		
	}
}

