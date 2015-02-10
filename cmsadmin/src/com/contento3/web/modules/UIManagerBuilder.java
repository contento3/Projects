package com.contento3.web.modules;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.AuthorizationException;

import com.contento3.common.dto.Dto;
import com.contento3.web.UIManagerContext;
import com.contento3.web.common.ListingUIHeaderBuilder;
import com.contento3.web.common.SearchBarFieldInfo;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.ScreenToolbarBuilder;
import com.contento3.web.email.marketing.NewsletterTableBuilder;
import com.contento3.web.email.marketing.NewsletterUIManager;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractSplitPanel.SplitterClickEvent;
import com.vaadin.ui.AbstractSplitPanel.SplitterClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;


public class UIManagerBuilder {

	private static final Logger LOGGER = Logger.getLogger(UIManagerBuilder.class);

	private Tree root;

    final HierarchicalContainer hwContainer;

    HorizontalLayout horizontalLayout = new HorizontalLayout();
    
	public UIManagerBuilder () {
		hwContainer = new HierarchicalContainer();
        root = new Tree("",hwContainer);
        root.setStyleName(BaseTheme.TREE_CONNECTORS);
        root.addContainerProperty("icon", Resource.class, null);
        root.setItemIconPropertyId("icon");
        hwContainer.addContainerProperty("name", String.class, null);
        hwContainer.addContainerProperty("id", Integer.class, null);
        root.addContainerProperty("icon", Resource.class, null);
	}
	
	public void buildTree(final ItemClickListener treeItemClickListener,final HorizontalSplitPanel horiz, final Action.Handler action,final Map<String,String> navConstantPermissionMap){
        
        // Add a horizontal SplitPanel to the lower area
        horiz.setLocked(true);
        horiz.setSplitPosition(12);
        
        horiz.addSplitterClickListener(new SplitterClickListener(){
			private static final long serialVersionUID = 1L;

			public void splitterClick(SplitterClickEvent event){
				float splitPosition = horiz.getSplitPosition();
				
		        if (splitPosition==2)
		        	horiz.setSplitPosition(15);
		        else
					horiz.setSplitPosition(2);
			}
    	});
    	
        HorizontalLayout hLayout = new HorizontalLayout();
        

        createNavigation(hwContainer,navConstantPermissionMap);
        Item childItem = null;

        hLayout.addComponent(root);
       // hLayout.addComponent(uri);
        hLayout.setWidth(100,Unit.PERCENTAGE);
        horiz.addComponent(hLayout);
        horiz.setWidth(100, Unit.PERCENTAGE);
        horiz.addComponent(horizontalLayout);
        horizontalLayout.setWidth(100, Unit.PERCENTAGE);

        root.addActionHandler(action);
    	root.setImmediate(true);

	   //When the item from the navigation is clicked then the 
       //below code will handle what is required to be done
        root.addItemClickListener(treeItemClickListener);
	}
	
    /*
     * Used to handle events after the user clicks the 
     * context menu from the left navigation menu.
     */
    public void handleAction(Action action, Object sender, Object target) {
    }

    private void createNavigation(final HierarchicalContainer hwContainer,final Map <String,String> navigationConstantPermissionMap){
    	
    	//TODO create navigation permissions
    	final Set <String> navigationItems = navigationConstantPermissionMap.keySet();
    	for (String item:navigationItems){
    		//if (SecurityUtils.getSubject().isPermitted(navigationConstantPermissionMap.get(item))){	
    			createNavigationItem(hwContainer,StringUtils.capitalize(item),"images/"+item.toLowerCase()+".png");
    		//}
    	}
    }
    
    private void createNavigationItem(final HierarchicalContainer hwContainer,final String navigationConstant,final String imagePath){
        final Item item = hwContainer.addItem(navigationConstant);
        item.getItemProperty("name").setValue(navigationConstant);
        root.setItemIcon(item, new ExternalResource(imagePath));
        item.getItemProperty("icon").setValue(new ExternalResource(imagePath));
    }

    public Component buildMainContentUI(final UIManagerContext uiContext,final Map<String,com.vaadin.event.MouseEvents.ClickListener> listeners,final String entityName,final Boolean buildList,final Collection <Dto> list) {
		final VerticalLayout mainAreaLayout = new VerticalLayout();
		mainAreaLayout.addComponent(buildHeader(uiContext,"Publish",null));

		if (buildList){
			mainAreaLayout.addComponent(buildListing(uiContext,list));
    	}
		
		mainAreaLayout.setSizeUndefined();
		mainAreaLayout.setWidth(100,Unit.PERCENTAGE);
		
		final GridLayout screenToolbar = buildScreenToolbar(uiContext,listeners,entityName);
		((HorizontalLayout)uiContext.getContainer()).addComponent(mainAreaLayout);
		((HorizontalLayout)uiContext.getContainer()).addComponent(screenToolbar);
		
		((HorizontalLayout)uiContext.getContainer()).setExpandRatio(mainAreaLayout, 100);
		((HorizontalLayout)uiContext.getContainer()).setExpandRatio(screenToolbar, 1);
		
		((HorizontalLayout)uiContext.getContainer()).setSizeUndefined();
		((HorizontalLayout)uiContext.getContainer()).setWidth(95,Unit.PERCENTAGE);

		return uiContext.getContainer();
    }

	private GridLayout buildScreenToolbar(final UIManagerContext uiContext,final Map<String,com.vaadin.event.MouseEvents.ClickListener> listeners,final String entityName){
		final GridLayout toolbarGridLayout = new GridLayout(1,2);

		final ScreenToolbarBuilder builder = new ScreenToolbarBuilder(toolbarGridLayout,entityName,listeners);
		builder.build();

		return toolbarGridLayout;
	}
	
	private VerticalLayout buildListing(final UIManagerContext uiContext,final Collection<Dto> dto){
		final VerticalLayout tableLayout = new VerticalLayout();
		final AbstractTableBuilder tableBuilder = new NewsletterTableBuilder(uiContext);
		
		try
		{
			tableBuilder.build((Collection)dto);
		} 
		catch(final AuthorizationException ex)
		{
			LOGGER.debug("you are not permitted to see newsletter listings", ex);
		}
		
		tableLayout.setSpacing(true);
		tableLayout.setMargin(true);
		tableLayout.addComponent(uiContext.getListingTable());
		
		return tableLayout;
	}

	private VerticalLayout buildHeader(final UIManagerContext uiContext,final String entityName, final Collection <SearchBarFieldInfo> searchBarFields){
		final ListingUIHeaderBuilder headerBuilder = new ListingUIHeaderBuilder(entityName,searchBarFields);
		return headerBuilder.build();
	}

}
