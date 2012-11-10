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
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class AddAssociatedUsersListener extends EntityListener implements ClickListener {

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
	 * Constructor
	 * @param main
	 * @param helper
	 * @param table
	 * @param groupId
	 * @param tableBuilder
	 */
	public AddAssociatedUsersListener(final Window main,final SpringContextHelper helper,final Integer groupId,final AbstractTableBuilder asscoiatedUserTable) {
		this.mainwindow = main;
		this.helper = helper;
		this.groupId = groupId;
		this.asscoiatedUserTable = asscoiatedUserTable;
	}

	/**
	 * Open popup of entity picker
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void buttonClick(ClickEvent event) {
		final SaltedHibernateUserService userService = (SaltedHibernateUserService) helper.getBean("saltedHibernateUserService");
		Integer accountId = (Integer) SessionHelper.loadAttribute(mainwindow,"accountId");
		
		Collection<String> listOfColumns = new ArrayList<String>();
		listOfColumns.add("name");
		GenricEntityPicker userPicker;
		this.vLayout = new VerticalLayout();
		Collection<Dto> dtos = null;
		
		dtos = (Collection) userService.findUsersByAccountId(accountId);
		if (dtos!=null) {
			setCaption("Add user");//extend class method
			userPicker = new GenricEntityPicker(dtos, listOfColumns,this.vLayout,mainwindow,this);
			userPicker.build();
			
		}
	}
	
	/**
	 * Add selected user to group
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateList() {	
		
		/* update group member */
		Collection<String> selectedItems =(Collection<String>) this.vLayout.getData();
		if(selectedItems != null){
			SaltedHibernateUserService userService =(SaltedHibernateUserService) this.helper.getBean("saltedHibernateUserService");
			GroupService groupService = (GroupService) this.helper.getBean("groupService");
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
		     	 }//end if
			}//end outer for
		
			groupService.update(group);	
			asscoiatedUserTable.rebuild((Collection) group.getMembers());
		}	
	}

}
