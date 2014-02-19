package com.contento3.web.site;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.dam.image.dto.ImageDto;
import com.contento3.dam.image.service.ImageService;
import com.contento3.web.UIManager;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.content.image.ImageLoader;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.site.listener.SiteCreatePopup;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

public class SitesDashBoard implements UIManager,Property.ValueChangeListener{

	private static final long serialVersionUID = 1L;

	/**
	 * Used to get service beans from spring context.
	 */
	final SpringContextHelper helper;
	
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
	private Label label2;
	
	/**
	 * constructor
	 * @param contextHelper
	 * @param parentWindow
	 */
	
	public SitesDashBoard(final TabSheet uiTabSheet,final SpringContextHelper contextHelper) {
		this.helper = contextHelper;
		siteService = (SiteService) contextHelper.getBean("siteService");
		articleService = (ArticleService) contextHelper.getBean("articleService");
		imageService = (ImageService) contextHelper.getBean("imageService");
		
		articleTable = new Table("Latest Articles");
		imageTable = new Table("Latest Images");
		siteDashboardTab = uiTabSheet; 
		//Get accountId from the session
        accountId = (Integer)SessionHelper.loadAttribute("accountId");
	}
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Component render(final String command) {
		
		final Label label = new Label("Welcome to Contento3!");
		label.setSizeUndefined(); // Shrink to fit content

		HorizontalLayout dashboardHeaderRow = new HorizontalLayout();
		dashboardHeaderRow.setSpacing(true);
		dashboardHeaderRow.addComponent(label);
		
		verticalLayout.addComponent(dashboardHeaderRow);
		verticalLayout.addComponent(createDashboardRow());
		return verticalLayout;
	}

	private HorizontalLayout createDashboardRow(){
		HorizontalLayout dashboardRow = new HorizontalLayout();
		dashboardRow.setSpacing(true);
		final Panel panel = new Panel();
		panel.setSizeUndefined(); // Shrink to fit content
		dashboardRow.addComponent(panel);
		dashboardRow.setMargin(true);
		panel.setContent(createDashBoardItem("Start creating websites right here.You can create multiple websites under one account.","Create a site","sites-96.png",new SiteCreatePopup(helper)));
//TODO
//		final Panel panel2 = new Panel();
//		panel2.setContent(createDashBoardItem("You can create as many pages as you want for sites you have under your account.","Create a page","pages-icon-96.png",new SiteCreateListener(helper,siteDashboardTab,verticalLayout)));
//		panel2.setSizeUndefined(); // Shrink to fit content
//		dashboardRow.addComponent(panel2);
		
		return dashboardRow;
	}	
	
	private HorizontalLayout createDashBoardItem(final String label,final String linkedCmsLabel,final String itemImage,final ClickListener listener){
		// Create the content
		final HorizontalLayout content = new HorizontalLayout();
		final Label item = new Label(label,ContentMode.HTML);
		final Button commandLabel = new Button(linkedCmsLabel);
		commandLabel.addStyleName("link");
		commandLabel.addClickListener(listener);
		final VerticalLayout labelLayout = new VerticalLayout();
		labelLayout.addComponent(item);
		labelLayout.addComponent(commandLabel);
		labelLayout.setWidth(150, Unit.PIXELS);
		labelLayout.setComponentAlignment(item, Alignment.MIDDLE_CENTER);

		final ImageLoader imageLoader = new ImageLoader();
		content.addComponent(labelLayout);
		content.addComponent(imageLoader.loadEmbeddedImageByPath("images/"+itemImage));
		content.setMargin(true);
		return content;		
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
		siteDto = (Collection<SiteDto>) siteService.findSitesByAccountId(accountId, false);
		//siteDto = new ArrayList<SiteDto>();
		if(!(siteDto.isEmpty())){
			
			renderCombobox();
			renderArticleTable();
			renderImageTable();
			verticalLayout.addComponent(articleTable);
			verticalLayout.addComponent(imageTable);
		}
		else {
			Button linkButton = new Button("Create new site");
			linkButton.setStyleName(BaseTheme.BUTTON_LINK);
			linkButton.addClickListener(new ClickListener() {
				
				private static final long serialVersionUID = 1L;

				public void buttonClick(ClickEvent event) {
					final SiteUIManager siteUiManager = new SiteUIManager(siteDashboardTab,helper);
					VerticalLayout newlayout = siteUiManager.renderNewSite();
					Tab tab1 = siteDashboardTab.addTab(newlayout, "Create site", null);
					tab1.setClosable(true);
					siteDashboardTab.setSelectedTab(newlayout);
				}
			});
			verticalLayout.addComponent(linkButton);
			
		}

		verticalLayout.addComponent(label);//article label
		verticalLayout.addComponent(label2);//image label
	
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
		siteComboBox.addValueChangeListener(this);
		verticalLayout.addComponent(siteComboBox);
	}

	/**
	 * Combo box handler to change the table data when
	 * the user selects the site from the combobox.
	 */
	@Override
	public void valueChange(ValueChangeEvent event) {
		Notification.show("Loading "+event.getProperty()+" latest contents");
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
		articleTable.setWidth(50, Unit.PERCENTAGE);
		articleTable.setPageLength(5);
		label = new Label("No article found");
		label2 = new Label("No image found");
		imageTable.setWidth(50, Unit.PERCENTAGE);
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
		
		articleDto = articleService.findLatestArticleBySiteId(siteId, 5,null);
		if(!articleDto.isEmpty()){
			for(ArticleDto article: articleDto){
					Item item = articleContainer.addItem(article.getId());
					item.getItemProperty("head").setValue(article.getHead());
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
					item.getItemProperty("date_created").setValue(formatter.format(article.getDateCreated()));
					
					if (null!=article.getExpiryDate()){
						item.getItemProperty("expiry_date").setValue(formatter.format(article.getExpiryDate()));
					}
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
			label2.setVisible(true);
			imageTable.setVisible(false);
		}else {
			label2.setVisible(false);
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
				Item item = imageContainer.addItem(image.getId());
				item.getItemProperty("name").setValue(image.getName());
				item.getItemProperty("alt_text").setValue(image.getAltText());
			}
		}
		imageContainer.sort(new Object[] { "name" }, new boolean[] { true });
		return imageContainer;
	}
	
}
