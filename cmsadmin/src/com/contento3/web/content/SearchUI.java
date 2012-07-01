package com.contento3.web.content;

import java.util.ArrayList;
import java.util.Collection;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

public class SearchUI {

	public Component render(){
		//Vertical Layout is the parent layout that will have
		//a form layout, that contains the search form
		final VerticalLayout searchUIParentLayout = new VerticalLayout();
		searchUIParentLayout.setSpacing(true);
		searchUIParentLayout.setMargin(true, false, true, true);
		//Form layout that displays the search form component.
		final FormLayout searchFormLayout = new FormLayout();
		searchFormLayout.setCaption("Search Content");
		searchFormLayout.setSpacing(true);
		
		final TextField searchedTermTxtField = new TextField();
		searchedTermTxtField.setCaption("Search");
		
		final Collection<String> contentTypeValue = new ArrayList<String>();
		contentTypeValue.add("Article");
		contentTypeValue.add("Image");
		contentTypeValue.add("Video");
		contentTypeValue.add("Document");
		
		final ComboBox contentTypeComboBox = new ComboBox("Content Type",contentTypeValue);
		contentTypeComboBox.setImmediate(true);

		final DateField creationDate = new PopupDateField("Creation date");

        // Set the value of the PopupDateField to current date
        creationDate.setValue(new java.util.Date());

        // Set the correct resolution
        creationDate.setResolution(PopupDateField.RESOLUTION_DAY);
        creationDate.setImmediate(true);
		
        final Button advancedSearchButton = new Button("Advanced Search");
        advancedSearchButton.setStyleName(BaseTheme.BUTTON_LINK);
        
        final Button searchButton = new Button("Search");
        
		searchFormLayout.addComponent(searchedTermTxtField);
		searchFormLayout.addComponent(contentTypeComboBox);
		searchFormLayout.addComponent(creationDate);
		searchFormLayout.addComponent(advancedSearchButton);
		searchFormLayout.addComponent(searchButton);

		searchUIParentLayout.addComponent(searchFormLayout);
		
		return  searchUIParentLayout;
 	}
}
