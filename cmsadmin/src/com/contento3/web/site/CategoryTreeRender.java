package com.contento3.web.site;

import java.util.Collection;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.service.CategoryService;
import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.service.PageService;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.web.UIManager;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Select;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TabSheet.Tab;

public class CategoryTreeRender implements UIManager, Handler{
	
	private SpringContextHelper contextHelper;
	private VerticalLayout verticalLayout;
	private FormLayout categoryFormLayout;
	private HorizontalLayout horizontalLayout;
	private HierarchicalContainer categoryContainer=null;
	private Tree categoryTree;
	private static final Action ADD_CATEGORY =new Action("Add category");
	private static final Action[] CATEGORY_ACTION = new Action[]{ADD_CATEGORY}; 
	private CategoryService categoryService;
	private PageService pageService;
	private Window parentWindow;
	private Collection<CategoryDto> categories;
	//private Integer siteId=null;
	private TabSheet tabSheet=null;
	private Integer pageId=null;
	private PageDto pageDto = null;
	private	String name =null;
	private final Button assignCategoryButton = new Button("Assign");
	private final Button renameCategoryButton = new Button("Rename");
	private final TextField selectedCategoryField = new TextField();
	private boolean addCategoryEnable=false; // allow or disallow to provide add category option
	/**
	 * Constructor
	 * @param helper
	 * @param parentWindow
	 */
	public CategoryTreeRender(final SpringContextHelper helper,final Window parentWindow) {
		this.verticalLayout = new VerticalLayout();
		this.categoryFormLayout = new FormLayout();
		this.horizontalLayout = new HorizontalLayout();
		this.contextHelper = helper;
		this.parentWindow = parentWindow;
		categoryService = (CategoryService)contextHelper.getBean("categoryService");
		this.pageService = (PageService) contextHelper.getBean("pageService");
	}
	/**
	 * render category tree screen to assign category to page
	 * @param siteId
	 * @param tabSheet
	 * @param pageId
	 * @param categoryLabel
	 */
	public void renderTreeToAssign(final Integer siteId,
			final TabSheet tabSheet, final Integer pageId,final Label categoryLabel){
		
		this.tabSheet = tabSheet;
		this.pageId = pageId;
		pageDto = pageService.findPageBySiteId(siteId, pageId);
		categories = pageDto.getCategories();
		renderCategory();
		categoryLabel.setValue("Category: "+name);
		horizontalLayout.addComponent(assignCategoryButton);
		assignCategoryButton.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				
        		CategoryDto categoryDto = categoryService.findCategoryByName(name,SessionHelper.);
        		//check if category already assign to the page if it is assigned then no action will take place
        		for( CategoryDto cat : categories){
        			if(cat.getCategoryName().equals(name)){
        				
        				parentWindow.showNotification("Category "+ name +" is already assigned to "
    							+ pageDto.getTitle()+" page" );
        				return;
        			}
        		}
        			
        		categories.add(categoryDto);
        		pageDto.setCategories(categories);
        		
        		try {
        			
					pageService.update(pageDto);
					parentWindow.showNotification("Category "+ name +" is successfully assigned to "
								+ pageDto.getTitle()+" page" );
				} catch (EntityAlreadyFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
  
			}
		});
		
	}
	/**
	 * render category screen to add some new categories to tree.
	 * @param siteId
	 * @param tabSheet
	 * @param pageId
	 */
	public void renderTreeToAddNewCategory(final Integer siteId,
			final TabSheet tabSheet, final Integer pageId){
		this.tabSheet = tabSheet;
		this.pageId = pageId;
		addCategoryEnable = true; //allow to addCategry
		renderCategory();

		renameCategoryButton.setEnabled(false);
		horizontalLayout.addComponent(renameCategoryButton);

		renameCategoryButton.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				// String name=(String) selectedCategoryField.getValue();
				if (!name.equals("")) {
					/* parent data */
					Object parentId = categoryTree.getParent(categoryTree
							.getValue());
					Item parentItem = categoryTree.getItem(parentId);
					String parentName = (String) parentItem.getItemProperty(
							"name").getValue();
					/* child data */
					Item item = categoryTree.getItem(categoryTree.getValue());
					item.getItemProperty("name").setValue(name);

					CategoryDto parentCategoryDto = categoryService
							.findCategoryByName(parentName);

					try {
						CategoryDto categoryDto = new CategoryDto();
						categoryDto.setCategoryId(null);
						categoryDto.setCategoryName(name);
						categoryDto.setParent(parentCategoryDto);
						categoryDto.setChild(null);
						categoryService.create(categoryDto);
					} catch (EntityAlreadyFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					assignCategoryButton.setEnabled(true);
					renameCategoryButton.setEnabled(false);
				}

			}
		});
	}

	/**
	 * render category screen 
	 */
	private void renderCategory(){

		verticalLayout.addComponent(categoryFormLayout);
		categoryFormLayout.setSpacing(true);

		Collection<CategoryDto> categoryDto = categoryService.findNullParentIdCategory();
		categoryTree = new Tree("Categories");// creating tree
		if(addCategoryEnable)
			categoryTree.addActionHandler(this);
		categoryTree.setImmediate(true);
		categoryTree.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
		categoryTree.setItemCaptionPropertyId("name");
		categoryContainer = getParentCategories(categoryDto);
		categoryTree.setContainerDataSource(categoryContainer);
		categoryFormLayout.addComponent(categoryTree);
		horizontalLayout.addComponent(selectedCategoryField);
		horizontalLayout.setSpacing(true);
		selectedCategoryField.setEnabled(false);
		categoryFormLayout.addComponent(horizontalLayout);
		
		categoryTree.addListener(new ItemClickListener() {
			private static final long serialVersionUID = -4607219466099528006L;
        	public void itemClick(ItemClickEvent event) {

        		categoryTree.expandItem(event.getItemId());
        		Integer itemId = (Integer) event.getItemId();
        		/* finding category which is slected in tree*/
       		 	name = (String) categoryTree.getContainerProperty(itemId, "name").getValue();
        		Collection<CategoryDto> childCategoryDtoList = categoryService.findChildCategories(itemId);
        		//Check if the itemId is for a directory
        		if (itemId!=null && !(name.equals("New Item"))){
        			Item parentItem = event.getItem();
        			//adding child categories in tree
        			addChildrenToCategoryTree(parentItem,childCategoryDtoList);
        			selectedCategoryField.setEnabled(true);
        		
        		}//end if
        		
        		
        		if(name.equals("New Item")){
        			renameCategoryButton.setEnabled(true);
        			assignCategoryButton.setEnabled(false);
        			selectedCategoryField.setEnabled(true);
        		}

            	selectedCategoryField.setValue(name);
        		
        	}//end 	itemClick
        });
		
		final String pageTabTitle = "New Category";
		Tab newPageTab = tabSheet.addTab(verticalLayout, pageTabTitle, null);
		tabSheet.setSelectedTab(verticalLayout);
		newPageTab.setVisible(true);
		newPageTab.setEnabled(true);
		newPageTab.setClosable(true);
		
	}//end renderCategory()

	/**
	 * return the set of available actions
	 * @param target
	 * @param sender
	 */
    public Action[] getActions(Object target, Object sender) {
        return CATEGORY_ACTION;
    }
    
    /**
     * handle actions
     * @param action
     * @param sender
     * @param target
     */
    public void handleAction(Action action, Object sender, Object target) {
       
    	if(action.equals(ADD_CATEGORY)){
    		 categoryTree.setChildrenAllowed(target, true);
    		 categoryTree.expandItem(target);
             // Create new item, set parent, disallow children (= leaf node)
             Object itemId = categoryTree.addItem();
             categoryTree.setParent(itemId, target);
             categoryTree.setChildrenAllowed(itemId, false);
             // Set the name for this item (we use it as item caption)
             Item item = categoryTree.getItem(itemId);
             item.getItemProperty("id").setValue("-1");
             item.getItemProperty("name").setValue("New Item");
    	}	
    	
    }

	/**	
	 * Returns a Container with all the Parent Categories.
	 * @param categoryList
	 * @return
	 */
	private HierarchicalContainer getParentCategories(
			final Collection<CategoryDto> categoryList) {
		final HierarchicalContainer container = new HierarchicalContainer();
		container.addContainerProperty("id", Integer.class, null);
		container.addContainerProperty("name", String.class, null);

		for(CategoryDto categoryDto :categoryList){
			Integer catId = categoryDto.getCategoryId();
			Item categoryItem = container.addItem(catId);
			categoryItem.getItemProperty("name").setValue(
					categoryDto.getCategoryName());
			categoryItem.getItemProperty("id").setValue(catId);
			container.setChildrenAllowed(catId, true);
			
		}//end for
		
		container.sort(new Object[] { "Categories" }, new boolean[] { true });
		return container;
	}//end getParentCategories()
	
	/**
	 * Add child categories to categoryTree using their parent info
	 * 
	 * @param parentItem
	 * @param categoryService
	 * @param categoryContainer
	 * 
	 */
	
	private void addChildrenToCategoryTree(final Item parentItem,
			final Collection<CategoryDto> childCategoryDtoList){
		Integer parentCategoryId = Integer.parseInt(parentItem.getItemProperty("id").getValue().toString());
		//String name = parentItem.getItemProperty("name").getValue().toString();

		for (CategoryDto childCategoryDto: childCategoryDtoList){
				Integer itemToAdd = childCategoryDto.getCategoryId();
				if (null==categoryContainer.getItem(itemToAdd)) {
					Item item = categoryContainer.addItem(itemToAdd);
					item.getItemProperty("id").setValue(itemToAdd);
					item.getItemProperty("name").setValue(childCategoryDto.getCategoryName());
					categoryContainer.setParent(childCategoryDto.getCategoryId(), parentCategoryId);
					categoryContainer.setChildrenAllowed(childCategoryDto.getCategoryId(), true);
				}//end if
			}//end for
		
	}//end addChildrenToCategoryTree()

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Component render(String command) {
		// TODO Auto-generated method stub
		return null;
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
	
}
