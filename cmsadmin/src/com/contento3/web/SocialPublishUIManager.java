package com.contento3.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.vaadin.addon.oauthpopup.OAuthListener;
import org.vaadin.addon.oauthpopup.OAuthPopupButton;
import org.vaadin.addon.oauthpopup.buttons.FacebookButton;

import com.contento3.common.dto.Dto;
import com.contento3.social.common.SocialApiEnum;
import com.contento3.social.common.model.SocialEntity;
import com.contento3.social.common.model.SocialGroup;
import com.contento3.social.common.model.SocialPage;
import com.contento3.social.facebook.FacebookApi;
import com.contento3.web.common.helper.ComboDataLoader;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.modules.UIManagerBuilder;
import com.vaadin.annotations.Push;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@Push(PushMode.AUTOMATIC)
public class SocialPublishUIManager extends UIManagerImpl {

	String accToken;

	private static final Logger LOGGER = Logger.getLogger(SocialPublishUIManager.class);

	VerticalLayout socialContainerLayout;

    OAuthPopupButton socialButton = null;

    HorizontalLayout socialItemContainer = null;
    
    final Map <SocialApiEnum,Collection<SocialEntity>> whereToPostCollection = new HashMap <SocialApiEnum,Collection<SocialEntity>>();
    
	public SocialPublishUIManager(final SpringContextHelper helper) {
		super.setUiContext(new UIManagerContext());
		this.getUiContext().setHelper(helper);
		super.getUiContext().setContainer(new HorizontalLayout());
		this.accToken = "";
	}

