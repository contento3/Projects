package com.contento3.web.user.listner;

import org.vaadin.dialogs.ConfirmDialog;

import com.contento3.common.service.Service;
import com.contento3.security.role.dto.RoleDto;
import com.contento3.security.role.service.RoleService;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.security.user.service.SaltedHibernateUserService;
import com.contento3.web.site.listener.EntityDeleteClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.Notification;

public class RoleDeleteClickListener extends EntityDeleteClickListener<RoleDto>{

	final Window window;
	public RoleDeleteClickListener(RoleDto dtoToDelete,
			Service<RoleDto> service,final Window window, Button deleteLink, Table table) {
		super(dtoToDelete, service, deleteLink, table);
		// TODO Auto-generated constructor stub
		this.window = window;
	}

	@SuppressWarnings("serial")
	@Override
	public void buttonClick(ClickEvent event) {
		
		final Object id = getDeleteLink().getData();
		final String name = (String) getTable().getContainerProperty(id,"role").getValue();
			if(getDtoToDelete().getRoleName().equals(name)){

						ConfirmDialog.show(window, "Please Confirm:"," Are you really sure to delete?",
						        "Yes", "Cancel", new ConfirmDialog.Listener() {
	
						            public void onClose(ConfirmDialog dialog) {
						                if (dialog.isConfirmed()) {
						                    // Confirmed to continue
						                	deleteRole(id);
						                	
						                } else {
						                    // User did not confirm
						                    
						                }
						            }
						        });
					
				
			}
			
	}	
	private void deleteRole(Object id){
		try {
			RoleDto dtoToDelete = ((RoleService) getService()).findRoleByName(getDtoToDelete().getName());
			((RoleService) getService()).delete(dtoToDelete);
			getTable().removeItem(id);
			getTable().setPageLength(getTable().getPageLength()-1);
			window.showNotification(getDtoToDelete().getName()+" role deleted succesfully");
		} catch (Exception e) {
			String description= "User " + getDtoToDelete().getName() + " cannot be deleted as users are associated with the role.";
			window.showNotification("Delete Failed",description,Notification.TYPE_WARNING_MESSAGE);
			e.printStackTrace();
		}
    	
	}

}
