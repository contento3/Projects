package com.contento3.web.site;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountService;
import com.contento3.cms.page.service.PageService;
import com.contento3.cms.site.structure.domain.dto.SiteDomainDto;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.web.UIManager;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.common.helper.ScreenToolbarBuilder;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.common.helper.TextFieldRendererHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.site.listener.ContentAssignerEventListener;
import com.contento3.web.site.listener.CreatePageEventListener;
import com.contento3.web.site.listener.SEOSettingsEventListener;
import com.contento3.web.site.listener.SiteConfigurationEventListener;
import com.contento3.web.site.seo.SEOUIManager;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

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

	/**
	 * UI tabsheet
	 */
	private TabSheet uiTabSheet;

	public SiteUIManager(final TabSheet uiTabSheet,final SpringContextHelper helper) {
		this.contextHelper = helper;
		this.siteService = (SiteService) contextHelper.getBean("siteService");
		this.pageService = (PageService) contextHelper.getBean("pageService");
		this.accountService = (AccountService) contextHelper.getBean("accountService");
		this.uiTabSheet = uiTabSheet;
	}

	@Override
	public void render() {

	}

	@Override
	public Component render(final String command) {
		Component componentToReturn = null;
		uiTabSheet.setHeight(100,Unit.PERCENTAGE);
		uiTabSheet.setWidth(100,Unit.PERCENTAGE);
		if(command == null){
				SitesDashBoard sitesDashBoard = new SitesDashBoard(uiTabSheet,contextHelper);
				componentToReturn = sitesDashBoard.render(null);
		}
//		else if (command.equals(NEWSITE)) {
//			VerticalLayout layout = renderNewSite();
//			uiTabSheet.addComponent(layout);
//			Tab tab1 = uiTabSheet.addTab(layout, "Create site", new ExternalResource("images/site.png"));
//			tab1.setClosable(true);
//			componentToReturn = uiTabSheet;
//		}
		return componentToReturn;
	}

	/**
	 * Renders the screen based on command passed.
	 **/
	
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
		
		pageUIManager = new PageUIManager(siteService,pageService,contextHelper);
		SiteContentAssignmentUIManager siteContentUIManager = new SiteContentAssignmentUIManager(uiTabSheet,contextHelper);

		GridLayout grid = new GridLayout(1, 4);
		grid.addStyleName("bordertest");
		
		siteConfigUIManager = new SiteConfigUIManager(pageService,siteService,contextHelper);
		SEOUIManager seoUIManager = new SEOUIManager(contextHelper);
		
		final Map<String,com.vaadin.event.MouseEvents.ClickListener> listeners = new LinkedHashMap<String,com.vaadin.event.MouseEvents.ClickListener>();
		listeners.put("PAGE:ADD",new CreatePageEventListener(pageUIManager,uiTabSheet,siteId));
		listeners.put("SITE_CONFIG:VIEW",new SiteConfigurationEventListener(siteConfigUIManager, uiTabSheet, siteId));
		listeners.put("SITE_CONTENT:VIEW",new ContentAssignerEventListener(siteContentUIManager, siteId));
		listeners.put("SEO:VIEW",new SEOSettingsEventListener(seoUIManager, uiTabSheet, siteId));
		
		final ScreenToolbarBuilder builder = new ScreenToolbarBuilder(grid,"site",listeners);
		builder.build();
		
		final HorizontalLayout horizontalLayout = new HorizontalLayout();
		final VerticalLayout veticalLayout = new VerticalLayout();
		final VerticalLayout veticalLayout2 = new VerticalLayout();
		//final TabSheet pagesTab = new TabSheet();
		final Label heading = new Label("Site dashboard");
		heading.setStyleName("screenHeading");
		veticalLayout2.addComponent(heading);
				
		veticalLayout2.addComponent(new HorizontalRuler());
		veticalLayout2.addComponent(horizontalLayout);
		veticalLayout.setMargin(true);
		
		uiTabSheet.setHeight(100,Unit.PERCENTAGE);
		uiTabSheet.setWidth(100,Unit.PERCENTAGE);

		horizontalLayout.addComponent(veticalLayout);
		horizontalLayout.addComponent(grid);
		horizontalLayout.setWidth(100,Unit.PERCENTAGE);
		horizontalLayout.setExpandRatio(veticalLayout, 10);
		horizontalLayout.setExpandRatio(grid, 1);
		
		Component component = pageUIManager.renderPageListing(siteId,uiTabSheet,veticalLayout2,veticalLayout);
		//renderButtons(horizontalLayout,siteId,uiTabSheet);
		
		return component;
	}

	/**
	 * Renders the buttons that are 
	 * displayed on the screen.
	 * @param horizontalLayout
	 * @param siteId
	 * @param pagesTab
	 */
	public void renderButtons(final HorizontalLayout horizontalLayout,final Integer siteId,final TabSheet pagesTab){
//		pagesTab.setHeight(100,Unit.PERCENTAGE);
//		pagesTab.setWidth(100,Unit.PERCENTAGE);

		// Button that when clicked rendered a new page tab.
		final Button newPageButton = new Button("Create page");
		horizontalLayout.addComponent(newPageButton);
		newPageButton.addClickListener(new ClickListener() {
		private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				pageUIManager.renderNewPage(siteId, pagesTab, null);
			}
		});

		// Button for site configuration
		final String buttonText = "Site Configuration";
		final Button siteConfigButton = new Button(buttonText);
		horizontalLayout.addComponent(siteConfigButton);
		siteConfigUIManager = new SiteConfigUIManager(pageService,siteService,contextHelper);
		siteConfigButton.addClickListener(new ClickListener() {
		private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				siteConfigUIManager.renderSiteConfig(siteId, pagesTab, null);
			}
		});

		//Category is moved to Left Navigation
//		final Button addNewCategoryButton = new Button("Categories");
//		horizontalLayout.addComponent(addNewCategoryButton);
//		addNewCategoryButton.addListener(new ClickListener() {
//			@Override
//			public void buttonClick(ClickEvent event) {
//				PageCategoryUIManager categoryUIManager = new PageCategoryUIManager(uiTabSheet,contextHelper,parentWindow);
//				categoryUIManager.renderCategoryList();
//			}
//		});

		final Button contentAssignmentButton = new Button("Content Assigner");
		horizontalLayout.addComponent(contentAssignmentButton);
		contentAssignmentButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				SiteContentAssignmentUIManager siteContentUIManager = new SiteContentAssignmentUIManager(uiTabSheet,contextHelper);
				siteContentUIManager.render(siteId);
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

		
		((Button) newSiteInputLayout.getComponent(2)).addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				SiteDto siteDto = new SiteDto();
				siteDto.setSiteName((String) ((TextField) newSiteInputLayout.getComponent(0)).getValue());
				final AccountDto account = accountService.findAccountById((Integer)SessionHelper.loadAttribute("accountId"));
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
		return null;
	}
}
