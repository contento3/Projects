package com.contento3.web.content;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.springframework.util.CollectionUtils;
import org.vaadin.openesignforms.ckeditor.CKEditorConfig;
import org.vaadin.openesignforms.ckeditor.CKEditorTextField;
import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountService;
import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.web.UIManager;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.themes.BaseTheme;

public class ArticleMgmtUIManager implements UIManager {
	/**
	 * Used to get service beans from spring context.
	 */
	private SpringContextHelper helper;
	 /**
     * Represents the parent window of the template ui
     */
	private Window parentWindow;
	/**
	 * TabSheet serves as the parent container for the article manager
	 */

	private TabSheet tabSheet;
	/**
	 * layout when adding or editing article
	 */
	private VerticalLayout formLayout;
	/**
	 * main layout for article manager screen
	 */
	private VerticalLayout verticalLayout = new VerticalLayout();
	/**
	 * Article table which shows articles
	 */
	private final Table articleTable =  new Table("Articles");
	/**
	 * Article Container to hold article listing
	 */
	private final IndexedContainer articleContainer = new IndexedContainer();
	/**
	 * Article service for article related operations
	 */
	private ArticleService articleService;
	/**
	 * Account service for account related activities
	 */
	private AccountService accountService;
	private final TextField articleHeading = new TextField();
	private final TextArea articleTeaser = new TextArea();
	private final CKEditorConfig config = new CKEditorConfig();
	private final CKEditorTextField bodyTextField = new CKEditorTextField(config);
	private final PopupDateField postedDatefield = new PopupDateField("Article Posted Date");
	private final PopupDateField expiryDatefield = new PopupDateField("Article Expiry Date");
	private Integer accountId;
	

	/**
	 * constructor
	 * @param helper
	 * @param parentWindow
	 */
	public ArticleMgmtUIManager(final SpringContextHelper helper,final Window parentWindow) {
		this.helper= helper;
		this.parentWindow = parentWindow;
		this.articleService = (ArticleService) this.helper.getBean("articleService");
		this.accountService = (AccountService) this.helper.getBean("accountService");
		//Get accountId from the session
        WebApplicationContext ctx = ((WebApplicationContext) parentWindow.getApplication().getContext());
        HttpSession session = ctx.getHttpSession();
        this.accountId =(Integer)session.getAttribute("accountId");
	}

	@Override
	public void render() {

		
	}
	/**
	 * Return tab sheet
	 */
	@Override
	public Component render(String command) {
		this.tabSheet = new TabSheet();
		this.tabSheet.setHeight(100, Sizeable.UNITS_PERCENTAGE);
		final Tab articleTab = tabSheet.addTab(verticalLayout, "Article Management");
		articleTab.setClosable(true);
		this.verticalLayout.setSpacing(true);
		this.verticalLayout.setWidth(100,Sizeable.UNITS_PERCENTAGE);
		renderArticleComponent();
		return this.tabSheet;
	}
	

	@Override
	public Component render(String command, Integer entityFilterId) {
	
		return null;
	}
	@Override
	public Component render(String command,
			HierarchicalContainer treeItemContainer) {
	
		return null;
	}
	
