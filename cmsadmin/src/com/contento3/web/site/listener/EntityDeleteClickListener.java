package com.contento3.web.site.listener;

import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.common.service.Service;
import com.contento3.web.site.PageTemplateTableBuilder;
import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;



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
	
	private int decision;
	// used in delete confirmation promt if true(2) then delete and if false(1) then leave
	
	private int response;
	// check if user click yes or no on deleting template prompt
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

	public final Service<T> getService() {
		return service;
	}

	public EntityDeleteClickListener(final T dtoToDelete,final Service<T> service,final Button deleteLink,final Table table){
		this.dtoToDelete = dtoToDelete;
		this.table = table;
		this.deleteLink = deleteLink;
		this.service = service;
	}



	Window main = new Window();
	//Window main = table.getApplication().getMainWindow();
	
	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
		//callConfirmDialog();
		deleteEntity(dtoToDelete);
		
	}
	
/**	public String setDicisionValFalse() {
		// TODO Auto-generated method stub
		//decision = 0;
		Window window = table.getApplication().getMainWindow();
		String val = "false";
		window.showNotification("canceled..");
		//notWindow.getWindow();
		return val;
	}
	public String setDicisionValTrue() {
		// TODO Auto-generated method stub
		//decision = 1;
		String val1 = "true";
		deleteEntity(dtoToDelete);
		
		//main.showNotification("Template is successfully unassigned");
		
		return val1;
	}	**/
				
	
	/**
	 * Deletes the dto from the db and also the table.
	 * @param dtoToDelete
	 */
	protected void deleteEntity(final T dtoToDelete) {
		final Object id = deleteLink.getData();
		final Window window = table.getApplication().getMainWindow();
		window.addWindow(new YesNoDialog(
				"Confirmation","Do you really want to delete template ?", "Yes", "No",
				new YesNoDialog.YesNoDialogCallback() {
					
					@Override
					public void response(boolean ok) {
						// TODO Auto-generated method stub
				if(ok)
					//decision = 1;
				//else decision = 0;
				{
					try {
						service.delete(dtoToDelete);
						table.removeItem(id);
						table.setPageLength(table.getPageLength()-1);
						//table.getApplication().getMainWindow().showNotification("Unable to delete entity."+e.getMessage());
					window.showNotification("successfully unassigned");
					} catch (EntityCannotBeDeletedException e) {
						//table.getApplication().getMainWindow().showNotification("Unable to delete entity."+e.getMessage());
					window.showNotification("Unable to delete entity");
					}
				}
				else 
					window.showNotification("canceled");
					}
				}
				));
		//if(decision == 1){
		
		//}
		
	}
	

	
	/**public void callConfirmDialog(){
	
				// TODO Auto-generated method stub
				main.addWindow(new YesNoDialog(
						"sure","really want to delete ?","yes","no",
						new YesNoDialog.YesNoDialogCallback() {
							
							@Override
							public void response(boolean ok) {
								// TODO Auto-generated method stub
							main.showNotification(ok ? setDicisionValTrue() : setDicisionValFalse());	
							}
						}
						)); 
}**/

				

				
	}
	
	