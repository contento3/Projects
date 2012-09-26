package com.contento3.web.site.listener;

import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.common.service.Service;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;

public class EntityDeleteClickListener<T> implements ClickListener {

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
	
	/**
	 * Service used to delete the entity from db.
	 */
	private final Service<T> service;

	public EntityDeleteClickListener(final T dtoToDelete,final Service<T> service,final Button deleteLink,final Table table){
		this.dtoToDelete = dtoToDelete;
		this.table = table;
		this.deleteLink = deleteLink;
		this.service = service;
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
		try {
			service.delete(dtoToDelete);
			table.removeItem(id);
			table.setPageLength(table.getPageLength()-1);
		} catch (EntityCannotBeDeletedException e) {
			table.getApplication().getMainWindow().showNotification("Unable to delete entity."+e.getMessage());
		}
	}
		
}	