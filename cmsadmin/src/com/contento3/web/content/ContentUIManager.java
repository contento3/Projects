package com.contento3.web.content;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.http.HttpSession;
import org.springframework.util.CollectionUtils;

import antlr.collections.List;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.cms.constant.NavigationConstant;
import com.contento3.web.UIManager;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.Action;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

/**
 * Entry point for all the logic related to CONTENT MANAGER functionality.
 * @author HAMMAD
 *
 */
public class ContentUIManager implements UIManager{

	/**
	 * Helper class to get beans from the spring application context.
	 */
    private SpringContextHelper helper;
    
    /**
     * Parent window that contains all the ui components.Used primarily to set notifications.
     */
	private Window parentWindow;
	/**
	 * Article table which shows articles
	 */
	private final Table articleTable =  new Table("Articles");
	/**
	 * Article service to perform article related tasks.
	 */
	private ArticleService articleService;
	
	/**
	 * Account id for the account that is currently in use.
	 */
	private Integer accountId=null;
	
	/**
	 * Article Container to hold article listing
	 */
	private final IndexedContainer articleContainer = new IndexedContainer();
	
	/**
	 * Main tabsheet that hold all the content.
	 */
	final TabSheet elementTab = new TabSheet();
	
	/**
	 * Represents the navigation items in the Content Manager section.
	 */
	private String[] navigationItems = {NavigationConstant.CONTENT_ART_MGMT,NavigationConstant.CONTENT_IMG_MGMT,NavigationConstant.CONTENT_VID_MGMT};
	
	final CssLayout verticalLayout = new CssLayout();


	/**
	 * Class contructor
	 * @param helper
	 * @param parentWindow
	 */
	public ContentUIManager(final SpringContextHelper helper,final Window parentWindow){
		this.helper = helper;
		this.parentWindow = parentWindow;
		this.articleService = (ArticleService) helper.getBean("articleService");
	}

	@Override
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

	/**
	 * Renders all the navigation items in the Content Manager section
	 * @param hwContainer
	 */
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

	/**
	 * Calls the appropiate sub ui manager based on the argument.
	 * @param element Tells what sub ui manager is require to be returned.
	 * @return
	 */
	private Component renderContentElementUI(final String element){
		//final TabSheet elementTab = new TabSheet();
		elementTab.setHeight(100, Sizeable.UNITS_PERCENTAGE);

		//final CssLayout verticalLayout = new CssLayout();

		Button button = new Button();
		verticalLayout.addComponent(button);
		verticalLayout.setSizeFull();
		final ImageMgmtUIManager imageMgmtUIMgr = new ImageMgmtUIManager(helper,parentWindow);

		elementTab.addTab(verticalLayout, String.format("%s Management",element));
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

			articleTable.setWidth(100, Sizeable.UNITS_PERCENTAGE);
			articleTable.setPageLength(5);
			articleTable.setImmediate(true);
			renderArticles(articleTable);

			verticalLayout.addComponent(articleTable);
			Button deleteButton = new Button("Delete");
			deleteButton.addListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
				     Collection<Object> toDelete =  new ArrayList<Object>();
				    
						
                       for (Object id : articleTable.getItemIds()) {
                           // Get the checkbox of this item (row)
                           CheckBox checkBox = (CheckBox) articleContainer
                                   .getContainerProperty(id, "Checkbox")
                                   .getValue();

                           if (checkBox.booleanValue()) {
                               toDelete.add(id);
                           }
                       }

                       // Perform the deletions
                       for (Object id : toDelete) {
                    	   articleContainer.removeItem(id);
                    	   ArticleDto article = (ArticleDto) articleService
      								.findById(Integer.parseInt(id.toString()));
      							article.setIsVisible(0);
      							articleService.update(article);
                       }
                   
				}
			});
			verticalLayout.addComponent(deleteButton);
			
		}
		else if (element.equals("Image")){
			verticalLayout.addComponent(imageMgmtUIMgr.listImage(1));
		}

		return elementTab;
	}
	/**
	 * display articles 
	 * @param articleTable
	 */
	private void renderArticles(final Table articleTable){
		
		articleContainer.addContainerProperty("Checkbox", CheckBox.class, null);
		articleContainer.addContainerProperty("Article", String.class, null);
		articleContainer.addContainerProperty("Date Created", String.class, null);
		articleContainer.addContainerProperty("Date Posted", String.class, null);
		articleContainer.addContainerProperty("Expiry Date", String.class, null);
		articleContainer.addContainerProperty("Edit", Button.class, null);
		articleContainer.addContainerProperty("Delete", Button.class, null);

		Collection<ArticleDto> articleDto = articleService.findByAccountId(accountId);
		if (!CollectionUtils.isEmpty(articleDto)) {

			for (ArticleDto article : articleDto) {
				if(article.getIsVisible()==1){	//check it was not deleted by user in past
					Button edit = new Button();
					Button delete = new Button();
					addArticlesToTable(article,edit,delete);
				}
			}

			articleTable.setContainerDataSource(articleContainer);
		} else {
			final Label label = new Label("No article found for this site");
		}
	}//end renderArticles
	
	/**
	 * add articles to articleContainer 
	 * which will be provided as source to table 
	 * @param article
	 * @param editLink
	 */
	private void addArticlesToTable(final ArticleDto article,final Button editLink,
			final Button deleteLink ){
		final Item item = articleContainer.addItem(article.getArticleId().toString());
		item.getItemProperty("Checkbox").setValue(new CheckBox());
		item.getItemProperty("Article").setValue(article.getHead());
		String DATE_FORMAT = "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		item.getItemProperty("Date Created").setValue(sdf.format(article.getDateCreated()));
		item.getItemProperty("Date Posted").setValue(sdf.format(article.getDatePosted()));
		item.getItemProperty("Expiry Date").setValue(sdf.format(article.getExpiryDate()));
		editLink.setCaption("Edit");
		editLink.setData(article.getArticleId());
		editLink.addStyleName("edit");
		editLink.setStyleName(BaseTheme.BUTTON_LINK);
		item.getItemProperty("Edit").setValue(editLink);
		deleteLink.setCaption("Delete");
		deleteLink.setData(article.getArticleId());
		deleteLink.addStyleName("delete");
		deleteLink.setStyleName(BaseTheme.BUTTON_LINK);
		item.getItemProperty("Delete").setValue(deleteLink);
	

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
		
		deleteLink.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
			
				Object id = deleteLink.getData();
				ArticleDto article = (ArticleDto) articleService
						.findById(Integer.parseInt(id.toString()));
				article.setIsVisible(0);
				articleService.update(article);
				//articleContainer.removeItem(id);
				articleTable.removeItem(id);
				//articleContainer.removeAllItems();
				//articleTable.setContainerDataSource(articleContainer);
				articleTable.requestRepaint();
				articleTable.requestRepaintAll();
				parentWindow.showNotification(article.getHead()+" deleted successfully");
				
			}
		});
		
	
	
	}//end addArticlesToTable

}