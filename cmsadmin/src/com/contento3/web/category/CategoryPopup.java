package com.contento3.web.category;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.service.CategoryService;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityNotFoundException;
import com.contento3.web.common.helper.AbstractTreeTableBuilder;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

public class CategoryPopup extends CustomComponent
implements Window.CloseListener,Button.ClickListener {

	private static final Logger LOGGER = Logger.getLogger(CategoryPopup.class);

	private static final long serialVersionUID = 1L;

    Window popupWindow;    // The window to be opened
    Button openbutton;  // Button for opening the window
    Button closebutton; // A button in the window
    Button editButton;
    
    private final CategoryService categoryService;

    boolean isModalWindowClosable = true;
    
    SpringContextHelper helper;
    
    private Integer categoryId;

    final TreeTable categoryTable;
    
    final TabSheet tabSheet;
    
    /**
     * Renders the tree with categories for Parent category selection.
     */
    final Tree tree;
    
    Integer selectedParentCategory = -1;
    
    final VerticalLayout parentLayout;
    
    public CategoryPopup(final SpringContextHelper helper,final TreeTable table,final TabSheet tabSheet) {
        this.helper = helper;
        this.categoryService = (CategoryService)helper.getBean("categoryService");
        this.tabSheet = tabSheet;
        this.categoryTable = table;
        tree = new Tree();
        // The component contains a button that opens the window.
        parentLayout = new VerticalLayout();
        openbutton = new Button("Add Category");
        openbutton.addClickListener(this);
        parentLayout.addComponent(openbutton);

        setCompositionRoot(parentLayout);
    }

    /** Handle the clicks for the two buttons. */
    public void openButtonClick(Button.ClickEvent event) {
        
    	/* Create a new window. */
        final Button categoryButton = new Button();
		popupWindow = new Window();
    	
		popupWindow.setPositionX(200);
    	popupWindow.setPositionY(100);

    	popupWindow.setHeight(56,Unit.PERCENTAGE);
    	popupWindow.setWidth(30,Unit.PERCENTAGE);
       
    	/* Add the window inside the main window. */
    	UI.getCurrent().addWindow(popupWindow);
        
        /* Listen for close events for the window. */
        popupWindow.addCloseListener(this);
        popupWindow.setModal(true);
        /* Reset old selected category. */
        selectedParentCategory = -1;
        
        final VerticalLayout popupMainLayout = new VerticalLayout();
        final Label categoryLbl = new Label("Name");
        final HorizontalLayout inputDataLayout = new HorizontalLayout();
        final TextField categoryNameTxtField = new TextField("");
        categoryNameTxtField.setInputPrompt("Enter Category Name");
        
        final TextArea categoryDescriptionTxtField = new TextArea("");
        categoryDescriptionTxtField.setInputPrompt("Enter Description Name");
        
        final Label parentCategoryLbl = new Label("<b>Select Parent Category</b>", ContentMode.HTML);
        inputDataLayout.setSpacing(true);
        inputDataLayout.setMargin(true);
        inputDataLayout.addComponent(categoryLbl);
        inputDataLayout.setComponentAlignment(categoryLbl, Alignment.BOTTOM_LEFT);
        inputDataLayout.addComponent(categoryNameTxtField);
        inputDataLayout.setComponentAlignment(categoryNameTxtField, Alignment.BOTTOM_LEFT);

        popupMainLayout.addComponent(inputDataLayout);
        popupMainLayout.setSpacing(true);
        popupMainLayout.addComponent(parentCategoryLbl);
        popupMainLayout.setMargin(true);
        popupWindow.setContent(popupMainLayout);
        popupWindow.setResizable(false);
        /* Allow opening only one window at a time. */
        openbutton.setEnabled(false);

    	if (event.getButton().getCaption().equals("Edit")){
	        categoryButton.setCaption("Save");
	        popupWindow.setCaption("Edit Category");
	        categoryId = (Integer)event.getButton().getData();
	        CategoryDto categoryDto;
	        
			try {
				categoryDto = categoryService.findById(categoryId);
				  categoryNameTxtField.setValue(categoryDto.getName());
			} catch (EntityNotFoundException e) {
				LOGGER.debug(e.getMessage());
			}
	      
	        buildTree(popupMainLayout);
	        categoryButton.addClickListener(new ClickListener() {
				private static final long serialVersionUID = 1L;
				public void buttonClick(ClickEvent event) {
					if (categoryId==selectedParentCategory){
						Notification.show("Parent category cannot be the same the current category you are editing");
					}
					handleEditCategory(categoryNameTxtField,categoryId);
				}	
			});
    	}
    	else
    	{
    		categoryButton.setCaption("Add");
	        popupWindow.setCaption("Add Category");
	        buildTree(popupMainLayout);
	        categoryButton.addClickListener(new ClickListener() {
				private static final long serialVersionUID = 1L;
				public void buttonClick(ClickEvent event) {
					handleNewCategory(categoryNameTxtField);
				}	
			});
    	}

        final HorizontalLayout addButtonLayout = new HorizontalLayout();
        popupMainLayout.addComponent(addButtonLayout);

        addButtonLayout.addComponent(categoryButton);
        addButtonLayout.setComponentAlignment(categoryButton, Alignment.BOTTOM_RIGHT);
        addButtonLayout.setWidth(100, Unit.PERCENTAGE);
    }

	private void buildTree(final VerticalLayout parentLayout) {
		Collection<CategoryDto> categoryDtos;
		try {
			categoryDtos = categoryService
					.findNullParentIdCategory((Integer) SessionHelper
							.loadAttribute("accountId"));

			HierarchicalContainer container = new HierarchicalContainer();
			container.addContainerProperty("category", String.class, null);
			container.addContainerProperty("select", CheckBox.class, null);

			tree.setContainerDataSource(categoryTable.getContainerDataSource());
			tree.setItemCaptionPropertyId("category");

			Integer categoryId;
			for (CategoryDto dto : categoryDtos) {
				categoryId = dto.getCategoryId();
				if (null == container.getItem(categoryId)) {
					Item item = container.addItem(categoryId);
					item.getItemProperty("category").setValue(dto.getName());
					item.getItemProperty("select").setValue(new CheckBox());

					final Collection<CategoryDto> children = dto.getChild();
					if (!CollectionUtils.isEmpty(children)) {
						for (CategoryDto categoryChild : children) {
							Item childItem = container.addItem(categoryChild
									.getCategoryId());
							childItem.getItemProperty("category").setValue(
									categoryChild.getName());
							childItem.getItemProperty("select").setValue(
									new CheckBox());

							container.setParent(categoryChild.getCategoryId(),
									categoryId);
							container.setChildrenAllowed(
									categoryChild.getCategoryId(), true);
						}
					}
				}
			}
		} catch (EntityNotFoundException e) {
			LOGGER.debug(e.getMessage());
		}
		parentLayout.addComponent(tree);

		tree.setImmediate(true);
		tree.addItemClickListener(new ItemClickListener() {
			private static final long serialVersionUID = -4607219466099528006L;

			public void itemClick(ItemClickEvent event) {
				String itemId = event.getItemId().toString();
				tree.select(itemId);
				tree.expandItem(itemId);
				selectedParentCategory = Integer.valueOf(itemId);
			}
		});
	}
    
    /**
     * Handles adding new SiteDomain
     * @param textField
     */
	private void handleNewCategory(final TextField textField){
		final CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName(textField.getValue().toString());
		categoryDto.setAccountId((Integer)SessionHelper.loadAttribute("accountId"));
		try {
		if (selectedParentCategory>0){
//			final CategoryDto parentCategory = categoryService.findById(selectedParentCategory);
//			categoryDto.setParent(parentCategory);
			categoryService.create(categoryDto, selectedParentCategory);
		}else{
			categoryService.create(categoryDto, null);
		}
			resetTable();
		} catch (EntityAlreadyFoundException e) {
			Notification.show("Category already found.");
		}
		UI.getCurrent().removeWindow(popupWindow);
    }

    /**
     * Handles adding new SiteDomain
     * @param textField
     */
	private void handleEditCategory(final TextField categoryNameTxtField,final Integer categoryId){
		CategoryDto updatedCategoryDto;
		try {
			updatedCategoryDto = categoryService.findById(categoryId);
	
			updatedCategoryDto.setName(categoryNameTxtField.getValue().toString());
			updatedCategoryDto.setAccountId((Integer)SessionHelper.loadAttribute("accountId"));
			if (selectedParentCategory>0){
				categoryService.update(updatedCategoryDto,selectedParentCategory);
			}
			else {
				categoryService.update(updatedCategoryDto,null);
			}
			resetTable();
			UI.getCurrent().removeWindow(popupWindow);
		} catch (EntityNotFoundException e) {
			LOGGER.debug(e.getMessage());
		}
    }

    @SuppressWarnings("rawtypes")
	private void resetTable(){
		final AbstractTreeTableBuilder tableBuilder = new CategoryTableBuilder(helper,tabSheet,categoryTable);
		Collection<CategoryDto> updatedCategoryDto;
		try {
			updatedCategoryDto = categoryService.findNullParentIdCategory((Integer)SessionHelper.loadAttribute("accountId"));
			tableBuilder.rebuild((Collection)updatedCategoryDto);
			parentLayout.removeComponent(popupWindow);
			openbutton.setEnabled(true);
		} catch (EntityNotFoundException e) {
			LOGGER.debug(e.getMessage());
		}
    }
    
    /** Handle Close button click and close the window. */
    public void closeButtonClick(Button.ClickEvent event) {
    	//if (!isModalWindowClosable){
        /* Windows are managed by the application object. */
        parentLayout.removeComponent(popupWindow);
        UI.getCurrent().removeWindow(popupWindow);
        
        /* Return to initial state. */
        openbutton.setEnabled(true);
    	//}
    }

    /** In case the window is closed otherwise. */
    public void windowClose(CloseEvent e) {
        /* Return to initial state. */
        openbutton.setEnabled(true);
    }

	@Override
	public void buttonClick(ClickEvent event) {
		this.openButtonClick(event);
	}

}