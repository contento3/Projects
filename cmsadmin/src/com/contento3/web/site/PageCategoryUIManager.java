package com.contento3.web.site;

import java.util.Collection;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.service.CategoryService;
import com.contento3.web.UIManager;
import com.contento3.web.category.CategoryPopup;
import com.contento3.web.category.CategoryTableBuilder;
import com.contento3.web.common.helper.AbstractTreeTableBuilder;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.common.helper.ScreenHeader;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;

/**
 * UI manager to display Category Related UI
 * @author HAMMAD
 *
 */
public class PageCategoryUIManager implements UIManager{


	/**
	 * Helper use to load spring beans
	 */
	private final SpringContextHelper contextHelper;

	/**
	 * 
	 */
	private final CategoryService categoryService;

	/**
	 * Article table which shows articles
	 */
	private final TreeTable categoryTable =  new TreeTable("Category");

	private final TabSheet tabSheet;


	/**
	 * main layout for article manager screen
	 */
	private VerticalLayout verticalLayout = new VerticalLayout();

	public PageCategoryUIManager(final TabSheet uiTabSheet,final SpringContextHelper contextHelper){
		this.contextHelper = contextHelper;
		this.categoryService = (CategoryService) contextHelper.getBean("categoryService");
		this.tabSheet = uiTabSheet;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Component render(final String command) {
		final ScreenHeader screenHeader = new ScreenHeader(verticalLayout,"Category Management");
		final AbstractTreeTableBuilder tableBuilder = new CategoryTableBuilder(this.contextHelper,this.tabSheet,this.categoryTable);
		Collection<CategoryDto> categories=this.categoryService.findNullParentIdCategory((Integer)SessionHelper.loadAttribute("accountId"));
		tableBuilder.build((Collection)categories);
		verticalLayout.addComponent(categoryTable);
		verticalLayout.setSpacing(true);
		verticalLayout.setMargin(true);

		Tab tab = tabSheet.addTab(verticalLayout);
		tab.setCaption("Categories");
		tab.setClosable(true);
		tabSheet.setSelectedTab(verticalLayout);

		//Pop-up that adds a new domain
		final Button button = new Button("Add Category");
		button.addClickListener(new CategoryPopup(contextHelper,categoryTable,tabSheet));
		verticalLayout.addComponent(button);
		verticalLayout.addComponent(new HorizontalRuler());
		return tabSheet;
	}//end renderCategory()


	@Override
	public void render() {
	}


	@Override
	public Component render(String command, Integer entityFilterId) {
		return null;
	}


	@Override
	public Component render(String command,
			HierarchicalContainer treeItemContainer) {
		return null;
	}

}