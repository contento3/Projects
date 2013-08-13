package com.contento3.web.user.security;
import java.util.Collection;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountService;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.security.role.dto.RoleDto;
import com.contento3.security.role.service.RoleService;
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
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

public class RolePopup extends CustomComponent implements Window.CloseListener,Button.ClickListener {
	
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
	final RoleService roleService;
	
	/**
	 * Account Service
	 */
	final AccountService accountService;
	
	/**
	 * Table for User
	 */
    final Table roleTable;
    
	boolean isModalWindowClosable = true;

	public RolePopup(final SpringContextHelper helper,final Table table) {
		this.helper = helper;
		this.roleTable = table;
		this.roleService = (RoleService) this.helper.getBean("roleService");
		this.accountService = (AccountService) this.helper.getBean("accountService");

		 // The component contains a button that opens the window.
        final VerticalLayout layout = new VerticalLayout();
        openbutton = new Button("Add Role");
        openbutton.addClickListener(this);
        layout.addComponent(openbutton);

        setCompositionRoot(layout);
	}

	public void openButtonClick(Button.ClickEvent event) {
        /* Create a new window. */
        final Button roleButton = new Button();
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
        final Label label = new Label("Role Name");
        label.setWidth(100,Unit.PERCENTAGE);
        final HorizontalLayout inputDataLayout = new HorizontalLayout();
        final TextField roleNameTxtFld = new TextField("Role Name");
        roleNameTxtFld.setInputPrompt("Enter role name");
        roleNameTxtFld.setWidth(100,Unit.PERCENTAGE);
        roleNameTxtFld.setColumns(15);
        
        inputDataLayout.setSizeFull();
        inputDataLayout.setSpacing(true);
        inputDataLayout.addComponent(roleNameTxtFld);
        inputDataLayout.setComponentAlignment(roleNameTxtFld, Alignment.TOP_LEFT);
        
        popupMainLayout.setSpacing(true);
        popupMainLayout.addComponent(inputDataLayout);
       
       

        /* adding first name text field */
        final HorizontalLayout firstNameLayout = new HorizontalLayout();
       
        final TextField roledescTxtFld = new TextField("Role Description");
        roledescTxtFld.setInputPrompt("Enter role description name");
        roledescTxtFld.setWidth(100,Unit.PERCENTAGE);
        roledescTxtFld.setColumns(15);
     	
        firstNameLayout.setSizeFull();
        firstNameLayout.setSpacing(true);
        firstNameLayout.addComponent(roledescTxtFld);
        firstNameLayout.setComponentAlignment(roledescTxtFld, Alignment.TOP_LEFT);
     	popupMainLayout.addComponent(firstNameLayout);

     	  final HorizontalLayout lastNameLayout = new HorizontalLayout();
          
          final TextField roleidTxtFld = new TextField("Role id");
          roleidTxtFld.setInputPrompt("Enter role id");
          roleidTxtFld.setWidth(100,Unit.PERCENTAGE);
          roleidTxtFld.setColumns(15);
       	
          lastNameLayout.setSizeFull();
          lastNameLayout.setSpacing(true);
          lastNameLayout.addComponent(roleidTxtFld);
          lastNameLayout.setComponentAlignment(roleidTxtFld, Alignment.TOP_LEFT);
       	popupMainLayout.addComponent(lastNameLayout);
        final HorizontalLayout addButtonLayout = new HorizontalLayout();
        popupMainLayout.addComponent(addButtonLayout);

        addButtonLayout.addComponent(roleButton);
        addButtonLayout.setComponentAlignment(roleButton, Alignment.BOTTOM_RIGHT);
        addButtonLayout.setWidth(100, Unit.PERCENTAGE);
        
        popupWindow.setContent(popupMainLayout);
        popupWindow.setResizable(false);
        /* Allow opening only one window at a time. */
        openbutton.setEnabled(false);

        if(event.getButton().getCaption().equals("Edit"))
        {
        	roleButton.setCaption("Edit");
        	popupWindow.setCaption("Edit Role");
			final int rolename =  (Integer) event.getButton().getData();
			RoleDto roleDto = roleService.findById(rolename);
			//roleNameTxtFld.setValue(roleDto.getName());
			roleidTxtFld.setValue(roleDto.getRoleid().toString());
        	roleButton.addClickListener(new ClickListener() {
    			private static final long serialVersionUID = 1L;
    			public void buttonClick(ClickEvent event) 
    			{
    					handleEditRole(roleNameTxtFld,roledescTxtFld,roleidTxtFld,Integer.parseInt(roleidTxtFld.getValue().toString()));
    			}
            	});
        }
        else
        {
        
        	roleButton.setCaption("Add");
        	popupWindow.setCaption("Add new role");
        	roleButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) 
			{
					handleNewRole(roleNameTxtFld,roledescTxtFld,roleidTxtFld);
			}
        	});
        }
     	/* adding last name text field */
        }

	
	
	private void handleNewRole(final TextField rolename,final TextField roledesc,final TextField roleidTxtFld){
		RoleDto roleDto = new RoleDto();
		try {
			String tempstr=roleidTxtFld.getValue().toString();
			int tempint= Integer.parseInt(tempstr);
			roleDto.setRoleid(tempint);
			roleDto.setRoleName(rolename.getValue().toString());
			roleDto.setRoleDesc(roledesc.getValue().toString());
			final AccountDto accountDto = accountService.findAccountById((Integer)SessionHelper.loadAttribute("accountId"));
			roleDto.setAccount(accountDto);
			roleService.create(roleDto);
		} catch (EntityAlreadyFoundException e) {
			Notification.show("Role already exists", Notification.Type.ERROR_MESSAGE);
		}
		catch (EntityNotCreatedException e) {
			Notification.show("Role not created", Notification.Type.ERROR_MESSAGE);
		}
		Notification.show(roleDto.getName()+" role created succesfully");
		resetTable();
    }
	private void handleEditRole(final TextField rolename,final TextField roledesc,final TextField roleid,final Integer editId){
		final RoleDto roleDto = roleService.findById(editId);
		/*String tempstr=roleid.getValue().toString();
		int tempint= Integer.parseInt(tempstr);
		roleDto.setRoleid(tempint);*/
		//roleid.setValue(roleDto.getRoleid().toString());
		roleDto.setRoleName(rolename.getValue().toString());
		roleDto.setRoleDesc(roledesc.getValue().toString());
		roleService.update(roleDto);
		Notification.show(roleDto.getName()+" role edit succesfully");
		resetTable();
    }
	@SuppressWarnings("rawtypes")
	private void resetTable(){
		final AbstractTableBuilder tableBuilder = new RoleTableBuilder(helper,roleTable);
		//final Collection<RoleDto> roleDto = roleService.findAllRoles();
		final Collection<RoleDto> roleDto = roleService.findRolesByAccountId((Integer)SessionHelper.loadAttribute("accountId"));
		tableBuilder.rebuild((Collection)roleDto);
		UI.getCurrent().removeWindow(popupWindow);
        openbutton.setEnabled(true);
    }
 
	 public void closeButtonClick(Button.ClickEvent event) {
	    	if (!isModalWindowClosable){
	        /* Windows are managed by the application object. */
	    		UI.getCurrent().removeWindow(popupWindow);
	        
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
		this.openButtonClick(event);		
	}

}
