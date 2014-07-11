package com.contento3.web.email.marketing;

import java.util.ArrayList;
import java.util.List;

import com.c3.email.marketing.subscription.dto.SubscriptionDto;
import com.c3.email.marketing.subscription.service.SubscriptionService;
import com.contento3.web.UIManagerContext;
import com.contento3.web.common.helper.ScreenToolbarBuilder;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class SubscriptionAddEditListener implements ClickListener,com.vaadin.event.MouseEvents.ClickListener {

		private static final long serialVersionUID = 1L;

		private final UIManagerContext context;
		
		private final SubscriptionService subscriptionService;
		
		GridLayout screenToolbar = null;
		
		GridLayout screenToolbarForListing;
		
		final private HorizontalLayout containerLayout;
		
		private SubscriptionForm subscriptionForm = new SubscriptionForm();
		
		public SubscriptionAddEditListener(final UIManagerContext context){
			this.context = context;
			containerLayout = (HorizontalLayout)context.getContainer();
			subscriptionService = (SubscriptionService)context.getHelper().getBean("subscriptionService");
			context.setForm(subscriptionForm);
		}
		
		
		@Override
		public void buttonClick(final ClickEvent event) {
			final Integer id = (Integer)event.getButton().getData();
			buildUI(id);
		}

		private void buildUI(final Integer subscriptionId){
			final VerticalLayout mainAreaLayout = ((VerticalLayout)((HorizontalLayout)context.getContainer()).getComponent(0));
			Integer numberOfCompnents = mainAreaLayout.getComponentCount();
			for (int i=0;i<numberOfCompnents;i++){
				mainAreaLayout.getComponent(i).setVisible(false);
			}		
			
			mainAreaLayout.addComponent(buildHeader());
			mainAreaLayout.addComponent(buildSubscriptionInfoUI(subscriptionId));
			
			if (null!=subscriptionId){
				mainAreaLayout.addComponent(buildUserAddUI(subscriptionId));
			}
			
			mainAreaLayout.addComponent(buildBackButtonUI());
			
			mainAreaLayout.setSpacing(true);
			mainAreaLayout.setMargin(true);
			
			screenToolbar = buildScreenToolbar();
			screenToolbarForListing = ((GridLayout)((HorizontalLayout)context.getContainer()).getComponent(1));
			screenToolbarForListing.setVisible(false);
			
			(((HorizontalLayout)context.getContainer())).addComponent(screenToolbar);
			(((HorizontalLayout)context.getContainer())).setExpandRatio(screenToolbar, 1);
			(((HorizontalLayout)context.getContainer())).setExpandRatio(mainAreaLayout, 100);

			//mainAreaLayout.setSizeUndefined();
			//mainAreaLayout.setWidth(95,Unit.PERCENTAGE);
		}
		
		private VerticalLayout buildHeader(){
			final VerticalLayout searchLayout = new VerticalLayout();
			final Label articleHeading = new Label("Subscriptions");
			articleHeading.setStyleName("screenHeading");
			articleHeading.setSizeUndefined();
			searchLayout.addComponent(articleHeading);
			searchLayout.setSizeUndefined();
			searchLayout.setWidth(100, Unit.PERCENTAGE);
			return searchLayout;
		}
		
		private Panel buildSubscriptionInfoUI(final Integer subscriptionId){
			SubscriptionDto subscription=null;
			String subscriptionName = null;
			String subscriptionDescription = null;
			if (null!=subscriptionId){
				subscription = subscriptionService.findById(subscriptionId);
				subscriptionName = subscription.getName();
				subscriptionDescription = subscription.getDescription();
				context.setIdToEdit(subscriptionId);
			}
			
			final VerticalLayout infoUI = new VerticalLayout();
			infoUI.setSpacing(true);
			infoUI.setMargin(true);
			
			final Panel panel = new Panel();
			panel.setWidth(100,Unit.PERCENTAGE);
			panel.setHeight(30,Unit.PERCENTAGE);
			
			FormLayout formLayout = buildTextField("Subscription name","Enter subscription name",false,subscriptionName);
			subscriptionForm.setSubscriptionName((TextField)formLayout.getComponent(0));
			infoUI.addComponent(formLayout);
			infoUI.setComponentAlignment(formLayout,Alignment.BOTTOM_RIGHT);

			FormLayout descriptionTextAreaForm = buildTextArea("Description","Enter description",subscriptionDescription);
			subscriptionForm.setSubscriptionDescription((TextArea)descriptionTextAreaForm.getComponent(0));
			infoUI.addComponent(descriptionTextAreaForm);
			
			final HorizontalLayout templateSelectionLayout = new HorizontalLayout();
			
			infoUI.addComponent(templateSelectionLayout);
				
			panel.setContent(infoUI);
			context.setForm(subscriptionForm);

			return panel;
		}

		private Panel buildUserAddUI(final Integer subscriptionId){

			SubscriptionDto subscription=null;
			String subscriptionName = null;
			String subscriptionDescription = null;
			if (null!=subscriptionId){
				subscription = subscriptionService.findById(subscriptionId);
				subscriptionName = subscription.getName();
				subscriptionDescription = subscription.getDescription();
				context.setIdToEdit(subscriptionId);
			}

			final VerticalLayout infoUI = new VerticalLayout();
			infoUI.setSpacing(true);
			infoUI.setMargin(true);
			
			final Panel panel = new Panel();
			panel.setWidth(100,Unit.PERCENTAGE);
			panel.setHeight(40,Unit.PERCENTAGE);
			
			final HorizontalLayout addUserLayout = new HorizontalLayout();
			final Label addUserLabel = new Label("<h3>Add subscriber</h3>",ContentMode.HTML);
			addUserLayout.addComponent(addUserLabel);
			infoUI.addComponent(addUserLayout);
			
			final HorizontalLayout subscriberInfoLayout = new HorizontalLayout();

			final FormLayout emailSubscriberLayout = buildTextField("Email","Enter subscriber email",false,null);
			subscriptionForm.setSubscriberEmail((TextField)emailSubscriberLayout.getComponent(0));
			subscriberInfoLayout.addComponent(emailSubscriberLayout);
			subscriberInfoLayout.setComponentAlignment(emailSubscriberLayout,Alignment.BOTTOM_RIGHT);

			final FormLayout firstNameLayout = buildTextField("First name","Enter first name",false,null);
			subscriptionForm.setSubscriberFirstName((TextField)firstNameLayout.getComponent(0));
			subscriberInfoLayout.addComponent(firstNameLayout);
			subscriberInfoLayout.setComponentAlignment(firstNameLayout,Alignment.BOTTOM_RIGHT);

			final FormLayout lastNameLayout = buildTextField("Last name","Enter last name",false,null);
			subscriptionForm.setSubscriberLastName((TextField)lastNameLayout.getComponent(0));
			subscriberInfoLayout.addComponent(lastNameLayout);
			subscriberInfoLayout.setComponentAlignment(lastNameLayout,Alignment.BOTTOM_RIGHT);

			final Button addUserButton = new Button("Add subscriber");
			subscriberInfoLayout.addComponent(addUserButton);
			subscriberInfoLayout.setComponentAlignment(addUserButton,Alignment.MIDDLE_RIGHT);
			
			addUserButton.addClickListener(new SubscriberAddListener(context,subscription));
			subscriberInfoLayout.setSpacing(true);
			infoUI.addComponent(subscriberInfoLayout);
			
			panel.setContent(infoUI);
			return panel;
		}

		private Panel buildBackButtonUI(){
			final VerticalLayout backButtonUI = new VerticalLayout();
			backButtonUI.setSpacing(true);
			backButtonUI.setMargin(true);
			
			final Panel panel = new Panel();
			panel.setWidth(100,Unit.PERCENTAGE);
			panel.setHeight(40,Unit.PERCENTAGE);
			
			final Button backButton = new Button("Back to listing");
			backButtonUI.addComponent(backButton);
			backButton.addClickListener(new ClickListener() {
				private static final long serialVersionUID = 1L;
				public void buttonClick(final ClickEvent event) {
					final Integer componentCount = ((VerticalLayout)((HorizontalLayout)context.getContainer()).getComponent(0)).getComponentCount();
					final VerticalLayout mainAreaLayout = ((VerticalLayout)((HorizontalLayout)context.getContainer()).getComponent(0));
					final List <Component> componentToRemove = new ArrayList<Component>();
					for (int i=0;i<componentCount;i++){
						if (!mainAreaLayout.getComponent(i).isVisible()){
							mainAreaLayout.getComponent(i).setVisible(true);
						}
						else {
							componentToRemove.add(mainAreaLayout.getComponent(i));
						}
					}
					
					for (Component compoenent:componentToRemove ){
						mainAreaLayout.removeComponent(compoenent);
					}

					mainAreaLayout.setWidth(100,Unit.PERCENTAGE);
					mainAreaLayout.setSpacing(false);
					mainAreaLayout.setMargin(false);

					((HorizontalLayout)context.getContainer()).removeComponent(((HorizontalLayout)context.getContainer()).getComponent(1));
					
					//remove the screentoolbar for detailed page
					((HorizontalLayout)context.getContainer()).removeComponent(screenToolbar);
					
					//And then make the listing screentoolbar visible.
					screenToolbarForListing.setVisible(true);
					((HorizontalLayout)context.getContainer()).addComponent(screenToolbarForListing);
				}
			
			});
			
			backButtonUI.setComponentAlignment(backButton, Alignment.MIDDLE_RIGHT);
			backButtonUI.setSpacing(true);
			backButtonUI.setMargin(true);
			
			panel.setContent(backButtonUI);
			return panel;
		}
		
		private FormLayout buildTextField(final String fieldText,final String fieldInputPrompt,final Boolean readOnly,final String value){
			final FormLayout fieldLayout = new FormLayout();
			fieldLayout.setSpacing(true);
			final TextField textField = new TextField(fieldText);
			textField.setColumns(15);
			
			if (null!=value){
				textField.setValue(value);
			}
			
			textField.setInputPrompt(fieldInputPrompt);
			textField.addStyleName("horizontalForm");
			textField.setReadOnly(readOnly);
			fieldLayout.addComponent(textField);
			fieldLayout.setComponentAlignment(textField,Alignment.MIDDLE_LEFT);

			return fieldLayout;
		}
		
		private FormLayout buildTextArea(final String fieldText,final String fieldInputPrompt,final String value){
			final FormLayout fieldLayout = new FormLayout();
			final TextArea textArea = new TextArea(fieldText);
			
			if (null!=value){
				textArea.setValue(value);
			}
			
			textArea.setColumns(50);
			textArea.setInputPrompt(fieldInputPrompt);
			textArea.addStyleName("horizontalForm");
			fieldLayout.addComponent(textArea);
			return fieldLayout;
		}

		private GridLayout buildScreenToolbar(){
			final GridLayout toolbarGridLayout = new GridLayout(1,2);

			final List<com.vaadin.event.MouseEvents.ClickListener> listeners = new ArrayList<com.vaadin.event.MouseEvents.ClickListener>();
			listeners.add(new SubscriptionSaveListener(context));

			final ScreenToolbarBuilder builder = new ScreenToolbarBuilder(toolbarGridLayout,"subscription-edit",listeners);
			builder.build();

			return toolbarGridLayout;
		}


		@Override
		public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
			buildUI(null);		
		}

}
