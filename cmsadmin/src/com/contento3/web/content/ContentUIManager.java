package com.contento3.web.content;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

import org.springframework.util.CollectionUtils;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.cms.constant.NavigationConstant;
import com.contento3.cms.site.structure.domain.dto.SiteDomainDto;
import com.contento3.cms.site.structure.domain.service.SiteDomainService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.web.UIManager;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ContentUIManager implements UIManager{

    private SpringContextHelper helper;
	private Window parentWindow;
	private ArticleService articleService;
	private Integer accountId=null;
	private final IndexedContainer articleContainer = new IndexedContainer();
	final TabSheet elementTab = new TabSheet();
	private String[] navigationItems = {NavigationConstant.CONTENT_ART_MGMT,NavigationConstant.CONTENT_IMG_MGMT,NavigationConstant.CONTENT_VID_MGMT};

	public ContentUIManager(final SpringContextHelper helper,final Window parentWindow){
		this.helper = helper;
		this.parentWindow = parentWindow;
		this.articleService = (ArticleService) helper.getBean("articleService");
		

	}
 
	public void render(){
	}

	@Override
	public Component render(final String command){
		Component componentToReturn = null;
		
		return componentToReturn;
	}

	@Override
	public Component render(final String command,final Integer id){

		return null;
	}

	@Override
	public Component render(final String command,final HierarchicalContainer hwContainer){
		Component tabsheet = null;
		if (command.equals(NavigationConstant.CONTENT_MANAGER)){
			//Add the content screen tab and also add the child items
			tabsheet = new TabSheet();
			renderContentNavigationItems(hwContainer);
		}
		else if (command.equals(NavigationConstant.CONTENT_ART_MGMT)){
			tabsheet = renderArticleUI();
		}
		else if (command.equals(NavigationConstant.CONTENT_IMG_MGMT)){
			tabsheet = renderImageUI();
		}
		else if (command.equals(NavigationConstant.CONTENT_VID_MGMT)){
			tabsheet = renderVideoUI();
		}
		return tabsheet;
	}

	public void renderContentNavigationItems(final HierarchicalContainer hwContainer){
		for (String navigationItem : navigationItems){
			Item item = hwContainer.addItem(navigationItem);
			if (null != item){
				item.getItemProperty("name").setValue(navigationItem);
				hwContainer.setParent(navigationItem, NavigationConstant.CONTENT_MANAGER);
				hwContainer.setChildrenAllowed(navigationItem, false);
			}
		}
	}

	private Component renderArticleUI(){
		return renderContentElementUI("Article");
	}

	private Component renderVideoUI(){
		TabSheet videoTab = new TabSheet();
		return renderContentElementUI("Video");
	}

	private Component renderImageUI(){
		TabSheet imageTab = new TabSheet();
		return renderContentElementUI("Image");
	}

	private Component renderContentElementUI(final String element){
		//final TabSheet elementTab = new TabSheet();
		elementTab.setHeight(100, Sizeable.UNITS_PERCENTAGE);

		final CssLayout verticalLayout = new CssLayout();

		Button button = new Button();
		verticalLayout.addComponent(button);
		verticalLayout.setSizeFull();

		final ImageMgmtUIManager imageMgmtUIMgr = new ImageMgmtUIManager(helper,parentWindow);
		elementTab.addTab(verticalLayout, String.format("%s Management",element));
		verticalLayout.addComponent(imageMgmtUIMgr.listImage(1));
		button.addListener(new ClickListener(){
			public void buttonClick(ClickEvent event){
				if (element.equals("Article")){
					ArticleMgmtUIManager artMgmtUIMgr = new ArticleMgmtUIManager(helper,parentWindow);
					VerticalLayout newArticleLayout = new VerticalLayout();
					Tab createNew = elementTab.addTab(newArticleLayout, String.format("Create new %s",element));
					createNew.setClosable(true);
					elementTab.setSelectedTab(newArticleLayout);
					newArticleLayout.addComponent(artMgmtUIMgr.renderAddScreen());
					newArticleLayout.setHeight("100%");
				}
				else if (element.equals("Image")){
					VerticalLayout newArticleLayout = new VerticalLayout();
					Tab createNew = elementTab.addTab(newArticleLayout, String.format("Create new %s",element));
					createNew.setClosable(true);
					elementTab.setSelectedTab(newArticleLayout);
					newArticleLayout.addComponent(imageMgmtUIMgr.renderAddScreen());
					newArticleLayout.setHeight("100%");
				}

   	        }
    	});

		button.setCaption(String.format("Add %s",element));
		if(element.equals("Article")){
			// Get accountId from the session
			WebApplicationContext ctx = ((WebApplicationContext) parentWindow
					.getApplication().getContext());
			HttpSession session = ctx.getHttpSession();
			accountId = (Integer) session.getAttribute("accountId");

			final Table articleTable = new Table("Articles");
			articleTable.setWidth(100, Sizeable.UNITS_PERCENTAGE);
			articleTable.setPageLength(5);
			articleTable.setImmediate(true);

			renderArticles(articleTable);

			verticalLayout.addComponent(articleTable);
		}

		return elementTab;
	}
	/**
	 * display articles 
	 */
	private void renderArticles(final Table articleTable){
		articleContainer.addContainerProperty("Article", String.class, null);
		articleContainer.addContainerProperty("Date_created", String.class, null);
		articleContainer.addContainerProperty("Date_posted", String.class, null);
		articleContainer.addContainerProperty("Expiry_Date", String.class, null);
		articleContainer.addContainerProperty("Edit", Button.class, null);
		Collection<ArticleDto> articleDto = articleService.findByAccountId(accountId);
		if (!CollectionUtils.isEmpty(articleDto)) {

			for (ArticleDto article : articleDto) {
				Button edit = new Button();
				addArticlesToTable(article,edit);
			}

			articleTable.setContainerDataSource(articleContainer);
		} else {
			final Label label = new Label("No article found for this site");
			//VerticalLayout
		}
	}
	
	private void addArticlesToTable(final ArticleDto article,final Button editLink ){
		final Item item = articleContainer.addItem(article.getArticleId().toString());

		item.getItemProperty("Article").setValue(article.getHead());
		//Date date = new Date();
		String DATE_FORMAT = "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		item.getItemProperty("Date_created").setValue(sdf.format(article.getDateCreated()));
		item.getItemProperty("Date_posted").setValue(sdf.format(article.getDatePosted()));
		item.getItemProperty("Expiry_Date").setValue(sdf.format(article.getExpiryDate()));
		editLink.setCaption("Edit");
		editLink.setData(article.getArticleId());
		editLink.addStyleName("edit");
		editLink.setStyleName(BaseTheme.BUTTON_LINK);
		item.getItemProperty("Edit").setValue(editLink);


		editLink.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Object id = editLink.getData();
				
				ArticleDto article = (ArticleDto) articleService.findById(Integer.parseInt(id.toString()));
				elementTab.setHeight(100, Sizeable.UNITS_PERCENTAGE);
					ArticleMgmtUIManager artMgmtUIMgr = new ArticleMgmtUIManager(helper,parentWindow);
					VerticalLayout newArticleLayout = new VerticalLayout();
					Tab createNew = elementTab.addTab(newArticleLayout, "Edit Article");
					createNew.setClosable(true);
					elementTab.setSelectedTab(newArticleLayout);
					newArticleLayout.addComponent(artMgmtUIMgr.renderEditScreen(article));
					newArticleLayout.setHeight("100%");
			}
		});


		
	}

}