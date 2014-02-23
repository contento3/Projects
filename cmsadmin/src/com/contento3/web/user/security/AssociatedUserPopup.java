package com.contento3.web.user.security;

import java.util.Collection;

import com.contento3.common.dto.Dto;
import com.contento3.security.group.service.GroupService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.user.listner.AddAssociatedUsersListener;
import com.contento3.web.user.listner.DeleteAssociatedUsersListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

public class AssociatedUserPopup extends CustomComponent implements Window.CloseListener,Button.ClickListener {

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
	
	final private VerticalLayout layout;
	/**
	 * Constructor
	 * @param main
	 * @param helper
	 * @param table
	 */
	public AssociatedUserPopup(final SpringContextHelper helper,final Table table) {
		this.helper = helper;
		this.groupService = (GroupService) this.helper.getBean("groupService");
		this.userTable = new Table();
		
		 // The component contains a button that opens the window.
        layout = new VerticalLayout();
        openbutton = new Button("Associated user");
        openbutton.addClickListener(this);
        
        layout.addComponent(openbutton);
        setCompositionRoot(layout);
	}
	
	/** 
	 * Button click listener
	 */
	  public void openButtonClick(final Button.ClickEvent event) {
	        /* Create a new window. */
		  	popupWindow = new Window();
		  	this.tableBuilder = new AssociatedUserTableBuilder(popupWindow,helper,userTable);
		  	
		  	Integer groupId = Integer.parseInt(event.getButton().getData().toString());
		  	
	        final Button addUserButton = new Button("Add");
	        addUserButton.addClickListener(new AddAssociatedUsersListener(mainwindow,helper, groupId,tableBuilder));
	        final Button deleteUserButton = new Button("Delete");
	    	deleteUserButton.addClickListener(new DeleteAssociatedUsersListener(mainwindow,helper, groupId,tableBuilder));
			popupWindow.setPositionX(200);
	    	popupWindow.setPositionY(100);

	    	popupWindow.setHeight(40,Unit.PERCENTAGE);
	    	popupWindow.setWidth(37,Unit.PERCENTAGE);
	       
	    	/* Add the window inside the main window. */
	     //   layout.addComponent(popupWindow);
	    	UI.getCurrent().addWindow(popupWindow);
	        /* Listen for close events for the window. */
	        popupWindow.addCloseListener(this);
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
	        popupMainLayout.setMargin(true);
	        popupWindow.setContent(popupMainLayout);
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
	  private Table renderAssociatedUserTable(final Integer groupId){
		  userTable.setPageLength(25);
		  Collection<Dto> userDto = (Collection)groupService.findById(groupId).getMembers();
		  tableBuilder.build(userDto);
		return userTable;
	  }
	  
	  /**
	   *  Handle Close button click and close the window.
	   */
	    public void closeButtonClick(final Button.ClickEvent event) {
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
		public void windowClose(final CloseEvent e) {
			  /* Return to initial state. */
	        openbutton.setEnabled(true);
		}

		@Override
		public void buttonClick(final Button.ClickEvent event) {
			this.openButtonClick(event);
		}

}
