package com.contento3.web.user.security;


import java.util.Collection;
import com.contento3.common.dto.Dto;
import com.contento3.security.group.dto.GroupDto;
import com.contento3.security.group.service.GroupService;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.security.user.service.SaltedHibernateUserService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.GenricEntityPicker;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class UserPicker extends GenricEntityPicker {

	/**
	 *  Reference to main window
	 */
	final Window mainwindow; 
	
	/**
	 * The window to be opened
	 */
	final Window popupWindow; 
	
	/**
	 * Used to get service beans from spring context.
	 */
	final SpringContextHelper helper;
	
	/**
	 * Group service used for group related operations
	 */
	final GroupService groupService;
	
	/**
	 * To hold Group 
	 */
	final GroupDto group;
	
	/**
	 * SaltedHibernateUser service used for user related operations
	 */
	final SaltedHibernateUserService userService; 
	
	/**
	 *Abstract TableBuilder  used to create dynamic table 
	 */
	final AbstractTableBuilder asscoiatedUserTable;
	
	/**
	 * Constructor
	 * @param helper
	 * @param mainWindow
	 * @param popupWindow
	 * @param table
	 * @param button
	 * @param dtos
	 * @param dto
	 * @param asscoiatedUserTable
	 */
	public UserPicker(SpringContextHelper helper,final Window mainWindow,final Window popupWindow,final Table table,final Button button,final Collection<Dto> dtos,final Dto dto,final AbstractTableBuilder asscoiatedUserTable) {
		super(table, button, dtos);
		this.group = (GroupDto) dto;
		this.helper = helper;
		this.mainwindow = mainWindow;
		this.popupWindow = popupWindow;
		this.userService = (SaltedHibernateUserService) helper.getBean("saltedHibernateUserService");
		this.groupService = (GroupService) helper.getBean("groupService");
		this.asscoiatedUserTable = asscoiatedUserTable;
	}

	/**
	 * Add button click listener
	 */
	@Override
	public void buttonlistner(final Table table,final Button button) {

		button.addListener(new ClickListener() {
			
			private static final long serialVersionUID = 1L;

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public void buttonClick(ClickEvent event) {
			
				
                for (Object id : table.getItemIds()) {
                	boolean isAddable = true;
                     // Get the check-box of this item (row)
                     CheckBox checkBox = (CheckBox) table
                             .getContainerProperty(id, "select")
                             .getValue();
                   
                     if (checkBox.booleanValue()) {
                    	 
                    	 SaltedHibernateUserDto user = userService.findUserByName(id.toString());
                    	// validation
                    	 for(SaltedHibernateUserDto dto : group.getMembers()){
                    		 if(dto.getName().equals(user.getName()))
                    			 isAddable = false;
                    	 }//end for
                    	 
                    	 if(isAddable){
                    		 group.getMembers().add(user);
                    	 }//end if
                     }//end if
                     
                 }//end for
               
                groupService.update(group);
				mainwindow.removeWindow(popupWindow);
				//refresh previous pop-up table
				asscoiatedUserTable.rebuild((Collection) group.getMembers());
			}
		});
		
	}

}
