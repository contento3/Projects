package com.contento3.web;

import java.util.Collection;
import java.util.Map;

import com.contento3.social.common.SocialApiEnum;
import com.contento3.social.common.SocialPublishApi;
import com.contento3.social.common.model.LinkedSocialContentToPost;
import com.contento3.social.common.model.SocialContentToPost;
import com.contento3.social.common.model.SocialEntity;
import com.contento3.social.common.model.SocialImageContentToPost;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.OptionGroup;

public class SocialPostListener implements ClickListener {

	private static final long serialVersionUID = 1L;

	final ContentToSendSelectionListener contentToSendListener;
	
	final Map <SocialApiEnum,Collection<SocialEntity>> whereToPostCollection;
	
	final SocialPublishApi api;
	
	final OptionGroup whatToPost;
	
	public SocialPostListener (final OptionGroup whatToPost,final ContentToSendSelectionListener contentToSendListener,final Map <SocialApiEnum,Collection<SocialEntity>> whereToPostCollection) {
		this.contentToSendListener = contentToSendListener;
		this.whereToPostCollection = whereToPostCollection;
		this.api = new SocialPublishApi();
		this.whatToPost = whatToPost;
	}
	
	@Override
	public void buttonClick(final ClickEvent event) {
		SocialContentToPost contentToPost = null;
		final String contentType = whatToPost.getValue().toString();
		
		if (contentType.equals("Status")){
			contentToPost = new SocialContentToPost(contentToSendListener.getStatusMessage());
		}
		else if (contentType.equals("Article")){
			contentToPost = new SocialContentToPost(contentToSendListener.getStatusMessage());
		}
		else if (contentType.equals("External Link")){
			contentToPost = new LinkedSocialContentToPost(contentToSendListener.getStatusMessage(),contentToSendListener.getUrlLink(),contentToSendListener.getName(),contentToSendListener.getCaption(),contentToSendListener.getDescription());
		}
		else if (contentType.equals("Image")){
			contentToPost = new SocialImageContentToPost(contentToSendListener.getStatusMessage(),contentToSendListener.getUrlLink(),contentToSendListener.getName(),contentToSendListener.getCaption(),contentToSendListener.getDescription(),contentToSendListener.getImageUrl(),contentToSendListener.getActionUrl());
		}
		else if (contentType.equals("Product")){
			contentToPost = new SocialContentToPost(contentToSendListener.getStatusMessage());
		}

		api.publish(contentToPost, whereToPostCollection);
	}

}
