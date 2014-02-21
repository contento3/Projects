package com.contento3.web.user.listner;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.contento3.common.dto.Dto;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.security.permission.dto.PermissionDto;
import com.contento3.security.permission.service.PermissionService;
import com.contento3.security.role.dto.RoleDto;
import com.contento3.security.role.service.RoleService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.EntityListener;
import com.contento3.web.common.helper.GenricEntityPicker;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class AddAssociatedPermissionsListener extends EntityListener implements ClickListener{

	private static final Logger LOGGER = Logger.getLogger(AddAssociatedPermissionsListener.class);

	private static final long serialVersionUID = 1L;
	
	/**
	 * Vertical layout for pop-up
	 */
	private VerticalLayout vLayout;
	
	/**
	 *  Reference to main window
	 */
	Window mainwindow; 
	
	/**
	 * Used to get service beans from spring context.
	 */
	SpringContextHelper helper;
	
	/**
	 * hold group id
	 */
	 Integer roleId=null;
	 
	/**
	 *Abstract TableBuilder  used to create dynamic table 
	 */
	private  AbstractTableBuilder associatedPermissionTable;
		
	/**
	 * Constructor
	 * @param main
	 * @param helper
	 * @param table
	 * @param roleId
	 * @param tableBuilder
	 */
	public AddAssociatedPermissionsListener(final Window main,final SpringContextHelper helper,final Integer roleId,final AbstractTableBuilder associatedPermissionTable) {
		this.mainwindow = main;
		this.helper = helper;
		this.roleId = roleId;
		this.associatedPermissionTable = associatedPermissionTable;
	}

	/**
	 * Open popup of entity picker
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void buttonClick(ClickEvent event) {
		final PermissionService permissionService = (PermissionService) helper.getBean("permissionService");
		Collection<String> listOfColumns = new ArrayList<String>();
		listOfColumns.add("permissions");
		GenricEntityPicker permissionPicker;
		this.vLayout = new VerticalLayout();
		Collection<Dto> dtos = null;
		
		dtos = (Collection) permissionService.findAllPermissions();
		if (dtos!=null) {
			setCaption("Add Permission");//extend class method
			permissionPicker = new GenricEntityPicker(dtos,null, listOfColumns,this.vLayout,this,false);
			permissionPicker.build();
		}
	}
	
	/**
	 * Add selected permission to role
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateList() {	
		/* update group member */
		Collection<String> selectedItems =(Collection<String>)  this.vLayout.getData();
		
		if(selectedItems != null){
			PermissionService permissionService =(PermissionService) this.helper.getBean("permissionService");
			RoleService roleService = (RoleService) this.helper.getBean("roleService");
			RoleDto role = roleService.findById(roleId);
			for(String id : selectedItems ){
				 PermissionDto permission = permissionService.findById(Integer.parseInt(id));
		     	// validation
				 boolean isAddable = true;
		     	 for(PermissionDto dto : role.getPermissions()){
		     		 if(dto.getId().equals(permission.getId()))
		     			 isAddable = false;
		     	 }//end inner for
		     	 
		     	 if(isAddable){
		     		 role.getPermissions().add(permission);
		     	 }//end if
			}//end outer for
		
			try {
				roleService.update(role);
			} catch (EntityAlreadyFoundException e) {
				Notification.show("Role cannot be updated",Notification.Type.TRAY_NOTIFICATION);
			}	
			associatedPermissionTable.rebuild((Collection) role.getPermissions());
		}	
	}
}
