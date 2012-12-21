package com.contento3.web.user.listner;


import java.util.ArrayList;
import java.util.Collection;
import com.contento3.common.dto.Dto;
import com.contento3.security.group.dto.GroupDto;
import com.contento3.security.group.service.GroupService;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.security.user.service.SaltedHibernateUserService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.EntityListener;
import com.contento3.web.common.helper.GenricEntityPicker;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class DeleteAssociatedUsersListener extends EntityListener implements ClickListener  {

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
	 Integer groupId=null;
	 
	 /**
	  *Abstract TableBuilder  used to create dynamic table 
	  */
	 private  AbstractTableBuilder asscoiatedUserTable;
	/**
	 * Group Service for group related activities	
	 */
	 GroupService groupService;
	 
	/**
	 * Constructor
	 * @param main
	 * @param helper
	 * @param table
	 * @param groupId
	 * @param tableBuilder
	 */
	public DeleteAssociatedUsersListener(final Window main,final SpringContextHelper helper,final Integer groupId,final AbstractTableBuilder asscoiatedUserTable) {
		
		this.mainwindow = main;
		this.helper = helper;
		this.groupId = groupId;
		this.asscoiatedUserTable = asscoiatedUserTable;
		this.groupService = (GroupService) helper.getBean("groupService");
	}
	
	/**
	 * Open popup of entity picker
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void buttonClick(ClickEvent event) {
		
		Collection<String> listOfColumns = new ArrayList<String>();
		listOfColumns.add("name");
		GenricEntityPicker userPicker;
		this.vLayout = new VerticalLayout();
		
		Collection<Dto> dtos = (Collection) groupService.findById(groupId).getMembers();
		if (dtos!=null) {
			setCaption("Delete user");//extend class method
			userPicker = new GenricEntityPicker(dtos, listOfColumns,this.vLayout,mainwindow,this,false);
			userPicker.build();
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
			Collection<SaltedHibernateUserDto> itemsToDelete = new ArrayList<SaltedHibernateUserDto>();
			SaltedHibernateUserService userService =(SaltedHibernateUserService) this.helper.getBean("saltedHibernateUserService");
			GroupDto group = groupService.findById(groupId);
			for(String name : selectedItems ){
				 SaltedHibernateUserDto user = userService.findUserByName(name);
		     	// validation
	
		     	 for(SaltedHibernateUserDto dto : group.getMembers()){
		     		 if(dto.getName().equals(user.getName()))
		     			itemsToDelete.add(dto);
		     	 }//end inner for
		     	
			}//end outer for
		
			group.getMembers().removeAll(itemsToDelete);
 		 	groupService.update(group);	
			asscoiatedUserTable.rebuild((Collection) group.getMembers()); //refresh previous popup table
		}	
	}

}
