package com.contento3.web.site;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.service.CategoryService;
import com.contento3.web.UIManager;
import com.contento3.web.category.CategoryPopup;
import com.contento3.web.category.CategoryTableBuilder;
import com.contento3.web.category.listener.AddCategoryClickListener;
import com.contento3.web.common.helper.AbstractTreeTableBuilder;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.common.helper.ScreenHeader;
import com.contento3.web.common.helper.ScreenToolbarBuilder;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.user.listner.AddUserClickListener;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
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
		HorizontalLayout horizon = new HorizontalLayout();
		VerticalLayout verticl = new VerticalLayout();
		this.verticalLayout.addComponent(horizon);
		horizon.addComponent(verticl);
		
		final ScreenHeader screenHeader = new ScreenHeader(verticl,"Category Management");
		final AbstractTreeTableBuilder tableBuilder = new CategoryTableBuilder(this.contextHelper,this.tabSheet,this.categoryTable);
		Collection<CategoryDto> categories=this.categoryService.findNullParentIdCategory((Integer)SessionHelper.loadAttribute("accountId"));
		tableBuilder.build((Collection)categories);
		verticl.addComponent(categoryTable);
		verticl.setSpacing(true);
		verticl.setMargin(true);

		Tab tab = tabSheet.addTab(verticalLayout);
		tab.setCaption("Categories");
		tab.setClosable(true);
		tabSheet.setSelectedTab(verticalLayout);

		//Pop-up that adds a new domain
		final Button button = new Button("Add Category");
		button.addClickListener(new CategoryPopup(contextHelper,categoryTable,tabSheet));
//		verticl.addComponent(button);
		verticl.addComponent(new HorizontalRuler());
		
		GridLayout toolbarGridLayout = new GridLayout(1,1);
		List<com.vaadin.event.MouseEvents.ClickListener> listeners = new ArrayList<com.vaadin.event.MouseEvents.ClickListener>();
		listeners.add(new AddCategoryClickListener(contextHelper, categoryTable,tabSheet ));
		
		ScreenToolbarBuilder builder = new ScreenToolbarBuilder(toolbarGridLayout,"page",listeners);
		builder.build();
		horizon.addComponent(toolbarGridLayout);
		horizon.setWidth(100,Unit.PERCENTAGE);
		horizon.setExpandRatio(toolbarGridLayout, 1);
		horizon.setExpandRatio(verticl, 9);
		
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