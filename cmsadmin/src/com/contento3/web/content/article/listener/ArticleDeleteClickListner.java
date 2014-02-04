package com.contento3.web.content.article.listener;

import org.apache.shiro.authz.AuthorizationException;
import org.vaadin.dialogs.ConfirmDialog;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.web.site.listener.EntityDeleteClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;


public class ArticleDeleteClickListner extends EntityDeleteClickListener<ArticleDto>  { 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 * @param articleDto
	 * @param window
	 * @param helper
	 * @param deleteLink
	 * @param table
	 */
	public ArticleDeleteClickListner(final ArticleDto articleDto,final ArticleService articleService,final Button deleteLink,final Table table) {
		super(articleDto,articleService,deleteLink,table);
	}
	
	/**
	 * Handle delete button click event 
	 */
	@Override
	public void buttonClick(ClickEvent event) {
		
		final Object id = getDeleteLink().getData();
		final String name = (String) getTable().getContainerProperty(id,"articles").getValue();
		if(getDtoToDelete().getHead().equals(name)){
			ConfirmDialog.show(UI.getCurrent(), "Please Confirm:"," Are you really sure to delete?",
			        "Yes", "Cancel", new ConfirmDialog.Listener() {

						private static final long serialVersionUID = 1L;

						public void onClose(ConfirmDialog dialog) {
			                if (dialog.isConfirmed()) {
			                    // Confirmed to continue
			                	try
			                	{
			                		getDtoToDelete().setIsVisible(0);
			                		((ArticleService) getService()).update(getDtoToDelete());
			                		getTable().removeItem(id);
			                		getTable().setPageLength(getTable().getPageLength()-1);
			        				Notification.show(getDtoToDelete().getHead()+" article deleted succesfully");
			                	}catch(AuthorizationException ex){Notification.show("You are not permitted to delete articles");}
			                } else {
			                    // User did not confirm
			                    
			                }
			            }
			        });
		}
	}

}
