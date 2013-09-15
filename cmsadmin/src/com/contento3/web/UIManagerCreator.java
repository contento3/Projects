package com.contento3.web;

import com.contento3.web.content.ContentUIManager;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.layout.LayoutUIManager;
import com.contento3.web.site.PageCategoryUIManager;
import com.contento3.web.site.SiteUIManager;
import com.contento3.web.template.TemplateUIManager;
import com.contento3.web.user.security.SecurityUIManager;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class UIManagerCreator {

	public static UIManager createUIManager(final TabSheet uiTabSheet,final Manager manager,final SpringContextHelper helper){
	    UIManager uiMgr = null;
	    
		if (manager.equals(Manager.Content)){
			uiMgr = new ContentUIManager(uiTabSheet,helper);
		}
		else if (manager.equals(Manager.Layout)){
			uiMgr = new LayoutUIManager(uiTabSheet,helper);
		}
		else if (manager.equals(Manager.Site)){
		 	uiMgr = new SiteUIManager(uiTabSheet,helper);
		}
		else 
	    if (manager.equals(Manager.Template)){
		 	uiMgr = new TemplateUIManager(uiTabSheet,helper);
		}
		else if (manager.equals(Manager.User)){
		 	uiMgr = new SecurityUIManager(uiTabSheet,helper);
		}
		else if (manager.equals(Manager.Category)){
		 	uiMgr = new PageCategoryUIManager(uiTabSheet,helper);
		}
	
		return uiMgr;
	}
	
}

