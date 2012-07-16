package com.contento3.web.user.security;

import java.util.Collection;
import com.contento3.security.group.dto.GroupDto;
import com.contento3.security.group.service.GroupService;
import com.contento3.web.UIManager;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.BaseTheme;

public class GroupUIManager implements UIManager {

	/**
	 * Used to get service beans from spring context.
	 */
    private SpringContextHelper contextHelper;
    
    /**
     * Represents the parent window of the template ui
     */
	private Window parentWindow;
	/**
	 * layout for group manager screen
	 */
	private VerticalLayout verticalLayout = new VerticalLayout();
	/**
	 * TabSheet serves as the parent container for the group manager
	 */
	private TabSheet tabSheet;
	/**
	 * Group service used for group related operations
	 */
	private GroupService groupService;
	/**
	 * Group container to hold group items
	 */
	private IndexedContainer groupContainer;
	
	/**
	 * Constructor
	 * @param helper
	 * @param parentWindow
	 */
	public GroupUIManager(final SpringContextHelper helper,final Window parentWindow) {
		this.contextHelper = helper;
		this.parentWindow = parentWindow;
		this.groupService = (GroupService) this.contextHelper.getBean("groupService");
		
	}
	
	@Override
	public void render() {
	

	}
	/**
	 * Return tab sheet  
	 */
	@Override
	public Component render(final String command) {
	
		this.tabSheet = new TabSheet();
		this.tabSheet.setHeight(100, Sizeable.UNITS_PERCENTAGE);
		Tab groupTab = tabSheet.addTab(verticalLayout, "Group Management");
		groupTab.setClosable(true);
		this.verticalLayout.setSpacing(true);
		this.verticalLayout.setWidth(100,Sizeable.UNITS_PERCENTAGE);
		
		renderGroupContent();
		
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
	/**
	 * Render group content U.I 
	 * this includes add group button
	 * and group table
	 */
	private void renderGroupContent() {
		addGroupButton();
		renderGroupTable();
	}
	/**
	 * diplay "Add Group" button on the top of tab sheet
	 */
	private void addGroupButton(){
		Button addButton = new Button("Add Group");
		addButton.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				renderAddScreen();		
			}

		});
		this.verticalLayout.addComponent(addButton);
	}
	/**
	 * render Add Group screen 
	 */
	private void renderAddScreen() {
		groupContentFields("Add",0);
		
	}
	/**
	 * Render Edit Group screen
	 * @param editgroupId
	 */
	private void renderEditScreen(final Integer editgroupId){
		groupContentFields("Edit",editgroupId);
	}
	/**
	 * Return group related U.i fields and button
	 * @param command
	 * @param editgroupId
	 */
	private void groupContentFields(final String command,final Integer editgroupId){
		VerticalLayout newGroupLayout = new VerticalLayout();
		Tab addgroupTab = this.tabSheet.addTab(newGroupLayout, command+" Group");
		addgroupTab.setClosable(true);
		newGroupLayout.setSpacing(true);
		this.tabSheet.setSelectedTab(newGroupLayout);
		
		final TextField groupNamefield = new TextField("Group Name");
		final TextArea descriptionArea = new TextArea("Description");
		descriptionArea.setWidth(50,Sizeable.UNITS_PERCENTAGE);
		final GroupDto group;
		 if (command.equals("Edit")) {
			final GroupDto editGroup = groupService.findById(editgroupId);
			groupNamefield.setValue(editGroup.getGroupName());
			descriptionArea.setValue(editGroup.getDescription());
		 }
		
		final Button saveButton = new Button("Save");
		saveButton.addListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				GroupDto group = new GroupDto();
				group.setGroupName(groupNamefield.getValue().toString());
				group.setDescription(descriptionArea.getValue().toString());
				if (command.equals("Add")) {
					groupService.create(group);
					group = (GroupDto) groupService.findByGroupName(group
							.getGroupName());
					if (group != null) {
						addGroupItem(group);
					}
					parentWindow.showNotification(group.getGroupName()
							+ " added succesfully");

				}// end if
				else if (command.equals("Edit")) {
					group.setGroupId(editgroupId);
					groupService.update(group);
					parentWindow.showNotification(group.getGroupName()
							+ " updated succesfully");
					Item item = (Item) groupContainer.getItem(group.getGroupId());
					if(item != null)
						item.getItemProperty("groups").setValue(group.getGroupName());
				}// end else
			}// end buttonClick()

		});
		
		newGroupLayout.addComponent(groupNamefield);
		newGroupLayout.addComponent(descriptionArea);
		newGroupLayout.addComponent(saveButton); 
	}
	/**
	 * Render group table to screen
	 */
	private void renderGroupTable() {
		Table groupTable = new Table("Groups");
		groupTable.setWidth(50, Sizeable.UNITS_PERCENTAGE);
		groupTable.setPageLength(10);
		groupTable.setContainerDataSource(loadGroups());
		this.verticalLayout.addComponent(groupTable);
	}
	
	/**
	 * Return groups 
	 * @return
	 */
	private IndexedContainer loadGroups(){
		this.groupContainer = new IndexedContainer();
		this.groupContainer.addContainerProperty("groups", String.class, null);
		this.groupContainer.addContainerProperty("edit", Button.class, null);
		this.groupContainer.addContainerProperty("delete", Button.class, null);
		
		final Collection<GroupDto> groupDto = this.groupService.findAllGroups();
		if(!groupDto.isEmpty()){
			for(GroupDto group:groupDto){
				addGroupItem(group);
			}//end for
		}//end if
		return this.groupContainer;
	}
	/**
	 * Add group item into groupContainer
	 * @param group
	 */
	private void addGroupItem(final GroupDto group){
		Item item = this.groupContainer.addItem(group.getGroupId());
		item.getItemProperty("groups").setValue(group.getGroupName());
		//adding edit button item into list
		final Button editLink = new Button();
		editLink.setCaption("Edit");
		editLink.setData(group.getGroupId());
		editLink.addStyleName("edit");
		editLink.setStyleName(BaseTheme.BUTTON_LINK);
		item.getItemProperty("edit").setValue(editLink);
		editLink.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Integer id = (Integer) editLink.getData();
				renderEditScreen(id);
				
			}
		});
		//adding delete button item  into list
		final Button deleteLink = new Button();
		deleteLink.setCaption("Delete");
		deleteLink.setData((group.getGroupId()));
		deleteLink.addStyleName("delete");
		deleteLink.setStyleName(BaseTheme.BUTTON_LINK);
		item.getItemProperty("delete").setValue(deleteLink);
		
		deleteLink.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				final Integer id = (Integer) deleteLink.getData();
				deleteGroup(id);
			}
		});
	}
	/**
	 * Delete group item from db as well as from groupContainer
	 * @param deleteId
	 */
	private void deleteGroup(final Integer deleteId) {
		final GroupDto group = this.groupService.findById(deleteId);
		this.groupService.delete(group);
		this.groupContainer.removeItem(deleteId);
		this.parentWindow.showNotification(group.getGroupName()
				+ " deleted succesfully");
	}
	
}
