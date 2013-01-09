package com.contento3.web.user.security;

import java.util.Collection;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountService;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.security.user.service.SaltedHibernateUserService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

public class UserPopup extends CustomComponent implements Window.CloseListener{
	private static final long serialVersionUID = 1L;

	/**
	 *  Reference to main window
	 */
	Window mainwindow; 
	
	/**
	 * The window to be opened
	 */
	Window popupWindow; 
	
	/**
	 * Button for opening the window
	 */
	Button openbutton; 
	
	/**
	 *  A button in the window
	 */
	Button closebutton; 
	
	/**
	 * Used to get service beans from spring context.
	 */
	SpringContextHelper helper;
	
	/**
	 * User service used for user related operations
	 */
	final SaltedHibernateUserService userService;
	
	/**
	 * Account Service
	 */
	final AccountService accountService;
	
	/**
	 * Table for User
	 */
    final Table userTable;
    
	boolean isModalWindowClosable = true;
	
	/**
	 * Constructor
	 * @param main
	 * @param helper
	 * @param table
	 */
	public UserPopup(final Window main,final SpringContextHelper helper,final Table table) {
		this.mainwindow = main;
		this.helper = helper;
		this.userTable = table;
		this.userService = (SaltedHibernateUserService) this.helper.getBean("saltedHibernateUserService");
		this.accountService = (AccountService) this.helper.getBean("accountService");

		 // The component contains a button that opens the window.
        final VerticalLayout layout = new VerticalLayout();
        openbutton = new Button("Add User", this, "openButtonClick");
        layout.addComponent(openbutton);

        setCompositionRoot(layout);
	}
	
	  /** 
	   * Handle the clicks for the two buttons.
	   */
    public void openButtonClick(Button.ClickEvent event) {
        /* Create a new window. */
        final Button userButton = new Button();
		popupWindow = new Window();
    	
		popupWindow.setPositionX(200);
    	popupWindow.setPositionY(100);

    	popupWindow.setHeight(41,Sizeable.UNITS_PERCENTAGE);
    	popupWindow.setWidth(20,Sizeable.UNITS_PERCENTAGE);
       
    	/* Add the window inside the main window. */
        mainwindow.addWindow(popupWindow);
        
        /* Listen for close events for the window. */
        popupWindow.addListener(this);
        popupWindow.setModal(true);
        
        final VerticalLayout popupMainLayout = new VerticalLayout();
        final Label label = new Label("User Name");
        label.setWidth(100,Sizeable.UNITS_PERCENTAGE);
        final HorizontalLayout inputDataLayout = new HorizontalLayout();
        final TextField userNameTxtFld = new TextField("User Name");
        userNameTxtFld.setInputPrompt("Enter user name");
        userNameTxtFld.setWidth(100,Sizeable.UNITS_PERCENTAGE);
        userNameTxtFld.setColumns(20);
        
        inputDataLayout.setSizeFull();
        inputDataLayout.setSpacing(true);
        inputDataLayout.addComponent(userNameTxtFld);
        inputDataLayout.setComponentAlignment(userNameTxtFld, Alignment.TOP_LEFT);
        
        popupMainLayout.setSpacing(true);
        popupMainLayout.addComponent(inputDataLayout);
       
        /* adding email text field */
        final HorizontalLayout emailLayout = new HorizontalLayout();
       
        final TextField emailTxtFld = new TextField("Email");
        emailTxtFld.setInputPrompt("Enter user email");
        emailTxtFld.setWidth(100,Sizeable.UNITS_PERCENTAGE);
        emailTxtFld.setColumns(20);
     	
        emailLayout.setSizeFull();
        emailLayout.setSpacing(true);
        emailLayout.addComponent(emailTxtFld);
        emailLayout.setComponentAlignment(emailTxtFld, Alignment.TOP_LEFT);
     	popupMainLayout.addComponent(emailLayout);

        /* adding first name text field */
        final HorizontalLayout firstNameLayout = new HorizontalLayout();
       
        final TextField fNameTxtFld = new TextField("First Name");
        fNameTxtFld.setInputPrompt("Enter first name");
        fNameTxtFld.setWidth(100,Sizeable.UNITS_PERCENTAGE);
        fNameTxtFld.setColumns(20);
     	
        firstNameLayout.setSizeFull();
        firstNameLayout.setSpacing(true);
        firstNameLayout.addComponent(fNameTxtFld);
        firstNameLayout.setComponentAlignment(fNameTxtFld, Alignment.TOP_LEFT);
     	popupMainLayout.addComponent(firstNameLayout);

     	/* adding first name text field */
        final HorizontalLayout lastNameLayout = new HorizontalLayout();
       
        final TextField lNameTxtFld = new TextField("Last Name");
        fNameTxtFld.setInputPrompt("Enter last name");
        fNameTxtFld.setWidth(100,Sizeable.UNITS_PERCENTAGE);
        fNameTxtFld.setColumns(20);
     	
        firstNameLayout.setSizeFull();
        firstNameLayout.setSpacing(true);
        firstNameLayout.addComponent(lNameTxtFld);
        firstNameLayout.setComponentAlignment(lNameTxtFld, Alignment.TOP_LEFT);
     	popupMainLayout.addComponent(lastNameLayout);

        final HorizontalLayout addButtonLayout = new HorizontalLayout();
        popupMainLayout.addComponent(addButtonLayout);

        addButtonLayout.addComponent(userButton);
        addButtonLayout.setComponentAlignment(userButton, Alignment.BOTTOM_RIGHT);
        addButtonLayout.setWidth(100, Sizeable.UNITS_PERCENTAGE);
        
        popupWindow.addComponent(popupMainLayout);
        popupWindow.setResizable(false);
        /* Allow opening only one window at a time. */
        openbutton.setEnabled(false);

		

    	if (event.getButton().getCaption().equals("Edit")){
			userButton.setCaption("Save");
			popupWindow.setCaption("Edit user");
			final String username = (String) event.getButton().getData();
			SaltedHibernateUserDto userDto = userService.findUserByName(username);
			userNameTxtFld.setValue(userDto.getName());
			//String description = userDto.getDescription();
	    	
//			if(description==null){
//	    		descriptionArea.setValue("");
//			}else{
//				descriptionArea.setValue(description);
//			}
	        userButton.addListener(new ClickListener() {
				private static final long serialVersionUID = 1L;
				public void buttonClick(ClickEvent event) {
					handleEditUser(userNameTxtFld,emailTxtFld,fNameTxtFld,lNameTxtFld,username);
				}	
			});
    	}
    	else
    	{
	        userButton.setCaption("Add");
	        popupWindow.setCaption("Add new user");
	        userButton.addListener(new ClickListener() {
				private static final long serialVersionUID = 1L;
				public void buttonClick(ClickEvent event) {
					handleNewUser(userNameTxtFld,emailTxtFld,fNameTxtFld,lNameTxtFld);
				}	
			});
    	}
    }

