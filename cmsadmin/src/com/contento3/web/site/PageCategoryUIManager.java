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
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
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

	public PageCategoryUIManager(final TabSheet uiTabSheet,final SpringContextHelper contextHelper,final Window parentWindow){
		this.siteService = (SiteService) contextHelper.getBean("siteService");
		this.pageService = (PageService) contextHelper.getBean("pageService");
		this.contextHelper = contextHelper;
		this.parentWindow = parentWindow;
		this.categoryService = (CategoryService) contextHelper.getBean("categoryService");
		this.tabSheet = uiTabSheet;
	}
	
	
	public Component renderCategoryList(final Integer siteId) {
		final ScreenHeader screenHeader = new ScreenHeader(verticalLayout,"Category Management");
		final AbstractTreeTableBuilder tableBuilder = new CategoryTableBuilder(this.parentWindow,this.contextHelper,this.tabSheet,this.categoryTable);
		Collection<CategoryDto> categories=this.categoryService.findNullParentIdCategory((Integer)SessionHelper.loadAttribute(parentWindow, "accountId"));
		tableBuilder.build((Collection)categories);
		verticalLayout.addComponent(categoryTable);
		verticalLayout.setSpacing(true);
		verticalLayout.setMargin(true);

		Tab tab = tabSheet.addTab(verticalLayout);
		tab.setCaption("Categories");
		tab.setClosable(true);
		tabSheet.setSelectedTab(verticalLayout);
		
		//Pop-up that adds a new domain
		final Button button = new Button("Add Category", new CategoryPopup(parentWindow, contextHelper,categoryTable,tabSheet), "openButtonClick");
		verticalLayout.addComponent(button);
		verticalLayout.addComponent(new HorizontalRuler());
		return tabSheet;
	}//end renderCategory()
	
}
