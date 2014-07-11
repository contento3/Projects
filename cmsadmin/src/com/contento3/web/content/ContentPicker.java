package com.contento3.web.content;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.common.dto.Dto;
import com.contento3.web.common.helper.EntityListener;
import com.contento3.web.common.helper.GenricEntityPicker;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ContentPicker extends GenricEntityPicker implements ClickListener {
	
	private static final long serialVersionUID = 1L;
	
	private VerticalLayout verticalLayout;
	private TextField searchField;
	private Button btnSearch;
	private Collection<Dto> searchedDtos;
	private Collection<Dto> searchedAssignedDtos;

	public ContentPicker(Collection<Dto> dtos, Collection<Dto> assignedDtos,
			Collection<String> listOfColumns, VerticalLayout vLayout,
			EntityListener entityListener, boolean isHierarchicalTable) {
		
		super(dtos, assignedDtos, listOfColumns, vLayout, entityListener,
				isHierarchicalTable);
		this.verticalLayout = vLayout;
		
	}

	/**
	 * Build table
	 */
	@Override
	public void build() {

		super.build();
		verticalLayout.setMargin(true);
		verticalLayout.setSpacing(true);
		searchField = new TextField();
		searchField.setInputPrompt("Search by name");
		btnSearch = new Button("Search");
		btnSearch.addClickListener(this);
		
		HorizontalLayout searchLayout = new HorizontalLayout(searchField, btnSearch);
		searchLayout.setSpacing(true);
		verticalLayout.addComponent(searchLayout, 0);
	}

	/**
	 * Button click handler 
	 */
	@Override
	public void buttonClick(ClickEvent event) {
	
		String searchTxt = searchField.getValue().toLowerCase();
		searchedDtos = new ArrayList<Dto>();
		searchedAssignedDtos = new ArrayList<Dto>(); 
		
		if(!searchTxt.equals("")) {
			
			for (Dto dto : super.dtos) {
				addIntoCollection(searchTxt, dto, searchedDtos);
			}
			
			for (Dto dto : super.assignedDto) {
				addIntoCollection(searchTxt, dto, searchedAssignedDtos);
			}
			
			rebuildTable(searchedAssignedDtos, searchedDtos);
		} else {
			rebuildTable(assignedDto, dtos);
		}	
	}
	
	/**
	 * Add matched search items to collection.
	 * @param searchTxt
	 * @param dto
	 * @param dtos
	 */
	private void addIntoCollection(String searchTxt, Dto dto, Collection<Dto> dtos) {
		
		if(dto.getName().toLowerCase().contains(searchTxt)) {
			dtos.add(dto);
		}
	}
}
