package com.contento3.web.site;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.dto.RelatedArticleDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.cms.article.service.RelatedArticleService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.dam.image.dto.ImageDto;
import com.contento3.dam.image.service.ImageService;
import com.contento3.web.UIManager;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

public class SitesDashBoard implements UIManager,Property.ValueChangeListener{

	private static final long serialVersionUID = 1L;

	/**
	 * Used to get service beans from spring context.
	 */
	final SpringContextHelper helper;
	
    /**
     * Represents the parent window of the template ui
     */
	private final Window parentWindow;
	
	/**
	 * VerticalLayout serves as the parent container for the dashboard.
	 */
	private final VerticalLayout verticalLayout = new VerticalLayout();
	
	/**
	 * Site service used to perform site related operations.
	 */
	private final SiteService siteService;
	
	/**
	 * Used to perform article related service
	 */
	private final ArticleService articleService;
	
	/**
	 * Used to perform image related operations
	 */
	private final ImageService imageService;
	
	/**
	 * Main tab for dashboard screen;
	 */
	private TabSheet siteDashboardTab;
	
	private Integer accountId;
	private String siteName = null;
	private Integer siteId;
	private final Table articleTable ,imageTable;
	private IndexedContainer articleContainer , imageContainer;
	private Collection<SiteDto> siteDto;
	private Collection<ArticleDto> articleDto;
	private Collection<ImageDto> imageDto;
	private Label label;
	
	/**
	 * constructor
	 * @param contextHelper
	 * @param parentWindow
	 */
	
	public SitesDashBoard(final SpringContextHelper contextHelper,final Window parentWindow) {
		this.helper = contextHelper;
		this.parentWindow = parentWindow;
		siteService = (SiteService) contextHelper.getBean("siteService");
		articleService = (ArticleService) contextHelper.getBean("articleService");
		imageService = (ImageService) contextHelper.getBean("imageService");
		
		articleTable = new Table("Latest Articles");
		imageTable = new Table("Latest Images");

		//Get accountId from the session
        accountId = (Integer)SessionHelper.loadAttribute(parentWindow, "accountId");
        //RelatedArticleService relatedArticleService = (RelatedArticleService) contextHelper.getBean("relatedArticleService");
	}
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Component render(String command) {
		// TODO Auto-generated method stub
		if (null==siteDashboardTab){ 
			siteDashboardTab = new TabSheet();
			siteDashboardTab.setHeight("585");
			siteDashboardTab.setWidth(100,Sizeable.UNITS_PERCENTAGE);
	    	
			verticalLayout.setSpacing(true);
			verticalLayout.setWidth(100,Sizeable.UNITS_PERCENTAGE);
			renderSiteContent();
			Tab tab2 =  siteDashboardTab.addTab(verticalLayout,"Site Dashboard",null);
			tab2.setClosable(true);
			siteDashboardTab.setSelectedTab(verticalLayout);
		}
	
		return siteDashboardTab;
	}

