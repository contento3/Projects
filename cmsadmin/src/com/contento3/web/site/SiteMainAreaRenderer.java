package com.contento3.web.site;

import java.util.Collection;

import com.contento3.cms.page.layout.model.PageLayoutType;
import com.contento3.cms.page.layout.service.PageLayoutTypeService;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;
//TODO needs to be part of SiteUIManager
/**
 * This class is used to render the site related main area 
 * when you click on the site in the left navigation.
 * @author HAMMAD
 *
 */
public class SiteMainAreaRenderer implements Button.ClickListener {
    
	private static final long serialVersionUID = 1L;

	public SiteMainAreaRenderer(final SpringContextHelper helper){
		this.helper = helper;	
	}
	
    private SpringContextHelper helper;

	TabSheet siteTab;

	/**
	 * 
	 * @return
	 */
	public Component renderSiteMainArea(){
		
		if (null==siteTab){ 
    	siteTab = new TabSheet();
    	siteTab.setHeight("675");
    	siteTab.setWidth("775");

    	Label siteConfig = new Label("Site Configuration");
    	Label sitePages = new Label("Pages");
    	Label contentQueues = new Label("Content Queues");
    	
    	VerticalLayout pageLayout = new VerticalLayout();
    	
    	Tab tab1 = siteTab.addTab(siteConfig,"Site Configuration",null);
    	Tab tab2 = siteTab.addTab(pageLayout,"Site Pages",null);
    	Tab tab3 = siteTab.addTab(contentQueues,"Content Queues",null);
    	tab2.setClosable(true);
    	tab3.setClosable(true);
    	
    	Button createPageButton = new Button("Create new page");
    	createPageButton.addClickListener(this); // react to clicks

    	pageLayout.addComponent(sitePages);
    	pageLayout.addComponent(createPageButton);
		}
	
		return siteTab;
	}
	
	/**
	 * 
	 */
    public void buttonClick(ClickEvent event) {
    	if (siteTab.getComponentCount()<8)
    	{	
    		if (event.getComponent().getCaption().equals("Create new page")){

    	    	VerticalLayout pageLayout = new VerticalLayout();
    			Tab newPageTab = siteTab.addTab(pageLayout,"Create new Page",null);
    			newPageTab.setClosable(true);  
    			siteTab.setSelectedTab(newPageTab.getComponent());  
    			
    	        ComboBox l = new ComboBox("Please select page layout type",
    	        		getPageLayoutTypes());

    	       
    	        // Sets the combobox to show a certain property as the item caption
    	        l.setItemCaptionPropertyId("name");
    	        l.setItemCaptionMode(ComboBox.ItemCaptionMode.PROPERTY);

    	        // Set a reasonable width
    	        l.setSizeUndefined();

    	        // Set the appropriate filtering mode for this example
    	        l.setFilteringMode(FilteringMode.CONTAINS);
    	        l.setImmediate(true);
    	        //l.addListener(this);

    	        // Disallow null selections
    	        l.setNullSelectionAllowed(false);
    	    	pageLayout.addComponent(l);
    	        
    		}
    	}
     }

    public IndexedContainer getPageLayoutTypes() {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("name", String.class,null);
        container.addContainerProperty("value", String.class,null);

        PageLayoutTypeService layoutTypeService  = (PageLayoutTypeService) helper.getBean("pageLayoutTypeService");
        Collection<PageLayoutType> layoutTypeList = layoutTypeService.findAllPageLayoutType();
        
        for (PageLayoutType layoutType : layoutTypeList){
        	Item item = container.addItem(layoutType.getName());
        	item.getItemProperty("name").setValue(layoutType.getName());
        	item.getItemProperty("value").setValue(layoutType.getId());
        }
        container.sort(new Object[] { "name" },
                new boolean[] { true });
        return container;
    }


}
