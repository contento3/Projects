package com.contento3.web.user.security;

import java.util.Collection;

import org.apache.shiro.authz.AuthorizationException;

import com.contento3.common.dto.Dto;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.security.entity.dto.EntityDto;
import com.contento3.security.entity.service.EntityService;
import com.contento3.security.entityoperation.dto.EntityOperationDto;
import com.contento3.security.entityoperation.service.EntityOperationService;
import com.contento3.security.permission.dto.PermissionDto;
import com.contento3.security.permission.service.PermissionService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.ComboDataLoader;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

public class PermissionPopup extends CustomComponent implements Window.CloseListener,Button.ClickListener{

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
	 * Permission service used for user related operations
	 */
	final PermissionService permissionService;
	
	final EntityService entityService;
	final EntityOperationService entityOperationService;
	/**
	 * Table for permission
	 */
    final Table permissionTable;
    boolean isModalWindowClosable = true;
    
    public PermissionPopup(final SpringContextHelper helper,final Table table)
    {
		this.helper = helper;
		this.permissionTable = table;
		this.permissionService = (PermissionService) this.helper.getBean("permissionService");
		this.entityService = (EntityService) this.helper.getBean("entityService");
		this.entityOperationService = (EntityOperationService) this.helper.getBean("entityOperationService");
		// The component contains a button that opens the window.
        final VerticalLayout layout = new VerticalLayout();
		openbutton = new Button("Add Permission");
		openbutton.addClickListener((ClickListener) this);
	    layout.addComponent(openbutton);
	    setCompositionRoot(layout);
    }
	
