package com.contento3.web.user.security;

import java.util.Collection;

import com.contento3.security.group.service.GroupService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

public class AssociatedRolePopup extends CustomComponent implements Window.CloseListener,Button.ClickListener
{

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
	 * Table for role
	 */
    Table roleTable;
	
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
	public AssociatedRolePopup(final SpringContextHelper helper,final Table table) {
		this.helper = helper;
		this.groupService = (GroupService) this.helper.getBean("groupService");
		this.roleTable = new Table();
		
		 // The component contains a button that opens the window.
        layout = new VerticalLayout();
        openbutton = new Button("Associated Role");
        openbutton.addClickListener(this);
        
        layout.addComponent(openbutton);
        setCompositionRoot(layout);
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		popupWindow = new Window();
	  	this.tableBuilder= new AssociatedRoleTableBuilder(helper,roleTable);
	  	
	  	//List<RoleDto> roles = event.getButton().getData();
	  	Integer groupId = Integer.parseInt(event.getButton().getData().toString());

        final Button addRoleButton = new Button("Add");
        addRoleButton.addClickListener(new AddAssociatedRoleListener(helper, groupId,tableBuilder));
        final Button deleteRoleButton = new Button("Delete");
        deleteRoleButton.addClickListener(new DeleteAssociatedRoleListener(helper, groupId,tableBuilder));
		popupWindow.setPositionX(200);
    	popupWindow.setPositionY(100);

    	popupWindow.setHeight(40,Unit.PERCENTAGE);
    	popupWindow.setWidth(37,Unit.PERCENTAGE);
       
    	/* Add the window inside the main window. */
        UI.getCurrent().addWindow(popupWindow);
        
        /* Listen for close events for the window. */
        popupWindow.addCloseListener(this);
        popupWindow.setModal(true);
        popupWindow.setCaption("Associated Role");
        final VerticalLayout popupMainLayout = new VerticalLayout();
        popupMainLayout.setSpacing(true);
        final HorizontalLayout addButtonLayout = new HorizontalLayout();
        addButtonLayout.setSpacing(true);
        popupMainLayout.addComponent(addButtonLayout);
        addButtonLayout.addComponent(addRoleButton);
        addButtonLayout.addComponent(deleteRoleButton);
        
        /* Adding user table to pop-up */
        popupMainLayout.addComponent(renderAssociatedRoleTable(groupId));
        popupMainLayout.setMargin(true);
        popupWindow.setContent(popupMainLayout);
        popupWindow.setResizable(false);
        
        /* Allow opening only one window at a time. */
        openbutton.setEnabled(false);

	}
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	  private Table renderAssociatedRoleTable(final Integer id){
		 roleTable.setPageLength(25);
		 tableBuilder.build((Collection)groupService.findById(id).getRoles());
		 return roleTable;
	  }


	@Override
	public void windowClose(final CloseEvent e) {
		// TODO Auto-generated method stub
		if (!isModalWindowClosable){
	        /* Windows are managed by the application object. */
	        layout.removeComponent(popupWindow);
	        
	        /* Return to initial state. */
	        openbutton.setEnabled(true);
	    	}
	}

}
