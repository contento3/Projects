package com.contento3.web.content.article.listner;

import org.vaadin.dialogs.ConfirmDialog;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class ArticleDeleteClickListner implements ClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Used to get service beans from spring context.
	 */
	private final SpringContextHelper contextHelper;
	 /**
     * Represents the parent window of the ui
     */
	final Window window;
	/**
	 * Table for Group
	 */
	private final Table table;
	/**
	 * ArticleDto
	 */
	final ArticleDto articleDto;
	/**
	 * Delete link button
	 */
	private final Button deleteLink;
	
	/**
	 * Constructor
	 * @param articleDto
	 * @param window
	 * @param helper
	 * @param deleteLink
	 * @param table
	 */
	public ArticleDeleteClickListner(final ArticleDto articleDto,final Window window,final SpringContextHelper helper,final Button deleteLink,final Table table) {
		this.articleDto = articleDto;
		this.window=window;
		this.contextHelper = helper;
		this.table = table;
		this.deleteLink = deleteLink;
	}
	
	/**
	 * Handle delete button click event 
	 */
	@Override
	public void buttonClick(ClickEvent event) {
		final ArticleService articleService = (ArticleService) contextHelper.getBean("articleService");
		final Object id = deleteLink.getData();
		final String name = (String) table.getContainerProperty(id,"articles").getValue();
		if(articleDto.getHead().equals(name)){
			ConfirmDialog.show(window, "Please Confirm:"," Are you really sure to delete?",
			        "Yes", "Cancel", new ConfirmDialog.Listener() {

						private static final long serialVersionUID = 1L;

						public void onClose(ConfirmDialog dialog) {
			                if (dialog.isConfirmed()) {
			                    // Confirmed to continue
			                	articleDto.setIsVisible(0);
			                	articleService.update(articleDto);
			                	table.removeItem(id);
			        			table.setPageLength(table.getPageLength()-1);
			        			window.showNotification(articleDto.getHead()+" article deleted succesfully");
			                	
			                } else {
			                    // User did not confirm
			                    
			                }
			            }
			        });
		}
	}

}
