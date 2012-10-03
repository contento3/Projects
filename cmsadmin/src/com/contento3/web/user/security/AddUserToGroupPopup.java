package com.contento3.web.user.security;

import java.util.ArrayList;
import java.util.Collection;
import com.contento3.common.dto.Dto;
import com.contento3.security.group.dto.GroupDto;
import com.contento3.security.group.service.GroupService;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.security.user.service.SaltedHibernateUserService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.GenricEntityPicker;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
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
	 * Vertical layout to add components
	 */
	final VerticalLayout popupMainLayout = new VerticalLayout();
	 
	/** Constructor
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

	    	popupWindow.setHeight(40,Sizeable.UNITS_PERCENTAGE);
	    	popupWindow.setWidth(40,Sizeable.UNITS_PERCENTAGE);
	       
	    	/* Add the window inside the main window. */
	        mainwindow.addWindow(popupWindow);
	        
	        /* Listen for close events for the window. */
	        popupWindow.addListener(this);
	        popupWindow.setModal(true);
	        popupWindow.setCaption("Add user");
	        
	        
	        popupMainLayout.setSpacing(true);
	 
	        /* Adding user table to pop-up */
	        renderUserTable(popupMainLayout);
	        
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
	private void renderUserTable(final VerticalLayout popupMainLayout) {

		final SaltedHibernateUserService userService = (SaltedHibernateUserService) helper.getBean("saltedHibernateUserService");
		Integer accountId = (Integer) SessionHelper.loadAttribute(mainwindow,"accountId");
		
		Collection<Dto> dtos = (Collection) userService.findUsersByAccountId(accountId);
		Collection<String> listOfColumns = new ArrayList<String>();
		listOfColumns.add("name");
		GenricEntityPicker userPicker = new GenricEntityPicker(dtos,listOfColumns,popupMainLayout);
		userPicker.build();

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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void windowClose(CloseEvent e) {
		
		/* update group member */
		Collection<String> selectedItems =(Collection<String>) this.popupMainLayout.getData();
		SaltedHibernateUserService userService =(SaltedHibernateUserService) this.helper.getBean("saltedHibernateUserService");
		GroupDto group = groupService.findById(groupId);
		for(String name : selectedItems ){
			 SaltedHibernateUserDto user = userService.findUserByName(name);
	     	// validation
			 boolean isAddable = true;
	     	 for(SaltedHibernateUserDto dto : group.getMembers()){
	     		 if(dto.getName().equals(user.getName()))
	     			 isAddable = false;
	     	 }//end inner for
	     	 
	     	 if(isAddable){
	     		 group.getMembers().add(user);
	     	 }//end i
		}//end outer for
	
		groupService.update(group);	
		asscoiatedUserTable.rebuild((Collection) group.getMembers());
		
		/* Return to initial state. */
		openbutton.setEnabled(true);
	}

}
