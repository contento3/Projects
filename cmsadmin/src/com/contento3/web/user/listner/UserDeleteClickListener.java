package com.contento3.web.user.listner;

import org.vaadin.dialogs.ConfirmDialog;

import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.security.user.service.SaltedHibernateUserService;
import com.contento3.web.site.listener.EntityDeleteClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;

public class UserDeleteClickListener extends EntityDeleteClickListener<SaltedHibernateUserDto> {
	private static final long serialVersionUID = 3126526402867446357L;

	/**
	 * Constructor
	 * @param userDto
	 * @param helper
	 * @param deleteLink
	 * @param table
	 */
	public UserDeleteClickListener(final SaltedHibernateUserDto userDto,final SaltedHibernateUserService userService,final Button deleteLink,final Table table){
		super(userDto,userService,deleteLink,table);
	}

	/**
	 * Handle delete button click event  
	 */
	@SuppressWarnings("serial")
	@Override
	public void buttonClick(ClickEvent event) {
		
		final Object id = getDeleteLink().getData();
		final String name = (String) getTable().getContainerProperty(id,"users").getValue();
			if(getDtoToDelete().getName().equals(name)){

						ConfirmDialog.show(UI.getCurrent(),"Please Confirm:"," Are you really sure to delete?",
						        "Yes", "Cancel", new ConfirmDialog.Listener() {
	
						            public void onClose(ConfirmDialog dialog) {
						                if (dialog.isConfirmed()) {
						                    // Confirmed to continue
						                	deleteUser(id);
						                	
						                } else {
						                    // User did not confirm
						                    
						                }
						            }
						        });
			}
	}	
	
	/**
	 * delete group from db and update table
	 * @param groupService
	 * @param id
	 */
	private void deleteUser(final Object id){
		try {
			SaltedHibernateUserDto dtoToDelete = ((SaltedHibernateUserService) getService()).findUserById(getDtoToDelete().getId());
			((SaltedHibernateUserService) getService()).delete(dtoToDelete);
			getTable().removeItem(id);
			getTable().setPageLength(getTable().getPageLength()-1);
			Notification.show(getDtoToDelete().getName()+" user deleted succesfully");
		} catch (EntityCannotBeDeletedException e) {
			String description= "User " + getDtoToDelete().getName() + " cannot be deleted as users are associated with the group.";
			Notification.show("Delete Failed",description,Notification.Type.WARNING_MESSAGE);
		}
    	
	}
}
