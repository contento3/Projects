package com.contento3.web.content.document.listener;


import org.apache.shiro.authz.AuthorizationException;
import org.vaadin.dialogs.ConfirmDialog;

import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.common.service.SimpleService;
import com.contento3.dam.document.dto.DocumentDto;
import com.contento3.dam.document.service.DocumentService;
import com.contento3.web.site.listener.EntityDeleteClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;

public class DocumentDeleteListener extends EntityDeleteClickListener<DocumentDto> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DocumentDeleteListener(final DocumentDto dtoToDelete, final SimpleService<DocumentDto> service, final Button deleteLink, final Table table) {
		super(dtoToDelete, service, deleteLink, table);
	}
	
	/**
	 * Handle delete button click event 
	 */
	@Override
	public void buttonClick(ClickEvent event) {
		
		try{
		final Object id = getDeleteLink().getData();
		final String name = (String) getTable().getContainerProperty(id,"documents").getValue();
		if(getDtoToDelete().getDocumentTitle().equals(name)){
			ConfirmDialog.show(UI.getCurrent(), " Are you really sure to delete?",
			        new ConfirmDialog.Listener() {

						private static final long serialVersionUID = 1L;

						public void onClose(ConfirmDialog dialog) {
			                if (dialog.isConfirmed()) {
			                    // Confirmed to continue
			                	try {
									((DocumentService) getService()).delete(getDtoToDelete());
								} catch (EntityCannotBeDeletedException e) {
									e.printStackTrace();
								}
			                	getTable().removeItem(id);
			                	getTable().setPageLength(getTable().getPageLength()-1);
			        			Notification.show(getDtoToDelete().getDocumentTitle()+" article deleted succesfully");
			                	
			                } else {
			                    // User did not confirm
			                    
			                }
			            }
			        });
			}
		}
		catch(AuthorizationException ex){Notification.show("You are not permitted to delete documents");}
	}
}
