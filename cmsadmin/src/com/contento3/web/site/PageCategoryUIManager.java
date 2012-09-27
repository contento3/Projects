package com.contento3.web.site;

import java.util.Collection;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.service.CategoryService;
import com.contento3.cms.page.service.PageService;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.web.category.CategoryPopup;
import com.contento3.web.category.CategoryTableBuilder;
import com.contento3.web.common.helper.AbstractTreeTableBuilder;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.common.helper.ScreenHeader;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * UI manager to display Category Related UI
 * @author HAMMAD
 *
 */
public class PageCategoryUIManager {


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

	/**
	 * 
	 */
	private final CategoryService categoryService;
	
	/**
	 * Article table which shows articles
	 */
	private final TreeTable categoryTable =  new TreeTable("Cateogory");

	private final TabSheet tabSheet;
	

	/**
	 * main layout for article manager screen
	 */
	private VerticalLayout verticalLayout = new VerticalLayout();

	public PageCategoryUIManager(final TabSheet uiTabSheet,final SiteService siteService,final PageService pageService,
			final SpringContextHelper contextHelper,final Window parentWindow){
		this.siteService = siteService;
		this.pageService = pageService;
		this.contextHelper = contextHelper;
		this.parentWindow = parentWindow;
		this.categoryService = (CategoryService) contextHelper.getBean("categoryService");
		this.tabSheet = uiTabSheet;
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

	public void renderCategoryList(final Integer siteId) {
		final ScreenHeader screenHeader = new ScreenHeader(verticalLayout,"Site Configuration");
		final AbstractTreeTableBuilder tableBuilder = new CategoryTableBuilder(this.parentWindow,this.contextHelper,this.tabSheet,this.categoryTable,siteId);
		Collection<CategoryDto> categories=this.categoryService.findNullParentIdCategory();
		tableBuilder.build((Collection)categories);
		verticalLayout.addComponent(categoryTable);
		verticalLayout.setSpacing(true);
		verticalLayout.setMargin(true);

		Tab tab = tabSheet.addTab(verticalLayout);
		tab.setCaption("Categories");
		tab.setClosable(true);
		tabSheet.setSelectedTab(verticalLayout);
		
		//Pop-up that adds a new domain
		final Button button = new Button("Add Category", new CategoryPopup(parentWindow, contextHelper,siteId,categoryTable,tabSheet), "openButtonClick");
		verticalLayout.addComponent(button);
		verticalLayout.addComponent(new HorizontalRuler());
	
	}//end renderCategory()
	
}
