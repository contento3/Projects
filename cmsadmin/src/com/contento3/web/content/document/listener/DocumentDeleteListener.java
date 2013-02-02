package com.contento3.web.content.document.listener;

import org.vaadin.dialogs.ConfirmDialog;

import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.common.service.Service;
import com.contento3.dam.document.dto.DocumentDto;
import com.contento3.dam.document.service.DocumentService;
import com.contento3.web.site.listener.EntityDeleteClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

public class DocumentDeleteListener extends EntityDeleteClickListener<DocumentDto> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * Represents the parent window of the ui
     */
	final Window window;
	
	public DocumentDeleteListener(final DocumentDto dtoToDelete, final Window window, final Service<DocumentDto> service, final Button deleteLink, final Table table) {
		super(dtoToDelete, service, deleteLink, table);
		this.window = window;
	}
	
	/**
	 * Handle delete button click event 
	 */
	@Override
	public void buttonClick(ClickEvent event) {
		
		final Object id = getDeleteLink().getData();
		final String name = (String) getTable().getContainerProperty(id,"documents").getValue();
		if(getDtoToDelete().getDocumentTitle().equals(name)){
			ConfirmDialog.show(window, "Please Confirm:"," Are you really sure to delete?",
			        "Yes", "Cancel", new ConfirmDialog.Listener() {

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
			        			window.showNotification(getDtoToDelete().getDocumentTitle()+" article deleted succesfully");
			                	
			                } else {
			                    // User did not confirm
			                    
			                }
			            }
			        });
		}
	}
}
