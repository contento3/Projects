package com.contento3.web.user.listner;

import org.hibernate.id.IdentityGenerator.GetGeneratedKeysDelegate;
import org.vaadin.dialogs.ConfirmDialog;
import com.contento3.security.group.dto.GroupDto;
import com.contento3.security.group.service.GroupService;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.site.listener.EntityDeleteClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window.Notification;

public class GroupDeleteClickListener extends EntityDeleteClickListener<GroupDto>{

	private static final long serialVersionUID = 3126526402867446357L;

	/**
     * Represents the parent window of the template ui
     */
	final Window window;
	/**
	 * Table for Group
	 */
	private final Table table;
	/**
	 * Group Dto 
	 */
	private final GroupDto groupDto;
	/**
	 * Delete link button
	 */
	private final Button deleteLink;
	
	
	/**
	 * Constructor
	 * @param groupDto
	 * @param helper
	 * @param deleteLink
	 * @param table
	 */
	public GroupDeleteClickListener(final GroupDto groupDto,final GroupService groupService,final Window window,final Button deleteLink,final Table table){
		
		super(groupDto,groupService,deleteLink,table);
		this.groupDto = groupDto;
		this.window=window;

		this.table = table;
		this.deleteLink = deleteLink;
	}

	/**
	 * Handle delete button click event  
	 */
	@Override
	public void buttonClick(ClickEvent event) {
		
		final Object id = deleteLink.getData();
		final String name = (String) table.getContainerProperty(id,"groups").getValue();
			if(groupDto.getGroupName().equals(name)){

						ConfirmDialog.show(window, "Please Confirm:"," Are you really sure to delete?",
						        "Yes", "Cancel", new ConfirmDialog.Listener() {
	
						            public void onClose(ConfirmDialog dialog) {
						                if (dialog.isConfirmed()) {
						                    // Confirmed to continue
						                	deleteGroup((GroupService) getService(),id);
						                	
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
	private void deleteGroup(final GroupService groupService,Object id){
		try {
			groupService.deleteWithException(groupDto);
			table.removeItem(id);
			table.setPageLength(table.getPageLength()-1);
			window.showNotification(groupDto.getGroupName()+" group deleted succesfully");
		} catch (Exception e) {
			String description= "Group " + groupDto.getGroupName() + " cannot be deleted as users are associated with the group.";
			window.showNotification("Delete Failed",description,Notification.TYPE_WARNING_MESSAGE);
			e.printStackTrace();
		}
    	
	}
}