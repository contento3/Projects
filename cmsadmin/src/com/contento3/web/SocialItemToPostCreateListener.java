package com.contento3.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.contento3.social.common.SocialApiEnum;
import com.contento3.social.common.model.SocialEntity;
import com.contento3.social.common.model.SocialGroup;
import com.contento3.social.common.model.SocialPage;
import com.contento3.web.content.image.ImageLoader;
import com.vaadin.data.Property;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

public class SocialItemToPostCreateListener implements ClickListener {

	private static final long serialVersionUID = 1L;

	/**
	 * Layout that contains all the item to which the content require to be posted
	 */
	final HorizontalLayout socialItemContainer;
	
	String itemId;
	
	final String itemType;
	
	String itemName;
	
	final ComboBox itemContainerCombo;
	
	final String accessToken;
	
	final SocialApiEnum socialApiName;
	
	final Map <SocialApiEnum,Collection<SocialEntity>> whereToPostCollection;
	
	public SocialItemToPostCreateListener(final String accessToken,final HorizontalLayout socialItemContainer,final String type,
			final ComboBox itemContainerCombo,final SocialApiEnum socialApiEnum, final Map <SocialApiEnum,Collection<SocialEntity>> whereToPostCollection){
		this.socialItemContainer = socialItemContainer;
		this.itemId = (String)itemContainerCombo.getValue();
		this.itemName = itemContainerCombo.getItemCaption(itemId);
		this.itemType = type;
		this.itemContainerCombo = itemContainerCombo;
		this.accessToken = accessToken;
		this.socialApiName = socialApiEnum;
		this.whereToPostCollection = whereToPostCollection;
	}
	
	@Override
	public void buttonClick(final ClickEvent event) {
		final Panel socialItem = new Panel();
		socialItem.setHeight(20,Unit.PERCENTAGE);
		socialItem.setData(itemType+":"+itemContainerCombo.getValue());

		this.itemId = (String)itemContainerCombo.getValue();
		this.itemName = itemContainerCombo.getItemCaption(itemId);

		Property property = itemContainerCombo.getContainerProperty(itemId, "description");
		
		final HorizontalLayout itemLayout = new HorizontalLayout();
		final Label itemLabel = new Label("<b>"+itemType+": "+property.getValue()+"</b>",ContentMode.HTML);
		final ImageLoader imageLoader = new ImageLoader();
		
		if (SocialApiEnum.facebook.equals(socialApiName)){
			itemLayout.addComponent(imageLoader.loadEmbeddedImageByPath("images/facebook16.png"));
		}
		else if (SocialApiEnum.linkedin.equals(socialApiName)){
			itemLayout.addComponent(imageLoader.loadEmbeddedImageByPath("images/linkedin16.png"));
		}
		else if (SocialApiEnum.twitter.equals(socialApiName)){
			itemLayout.addComponent(imageLoader.loadEmbeddedImageByPath("images/twitter16.png"));
		}
	
		itemLabel.setContentMode(ContentMode.HTML);
		itemLabel.setWidth(200, Unit.PERCENTAGE);
		
		itemLayout.addComponent(itemLabel);
		
		final Button button = new Button("X");
		button.setStyleName("link");
		itemLayout.addComponent(button);
		itemLayout.setComponentAlignment(itemLabel,Alignment.BOTTOM_RIGHT);
		itemLayout.setComponentAlignment(button,Alignment.BOTTOM_RIGHT);
		
		socialItem.setContent(itemLayout);
		itemLayout.setSpacing(true);
		socialItemContainer.setHeight(20,Unit.PERCENTAGE);
		socialItemContainer.setSpacing(true);
		socialItemContainer.setMargin(true);
		socialItemContainer.addComponent(socialItem);
		
		SocialEntity entity = null;
		if (itemType.equals("Page"))
			entity = new SocialPage(property.getValue().toString(),itemId,this.accessToken);
		else if (itemType.equals("Group"))
			entity = new SocialGroup(property.getValue().toString(),itemId,this.accessToken);
		
		if (whereToPostCollection.get(this.socialApiName)==null){
			whereToPostCollection.put(socialApiName, new ArrayList<SocialEntity>());
		}
		
		whereToPostCollection.get(socialApiName).add(entity);
		
	}

}
