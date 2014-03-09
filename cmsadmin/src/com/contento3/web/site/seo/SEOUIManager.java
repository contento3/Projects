package com.contento3.web.site.seo;

import java.util.Collection;

import com.contento3.cms.seo.dto.MetaTagDto;
import com.contento3.cms.seo.service.MetaTagService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.helper.SpringContextHelper;
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
import com.vaadin.ui.VerticalLayout;

public class SEOUIManager implements ClickListener  {
	
	private static final long serialVersionUID = 1L;
	private final static String SCREEN_HEADING = "SEO Settings";
	private final static String LABEL_META_ATTRIBUTE= "Meta attributes";
	private final static String LABEL_CONTENT_ATTRIBUTE_VALUE= "Content attribute value";
	private final static String LABEL_META_VALUES= "Values";
	private static final String MSG_CREATE_ATTRIBUTE = "Attribute added successfuly.";
	
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
	private TextField valuField;
	
	/**
	 * Textfield for content value
	 */
	private TextField contentValuField;
	
	/**
	 * Table component
	 */
	private SEOTableBuilder tableBuilder;
	
	public SEOUIManager(final SpringContextHelper helper) {
		
		this.contextHelper = helper;
		this.metaTagService = (MetaTagService) helper.getBean("metaTagService");
	}
	
	public void renderSEOSettingsManager(final TabSheet tabSheet,final Integer siteId) {
		
		this.siteId = siteId;
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

	private void addUIComponents() {

		seoParentLayout.addComponent(createScreenHeading());	
		seoParentLayout.addComponent(new HorizontalRuler());
		seoParentLayout.addComponent(createTableForSeo());
		seoParentLayout.addComponent(createAddingAttributeSection());
	}

	private Label createScreenHeading() {
		
		final Label heading = new Label(SCREEN_HEADING);
		heading.setStyleName("screenHeading");
		return heading;
	}
	
	private Table createTableForSeo() {
		
		Collection<MetaTagDto> tags = metaTagService.findBySiteId(siteId);
	
		Table table = new Table();
		table.setImmediate(true);
		
		tableBuilder = new SEOTableBuilder(table);
		tableBuilder.build((Collection)tags);
		return table;
	}
	
	private HorizontalLayout createAddingAttributeSection() {
					
		GridLayout grid = new GridLayout(2, 5);
		grid.setMargin(true);
		grid.setWidth("400px");
		grid.setHeight("200px");
		
		Label label = new Label();
		label.setCaption(LABEL_META_ATTRIBUTE);
		grid.addComponent(label, 0, 0);
		
		attributeCombo = new ComboBox();
		attributeCombo.setValue(new String("test"));
		attributeCombo.setWidth(100, Unit.PERCENTAGE);
		grid.addComponent(attributeCombo, 1, 0);
		
		label = new Label();
		label.setCaption(LABEL_META_VALUES);
		grid.addComponent(label, 0, 1);
		
		valuField = new TextField();
		valuField.setWidth(100, Unit.PERCENTAGE);
		grid.addComponent(valuField, 1, 1);
		
		label = new Label();
		label.setCaption(LABEL_CONTENT_ATTRIBUTE_VALUE);
		grid.addComponent(label, 0, 3);
		
		contentValuField = new TextField();
		contentValuField.setWidth(100, Unit.PERCENTAGE);
		grid.addComponent(contentValuField, 1, 3);
		
		Button btnAddAttribute = new Button("Add attribute");
		btnAddAttribute.addClickListener(this);
		grid.addComponent(btnAddAttribute, 1, 4);
		grid.setComponentAlignment(btnAddAttribute, Alignment.MIDDLE_RIGHT);
		
		grid.setColumnExpandRatio(0, 1);
		grid.setColumnExpandRatio(1, 1);
		
		HorizontalLayout horizLayout = new HorizontalLayout();
		horizLayout.addComponent(grid);
		horizLayout.setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
		horizLayout.setWidth(80, Unit.PERCENTAGE);
		return horizLayout;
	}
	
	private void refreshTableData() {
		//MetaTagDto dto = metaTagService.findByID(id)
		//tableBuilder.addItem(dto);
	}

	@Override
	public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
		
		String attribute = "test"; 
		if(attributeCombo.getValue() != null) { 
			attributeCombo.getValue().toString();
		}
		String attributeValue = valuField.getValue();
		String contentValue = valuField.getValue();
		
		if(attribute.equals("") || attributeValue.equals("") || contentValue.equals("")) {
			Notification.show("", "One or more field is empty.", Type.ERROR_MESSAGE);
		} else {
			MetaTagDto dto = new MetaTagDto();
			dto.setAttribute(attribute);
			dto.setAttributeValue(attributeValue);
			dto.setAttributeContent(contentValue);
			SiteService siteService = (SiteService) contextHelper.getBean("siteService");
			SiteDto site = siteService.findSiteById(siteId);
			dto.setSite(site);
			metaTagService.create(dto);
			Notification.show("", MSG_CREATE_ATTRIBUTE, Type.ERROR_MESSAGE);
			//refreshTableData();
		}
	}

}
