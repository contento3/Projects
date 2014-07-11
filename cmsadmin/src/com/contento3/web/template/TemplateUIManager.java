package com.contento3.web.template;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.util.CollectionUtils;
import org.vaadin.aceeditor.AceEditor;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.peter.contextmenu.ContextMenu;
import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuItem;
import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuItemClickEvent;
import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuItemClickListener;
import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuOpenedListener;
import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuOpenedOnTreeItemEvent;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountService;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.cms.page.template.dto.TemplateDirectoryDto;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.service.TemplateDirectoryService;
import com.contento3.cms.page.template.service.TemplateService;
import com.contento3.cms.page.templatecategory.service.TemplateCategoryService;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.web.UIManager;
import com.contento3.web.common.helper.ScreenHeader;
import com.contento3.web.common.helper.ScreenToolbarBuilder;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.content.image.ImageLoader;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.template.listener.AddTemplateButtonListener;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.ServerSideCriterion;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Tree.TreeDragMode;
import com.vaadin.ui.Tree.TreeTargetDetails;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class TemplateUIManager implements UIManager {

	TemplateCategoryService templateCategoryService;
	
	private static final String CONTEXT_ITEM_RENAME = "Rename directory";

	private static final String CONTEXT_ITEM_CREATE = "Create new directory";

	private static final String CONTEXT_ITEM_CREATE_TEMPLATE = "Create new template";

	private static final String CONTEXT_ITEM_OPEN = "Open template";

	private static final String CONTEXT_ITEM_CLEAR_CACHE = "Clear Cache";

	private static final String CONTEXT_ITEM_MOVE_DIRECTORY = "Move directory";

	private static final String CONTEXT_ITEM_DELETE_DIRECTORY = "Delete directory";

	/**
	 * Logger for Template
	 */
	private static final Logger LOGGER = Logger
			.getLogger(TemplateUIManager.class);

	/**
	 * Used to get service beans from spring context.
	 */
	private SpringContextHelper helper;

	/**
	 * Template Service to access template related services.
	 */
	private TemplateService templateService;

	/**
	 * Template Directory Service to access template directory related services.
	 */
	private TemplateDirectoryService templateDirectoryService;

	/**
	 * Represents the root object of a tree that contains template and template
	 * directories.
	 */
	private Tree root;

	/**
	 * Used to hold the id of currently selected directory from the tree.
	 */
	private Integer selectedDirectoryId;

	private Integer selectedTreeItemId = -1;
	/**
	 * Used to hold the account id
	 */

	private Integer accountId;

	TabSheet templateTab;

	boolean selectedTemplateDirScope;

	/**
	 * Container for global templates
	 * 
	 */
	final HierarchicalContainer globalTemplateDirContainer;

	/**
	 * Container for local templates
	 */
	final HierarchicalContainer localTemplateDirContainer;

	private ImageLoader imageLoader;

	String itemId;

	final HorizontalLayout mainContent = new HorizontalLayout();

	/**
	 * Constructor
	 * 
	 * @param helper
	 * @param parentWindow
	 */
	public TemplateUIManager(final TabSheet uiTabSheet,
			final SpringContextHelper helper) {
		this.helper = helper;
		this.templateService = (TemplateService) helper.getBean("templateService");
		this.templateDirectoryService = (TemplateDirectoryService) helper.getBean("templateDirectoryService");
		this.templateCategoryService = (TemplateCategoryService) helper.getBean("templateCategoryService");
		
		// Get accountId from the session
		this.accountId = (Integer) SessionHelper.loadAttribute("accountId");
		this.globalTemplateDirContainer = new HierarchicalContainer();
		this.localTemplateDirContainer = new HierarchicalContainer();
		this.templateTab = uiTabSheet;
		this.imageLoader = new ImageLoader();
	}

	@Override
	public void render() {
	}

	@Override
	public Component render(String command) {

		templateTab.setHeight(100, Unit.PERCENTAGE);
		templateTab.setWidth(100, Unit.PERCENTAGE);

		final VerticalLayout layout = new VerticalLayout();
		layout.setWidth(100, Unit.PERCENTAGE);
		layout.setHeight(100, Unit.PERCENTAGE);

		Tab tab2 = templateTab.addTab(layout, "Template", new ExternalResource(
				"images/template.png"));
		tab2.setClosable(true);

		// Create the Accordion.
		final Accordion accordion = new Accordion();
		accordion.setWidth(80, Unit.PERCENTAGE);
		accordion.setHeight(100, Unit.PERCENTAGE);

		renderTemplateListTab(layout, accordion);
		return templateTab;
	}

	@Override
	public Component render(final String command, final Integer entityFilterId) {
		return null;
	}

	public void renderTemplateListTab(final VerticalLayout vLayout,
			final Accordion accordion) {

		populateAccordion(accordion);

		// A container for the template ui.
		final Panel panel = new Panel();
		panel.setWidth(20, Unit.PERCENTAGE);
		panel.setHeight(100, Unit.PERCENTAGE);

		final Embedded iconTemplate = imageLoader
				.loadEmbeddedImageByPath("images/template.png");
		iconTemplate.setDescription("Add template");
		iconTemplate.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			public void click(ClickEvent event) {
				renderTemplate(null);
			}
		});

		final Embedded iconDirectory = imageLoader
				.loadEmbeddedImageByPath("images/add-directory.png");
		iconDirectory.setDescription("Add directory");
		iconDirectory.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			public void click(ClickEvent event) {

				try {
					renderFolderTab(selectedDirectoryId);
				} catch (final Exception e) {
					if (root.getItemIds().isEmpty()) {
						renderFolderTab(null);
					}
				}
			}
		});
		final Embedded addTeCat = imageLoader.loadEmbeddedImageByPath("images/addcategory.png");
        addTeCat.setDescription("Add template category");

        addTeCat.addClickListener(new ClickListener() {
                    private static final long serialVersionUID = 1L;
                    public void click(ClickEvent event) {
                    	UI.getCurrent().addWindow(new NewTemplateCategoryPopup(helper));
                    }
            });	
		final Embedded deleteIcon = imageLoader
				.loadEmbeddedImageByPath("images/delete_template.png");
		deleteIcon.setDescription("Delete template");
		deleteIcon.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			public void click(ClickEvent event) {
				if(selectedTreeItemId == -1){
					Notification.show("Please Select", "Please select any template to Delete",
							Notification.Type.TRAY_NOTIFICATION);
					return;
				}
				TemplateDto templateToDelete = templateService.findTemplateById(selectedTreeItemId);
				UI.getCurrent()
				.addWindow(new DeleteTemplatePopup(helper, templateToDelete));
			}
		});

		final HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.addComponent(iconTemplate);
		buttonLayout.addComponent(deleteIcon);
		buttonLayout.addComponent(iconDirectory);
		buttonLayout.addComponent(addTeCat);
		buttonLayout.setHeight(100, Unit.PERCENTAGE);
		buttonLayout.setSpacing(true);
		buttonLayout.setWidth(175, Unit.PIXELS);

		final VerticalLayout panelContent = new VerticalLayout();
		panelContent.setHeight(100, Unit.PERCENTAGE);
		panelContent.setWidth(200, Unit.PIXELS);
		panelContent.addComponent(accordion);
		panelContent.addComponent(buttonLayout);
		panelContent.setExpandRatio(accordion, 90);
		panelContent.setExpandRatio(buttonLayout, 1);

		mainContent.addComponent(panelContent);
		mainContent.setExpandRatio(panelContent, 1);
		mainContent.setWidth(100, Unit.PERCENTAGE);
		mainContent.setHeight(90, Unit.PERCENTAGE);

		vLayout.addComponent(mainContent);
		vLayout.setHeight(100, Unit.PERCENTAGE);
		vLayout.setWidth(100, Unit.PERCENTAGE);
	}

	public void populateAccordion(final Accordion accordion) {

		// Populate the global template list
		final VerticalLayout globalTemplateListLayout = new VerticalLayout();
		globalTemplateListLayout.setHeight(80, Unit.PERCENTAGE);
		final Tab globalTemplatesTab = accordion.addTab(
				globalTemplateListLayout, "Global Templates",
				new ExternalResource("images/global-template-16.png"));
		accordion.addTab(globalTemplateListLayout, "Global Templates",
				new ExternalResource("images/global-template-16.png"));
		globalTemplatesTab.setClosable(true);
		
		// The global templates are only associated to accountId=1, this means
		// either this is contento3 account or if separately hosted means single account
		// This means we are not going to use the accountId from session for global templates. 
		// Hard coded to accountId = 1
		final Collection<TemplateDirectoryDto> globalTemplateDirectoryList = templateDirectoryService.findRootDirectories(true,1);
		globalTemplateListLayout.addComponent(populateTemplateList(
				globalTemplateDirectoryList, templateDirectoryService, true));

		// Populate the template list
		final VerticalLayout templateListLayout = new VerticalLayout();
		templateListLayout.setHeight(80, Unit.PERCENTAGE);
		final Tab templatesTab = accordion.addTab(templateListLayout,
				"Templates", new ExternalResource("images/add-template-16.png"));
		final Collection<TemplateDirectoryDto> templateDirectoryList = templateDirectoryService
				.findRootDirectories(false,accountId);
		accordion.setSelectedTab(templateListLayout);

		// Add the tree to the vertical layout for template list.
		templateListLayout.addComponent(populateTemplateList(
				templateDirectoryList, templateDirectoryService, false));
	}

	public Tree populateTemplateList(
			final Collection<TemplateDirectoryDto> directoryDtos,
			final TemplateDirectoryService templateDirectoryService,
			final boolean isGlobalDir) {
		final HierarchicalContainer templateContainer;
		if (isGlobalDir)
			templateContainer = globalTemplateDirContainer;
		else
			templateContainer = localTemplateDirContainer;

		root = new Tree("", templateContainer);
		root.setImmediate(true);
		root.setItemCaptionPropertyId("name");

		final ContextMenuOpenedListener.TreeListener treeItemListener = new ContextMenuOpenedListener.TreeListener() {
			public void onContextMenuOpenFromTreeItem(
					final ContextMenuOpenedOnTreeItemEvent event) {
				itemId = event.getItemId().toString();

				final ContextMenu contextMenu = event.getContextMenu();
				contextMenu.removeAllItems();
				if (itemId.startsWith("file:")) {
					contextMenu.addItem(CONTEXT_ITEM_OPEN).setData(
							CONTEXT_ITEM_OPEN);
					contextMenu.addItem(CONTEXT_ITEM_CREATE_TEMPLATE).setData(
							CONTEXT_ITEM_CREATE_TEMPLATE);
					contextMenu.addItem(CONTEXT_ITEM_CLEAR_CACHE).setData(
							CONTEXT_ITEM_CLEAR_CACHE);
				} else {
					contextMenu.addItem(CONTEXT_ITEM_CREATE).setData(
							CONTEXT_ITEM_CREATE);
					contextMenu.addItem(CONTEXT_ITEM_RENAME).setData(
							CONTEXT_ITEM_RENAME);
					contextMenu.addItem(CONTEXT_ITEM_DELETE_DIRECTORY).setData(
							CONTEXT_ITEM_DELETE_DIRECTORY);
					contextMenu.addItem(CONTEXT_ITEM_MOVE_DIRECTORY).setData(
							CONTEXT_ITEM_MOVE_DIRECTORY);
					contextMenu.addItem(CONTEXT_ITEM_CREATE_TEMPLATE).setData(
							CONTEXT_ITEM_CREATE_TEMPLATE);
				}
			}
		};

		final ContextMenuItemClickListener itemClickListener = new ContextMenu.ContextMenuItemClickListener() {
			public void contextMenuItemClicked(final ContextMenuItemClickEvent event) {

				final ContextMenuItem source = (ContextMenuItem) event.getSource();
				final String itemName = (String) source.getData();

				// Open the template
				if (itemId.startsWith("file:")) {
					if (itemName.equals(CONTEXT_ITEM_OPEN)) {
						renderTemplate(new Integer(itemId.substring(5)));
						itemId = null;
					} else if (itemName.equals(CONTEXT_ITEM_CREATE_TEMPLATE)) {
						renderTemplate(null);
						itemId = null;
					} else if (itemName.equals(CONTEXT_ITEM_CLEAR_CACHE)) {
						clearTemplateCache(new Integer(itemId.substring(5)));
						itemId = null;
					}
				} else {
					if (itemName.equals(CONTEXT_ITEM_RENAME)) {
						final TemplateDirectoryDto dto = templateDirectoryService.findById(Integer.parseInt(itemId));
						renameDirectory(dto);
						itemId = null;
					} else if (itemName.equals(CONTEXT_ITEM_CREATE)) {
						renderFolderTab(null);
					} else if (itemName.equals(CONTEXT_ITEM_CREATE_TEMPLATE)) {
						renderTemplate(null);
						itemId = null;
					}
					else if (itemName.equals(CONTEXT_ITEM_MOVE_DIRECTORY)) {
						renderMoveDirectory(itemId);
						itemId = null;
					}
					else if (itemName.equals(CONTEXT_ITEM_DELETE_DIRECTORY)) {
						renderDeleteDirectory(selectedTreeItemId);
						itemId = null;
					}
				}
			}
		};

		final ContextMenu contextMenu = new ContextMenu();
		contextMenu.setAsContextMenuOf(root);
		contextMenu.addContextMenuTreeListener(treeItemListener);
		contextMenu.addItemClickListener(itemClickListener);

		/**
		 * Move template code
		 * */
		root.setDragMode(TreeDragMode.NODE);
		root.setDropHandler(new DropHandler() {
			private static final long serialVersionUID = 1L;

			@Override
			public AcceptCriterion getAcceptCriterion() {
				final ServerSideCriterion serverCriteria = new ServerSideCriterion() {
					private static final long serialVersionUID = 1L;

					@Override
					public boolean accept(DragAndDropEvent dragEvent) {
						final String sourceId = dragEvent.getTransferable()
								.getData("itemId").toString();
						if (sourceId.startsWith("file:"))
							return true;
						else
							return false;
					}
				};

				return serverCriteria;
			}

			@Override
			public void drop(DragAndDropEvent event) {
				// Wrapper for the object that is dragged
				Transferable t = event.getTransferable();

				// Make sure the drag source is the same tree
				if (t.getSourceComponent() != root)
					return;

				TreeTargetDetails target = (TreeTargetDetails) event
						.getTargetDetails();

				// Get ids of the dragged item and the target item
				Object sourceItemId = t.getData("itemId");
				Object targetItemId = target.getItemIdOver();

				// On which side of the target the item was dropped
				VerticalDropLocation location = target.getDropLocation();

				HierarchicalContainer container = (HierarchicalContainer) root
						.getContainerDataSource();

				// Drop right on an item -> make it a child
				if (location == VerticalDropLocation.MIDDLE) {
					root.setParent(sourceItemId, targetItemId);
				}

				// Drop at the top of a subtree -> make it previous
				else if (location == VerticalDropLocation.TOP) {
					Object parentId = container.getParent(targetItemId);
					container.setParent(sourceItemId, parentId);
					container.moveAfterSibling(sourceItemId, targetItemId);
					container.moveAfterSibling(targetItemId, sourceItemId);
				}

				// Drop below another item -> make it next
				else if (location == VerticalDropLocation.BOTTOM) {
					Object parentId = container.getParent(targetItemId);
					container.setParent(sourceItemId, parentId);
					container.moveAfterSibling(sourceItemId, targetItemId);
				}

				// Modify parent inside the template
				TemplateDto templateDto = templateService
						.findTemplateById(Integer.parseInt(sourceItemId
								.toString().substring(5)));
				templateDto.setTemplateDirectoryDto(templateDirectoryService
						.findById((Integer) targetItemId));
				try {
					templateService.updateTemplate(templateDto);
					Notification.show("Notification", "Test description",
							Notification.Type.TRAY_NOTIFICATION);
				} catch (EntityAlreadyFoundException e) {
					LOGGER.info("Unable to update templateDto with id:"
							+ templateDto.getTemplateId());
				}
			}
		});

		templateContainer.addContainerProperty("id", Integer.class, null);
		templateContainer.addContainerProperty("fileid", String.class, null);
		templateContainer.addContainerProperty("name", String.class, null);

		Item item;
		root.addContainerProperty("icon", Resource.class, null);
		root.setItemIconPropertyId("icon");

		for (TemplateDirectoryDto directoryDto : directoryDtos) {
			item = templateContainer.addItem(directoryDto.getId());
			item.getItemProperty("id").setValue(directoryDto.getId());
			item.getItemProperty("name").setValue(
					directoryDto.getDirectoryName());
			templateContainer.setChildrenAllowed(directoryDto.getId(), true);
			item.getItemProperty("icon").setValue(
					new ExternalResource("images/directory.png"));
		}

		root.expandItem(new Integer(1));
		root.addItemClickListener(new ItemClickListener() {
			private static final long serialVersionUID = -4607219466099528006L;

			public void itemClick(ItemClickEvent event) {
				
				root.expandItem(event.getItemId());
				String itemId = event.getItemId().toString();
				// Check if the itemId is for a directory
				if (!itemId.startsWith("file:")) {
					selectedTreeItemId = -1;
					Item parentItem = event.getItem();
					addChildrenToSelectedDirectory(parentItem,
							templateDirectoryService, templateContainer);
				} else {
					selectedTreeItemId = new Integer(itemId.substring(5));
					// renderTemplate(new Integer(itemId.substring(5)));
				}
			}
		});
		return root;
	}

	private void renameDirectory(final TemplateDirectoryDto dtoToUpdate) {
		UI.getCurrent().addWindow(new RenameDirectoryPopup(helper, dtoToUpdate));
	}

	private void renderMoveDirectory(final String itemToMoveId){
		UI.getCurrent().addWindow(new TemplateDirectoryPopup(helper,itemToMoveId));
	}

	private void renderDeleteDirectory(final Integer itemToDelete){
			if (!CollectionUtils.isEmpty(templateService.findTemplateByDirectoryId(this.selectedDirectoryId))){
				Notification.show("Directory canot be deleted as it contains templates.",Notification.Type.TRAY_NOTIFICATION);
			}
			else {				
				try {
					templateDirectoryService.delete(this.selectedDirectoryId);
					Notification.show("Directory deleted succesfully.",Notification.Type.TRAY_NOTIFICATION);
				} catch (final EntityCannotBeDeletedException e) {
					LOGGER.info("Unable to delete directory");
				}
			}	
	}

	private void addChildrenToSelectedDirectory(final Item parentItem,
			final TemplateDirectoryService templateDirectoryService,
			final HierarchicalContainer templateContainer) {
		selectedDirectoryId = Integer.parseInt(parentItem.getItemProperty("id")
				.getValue().toString());
		String name = parentItem.getItemProperty("name").getValue().toString();

		Collection<TemplateDirectoryDto> templateDirectoryDtoList = templateDirectoryService
				.findChildDirectories(selectedDirectoryId,accountId);

		for (TemplateDirectoryDto templateDirectoryDto : templateDirectoryDtoList) {
			Integer itemToAdd = templateDirectoryDto.getId();
			if (null == templateContainer.getItem(itemToAdd)) {
				Item item = templateContainer.addItem(itemToAdd);
				item.getItemProperty("id").setValue(itemToAdd);
				item.getItemProperty("name").setValue(
						templateDirectoryDto.getDirectoryName());
				templateContainer.setParent(templateDirectoryDto.getId(),
						selectedDirectoryId);
				templateContainer.setChildrenAllowed(
						templateDirectoryDto.getId(), true);

				item.getItemProperty("icon").setValue(
						new ExternalResource("images/directory.png"));
			}
		}

		Collection<TemplateDto> templateDtoList = templateService
				.findTemplateByDirectoryId(selectedDirectoryId);

		for (TemplateDto templateDto : templateDtoList) {
			String templateItemId = String.format("file:%d",
					templateDto.getTemplateId());
			if (null == templateContainer.getItem(templateItemId)) {
				Item item = templateContainer.addItem(templateItemId);
				item.getItemProperty("fileid").setValue(templateItemId);
				item.getItemProperty("name").setValue(
						templateDto.getTemplateName());
				templateContainer.setParent(
						String.format("file:%d", templateDto.getTemplateId()),
						selectedDirectoryId);
				templateContainer.setChildrenAllowed(
						String.format("file:%d", templateDto.getTemplateId()),
						false);
				item.getItemProperty("icon").setValue(
						new ExternalResource("images/add-template-16.png"));
			}
		}
	}

	private void clearTemplateCache(final Integer templateId) {
		templateService.clearCache(templateId);
	}

	private void renderTemplate(final Integer templateId) {
		try {
			TemplateDto templateDto = null;
			if (null != templateId) {
				templateDto = templateService.findTemplateById(templateId);
				createTemplateUI(templateDto, templateDto.getTemplateDirectoryDto());
			} else {
				// This is a new template to be created we need to get the
				// directory which was selected.
				if (null == selectedDirectoryId) {
					if (root.getItemIds().isEmpty()) {
						Notification
								.show(String
										.format("Please Create template directory to create a new template."),
										Notification.Type.TRAY_NOTIFICATION);
					} else {
						Notification
								.show(String
										.format("Please select template directory to create a new template."),
										Notification.Type.TRAY_NOTIFICATION);
					}
				} else {
					final TemplateDirectoryDto directoryDto = templateDirectoryService
							.findById(selectedDirectoryId);
					createTemplateUI(templateDto, directoryDto);
				}
			}
		} catch (final Exception exception) {
			LOGGER.fatal("Error in templateui manager", exception);
		}
	}

	private void createTemplateUI(final TemplateDto templateDto,
			final TemplateDirectoryDto selectedDirectory) {
		final TemplateForm form = new TemplateForm(this.helper, templateCategoryService);
		final VerticalLayout createNewTemplate = new VerticalLayout();

		final VerticalLayout templateEditorLayout = new VerticalLayout();
		templateEditorLayout.setMargin(true);

		final ScreenHeader screenHeader = new ScreenHeader(
				templateEditorLayout, "Templates");
		final HorizontalLayout mainLayout = new HorizontalLayout();

		mainLayout.addComponent(createNewTemplate);

		final GridLayout toolbarGridLayout = new GridLayout(1, 1);
		final List<com.vaadin.event.MouseEvents.ClickListener> listeners = new ArrayList<com.vaadin.event.MouseEvents.ClickListener>();

		try {
			listeners.add(new AddTemplateButtonListener(this.helper, form,
					templateDto));
		} catch (final AuthorizationException ex) {
			Notification.show("You are not permitted to add documents",
					Notification.Type.TRAY_NOTIFICATION);
		}

		mainLayout.addComponent(toolbarGridLayout);
		mainLayout.setWidth(100, Unit.PERCENTAGE);
		mainLayout.setHeight(100, Unit.PERCENTAGE);
		mainLayout.setExpandRatio(createNewTemplate, 15);
		mainLayout.setExpandRatio(toolbarGridLayout, 1);

		final ScreenToolbarBuilder builder = new ScreenToolbarBuilder(
				toolbarGridLayout, "template", listeners);
		builder.build();

		templateEditorLayout.setSpacing(true);
		templateEditorLayout.setMargin(true);

		templateEditorLayout.setWidth(100, Unit.PERCENTAGE);
		templateEditorLayout.setHeight(100, Unit.PERCENTAGE);
		templateEditorLayout.setSpacing(false);
		templateEditorLayout.setMargin(false);

		final HorizontalLayout editorFieldsLayout = new HorizontalLayout();

		final AceEditor editor = form.getEditor();
		editor.setWidth(99, Unit.PERCENTAGE);
		editor.setHeight(100, Unit.PERCENTAGE);
		editor.setShowGutter(true);
		editor.setShowPrintMargin(true);

		if (null != templateDto) {
			form.getTemplateNameTxtFld()
					.setValue(templateDto.getTemplateName());
			editor.setValue(templateDto.getTemplateText());
		}

		if (selectedDirectory.isGlobal()) {
			form.getIsGlobal().setValue(true);
		}

		form.getIsGlobal().setReadOnly(true);

		form.getDirectoryId().setValue(String.valueOf(selectedDirectory.getId()));
		form.getTemplatePathTxtFld().setValue(
				"/"
						+ buildPath(selectedDirectory.getId(),
								selectedDirectory.getDirectoryName()));
		form.getTemplatePathTxtFld().setReadOnly(true);

		editorFieldsLayout.addComponent(form.getTemplateNameTxtFld());
		editorFieldsLayout.addComponent(form.getTemplatePathTxtFld());
		editorFieldsLayout.addComponent(form.getTemplateKeyTxtFld());
		editorFieldsLayout.addComponent(form.getTemplateCategoryCombo());
		editorFieldsLayout.addComponent(form.getIsGlobal());
		editorFieldsLayout.setComponentAlignment(form.getIsGlobal(),
				Alignment.BOTTOM_CENTER);

		editorFieldsLayout.setSpacing(true);
		editorFieldsLayout.setMargin(true);

		templateEditorLayout.addComponent(editorFieldsLayout);
		templateEditorLayout.addComponent(form.getEditor());
		templateEditorLayout.setExpandRatio(editorFieldsLayout, 1);
		templateEditorLayout.setExpandRatio(editor, 6);

		mainContent.addComponent(templateEditorLayout);
		mainContent.setExpandRatio(templateEditorLayout, 3);
		mainContent.setSpacing(false);
		mainContent.setMargin(false);

		createNewTemplate.setWidth(100, Unit.PERCENTAGE);
		createNewTemplate.setHeight(100, Unit.PERCENTAGE);
		createNewTemplate.addComponent(templateEditorLayout);

		Tab tab2;

		if (templateDto == null) {
			tab2 = templateTab.addTab(mainLayout, "Create template",
					new ExternalResource("images/template.png"));
		} else {
			tab2 = templateTab.addTab(mainLayout,
					templateDto.getTemplateName(), new ExternalResource(
							"images/template.png"));
		}

		tab2.setClosable(true);
		templateTab.setSelectedTab(mainLayout);
	}

	private void renderFolderTab(final Integer folderId) {
		final VerticalLayout createNewFolder = new VerticalLayout();

		final FormLayout formLayout = new FormLayout();
		final TextField name = new TextField();
		final OptionGroup isGlobalOptionsGroup = new OptionGroup();
		final Tab createDirTab = this.templateTab.addTab(createNewFolder,
				"Create directory", null);
		; // to make the create dir tab autoclose

		createNewFolder.setWidth(100, Unit.PERCENTAGE);
		createNewFolder.setHeight(100, Unit.PERCENTAGE);
		createNewFolder.addComponent(formLayout);

		createDirTab.setClosable(true);

		isGlobalOptionsGroup.addItem("Global");
		isGlobalOptionsGroup.addItem("Local");
		isGlobalOptionsGroup
				.addValueChangeListener(new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						selectedTemplateDirScope = (event.getProperty()
								.toString().equals("Global") ? true : false);
					}
				});

		name.setCaption("Name");
		formLayout.addComponent(name);
		formLayout.addComponent(isGlobalOptionsGroup);

		if (null != folderId) {// when no items in tree this will not work
			TemplateDirectoryDto templateDirectory = templateDirectoryService
					.findById(folderId);
			TextField parentPath = new TextField();
			parentPath.setCaption("Parent");
			parentPath.setEnabled(false);
			parentPath.setValue(String.format("/%s",
					buildPath(folderId, templateDirectory.getDirectoryName())));
			formLayout.addComponent(parentPath);

			// to disable global/local if parent exist
			if (templateDirectory.isGlobal())
				isGlobalOptionsGroup.select("Global");
			else
				isGlobalOptionsGroup.select("Local");

			isGlobalOptionsGroup.setEnabled(false);
		}

		final Button addButton = new Button();
		addButton.setCaption("Add Folder");
		addButton.addClickListener(new com.vaadin.ui.Button.ClickListener() {
			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				final AccountService accountService = (AccountService) helper
						.getBean("accountService");
				
				AccountDto account = null;
				if (selectedTemplateDirScope){
					account = accountService.findAccountById(1);
				}
				else{
					account = accountService.findAccountById(accountId);
				}
				
				TemplateDirectoryDto directoryDto = new TemplateDirectoryDto();
				String dirToAdd = name.getValue().toString();
				directoryDto.setDirectoryName(dirToAdd);

				directoryDto.setGlobal(selectedTemplateDirScope);
				directoryDto.setAccount(account);

				boolean isSiblingWithSameName = false;
				if (null != selectedDirectoryId) {
					TemplateDirectoryDto parentDirectory = templateDirectoryService
							.findById(selectedDirectoryId);
					directoryDto.setParent(parentDirectory);
					final Collection<TemplateDirectoryDto> childDirectories = templateDirectoryService
							.findChildDirectories(parentDirectory.getId(),account.getAccountId());
					isSiblingWithSameName = isChildWithSameNameExist(
							childDirectories, dirToAdd);
				}

				Integer newDirectory = null;
				if (StringUtils.isEmpty(dirToAdd)) {
					Notification.show("Directory name cannot be empty",
							Notification.Type.TRAY_NOTIFICATION);
				} else if (isSiblingWithSameName
						&& StringUtils.isNotEmpty(dirToAdd)) {
					Notification
							.show("Directory name already exist,please choose something different",
									Notification.Type.TRAY_NOTIFICATION);
				} else {
					newDirectory = templateDirectoryService.create(directoryDto);
					if (folderId == null) { // works when no items in tree
						directoryDto = templateDirectoryService
								.findById(newDirectory);

						HierarchicalContainer templateContainer;

						if (directoryDto.isGlobal())
							templateContainer = globalTemplateDirContainer;
						else
							templateContainer = localTemplateDirContainer;

						Item item = templateContainer.addItem(directoryDto
								.getId());
						item.getItemProperty("id").setValue(
								directoryDto.getId());
						item.getItemProperty("name").setValue(
								directoryDto.getDirectoryName());
						templateContainer.setChildrenAllowed(
								directoryDto.getId(), true);
					}

					if (newDirectory != null) {
						if (createDirTab != null) // sanity check
							templateTab.removeTab(createDirTab);

						Notification.show(name.getValue()
								+ " folder added successfully",
								Notification.Type.TRAY_NOTIFICATION);
					}
				}
			}
		});
		formLayout.addComponent(addButton);

		createNewFolder.setWidth(100, Unit.PERCENTAGE);
		createNewFolder.setHeight(100, Unit.PERCENTAGE);
		createNewFolder.addComponent(formLayout);

		Tab tab2 = this.templateTab.addTab(createNewFolder, "Create directory",
				new ExternalResource("images/add-directory.png"));
		tab2.setClosable(true);

		this.templateTab.setSelectedTab(createNewFolder);
	}

	private boolean isChildWithSameNameExist(
			final Collection<TemplateDirectoryDto> childDirectories,
			final String directoryNameToAdd) {
		for (TemplateDirectoryDto dto : childDirectories) {
			if (dto.getDirectoryName().equals(directoryNameToAdd)) {
				return true;
			}
		}
		return false;
	}

	private String buildPath(Integer id, String path) {
		if (null != root.getParent(id)) {
			Integer itemId = (Integer) root.getParent(id);
			return buildPath(itemId, root.getItemCaption(itemId) + "/" + path);
		} else
			return path;
	}

	@Override
	public Component render(String command,
			HierarchicalContainer treeItemContainer) {
		return null;
	}
}