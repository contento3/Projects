package com.contento3.web.user.security;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountService;
import com.contento3.security.group.dto.GroupDto;
import com.contento3.security.group.service.GroupService;
import com.contento3.security.role.model.Role;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

public class GroupPopup extends CustomComponent implements Window.CloseListener,Button.ClickListener {
	
	private static final long serialVersionUID = 1L;

	/**
	 *  Reference to main window
	 */
	Window mainwindow; 
	
	/**
	 * The window to be opened
	 */
	Window popupWindow; 
	
	/**
	 * Button for opening the window
	 */
	Button openbutton; 
	
	/**
	 *  A button in the window
	 */
	Button closebutton; 
	
	/**
	 * Used to get service beans from spring context.
	 */
	SpringContextHelper helper;
	
	/**
	 * Group service used for group related operations
	 */
	GroupService groupService;
	
	/**
	 * AccountService used for account manipulation
	 */
	AccountService accountService;
	
	/**
	 * Table for Group
	 */
    final Table groupTable;
	boolean isModalWindowClosable = true;
	
	/**
	 * Constructor
	 * @param main
	 * @param helper
	 * @param table
	 */
	public GroupPopup(final SpringContextHelper helper,final Table table) {
		this.helper = helper;
		this.groupTable = table;
		this.groupService = (GroupService) this.helper.getBean("groupService");
		this.accountService = (AccountService) this.helper.getBean("accountService");

		// The component contains a button that opens the window.
        final VerticalLayout layout = new VerticalLayout();
        openbutton = new Button("Add Group", this);
        openbutton.addClickListener(this);
        layout.addComponent(openbutton);

        setCompositionRoot(layout);
	}
	
	  /** 
	   * Handle the clicks for the two buttons.
	   */
    public void openButtonClick(Button.ClickEvent event) {
        /* Create a new window. */
        final Button groupButton = new Button();
		popupWindow = new Window();
    	
		popupWindow.setPositionX(200);
    	popupWindow.setPositionY(100);

    	popupWindow.setHeight(41,Unit.PERCENTAGE);
    	popupWindow.setWidth(20,Unit.PERCENTAGE);
       
    	/* Add the window inside the main window. */
        UI.getCurrent().addWindow(popupWindow);
        
        /* Listen for close events for the window. */
        popupWindow.addCloseListener(this);
        popupWindow.setModal(true);
        
        final VerticalLayout popupMainLayout = new VerticalLayout();
        final Label label = new Label("Group Name");
        label.setWidth(100,Unit.PERCENTAGE);
        final HorizontalLayout inputDataLayout = new HorizontalLayout();
        final TextField textField = new TextField("Group Name");
        textField.setInputPrompt("Enter group name");
        textField.setWidth(100,Unit.PERCENTAGE);
        textField.setColumns(20);
        
        inputDataLayout.setSizeFull();
        inputDataLayout.setSpacing(true);
        inputDataLayout.addComponent(textField);
        inputDataLayout.setComponentAlignment(textField, Alignment.TOP_LEFT);
        
        popupMainLayout.setSpacing(true);
        popupMainLayout.addComponent(inputDataLayout);
       
        /* adding description area */
        final HorizontalLayout addDescriptionLayout = new HorizontalLayout();
       
        final TextArea descriptionArea = new TextArea("Description");
        descriptionArea.setInputPrompt("Enter group description");
     	descriptionArea.setWidth(100,Unit.PERCENTAGE);
     	descriptionArea.setColumns(20);
    	descriptionArea.setRows(5);
     	
     	addDescriptionLayout.setSizeFull();
     	addDescriptionLayout.setSpacing(true);
     	addDescriptionLayout.addComponent(descriptionArea);
    	addDescriptionLayout.setComponentAlignment(descriptionArea, Alignment.TOP_LEFT);
     	popupMainLayout.addComponent(addDescriptionLayout);
     
        final HorizontalLayout addButtonLayout = new HorizontalLayout();
        popupMainLayout.addComponent(addButtonLayout);

        addButtonLayout.addComponent(groupButton);
        addButtonLayout.setComponentAlignment(groupButton, Alignment.BOTTOM_RIGHT);
        addButtonLayout.setWidth(100, Unit.PERCENTAGE);
        
        popupWindow.setContent(popupMainLayout);
        popupWindow.setResizable(false);
        /* Allow opening only one window at a time. */
        openbutton.setEnabled(false);

		

    	if (event.getButton().getCaption().equals("Edit")){
			groupButton.setCaption("Save");
			popupWindow.setCaption("Edit group");
			final Integer Id = (Integer) event.getButton().getData();
			GroupDto groupDto = groupService.findById(Id);
			textField.setValue(groupDto.getGroupName());
			String description = groupDto.getDescription();
	    	
			if(description==null){
	    		descriptionArea.setValue("");
			}else{
				descriptionArea.setValue(description);
			}
	        groupButton.addClickListener(new ClickListener() {
				private static final long serialVersionUID = 1L;
				public void buttonClick(ClickEvent event) {
					handleEditGroup(textField,descriptionArea,Id);
				}	
			});
    	}
    	else
    	{
	        groupButton.setCaption("Add");
	        popupWindow.setCaption("Add new group");
	        groupButton.addClickListener(new ClickListener() {
				private static final long serialVersionUID = 1L;
				public void buttonClick(ClickEvent event) {
					handleNewGroup(textField,descriptionArea);
				}	
			});
    	}
    }

