package com.contento3.web.user.listner;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.common.dto.Dto;
import com.contento3.security.role.service.RoleService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.EntityListener;
import com.contento3.web.common.helper.GenricEntityPicker;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class AddRoleListener extends EntityListener implements ClickListener{


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
	 Integer roleId=null;
	 
	/**
	 *Abstract TableBuilder  used to create dynamic table 
	 */
	private  AbstractTableBuilder roleTable;
		
	/**
	 * Constructor
	 * @param main
	 * @param helper
	 * @param table
	 * @param groupId
	 * @param tableBuilder
	 */
	public AddRoleListener(final Window main,final SpringContextHelper helper,final Integer roleId,final AbstractTableBuilder roleTable) {
		this.mainwindow = main;
		this.helper = helper;
		this.roleId = roleId;
		this.roleTable = roleTable;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		final RoleService roleService = (RoleService) helper.getBean("roleService");
		Integer accountId = (Integer) SessionHelper.loadAttribute("accountId");
		
		final Collection<String> listOfColumns = new ArrayList<String>();
		listOfColumns.add("name");
		GenricEntityPicker userPicker;
		this.vLayout = new VerticalLayout();
		Collection<Dto> dtos = null;
		
		dtos = (Collection) roleService.findRolesByAccountId(accountId);
		if (dtos!=null) {
			setCaption("Add role");//extend class method
			userPicker = new GenricEntityPicker(dtos,null, listOfColumns,this.vLayout,this,false);
			userPicker.build(null);
			
		}
	}

}
