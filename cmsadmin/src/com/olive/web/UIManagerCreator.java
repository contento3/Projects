package com.olive.web;

import com.olive.web.content.ContentUIManager;
import com.olive.web.helper.SpringContextHelper;
import com.olive.web.layout.LayoutUIManager;
import com.olive.web.site.SiteUIManager;
import com.olive.web.template.TemplateUIManager;
import com.vaadin.ui.Window;

public class UIManagerCreator {

	public static UIManager createUIManager(Manager manager,SpringContextHelper helper,Window parentWindow){
	    UIManager uiMgr = null;
	    
		if (manager.equals(Manager.Content)){
			uiMgr = new ContentUIManager(helper,parentWindow);
		}
		else if (manager.equals(Manager.Layout)){
			uiMgr = new LayoutUIManager(helper,parentWindow);
		}
		else if (manager.equals(Manager.Site)){
		 	uiMgr = new SiteUIManager(helper,parentWindow);
		}
		else if (manager.equals(Manager.Template)){
		 	uiMgr = new TemplateUIManager(helper,parentWindow);
		}
	
		return uiMgr;
	}
	
}

