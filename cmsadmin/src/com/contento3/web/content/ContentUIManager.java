package com.contento3.web.content;

import org.apache.shiro.SecurityUtils;

import com.contento3.cms.constant.NavigationConstant;
import com.contento3.web.UIManager;
import com.contento3.web.content.article.ArticleMgmtUIManager;
import com.contento3.web.content.document.DocumentMgmtUIManager;
import com.contento3.web.content.image.ImageMgmtUIManager;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TabSheet;


/**
 * Entry point for all the logic related to CONTENT MANAGER functionality.
 * @author HAMMAD
 *
 */
public class ContentUIManager implements UIManager{

	/**
	 * Helper class to get beans from the spring application context.
	 */
    private SpringContextHelper helper;
    
	/**
	 * Main tabsheet that hold all the content.
	 */
	private TabSheet elementTab;
	
	ArticleMgmtUIManager articleManager;
	
	ImageMgmtUIManager imageManager;
	
	DocumentMgmtUIManager documentMgmtUIMgr;
	/**
	 * Represents the navigation items in the Content Manager section.
	 */
	private String[] navigationItems = {NavigationConstant.DOCUMENT_MGMT, NavigationConstant.CONTENT_ART_MGMT
			,NavigationConstant.CONTENT_IMG_MGMT
			//TODO Need to uncomment when we add Video related functionality in the cms ,NavigationConstant.CONTENT_VID_MGMT
			};
	
	final CssLayout verticalLayout = new CssLayout();


	/**
	 * Class constructor
	 * @param helper
	 * @param parentWindow
	 */
	public ContentUIManager(final TabSheet uiTabSheet,final SpringContextHelper helper){
		this.helper = helper;
		this.elementTab = uiTabSheet;
	}

	@Override
	public void render(){
	}

	@Override
	public Component render(final String command){
		Component componentToReturn = null;
		return componentToReturn;
	}

	@Override
	public Component render(final String command,final Integer id){
		return null;
	}

	@Override
	public Component render(final String command,final HierarchicalContainer hwContainer){
		Component tabsheet = null;
		if (command.equals(NavigationConstant.CONTENT_MANAGER)){
			//Add the content screen tab and also add the child items
			//tabsheet = new TabSheet();
			if (hwContainer.size()!=0)
				renderContentNavigationItems(hwContainer);
			
		}
		else if (command.equals(NavigationConstant.CONTENT_ART_MGMT)  && SecurityUtils.getSubject().isPermitted("ARTICLE:NAVIGATION")){
			tabsheet = renderArticleUI();
		}
		else if (command.equals(NavigationConstant.CONTENT_IMG_MGMT)  && SecurityUtils.getSubject().isPermitted("IMAGE:NAVIGATION")){
			tabsheet = renderImageUI();
		}
		else if (command.equals(NavigationConstant.CONTENT_VID_MGMT)  && SecurityUtils.getSubject().isPermitted("VIDEO:NAVIGATION")){
			tabsheet = renderVideoUI();
		}
		else if (command.equals(NavigationConstant.DOCUMENT_MGMT)  && SecurityUtils.getSubject().isPermitted("DOCUMENT:NAVIGATION")){
			tabsheet = renderDocumentUI();
		}
		return tabsheet;
	}

	/**
	 * Renders all the navigation items in the Content Manager section
	 * @param hwContainer
	 */
	public void renderContentNavigationItems(final HierarchicalContainer hwContainer){
		for (String navigationItem : navigationItems){
			if (navigationItem.equals(NavigationConstant.CONTENT_ART_MGMT)){
				if (SecurityUtils.getSubject().isPermitted("ARTICLE:NAVIGATION")){
					addItemToParent(hwContainer,navigationItem);
				}
			}
			else if (navigationItem.equals(NavigationConstant.CONTENT_IMG_MGMT)){
				if (SecurityUtils.getSubject().isPermitted("IMAGE:NAVIGATION")){
					addItemToParent(hwContainer,navigationItem);
				}
			}
			else if (navigationItem.equals(NavigationConstant.CONTENT_VID_MGMT)){
				if (SecurityUtils.getSubject().isPermitted("VIDEO:NAVIGATION")){
					addItemToParent(hwContainer,navigationItem);
				}
			}
			else if (navigationItem.equals(NavigationConstant.DOCUMENT_MGMT)){
				if (SecurityUtils.getSubject().isPermitted("DOCUMENT:NAVIGATION")){
					addItemToParent(hwContainer,navigationItem);
				}
			}
		}
	}
	
	private void addItemToParent(final HierarchicalContainer hwContainer,final String navigationItem){
		Item item = hwContainer.addItem(navigationItem);
		if (null != item){
			item.getItemProperty("name").setValue(navigationItem);
			hwContainer.setParent(navigationItem, NavigationConstant.CONTENT_MANAGER);
			hwContainer.setChildrenAllowed(navigationItem, false);
		}
	}

	private Component renderArticleUI(){
		return renderContentElementUI("Article");
	}

	private Component renderVideoUI(){
		TabSheet videoTab = new TabSheet();
		return renderContentElementUI("Video");
	}

	private Component renderImageUI(){
		TabSheet imageTab = new TabSheet();
		return renderContentElementUI("Image");
	}

	private Component renderDocumentUI() {
		TabSheet documentTab = new TabSheet();
		return renderContentElementUI("Document");
	}

	/**
	 * Calls the appropiate sub ui manager based on the argument.
	 * @param element Tells what sub ui manager is require to be returned.
	 * @return
	 */
	private Component renderContentElementUI(final String element){
		//final TabSheet elementTab = new TabSheet();
		elementTab.setHeight(100, Unit.PERCENTAGE);

		
		if(element.equals("Article")){
			if (null==articleManager){
			articleManager = new ArticleMgmtUIManager(elementTab,helper);
			}
			elementTab = (TabSheet) articleManager.render(null);
		}
		else if(element.equals("Image")){
			if (null==imageManager){
				imageManager = new ImageMgmtUIManager(elementTab,helper);
			}
			elementTab = (TabSheet) imageManager.render(null);
		}
		else if(element.equals("Document")){
			if (null==documentMgmtUIMgr){
				documentMgmtUIMgr = new DocumentMgmtUIManager(elementTab,helper);
			}
			elementTab = (TabSheet) documentMgmtUIMgr.render(null);	
		}

		return elementTab;
	}


}