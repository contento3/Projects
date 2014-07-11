package com.contento3.web.common;

import java.util.ArrayList;
import java.util.Collection;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ListingUIHeaderBuilder {

	private String screenHeader;
	
	private Collection<SearchBarFieldInfo> fields = new ArrayList<SearchBarFieldInfo>();
	
	public ListingUIHeaderBuilder (final String screenHeader, final Collection<SearchBarFieldInfo> fields){
		this.screenHeader = screenHeader;
		this.fields = fields;
	}
	
	public VerticalLayout build(){
		final VerticalLayout searchLayout = new VerticalLayout();

		final Label articleHeading = new Label(screenHeader);
		articleHeading.setStyleName("screenHeading");
		articleHeading.setSizeUndefined();
		searchLayout.addComponent(articleHeading);
		searchLayout.setMargin(true);
		searchLayout.setSizeUndefined();
		searchLayout.setWidth(100, Unit.PERCENTAGE);

		final VerticalLayout emptySpaceLayout = new VerticalLayout();
		searchLayout.addComponent(emptySpaceLayout);

		Button searchBtn = new Button("Search");

		GridLayout searchBar = new GridLayout(3,1);
		searchBar.setMargin(true);
		searchBar.setSpacing(true);
		searchBar.addStyleName("horizontalForm");
		searchBar.setSizeFull();

		//Add the searchbar fields
		for (SearchBarFieldInfo info : fields){
			searchBar.addComponent(buildTextField(info.getTextFieldLabel(),info.getTextFieldPrompt()));
		}
		
		searchBar.addComponent(searchBtn);
		searchBar.setComponentAlignment(searchBtn, Alignment.MIDDLE_CENTER);
		searchBar.setWidth(800,Unit.PIXELS);

		final Panel searchPanel = new Panel();
		searchPanel.setSizeUndefined(); 
		searchPanel.setContent(searchBar);
		searchLayout.addComponent(searchPanel);
		searchPanel.setWidth(100,Unit.PERCENTAGE);
		
		searchLayout.setSpacing(true);
		return searchLayout;
	}
	
	private FormLayout buildTextField(final String fieldText,final String fieldInputPrompt){
		final FormLayout fieldLayout = new FormLayout();
		final TextField searchHeader = new TextField(fieldText);
		searchHeader.setInputPrompt(fieldInputPrompt);
		searchHeader.addStyleName("horizontalForm");
		fieldLayout.addComponent(searchHeader);
		return fieldLayout;
	}
}
