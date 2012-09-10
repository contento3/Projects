package com.contento3.web.site;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.exception.PageNotFoundException;
import com.contento3.cms.page.layout.dto.PageLayoutDto;
import com.contento3.cms.page.layout.service.PageLayoutService;
import com.contento3.cms.page.section.dto.PageSectionDto;
import com.contento3.cms.page.section.dto.PageSectionTypeDto;
import com.contento3.cms.page.section.model.PageSectionTypeEnum;
import com.contento3.cms.page.section.service.PageSectionTypeService;
import com.contento3.cms.page.service.PageService;
import com.contento3.cms.page.template.dto.PageTemplateDto;
import com.contento3.cms.page.template.service.PageTemplateService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.web.common.helper.ComboDataLoader;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.common.helper.PageTemplateAssignmentPopup;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Select;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

public class PageUIManager {

	private static final Logger LOGGER = Logger.getLogger(PageUIManager.class);

	/**
	 * Site Service to find site related information
	 */
	private SiteService siteService;

	/**
	 * Service layer class for page entity
	 */
	private PageService pageService;

	/**
	 * Helper use to load spring beans
	 */
	final SpringContextHelper contextHelper;
	
	/**
	 * Application ui window that contains all the ui
	 */
	final Window parentWindow;
	
	/**
	 * IndexedContainer used to contain data
	 */
	private final IndexedContainer container = new IndexedContainer();

	private PageDto newPageDtoWithLayout;

	private int selectedPageId;

	TabSheet pageLayoutsTab;

	public PageUIManager (final SiteService siteService,final PageService pageService,final SpringContextHelper helper,final Window parentWindow){
		this.siteService = siteService;
		this.pageService = pageService;
		this.parentWindow = parentWindow;
		this.contextHelper = helper;
		
		setPageContainerProperty();
	
	}
	
	/**
	 * Add properties to index container of pages
	 */
	private void setPageContainerProperty() {
		
		container.addContainerProperty("Title", String.class, null);
		container.addContainerProperty("Uri", String.class, null);
		container.addContainerProperty("Edit", Button.class, null);
	}

	public TabSheet renderPageListing(final Integer siteId,final TabSheet pagesTab,final HorizontalLayout horizontalLayout,final VerticalLayout pageLayout) {
		pageLayout.addComponent(horizontalLayout);

		final Label subHeadingLbl = new Label("Site pages");
		subHeadingLbl.setStyleName("screenSubHeading");
		
		pageLayout.setSpacing(true);
		pageLayout.addComponent(subHeadingLbl);
		pageLayout.addComponent(new HorizontalRuler());
		
		final Table table = new Table();
		table.setImmediate(true);

		// Create a new layout and add as a the 
		// main component for the new site tab
		horizontalLayout.setSpacing(true);

		pagesTab.addComponent(pageLayout);
		pagesTab.setHeight("675");
		pagesTab.setWidth("775");

		SiteDto siteDto = siteService.findSiteById(siteId);
		pagesTab.addTab(pageLayout, siteDto.getSiteName(),null);
		pagesTab.setImmediate(true);
		pagesTab.addListener(new SelectedTabChangeListener() {
			private static final long serialVersionUID = 1L;

			public void selectedTabChange(SelectedTabChangeEvent event) {
				event.getTabSheet().getSelectedTab().requestRepaint();
				table.requestRepaint();
			}
		});
		

		// Button that when clicked rendered a new page tab.
//		Button newPageButton = new Button("Create new page");
//		horizontalLayout.addComponent(newPageButton);
//		newPageButton.addListener(new ClickListener() {
//			private static final long serialVersionUID = 1L;
//
//			public void buttonClick(ClickEvent event) {
//				renderNewPage(siteId, pagesTab, null);
//			}
//		});

		// Button for site configuration
//		String buttonText = "Site Config";
//		Button siteConfigButton = new Button(buttonText);
//		horizontalLayout.addComponent(siteConfigButton);
//		siteConfigButton.addListener(new ClickListener() {
//			private static final long serialVersionUID = 1L;
//
//			public void buttonClick(ClickEvent event) {
//			//	renderSiteConfig(siteId, pagesTab, null);
//			}
//		});
//		
		
//		Button addNewCategoryButton = new Button("Add New Category");
//		horizontalLayout.addComponent(addNewCategoryButton);
//		addNewCategoryButton.addListener(new ClickListener() {
//			
//			@Override
//			public void buttonClick(ClickEvent event) {
//				CategoryTreeRender categoryTree = new CategoryTreeRender(contextHelper, parentWindow);
//				categoryTree.renderTreeToAddNewCategory(siteId, pagesTab, null);
//				
//			}
//		});
		final PageService pageService = (PageService) contextHelper.getBean("pageService");
		final Collection<PageDto> pageDtos = pageService.findPageBySiteId(siteId);

		if (!CollectionUtils.isEmpty(pageDtos)) {
			
			table.setWidth(100, Sizeable.UNITS_PERCENTAGE);
			table.setPageLength(5);
			Button link = null;
			for (PageDto page : pageDtos) {
				addPageToPageListTable(page, siteId, pagesTab, link);
			}

			table.setContainerDataSource(container);
			pageLayout.addComponent(table);
		} else {
			final Label label = new Label("No pages found for this site.");
			pageLayout.addComponent(label);
		}

		return pagesTab;
	}


