package com.contento3.web.site;

import java.util.ArrayList;
import java.util.List;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountService;
import com.contento3.cms.page.service.PageService;
import com.contento3.cms.site.structure.domain.dto.SiteDomainDto;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.web.UIManager;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.common.helper.TextFieldRendererHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Used to render ui related to sites and site pages.
 * @author HAMMAD
 * 
 */
public class SiteUIManager implements UIManager {

	public static final String NEWSITE = "newsite";
	public static final String SITEDASHBOARD = "pages";
	public static final String NEWPAGE = "newpage";

	/**
	 * Used to get spring beans.  
	 */
	private SpringContextHelper contextHelper;
	
	/**
	 * Application parent window that holds all the screens
	 */
	private Window parentWindow;
	
	/**
	 * Service layer that use to provide functionality related to site.
	 */
	private SiteService siteService;

	/**
	 * Service layer that use to provide functionality related to Account.
	 */
	private AccountService accountService;
	
	/**
	 * Service layer that use to provide functionality related to Page.
	 */
	private PageService pageService;
	
	/**
	 * UI Manager that renders page related screens
	 */
	private PageUIManager pageUIManager;
	
	/**
	 * UI Manager that renders site configuration related screens
	 */
	private SiteConfigUIManager siteConfigUIManager;
	
	public SiteUIManager(final SpringContextHelper helper,final Window parentWindow) {
		this.contextHelper = helper;
		this.parentWindow = parentWindow;
		this.siteService = (SiteService) contextHelper.getBean("siteService");
		this.pageService = (PageService) contextHelper.getBean("pageService");
		this.accountService = (AccountService) contextHelper.getBean("accountService");
	}
	
	@Override
	public void render() {

	}

	@Override
	public Component render(final String command) {
		Component componentToReturn = null;
		if(command == null){
				SitesDashBoard sitesDashBoard = new SitesDashBoard(contextHelper,parentWindow);
				componentToReturn = sitesDashBoard.render(null);
		}
		else if (command.equals(NEWSITE)) {
			TabSheet newsiteTab = new TabSheet();
			newsiteTab = new TabSheet();
			newsiteTab.setHeight("675");
			newsiteTab.setWidth("775");
			VerticalLayout layout = renderNewSite();
			newsiteTab.addComponent(layout);
			Tab tab1 = newsiteTab.addTab(layout, "Create site", null);
			tab1.setClosable(true);
			componentToReturn = newsiteTab;
		}
		return componentToReturn;
	}

	/**
	 * Renders the screen based on command passed.
	 */
	public Component render(final String command,final Integer id) {
		Component componentToReturn = null;
		if (command.equals(SITEDASHBOARD)) {
			componentToReturn = renderSiteDashboard(id);
		}
		return componentToReturn;
	}

	/**
	 * Renders the SiteDashboard.
	 * Currently, this displays a table with a page.
	 * @param siteId
	 * @return Component
	 */
	public Component renderSiteDashboard(final Integer siteId){
		final HorizontalLayout horizontalLayout = new HorizontalLayout();
		final TabSheet pagesTab = new TabSheet();
		renderButtons(horizontalLayout,siteId,pagesTab);
		pageUIManager = new PageUIManager(siteService,pageService,contextHelper,parentWindow);
		return pageUIManager.renderPageListing(siteId,pagesTab,horizontalLayout);
	}
	
	/**
	 * Renders the buttons that are 
	 * displayed on the screen.
	 * @param horizontalLayout
	 * @param siteId
	 * @param pagesTab
	 */
	public void renderButtons(final HorizontalLayout horizontalLayout,final Integer siteId,final TabSheet pagesTab){
		// Button that when clicked rendered a new page tab.
		final Button newPageButton = new Button("Create new page");
		horizontalLayout.addComponent(newPageButton);
		newPageButton.addListener(new ClickListener() {
		private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				pageUIManager.renderNewPage(siteId, pagesTab, null);
			}
		});

		// Button for site configuration
		final String buttonText = "Site Config";
		final Button siteConfigButton = new Button(buttonText);
		horizontalLayout.addComponent(siteConfigButton);
		siteConfigUIManager = new SiteConfigUIManager(siteService,contextHelper,parentWindow);
		siteConfigButton.addListener(new ClickListener() {
		private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				siteConfigUIManager.renderSiteConfig(siteId, pagesTab, null);
			}
		});
			
		final Button addNewCategoryButton = new Button("Add New Category");
		horizontalLayout.addComponent(addNewCategoryButton);
		addNewCategoryButton.addListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				CategoryTreeRender categoryTree = new CategoryTreeRender(contextHelper, parentWindow);
				categoryTree.renderTreeToAddNewCategory(siteId, pagesTab, null);
				}
		});
	}
	
	/**
	 * Used to render a screen (tab) for creating a new site
	 */
	public VerticalLayout renderNewSite() {
		// Create a new layout and add as a the main component for the new site
		// tab
		final VerticalLayout newSiteInputLayout = new VerticalLayout();

		// Now add the input controls to get the new site,
		final TextFieldRendererHelper txtHelper = new TextFieldRendererHelper();
		txtHelper.addTextInputs(newSiteInputLayout, "Site Name", "Site Domain");
		txtHelper.addSubmitButton(newSiteInputLayout, "Add Site");

		((Button) newSiteInputLayout.getComponent(2)).addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				SiteDto siteDto = new SiteDto();
				siteDto.setSiteName((String) ((TextField) newSiteInputLayout.getComponent(0)).getValue());
				final AccountDto account = accountService.findAccountById((Integer)SessionHelper.loadAttribute(parentWindow,"accountId"));
				siteDto.setAccountDto(account);
				final SiteDomainDto siteDomainDto = new SiteDomainDto();
				siteDomainDto.setDomainName((String) ((TextField) newSiteInputLayout.getComponent(1)).getValue());
				final List <SiteDomainDto> siteDomains = new ArrayList<SiteDomainDto>();
				siteDomains.add(siteDomainDto);
				siteDto.setSiteDomainDto(siteDomains);
				siteService.create(siteDto);
			}
		});
		return newSiteInputLayout;
	}

	@Override
	public Component render(String command,
			HierarchicalContainer treeItemContainer) {
		// TODO Auto-generated method stub
		return null;
	}
}