package com.contento3.web.user.security;

import java.util.Collection;
import com.contento3.common.dto.Dto;
import com.contento3.security.group.service.GroupService;
import com.contento3.security.user.service.SaltedHibernateUserService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.GenricEntityPicker;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

public class AddUserToGroupPopup extends CustomComponent implements Window.CloseListener{

	/**
	 * 
	 */
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
	
	boolean isModalWindowClosable = true;
	
	/**
	 * Group service used for group related operations
	 */
	final GroupService groupService;
	
	/**
	 *Abstract TableBuilder  used to create dynamic table 
	 */
	final AbstractTableBuilder asscoiatedUserTable;
	
	/**
	 * hold group id
	 */
	final Integer groupId;
	/**
	 * Constructor
	 * @param main
	 * @param helper
	 * @param table
	 * @param groupId
	 * @param tableBuilder
	 */
	public AddUserToGroupPopup(final Window main,final SpringContextHelper helper,final Table table,final Integer groupId,final AbstractTableBuilder tableBuilder) {
		this.mainwindow = main;
		this.helper = helper;
		this.groupId = groupId;
		this.groupService = (GroupService) helper.getBean("groupService");
		this.asscoiatedUserTable = tableBuilder;
		
		 // The component contains a button that opens the window.
        final VerticalLayout layout = new VerticalLayout();
        openbutton = new Button("Add suser", this, "openButtonClick");
        layout.addComponent(openbutton);

        setCompositionRoot(layout);
	}
	
	/**
	 * open Butto listener
	 * @param event
	 */
	 public void openButtonClick(Button.ClickEvent event) {
	        /* Create a new window. */
	       
			popupWindow = new Window();
	    	
			popupWindow.setPositionX(200);
	    	popupWindow.setPositionY(100);

	    	popupWindow.setHeight(35,Sizeable.UNITS_PERCENTAGE);
	    	popupWindow.setWidth(30,Sizeable.UNITS_PERCENTAGE);
	       
	    	/* Add the window inside the main window. */
	        mainwindow.addWindow(popupWindow);
	        
	        /* Listen for close events for the window. */
	        popupWindow.addListener(this);
	        popupWindow.setModal(true);
	        popupWindow.setCaption("Add user");
	        final VerticalLayout popupMainLayout = new VerticalLayout();
	        popupMainLayout.setSpacing(true);
	      
	        final HorizontalLayout addButtonLayout = new HorizontalLayout();
	        
	        final Button addUserButton = new Button();
	        addUserButton.setCaption("Add user");
	        /* Adding user table to pop-up */
	        popupMainLayout.addComponent(renderUserTable(addUserButton));
	        popupMainLayout.addComponent(addButtonLayout);
	        addButtonLayout.addComponent(addUserButton);
	        addButtonLayout.setComponentAlignment(addUserButton, Alignment.BOTTOM_RIGHT);
	        addButtonLayout.setWidth(100, Sizeable.UNITS_PERCENTAGE);
	        
	       
	        popupWindow.addComponent(popupMainLayout);
	        popupWindow.setResizable(false);
	        /* Allow opening only one window at a time. */
	        openbutton.setEnabled(false);
	    }
	
	/**
	 * Render user table
	 * @param addUserButton
	 * @return
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	private Table renderUserTable(final Button addUserButton) {
		Table userTable = new Table();
		userTable.setData("add users");
		userTable.setColumnWidth("select", 40);
		userTable.setPageLength(5);
		
		final SaltedHibernateUserService userService = (SaltedHibernateUserService) helper.getBean("saltedHibernateUserService");
		Integer accountId = (Integer) SessionHelper.loadAttribute(mainwindow,"accountId");
		Dto dto = groupService.findById(groupId);
		GenricEntityPicker userPicker = new UserPicker(helper,mainwindow,popupWindow, userTable, addUserButton, (Collection) userService.findUsersByAccountId(accountId),dto,asscoiatedUserTable);
		userPicker.build();
		
		return userTable;
	}


	/**
	 * Handle Close button click and close the window.
	 */
	public void closeButtonClick(Button.ClickEvent event) {
		if (!isModalWindowClosable) {
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
