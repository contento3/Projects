package com.contento3.web.user.listner;

import com.contento3.security.group.dto.GroupDto;
import com.contento3.security.group.service.GroupService;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.site.SiteDomainPopup;
import com.vaadin.data.Item;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;

public class GroupDeleteClickListener implements ClickListener {

	private static final long serialVersionUID = 3126526402867446357L;

	/**
	 * Used to get service beans from spring context.
	 */
	private final SpringContextHelper contextHelper;
	/**
	 * Table for Group
	 */
	private final Table table;
	/**
	 * Group Dto 
	 */
	private final GroupDto groupDto;
	/**
	 * Delete link button
	 */
	private final Button deleteLink;
	
	/**
	 * Constructor
	 * @param groupDto
	 * @param helper
	 * @param deleteLink
	 * @param table
	 */
	public GroupDeleteClickListener(final GroupDto groupDto,final SpringContextHelper helper,final Button deleteLink,final Table table){
		this.groupDto = groupDto;
		this.contextHelper = helper;
		this.table = table;
		this.deleteLink = deleteLink;
	}

	/**
	 * Handle delete button click event  
	 */
	@Override
	public void buttonClick(ClickEvent event) {
		final GroupService groupService =  (GroupService) contextHelper.getBean("groupService");
		final Object id = deleteLink.getData();
		final String name = (String) table.getContainerProperty(id,"groups").getValue();
			if(groupDto.getGroupName().equals(name)){
				groupService.delete(groupDto);
				table.removeItem(id);
				table.setPageLength(table.getPageLength()-1);
			}
	}	
}