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
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class AddAssociatedRoleListener extends EntityListener implements ClickListener {

	/**
	 * Used to get service beans from spring context.
	 */
	SpringContextHelper helper;

	AbstractTableBuilder tableBuilder;
	
	Integer groupId;
	
	VerticalLayout vLayout;
	
	public AddAssociatedRoleListener(SpringContextHelper helper, Integer groupId,
			AbstractTableBuilder tableBuilder) {
		this.helper = helper;
		this.tableBuilder = tableBuilder;
		this.groupId = groupId;
	}

	
	/**
	 * Open popup of entity picker
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void buttonClick(final ClickEvent event) {
		final RoleService roleService = (RoleService) helper.getBean("roleService");
		Collection<String> listOfColumns = new ArrayList<String>();
		listOfColumns.add("roles");
		GenricEntityPicker permissionPicker;
		this.vLayout = new VerticalLayout();
		Collection<Dto> dtos = null;
		
		dtos = (Collection) roleService.findRolesByAccountId((Integer)SessionHelper.loadAttribute("accountId"));
		
		if (dtos!=null) {
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
			RoleService roleService =(RoleService) this.helper.getBean("roleService");
			GroupService groupService = (GroupService) this.helper.getBean("groupService");
			GroupDto group = groupService.findById(groupId);
			
			for(String id : selectedItems ){
				 RoleDto role = roleService.findById(Integer.parseInt(id));
		     	
				 // validation
				 boolean isAddable = true;
		     	 for(RoleDto dto : group.getRoles()){
		     		 if(dto.getId().equals(role.getId()))
		     			 isAddable = false;
		     	 }//end inner for
		     	 
		     	 if(isAddable){
		     		 group.getRoles().add(role);
		     	 }//end if
			}//end outer for
		
			try {
				groupService.update(group);
			} catch (final EntityAlreadyFoundException e) {
				Notification.show("Group with name "+group.getGroupName()+" already exists.");
			}	
			tableBuilder.rebuild((Collection)group.getRoles());
		}	
	}

}
