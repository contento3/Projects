package com.contento3.web.user.security;


import java.util.ArrayList;
import java.util.Collection;
import com.contento3.security.group.dto.GroupDto;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.security.user.service.SaltedHibernateUserService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

public class DeleteAssociatedUsers extends AddDeleteUserToGroupPopup {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 * @param main
	 * @param helper
	 * @param table
	 * @param groupId
	 * @param tableBuilder
	 */
	public DeleteAssociatedUsers(Window main, SpringContextHelper helper,
			Table table, Integer groupId, AbstractTableBuilder tableBuilder) {
		super(main, helper, table, groupId, tableBuilder);
		
	}

	/**
	 * Window close event
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void windowClose(CloseEvent e) {
		
		/* update group member */
		Collection<String> selectedItems =(Collection<String>) this.popupMainLayout.getData();
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
			asscoiatedUserTable.rebuild((Collection) group.getMembers());
		}
		/* Return to initial state. */
		openbutton.setEnabled(true);
		
	}

}