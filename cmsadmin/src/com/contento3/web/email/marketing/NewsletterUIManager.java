package com.contento3.web.email.marketing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.AuthorizationException;

import com.c3.email.marketing.newsletter.dto.NewsletterDto;
import com.c3.email.marketing.newsletter.service.NewsletterService;
import com.contento3.web.UIManagerContext;
import com.contento3.web.UIManagerImpl;
import com.contento3.web.common.ListingUIHeaderBuilder;
import com.contento3.web.common.SearchBarFieldInfo;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.ScreenToolbarBuilder;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class NewsletterUIManager extends UIManagerImpl {
	
	private static final Logger LOGGER = Logger.getLogger(NewsletterUIManager.class);

	private NewsletterService newsletterService;
	
	public NewsletterUIManager(final SpringContextHelper helper){
		super.setUiContext(new UIManagerContext());
		this.getUiContext().setHelper(helper);
		this.newsletterService = (NewsletterService)helper.getBean("newsletterService");
		super.getUiContext().setContainer(new HorizontalLayout());
	}
	
	private VerticalLayout buildListing(){
		final VerticalLayout tableLayout = new VerticalLayout();
		final AbstractTableBuilder tableBuilder = new NewsletterTableBuilder(this.getUiContext());
		
		try
		{
			final Collection<NewsletterDto> newsletters = newsletterService.findByAccountId((Integer)SessionHelper.loadAttribute("accountId"));
			tableBuilder.build((Collection)newsletters);
		} 
		catch(final AuthorizationException ex)
		{
			LOGGER.debug("you are not permitted to see newsletter listings", ex);
		}
		
		tableLayout.setSpacing(true);
		tableLayout.setMargin(true);
		tableLayout.addComponent(this.getUiContext().getListingTable());
		
		return tableLayout;
	}

	private VerticalLayout buildHeader(){
		final SearchBarFieldInfo fieldOne = new SearchBarFieldInfo("Name","Enter newsletter name");
		final SearchBarFieldInfo fieldTwo = new SearchBarFieldInfo("Status","Newsletter status");

		final Collection <SearchBarFieldInfo> fields = new ArrayList<SearchBarFieldInfo>();
		fields.add(fieldOne);
		fields.add(fieldTwo);
		
		final ListingUIHeaderBuilder headerBuilder = new ListingUIHeaderBuilder("Newsletters",fields);
		return headerBuilder.build();
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
		
		final GridLayout screenToolbar = buildScreenToolbar();
		((HorizontalLayout)this.getUiContext().getContainer()).addComponent(mainAreaLayout);
		((HorizontalLayout)this.getUiContext().getContainer()).addComponent(screenToolbar);
		
		((HorizontalLayout)this.getUiContext().getContainer()).setExpandRatio(mainAreaLayout, 100);
		((HorizontalLayout)this.getUiContext().getContainer()).setExpandRatio(screenToolbar, 1);
		
		((HorizontalLayout)this.getUiContext().getContainer()).setSizeUndefined();
		((HorizontalLayout)this.getUiContext().getContainer()).setWidth(95,Unit.PERCENTAGE);

		return this.getUiContext().getContainer();
	}

	private GridLayout buildScreenToolbar(){
		final GridLayout toolbarGridLayout = new GridLayout(1,2);

		final List<com.vaadin.event.MouseEvents.ClickListener> listeners = new ArrayList<com.vaadin.event.MouseEvents.ClickListener>();
		listeners.add(new NewsletterAddEditListener(this.getUiContext()));

		final ScreenToolbarBuilder builder = new ScreenToolbarBuilder(toolbarGridLayout,"newsletter",listeners);
		builder.build();

		return toolbarGridLayout;
	}
	
	@Override
	public Component render(final String command, final Integer entityFilterId) {
		return null;
	}

	@Override
	public Component render(String command,final HierarchicalContainer treeItemContainer) {
		return null;
	}

}
