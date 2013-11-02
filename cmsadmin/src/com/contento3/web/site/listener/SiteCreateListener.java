package com.contento3.web.site.listener;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.common.dto.Dto;
import com.contento3.security.role.service.RoleService;
import com.contento3.web.Manager;
import com.contento3.web.UIManager;
import com.contento3.web.UIManagerCreator;
import com.contento3.web.common.helper.EntityListener;
import com.contento3.web.common.helper.GenricEntityPicker;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.site.SiteUIManager;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class SiteCreateListener extends EntityListener implements ClickListener{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Vertical layout for pop-up
	 */
	private VerticalLayout vLayout;
	
	/**
	 *  Reference to main window
	 */
	Window mainwindow; 
	
	/**
	 * Used to get service beans from spring context.
	 */
	SpringContextHelper helper;
	
	TabSheet uiTabSheet;
	
	final UIManager siteUIMgr;
	/**
	 * Constructor
	 * @param main
	 * @param helper
	 * @param table
	 * @param groupId
	 * @param tableBuilder
	 */
	public SiteCreateListener(final SpringContextHelper helper,final TabSheet uiTabsheet,final VerticalLayout verticalLayout) {
		this.helper = helper;
		siteUIMgr = UIManagerCreator.createUIManager(uiTabsheet,Manager.Site,helper);
		siteUIMgr.render(SiteUIManager.NEWSITE);
		uiTabSheet = uiTabsheet;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		SiteCreatePopup popup= new SiteCreatePopup(helper);
	}

}
