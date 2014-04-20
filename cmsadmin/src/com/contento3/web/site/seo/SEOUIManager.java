package com.contento3.web.site.seo;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;

import org.vaadin.dialogs.ConfirmDialog;

import com.contento3.cms.seo.dto.MetaTagDto;
import com.contento3.cms.seo.service.MetaTagService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.site.seo.listener.SEOListener;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class SEOUIManager implements ClickListener, SEOListener  {
	
	private static final long serialVersionUID = 1L;
	private final static String SCREEN_HEADING = "SEO Settings";
	private final static String LABEL_META_ATTRIBUTE= "Meta attributes";
	private final static String LABEL_CONTENT_ATTRIBUTE_VALUE= "Content attribute value";
	private final static String LABEL_META_VALUES= "Values";
	private static final String MSG_ATTRIBUTE_CREATED = "Attribute added successfuly.";
	private static final String MSG_ATTRIBUTE_UPDATED = "Attribute updated successfuly.";
	private static final String MSG_ATTRIBUTE_DELETED = "Attribute deleted successfuly.";
	private static final String BUTTON_NAME_ADD = "Add attribute";
	private static final String BUTTON_NAME_UPDATE = "Update";
	
	private final String[] ATTRIBUTE_COMBOX_VALUES= {"http-equiv", "charset", "name"};

	
	/**
	 * Helper use to load spring beans
	 */
	 private SpringContextHelper contextHelper;
	 
	 /**
	  * Service for access MetaTags
	  */
	 private MetaTagService metaTagService;
	
	/**
	 * Layout for SEO Manager
	 */
	private VerticalLayout seoParentLayout;
	
	/**
	 * Id of site
	 */
	private Integer siteId;
	
	/**
	 * Combobox for attributes
	 */
	private ComboBox attributeCombo;
	
	/**
	 * Textfield for attribute value
	 */
	private TextField valueField;
	
	/**
	 * Textfield for content value
	 */
	private TextField contentValuField;
	
	/**
	 * Table component
	 */
	private SEOTableBuilder tableBuilder;
	
	/**
	 * Add/Update Button 
	 */
	private Button btnAddAttribute;
	
	private MetaTagDto metaTagDto;
	
	private Integer pageId;
	
	public SEOUIManager(final SpringContextHelper helper) {
		
		this.contextHelper = helper;
		this.metaTagService = (MetaTagService) helper.getBean("metaTagService");
	}
	
	/**
	 * Show SEO setting screen
	 * @param tabSheet
	 * @param siteId
	 */
	public void renderSEOSettingsManager(final TabSheet tabSheet,final Integer siteId, final Integer pageId) {
		
		this.siteId = siteId;
		this.pageId = pageId;
		seoParentLayout = new VerticalLayout();
		seoParentLayout.setSpacing(true);
		seoParentLayout.setMargin(true);
		
		Tab seoSettingsTab = tabSheet.addTab(seoParentLayout, "SEO Settings");
		tabSheet.setSelectedTab(seoParentLayout);
		seoSettingsTab.setVisible(true);
		seoSettingsTab.setEnabled(true);
		seoSettingsTab.setClosable(true);
		addUIComponents();
	}

	/**
	 * Add UI components to screen
	 */
	private void addUIComponents() {

		seoParentLayout.addComponent(createScreenHeading());	
		seoParentLayout.addComponent(new HorizontalRuler());
		seoParentLayout.addComponent(createTableForSeo());
		seoParentLayout.addComponent(createAddingAttributeSection());
	}

	/**
	 * Create Heading 
	 * @return
	 */
	private Label createScreenHeading() {
		
		final Label heading = new Label(SCREEN_HEADING);
		heading.setStyleName("screenHeading");
		return heading;
	}
	
	/**
	 * Create table
	 * @return
	 */
	private Table createTableForSeo() {
		
		Collection<MetaTagDto> tags = metaTagService.findBySiteId(siteId);
	
		Table table = new Table();
		table.setImmediate(true);
		
		tableBuilder = new SEOTableBuilder(table, this);
		tableBuilder.build((Collection)tags);
		return table;
	}
	
	private void setComboxValues() {
		
		for (String val : ATTRIBUTE_COMBOX_VALUES) {
			attributeCombo.addItem(val);
		}
	}
	
	private HorizontalLayout createAddingAttributeSection() {
					
		GridLayout grid = new GridLayout(2, 6);
		grid.setMargin(true);
		grid.setWidth("400px");
		grid.setHeight("200px");
		
		Label label = new Label();
		label.setCaption(LABEL_META_ATTRIBUTE);
		grid.addComponent(label, 0, 1);
		
		attributeCombo = new ComboBox();
		setComboxValues();
		attributeCombo.setWidth(100, Unit.PERCENTAGE);
		attributeCombo.addValueChangeListener(new Property.ValueChangeListener() { // for charset content field disabled
	
			private static final long serialVersionUID = 1L;
			public void valueChange(ValueChangeEvent event) {
				contentValuField.setEnabled(true);
				if(event.getProperty() != null && event.getProperty().getValue() != null) {
					String value = event.getProperty().getValue().toString();
					if(value.equals(ATTRIBUTE_COMBOX_VALUES[1])) {
						contentValuField.setEnabled(false);		
					} 
				}
			}
		});
		attributeCombo.setImmediate(true);
		grid.addComponent(attributeCombo, 1, 1);
		
		label = new Label();
		label.setCaption(LABEL_META_VALUES);
		grid.addComponent(label, 0, 2);
		
		valueField = new TextField();
		valueField.setWidth(100, Unit.PERCENTAGE);
		grid.addComponent(valueField, 1, 2);
		
		label = new Label();
		label.setCaption(LABEL_CONTENT_ATTRIBUTE_VALUE);
		grid.addComponent(label, 0, 4);
		
		contentValuField = new TextField();
		contentValuField.setWidth(100, Unit.PERCENTAGE);
		grid.addComponent(contentValuField, 1, 4);
		
		btnAddAttribute = new Button("Add attribute");
		btnAddAttribute.addClickListener(this);
		grid.addComponent(btnAddAttribute, 1, 5);
		grid.setComponentAlignment(btnAddAttribute, Alignment.BOTTOM_RIGHT);
		
		grid.setColumnExpandRatio(0, 1);
		grid.setColumnExpandRatio(1, 1);
		
		HorizontalLayout horizLayout = new HorizontalLayout();
		horizLayout.addComponent(grid);
		horizLayout.setComponentAlignment(grid, Alignment.MIDDLE_LEFT);
		horizLayout.setWidth(80, Unit.PERCENTAGE);
		return horizLayout;
	}
	
	/**
	 * Refresh table rows
	 * @param id
	 */
	private void refreshTableData() {

		Collection<MetaTagDto> dtos = metaTagService.findBySiteId(siteId);
		tableBuilder.rebuild((Collection)dtos);
	}

	/**
	 * Button click handler
	 */
	@Override
	public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
		
		String attribute = ""; 
		String contentValue = "";
		if(attributeCombo.getValue() != null) { 
			attribute = attributeCombo.getValue().toString();
		}
		String attributeValue = valueField.getValue();
		contentValue = contentValuField.getValue();
		
		if( (attribute.equals("") || attributeValue.equals("") )) { 
			Notification.show("", "One or more field is empty.", Type.ERROR_MESSAGE);
		
		} else {
			
			MetaTagDto dto = new MetaTagDto();
			dto.setAttribute(attribute);
			dto.setAttributeValue(attributeValue);
			contentValue = (contentValue == null) ?  "" : contentValue;
			dto.setAttributeContent(contentValue);
			
			SiteService siteService = (SiteService) contextHelper.getBean("siteService");
			SiteDto site = siteService.findSiteById(siteId);
			dto.setSite(site);

			if(event.getButton().getCaption().equals(BUTTON_NAME_ADD)) { //add
				
				if(pageId == null) {
					dto.setAssociatedId(site.getSiteId());
					dto.setLevel("site"); 
				} else {
					dto.setAssociatedId(pageId); 
					dto.setLevel("page");
				}

				int id = metaTagService.create(dto);
				Notification.show("", MSG_ATTRIBUTE_CREATED, Type.TRAY_NOTIFICATION);
			} else  { //edit
				
				dto.setMetaTagId(metaTagDto.getMetaTagId());
				dto.setAssociatedId(metaTagDto.getAssociatedId());
				dto.setLevel(metaTagDto.getLevel());
				metaTagService.update(dto);
				Notification.show("", MSG_ATTRIBUTE_UPDATED, Type.TRAY_NOTIFICATION);
				btnAddAttribute.setCaption(BUTTON_NAME_ADD);
			}
			emptyFormFileds();
			refreshTableData();
		}
		
	}
	
	/**
	 * Empty SEO form fields
	 */
	private void emptyFormFileds() {
		attributeCombo.select(null);
		valueField.setValue("");
		contentValuField.setValue("");
	}	

	/**
	 * Edit handler for SEO table edit button
	 */
	@Override
	public void editAttribute(MetaTagDto dto) {
		metaTagDto = dto;
		attributeCombo.setValue((Object)dto.getAttribute());
		valueField.setValue(dto.getAttributeValue());
		contentValuField.setValue(dto.getAttributeContent());
		btnAddAttribute.setCaption(BUTTON_NAME_UPDATE);
	}

	/**
	 * Delete handler for SEO table delete button
	 */
	public void deleteAttribute(final MetaTagDto dto) {
		
	
		ConfirmDialog.show(UI.getCurrent(), "Please Confirm"," Are you really sure to delete?",
		        "Yes", "Cancel", new ConfirmDialog.Listener() {

			private static final long serialVersionUID = 1L;
		
			public void onClose(ConfirmDialog dialog) {
                if (dialog.isConfirmed()) {
                    // Confirmed to continue
                	
                	metaTagService.delete(dto);	
                	Notification.show("", MSG_ATTRIBUTE_DELETED, Type.TRAY_NOTIFICATION);
                	refreshTableData();
        			emptyFormFileds();
                } else {
                    // User did not confirm
                    
                }
            }
        });
    	
	}

}
