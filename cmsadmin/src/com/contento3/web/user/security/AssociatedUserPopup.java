package com.contento3.web.user.security;

import java.util.Collection;
import com.contento3.security.group.service.GroupService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

public class AssociatedUserPopup extends CustomComponent implements Window.CloseListener {

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
	
	/**
	 * Group service used for group related operations
	 */
	GroupService groupService;
	
	/**
	 * Table for user
	 */
    Table userTable;
	
	boolean isModalWindowClosable = true;
	
	/**
	 *Abstract TableBuilder  used to create dynamic table 
	 */
	private  AbstractTableBuilder tableBuilder;
	
	/**
	 * Constructor
	 * @param main
	 * @param helper
	 * @param table
	 */
	public AssociatedUserPopup(final Window main,final SpringContextHelper helper,final Table table) {
		this.mainwindow = main;
		this.helper = helper;
		this.groupService = (GroupService) this.helper.getBean("groupService");
		this.userTable = new Table();
		
		
		 // The component contains a button that opens the window.
        final VerticalLayout layout = new VerticalLayout();
        openbutton = new Button("Associated user", this, "openButtonClick");
        layout.addComponent(openbutton);
        setCompositionRoot(layout);
		
	}
	
		/** 
		 * Button click listener
		 */
	  public void openButtonClick(Button.ClickEvent event) {
	        /* Create a new window. */
		  	popupWindow = new Window();
		  	this.tableBuilder = new AssociatedUserTableBuilder(popupWindow,helper,userTable);
		  	
		  	Integer groupId = Integer.parseInt(event.getButton().getData().toString());
		  	
	        final Button addUserButton = new Button("Add",new AddAssociatedUsers(mainwindow, helper, userTable,groupId,tableBuilder), "openButtonClick");
	        final Button deleteUserButton = new Button("Delete",new DeleteAssociatedUsers(mainwindow, helper, userTable,groupId,tableBuilder), "openButtonClick");
	    	
			popupWindow.setPositionX(200);
	    	popupWindow.setPositionY(100);

	    	popupWindow.setHeight(40,Sizeable.UNITS_PERCENTAGE);
	    	popupWindow.setWidth(37,Sizeable.UNITS_PERCENTAGE);
	       
	    	/* Add the window inside the main window. */
	        mainwindow.addWindow(popupWindow);
	        
	        /* Listen for close events for the window. */
	        popupWindow.addListener(this);
	        popupWindow.setModal(true);
	        popupWindow.setCaption("Associated users");
	        final VerticalLayout popupMainLayout = new VerticalLayout();
	        popupMainLayout.setSpacing(true);
	        final HorizontalLayout addButtonLayout = new HorizontalLayout();
	        addButtonLayout.setSpacing(true);
	        popupMainLayout.addComponent(addButtonLayout);
	        addButtonLayout.addComponent(addUserButton);
	        addButtonLayout.addComponent(deleteUserButton);
	        
	        /* Adding user table to pop-up */
	        popupMainLayout.addComponent(renderAssociatedUserTable(groupId));
	        popupWindow.addComponent(popupMainLayout);
	        popupWindow.setResizable(false);
	        /* Allow opening only one window at a time. */
	        openbutton.setEnabled(false);
	    }
	
	  /**
	   * Render AssociatedUserTable
	   * @param groupId
	   * @return
	   */
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	  private Table  renderAssociatedUserTable(Integer groupId){
		  userTable.setPageLength(25);
		  tableBuilder.build((Collection)groupService.findById(groupId).getMembers());
		return userTable;
		  
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
