package com.contento3.web.site;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.util.CollectionUtils;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.exception.PageCannotCreateException;
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
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.ComboDataLoader;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.common.helper.PageTemplateAssignmentPopup;
import com.contento3.web.common.helper.ScreenHeader;
import com.contento3.web.common.helper.ScreenToolbarBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.site.listener.AddPageButtonClickListener;
import com.contento3.web.site.listener.PageAssignCategoryListener;
import com.contento3.web.site.listener.SEOSettingsEventListener;
import com.contento3.web.site.seo.SEOUIManager;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class PageUIManager {

	private static final Logger LOGGER = Logger.getLogger(PageUIManager.class);
	
	private final static String BUTTON_NAME_PUBLISHED = "Publish";

	private final static String BUTTON_NAME_UNPUBLISHED = "Unpublish";

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
	 * IndexedContainer used to contain data
	 */
	private final IndexedContainer container = new IndexedContainer();
	
	/**
	 * UI Manager that renders page related screens
	 */
	private SEOUIManager seoUIManager;

	private PageDto newPageDtoWithLayout;

	private int selectedPageId;

	TabSheet pageLayoutsTab;
	ComboBox pageLayoutCombo ;
	SiteDto siteDto ;
	PageLayoutService pageLayoutService;
	
	public PageUIManager (final SiteService siteService,final PageService pageService,final SpringContextHelper helper){
		this.siteService = siteService;
		this.pageService = pageService;
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
	
	public TabSheet renderPageListing(final Integer siteId,final TabSheet pagesTab,final VerticalLayout horizontalLayout,final VerticalLayout pageLayout) {
		final Table table = new Table();
		try {
			siteDto = siteService.findSiteById(siteId);
		
		HorizontalLayout horizLayout = new HorizontalLayout();
		
		final Label subHeadingLbl = new Label("Site page");
		subHeadingLbl.setStyleName("screenSubHeading");
		horizLayout.addComponent(subHeadingLbl);
		
		HorizontalLayout layoutForBtns = new HorizontalLayout();
		layoutForBtns.setSpacing(true);
		
		
		if (SecurityUtils.getSubject().isPermitted("SITE:PUBLISH") && SecurityUtils.getSubject().isPermitted("SITE:UNPUBLISH")){
			// published/unpublished button
			final Button btnPublish = new Button(getButtonTitle()); 
			btnPublish.addClickListener(publishedListener());
			layoutForBtns.addComponent(btnPublish);
		}
		
		horizLayout.setWidth(100, Unit.PERCENTAGE);
		horizLayout.addComponent(layoutForBtns);
		horizLayout.setComponentAlignment(layoutForBtns, Alignment.TOP_RIGHT);
		pageLayout.addComponent(horizLayout);
		pageLayout.setSpacing(true);
		pageLayout.addComponent(new HorizontalRuler());
		
		table.setImmediate(true);

		// Create a new layout and add as a the 
		// main component for the new site tab
		horizontalLayout.setSpacing(true);

		pagesTab.addComponent(horizontalLayout);

		Tab tab = pagesTab.addTab(horizontalLayout, siteDto.getSiteName(),new ExternalResource("images/site.png"));
		tab.setClosable(true);
		pagesTab.setImmediate(true);
		pagesTab.addSelectedTabChangeListener(new SelectedTabChangeListener() {
			private static final long serialVersionUID = 1L;

			public void selectedTabChange(SelectedTabChangeEvent event) {
				event.getTabSheet().getSelectedTab().markAsDirty();
				table.markAsDirty();
			}
		});
		
		final PageService pageService = (PageService) contextHelper.getBean("pageService");
			final Collection<PageDto> pageDtos = pageService.findPageBySiteId(siteId);
	
			if (!CollectionUtils.isEmpty(pageDtos)) {
				
				table.setWidth(100, Unit.PERCENTAGE);
				table.setPageLength(pageDtos.size());
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
		}
		catch (final AuthorizationException ae){
			LOGGER.debug("User does not have permission to perform this operation.");
			table.setPageLength(1);
			addEmpytPageTable(table);
			pageLayout.addComponent(table);
			table.setWidth(100,Unit.PERCENTAGE);
		}
		return pagesTab;
	}

	/**
	 * Get Title for published/Unpublished button
	 * @return Button name 
	 */
	private String getButtonTitle() {
		
		if(siteDto.getStatus() == null || siteDto.getStatus() == 0) {
			return BUTTON_NAME_PUBLISHED;
		} else {
			return BUTTON_NAME_UNPUBLISHED;
		}
	}
	
	
	/**
	 * Listener for publish button
	 * @return
	 */
	private ClickListener publishedListener() {
	
		ClickListener listener = new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				if(siteDto != null) {
					if( event.getButton().getCaption().equals(BUTTON_NAME_PUBLISHED) ) {
						siteDto.setStatus(1); // set status publishes
						event.getButton().setCaption(BUTTON_NAME_UNPUBLISHED);
						
					} else {
						siteDto.setStatus(0);
						event.getButton().setCaption(BUTTON_NAME_PUBLISHED);
					}
					siteService.update(siteDto);
				}
			}
		};
		
		return listener;
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

		try {
			final HorizontalLayout newPageRootlayout = new HorizontalLayout();
			final VerticalLayout newPageParentlayout = new VerticalLayout();
			newPageParentlayout.setSpacing(true);
			
			ScreenHeader screenHeader = new ScreenHeader(newPageParentlayout,"Page");
	        newPageRootlayout.setSpacing(true);
			newPageRootlayout.addComponent(newPageParentlayout);
	
			final FormLayout newPageFormLayout = new FormLayout();
			newPageParentlayout.addComponent(newPageFormLayout);
			pagesTab.setSelectedTab(newPageRootlayout);
	
			final TextField titleTxt = new TextField();
			titleTxt.setCaption("Title");
	
			final TextField uriTxt = new TextField();
			uriTxt.setCaption("Uri");
			siteDto = siteService.findSiteById(siteId);
			
			final CheckBox checkbox1 = new CheckBox();
			checkbox1.setCaption("Add to Navigation");
			
			newPageFormLayout.addComponent(titleTxt);
			newPageFormLayout.addComponent(uriTxt);
			newPageFormLayout.addComponent(checkbox1);
			newPageFormLayout.setWidth(100,Unit.PERCENTAGE);
			newPageFormLayout.setSpacing(true);
			
	
			String pageTabTitle = "Untitled page";
			String pageButtonTitle = "Add page";
			PageDto pageDto = null;
			//categories = null;
			if (null != pageId) {
	
				try {
					pageDto = pageService.findPageWithLayout(pageId);
					
				} catch (final PageNotFoundException e) {
					LOGGER.debug(String.format("Page not found %s", pageId));
				}
				pageTabTitle = String.format("Edit %s", pageDto.getTitle());
				pageButtonTitle = "Save";
				
			
				//categories = pageDto.getCategories();
				Iterator<CategoryDto> itr=pageDto.getCategories().iterator();
			}
			
			Tab newPageTab = pagesTab.addTab(newPageRootlayout, pageTabTitle,
					new ExternalResource("images/site.png"));
			pagesTab.setSelectedTab(newPageParentlayout);
			newPageTab.setVisible(true);
			newPageTab.setEnabled(true);
			newPageTab.setClosable(true);
	
			// List box to select Page layouts
	
			pageLayoutService = (PageLayoutService) contextHelper
					.getBean("pageLayoutService");
			siteDto = siteService.findSiteById(siteId);
	
			int accountId = siteDto.getAccountDto().getAccountId();
			Collection<PageLayoutDto> pageLayoutDto = pageLayoutService.findPageLayoutByAccount(accountId);
			final ComboDataLoader comboDataLoader = new ComboDataLoader();
			pageLayoutCombo = new ComboBox("Select Page Layouts",
					comboDataLoader.loadDataInContainer((Collection)pageLayoutDto));
	
			Button newPageSubmitBtn = new Button(pageButtonTitle);
			//newPageFormLayout.addComponent(pageLayoutCombo);
			//newPageFormLayout.addComponent(newPageSubmitBtn);
			pageLayoutCombo.setItemCaptionMode(ComboBox.ItemCaptionMode.PROPERTY);
			pageLayoutCombo.setItemCaptionPropertyId("name");
	
	
			int gridRows = 1;
			Map<String,com.vaadin.event.MouseEvents.ClickListener> listeners = new LinkedHashMap<String,com.vaadin.event.MouseEvents.ClickListener>();
			listeners.put("PAGE:ADD",new AddPageButtonClickListener(contextHelper,titleTxt,uriTxt,checkbox1,siteDto,pageLayoutCombo,pageLayoutService, pageId,newPageDtoWithLayout,pagesTab,newPageParentlayout,this));
			
			if (null != pageId) {
				gridRows = 3;
				SEOUIManager seoUIManager = new SEOUIManager(contextHelper);
				listeners.put("SEO:VIEW",new SEOSettingsEventListener(seoUIManager, pagesTab, siteId, pageId));
				listeners.put("PAGE:ASSOCIATE_CATEGORY",new PageAssignCategoryListener(this.contextHelper, pageDto, accountId));
			}	
			
			final GridLayout toolbarGridLayout = new GridLayout(1, gridRows);
			ScreenToolbarBuilder builder = new ScreenToolbarBuilder(toolbarGridLayout,"page",listeners);
			builder.build();
	
			newPageRootlayout.addComponent(toolbarGridLayout);
			newPageRootlayout.setWidth(100,Unit.PERCENTAGE);
			
			newPageRootlayout.setExpandRatio(toolbarGridLayout, 1);
			newPageRootlayout.setExpandRatio(newPageParentlayout, 14);
	
			newPageSubmitBtn.addClickListener(new ClickListener() {
				private static final long serialVersionUID = 1L;
	
				public void buttonClick(ClickEvent event) {
					PageService pageService = (PageService) contextHelper
							.getBean("pageService");
					PageDto pageDto = new PageDto();
					pageDto.setTitle(titleTxt.getValue().toString());
					pageDto.setUri(uriTxt.getValue().toString());
					pageDto.setIsNavigable((checkbox1.getValue())?1:0);
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
	
		try {
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
	
					Notification.show(notificationMsg);
					}
					catch(final EntityAlreadyFoundException e){
						Notification.show("Page already exists with this title or uri",Notification.Type.ERROR_MESSAGE);
					} catch(final PageCannotCreateException e) {
						Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
					}
					catch (final AuthorizationException ae) {
						LOGGER.debug("User do not have permission [PAGE:VIEW]");
						Notification.show("Unauthorized", "You do not have permissions to perform this operation", Notification.Type.TRAY_NOTIFICATION);
					}
				}
	
			});
	
			// Call for editing
			if (null != pageId) {
				newPageParentlayout.addComponent(populatePage(pageDto,
						newPageFormLayout));
			}
	}
	catch (final AuthorizationException ae) {
		LOGGER.debug("User do not have permission [PAGE:VIEW]");
		Notification.show("Unauthorized", "You do not have permissions to perform this operation", Notification.Type.TRAY_NOTIFICATION);
	}
	
		
	}

	public void addPageToPageListTable(final PageDto page,
			final Integer siteId, final TabSheet pagesTab, Button link) {

		Item item = container.addItem(page.getPageId());
		item.getItemProperty("Title").setValue(page.getTitle());
		item.getItemProperty("Uri").setValue(page.getUri());
		
		if (SecurityUtils.getSubject().isPermitted("PAGE:EDIT")){
			link = new Button();
			link.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;
	
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
	}

	public void addEmpytPageTable(final Table table) {

		Item item = container.addItem("-1");
		item.getItemProperty("Title").setValue("No page found");
		item.getItemProperty("Uri").setValue("");
		table.setContainerDataSource(container);
	}

	
	/**
	 * Renders the page layouts. A PageDto is passed as a parameter so that
	 * associated layouts to a page can be rendered.
	 * 
	 * @param pageDtoWithLayout
	 */
	public TabSheet renderPageLayouts(PageDto pageDtoWithLayout) {

		pageLayoutsTab = new TabSheet();
		if (SecurityUtils.getSubject().isPermitted("PAGE:ASSOCIATE_TEMPLATE")){
			final PageLayoutDto layoutDto = pageDtoWithLayout.getPageLayoutDto();
			pageLayoutsTab.setHeight(10,Unit.PERCENTAGE);
			pageLayoutsTab.setWidth(100,Unit.PERCENTAGE);
	
			PageTemplateDto pageTemplateDto = new PageTemplateDto();
			//pageTemplateDto.setPageId(selectedPageId);
			pageTemplateDto.setPageId(pageDtoWithLayout.getPageId());
			UI.getCurrent().setData(pageTemplateDto);
	
			//creating associated template table
			Table templateTable = new Table();
			final AbstractTableBuilder templateTableBuilder = new PageTemplateTableBuilder(contextHelper, templateTable);
			
			
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
					pageLayoutsTab.setHeight(100,Unit.PERCENTAGE);
					pageLayoutsTab.setWidth(100,Unit.PERCENTAGE);
	
					renderPageSection(pageLayoutsTab, pageSectionLayout,
							pageSectionIterator.next(), pageTemplateDto,templateTableBuilder,templateTable);
					pageSectionLayout.addComponent(templateTable);
				}
			}
			// otherwise add a section to add layout based on a template.
			else {
				final VerticalLayout pageSectionLayout = new VerticalLayout();
				pageSectionLayout.setWidth(100,Unit.PERCENTAGE);
				pageSectionLayout.setSpacing(true);
				Tab tab = pageLayoutsTab.addTab(pageSectionLayout, "Custom Layout", new ExternalResource("images/site.png"));
				tab.setClosable(false);
				
				pageSectionLayout.addComponent(new PageTemplateAssignmentPopup("Open", contextHelper,templateTableBuilder));
				renderPageTemplateList(pageSectionLayout,PageSectionTypeEnum.CUSTOM,templateTableBuilder,templateTable);
				pageSectionLayout.addComponent(templateTable);
			}
		}
		else {
			pageLayoutsTab.setVisible(false);
		}
		return pageLayoutsTab;
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void renderPageTemplateList(final VerticalLayout pageSectionLayout,
			final PageSectionTypeEnum sectionType,final AbstractTableBuilder templateTableBuilder,final Table templateTable) {
		PageTemplateService pageTemplateService = (PageTemplateService) contextHelper.getBean("pageTemplateService");
		PageSectionTypeService pageSectionTypeService = (PageSectionTypeService) contextHelper.getBean("pageSectionTypeService");
		PageSectionTypeDto sectionTypeDto = pageSectionTypeService.findByName(sectionType);
		
		if (SecurityUtils.getSubject().isPermitted("PAGE:ASSOCIATE_TEMPLATE")){
			final Collection<PageTemplateDto> newPageTemplates = pageTemplateService.findByPageAndPageSectionType(selectedPageId,sectionTypeDto.getId());
		
			//adding associated template item to table
			if (!CollectionUtils.isEmpty(newPageTemplates)){
				templateTableBuilder.build((Collection)newPageTemplates);
			}
		}
	}


	public void renderPageSection(final TabSheet pageLayoutsTab,
			final VerticalLayout pageSectionLayout,
			final PageSectionDto pageSectionDto,
			final PageTemplateDto pageTemplateDto,
			final AbstractTableBuilder templateTableBuilder,
			final Table templateTable) {
		if (SecurityUtils.getSubject().isPermitted("PAGE:ASSOCIATE_TEMPLATE")){
			Tab tab = pageLayoutsTab.addTab(pageSectionLayout, pageSectionDto.getSectionTypeDto().getName(), new ExternalResource("images/site.png"));
			tab.setClosable(true);

			pageTemplateDto.setSectionTypeId(pageSectionDto.getSectionTypeDto().getId());
			PageSectionTypeService pageSectionTypeService = (PageSectionTypeService) contextHelper.getBean("pageSectionTypeService");
			PageSectionTypeDto sectionTypeDto = pageSectionTypeService.findById(pageSectionDto.getSectionTypeDto().getId());
	
			pageSectionLayout.addComponent(new PageTemplateAssignmentPopup("Open", contextHelper,templateTableBuilder));
			renderPageTemplateList(pageSectionLayout,PageSectionTypeEnum.valueOf(sectionTypeDto.getName()),templateTableBuilder,templateTable);
		}
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

		((TextField) newPageFormLayout.getComponent(0)).setValue(pageDto
				.getTitle());
		((TextField) newPageFormLayout.getComponent(1)).setValue(pageDto
				.getUri());
		Boolean checkboxValue = (pageDto.getIsNavigable() == null) ? false : true; 
		((CheckBox) newPageFormLayout.getComponent(2)).setValue(checkboxValue); 

		// This will be used to be passed to the template assignment sub window
		selectedPageId = pageDto.getPageId();

//		if (null != pageDto.getPageLayoutDto()) {
//			((ComboBox) newPageFormLayout.getComponent(2)).select(pageDto
//					.getPageLayoutDto().getId());
//		}
		return renderPageLayouts(pageDto);
	}

}
