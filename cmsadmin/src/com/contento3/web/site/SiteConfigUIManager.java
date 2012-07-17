package com.contento3.web.site;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.contento3.account.dto.AccountDto;
import com.contento3.cms.page.layout.dto.PageLayoutDto;
import com.contento3.cms.page.layout.service.PageLayoutService;
import com.contento3.cms.page.service.PageService;
import com.contento3.cms.site.structure.domain.dto.SiteDomainDto;
import com.contento3.cms.site.structure.domain.service.SiteDomainService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.util.CachedTypedProperties;
import com.contento3.web.common.helper.ComboDataLoader;
import com.contento3.web.content.ImageMgmtUIManager;
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
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

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
	 * Container to consist of domain tables
	 */
	private IndexedContainer domainsContainer;
	
	/**
	 * Collection of @{link java.util.Collection} 
	 * SiteDomainDto 
	 */
	private Collection <SiteDomainDto> siteDomainDto;

	public SiteConfigUIManager (final SiteService siteService,final SpringContextHelper helper,final Window parentWindow){
		this.siteService = siteService;
		this.parentWindow = parentWindow;
		this.contextHelper = helper;
	}

	/**
	 * Used to render a tab to configure site.This includes selecting layout for
	 * page and other site related information.
	 * 
	 * @param accountId
	 * @param tabSheet
	 * @throws ClassNotFoundException 
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void renderSiteConfig(final Integer siteId,final TabSheet tabSheet, final Integer pageId) {
		renderSiteDomains(siteId,tabSheet,pageId);
		renderLayoutAndLanguage();
	}// end renderSiteConfig()

	public void renderSiteDomains(final Integer siteId,final TabSheet tabSheet, final Integer pageId){
//		final SiteDto siteDto = siteService.findSiteById(siteId);
//		siteDomainDto = siteDto.getSiteDomainDto();
//		domainsContainer = new IndexedContainer();
//		VerticalLayout verticalLayout = new VerticalLayout();
//		final FormLayout configSiteFormLayout = new FormLayout();
//		verticalLayout.addComponent(configSiteFormLayout);
//
//		final String siteNameTxt = siteDto.getSiteName();
//		Label siteNameLabel = new Label("Site Configuration for " + "<b>" + siteNameTxt + "</b>");
//		siteNameLabel.setContentMode(Label.CONTENT_XML);
//		configSiteFormLayout.addComponent(siteNameLabel);
//
//		final Table domainsTable = new Table();
//		domainsTable.setWidth(100, Sizeable.UNITS_PERCENTAGE);
//		domainsTable.setPageLength(siteDomainDto.size()+1);
//		final Button editButton = new Button();
//		final Button addDomainButton = new Button();
//		String saveButtonTitle = "Save";
//		final Button siteSaveButton = new Button(saveButtonTitle);
//		final Label label = new Label("No domains found for this site.");
//
//		domainsContainer.addContainerProperty("Domains", String.class, null);
//		domainsContainer.addContainerProperty("Delete", Button.class, null);
//
//		//adding rows in Domains Table from DB
//		if (!CollectionUtils.isEmpty(siteDto.getSiteDomainDto())) {
//			for (SiteDomainDto domain : siteDto.getSiteDomainDto()) {
//				Button delete = new Button();
//				addDomainsListTable(domain, domainsTable, delete, siteId);
//			}
//			domainsTable.setContainerDataSource(domainsContainer);
//			configSiteFormLayout.addComponent(domainsTable);
//			editButton.setEnabled(false);
//		} else {
//			editButton.setEnabled(false);
//			configSiteFormLayout.addComponent(label);
//			configSiteFormLayout.addComponent(domainsTable);
//			domainsTable.setContainerDataSource(domainsContainer);
//			domainsTable.setVisible(false);
//		}
//
//		HorizontalLayout horizLayout = new HorizontalLayout();
//		editButton.setCaption("Edit");
//		editButton.addStyleName("edit");
//		editButton.addListener(new Button.ClickListener() {
//
//			private static final long serialVersionUID = 1L;
//
//			public void buttonClick(ClickEvent event) {
//				domainsTable.setEditable(!domainsTable.isEditable());
//				editButton.setCaption((domainsTable.isEditable() ? "Done" : "Edit"));
//				if(editButton.getCaption().equals("Done")){
//					addDomainButton.setEnabled(false);
//				}
//				else{
//					addDomainButton.setEnabled(true);
//					parentWindow.showNotification(String.format("Domains have been changed successfully"));
//				}
//
//			}
//		});
//
//		addDomainButton.setCaption("Add Domain");
//		addDomainButton.addStyleName("add");
//
//		horizLayout.addComponent(addDomainButton);
//		horizLayout.addComponent(editButton);
//		
//		configSiteFormLayout.addComponent(horizLayout);
//		final String pageTabTitle = "Site Config: [" + siteNameTxt + "]";
//
//		final Tab newPageTab = tabSheet.addTab(verticalLayout, pageTabTitle, null);
//		tabSheet.setSelectedTab(verticalLayout);
//		newPageTab.setVisible(true);
//		newPageTab.setEnabled(true);
//		newPageTab.setClosable(true);
//
//		/* addDomainButton Listener*/
//		addDomainButton.addListener(new Button.ClickListener() {
//			public void buttonClick(ClickEvent event) {
//				int index=-1;
//				final SiteDto siteDto = siteService.findSiteById(siteId);
//				siteDomainDto = siteDto.getSiteDomainDto();
//				final Button deleteLink = new Button();
//				domainsTable.setEditable(!domainsTable.isEditable());
//
//				addDomainButton.setCaption((domainsTable.isEditable() ? "Done"
//						: "Add Domain"));
//
//
//				if(addDomainButton.getCaption().equals("Add Domain")){
//
//					editButton.setEnabled(true);
//					String domainName = (String) domainsTable
//							.getContainerProperty(index, "Domains").getValue();
//					SiteDomainDto domaindto = new SiteDomainDto();
//					domaindto.setDomainId(null);
//					domaindto.setDomainName(domainName);
//					siteDomainDto.add(domaindto);
//					//saveSiteDto(siteDto,siteDomainDto, domainsTable, pageLayoutService, pageLayoutCombo, siteNameTxt);
//					parentWindow.showNotification(String.format(
//							"Domain %s added successfullly",
//								domainName));
//				}
//				else {
//					label.setVisible(false);
//					domainsTable.setVisible(true);
//					final Item item = domainsContainer.addItem(index);
//					item.getItemProperty("Domains").setValue("Enter new domain");
//					deleteLink.setCaption("Delete");
//					deleteLink.setData(index);
//					deleteLink.addStyleName("delete");
//					deleteLink.setStyleName(BaseTheme.BUTTON_LINK);
//					item.getItemProperty("Delete").setValue(deleteLink);
//					editButton.setEnabled(false);
//
//					deleteLink.addListener(new Button.ClickListener() {
//						public void buttonClick(ClickEvent event) {
//
//							SiteDomainService siteDomainService = (SiteDomainService) contextHelper
//									.getBean("siteDomainService");
//
//							Object id = deleteLink.getData();
//							String domainName = (String) domainsTable
//									.getContainerProperty(id, "Domains")
//									.getValue();
//
//							Iterator<SiteDomainDto> itr = siteDomainDto
//									.iterator();
//							while (itr.hasNext()) {
//								SiteDomainDto dto = itr.next();
//								if (dto.getDomainName().equals(domainName)) {
//									itr.remove();
//									siteDomainService.delete(dto);
//									domainsTable.removeItem(id);
//									break;
//								}
//							}//end while()
//							parentWindow.showNotification(String.format(
//									"Domain %s deleted successfullly",
//										domainName));
//						}
//					}); //end deleteLink listener
//
//				}//end else
//			}
//		});//end addDomainButton listener
	}

	public void renderLayoutAndLanguage(){
//		// List box to select Page layouts
//		final PageLayoutService pageLayoutService = (PageLayoutService) contextHelper.getBean("pageLayoutService");
//		final Collection<PageLayoutDto> pageLayoutDto = pageLayoutService.findPageLayoutByAccount(siteDto.getAccountDto().getAccountId());
//		final ComboDataLoader comboDataLoader = new ComboDataLoader();
//		final ComboBox pageLayoutCombo = new ComboBox("Select Page Layouts",comboDataLoader.loadDataInContainer((Collection)pageLayoutDto));
//		pageLayoutCombo.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
//		pageLayoutCombo.setItemCaptionPropertyId("name");
//		pageLayoutCombo.setValue(siteDto.getDefaultLayoutId());
//		HorizontalLayout horiz = new HorizontalLayout();
//		horiz.addComponent(pageLayoutCombo);
//		configSiteFormLayout.addComponent(horiz);
//		
//		/* Reading CachedTypedProperties file :"language.propeties" */
//		final Collection<String> languages = new ArrayList<String>();
//		String presetLanguage = "";
//		try {
//			final CachedTypedProperties languageProperties = CachedTypedProperties.getInstance("languages.properties");
//			final Collection<Object> langProperties = languageProperties.keySet();
//			for (Object t : langProperties) {
//				languages.add(t.toString());
//				if (languageProperties.get(t).equals(siteDto.getLanguage())) {
//					presetLanguage = t.toString();
//				}
//			}
//		} catch (ClassNotFoundException e) {
//			LOGGER.error("Unable to read languages.properties,Reason:"+e);
//		}
//
//		final ComboBox languageCombo = new ComboBox("Select Language",
//				languages);
//
//		languageCombo.setValue(presetLanguage);
//		//configSiteFormLayout.addComponent(languageCombo);
//		horiz.addComponent(languageCombo);
//		configSiteFormLayout.addComponent(siteSaveButton);

		/* siteSaveButton Listener*/
//		siteSaveButton.addListener(new ClickListener() {
//
//			private static final long serialVersionUID = 1L;
//
//			public void buttonClick(ClickEvent event) {
//				
//				String lang = languageCombo.getValue().toString();
//				if(!lang.equals("")){
//					CachedTypedProperties languageProperties;
//					try {
//						languageProperties = CachedTypedProperties
//								.getInstance("languages.properties");
//						String val =languageProperties.getProperty(lang);
//						siteDto.setLanguage(val);
//					} catch (ClassNotFoundException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					
//				}
//				
//			//	saveSiteDto(siteDto,siteDomainDto, domainsTable, pageLayoutService, pageLayoutCombo, siteNameTxt,siteId);
//				parentWindow.showNotification(String.format(
//						"SiteConfig for %s saved successfullly",
//								siteNameTxt));
//
//			}// end buttonClick
//
//		});// end siteSaveButton listener
//		

	}

	/**
	 * Used to add domain name in container
	 * and delete rows from table
	 * @param domain
	 * @param table
	 * @param deleteLink
	 * @param siteId 
	 */
	private void addDomainsListTable(final SiteDomainDto domain,
			final Table table,final  Button deleteLink,final Integer siteId) {

		final Item item = domainsContainer.addItem(domain.getDomainId());
		item.getItemProperty("Domains").setValue(domain.getDomainName());

		deleteLink.setCaption("Delete");
		deleteLink.setData(domain.getDomainId());
		deleteLink.addStyleName("delete");
		deleteLink.setStyleName(BaseTheme.BUTTON_LINK);
		item.getItemProperty("Delete").setValue(deleteLink);

		deleteLink.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				final SiteDto siteDto = siteService.findSiteById(siteId);
				siteDomainDto = siteDto.getSiteDomainDto();
				SiteDomainService siteDomainService = (SiteDomainService) contextHelper.getBean("siteDomainService");
				Object id = deleteLink.getData();
				String domainName = (String) table.getContainerProperty(id,"Domains").getValue();
				Iterator<SiteDomainDto> itr = siteDomainDto.iterator();
				while(itr.hasNext()){
					SiteDomainDto dto = itr.next();
					if(dto.getDomainName().equals(domainName)){
						itr.remove();
						siteDomainService.delete(dto);
						table.removeItem(id);
						break;
					}
				}

			}
		});

	}

	/**
	 * save siteDto into DB
	 * 
	 * @param siteDto
	 * @param siteDomainDto
	 * @param domainsTable
	 * @param pageLayoutService
	 * @param pageLayoutCombo
	 * @param siteNameTxt
	 */

	private void saveSiteDto(final SiteDto siteDto,final Collection<SiteDomainDto> siteDomainDto, final Table domainsTable,
			final PageLayoutService pageLayoutService,
			final ComboBox pageLayoutCombo, final String siteNameTxt,final Integer siteId) {

		//Integer siteId=this.siteid;
		final AccountDto accountDto = siteDto.getAccountDto();
		Iterator<SiteDomainDto> itr= siteDomainDto.iterator();
		for (Iterator i = domainsTable.getItemIds().iterator(); i.hasNext();) {
			int iid = (Integer) i.next();
			Item item = domainsTable.getItem(iid);
			SiteDomainDto domain = itr.next();
			domain.setDomainName(item.getItemProperty("Domains").getValue().toString());
		}

		if (null != pageLayoutCombo.getValue()) {
			siteDto.setSiteId(siteId);
			siteDto.setSiteName(siteNameTxt);
			siteDto.setAccountDto(accountDto);
			siteDto.setSiteDomainDto(siteDomainDto);
			siteDto.setDefaultLayoutId(pageLayoutService
					.findPageLayoutById(
							Integer.parseInt(pageLayoutCombo.getValue()
									.toString())).getId());
			siteService.update(siteDto);

	/*
	 * delete row which has id=-1 and
	 *  re insert previously deleted row into Table by getting its new id from DB
	 */		
			Object id=-1;
			Item item = domainsTable.getItem(id);
			if(item!=null){
				String domainName = (String) domainsTable.getContainerProperty(id,
						"Domains").getValue();

				SiteDomainService siteDomainService = (SiteDomainService) contextHelper
						.getBean("siteDomainService");
				SiteDomainDto dto = siteDomainService
						.findSiteDomainByName(domainName);

				if (dto.getDomainName().equals(domainName)) {
					final Button deleteLink = new Button();
					addDomainsListTable(dto, domainsTable, deleteLink, siteId);
					domainsTable.removeItem(id);
				}
			}

		}// end if

	}//end saveSiteDto	
}
