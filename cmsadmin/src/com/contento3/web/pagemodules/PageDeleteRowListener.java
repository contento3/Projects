package com.contento3.web.pagemodules;

import java.util.Map;

import org.thymeleaf.util.MapUtils;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class PageDeleteRowListener implements ClickListener {

	private static final long serialVersionUID = 1L;

	final VerticalLayout selectionLayout;

	final String selectedTemplateKey;
	
	final Panel panel;
	
	final Map <Panel, String> addedTemplateMap;
	
	final Label htmlLabel;
	
	Panel previewHtmlPanel;
	
	final VerticalLayout previewLayout;
	
	final Button createPageButton;
	
	final Button previewButton;
	
	public PageDeleteRowListener(final VerticalLayout selectionLayout,final Panel panel,final String selectedTemplateKey,final Map <Panel,String> addedTemplateMap,final Label label,final VerticalLayout previewLayout,final Button createPageButton,final Button previewButton){
		this.panel = panel;
		this.selectedTemplateKey = selectedTemplateKey;
		this.selectionLayout = selectionLayout;
		this.addedTemplateMap = addedTemplateMap;
		this.htmlLabel = label;
		this.previewLayout = previewLayout;
		this.createPageButton = createPageButton;
		this.previewButton = previewButton;
	}
	
	@Override
	public void buttonClick(final ClickEvent event) {
		
		//Remove the ui
		final Integer indexOfPanelToRemove = selectionLayout.getComponentIndex(panel);
		selectionLayout.removeComponent(panel);
		
		//Update the html by 
		addedTemplateMap.remove(panel);

		if (MapUtils.isEmpty(addedTemplateMap)){
			previewHtmlPanel = (Panel)this.previewLayout.getComponent(0);
			previewHtmlPanel.setVisible(false);
			
			//Final create button must be disabled if nothing is there to add
			createPageButton.setVisible(false);
			
			//Disable the preview button
			previewButton.setEnabled(false);
		}
		
		rebuildHTML ();
		//TODO remove itemToDelete tempalte;
	}

	private void rebuildHTML () {
		StringBuilder htmlString = new StringBuilder();
		
		for (Map.Entry<Panel, String> entry : addedTemplateMap.entrySet()){
			htmlString.append(entry.getValue());
		}			
		htmlLabel.setValue(htmlString.toString());
	}
}
