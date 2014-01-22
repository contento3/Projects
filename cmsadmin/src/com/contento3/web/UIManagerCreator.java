package com.contento3.web;

import com.contento3.web.content.ContentUIManager;
import com.contento3.web.content.article.ArticleMgmtUIManager;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.layout.LayoutUIManager;
import com.contento3.web.site.PageCategoryUIManager;
import com.contento3.web.site.SiteUIManager;
import com.contento3.web.site.SitesDashBoard;
import com.contento3.web.template.TemplateUIManager;
import com.contento3.web.user.security.SecurityUIManager;
import com.vaadin.ui.TabSheet;

public class UIManagerCreator {

	public static UIManager createUIManager(final TabSheet uiTabSheet,final Manager manager,final SpringContextHelper helper){
	    UIManager uiMgr = null;
	    
	    UIManager contentUIMgr = null;
		if (manager.equals(Manager.Content)){
			if (contentUIMgr==null)
				contentUIMgr = new ContentUIManager(uiTabSheet,helper);
			return contentUIMgr;
		}
		else if (manager.equals(Manager.Dashboard)){
			SitesDashBoard siteDashboard = new SitesDashBoard(uiTabSheet,helper);
			return siteDashboard;
		}
		else if (manager.equals(Manager.Layout)){
			uiMgr = new LayoutUIManager(uiTabSheet,helper);
			return uiMgr;
		}
		else if (manager.equals(Manager.Site)){
		 	uiMgr = new SiteUIManager(uiTabSheet,helper);
			return uiMgr;
		}
		else 
	    if (manager.equals(Manager.Template)){
	    	UIManager templateUIMgr;
			templateUIMgr = new TemplateUIManager(uiTabSheet,helper);
			return templateUIMgr;
	    }
		else if (manager.equals(Manager.User)){
		 	uiMgr = new SecurityUIManager(uiTabSheet,helper);
			return uiMgr;
		}
		else if (manager.equals(Manager.Category)){
			UIManager categoryUIMgr;
			categoryUIMgr = new PageCategoryUIManager(uiTabSheet,helper);
			return categoryUIMgr;
		}
			return null;
	}
	
}