	/**
	 * Used to render a tab to create a new page.This includes selecting layout
	 * for page and other page related information.
	 * 
	 * @param accountId
	 * @param tabSheet
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void renderNewPage(final Integer siteId, final TabSheet pagesTab,
			final Integer pageId) {
		final VerticalLayout newPageParentlayout = new VerticalLayout();

		final FormLayout newPageFormLayout = new FormLayout();
		newPageParentlayout.addComponent(newPageFormLayout);

		final TextField titleTxt = new TextField();
		titleTxt.setCaption("Title");

		final TextField uriTxt = new TextField();
		uriTxt.setCaption("Uri");
		final Label categoryLabel = new Label();

		// Button for assigning category 
		//categoryLabel
		String addCateogryButtonText = "Assign Category";
		Button addCateogryButton = new Button(addCateogryButtonText);
		addCateogryButton.setEnabled(false);
		HorizontalLayout horiz = new HorizontalLayout();
		horiz.setSpacing(true);
		horiz.addComponent(categoryLabel);
		horiz.addComponent(addCateogryButton);
		newPageFormLayout.addComponent(horiz);
		newPageFormLayout.addComponent(titleTxt);
		newPageFormLayout.addComponent(uriTxt);

		// TODO get it from a property file
		String pageTabTitle = "Untitled page";
		String pageButtonTitle = "Add page";
		PageDto pageDto = null;
		//categories = null;
		if (null != pageId) {

			try {
				pageDto = pageService.findPageWithLayout(pageId);
				
			} catch (PageNotFoundException e) {
				LOGGER.equals(String.format("Page not found %s", pageId));
			}

			pageTabTitle = String.format("Edit %s", pageDto.getTitle());
			pageButtonTitle = "Save";
			
		
			//categories = pageDto.getCategories();
			Iterator<CategoryDto> itr=pageDto.getCategories().iterator();
			addCateogryButton.setEnabled(true);
			if(itr.hasNext()){
				CategoryDto dto = itr.next();
				categoryLabel.setValue("Category: " + dto.getCategoryName());
				addCateogryButton.setCaption("Edit Categories");
				
			}
			else{
				categoryLabel.setValue("No Category Assigned");
				addCateogryButton.setCaption("Assign Category");
				
			}
		}
		
		addCateogryButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				//renderCategory(siteId, pagesTab, pageId,categoryLabel);
			}
		});
		Tab newPageTab = pagesTab.addTab(newPageParentlayout, pageTabTitle,
				null);
		pagesTab.setSelectedTab(newPageParentlayout);
		newPageTab.setVisible(true);
		newPageTab.setEnabled(true);
		newPageTab.setClosable(true);

		// List box to select Page layouts

		final PageLayoutService pageLayoutService = (PageLayoutService) contextHelper
				.getBean("pageLayoutService");
		final SiteDto siteDto = siteService.findSiteById(siteId);

		Collection<PageLayoutDto> pageLayoutDto = pageLayoutService.findPageLayoutByAccount(siteDto.getAccountDto().getAccountId());
		final ComboDataLoader comboDataLoader = new ComboDataLoader();
		final ComboBox pageLayoutCombo = new ComboBox("Select Page Layouts",
				comboDataLoader.loadDataInContainer((Collection)pageLayoutDto));

		Button newPageSubmitBtn = new Button(pageButtonTitle);
		newPageFormLayout.addComponent(pageLayoutCombo);
		newPageFormLayout.addComponent(newPageSubmitBtn);
		pageLayoutCombo.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
		pageLayoutCombo.setItemCaptionPropertyId("name");

		newPageSubmitBtn.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				PageService pageService = (PageService) contextHelper
						.getBean("pageService");
				PageDto pageDto = new PageDto();
				pageDto.setTitle(titleTxt.getValue().toString());
				pageDto.setUri(uriTxt.getValue().toString());
				pageDto.setSite(siteDto);
				pageDto.setCategories(new ArrayList<CategoryDto>());
////				if(categories!=null){
////					pageDto.setCategories(categories);
////				}else{
////					pageDto.setCategories(null);
//				}
				if (null != pageLayoutCombo.getValue()) {
					pageDto.setPageLayoutDto(pageLayoutService
							.findPageLayoutById(Integer
									.parseInt(pageLayoutCombo.getValue()
											.toString())));
				}

	try{
				String notificationMsg = "Page %s %s successfullly";
				if (null!=pageId){
					pageDto.setPageId(pageId);
					pageService.update(pageDto);	
					notificationMsg = String.format(notificationMsg,pageDto.getTitle(),"updated");
				}
				else {
					// Create a new page,get page dto with its layout.
					newPageDtoWithLayout = pageService.createAndReturn(pageDto);
					addPageToPageListTable(newPageDtoWithLayout, siteId, pagesTab,
							new Button());

					// Render the page layout by splitting them with page sections
					// and add them to the parent layout i.e. VerticalLayout
					newPageParentlayout
							.addComponent(renderPageLayouts(newPageDtoWithLayout));
					notificationMsg = String.format(
							"Page %s added successfully",
							newPageDtoWithLayout.getTitle());
				}

				parentWindow.showNotification(notificationMsg);
				}
				catch(EntityAlreadyFoundException e){
					parentWindow.showNotification("Page already exists with this title or uri",Notification.TYPE_ERROR_MESSAGE);
				}

			}

		});

		// Call for editing
		if (null != pageId) {
			newPageParentlayout.addComponent(populatePage(pageDto,
					newPageFormLayout));
		}
	}

	private void addPageToPageListTable(final PageDto page,
			final Integer siteId, final TabSheet pagesTab, Button link) {

		Item item = container.addItem(page.getPageId());
		item.getItemProperty("Title").setValue(page.getTitle());
		item.getItemProperty("Uri").setValue(page.getUri());
		link = new Button();

		link.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				// Get the item identifier from the user-defined data.
				// Integer pageId = (Integer)event.getButton().getData();
				renderNewPage(siteId, pagesTab, page.getPageId());
			}
		});

		link.setCaption("Edit");
		link.setData(page.getPageId());
		link.addStyleName("link");
		item.getItemProperty("Edit").setValue(link);
	}

	/**
	 * Renders the page layouts. A PageDto is passed as a parameter so that
	 * associated layouts to a page can be rendered.
	 * 
	 * @param pageDtoWithLayout
	 */
	public TabSheet renderPageLayouts(PageDto pageDtoWithLayout) {
		final PageLayoutDto layoutDto = pageDtoWithLayout.getPageLayoutDto();
		pageLayoutsTab = new TabSheet();
		pageLayoutsTab.setHeight("");
		pageLayoutsTab.setWidth("775");

		PageTemplateDto pageTemplateDto = new PageTemplateDto();
		//pageTemplateDto.setPageId(selectedPageId);
		pageTemplateDto.setPageId(pageDtoWithLayout.getPageId());
		parentWindow.setData(pageTemplateDto);

		// If there are layout with page sections then add it
		if (null != layoutDto && !layoutDto.getLayoutTypeDto().getName().equalsIgnoreCase(PageSectionTypeEnum.CUSTOM.toString())) {
			final List<PageSectionDto> pageSections = (List<PageSectionDto>) layoutDto
					.getPageSections();

			Collections.sort(pageSections);
			final Iterator<PageSectionDto> pageSectionIterator = pageSections
					.iterator();

			while (pageSectionIterator.hasNext()) {
				final VerticalLayout pageSectionLayout = new VerticalLayout();
				pageLayoutsTab.addComponent(pageSectionLayout);
				pageLayoutsTab.setSizeFull();
				renderPageSection(pageLayoutsTab, pageSectionLayout,
						pageSectionIterator.next(), pageTemplateDto);
			}
		}
		// otherwise add a section to add layout based on a template.
		else {
			final VerticalLayout pageSectionLayout = new VerticalLayout();
			pageLayoutsTab.addTab(pageSectionLayout, "Custom Layout", null);
			renderPageTemplateList(pageSectionLayout,PageSectionTypeEnum.CUSTOM);
			pageSectionLayout.addComponent(new PageTemplateAssignmentPopup("Open", parentWindow, contextHelper));
		}
		return pageLayoutsTab;
	}

