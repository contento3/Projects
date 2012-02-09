package com.contento3.web.site;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.contento3.account.dto.AccountDto;
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
import com.contento3.web.UIManager;
import com.contento3.web.common.helper.PageTemplateAssignmentPopup;
import com.contento3.web.common.helper.TextFieldRendererHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
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

import java.util.Collections;

/**
 * Used to render ui related to sites and site pages.
 * 
 * @author HAMMAD
 * 
 */
public class SiteUIManager implements UIManager {

	private static final Logger LOGGER = Logger.getLogger(SiteUIManager.class);

	public static final String NEWSITE = "newsite";
	public static final String SITEDASHBOARD = "pages";
	public static final String NEWPAGE = "newpage";
	private SpringContextHelper contextHelper;
	private final IndexedContainer container = new IndexedContainer();
	private PageDto newPageDtoWithLayout;
	private Window parentWindow;

	private int selectedPageId;
	private boolean newPageFlag = false;

	private SiteService siteService;
	private PageService pageService;

	public SiteUIManager(final SpringContextHelper helper,
			final Window parentWindow) {
		this.contextHelper = helper;
		this.parentWindow = parentWindow;
		this.siteService = (SiteService) contextHelper.getBean("siteService");
		this.pageService = (PageService) contextHelper.getBean("pageService");
	}

	@Override
	public void render() {

	}

	@Override
	public Component render(String command) {
		Component componentToReturn = null;
		if (command.equals(NEWSITE)) {
			componentToReturn = renderNewSite();
		}
		return componentToReturn;
	}

	public Component render(String command, Integer id) {
		Component componentToReturn = null;
		if (command.equals(SITEDASHBOARD)) {
			componentToReturn = renderPage(id);
		}
		return componentToReturn;
	}

	/**
	 * Used to render a screen (tab) for creating a new site
	 */
	private TabSheet renderNewSite() {
		TabSheet newsiteTab = new TabSheet();

		newsiteTab = new TabSheet();
		newsiteTab.setHeight("675");
		newsiteTab.setWidth("775");

		// Create a new layout and add as a the main component for the new site
		// tab
		final VerticalLayout newSiteInputLayout = new VerticalLayout();
		newsiteTab.addComponent(newSiteInputLayout);

		//
		Tab tab1 = newsiteTab.addTab(newSiteInputLayout, "Create site", null);
		tab1.setClosable(true);

		// Now add the input controls to get the new site,
		TextFieldRendererHelper txtHelper = new TextFieldRendererHelper();
		txtHelper.addTextInputs(newSiteInputLayout, "Site Name", "Site Domain");
		txtHelper.addSubmitButton(newSiteInputLayout, "Add Site");

		((Button) newSiteInputLayout.getComponent(2))
				.addListener(new ClickListener() {
					private static final long serialVersionUID = 1L;

					public void buttonClick(ClickEvent event) {
						SiteDto siteDto = new SiteDto();
						siteDto.setSiteName((String) ((TextField) newSiteInputLayout
								.getComponent(0)).getValue());
						siteDto.setUrl((String) ((TextField) newSiteInputLayout
								.getComponent(1)).getValue());

						// TODO this need to be changed to get the accountid
						// from the session.
						AccountDto account = new AccountDto();
						account.setAccountId(1);
						account.setName("test account");
						siteDto.setAccountDto(account);
						siteService.create(siteDto);
					}
				});

		return newsiteTab;
	}

	final TabSheet pagesTab = new TabSheet();

