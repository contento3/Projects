package com.olive.web.content;

import com.olive.cms.constant.NavigationConstant;
import com.olive.web.UIManager;
import com.olive.web.helper.SpringContextHelper;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Window;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;

public class ContentUIManager implements UIManager{

    private SpringContextHelper helper;
	private Window parentWindow;

	private String[] navigationItems = {NavigationConstant.CONTENT_ART_MGMT,NavigationConstant.CONTENT_IMG_MGMT,NavigationConstant.CONTENT_VID_MGMT};
	
	public ContentUIManager(final SpringContextHelper helper,final Window parentWindow){
		this.helper = helper;
		this.parentWindow = parentWindow;
	}
 
	public void render(){
		System.out.println("Rendering content ui ......");
	}
	
	@Override
	public Component render(final String command){
		Component componentToReturn = null;
		System.out.println("Rendering content ui ......");

		return componentToReturn;
	}
	
	@Override
	public Component render(final String command,final Integer id){
		System.out.println("Rendering content ui ......");

		return null;
	}

	@Override
	public Component render(final String command,final HierarchicalContainer hwContainer){
		Component tabsheet = null;
		if (command.equals(NavigationConstant.CONTENT_MANAGER)){
			//Add the content screen tab and also add the child items
			tabsheet = new TabSheet();
			renderContentNavigationItems(hwContainer);
		}
		else if (command.equals(NavigationConstant.CONTENT_ART_MGMT)){
			tabsheet = renderArticleUI();
		}
		else if (command.equals(NavigationConstant.CONTENT_IMG_MGMT)){
			tabsheet = renderImageUI();
		}
		else if (command.equals(NavigationConstant.CONTENT_VID_MGMT)){
			tabsheet = renderVideoUI();
		}
		return tabsheet;
	}

	public void renderContentNavigationItems(final HierarchicalContainer hwContainer){
		System.out.println("Rendering content ui 123......");
		
		for (String navigationItem : navigationItems){
			Item item = hwContainer.addItem(navigationItem);
			if (null != item){
				item.getItemProperty("name").setValue(navigationItem);
				hwContainer.setParent(navigationItem, NavigationConstant.CONTENT_MANAGER);
				hwContainer.setChildrenAllowed(navigationItem, false);
			}
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

	private Component renderContentElementUI(final String element){
		final TabSheet elementTab = new TabSheet();
		elementTab.setHeight(100, Sizeable.UNITS_PERCENTAGE);

		final VerticalLayout verticalLayout = new VerticalLayout();

		Button button = new Button();
		verticalLayout.addComponent(button);
		verticalLayout.setHeight(100, Sizeable.UNITS_PERCENTAGE);
		elementTab.addTab(verticalLayout, String.format("%s Management",element));

		button.addListener(new ClickListener(){
			public void buttonClick(ClickEvent event){
				if (element.equals("Article")){
					ArticleMgmtUIManager artMgmtUIMgr = new ArticleMgmtUIManager();
					VerticalLayout newArticleLayout = new VerticalLayout();
					Tab createNew = elementTab.addTab(newArticleLayout, String.format("Create new %s",element));
					createNew.setClosable(true);
					elementTab.setSelectedTab(newArticleLayout);
					newArticleLayout.addComponent(artMgmtUIMgr.renderAddScreen());
					newArticleLayout.setHeight("100%");
				}
				else if (element.equals("Image")){
					ImageMgmtUIManager imageMgmtUIMgr = new ImageMgmtUIManager(helper,parentWindow);
					VerticalLayout newArticleLayout = new VerticalLayout();
					Tab createNew = elementTab.addTab(newArticleLayout, String.format("Create new %s",element));
					createNew.setClosable(true);
					elementTab.setSelectedTab(newArticleLayout);
					newArticleLayout.addComponent(imageMgmtUIMgr.renderAddScreen());
					newArticleLayout.setHeight("100%");
				}

   	        }
    	});
		
		button.setCaption(String.format("Add %s",element));
		
		return elementTab;
	}
	
}
