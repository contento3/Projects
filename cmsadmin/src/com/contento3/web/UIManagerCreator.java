package com.contento3.web;

import com.contento3.web.content.ContentUIManager;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.layout.LayoutUIManager;
import com.contento3.web.site.SiteUIManager;
import com.contento3.web.template.TemplateUIManager;
import com.contento3.web.user.security.UserUIManager;
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
		else if (manager.equals(Manager.User)){
		 	uiMgr = new UserUIManager(helper,parentWindow);
		}
	
		return uiMgr;
	}
	
}

