package com.contento3.web.site.listener;

import org.vaadin.dialogs.ConfirmDialog;

import com.amazonaws.services.simpleemail.model.NotificationType;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.common.service.Service;
import com.contento3.dam.document.service.DocumentService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;


/**
 * Generic entity class to delete the entity from the table listing.
 * @author hamakhaa
 *
 * @param <T>
 */
public class EntityDeleteClickListener<T>  implements ClickListener {

	private static final long serialVersionUID = 3126526402867446357L;

	/**
	 * Dto to be deleted
	 */
	private final T dtoToDelete;
	
	/**
	 * @link {com.vaadin.ui.Button} clicked to be deleted.
	 */
	private final Button deleteLink;
	
	/**
	 * Table that displays the entity listing.This is 
	 * used here to delete the record from the displaying table.
	 */
	
	private final Table table;
	
	public final T getDtoToDelete() {
		return dtoToDelete;
	}

	public final Button getDeleteLink() {
		return deleteLink;
	}

	public final Table getTable() {
		return table;
	}

	/**
	 * Service used to delete the entity from db.
	 */
	private final Service<T> service;

	private String confirmationMessage;
	
	public final Service<T> getService() {
		return service;
	}

	public EntityDeleteClickListener(final T dtoToDelete,final Service<T> service,final Button deleteLink,final Table table,final String message){
		this.dtoToDelete = dtoToDelete;
		this.table = table;
		this.deleteLink = deleteLink;
		this.service = service;
		this.confirmationMessage = message;
	}

	public EntityDeleteClickListener(final T dtoToDelete,final Service<T> service,final Button deleteLink,final Table table){
		this(dtoToDelete,service,deleteLink,table,"Are you sure you want to delete?");
	}

	@Override
	public void buttonClick(ClickEvent event) {
			
		deleteEntity(dtoToDelete);
		
	}
	
	/**
	 * Deletes the dto from the db and also the table.
	 * @param dtoToDelete
	 */
	protected void deleteEntity(final T dtoToDelete) {
		final Object id = deleteLink.getData();
		ConfirmDialog.show(UI.getCurrent(), confirmationMessage,
		        new ConfirmDialog.Listener() {
					private static final long serialVersionUID = 1L;
					public void onClose(ConfirmDialog dialog) 
					{
		                if (dialog.isConfirmed()) {
		                	try {
		                		service.delete(dtoToDelete);
		                		table.removeItem(id);
		                		table.setPageLength(table.getPageLength()-1);			
		                		Notification.show("Deleted", "Successfully deleted",Notification.Type.TRAY_NOTIFICATION);
		                	} 
		                	catch (final EntityCannotBeDeletedException e)
		                	{						
		                		Notification.show("Unable to delete entity",Notification.Type.TRAY_NOTIFICATION);
		                	}
		                }
					}
				}	
		);
	}

}
	
	