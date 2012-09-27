package com.contento3.web.content.article.listner;

import org.vaadin.dialogs.ConfirmDialog;
import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.web.site.listener.EntityDeleteClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;


public class ArticleDeleteClickListner extends EntityDeleteClickListener<ArticleDto>  { 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 /**
     * Represents the parent window of the ui
     */
	final Window window;
	
	
	/**
	 * Constructor
	 * @param articleDto
	 * @param window
	 * @param helper
	 * @param deleteLink
	 * @param table
	 */
	public ArticleDeleteClickListner(final ArticleDto articleDto,final Window window,final ArticleService articleService,final Button deleteLink,final Table table) {
		
		super(articleDto,articleService,deleteLink,table);
		this.window=window;
		
	}
	
	/**
	 * Handle delete button click event 
	 */
	@Override
	public void buttonClick(ClickEvent event) {
		
		final Object id = getDeleteLink().getData();
		final String name = (String) getTable().getContainerProperty(id,"articles").getValue();
		if(getDtoToDelete().getHead().equals(name)){
			ConfirmDialog.show(window, "Please Confirm:"," Are you really sure to delete?",
			        "Yes", "Cancel", new ConfirmDialog.Listener() {

						private static final long serialVersionUID = 1L;

						public void onClose(ConfirmDialog dialog) {
			                if (dialog.isConfirmed()) {
			                    // Confirmed to continue
			                	getDtoToDelete().setIsVisible(0);
			                	((ArticleService) getService()).update(getDtoToDelete());
			                	getTable().removeItem(id);
			                	getTable().setPageLength(getTable().getPageLength()-1);
			        			window.showNotification(getDtoToDelete().getHead()+" article deleted succesfully");
			                	
			                } else {
			                    // User did not confirm
			                    
			                }
			            }
			        });
		}
	}

}
