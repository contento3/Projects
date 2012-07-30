package com.contento3.web.site;

import com.contento3.cms.page.service.PageService;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Window;

/**
 * UI manager to display Category Related UI
 * @author HAMMAD
 *
 */
public class CategoryUIManager {


	/**
	 * Site Service to find site related information
	 */
	private final SiteService siteService;

	/**
	 * Service layer class for page entity
	 */
	private final PageService pageService;

	/**
	 * Helper use to load spring beans
	 */
	private final SpringContextHelper contextHelper;
	
	/**
	 * Parent window that contains this ui
	 */
	private final Window parentWindow;

	public CategoryUIManager(final SiteService siteService,final PageService pageService,final SpringContextHelper contextHelper,final Window parentWindow){
		this.siteService = siteService;
		this.pageService = pageService;
		this.contextHelper = contextHelper;
		this.parentWindow = parentWindow;
	}
	
	
	/**
	 * Used to render a new tab to assign category to page.
	 * 
	 * @param siteId
	 * @param tabSheet
	 * @param pageId
	 * @param categoryLabel
	 */

	private void renderCategory(final Integer siteId,
			final TabSheet tabSheet, final Integer pageId,final Label categoryLabel) {
		
		CategoryTreeRender tree = new CategoryTreeRender(contextHelper, parentWindow);
		tree.renderTreeToAssign(siteId, tabSheet, pageId, categoryLabel);
		
	}//end renderCategory()

}