	public void openButtonClick(Button.ClickEvent event) {

		/* Create a new window. */
        final Button permissionButton = new Button();
		popupWindow = new Window();
    	
		popupWindow.setPositionX(200);
    	popupWindow.setPositionY(100);

    	popupWindow.setHeight(38,Unit.PERCENTAGE);
    	popupWindow.setWidth(30,Unit.PERCENTAGE);
       
    	/* Add the window inside the main window. */
        UI.getCurrent().addWindow(popupWindow);
        
        /* Listen for close events for the window. */
        popupWindow.addCloseListener(this);
        popupWindow.setModal(true);
        
        final VerticalLayout popupMainLayout = new VerticalLayout();
        final Label label = new Label("Permission Id");
        label.setWidth(100,Unit.PERCENTAGE);
        final HorizontalLayout inputDataLayout = new HorizontalLayout();
        final TextField permissionIdTxtFld = new TextField("Permission Id");
        permissionIdTxtFld.setInputPrompt("Enter Permission Id");
        permissionIdTxtFld.setWidth(100,Unit.PERCENTAGE);
        permissionIdTxtFld.setColumns(15);
        
        inputDataLayout.setSizeFull();
        inputDataLayout.setSpacing(true);
       // inputDataLayout.addComponent(permissionIdTxtFld);
       // inputDataLayout.setComponentAlignment(permissionIdTxtFld, Alignment.TOP_LEFT);
        
        popupMainLayout.setSpacing(true);
        popupMainLayout.setMargin(true);
        popupMainLayout.addComponent(inputDataLayout);
       
       

        /* adding first name text field */
        final HorizontalLayout firstNameLayout = new HorizontalLayout();

        ComboDataLoader comboLoader =  new ComboDataLoader();

        Collection<Dto> entityDtos= (Collection) entityService.findAllEntities();
        final ComboBox entityCombo= new ComboBox("Entity",comboLoader.loadDataInContainer(entityDtos));

        entityCombo.setItemCaptionMode(ComboBox.ItemCaptionMode.PROPERTY);
        entityCombo.setItemCaptionPropertyId("name");

//        for(EntityDto dto:entityDto)
//        {
//        	Entity.addItem(dto.getId());
//        }
        
        firstNameLayout.setSizeFull();
        firstNameLayout.setSpacing(true);
        firstNameLayout.addComponent(entityCombo);
        firstNameLayout.setComponentAlignment(entityCombo, Alignment.TOP_LEFT);
     	popupMainLayout.addComponent(firstNameLayout);
        
        firstNameLayout.addComponent(entityCombo);
        popupMainLayout.addComponent(firstNameLayout);
        final HorizontalLayout lastNameLayout = new HorizontalLayout();
          
        
          //final TextField EntityOperationTxtFld = new TextField("Entity Operation id");
          Collection<Dto> entityOperationDto= (Collection)  entityOperationService.findAllEntityOperations();
          final ComboBox entityOperationCombo= new ComboBox("Entity Operation:",comboLoader.loadDataInContainer(entityOperationDto));

          entityOperationCombo.setItemCaptionMode(ComboBox.ItemCaptionMode.PROPERTY);
          entityOperationCombo.setItemCaptionPropertyId("name");

//          for(EntityOperationDto dto:entityOperationDto)
//          {
//          	EntityOperation.addItem(dto.getId());
//          }
          //EntityOperationTxtFld.setInputPrompt("Enter Entity Operation id");
          //EntityOperationTxtFld.setWidth(100,Sizeable.UNITS_PERCENTAGE);
          //EntityOperationTxtFld.setColumns(15);
          
          lastNameLayout.setSizeFull();
          lastNameLayout.setSpacing(true);
          lastNameLayout.addComponent(entityOperationCombo);
          lastNameLayout.setComponentAlignment(entityOperationCombo, Alignment.TOP_LEFT);
       	popupMainLayout.addComponent(lastNameLayout);
        final HorizontalLayout addButtonLayout = new HorizontalLayout();
        popupMainLayout.addComponent(addButtonLayout);

        addButtonLayout.addComponent(permissionButton);
        addButtonLayout.setComponentAlignment(permissionButton, Alignment.BOTTOM_RIGHT);
        addButtonLayout.setWidth(100, Unit.PERCENTAGE);
        
        popupWindow.setContent(popupMainLayout);
        popupWindow.setResizable(false);
        /* Allow opening only one window at a time. */
        openbutton.setEnabled(false);
        if(event.getButton().getCaption().equals("Edit"))
        {
        	permissionButton.setCaption("Edit");
        	popupWindow.setCaption("Edit Permission");
			final int permissionId =  (Integer) event.getButton().getData();
			PermissionDto permissionDto = permissionService.findById(permissionId);
			//roleNameTxtFld.setValue(roleDto.getName());
			permissionIdTxtFld.setValue(Integer.toString(permissionDto.getId()));
        	permissionButton.addClickListener(new ClickListener(){
    			private static final long serialVersionUID = 1L;
    			public void buttonClick(ClickEvent event) 
    			{
   					try
   					{
    				handleEditPermission(entityCombo,entityOperationCombo,Integer.parseInt(permissionIdTxtFld.getValue().toString()));
   					}catch(AuthorizationException ex){}
   				}
            	});
        }
        else
        {
        
        	permissionButton.setCaption("Add");
        	popupWindow.setCaption("Add Permission");
        	permissionButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) 
			{
				try
				{
					handleNewPermission(permissionIdTxtFld,entityCombo,entityOperationCombo);
				}catch(AuthorizationException ex){}
			}
        	});
        }
        
	}
	private void handleNewPermission(final TextField permissionIdTxtFld,ComboBox entityIdCmboBox,final ComboBox entityOperationIdCmboBox){
		PermissionDto permissionDto = new PermissionDto();
		try {
			String permstr=permissionIdTxtFld.getValue().toString();
			//int permId= Integer.parseInt(permstr);
			String entitystr= entityIdCmboBox.getValue().toString();
			int entId = Integer.parseInt(entitystr);
			String entityopstr= entityOperationIdCmboBox.getValue().toString();
			int entopId = Integer.parseInt(entityopstr);
			//permissionDto.setPermissionId(permId);
			
			final EntityDto entityDto = entityService.findById(entId);
			permissionDto.setEntity(entityDto);
			final EntityOperationDto entityOperationDto = entityOperationService.findById(entopId);
			permissionDto.setEntityOperation(entityOperationDto);
			permissionService.create(permissionDto);
		} catch (EntityAlreadyFoundException e) {
			Notification.show("Permission already exists", Notification.Type.ERROR_MESSAGE);
		}
		catch (EntityNotCreatedException e) {
			Notification.show("Permission not created", Notification.Type.ERROR_MESSAGE);
		}
		catch(AuthorizationException ex){
			Notification.show("You are not authorized to do this operation.");
		}
		Notification.show(permissionDto.getId() +" permission created succesfully");
		resetTable();
    }
	private void handleEditPermission(final ComboBox entityIdCmboBox,final ComboBox entityOperationIdCmboBox,final Integer editId){
		try{
		final PermissionDto permissionDto = permissionService.findById(editId);
		String entitystr= entityIdCmboBox.getValue().toString();
		int entId = Integer.parseInt(entitystr);
		String entityopstr= entityOperationIdCmboBox.getValue().toString();
		int entopId = Integer.parseInt(entityopstr);
		permissionDto.setPermissionId(editId);
		final EntityDto entityDto = entityService.findById(entId);
		permissionDto.setEntity(entityDto);
		final EntityOperationDto entityOperationDto = entityOperationService.findById(entopId);
		permissionDto.setEntity(entityDto);
		permissionDto.setEntityOperation(entityOperationDto);
		permissionService.update(permissionDto);
		Notification.show(permissionDto.getId()+" permission edit succesfully");
		}catch(AuthorizationException ex){}
		resetTable();
    }
	@Override
	public void windowClose(CloseEvent e) {
		// TODO Auto-generated method stub
		openbutton.setEnabled(true);
	}

	@SuppressWarnings("rawtypes")
	private void resetTable(){
		final AbstractTableBuilder tableBuilder = new PermissionTableBuilder(helper,permissionTable);
		//final Collection<RoleDto> roleDto = roleService.findAllRoles();
		final Collection<PermissionDto> permissionDto = permissionService.findAllPermissions();
		tableBuilder.rebuild((Collection)permissionDto);
		UI.getCurrent().removeWindow(popupWindow);
        openbutton.setEnabled(true);
    }
	 public void closeButtonClick(Button.ClickEvent event) {
	    	if (!isModalWindowClosable){
	        /* Windows are managed by the application object. */
	        UI.getCurrent().removeWindow(popupWindow);
	        
	        /* Return to initial state. */
	        openbutton.setEnabled(true);
	    	}
	    }

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		this.openButtonClick(event);
		
	}
}
