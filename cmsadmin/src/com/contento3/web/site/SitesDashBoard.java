package com.contento3.web.site;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.servlet.http.HttpSession;
import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.dto.RelatedArticleLinkDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.dam.image.dto.ImageDto;
import com.contento3.dam.image.service.ImageService;
import com.contento3.web.UIManager;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component.Event;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.themes.BaseTheme;

public class SitesDashBoard implements UIManager,Property.ValueChangeListener{
	/**
	 * Used to get service beans from spring context.
	 */
	final SpringContextHelper helper;
    /**
     * Represents the parent window of the template ui
     */
	private final Window parentWindow;
	private final VerticalLayout verticalLayout = new VerticalLayout();
	private final HorizontalLayout horizLayout = new  HorizontalLayout();
	private final SiteService siteService;
	private final ArticleService articleSerivce;
	private final ImageService imageService;
	private TabSheet siteTab;
	private Integer accountId;
	private String siteName = null;
	private Integer siteId;
	private Table articleTable ,imageTable;
	private IndexedContainer articleContainer , imageContainer;
	private Collection<SiteDto> siteDto;
	private Collection<ArticleDto> articleDto;
	private Collection<ImageDto> imageDto;
	
	/**
	 * constructor
	 * @param contextHelper
	 * @param parentWindow
	 */
	public SitesDashBoard(final SpringContextHelper contextHelper,final Window parentWindow) {
		this.helper = contextHelper;
		this.parentWindow = parentWindow;
		siteService = (SiteService) contextHelper.getBean("siteService");
		articleSerivce = (ArticleService) contextHelper.getBean("articleService");
		imageService = (ImageService) contextHelper.getBean("imageService");
		//Get accountId from the session
        WebApplicationContext ctx = ((WebApplicationContext) parentWindow.getApplication().getContext());
        HttpSession session = ctx.getHttpSession();
        accountId= (Integer)session.getAttribute("accountId");
	}
	

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Component render(String command) {
		// TODO Auto-generated method stub
		if (null==siteTab){ 
			siteTab = new TabSheet();
			siteTab.setHeight("585");
			siteTab.setWidth(100,Sizeable.UNITS_PERCENTAGE);
	    	
			verticalLayout.setSpacing(true);
			verticalLayout.setWidth(100,Sizeable.UNITS_PERCENTAGE);
			renderSiteContent();
			Tab tab2 =  siteTab.addTab(verticalLayout,"Site Dashboard",null);
			tab2.setClosable(true);
			siteTab.setSelectedTab(verticalLayout);
	    	
		}
			return siteTab;
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
	
		siteDto = (Collection<SiteDto>) siteService.findSitesByAccountId(accountId);
		if(!(siteDto.isEmpty())){
			renderCombobox();
			renderArtilceTable();
			renderImageTable();
		}else{
			
			Button linkButton = new Button("Create new site");
			linkButton.setStyleName(BaseTheme.BUTTON_LINK);
			linkButton.addListener(new ClickListener() {
				
				public void buttonClick(ClickEvent event) {
					final SiteUIManager siteUiManager = new SiteUIManager(helper, parentWindow);

					VerticalLayout newlayout = siteUiManager.renderNewSite();
					Tab tab1 = siteTab.addTab(newlayout, "Create site", null);
					tab1.setClosable(true);
					siteTab.setSelectedTab(newlayout);
				}
			});
			verticalLayout.addComponent(linkButton);
		}
		
	}

	/**
	 * render combo box
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
	 * Combo box handler 
	 */
	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		parentWindow.showNotification("Showing "+event.getProperty()+" latest contents");
		siteName =  event.getProperty().getValue().toString();
		articleTable.setContainerDataSource(getLatestArticles());
		imageTable.setContainerDataSource(getLatestImages());
	}
	/**
	 * render article table
	 */
	private void renderArtilceTable(){
		articleTable = new Table("Latest Articles");
		articleTable.setWidth(50, Sizeable.UNITS_PERCENTAGE);
		articleTable.setPageLength(5);
		articleTable.setContainerDataSource(getLatestArticles());
		if(articleDto.isEmpty()){
			Label label = new Label("No article found");
			verticalLayout.addComponent(label);
		}else {
			verticalLayout.addComponent(articleTable);
		}
		
	}
	/**
	 * Return latest Articles
	 * @return
	 */
	private IndexedContainer getLatestArticles() {
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
		
		articleDto = articleSerivce.findLatestArticleBySiteId(siteId, 5);
		
		if(!articleDto.isEmpty()){
			for(ArticleDto article: articleDto){
				Item item = articleContainer.addItem(article.getArticleId());
				item.getItemProperty("head").setValue(article.getHead());
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				item.getItemProperty("date_created").setValue( formatter.format(article.getDateCreated()));
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
		imageTable = new Table("Latest Images");
		imageTable.setWidth(50, Sizeable.UNITS_PERCENTAGE);
		imageTable.setPageLength(5);
		imageTable.setContainerDataSource(getLatestImages());
		if(imageDto.isEmpty()){
			Label label = new Label("No Image found");
			verticalLayout.addComponent(label);
		}else {
			verticalLayout.addComponent(imageTable);
		}
		
	}
	/**
	 * Return latest Images
	 * @return
	 */
	private IndexedContainer getLatestImages() {
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
