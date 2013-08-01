package com.contento3.web.user.listner;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.common.dto.Dto;
import com.contento3.security.permission.service.PermissionService;
import com.contento3.security.role.service.RoleService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.EntityListener;
import com.contento3.web.common.helper.GenricEntityPicker;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class AddPermissionListener extends EntityListener implements ClickListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Vertical layout for pop-up
	 */
	private VerticalLayout vLayout;
	
	/**
	 *  Reference to main window
	 */
	Window mainwindow; 
	
	/**
	 * Used to get service beans from spring context.
	 */
	SpringContextHelper helper;
	
	/**
	 * hold group id
	 */
	 Integer permissionId=null;
	 
	/**
	 *Abstract TableBuilder  used to create dynamic table 
	 */
	private  AbstractTableBuilder permissionTable;
		
	/**
	 * Constructor
	 * @param main
	 * @param helper
	 * @param table
	 * @param groupId
	 * @param tableBuilder
	 */
	public AddPermissionListener(final Window main,final SpringContextHelper helper,final Integer permissionId,final AbstractTableBuilder permissionTable) {
		this.mainwindow = main;
		this.helper = helper;
		this.permissionId = permissionId;
		this.permissionTable = permissionTable;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		final PermissionService permissionService = (PermissionService) helper.getBean("permissionService");
		Integer permissionId = (Integer) SessionHelper.loadAttribute(mainwindow,"permissionId");
		
		Collection<String> listOfColumns = new ArrayList<String>();
		listOfColumns.add("name");
		GenricEntityPicker permissionPicker;
		this.vLayout = new VerticalLayout();
		Collection<Dto> dtos = null;
		
		dtos = (Collection) permissionService.findById(permissionId);
		if (dtos!=null) {
			setCaption("Add Permission");//extend class method
			permissionPicker = new GenricEntityPicker(dtos,null, listOfColumns,this.vLayout,mainwindow,this,false);
			permissionPicker.build();
			
		}
	}
		
	}