	public void renderPageTemplateList(final VerticalLayout pageSectionLayout,
			final PageSectionTypeEnum sectionType) {
		PageTemplateService pageTemplateService = (PageTemplateService) contextHelper.getBean("pageTemplateService");
		PageSectionTypeService pageSectionTypeService = (PageSectionTypeService) contextHelper.getBean("pageSectionTypeService");
		PageSectionTypeDto sectionTypeDto = pageSectionTypeService.findByName(sectionType);
		Collection<PageTemplateDto> newPageTemplates = pageTemplateService.findByPageAndPageSectionType(selectedPageId,sectionTypeDto.getId());

		if (!CollectionUtils.isEmpty(newPageTemplates)){}
	}


	public void renderPageSection(final TabSheet pageLayoutsTab,
			final VerticalLayout pageSectionLayout,
			final PageSectionDto pageSectionDto,
			final PageTemplateDto pageTemplateDto) {
		pageLayoutsTab.addTab(pageSectionLayout, pageSectionDto.getSectionTypeDto().getName(), null);
		pageTemplateDto.setSectionTypeId(pageSectionDto.getSectionTypeDto().getId());
		PageSectionTypeService pageSectionTypeService = (PageSectionTypeService) contextHelper.getBean("pageSectionTypeService");
		PageSectionTypeDto sectionTypeDto = pageSectionTypeService.findById(pageSectionDto.getSectionTypeDto().getId());

		renderPageTemplateList(pageSectionLayout,PageSectionTypeEnum.valueOf(sectionTypeDto.getName()));
		pageSectionLayout.addComponent(new PageTemplateAssignmentPopup("Open",parentWindow, contextHelper));
	}

	/**
	 * Populate the page ui if the tab is opened for editing.
	 * 
	 * @param pageId
	 * @param newPageFormLayout
	 * @return tabsheet with page layout and its section if present or empty
	 *         pagelayout without page section.
	 */
	private TabSheet populatePage(final PageDto pageDto,
			final FormLayout newPageFormLayout) {

		((TextField) newPageFormLayout.getComponent(1)).setValue(pageDto
				.getTitle());
		((TextField) newPageFormLayout.getComponent(2)).setValue(pageDto
				.getUri());

		// This will be used to be passed to the template assignment sub window
		selectedPageId = pageDto.getPageId();

		if (null != pageDto.getPageLayoutDto()) {
			((ComboBox) newPageFormLayout.getComponent(3)).select(pageDto
					.getPageLayoutDto().getId());
		}
		return renderPageLayouts(pageDto);
	}

}
