package com.contento3.web;

import com.contento3.web.common.ListingUIHeaderBuilder;
import com.contento3.web.content.image.ImageLoader;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.pagemodules.PageModuleDesignListener;
import com.contento3.web.pagemodules.PageModuleLayoutsEnum;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class PageModulesUIManager extends UIManagerImpl {

	public PageModulesUIManager(final SpringContextHelper helper) {
		super.setUiContext(new UIManagerContext());
		this.getUiContext().setHelper(helper);
		super.getUiContext().setContainer(new HorizontalLayout());
	}

	@Override
	public void render() {
		
	}

	@Override
	public Component render(final String command) {
		final VerticalLayout mainAreaLayout = new VerticalLayout();
		mainAreaLayout.addComponent(buildHeader());
		mainAreaLayout.addComponent(buildListing());
		mainAreaLayout.setSizeUndefined();
		mainAreaLayout.setWidth(100,Unit.PERCENTAGE);
		
		((HorizontalLayout)this.getUiContext().getContainer()).addComponent(mainAreaLayout);
		((HorizontalLayout)this.getUiContext().getContainer()).setExpandRatio(mainAreaLayout, 100);
		((HorizontalLayout)this.getUiContext().getContainer()).setSizeUndefined();
		((HorizontalLayout)this.getUiContext().getContainer()).setWidth(100,Unit.PERCENTAGE);

		return this.getUiContext().getContainer();
	}

	@Override
	public Component render(String command, Integer entityFilterId) {
		return null;
	}

	@Override
	public Component render(String command,
			HierarchicalContainer treeItemContainer) {
		return null;
	}

	private VerticalLayout buildListing(){
		final VerticalLayout mainContentLayout = new VerticalLayout();
		final HorizontalLayout pageLayouts = new HorizontalLayout();
		
		for (PageModuleLayoutsEnum layout : PageModuleLayoutsEnum.values()){
			final Panel panel = new Panel();
			panel.setWidth(250,Unit.PIXELS);
			panel.setHeight(225,Unit.PIXELS);
			panel.addClickListener(new PageModuleDesignListener(mainContentLayout,this.getUiContext(),layout));
		    panel.setContent(createItem (layout.toString()));
			
			pageLayouts.addComponent(panel);
			pageLayouts.setSpacing(true);
			pageLayouts.setMargin(true);
			
			mainContentLayout.setSpacing(true);
			mainContentLayout.addComponent(pageLayouts);
		}
		return mainContentLayout;
	}

	private VerticalLayout createItem (final String imagename) {
	    final ImageLoader imageLoader = new ImageLoader();
	    final Embedded embedded = imageLoader.loadEmbeddedImageByPath("images/template_library/content/"+imagename+".png");

		final VerticalLayout itemLayout = new VerticalLayout();
	    itemLayout.addComponent(embedded);
		itemLayout.setComponentAlignment(embedded, Alignment.BOTTOM_CENTER);
		return itemLayout;
	}
	
	private VerticalLayout buildHeader(){
		final ListingUIHeaderBuilder headerBuilder = new ListingUIHeaderBuilder("Page Modules",null);
		final VerticalLayout headerLayout = headerBuilder.build();
		headerLayout.addComponent(new Label("Page Modules are here for you to build dynamic pages quickly for your website.Use already designed page or start from scratch to build your pages with speed."));
		return headerLayout;
	}
}
