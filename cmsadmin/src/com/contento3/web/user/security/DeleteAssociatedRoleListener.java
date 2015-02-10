package com.contento3.web.user.security;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.common.dto.Dto;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.security.group.dto.GroupDto;
import com.contento3.security.group.service.GroupService;
import com.contento3.security.role.dto.RoleDto;
import com.contento3.security.role.service.RoleService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.EntityListener;
import com.contento3.web.common.helper.GenricEntityPicker;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class DeleteAssociatedRoleListener extends EntityListener implements ClickListener {


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
	 Integer groupId=null;
	 
	 /**
	  *Abstract TableBuilder  used to create dynamic table 
	  */
	 private  AbstractTableBuilder asscoiatedRoleTable;
	/**
	 * Role Service for role related activities	
	 */
	 RoleService roleService;
	 
	 GroupService groupService;
	 
	/**
	 * Constructor
	 * @param main
	 * @param helper
	 * @param table
	 * @param groupId
	 * @param tableBuilder
	 */
	public DeleteAssociatedRoleListener(final SpringContextHelper helper,final Integer groupId,final AbstractTableBuilder asscoiatedRoleTable) {
		this.helper = helper;
		this.groupId = groupId;
		this.asscoiatedRoleTable = asscoiatedRoleTable;
		this.roleService = (RoleService) helper.getBean("roleService");
	}
	
	/**
	 * Open popup of entity picker
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void buttonClick(Button.ClickEvent event) {
		
		Collection<String> listOfColumns = new ArrayList<String>();
		listOfColumns.add("name");
		GenricEntityPicker PermissionPicker;
		this.vLayout = new VerticalLayout();
		this.groupService = (GroupService) helper.getBean("groupService");
		Collection<Dto> dtos = (Collection) groupService.findById(groupId).getRoles();
		
		if (dtos!=null) {
			setCaption("Delete Role");//extend class method
			PermissionPicker = new GenricEntityPicker(dtos,null, listOfColumns,this.vLayout,this,false);
			PermissionPicker.build(null);
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
			final Collection<RoleDto> itemsToDelete = new ArrayList<RoleDto>();
			final GroupDto group = groupService.findById(groupId);
			
			for(String id : selectedItems ){
				 RoleDto role = roleService.findById(Integer.parseInt(id));
		     	// validation
	
		     	 for(RoleDto dto : group.getRoles()){
		     		 if(role.getId().equals(dto.getId()))
		     			itemsToDelete.add(dto);
		     	 }//end inner for
		     	
			}//end outer for
		
			group.getRoles().removeAll(itemsToDelete);
 		 	try {
				groupService.update(group);
			} catch (final EntityAlreadyFoundException e) {
				Notification.show("Group with name "+group.getGroupName()+" already exists.");
			}	
			asscoiatedRoleTable.rebuild((Collection) group.getRoles()); //refresh previous popup table
		}	
	}



}
