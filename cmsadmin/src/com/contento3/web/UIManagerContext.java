package com.contento3.web;

import com.contento3.common.dto.Dto;
import com.contento3.web.common.Form;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;

public class UIManagerContext {

	private Layout container;
	
	private SpringContextHelper helper;

	private Table listingTable =  new Table();

	private Integer idToEdit;
	
	private Form form;
	
	public Layout getContainer() {
		return container;
	}

	public void setContainer(final Layout container) {
		this.container = container;
	}

	public SpringContextHelper getHelper() {
		return helper;
	}

	public void setHelper(SpringContextHelper helper) {
		this.helper = helper;
	}

	public Table getListingTable() {
		return listingTable;
	}

	public void setListingTable(Table listingTable) {
		this.listingTable = listingTable;
	}

	public Integer getIdToEdit() {
		return idToEdit;
	}

	public void setIdToEdit(final Integer idToEdit) {
		this.idToEdit = idToEdit;
	}

	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

}
