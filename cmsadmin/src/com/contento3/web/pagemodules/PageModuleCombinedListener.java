package com.contento3.web.pagemodules;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.web.UIManagerContext;
import com.contento3.web.common.helper.ComboDataLoader;
import com.contento3.web.common.helper.SessionHelper;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class PageModuleCombinedListener implements ClickListener {

	private static final long serialVersionUID = 1L;

	private final Window popupWindow;

	final private HorizontalLayout mainContentLayout = new HorizontalLayout();

	private ComboBox sitesComboBox;

	final SiteService siteService;

	UIManagerContext context;
	
	final PageModuleLayoutsEnum pageLayout;
	
	final Map <String,TemplateDto> pageLayoutsMap = new HashMap<String,TemplateDto> ();
	
	public PageModuleCombinedListener (final UIManagerContext context,final PageModuleLayoutsEnum pageLayout) {
		popupWindow = new Window();
		siteService = (SiteService)context.getHelper().getBean("siteService");
		this.context = context;
		this.pageLayout = pageLayout;
	}
	
	@Override
	public void buttonClick(final ClickEvent event) {
		popupWindow.setPositionX(375);
    	popupWindow.setPositionY(300);
    	popupWindow.setHeight(20,Unit.PERCENTAGE);
    	popupWindow.setWidth(45,Unit.PERCENTAGE);
    	popupWindow.setResizable(false);

    	mainContentLayout.setHeight(100,Unit.PERCENTAGE);
    	mainContentLayout.setWidth(100,Unit.PERCENTAGE);
    	mainContentLayout.setSpacing(true);
    	mainContentLayout.setMargin(true);

    	popupWindow.setContent(mainContentLayout);
    	popupWindow.setCaption("Select site to create page");
    	
    	if (null==sitesComboBox){
	    	final Collection <SiteDto> siteDtos = (Collection <SiteDto>)siteService.findSitesByAccountId((Integer)SessionHelper.loadAttribute("accountId"), false);
	    	final ComboDataLoader loader = new ComboDataLoader();
	    	final IndexedContainer sitesDataContainer = loader.loadDataInContainer((Collection)siteDtos);
			
	    	sitesComboBox = new ComboBox("Select site to create page",sitesDataContainer);
	    	sitesComboBox.setItemCaptionMode(ComboBox.ItemCaptionMode.PROPERTY);
	    	sitesComboBox.setItemCaptionPropertyId("name");
	    	sitesComboBox.setImmediate(true);

	    	mainContentLayout.addComponent(sitesComboBox);

	    	final TextField pageTitleTextField = new TextField("Enter page title");
	    	mainContentLayout.addComponent(pageTitleTextField);

	    	final Button theFinalButton = new Button("Done");
	    	theFinalButton.setDisableOnClick(true);
	    	theFinalButton.addClickListener(new PageTemplateCreatorListener(this.context,pageLayout,sitesComboBox,pageTitleTextField));
	    	mainContentLayout.addComponent(theFinalButton);
	    	mainContentLayout.setComponentAlignment(theFinalButton, Alignment.MIDDLE_CENTER);
    	}
    
    	UI.getCurrent().addWindow(popupWindow);
	}

}
