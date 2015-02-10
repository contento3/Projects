package com.contento3.web.email.marketing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.AuthorizationException;

import com.c3.email.marketing.subscription.dto.SubscriptionDto;
import com.c3.email.marketing.subscription.service.SubscriptionService;
import com.contento3.common.exception.EntityNotFoundException;
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

public class SubscriptionUIManager extends UIManagerImpl {

	private static final Logger LOGGER = Logger.getLogger(SubscriptionUIManager.class);

	private SubscriptionService subscriptionService;
	
	public SubscriptionUIManager (final SpringContextHelper helper){
		super.setUiContext(new UIManagerContext());
		this.getUiContext().setHelper(helper);
		this.getUiContext().setContainer(new HorizontalLayout());
		subscriptionService = (SubscriptionService)helper.getBean("subscriptionService");
	}
	
	@Override
	public void render() {
	}

	@Override
	public Component render(final String command) {
		final VerticalLayout mainAreaLayout = new VerticalLayout();
		mainAreaLayout.addComponent(buildHeader());
		mainAreaLayout.addComponent(buildListing());
		
		getUiContext().getContainer().addComponent(mainAreaLayout);

		final GridLayout screenToolbar = buildScreenToolbar();
		((HorizontalLayout)this.getUiContext().getContainer()).addComponent(mainAreaLayout);
		((HorizontalLayout)this.getUiContext().getContainer()).addComponent(screenToolbar);
		
		((HorizontalLayout)this.getUiContext().getContainer()).setExpandRatio(mainAreaLayout, 100);
		((HorizontalLayout)this.getUiContext().getContainer()).setExpandRatio(screenToolbar, 1);
		
		((HorizontalLayout)this.getUiContext().getContainer()).setSizeUndefined();
		((HorizontalLayout)this.getUiContext().getContainer()).setWidth(95,Unit.PERCENTAGE);

		return getUiContext().getContainer();
	}

	@Override
	public Component render(final String command, final Integer entityFilterId) {
		return null;
	}

	@Override
	public Component render(final String command,final HierarchicalContainer treeItemContainer) {
		return null;
	}

	private VerticalLayout buildListing(){
		final VerticalLayout tableLayout = new VerticalLayout();
		final AbstractTableBuilder tableBuilder = new SubscriptionTableBuilder(getUiContext());
		
		try
		{
			final Collection<SubscriptionDto> subscriptions = subscriptionService.findByAccountId((Integer)SessionHelper.loadAttribute("accountId"));
			tableBuilder.build((Collection)subscriptions);
		} 
		catch(final AuthorizationException ex)
		{
			LOGGER.debug("you are not permitted to see newsletter listings", ex);
		}
		catch(final EntityNotFoundException ex)
		{
			LOGGER.debug("you are not permitted to see newsletter listings", ex);
		}
		
		tableLayout.setSpacing(true);
		tableLayout.setMargin(true);
		tableLayout.addComponent(getUiContext().getListingTable());
		
		return tableLayout;
	}


	private VerticalLayout buildHeader() {
		final SearchBarFieldInfo fieldOne = new SearchBarFieldInfo("Name","Enter subscription name");

		final Collection <SearchBarFieldInfo> fields = new ArrayList<SearchBarFieldInfo>();
		fields.add(fieldOne);
		
		final ListingUIHeaderBuilder headerBuilder = new ListingUIHeaderBuilder("Subscriptions",fields);
		return headerBuilder.build();
	}

	private GridLayout buildScreenToolbar(){
		final GridLayout toolbarGridLayout = new GridLayout(1,2);

		final Map<String,com.vaadin.event.MouseEvents.ClickListener> listeners = new HashMap<String,com.vaadin.event.MouseEvents.ClickListener>();
		listeners.put("SUBSCRIPTION:ADD",new SubscriptionAddEditListener(this.getUiContext()));

		final ScreenToolbarBuilder builder = new ScreenToolbarBuilder(toolbarGridLayout,"subscription",listeners);
		builder.build();

		return toolbarGridLayout;
	}

}
