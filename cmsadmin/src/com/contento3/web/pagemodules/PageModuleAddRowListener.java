package com.contento3.web.pagemodules;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.service.TemplateService;
import com.contento3.common.exception.ResourceNotFoundException;
import com.contento3.web.UIManagerContext;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class PageModuleAddRowListener implements ClickListener {

	private static final Logger LOGGER = Logger.getLogger(PageModuleAddRowListener.class);
	
	private static final long serialVersionUID = 1L;

	final ComboBox comboBox;
	
	final VerticalLayout selectionLayout;
	
	final VerticalLayout previewLayout;
	
	final UIManagerContext context;
	
	final TemplateService templateService;

	final String pageSection;
	
	/**
	 * Panel that displays the html preview
	 */
	Panel htmlPanel = null;
	
	/**
	 * Label that contains the value of html for preview
	 */
	Label htmlLabel = null;

	final Map<Panel,String> addedTemplateMap = new LinkedHashMap <Panel,String>();
	
	final Button createPageButton;
	
	Label finalHtmlLabel;

	final Button previewButton;
	
	public PageModuleAddRowListener(final UIManagerContext context, final ComboBox combo,final VerticalLayout selectionLayout,final VerticalLayout previewLayout,final String pageSectionName,final Button createPageButton,final Label finalHtmlLabel,final Button previewButton) {
		this.comboBox = combo;
		this.selectionLayout = selectionLayout;
		this.previewLayout = previewLayout;
		this.context = context;
		templateService = (TemplateService)context.getHelper().getBean("templateService");
		htmlLabel = new Label("",ContentMode.HTML);
		this.pageSection = pageSectionName;
		this.createPageButton = createPageButton;
		this.finalHtmlLabel = finalHtmlLabel;
		this.previewButton = previewButton;
	}

	@Override
	public void buttonClick(final ClickEvent event) {
		previewButton.setEnabled(true);
		addRowPanel ();
	}

	public void addRowPanel(){

		createPageButton.setVisible(true);
		
		final Panel panel = new Panel();
		panel.setWidth(100,Unit.PERCENTAGE);
		panel.setHeight(45,Unit.PIXELS);
			
		getLayoutHtml(panel);

		final HorizontalLayout addedLayoutRow = new HorizontalLayout();
		final Label rowLabel = new Label(comboBox.getItemCaption(comboBox.getValue()));
			
		addedLayoutRow.addComponent(rowLabel);
		addedLayoutRow.setComponentAlignment(rowLabel,Alignment.TOP_LEFT);
		addedLayoutRow.setMargin(true);
		addedLayoutRow.setWidth(99,Unit.PERCENTAGE);
	
		final Button crossButton = new Button("X");
		crossButton.addStyleName("link");
		crossButton.setWidth(10, Unit.PERCENTAGE);
		crossButton.addClickListener(new PageDeleteRowListener(selectionLayout,panel,comboBox.getValue().toString(),addedTemplateMap,htmlLabel,previewLayout,createPageButton,previewButton));
		
		final Button contentPopulateButton = new Button("Content");
		contentPopulateButton.addStyleName("link");
		contentPopulateButton.setWidth(40,Unit.PIXELS);
		contentPopulateButton.addClickListener(new PageRowContentPopulatorListener(pageSection+"."+comboBox.getValue().toString(),context,panel));
		addedLayoutRow.addComponent(contentPopulateButton);
		addedLayoutRow.setComponentAlignment(contentPopulateButton,Alignment.MIDDLE_RIGHT);

		addedLayoutRow.addComponent(crossButton);
		addedLayoutRow.setComponentAlignment(crossButton,Alignment.MIDDLE_RIGHT);
		addedLayoutRow.setHeight(25,Unit.PIXELS);
			
		panel.setContent(addedLayoutRow);
		panel.setData(pageSection+"."+comboBox.getValue().toString());
		selectionLayout.addComponent(panel);
			
		if (htmlPanel==null){
			htmlPanel = new Panel();
			htmlPanel.setWidth(600,Unit.PIXELS);
			htmlPanel.setHeight(125,Unit.PIXELS);
	
			previewLayout.addComponent(htmlPanel);
			previewLayout.setComponentAlignment(htmlPanel,Alignment.BOTTOM_CENTER);
		}
		else if (!htmlPanel.isVisible()){
			htmlPanel.setVisible(true);
		}
		
		// Get the enclosing html template which has the placeholder
		TemplateDto mainTemplate = null;
		try {
			mainTemplate = templateService.findGlobalTemplateByKey("PAGE_MODULE_MAIN_TEMPLATE");
		} catch (final ResourceNotFoundException e) {
			LOGGER.debug("Template with key ["+"PAGE_MODULE_MAIN_TEMPLATE"+"] not found in the global template library");		
		}
		
		if (null!=mainTemplate){
			String temp = htmlLabel.getValue();
			finalHtmlLabel.setValue(StringUtils.replace(mainTemplate.getTemplateText(), "@C3_TEMPLATE_PLACEHOLDER", temp));
		}	
//		else {
//		//	finalHtmlLabel = htmlLabel;
//		}
	}
	
		
	private void getLayoutHtml(final Panel panel) {
		TemplateDto templateDto;
		final String templateKey = comboBox.getValue().toString();

		try {
			templateDto = templateService.findGlobalTemplateByKey(templateKey);
			if (null!=templateDto){
				final String labelPrevious = htmlLabel.getValue();
				
				//Map that holds template key as key and it's html as value
				if (null==addedTemplateMap.get(panel)){
					htmlLabel.setValue(labelPrevious+templateDto.getTemplateText());
					addedTemplateMap.put(panel, templateDto.getTemplateText());
				}
			}
			else {
				htmlLabel.setValue("");
			}
		} 
		catch (final ResourceNotFoundException e) {
			LOGGER.debug("Template with key ["+templateKey+"] not found in the global template library");		
		}
	}

}

