package com.contento3.web;

import com.contento3.web.common.helper.GenricEntityPicker;
import com.contento3.web.content.ContentPickerHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.pagemodules.ContentTypeEnum;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ContentToSendSelectionListener implements ValueChangeListener {

	private static final long serialVersionUID = 1L;

	private HorizontalLayout whatSelectionLayout;
	
	final VerticalLayout layoutForInputFields;
	
	final SpringContextHelper helper;
	
	Button pickerButton;
	
	TextField statusMessage;
	
	TextField linkUrl;
	
	TextField caption;

	TextField actionUrl;

	TextField imageUrl;

	TextArea description;
	
	TextField name;
	
	
	public ContentToSendSelectionListener(final SpringContextHelper helper, final HorizontalLayout whatSelectionLayout) {
		this.whatSelectionLayout = whatSelectionLayout;
		layoutForInputFields = new VerticalLayout();
		this.helper = helper;
	}

	@Override
	public void valueChange(final ValueChangeEvent event) {

		layoutForInputFields.removeAllComponents();
		
		statusMessage = new TextField();
		statusMessage.setColumns(50);
		final String toSelect = event.getProperty().getValue().toString();
		if (toSelect.equalsIgnoreCase("STATUS")){
			layoutForInputFields.addComponent(statusMessage);
			statusMessage.setCaption("Status");
		}
		else if (toSelect.equalsIgnoreCase("IMAGE") || toSelect.equalsIgnoreCase("ARTICLE")){
			pickerButton = new Button("Pick "+toSelect);
			pickerButton.setStyleName("link");
			
			buildBasicLinkField();
			
			final TextField  actionUrl = new TextField();
			layoutForInputFields.addComponent(actionUrl);
			layoutForInputFields.setComponentAlignment(actionUrl, Alignment.BOTTOM_LEFT);
			
			layoutForInputFields.addComponent(pickerButton);
			layoutForInputFields.setComponentAlignment(pickerButton, Alignment.BOTTOM_LEFT);

			pickerButton.addClickListener(new ClickListener() {
				private static final long serialVersionUID = 1L;
				@Override
				public void buttonClick(final ClickEvent event) {
					final VerticalLayout contentPickerLayout = new VerticalLayout ();
					final ContentPickerHelper contentPickerHelper = new ContentPickerHelper(helper);
					final GenricEntityPicker contentPicker = contentPickerHelper.prepareContentPickerData(toSelect,contentPickerLayout,null,false,null,null);			
					contentPicker.build(ContentTypeEnum.IMAGE);
				}
			});
		}
		else if (toSelect.equalsIgnoreCase("ARTICLE")){
			
		}
		else if (toSelect.equalsIgnoreCase("EXTERNAL LINK")){
			buildBasicLinkField();
		}

		whatSelectionLayout.addComponent(layoutForInputFields);
		whatSelectionLayout.setSpacing(true);
		whatSelectionLayout.setMargin(true);
		whatSelectionLayout.setComponentAlignment(layoutForInputFields,Alignment.BOTTOM_LEFT);
	}

	private void buildBasicLinkField(){
		linkUrl = new TextField();
		caption = new TextField();
		name = new TextField();
		description = new TextArea();
		layoutForInputFields.addComponent(statusMessage);
		statusMessage.setCaption("Status");
		
		final HorizontalLayout fieldLayout = new HorizontalLayout();
		layoutForInputFields.addComponent(fieldLayout);
		fieldLayout.addComponent(linkUrl);
		linkUrl.setCaption("Link URL");
		
		layoutForInputFields.addComponent(caption);
		fieldLayout.addComponent(caption);
		caption.setCaption("Caption");

		layoutForInputFields.addComponent(name);
		fieldLayout.addComponent(name);
		name.setCaption("Name");

		layoutForInputFields.addComponent(description);
		description.setColumns(40);
		description.setRows(3);
		description.setCaption("Description");

		layoutForInputFields.setSpacing(true);
		layoutForInputFields.setMargin(true);
	}

	public String getStatusMessage(){
		return statusMessage.getValue().toString();
	}
	
	public String getUrlLink(){
		return linkUrl.getValue().toString();
	} 

	public String getName(){
		return name.getValue().toString();
	} 

	public String getDescription(){
		return description.getValue().toString();
	} 

	public String getCaption(){
		return caption.getValue().toString();
	} 

	public String getImageUrl(){
		return "";
		//return imageUrl.getValue().toString();
	}
	
	public String getActionUrl(){
		return "";
		//return actionUrl.getValue().toString();
	}
}
