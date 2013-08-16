package com.contento3.web.user.security;

import java.util.Collection;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountService;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.security.user.service.SaltedHibernateUserService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

public class UserPopup extends CustomComponent implements Window.CloseListener,Button.ClickListener{
	private static final long serialVersionUID = 1L;
	
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
	
	final VerticalLayout layout;
	
	/**
	 * Constructor
	 * @param main
	 * @param helper
	 * @param table
	 */
	public UserPopup(final SpringContextHelper helper,final Table table) {
		this.helper = helper;
		this.userTable = table;
		this.userService = (SaltedHibernateUserService) this.helper.getBean("saltedHibernateUserService");
		this.accountService = (AccountService) this.helper.getBean("accountService");

		 // The component contains a button that opens the window.
        layout = new VerticalLayout();

        openbutton = new Button("Add User");
        openbutton.addClickListener(this);
        
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

    	popupWindow.setHeight(61,Unit.PERCENTAGE);
    	popupWindow.setWidth(23,Unit.PERCENTAGE);
       
    	/* Add the window inside the main window. */
        UI.getCurrent().addWindow(popupWindow);
        
        /* Listen for close events for the window. */
        popupWindow.addCloseListener(this);
        popupWindow.setModal(true);
        
        final VerticalLayout popupMainLayout = new VerticalLayout();
        final Label label = new Label("User Name");
        label.setWidth(100,Unit.PERCENTAGE);
        final HorizontalLayout inputDataLayout = new HorizontalLayout();
        final TextField userNameTxtFld = new TextField("User Name");
        userNameTxtFld.setInputPrompt("Enter user name");
        userNameTxtFld.setWidth(100,Unit.PERCENTAGE);
        userNameTxtFld.setColumns(15);
        
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
        emailTxtFld.setWidth(100,Unit.PERCENTAGE);
        emailTxtFld.setColumns(15);
     	
        emailLayout.setSizeFull();
        emailLayout.setSpacing(true);
        emailLayout.addComponent(emailTxtFld);
        emailLayout.setComponentAlignment(emailTxtFld, Alignment.TOP_LEFT);
     	popupMainLayout.addComponent(emailLayout);

        /* adding first name text field */
        final HorizontalLayout firstNameLayout = new HorizontalLayout();
       
        final TextField fNameTxtFld = new TextField("First Name");
        fNameTxtFld.setInputPrompt("Enter first name");
        fNameTxtFld.setWidth(100,Unit.PERCENTAGE);
        fNameTxtFld.setColumns(15);
     	
        firstNameLayout.setSizeFull();
        firstNameLayout.setSpacing(true);
        firstNameLayout.addComponent(fNameTxtFld);
        firstNameLayout.setComponentAlignment(fNameTxtFld, Alignment.TOP_LEFT);
     	popupMainLayout.addComponent(firstNameLayout);

     	
     	/* adding last name text field */
        final HorizontalLayout lastNameLayout = new HorizontalLayout();
       
        final TextField lNameTxtFld = new TextField("Last Name");
        lNameTxtFld.setInputPrompt("Enter last name");
        lNameTxtFld.setWidth(100,Unit.PERCENTAGE);
        lNameTxtFld.setColumns(15);
     	
        lastNameLayout.setSizeFull();
        lastNameLayout.setSpacing(true);
        lastNameLayout.addComponent(lNameTxtFld);
        lastNameLayout.setComponentAlignment(lNameTxtFld, Alignment.TOP_LEFT);
     	popupMainLayout.addComponent(lastNameLayout);


     	/* adding last name text field */
        final HorizontalLayout pwdLayout = new HorizontalLayout();
       
        final PasswordField pwdTxtFld = new PasswordField("Password");
        pwdTxtFld.setInputPrompt("Enter password");
        pwdTxtFld.setWidth(100,Unit.PERCENTAGE);
        pwdTxtFld.setColumns(15);
     	
        pwdLayout.setSizeFull();
        pwdLayout.setSpacing(true);
        pwdLayout.addComponent(pwdTxtFld);
        pwdLayout.setComponentAlignment(pwdTxtFld, Alignment.TOP_LEFT);
     	popupMainLayout.addComponent(pwdLayout);

     	/* adding last name text field */
        final HorizontalLayout confirmPwdLayout = new HorizontalLayout();
       
        final PasswordField confirmPwdTxtFld = new PasswordField("Confirm Password");
        confirmPwdTxtFld.setInputPrompt("Confirm Password");
        confirmPwdTxtFld.setWidth(100,Unit.PERCENTAGE);
        confirmPwdTxtFld.setColumns(20);
        
     	
        confirmPwdLayout.setSizeFull();
        confirmPwdLayout.setSpacing(true);
        confirmPwdLayout.addComponent(confirmPwdTxtFld);
        confirmPwdLayout.setComponentAlignment(confirmPwdTxtFld, Alignment.TOP_LEFT);
     	popupMainLayout.addComponent(confirmPwdLayout);
     	
        final HorizontalLayout addButtonLayout = new HorizontalLayout();
        popupMainLayout.addComponent(addButtonLayout);

        addButtonLayout.addComponent(userButton);
        addButtonLayout.setComponentAlignment(userButton, Alignment.BOTTOM_RIGHT);
        addButtonLayout.setWidth(100, Unit.PERCENTAGE);
        
        popupWindow.setContent(popupMainLayout);
        popupWindow.setResizable(false);
        /* Allow opening only one window at a time. */
        openbutton.setEnabled(false);

		
		if (pwdTxtFld.getValue().equals(confirmPwdTxtFld.getValue())){
			if (event.getButton().getCaption().equals("Edit")){
				userButton.setCaption("Save");
				popupWindow.setCaption("Edit user");
				final String username = (String) event.getButton().getData();
				SaltedHibernateUserDto userDto = userService.findUserByUsername(username);
				userNameTxtFld.setValue(userDto.getName());
				lNameTxtFld.setValue(userDto.getLastName());
				fNameTxtFld.setValue(userDto.getFirstName());
				
				if (null!=userDto.getEmail()){
					emailTxtFld.setValue(userDto.getEmail());
				}
				
		        userButton.addClickListener(new ClickListener() {
					private static final long serialVersionUID = 1L;
					public void buttonClick(ClickEvent event) {
						handleEditUser(userNameTxtFld,emailTxtFld,fNameTxtFld,lNameTxtFld,username,pwdTxtFld);
					}	
				});
	    	}
	    	else
	    	{
		        userButton.setCaption("Add");
		        popupWindow.setCaption("Add new user");
		        userButton.addClickListener(new ClickListener() {
					private static final long serialVersionUID = 1L;
					public void buttonClick(ClickEvent event) {
							handleNewUser(userNameTxtFld,emailTxtFld,fNameTxtFld,lNameTxtFld,pwdTxtFld);
						}
		        });
	    	}
		}
		else {
			Notification.show("Password and confirm password field does not match", Notification.Type.WARNING_MESSAGE);
		}
    }

