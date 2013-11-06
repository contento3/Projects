package com.contento3.web.user.listner;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.common.dto.Dto;
import com.contento3.security.group.dto.GroupDto;
import com.contento3.security.group.service.GroupService;
import com.contento3.security.role.dto.RoleDto;
import com.contento3.security.role.service.RoleService;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.security.user.service.SaltedHibernateUserService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.EntityListener;
import com.contento3.web.common.helper.GenricEntityPicker;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.contento3.security.permission.dto.*;
import com.contento3.security.permission.service.*;


public class DeleteAssociatedPermissionListener extends EntityListener implements ClickListener {
	/**
	 * 
	 */
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
	 private  AbstractTableBuilder asscoiatedPermissionTable;
	/**
	 * Role Service for role related activities	
	 */
	 RoleService roleService;
	 
	/**
	 * Constructor
	 * @param main
	 * @param helper
	 * @param table
	 * @param groupId
	 * @param tableBuilder
	 */
	public DeleteAssociatedPermissionListener(final Window main,final SpringContextHelper helper,final Integer roleId,final AbstractTableBuilder asscoiatedPermissionTable) {
		
		this.mainwindow = main;
		this.helper = helper;
		this.roleId = roleId;
		this.asscoiatedPermissionTable = asscoiatedPermissionTable;
		this.roleService = (RoleService) helper.getBean("roleService");
	}
	
	/**
	 * Open popup of entity picker
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void buttonClick(Button.ClickEvent event) {
		
		Collection<String> listOfColumns = new ArrayList<String>();
		listOfColumns.add("id");
		GenricEntityPicker PermissionPicker;
		this.vLayout = new VerticalLayout();
		
		Collection<Dto> dtos = (Collection) roleService.findById(roleId).getPermissions();
		
		if (dtos!=null) {
			setCaption("Delete Permission");//extend class method
			PermissionPicker = new GenricEntityPicker(dtos,null, listOfColumns,this.vLayout,this,false);
			PermissionPicker.build();
		}
	}
	
	/**
	 * Delete Selected user from group
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void updateList(){
		
		/* update group member */
		Collection<String> selectedItems =(Collection<String>) this.vLayout.getData();
		if(selectedItems != null){
			Collection<PermissionDto> itemsToDelete = new ArrayList<PermissionDto>();
			PermissionService permissionService =(PermissionService) this.helper.getBean("permissionService");
			RoleDto role = roleService.findById(roleId);
			for(String id : selectedItems ){
				 PermissionDto permission = permissionService.findById(Integer.parseInt(id));
		     	// validation
	
		     	 for(PermissionDto dto : role.getPermissions()){
		     		 if(dto.getId().equals(permission.getId()))
		     			itemsToDelete.add(dto);
		     	 }//end inner for
		     	
			}//end outer for
		
			role.getPermissions().removeAll(itemsToDelete);
 		 	roleService.update(role);	
			asscoiatedPermissionTable.rebuild((Collection) role.getPermissions()); //refresh previous popup table
		}	
	}


}
