package com.contento3.web.user.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;

import com.contento3.common.dto.Dto;
import com.contento3.security.role.dto.RoleDto;
import com.contento3.security.role.service.RoleService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.user.listner.RoleDeleteClickListener;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.BaseTheme;

public class RoleTableBuilder extends AbstractTableBuilder {
	
	final SpringContextHelper contextHelper;

	final RoleService roleService;



	public RoleTableBuilder(final SpringContextHelper helper,final Table table){
		super(table);
		this.contextHelper = helper;
		this.roleService = (RoleService) contextHelper.getBean("roleService");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void assignDataToTable(final Dto dto,final Table roletable,final Container rolecontainer) {
		
		try
		{
			RoleDto role = (RoleDto) dto;
			Item item = rolecontainer.addItem(role.getId());
			item.getItemProperty("role").setValue(role.getName());
	
			if (SecurityUtils.getSubject().isPermitted("ROLE:EDIT")){
				//adding edit button item into list
			    final Button editLink = new Button("Edit roles",new RolePopup(contextHelper, roletable));
				editLink.setCaption("Edit");
				editLink.setData(role.getId());
				editLink.addStyleName("edit");
				editLink.setStyleName(BaseTheme.BUTTON_LINK);
				item.getItemProperty("edit").setValue(editLink);
			}
			
			if (SecurityUtils.getSubject().isPermitted("ROLE:DELETE")){
				//adding delete button item  into list
				final Button deleteLink = new Button();
				deleteLink.setCaption("Delete");
				deleteLink.setData((role.getId()));
				deleteLink.addStyleName("delete");
				deleteLink.setStyleName(BaseTheme.BUTTON_LINK);
				item.getItemProperty("delete").setValue(deleteLink);
				deleteLink.addClickListener(new RoleDeleteClickListener(role, roleService, deleteLink, roletable));
			}
			
			if (SecurityUtils.getSubject().isPermitted("ROLE:ASSOCIATE_PERMISSION")){
				//add view button item into list
				final Button viewLink = new Button("permissions",new AssociatedPermissionPopup(contextHelper, new Table()));
				viewLink.setCaption("View");
				viewLink.setData(role.getId());
				viewLink.addStyleName("permissions");
				viewLink.setStyleName(BaseTheme.BUTTON_LINK);
				item.getItemProperty("permissions").setValue(viewLink);
			}
		}
		catch(final AuthorizationException ex){
			
		}
	}

	@Override
	public void buildHeader(Table roletable, Container rolecontainer) {
		rolecontainer.addContainerProperty("role", String.class, null);
		rolecontainer.addContainerProperty("edit", Button.class, null);
		rolecontainer.addContainerProperty("delete", Button.class, null);
		rolecontainer.addContainerProperty("permissions", Button.class, null);

		roletable.setWidth(100, Unit.PERCENTAGE);
		roletable.setContainerDataSource(rolecontainer);
		
	}

	/**
	 * Create empty table
	 */
	@Override
	public void buildEmptyTable(final Container roleContainer) {
		Item item = roleContainer.addItem("-1");
		item.getItemProperty("role").setValue("No record found.");
	}

}