	@Override
	public Component render(String command, Integer entityFilterId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component render(String command,
			HierarchicalContainer treeItemContainer) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * render site contents com box,article table,image table
	 */
	public void  renderSiteContent(){
		buildTables();
		siteDto = (Collection<SiteDto>) siteService.findSitesByAccountId(accountId);
		if(!(siteDto.isEmpty())){
			renderCombobox();
			renderArticleTable();
			renderImageTable();
		}
		else {
			Button linkButton = new Button("Create new site");
			linkButton.setStyleName(BaseTheme.BUTTON_LINK);
			linkButton.addListener(new ClickListener() {
				
				public void buttonClick(ClickEvent event) {
					final SiteUIManager siteUiManager = new SiteUIManager(helper, parentWindow);
					VerticalLayout newlayout = siteUiManager.renderNewSite();
					Tab tab1 = siteDashboardTab.addTab(newlayout, "Create site", null);
					tab1.setClosable(true);
					siteDashboardTab.setSelectedTab(newlayout);
				}
			});
			verticalLayout.addComponent(linkButton);
		}

		verticalLayout.addComponent(articleTable);
		verticalLayout.addComponent(imageTable);
		verticalLayout.addComponent(label);
	}

	/**
	 * Render combo box that contains the sites
	 */
	private void renderCombobox(){

		Collection<String> sitesName= new ArrayList<String>();
			for(SiteDto site : siteDto){
				sitesName.add(site.getSiteName());
			}
		
		ComboBox siteComboBox = new ComboBox("Select Site",sitesName);
		siteName = sitesName.iterator().next();
		siteComboBox.setValue(siteName);
		siteComboBox.setImmediate(true);
		siteComboBox.addListener(this);
		verticalLayout.addComponent(siteComboBox);
	}

	/**
	 * Combo box handler to change the table data when
	 * the user selects the site from the combobox.
	 */
	@Override
	public void valueChange(ValueChangeEvent event) {
		parentWindow.showNotification("Loading "+event.getProperty()+" latest contents");
		siteName =  event.getProperty().getValue().toString();
		renderArticleTable();
		renderImageTable();
	}

	/**
	 * Used to render the article if there are latest 
	 * articles to be displayed, otherwise table is 
	 * hidden and a message is displayed.
	 */
	private void renderArticleTable(){
		articleTable.setContainerDataSource(loadLatestArticles());
		if(articleDto.isEmpty()){
			label.setVisible(true);
			articleTable.setVisible(false);
		}else {
			label.setVisible(false);
			articleTable.setVisible(true);
		}
	}
	
	/**
	 * Used to just build the article and image tables 
	 * i.e. sets the properties for the table.
	 */
	private void buildTables(){
		articleTable.setWidth(50, Sizeable.UNITS_PERCENTAGE);
		articleTable.setPageLength(5);
		label = new Label("No article found");
		
		imageTable.setWidth(50, Sizeable.UNITS_PERCENTAGE);
		imageTable.setPageLength(5);
	}
	
	/**
	 * Return latest Articles
	 * @return
	 */
	private IndexedContainer loadLatestArticles() {
		articleDto=null;
		articleContainer = new IndexedContainer();
		articleContainer.addContainerProperty("head", String.class, null);
		articleContainer.addContainerProperty("date_created", String.class, null);
		articleContainer.addContainerProperty("expiry_date", String.class, null);
		
		for(SiteDto site : siteDto){
			if(site.getSiteName().equals(siteName)){
				siteId = site.getSiteId();
			}
		}
		
		articleDto = articleService.findLatestArticleBySiteId(siteId, 5);
		if(!articleDto.isEmpty()){
			for(ArticleDto article: articleDto){
				Item item = articleContainer.addItem(article.getArticleId());
				item.getItemProperty("head").setValue(article.getHead());
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				item.getItemProperty("date_created").setValue(formatter.format(article.getDateCreated()));
				item.getItemProperty("expiry_date").setValue(formatter.format(article.getExpiryDate()));
			}
		}

		articleContainer.sort(new Object[] { "date_created" }, new boolean[] { true });
		return articleContainer;
	}
	
	/**
	 * render image table
	 */
	private void renderImageTable(){
		imageTable.setContainerDataSource(loadLatestImages());
		if(imageDto.isEmpty()){
			label.setVisible(true);
			label.setCaption("No Image found");
			imageTable.setVisible(false);
		}else {
			label.setVisible(false);
			imageTable.setVisible(true);
		}
	}

	/**
	 * Return latest Images
	 * @return
	 */
	private IndexedContainer loadLatestImages() {
		imageDto = null;
		imageContainer = new IndexedContainer();
		imageContainer.addContainerProperty("name", String.class, null);
		imageContainer.addContainerProperty("alt_text", String.class, null);
		
		for(SiteDto site : siteDto){
			if(site.getSiteName().equals(siteName)){
				siteId = site.getSiteId();
			}
		}
		
		imageDto = imageService.findLatestImagesBySiteId(siteId, 5);
		if(!imageDto.isEmpty()){
			for(ImageDto image: imageDto){
				Item item = imageContainer.addItem(image.getImageId());
				item.getItemProperty("name").setValue(image.getName());
				item.getItemProperty("alt_text").setValue(image.getAltText());
			}
		}
		imageContainer.sort(new Object[] { "name" }, new boolean[] { true });
		return imageContainer;
	}
	
}
