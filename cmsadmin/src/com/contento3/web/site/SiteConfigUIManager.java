package com.contento3.web.site;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.contento3.cms.page.layout.dto.PageLayoutDto;
import com.contento3.cms.page.layout.service.PageLayoutService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.util.CachedTypedProperties;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.ComboDataLoader;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.common.helper.ScreenHeader;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.Select;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class SiteConfigUIManager {
	
	private static final Logger LOGGER = Logger.getLogger(SiteConfigUIManager.class);

	/**
	 * Site Service to find site related information
	 */
	private SiteService siteService;

	/**
	 * Helper use to load spring beans
	 */
	final SpringContextHelper contextHelper;
	
	/**
	 * Application ui window that contains all the ui
	 */
	final Window parentWindow;

	/**
	 * 	Layout that contains all the elements on 
	 * the site configuration screen
	 */
	VerticalLayout siteConfigParentLayout;

	/**
	 * Table that holds site domains
	 */
	Table siteDomainTable;
	
	/**
	 * PageLayoutService
	 */
	final PageLayoutService pageLayoutService;
	
	public SiteConfigUIManager (final SiteService siteService,final SpringContextHelper helper,final Window parentWindow){
		this.siteService = siteService;
		this.parentWindow = parentWindow;
		this.contextHelper = helper;
		this.pageLayoutService = (PageLayoutService) contextHelper.getBean("pageLayoutService");
		
	}

	/**
	 * Used to render a tab to configure site.This includes selecting layout for
	 * page and other site related information.
	 * 
	 * @param accountId
	 * @param tabSheet
	 * @throws ClassNotFoundException 
	 */
	public void renderSiteConfig(final Integer siteId,final TabSheet siteTabSheet, final Integer pageId) {
			final SiteDto siteDto = siteService.findSiteById(siteId);
			
			siteConfigParentLayout = new VerticalLayout();
			siteConfigParentLayout.setSpacing(true);
			siteConfigParentLayout.setMargin(true);
			
			renderSiteDomains(siteDto,siteTabSheet,pageId);
			renderLayoutAndLanguage(siteDto);
	}// end renderSiteConfig()

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void renderSiteDomains(final SiteDto siteDto,final TabSheet siteTabSheet, final Integer pageId){
		final ScreenHeader screenHeader = new ScreenHeader(siteConfigParentLayout,"Site Configuration");
		final Label siteDomainLbl = new Label("Domains"); 
		siteDomainLbl.setStyleName("screenSubHeading");
		siteConfigParentLayout.addComponent(siteDomainLbl);

		if (null==siteDomainTable){
			siteDomainTable = new Table();
			final AbstractTableBuilder tableBuilder = new SiteDomainTableBuilder(parentWindow,contextHelper,siteDomainTable);
			tableBuilder.build((Collection)siteDto.getSiteDomainDto());
		}

		siteConfigParentLayout.addComponent(siteDomainTable);
		siteConfigParentLayout.setWidth(100, Sizeable.UNITS_PERCENTAGE);

		final Tab siteConfigTab = siteTabSheet.addTab(siteConfigParentLayout,"",new ExternalResource("images/configuration.png"));
		siteConfigTab.setCaption("Site configuration for :"+siteDto.getSiteName());
		siteConfigTab.setClosable(true);
		
		siteTabSheet.setSelectedTab(siteConfigParentLayout);
		
		//Pop-up that adds a new domain
		final Button button = new Button("Add domain", new SiteDomainPopup(parentWindow, contextHelper,siteDto.getSiteId(),siteDomainTable), "openButtonClick");
		siteConfigParentLayout.addComponent(button);
        siteConfigParentLayout.addComponent(new HorizontalRuler());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void renderLayoutAndLanguage(final SiteDto siteDto){
		// List box to select Page layouts
		final Collection<PageLayoutDto> pageLayoutDto = pageLayoutService.findPageLayoutByAccount(siteDto.getAccountDto().getAccountId());
		final ComboDataLoader comboDataLoader = new ComboDataLoader();
		final ComboBox pageLayoutCombo = new ComboBox("Default Page Layout",comboDataLoader.loadDataInContainer((Collection)pageLayoutDto));
		pageLayoutCombo.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
		pageLayoutCombo.setItemCaptionPropertyId("name");
		pageLayoutCombo.setValue(siteDto.getDefaultLayoutId());
		
		final VerticalLayout siteDefaultLayoutLanguageLayout = new VerticalLayout();
		siteDefaultLayoutLanguageLayout.addComponent(pageLayoutCombo);
		siteConfigParentLayout.addComponent(siteDefaultLayoutLanguageLayout);
		
		/* Reading CachedTypedProperties file :"language.propeties" */
		final Collection<String> languages = new ArrayList<String>();
		String presetLanguage = "";
		
		try {
			final CachedTypedProperties languageProperties = CachedTypedProperties.getInstance("languages.properties");
			final Collection<Object> langProperties = languageProperties.keySet();
			for (Object t : langProperties) {
				languages.add(t.toString());
				if (languageProperties.get(t).equals(siteDto.getLanguage())) {
					presetLanguage = t.toString();
				}
			}
		} catch (ClassNotFoundException e) {
			LOGGER.error("Unable to read languages.properties,Reason:"+e);
		}

		final ComboBox languageCombo = new ComboBox("Select Language",
				languages);

		languageCombo.setValue(presetLanguage);
		siteConfigParentLayout.addComponent(languageCombo);

		final Button siteSaveButton = new Button("Save");
		siteConfigParentLayout.addComponent(siteSaveButton);

		siteSaveButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				saveSite(siteDto,  pageLayoutCombo,languageCombo);
				parentWindow.showNotification(String.format("SiteConfig for %s saved successfullly",siteDto.getSiteName()));
			}// end buttonClick
		});// end siteSaveButton listener
	}
	
	/**
	 * Saves siteDto into DB
	 * 
	 * @param siteDto
	 * @param pageLayoutCombo
	 * @param languageCombo
	 */
	private void saveSite(final SiteDto siteDto,final ComboBox pageLayoutCombo,final ComboBox languageCombo) {
		
		
		if(languageCombo.getValue()!=null){
			final String lang = languageCombo.getValue().toString();
			CachedTypedProperties languageProperties;
			try {
				languageProperties = CachedTypedProperties.getInstance("languages.properties");
				siteDto.setLanguage(languageProperties.getProperty(lang));
			} catch (ClassNotFoundException e) {
				LOGGER.error(String.format("Unable to read value for key [%s] from language.properties",lang));
			}
		}
		
		if (null != pageLayoutCombo.getValue()) {
			siteDto.setDefaultLayoutId(pageLayoutService.findPageLayoutById(Integer.parseInt(pageLayoutCombo.getValue().toString())).getId());
		}	
		siteService.update(siteDto);
	}//end saveSite	

}