	private void renderArticleComponent() {
		addArticleButton();
		renderArticleTable();
		
	}
	/**
	 * Render article table
	 */
	private void renderArticleTable() {
		articleTable.setWidth(100, Sizeable.UNITS_PERCENTAGE);
		articleTable.setPageLength(5);
		articleTable.setImmediate(true);
		renderArticles();
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

	/**
	 * Display "Add Article" button on the top of tab 
	 */
	private void addArticleButton(){
		Button addButton = new Button("Add Article");
		addButton.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				renderAddScreen();		
			}

		});
		this.verticalLayout.addComponent(addButton);
	}
	/**
	 * render screen for adding article
	 * @return
	 */
	public void renderAddScreen(){
		formlayoutSettings();
		final Tab addArticleTab = this.tabSheet.addTab(formLayout,"Add Article");
		this.tabSheet.setSelectedTab(formLayout);
		addArticleTab.setClosable(true);
		
        final String editorInitialValue = 
                  "<p>Thanks TinyMCEEditor for getting us started on the CKEditor integration.</p><h1>Like TinyMCEEditor said, &quot;Vaadin rocks!&quot;</h1><h1>And CKEditor is no slouch either.</h1>";
        bodyTextField.setValue(editorInitialValue);
        postedDatefield.setValue(new Date());

        final Button saveButton = new Button("Save");
        saveButton.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				ArticleDto article = new ArticleDto();
				article.setHead(articleHeading.getValue().toString());
				article.setTeaser(articleTeaser.getValue().toString());
				article.setBody(bodyTextField.getValue().toString());
				postedDatefield.getValue();
				article.setDatePosted((Date)postedDatefield.getValue());
				Date createdDate= new Date();
				article.setDateCreated(createdDate);
				article.setLastUpdated(createdDate);
				article.setExpiryDate((Date)expiryDatefield.getValue());
				article.setIsVisible(1);
				AccountDto account = accountService.findAccountById(accountId);
				article.setAccount(account);
				article.setSite(new ArrayList());
				articleService.create(article);
				String notification ="Artilce added successfully"; 
				parentWindow.showNotification(notification);
				
			}
		});

        formLayout.addComponent(saveButton);
      }
	/**
	 * render screen for editing article
	 * @param article
	 * @return
	 */
	public Component renderEditScreen(final Integer editId){
		formlayoutSettings();
		final ArticleDto article = this.articleService.findById(editId);
		final Tab editArticleTab = this.tabSheet.addTab(formLayout,"Edit Article");
		this.tabSheet.setSelectedTab(formLayout);
		editArticleTab.setClosable(true);
	
		articleHeading.setValue(article.getHead());
		articleTeaser.setValue(article.getTeaser());
		bodyTextField.setValue(article.getBody());
		postedDatefield.setValue(article.getDatePosted());
		expiryDatefield.setValue(article.getExpiryDate());
		final Button editButton = new Button("Save");
		editButton.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				article.setHead(articleHeading.getValue().toString());
				article.setTeaser(articleTeaser.getValue().toString());
				article.setBody(bodyTextField.getValue().toString());
				Date date = (Date) postedDatefield.getValue();
				article.setDatePosted(date);
				article.setLastUpdated(new Date());
				article.setExpiryDate((Date)expiryDatefield.getValue());
				article.setIsVisible(1);
				AccountDto account = accountService.findAccountById(accountId);
				article.setAccount(account);
				article.setSite(new ArrayList());
				articleService.update(article);
				String notification ="Artilce updated successfully"; 
				parentWindow.showNotification(notification);
				
			}
		});
		
	    formLayout.addComponent(editButton);
		return formLayout;
	}
	/**
	 * setting formLayout for showing edit & add article screen
	 */
	public void formlayoutSettings(){
		formLayout = new VerticalLayout();
		formLayout.setHeight("100%");
		articleHeading.setCaption("Header");

		articleTeaser.setCaption("Teaser");
		articleTeaser.setColumns(50);
		articleTeaser.setRows(2);
		
        config.useCompactTags();
        config.disableElementsPath();
        config.setResizeDir(CKEditorConfig.RESIZE_DIR.BOTH);
        config.disableSpellChecker();
        config.setToolbarCanCollapse(true);
        config.setWidth("75%");
        config.setHeight("100%");
        bodyTextField.setCaption("Article Body");
        
        postedDatefield.setInputPrompt("Insert Date");
        // Set the correct resolution only date
        postedDatefield.setResolution(PopupDateField.RESOLUTION_DAY);
        expiryDatefield.setInputPrompt("Insert Date");
        expiryDatefield.setResolution(PopupDateField.RESOLUTION_DAY);
        formLayout.addComponent(articleHeading);
	    formLayout.addComponent(articleTeaser);
	    formLayout.addComponent(postedDatefield);
	   // formLayout.addComponent(lastUpdatedDatefield);
	    formLayout.addComponent(expiryDatefield);
	    formLayout.addComponent(bodyTextField);
	}
	
	/**
	 * display articles 
	 * @param articleTable
	 */
	private void renderArticles(){
		
		this.articleContainer.addContainerProperty("Checkbox", CheckBox.class, null);
		this.articleContainer.addContainerProperty("Article", String.class, null);
		this.articleContainer.addContainerProperty("Date Created", String.class, null);
		this.articleContainer.addContainerProperty("Date Posted", String.class, null);
		this.articleContainer.addContainerProperty("Expiry Date", String.class, null);
		this.articleContainer.addContainerProperty("Edit", Button.class, null);
		this.articleContainer.addContainerProperty("Delete", Button.class, null);

		Collection<ArticleDto> articleDto = this.articleService.findByAccountId(accountId);
		if (!CollectionUtils.isEmpty(articleDto)) {

			for (ArticleDto article : articleDto) {
					Button edit = new Button();
					Button delete = new Button();
					addArticlesToTable(article,edit,delete);
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
		final Item item = this.articleContainer.addItem(article.getArticleId().toString());
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
				Integer id = (Integer) editLink.getData();
				renderEditScreen(id);
				
			}
		});
		
		deleteLink.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
			
				Integer id = (Integer) deleteLink.getData();
				deleteArticle(id);
			}
		
		});
		
	
	
	}//end addArticlesToTable
	/**
	 
	 * delete article item from database as well as articleContainer
	 * @param id
	 */
	private void deleteArticle(final Integer id) {
		ArticleDto article = (ArticleDto) this.articleService
				.findById(Integer.parseInt(id.toString()));
		article.setIsVisible(0);
		this.articleContainer.removeItem(id);
		this.articleService.update(article);
		this.parentWindow.showNotification(article.getHead()+" deleted successfully");
		
	}

}
