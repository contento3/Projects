package com.contento3.web.site;

import java.util.Collection;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.web.category.CategoryPopup;
import com.contento3.web.category.CategoryTableBuilder;
import com.contento3.web.common.helper.AbstractTreeTableBuilder;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.common.helper.ScreenHeader;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.TabSheet.Tab;

public class SiteContentAssignmentUIManager {

	private final TabSheet tabSheet;

	/**
	 * Helper use to load spring beans
	 */
	private final SpringContextHelper contextHelper;

	/**
	 * Parent window that contains this ui
	 */
	private final Window parentWindow;

	/**
	 * main layout for content assignment screen
	 */
	private VerticalLayout verticalLayout = new VerticalLayout();

	public SiteContentAssignmentUIManager(TabSheet uiTabSheet,
			SpringContextHelper contextHelper, Window parentWindow) {
		tabSheet = uiTabSheet;
		this.contextHelper = contextHelper;
		this.parentWindow = parentWindow;
	}

	public Component render(final Integer siteId){
		final ScreenHeader screenHeader = new ScreenHeader(verticalLayout,"Assign Content to Site");
//		final AbstractTreeTableBuilder tableBuilder = new CategoryTableBuilder(this.parentWindow,this.contextHelper,this.tabSheet,this.categoryTable);
//		Collection<CategoryDto> categories=this.categoryService.findNullParentIdCategory((Integer)SessionHelper.loadAttribute(parentWindow, "accountId"));
//		tableBuilder.build((Collection)categories);
	//	verticalLayout.addComponent(categoryTable);
		verticalLayout.setSpacing(true);
		verticalLayout.setMargin(true);

		Tab tab = tabSheet.addTab(verticalLayout);
		tab.setCaption("Categories");
		tab.setClosable(true);
		tabSheet.setSelectedTab(verticalLayout);

		//Pop-up that adds a new domain
		final Button button = new Button("Add Category");
		verticalLayout.addComponent(button);
		verticalLayout.addComponent(new HorizontalRuler());
		return tabSheet;
	}
	
}