    /**
     * Handles adding new SiteDomain
     * @param textField
     */
	private void handleNewUser(final TextField username,final TextField emailField,final TextField firstName,final TextField lastName,final PasswordField pwdTxtFld){
		SaltedHibernateUserDto userDto = new SaltedHibernateUserDto();
		try {
			userDto.setUserName(username.getValue().toString());
			userDto.setEnabled(true);
			userDto.setPassword(pwdTxtFld.getValue().toString());
			userDto.setFirstName(firstName.getValue().toString());
			userDto.setLastName(lastName.getValue().toString());
			userDto.setEmail(emailField.getValue().toString());
			final AccountDto accountDto = accountService.findAccountById((Integer)SessionHelper.loadAttribute("accountId"));
			userDto.setAccount(accountDto);
			userService.create(userDto);
		} catch (EntityAlreadyFoundException e) {
			Notification.show("User already exists", Notification.Type.ERROR_MESSAGE);
		}
		catch (EntityNotCreatedException e) {
			Notification.show("User not created", Notification.Type.ERROR_MESSAGE);
		}
		Notification.show(userDto.getName()+" user created succesfully");
		resetTable();
    }
    
    /**
     * Handles editing users
     * @param textField
     */
	private void handleEditUser(final TextField username,final TextField emailField,final TextField firstName,final TextField lastName,final String editId,final PasswordField password){
		final SaltedHibernateUserDto userDto = userService.findUserByUsername(editId);
		userDto.setUserName(username.getValue().toString());
		userDto.setPassword(password.getValue().toString());
		//userService.update(userDto);
		Notification.show(userDto.getName()+" user edit succesfully");
		resetTable();
    }
	
	/**
	 * Reset table
	 */
	 @SuppressWarnings("rawtypes")
		private void resetTable(){
			final AbstractTableBuilder tableBuilder = new UserTableBuilder(helper,userTable);
			final Collection<SaltedHibernateUserDto> userDto = userService.findUsersByAccountId((Integer)SessionHelper.loadAttribute("accountId"));
			tableBuilder.rebuild((Collection)userDto);
			layout.removeComponent(popupWindow);

	        openbutton.setEnabled(true);
	    }
	 
	  /**
	   *  Handle Close button click and close the window.
	   */
	    public void closeButtonClick(Button.ClickEvent event) {
	    	if (!isModalWindowClosable){
	        /* Windows are managed by the application object. */
	        layout.removeComponent(popupWindow);
	        
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

		@Override
		public void buttonClick(ClickEvent event) {
			this.openButtonClick(event);
		}


}