    /**
     * Handles adding new Group
     * @param textField
     */
	private void handleNewGroup(final TextField textField,final TextArea descriptionArea){
		final GroupDto groupDto = new GroupDto();
		groupDto.setGroupName(textField.getValue().toString());
		groupDto.setDescription(descriptionArea.getValue().toString());
		groupDto.setMembers(new ArrayList<SaltedHibernateUserDto>());
		groupDto.setRoles(new ArrayList<Role>());
		
		final AccountDto accountDto = accountService.findAccountById(((Integer)SessionHelper.loadAttribute("accountId")));
		groupDto.setAccountDto(accountDto);
		
		groupService.create(groupDto);
		Notification.show(groupDto.getGroupName()+" group created succesfully");
		resetTable();
    }

    
    /**
     * Handles editing groups
     * @param textField
     */
	private void handleEditGroup(final TextField textField,final TextArea descriptionArea,final Integer editId){
		final GroupDto groupDto = groupService.findById(editId);
			groupDto.setGroupName(textField.getValue().toString());
			groupDto.setDescription(descriptionArea.getValue().toString());
			groupService.update(groupDto);
			Notification.show(groupDto.getGroupName()+" group edit succesfully");
		resetTable();
    }
	/**
	 * Reset table
	 */
	 @SuppressWarnings("rawtypes")
		private void resetTable(){
			final AbstractTableBuilder tableBuilder = new GroupTableBuilder(helper,groupTable);
			final Collection<GroupDto> groupDto = this.groupService.findAllGroups();
			tableBuilder.rebuild((Collection)groupDto);
			UI.getCurrent().removeWindow(popupWindow);
	        openbutton.setEnabled(true);
	    }
	 
	  /**
	   *  Handle Close button click and close the window.
	   */
	    public void closeButtonClick(Button.ClickEvent event) {
	    	if (!isModalWindowClosable){
	        /* Windows are managed by the application object. */
	        UI.getCurrent().removeWindow(popupWindow);
	        
	        /* Return to initial state. */
	        openbutton.setEnabled(true);
	    	}
	    }

	    /**
	     * Handle window close event
	     */
		@Override
		public void windowClose(CloseEvent e) {
			  /* Return to initial state. */
	        openbutton.setEnabled(true);
		}

		@Override
		public void buttonClick(ClickEvent event) {
			this.openButtonClick(event);
		}

}
