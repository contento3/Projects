package com.contento3.web.pagemodules;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.contento3.cms.page.section.model.PageSectionTypeEnum;
import com.contento3.util.CachedTypedProperties;
import com.contento3.web.UIManagerContext;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;


public class PageModuleDesignListener implements com.vaadin.event.MouseEvents.ClickListener{
	private static final Logger LOGGER = Logger.getLogger(PageModuleDesignListener.class);

	private static final long serialVersionUID = 1L;

	private VerticalLayout contentLayout;
	
	final UIManagerContext uiContext;
	
	final PageModuleLayoutsEnum layout;
	
	Button createPageButton = null;
	
	Label finalHtmlLabel = new Label("",ContentMode.HTML); 
	
	public PageModuleDesignListener (final VerticalLayout mainContentLayout,final UIManagerContext uiContext,final PageModuleLayoutsEnum pageLayout){
		this.contentLayout = mainContentLayout;
		this.uiContext = uiContext;
		this.layout = pageLayout;
		UI.getCurrent().getSession().getSession().removeAttribute("templateDtoMap");
		createPageButton = new Button("Create page");
	}
	
	@Override
	public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
		contentLayout.removeAllComponents();
		contentLayout.addComponent(buildPageSection(PageSectionTypeEnum.HEADER));
		contentLayout.setMargin(true);
		
		createPageButton.addClickListener(new PageModuleCombinedListener(uiContext,layout));
		contentLayout.addComponent(createPageButton);
		contentLayout.setComponentAlignment(createPageButton,Alignment.BOTTOM_RIGHT);
	}

	
	private VerticalLayout buildPageSection(final PageSectionTypeEnum pageSectionName){
		HorizontalLayout selectionLayout = new HorizontalLayout();

		final VerticalLayout pageSectionsContainer = new VerticalLayout();
		final VerticalLayout pageSectionPreview = new VerticalLayout();
		pageSectionPreview.setSizeFull();
		pageSectionPreview.setWidth(100,Unit.PERCENTAGE);
		pageSectionPreview.setMargin(true);
		pageSectionPreview.setSpacing(true);	
		pageSectionsContainer.setMargin(true);
		pageSectionsContainer.setWidth(500,Unit.PIXELS);

		if (pageSectionName.equals(PageSectionTypeEnum.HEADER)){
			selectionLayout = new HorizontalLayout();
			
			final ComboBox pageSectionLayoutOptionsCombo = new ComboBox("SELECT "+PageSectionTypeEnum.HEADER);
			pageSectionLayoutOptionsCombo.setContainerDataSource(loadCombo(pageSectionName));
			pageSectionLayoutOptionsCombo.setWidth(100,Unit.PERCENTAGE);
			pageSectionLayoutOptionsCombo.setItemCaptionMode(ItemCaptionMode.EXPLICIT);
			pageSectionLayoutOptionsCombo.setItemCaptionPropertyId("name");

			final FormLayout formLayout = new FormLayout();
			formLayout.setWidth(20,Unit.PERCENTAGE);
			formLayout.setSizeUndefined();
			formLayout.addStyleName("horizontalForm");
			formLayout.addComponent(pageSectionLayoutOptionsCombo);
			
			selectionLayout.addComponent(formLayout);

			final Button previewSectionButton = new Button ("Preview");
			previewSectionButton.setEnabled(false);
			
			final Button sectionAddButton = new Button("Add"); 
			selectionLayout.addComponent(sectionAddButton);
			selectionLayout.setComponentAlignment(sectionAddButton,Alignment.MIDDLE_CENTER);
			sectionAddButton.addClickListener(new PageModuleAddRowListener(uiContext, pageSectionLayoutOptionsCombo,pageSectionsContainer,pageSectionPreview,pageSectionName.toString(),createPageButton,finalHtmlLabel, previewSectionButton));
			
			selectionLayout.addComponent(previewSectionButton);
			selectionLayout.setComponentAlignment(previewSectionButton,Alignment.MIDDLE_CENTER);
			previewSectionButton.addClickListener(new PageSectionPreviewListener(finalHtmlLabel));

			selectionLayout.setSpacing(true);
			selectionLayout.setMargin(true);
		}

		final VerticalLayout pageSectionHeadLayout = new VerticalLayout();
		pageSectionHeadLayout.addComponent(new Label ("<h3>"+PageSectionTypeEnum.HEADER.toString()+"</h3>",ContentMode.HTML));
		pageSectionHeadLayout.addComponent(selectionLayout);
		pageSectionHeadLayout.setWidth(100,Unit.PERCENTAGE);

		final VerticalLayout parentContainer = new VerticalLayout(); 
		parentContainer.addComponent(pageSectionHeadLayout);

		final HorizontalLayout pageSectionLayout = new HorizontalLayout();
		pageSectionLayout.setHeight(100,Unit.PERCENTAGE);
		pageSectionLayout.setWidth(100,Unit.PERCENTAGE);
		
		pageSectionLayout.addComponent(pageSectionsContainer);
		pageSectionLayout.addComponent(pageSectionPreview);

		pageSectionLayout.setExpandRatio(pageSectionsContainer, 2);
		pageSectionLayout.setExpandRatio(pageSectionPreview, 2);
		
		parentContainer.addComponent(pageSectionLayout);
		return parentContainer;
	}
	

	private List <String> getLayoutConfig(final String layoutName) {
		try {
			final CachedTypedProperties properties = CachedTypedProperties.getInstance("pagemodulesconfig.properties");
			final List <String> layoutInfo = properties.getDelimetedProperty(layoutName,"|");
			return layoutInfo;
		} catch (final ClassNotFoundException e) {
			LOGGER.error("Unable to read languages.properties,Reason:"+e);
		}
		catch (final NullPointerException npe) {
			LOGGER.error("Layouts with name: ["+layoutName+"] not exist in the pagemodulesconfig.properties file"+npe);
			return null;
		}
		return null;
	}
	
	private Container loadCombo (final PageSectionTypeEnum pageSectionName){
		final IndexedContainer container = new IndexedContainer();
		container.addContainerProperty("name", String.class, null);
		container.addContainerProperty("value", String.class, null);

		try {
			final CachedTypedProperties properties = CachedTypedProperties.getInstance("pagemodulelist.properties");
			final String pageSection = pageSectionName.toString();
			final List <String> layoutList = properties.getDelimetedProperty(pageSectionName.toString(),",");

			for (String layout : layoutList) {
				final List <String> layoutInfo = getLayoutConfig(pageSection+"."+layout);
				if (!CollectionUtils.isEmpty(layoutInfo)){
					final Item pageLayoutItem = container.addItem(layout);
					pageLayoutItem.getItemProperty("name").setValue(layoutInfo.get(0));
					pageLayoutItem.getItemProperty("value").setValue(layout);
				}
				
			}
		} catch (final ClassNotFoundException e) {
			LOGGER.error("Unable to read languages.properties,Reason:"+e);
		}

		container.sort(new Object[] { "name" }, new boolean[] { true });
		return container;

	}
}
