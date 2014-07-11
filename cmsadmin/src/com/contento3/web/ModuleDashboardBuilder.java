package com.contento3.web;

import com.contento3.web.content.image.ImageLoader;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.modules.EmailMarketingUI;
import com.contento3.web.modules.SitesUI;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

public class ModuleDashboardBuilder  {

	private final SpringContextHelper helper;
	
	private final VerticalSplitPanel verticalSplitPanel;

	final ImageLoader imageLoader = new ImageLoader();

	public ModuleDashboardBuilder(final SpringContextHelper helper,final VerticalSplitPanel vert){
		this.helper = helper;
		this.verticalSplitPanel = vert;
	}
	
	public VerticalLayout buildModuleDashboardUI(final HorizontalLayout headerLayout){
		
		final GridLayout grid = new GridLayout(5,1);
		grid.setSpacing(true);
		grid.setMargin(true);

		//Add module in menubar
		MenuBar moduleItems = new MenuBar();
		moduleItems.setStyleName("custommenubar");
		headerLayout.addComponent(moduleItems,1);
		headerLayout.setComponentAlignment(moduleItems,Alignment.BOTTOM_CENTER);
		
		//Module
		final HorizontalLayout websitesModule = createDashBoardItem("Websites","sites-96.png",new SitesUI(helper,verticalSplitPanel));
		grid.addComponent(websitesModule,0,0);
		grid.setComponentAlignment(websitesModule, Alignment.MIDDLE_CENTER);
		
		
		// Define a common menu command for all the menu items
		MenuBar.Command menuBarCommand = new MenuBar.Command() {

		    public void menuSelected(final MenuItem selectedItem) {
		    	if (selectedItem.getText().equals("Websites")){
		    		final SitesUI sitesUI = new SitesUI(helper,verticalSplitPanel);
		    		verticalSplitPanel.replaceComponent(verticalSplitPanel.getSecondComponent(),sitesUI.buildUI());
		    	}
		    	else if (selectedItem.getText().equals("Email Marketing")){
		    		final EmailMarketingUI email = new EmailMarketingUI(helper,verticalSplitPanel);
		    		HorizontalSplitPanel panel = email.buildUI();
		    		verticalSplitPanel.replaceComponent(verticalSplitPanel.getSecondComponent(),panel);
		    	}
		    }  
		};
		        
		MenuItem item = moduleItems.addItem("Modules",new ExternalResource("images/arrow.png"), null);
		moduleItems.setMoreMenuItem(null);
		item.addItem("Websites", menuBarCommand);

		final HorizontalLayout emailMarketingModule = createDashBoardItem("Email marketing","email-marketing-icon.png",new EmailMarketingUI(helper,verticalSplitPanel));
		grid.addComponent(emailMarketingModule,1,0);
		grid.setComponentAlignment(emailMarketingModule, Alignment.MIDDLE_CENTER);

		item.addItem("Email Marketing", menuBarCommand);

//		HorizontalLayout socialMediaModule = createDashBoardItem("Social Media","social-media-icon.png",new SocialMediaManagementUI());
//		grid.addComponent(socialMediaModule,2,0);
//		grid.setComponentAlignment(socialMediaModule, Alignment.MIDDLE_CENTER);
//
//		HorizontalLayout siteUsersModule = createDashBoardItem("Site users","social-media-icon.png",new SocialMediaManagementUI());
//		grid.addComponent(siteUsersModule);
//		grid.setComponentAlignment(siteUsersModule, Alignment.MIDDLE_CENTER);

		grid.setSizeFull();
		
		final VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.addComponent(grid);
		verticalLayout.setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
		verticalLayout.setSizeUndefined();
		return verticalLayout;
	}

	/**
	 * Builds the dashboard item for the module dashboard screen
	 * @param label
	 * @param itemImage
	 * @param listener
	 * @return
	 */
	private HorizontalLayout createDashBoardItem(final String label,final String itemImage,final ClickListener listener){
		
		final Embedded moduleImage = imageLoader.loadEmbeddedImageByPath("images/"+itemImage);
		moduleImage.setWidth(100,Unit.PIXELS);
	
		// Create the content
		final VerticalLayout content = new VerticalLayout();
		final Label item = new Label(label,ContentMode.HTML);
		item.setSizeUndefined();
	
		Button labelBtn = new Button (label);
		labelBtn.setStyleName("link");
		labelBtn.addClickListener(listener);
		VerticalLayout labelLayout = new VerticalLayout();
		labelLayout.setSizeUndefined();
		labelLayout.addComponent(labelBtn);
		labelLayout.setComponentAlignment(labelBtn, Alignment.MIDDLE_CENTER);
		labelLayout.setWidth(100,Unit.PIXELS);
		labelLayout.setHeight(20,Unit.PIXELS);
	
		content.addComponent(moduleImage);
	
		content.addComponent(labelLayout);
	
		content.setSizeUndefined();
	
		final VerticalLayout itemLayout = new VerticalLayout();
		itemLayout.addComponent(content);
		itemLayout.setComponentAlignment(content, Alignment.MIDDLE_CENTER);

		final HorizontalLayout dashboardItem = new HorizontalLayout();
		final Panel panel = new Panel();
		panel.setSizeUndefined(); // Shrink to fit content
		dashboardItem.addComponent(panel);
		panel.setContent(itemLayout);
		panel.setWidth(150, Unit.PIXELS);
		return dashboardItem;		
	}


}
