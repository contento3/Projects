package com.contento3.web.site;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.common.helper.ScreenHeader;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

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

	private SiteService siteService;
	
	public SiteContentAssignmentUIManager(TabSheet uiTabSheet,
			SpringContextHelper contextHelper, Window parentWindow) {
		tabSheet = uiTabSheet;
		this.contextHelper = contextHelper;
		this.parentWindow = parentWindow;
		this.siteService = (SiteService)contextHelper.getBean("siteService");
	}

	public Component render(final Integer siteId){
		final ScreenHeader screenHeader = new ScreenHeader(verticalLayout,"Assign Content to Site : "+siteService.findSiteById(siteId).getSiteName());
//		final AbstractTreeTableBuilder tableBuilder = new CategoryTableBuilder(this.parentWindow,this.contextHelper,this.tabSheet,this.categoryTable);
//		Collection<CategoryDto> categories=this.categoryService.findNullParentIdCategory((Integer)SessionHelper.loadAttribute(parentWindow, "accountId"));
//		tableBuilder.build((Collection)categories);
	//	verticalLayout.addComponent(categoryTable);
		verticalLayout.setSpacing(true);
		verticalLayout.setMargin(true);

		Tab tab = tabSheet.addTab(verticalLayout);
		tab.setCaption("Site Content Assigner");
		tab.setClosable(true);
		tabSheet.setSelectedTab(verticalLayout);

		//Pop-up that adds a new domain
		final FormLayout formLayout = new FormLayout();
		final Collection<String> contentTypeValue = new ArrayList<String>();
		contentTypeValue.add("Article");
		contentTypeValue.add("Image");
		contentTypeValue.add("Video");
		contentTypeValue.add("Document");
		
		final ComboBox contentTypeComboBox = new ComboBox("Content Type",contentTypeValue);
		contentTypeComboBox.setImmediate(true);
		formLayout.addComponent(contentTypeComboBox);
		
		final Button button = new Button("Search Assigned Content");
		formLayout.addComponent(button);

		verticalLayout.addComponent(formLayout);
		verticalLayout.addComponent(new HorizontalRuler());
		return tabSheet;
	}
	
}