	@Override
	public Component render(final String command){
		final UIManagerBuilder uiMB = new UIManagerBuilder();
		uiMB.buildMainContentUI(this.getUiContext(),null,"socialpublish",false,null);
		
		//What we are sharing? 
		final OptionGroup whatRadio = new OptionGroup("What do you want to publish?");
		whatRadio.addItem("Article");
		whatRadio.addItem("Image");
		//whatRadio.addItem("Product");
		whatRadio.addItem("External Link");
		whatRadio.addItem("Status");
		whatRadio.setImmediate(true);

		final HorizontalLayout whatSelectionLayout = new HorizontalLayout();
		whatSelectionLayout.addComponent(whatRadio);
		whatSelectionLayout.setSpacing(true);
		whatSelectionLayout.setMargin(true);

		final ContentToSendSelectionListener contentToSendListener = new ContentToSendSelectionListener(this.getUiContext().getHelper(),whatSelectionLayout);
		whatRadio.addValueChangeListener(contentToSendListener);

		//We done have to show the listing so we 
		//can add the components for publishing
		final ComboDataLoader comboLoader = new ComboDataLoader();
		final Collection <Dto> socialDtoList = new ArrayList<Dto>();
		final Dto dto = new Dto(1,"Facebook Connect");
		socialDtoList.add(dto);
		
		final IndexedContainer container = comboLoader.loadDataInContainer(socialDtoList);
		final ComboBox socialNetworkCombo = new ComboBox("",container);
		final HorizontalLayout publishingOptionsLayout = new HorizontalLayout();
		publishingOptionsLayout.addComponent(socialNetworkCombo);


		final VerticalLayout socialNetworkPanelContainer = new VerticalLayout();

		final Button socialNetworkAddButton = new Button("Add social network to post");
		publishingOptionsLayout.addComponent(socialNetworkAddButton);
		publishingOptionsLayout.setComponentAlignment(socialNetworkAddButton,Alignment.BOTTOM_LEFT);

		socialNetworkAddButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				socialNetworkPanelContainer.addComponent(createSocialPanel(socialNetworkCombo.getValue().toString()));
			}
		});

		publishingOptionsLayout.setSpacing(true);
		publishingOptionsLayout.setMargin(true);

		socialNetworkCombo.setItemCaptionMode(ComboBox.ItemCaptionMode.PROPERTY);
		socialNetworkCombo.setItemCaptionPropertyId("name");

		final Iterator component = this.getUiContext().getContainer().iterator();
		final VerticalLayout mainComponent = (VerticalLayout) component.next();
		mainComponent.setSpacing(true);

		mainComponent.addComponent(whatSelectionLayout);
		mainComponent.addComponent(publishingOptionsLayout);

		mainComponent.addComponent(socialNetworkPanelContainer);
		
		final Button postButton = new Button("Post");
		
		postButton.addClickListener(new SocialPostListener(whatRadio,contentToSendListener,whereToPostCollection));
		mainComponent.addComponent(postButton);
		mainComponent.setComponentAlignment(postButton,Alignment.BOTTOM_RIGHT);
        return this.getUiContext().getContainer();
	}


	private VerticalLayout createSocialPanel(final String socialEnum){
		
		if (null==socialContainerLayout){
			socialContainerLayout = new VerticalLayout();
		}
		
		final Panel panel = new Panel();
		panel.setWidth(100,Unit.PERCENTAGE);
		panel.setHeight(80,Unit.PIXELS);
			
		final HorizontalLayout addedLayoutRow = new HorizontalLayout();
	
			//Facebook
			if (socialEnum.equals("1")){
				//TODO get key and secret key from properties file
				socialButton = new FacebookButton("581065758620922", "f10f99b74466ac5a017bc845b12dd878");
				socialButton.addOAuthListener(new OAuthListener() {
				  @Override
				  public void authSuccessful(final String accessToken,final String accessTokenSecret) {
					    Notification.show("Authorization","You are now connected to Facebook.",Notification.Type.TRAY_NOTIFICATION);
					    socialButton.setReadOnly(true);
						socialButton.setCaption("Facebook connected");
						
						accToken = accessToken;

						addSocialCombo("Facebook pages",addedLayoutRow,accessToken,"Page",SocialApiEnum.facebook);
						addSocialCombo("Facebook groups",addedLayoutRow,accessToken,"Group",SocialApiEnum.facebook);
				  }
	
				  @Override
				  public void authDenied(String reason) {
				    Notification.show("Authorization denied");
				  }
			});
		}
		
		addedLayoutRow.addComponent(socialButton);
		addedLayoutRow.setComponentAlignment(socialButton,Alignment.BOTTOM_LEFT);
		addedLayoutRow.setSpacing(true); 
		addedLayoutRow.setMargin(true);
		
		panel.setContent(addedLayoutRow);
		socialContainerLayout.addComponent(panel);
		socialContainerLayout.setWidth(100,Unit.PERCENTAGE);
		return socialContainerLayout;
	}

	private void addSocialCombo(final String comboTitle,final HorizontalLayout addedLayoutRow,final String accessToken,final String type,final SocialApiEnum socialSiteName){
		IndexedContainer container =null;
		
		if (type.equals("Page"))
			container = loadSocialPageInContainer(FacebookApi.fetchPages(accessToken));
		else if (type.equals("Group"))
			container = loadSocialGroupInContainer(FacebookApi.fetchGroups(accessToken));
			
		final ComboBox socialCombo = new ComboBox(comboTitle,container);
		socialCombo.setItemCaptionMode(ComboBox.ItemCaptionMode.PROPERTY);
		socialCombo.setItemCaptionPropertyId("name");
		socialCombo.setWidth(50, Unit.PERCENTAGE);

		addedLayoutRow.addComponent(socialCombo);
		addedLayoutRow.setComponentAlignment(socialCombo,Alignment.TOP_CENTER);
		
		final Button addButton = new Button("Add");
		
		if (null==socialItemContainer){
			socialItemContainer = new HorizontalLayout();
		}
		socialContainerLayout.addComponent(socialItemContainer);
		addButton.addClickListener(new SocialItemToPostCreateListener(accessToken,socialItemContainer, type,socialCombo,socialSiteName,whereToPostCollection));
		addedLayoutRow.addComponent(addButton);
		addedLayoutRow.setComponentAlignment(addButton,Alignment.BOTTOM_LEFT);
	}

	private IndexedContainer loadSocialPageInContainer(final Collection<SocialPage> dtoList) {
		final IndexedContainer container = new IndexedContainer();
		container.addContainerProperty("name", String.class, null);
		container.addContainerProperty("value", Integer.class, null);
		container.addContainerProperty("description", String.class, null);

		if (!CollectionUtils.isEmpty(dtoList)){
			final Iterator<SocialPage> dtoIterator = dtoList.iterator();
	
			while (dtoIterator.hasNext()) {
				final SocialPage dto = dtoIterator.next();
				final Item pageLayoutItem = container.addItem(dto.getId());
				pageLayoutItem.getItemProperty("name").setValue(dto.getShortName());
				pageLayoutItem.getItemProperty("description").setValue(dto.getName());
			}
			
			container.sort(new Object[] { "name" }, new boolean[] { true });
		}
		return container;
	}

	private IndexedContainer loadSocialGroupInContainer(final Collection<SocialGroup> dtoList) {
		final IndexedContainer container = new IndexedContainer();
		container.addContainerProperty("name", String.class, null);
		container.addContainerProperty("value", Integer.class, null);
		container.addContainerProperty("description", String.class, null);

		if (!CollectionUtils.isEmpty(dtoList)){
			final Iterator<SocialGroup> dtoIterator = dtoList.iterator();
	
			while (dtoIterator.hasNext()) {
				final SocialGroup dto = dtoIterator.next();
				final Item pageLayoutItem = container.addItem(dto.getId());
				pageLayoutItem.getItemProperty("name").setValue(dto.getShortName());
				pageLayoutItem.getItemProperty("description").setValue(dto.getName());
			}
			
			container.sort(new Object[] { "name" }, new boolean[] { true });
		}
		return container;
	}

}
