package com.contento3.web.site;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.layout.dto.PageLayoutDto;
import com.contento3.cms.page.layout.service.PageLayoutService;
import com.contento3.cms.page.service.PageService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.util.CachedTypedProperties;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.ComboDataLoader;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.common.helper.ScreenHeader;
import com.contento3.web.common.helper.ScreenToolbarBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.site.listener.AddSiteDomainClickListener;
import com.contento3.web.site.listener.AddSiteLanguageClickListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

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
	
	final PageService pageService;
	
	public SiteConfigUIManager (final PageService pageService,final SiteService siteService,final SpringContextHelper helper){
		this.siteService = siteService;
		this.pageService = pageService;
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

			final ScreenHeader screenHeader = new ScreenHeader(siteConfigParentLayout,"Site Configuration");
			VerticalLayout verticl = new VerticalLayout();
			HorizontalLayout horizontl = new HorizontalLayout();
			verticl.setSpacing(true);
			//verticl.setMargin(true);
			horizontl.addComponent(verticl);
			siteConfigParentLayout.addComponent(horizontl);
			GridLayout toolbarGridLayout = new GridLayout(1,2);
			List<com.vaadin.event.MouseEvents.ClickListener> listeners = new ArrayList<com.vaadin.event.MouseEvents.ClickListener>();
			horizontl.setWidth(100, Unit.PERCENTAGE);
			horizontl.addComponent(toolbarGridLayout);
			horizontl.setExpandRatio(verticl, 8);
			horizontl.setExpandRatio(toolbarGridLayout, 1);

			renderSiteDomains(siteDto,siteTabSheet,pageId,horizontl,verticl,listeners);
			renderLayoutAndLanguage(siteDto,horizontl,verticl,listeners);
			
			ScreenToolbarBuilder builder = new ScreenToolbarBuilder(toolbarGridLayout,"siteConfig",listeners);
			builder.build();
	}// end renderSiteConfig()
	
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void renderSiteDomains(final SiteDto siteDto,final TabSheet siteTabSheet, final Integer pageId, HorizontalLayout horizontl, VerticalLayout verticl, List<com.vaadin.event.MouseEvents.ClickListener> listeners){
		
		final Label siteDomainLbl = new Label("Domains"); 
		siteDomainLbl.setStyleName("screenSubHeading");
		verticl.addComponent(siteDomainLbl);
		verticl.setSpacing(true);
		verticl.setMargin(true);
		if (null==siteDomainTable){
			siteDomainTable = new Table();
			final AbstractTableBuilder tableBuilder = new SiteDomainTableBuilder(contextHelper,siteDomainTable);
			tableBuilder.build((Collection)siteDto.getSiteDomainDto());
		}

		verticl.addComponent(siteDomainTable);
		verticl.setWidth(100, Unit.PERCENTAGE);

		final Tab siteConfigTab = siteTabSheet.addTab(siteConfigParentLayout,"",new ExternalResource("images/configuration.png"));
		siteConfigTab.setCaption("Site configuration for :"+siteDto.getSiteName());
		siteConfigTab.setClosable(true);
		
		siteTabSheet.setSelectedTab(siteConfigParentLayout);
		
		listeners.add(new AddSiteDomainClickListener(contextHelper,siteDto.getSiteId(),siteDomainTable));
//		ScreenToolbarBuilder builder = new ScreenToolbarBuilder(toolbarGridLayout,"page",listeners);
//		builder.build();
		
		//Pop-up that adds a new domain
		//final Button button = new Button("Add domain", new SiteDomainPopup(contextHelper,siteDto.getSiteId(),siteDomainTable));
		//verticl.addComponent(button);
		
		verticl.addComponent(new HorizontalRuler());
		
        
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void renderLayoutAndLanguage(final SiteDto siteDto, HorizontalLayout horizontl, VerticalLayout verticl, List<com.vaadin.event.MouseEvents.ClickListener> listeners){

		// List box to select Default Page 
		final Collection<PageDto> pagesDto = pageService.findPageBySiteId(siteDto.getSiteId());
		final ComboDataLoader comboDataLoader = new ComboDataLoader();
		final ComboBox pageCombo = new ComboBox("Default Page",comboDataLoader.loadDataInContainer((Collection)pagesDto));
		pageCombo.setItemCaptionMode(ItemCaptionMode.EXPLICIT);
		pageCombo.setItemCaptionPropertyId("name");
		pageCombo.setValue(siteDto.getDefaultPageId());

		
		// List box to select Page layouts 
		final Collection<PageLayoutDto> pageLayoutDto = pageLayoutService.findPageLayoutByAccount(siteDto.getAccountDto().getAccountId());
		final ComboBox pageLayoutCombo = new ComboBox("Default Page Layout",comboDataLoader.loadDataInContainer((Collection)pageLayoutDto));
		pageLayoutCombo.setItemCaptionMode(ItemCaptionMode.EXPLICIT);
		pageLayoutCombo.setItemCaptionPropertyId("name");
		pageLayoutCombo.setValue(siteDto.getDefaultLayoutId());

		verticl.addComponent(pageCombo);
		verticl.addComponent(pageLayoutCombo);

		verticl.setSpacing(true);
		verticl.setMargin(true);
		
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

		final ComboBox languageCombo = new ComboBox("Select Language",languages);

		languageCombo.setValue(presetLanguage);
		verticl.addComponent(languageCombo);
		verticl.setSpacing(true);
		verticl.setMargin(true);
		Label emptyLabel2 = new Label("");
		emptyLabel2.setHeight("1em");
		verticl.addComponent(emptyLabel2);
		final Button siteSaveButton = new Button("Save");
//		verticl.addComponent(siteSaveButton);
		verticl.setSpacing(true);
		verticl.setMargin(true);
		siteSaveButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				saveSite(siteDto,  pageLayoutCombo,languageCombo,pageCombo);
				Notification.show(String.format("SiteConfig for %s saved successfullly",siteDto.getSiteName()));
			}// end buttonClick
		});// end siteSaveButton listener
		
		listeners.add(new AddSiteLanguageClickListener(siteDto,  pageLayoutCombo,languageCombo,pageCombo,this));
	}
	
	/**
	 * Saves siteDto into DB
	 * 
	 * @param siteDto
	 * @param pageLayoutCombo
	 * @param languageCombo
	 */
	public void saveSite(final SiteDto siteDto,final ComboBox pageLayoutCombo,final ComboBox languageCombo,final ComboBox pageCombo) {
		
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
		
		if (null != pageCombo.getValue()) {
			siteDto.setDefaultPageId(Integer.parseInt(pageCombo.getValue().toString()));
		}	

		
		siteService.update(siteDto);
	}//end saveSite	

}