    /**
     * Handles adding new SiteDomain
     * @param textField
     */
	private void handleNewUser(final TextField username,final TextField emailField,final TextField firstName,final TextField lastName){
		SaltedHibernateUserDto userDto = new SaltedHibernateUserDto();
		try {
			userDto.setUserName(username.getValue().toString());
			userDto.setEnabled(true);
			
			final AccountDto accountDto = accountService.findAccountById((Integer)SessionHelper.loadAttribute(mainwindow, "accountId"));
			userDto.setAccount(accountDto);
			
			userService.create(userDto);
		} catch (EntityAlreadyFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainwindow.showNotification(userDto.getName()+" user created succesfully");
		resetTable();
    }

    
    /**
     * Handles editing users
     * @param textField
     */
	private void handleEditUser(final TextField username,final TextField emailField,final TextField firstName,final TextField lastName,final String editId){
		final SaltedHibernateUserDto userDto = userService.findUserByName(editId);
		userDto.setUserName(username.getValue().toString());
		//userService.update(userDto);
		mainwindow.showNotification(userDto.getName()+" user edit succesfully");
		resetTable();
    }
	/**
	 * Reset table
	 */
	 @SuppressWarnings("rawtypes")
		private void resetTable(){
			final AbstractTableBuilder tableBuilder = new UserTableBuilder(mainwindow,helper,userTable);
			final Collection<SaltedHibernateUserDto> userDto = userService.findUsersByAccountId((Integer)SessionHelper.loadAttribute(mainwindow, "accountId"));
			tableBuilder.rebuild((Collection)userDto);
			mainwindow.removeWindow(popupWindow);
	        openbutton.setEnabled(true);
	    }
	 
	  /**
	   *  Handle Close button click and close the window.
	   */
	    public void closeButtonClick(Button.ClickEvent event) {
	    	if (!isModalWindowClosable){
	        /* Windows are managed by the application object. */
	        mainwindow.removeWindow(popupWindow);
	        
	        /* Return to initial state. */
	        openbutton.setEnabled(true);
	    	}
	    }

	    /**
	     * Handle window close event
	     */
		@Override
		public void windowClose(CloseEvent e) {
			  /* Return to initial state. */
	        openbutton.setEnabled(true);
		}


}
