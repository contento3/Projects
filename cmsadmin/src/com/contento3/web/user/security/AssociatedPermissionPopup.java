package com.contento3.web.user.security;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;

import com.contento3.security.permission.dto.PermissionDto;
import com.contento3.security.role.service.RoleService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.user.listner.AddAssociatedPermissionsListener;
import com.contento3.web.user.listner.DeleteAssociatedPermissionListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

public class AssociatedPermissionPopup extends CustomComponent implements Window.CloseListener,Button.ClickListener{

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
	RoleService roleService;
	
	/**
	 * Table for user
	 */
    Table permissionTable;
	
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
	public AssociatedPermissionPopup(final SpringContextHelper helper,final Table table) {
		this.helper = helper;
		this.roleService = (RoleService) this.helper.getBean("roleService");
		this.permissionTable = new Table();
		
		 // The component contains a button that opens the window.
        layout = new VerticalLayout();
        openbutton = new Button("Associated Permission");
        openbutton.addClickListener(this);
        
        layout.addComponent(openbutton);
        setCompositionRoot(layout);
	}
	
	 public void openButtonClick(final Button.ClickEvent event) {
		// TODO Auto-generated method stub
		 /* Create a new window. */
	  	popupWindow = new Window();
	  	this.tableBuilder = new AssociatedPermissionTableBuilder(popupWindow,helper,permissionTable);
	  	
	  	int roleId = (Integer) event.getButton().getData();

        final HorizontalLayout addButtonLayout = new HorizontalLayout();

        if (SecurityUtils.getSubject().isPermitted("ROLE:ASSOCIATE_PERMISSION")) {
	        final Button addPermissionButton = new Button("Add");
	        addPermissionButton.addClickListener(new AddAssociatedPermissionsListener(mainwindow,helper, roleId,tableBuilder));
	        addButtonLayout.addComponent(addPermissionButton);
        }
        
        if (SecurityUtils.getSubject().isPermitted("ROLE:DISASSOCIATE_PERMISSION")) {
	        final Button deletePermissionButton = new Button("Delete");
	    	deletePermissionButton.addClickListener(new DeleteAssociatedPermissionListener(mainwindow,helper, roleId,tableBuilder));
	        addButtonLayout.addComponent(deletePermissionButton);
        }

    	popupWindow.setPositionX(200);
    	popupWindow.setPositionY(100);

    	popupWindow.setHeight(40,Unit.PERCENTAGE);
    	popupWindow.setWidth(37,Unit.PERCENTAGE);
       
    	/* Add the window inside the main window. */
        UI.getCurrent().addWindow(popupWindow);
        
        /* Listen for close events for the window. */
        popupWindow.addCloseListener(this);
        popupWindow.setModal(true);
        popupWindow.setCaption("Associated Permissions");
        final VerticalLayout popupMainLayout = new VerticalLayout();
        popupMainLayout.setSpacing(true);
        addButtonLayout.setSpacing(true);
        popupMainLayout.addComponent(addButtonLayout);
        
        /* Adding user table to pop-up */
        popupMainLayout.addComponent(renderAssociatedPermissionTable(roleId));
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
	  private Table renderAssociatedPermissionTable(final Integer roleId){
		  permissionTable.setPageLength(25);
		  
		  try{
			  tableBuilder.build((Collection)roleService.findById(roleId).getPermissions());
		  }
		  catch (final AuthorizationException ae){
			  tableBuilder.build((Collection) new ArrayList<PermissionDto>());
		  }
		return permissionTable;
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
	@Override
	public void windowClose(CloseEvent e) {
		// TODO Auto-generated method stub
		  /* Return to initial state. */
        openbutton.setEnabled(true);
	}
	
	@Override
	public void buttonClick(final Button.ClickEvent event) {
		this.openButtonClick(event);
	}

	
}