	private TabSheet renderPage(final Integer siteId) {
		final Table table = new Table("Site pages");
		table.setImmediate(true);

		// Create a new layout and add as a the main component for the new site
		// tab
		final VerticalLayout pageLayout = new VerticalLayout();
		final HorizontalLayout horizontalLayout = new HorizontalLayout();
		pagesTab.addComponent(pageLayout);
		pagesTab.setHeight("675");
		pagesTab.setWidth("775");

		SiteDto siteDto = siteService.findSiteById(siteId);

		final Tab tab1 = pagesTab.addTab(pageLayout, siteDto.getSiteName(),
				null);
		// tab1.setCaption("test");
		pagesTab.setImmediate(true);
		pagesTab.addListener(new SelectedTabChangeListener() {
			private static final long serialVersionUID = 1L;

			public void selectedTabChange(SelectedTabChangeEvent event) {
				event.getTabSheet().getSelectedTab().requestRepaint();
				table.requestRepaint();
			}
		});
		//

		// Button that when clicked rendered a new page tab.
		Button newPageButton = new Button("Create new page");
		horizontalLayout.addComponent(newPageButton);
		newPageButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				renderNewPage(siteId, pagesTab, null);
			}
		});

		// Button for site configuration
		String buttonText = "Site Config";
		Button siteConfigButton = new Button(buttonText);
		horizontalLayout.addComponent(siteConfigButton);
		siteConfigButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				renderSiteConfig(siteId, pagesTab, null);
			}
		});
		pageLayout.addComponent(horizontalLayout);
		final PageService pageService = (PageService) contextHelper
				.getBean("pageService");
		final Collection<PageDto> pageDtos = pageService
				.getPageBySiteId(siteId);

		if (!CollectionUtils.isEmpty(pageDtos)) {
			container.addContainerProperty("Title", String.class, null);
			container.addContainerProperty("Uri", String.class, null);
			container.addContainerProperty("Edit", Button.class, null);

			table.setWidth(100, Sizeable.UNITS_PERCENTAGE);

			table.setPageLength(25);
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
	 * Used to render a tab to configure site.This includes selecting layout for
	 * page and other site related information.
	 * 
	 * @param accountId
	 * @param tabSheet
	 */

	private void renderSiteConfig(final Integer siteId,
			final TabSheet tabSheet, final Integer pageId) {

		final SiteDto siteDto = siteService.findSiteById(siteId);
		VerticalLayout verticalLayout = new VerticalLayout();
		final FormLayout newPageFormLayout = new FormLayout();
		verticalLayout.addComponent(newPageFormLayout);

		final String siteNameTxt = siteDto.getSiteName();
		Label siteNameLabel = new Label("Site Name: " + "<b>" + siteNameTxt + "</b>");
		siteNameLabel.setContentMode(Label.CONTENT_XML);
		
		final TextField domainTxt = new TextField();
		domainTxt.setCaption("Domain"); 
		
		newPageFormLayout.addComponent(siteNameLabel);
		newPageFormLayout.addComponent(domainTxt);

		String pageTabTitle = "Site Config: [" + siteNameTxt + "]";
		String saveButtonTitle = "Save";
		Tab newPageTab = tabSheet.addTab(verticalLayout, pageTabTitle, null);
		tabSheet.setSelectedTab(verticalLayout);
		newPageTab.setVisible(true);
		newPageTab.setEnabled(true);
		newPageTab.setClosable(true);

		// List box to select Page layouts
		final PageLayoutService pageLayoutService = (PageLayoutService) contextHelper
				.getBean("pageLayoutService");

		Collection<PageLayoutDto> pageLayoutDto = pageLayoutService
				.findPageLayoutByAccount(siteDto.getAccountDto().getAccountId());
		final ComboBox pageLayoutCombo = new ComboBox("Select Page Layouts",
				getPageLayouts(pageLayoutDto));

		Button siteSaveButton = new Button(saveButtonTitle);
		newPageFormLayout.addComponent(pageLayoutCombo);
		newPageFormLayout.addComponent(siteSaveButton);
		pageLayoutCombo.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
		pageLayoutCombo.setItemCaptionPropertyId("name");
		pageLayoutCombo.setValue(siteDto.getDefaultLayoutId());

		siteSaveButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			AccountDto accountDto = siteDto.getAccountDto();
			public void buttonClick(ClickEvent event) {
				if (null != pageLayoutCombo.getValue()) {
					siteDto.setSiteId(siteId);
					siteDto.setSiteName(siteNameTxt);
					siteDto.setUrl(siteDto.getUrl());
					siteDto.setAccountDto(accountDto);
					siteDto.setDefaultLayoutId(	pageLayoutService
							.findPageLayoutById(Integer
									.parseInt(pageLayoutCombo.getValue()
											.toString()))
												.getId());
					
					siteService.update(siteDto);
					
					
				}//end if
			}//end buttonClick

		});//end siteSaveButton listener

		
		
	}//end renderSiteConfig()

	/**
	 * Used to render a tab to create a new page.This includes selecting layout
	 * for page and other page related information.
	 * 
	 * @param accountId
	 * @param tabSheet
	 */
	public void renderNewPage(final Integer siteId, final TabSheet tabSheet,
			final Integer pageId) {
		final VerticalLayout newPageParentlayout = new VerticalLayout();

		final FormLayout newPageFormLayout = new FormLayout();
		newPageParentlayout.addComponent(newPageFormLayout);
		final TextField titleTxt = new TextField();
		titleTxt.setCaption("Title");

		final TextField uriTxt = new TextField();
		uriTxt.setCaption("Uri");

		newPageFormLayout.addComponent(titleTxt);
		newPageFormLayout.addComponent(uriTxt);

		// TODO get it from a property file
		String pageTabTitle = "Untitled page";
		String pageButtonTitle = "Add page";
		PageDto pageDto = null;

		if (null != pageId) {

			try {
				pageDto = pageService.findPageWithLayout(pageId);
			} catch (PageNotFoundException e) {
				LOGGER.equals(String.format("Page not found %s", pageId));
			}

			pageTabTitle = String.format("Edit %s", pageDto.getTitle());
			pageButtonTitle = "Save";
		}

		Tab newPageTab = tabSheet.addTab(newPageParentlayout, pageTabTitle,
				null);
		tabSheet.setSelectedTab(newPageParentlayout);
		newPageTab.setVisible(true);
		newPageTab.setEnabled(true);
		newPageTab.setClosable(true);

		// List box to select Page layouts

		final PageLayoutService pageLayoutService = (PageLayoutService) contextHelper
				.getBean("pageLayoutService");
		final SiteDto siteDto = siteService.findSiteById(siteId);

		Collection<PageLayoutDto> pageLayoutDto = pageLayoutService
				.findPageLayoutByAccount(siteDto.getAccountDto().getAccountId());
		final ComboBox pageLayoutCombo = new ComboBox("Select Page Layouts",
				getPageLayouts(pageLayoutDto));

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

				if (null != pageLayoutCombo.getValue()) {
					pageDto.setPageLayoutDto(pageLayoutService
							.findPageLayoutById(Integer
									.parseInt(pageLayoutCombo.getValue()
											.toString())));
				}

				// Create a new page,get page dto with its layout.
				newPageDtoWithLayout = pageService.createAndReturn(pageDto);
				parentWindow.showNotification(String.format(
						"Page %s added successfullly",
						newPageDtoWithLayout.getTitle()));
				addPageToPageListTable(newPageDtoWithLayout, siteId, pagesTab,
						new Button());

				// Render the page layout by splitting them with page sections
				// and add them to the parent layout i.e. VerticalLayout
				newPageParentlayout
						.addComponent(renderPageLayouts(newPageDtoWithLayout));
			}
		});

		// Call for editing
		if (null != pageId) {
			newPageParentlayout.addComponent(populatePage(pageDto,
					newPageFormLayout));
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

		// This will be used to be passed to the template assignment sub window
		selectedPageId = pageDto.getPageId();

		if (null != pageDto.getPageLayoutDto()) {
			((ComboBox) newPageFormLayout.getComponent(2)).select(pageDto
					.getPageLayoutDto().getId());
		}
		return renderPageLayouts(pageDto);
	}

	TabSheet pageLayoutsTab;

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
		pageTemplateDto.setPageId(selectedPageId);
		parentWindow.setData(pageTemplateDto);

		// If there are layout with page sections then add it
		if (null != layoutDto) {
			final List<PageSectionDto> pageSections = (List<PageSectionDto>) layoutDto
					.getPageSections();

			Collections.sort(pageSections);
			final Iterator<PageSectionDto> pageSectionIterator = pageSections
					.iterator();

			while (pageSectionIterator.hasNext()) {
				final VerticalLayout pageSectionLayout = new VerticalLayout();
				pageLayoutsTab.addComponent(pageSectionLayout);
				renderPageSection(pageLayoutsTab, pageSectionLayout,
						pageSectionIterator.next(), pageTemplateDto);
			}
		}
		// otherwise add a section to add layout based on a template.
		else {
			final VerticalLayout pageSectionLayout = new VerticalLayout();
			pageLayoutsTab.addTab(pageSectionLayout, "Custom Layout", null);
			renderPageTemplateList(pageSectionLayout,
					PageSectionTypeEnum.CUSTOM);
			pageSectionLayout.addComponent(new PageTemplateAssignmentPopup(
					"Open", parentWindow, contextHelper));
		}
		return pageLayoutsTab;
	}

	public void renderPageTemplateList(final VerticalLayout pageSectionLayout,
			final PageSectionTypeEnum sectionType) {
		PageTemplateService pageTemplateService = (PageTemplateService) contextHelper
				.getBean("pageTemplateService");
		PageSectionTypeService pageSectionTypeService = (PageSectionTypeService) contextHelper
				.getBean("pageSectionTypeService");
		PageSectionTypeDto sectionTypeDto = pageSectionTypeService
				.findByName(sectionType);
		Collection<PageTemplateDto> newPageTemplates = pageTemplateService
				.findByPageAndPageSectionType(selectedPageId,
						sectionTypeDto.getId());

		for (PageTemplateDto dto : newPageTemplates) {
			Label label = new Label(dto.getTemplateName());
			pageSectionLayout.addComponent(label);
		}

	}

	public void renderPageSection(final TabSheet pageLayoutsTab,
			final VerticalLayout pageSectionLayout,
			final PageSectionDto pageSectionDto,
			final PageTemplateDto pageTemplateDto) {
		pageLayoutsTab.addTab(pageSectionLayout, pageSectionDto
				.getSectionTypeDto().getName(), null);
		pageTemplateDto.setSectionTypeId(pageSectionDto.getSectionTypeDto()
				.getId());
		PageSectionTypeService pageSectionTypeService = (PageSectionTypeService) contextHelper
				.getBean("pageSectionTypeService");
		PageSectionTypeDto sectionTypeDto = pageSectionTypeService
				.findById(pageSectionDto.getSectionTypeDto().getId());

		renderPageTemplateList(pageSectionLayout,
				PageSectionTypeEnum.valueOf(sectionTypeDto.getName()));
		pageSectionLayout.addComponent(new PageTemplateAssignmentPopup("Open",
				parentWindow, contextHelper));
	}

	// private void addPageTemplates(final VerticalLayout pageSectionLayout) {
	// Object pageTemplates = parentWindow.getData();
	// Panel panel = null;
	// if (pageTemplates!=null && pageTemplates instanceof Collection){
	// ArrayList<PageTemplateDto> pt =
	// (ArrayList<PageTemplateDto>)pageTemplates;
	// for (PageTemplateDto pageTemplate : pt){
	// panel = new Panel();
	// panel.setCaption(pageTemplate.getTemplateName());
	// panel.addComponent(panel);
	// }
	// pageSectionLayout.addComponent(panel);
	// }
	// }

	/**
	 * Returns a Container with all the pageLayout.
	 * 
	 * @param pageLayoutList
	 * @return
	 */
	private IndexedContainer getPageLayouts(
			final Collection<PageLayoutDto> pageLayoutList) {
		IndexedContainer container = new IndexedContainer();
		container.addContainerProperty("name", String.class, null);
		container.addContainerProperty("value", String.class, null);

		Iterator<PageLayoutDto> pageLayoutIterator = pageLayoutList.iterator();

		while (pageLayoutIterator.hasNext()) {
			PageLayoutDto pageLayoutDto = pageLayoutIterator.next();
			Item pageLayoutItem = container.addItem(pageLayoutDto.getId());
			System.out.println("page layout:" + pageLayoutDto.getId());
			pageLayoutItem.getItemProperty("name").setValue(
					pageLayoutDto.getName());
			pageLayoutItem.getItemProperty("value").setValue(
					pageLayoutDto.getId());
		}

		container.sort(new Object[] { "name" }, new boolean[] { true });
		return container;
	}

	@Override
	public Component render(String command,
			HierarchicalContainer treeItemContainer) {
		// TODO Auto-generated method stub
		return null;
	}

}
