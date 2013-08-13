package com.contento3.web.user.listner;

import org.vaadin.dialogs.ConfirmDialog;

import com.contento3.common.service.Service;
import com.contento3.security.role.dto.RoleDto;
import com.contento3.security.role.service.RoleService;
import com.contento3.web.site.listener.EntityDeleteClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;

public class RoleDeleteClickListener extends EntityDeleteClickListener<RoleDto>{

	private static final long serialVersionUID = 1L;

	public RoleDeleteClickListener(RoleDto dtoToDelete,
			Service<RoleDto> service, Button deleteLink, Table table) {
		super(dtoToDelete, service, deleteLink, table);
	}

	@SuppressWarnings("serial")
	@Override
	public void buttonClick(ClickEvent event) {
		
		final Object id = getDeleteLink().getData();
		final String name = (String) getTable().getContainerProperty(id,"role").getValue();
			if(getDtoToDelete().getRoleName().equals(name)){

						ConfirmDialog.show(UI.getCurrent(), "Please Confirm:"," Are you really sure to delete?",
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
			Notification.show(getDtoToDelete().getName()+" role deleted succesfully");
		} catch (Exception e) {
			String description= "User " + getDtoToDelete().getName() + " cannot be deleted as users are associated with the role.";
			Notification.show("Delete Failed",description,Notification.Type.WARNING_MESSAGE);
			e.printStackTrace();
		}
    	
	}

}
