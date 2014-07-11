package com.contento3.web.email.marketing;

import java.util.ArrayList;
import java.util.List;

import com.c3.email.marketing.newsletter.dto.NewsletterDto;
import com.c3.email.marketing.newsletter.service.NewsletterService;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.web.UIManagerContext;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.ScreenToolbarBuilder;
import com.contento3.web.site.PageTemplateTableBuilder;
import com.vaadin.server.Sizeable.Unit;
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
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class NewsletterAddEditListener implements ClickListener,com.vaadin.event.MouseEvents.ClickListener {

	private static final long serialVersionUID = 1L;

	private final UIManagerContext context;
	
	private final NewsletterService newsletterService;
	
	GridLayout screenToolbar = null;
	
	GridLayout screenToolbarForListing;
	
	final private HorizontalLayout containerLayout;
	
	private NewsletterForm newsletterForm = new NewsletterForm();
	
	public NewsletterAddEditListener(final UIManagerContext context){
		this.context = context;
		containerLayout = (HorizontalLayout)context.getContainer();
		newsletterService = (NewsletterService)context.getHelper().getBean("newsletterService");
		context.setForm(newsletterForm);
	}
	
	
	@Override
	public void buttonClick(final ClickEvent event) {
		final Integer id = (Integer)event.getButton().getData();
		buildUI(id);
	}

	private void buildUI(final Integer newsletterId){
		final VerticalLayout mainAreaLayout = ((VerticalLayout)((HorizontalLayout)context.getContainer()).getComponent(0));
		Integer numberOfCompnents = mainAreaLayout.getComponentCount();
		for (int i=0;i<numberOfCompnents;i++){
			mainAreaLayout.getComponent(i).setVisible(false);
		}		
		
		mainAreaLayout.addComponent(buildHeader());
		mainAreaLayout.addComponent(buildNewsletterInfoUI(newsletterId));
		
		if (null!=newsletterId){
			mainAreaLayout.addComponent(buildSubscriptionUI(newsletterId));
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
		final Label articleHeading = new Label("Newsletters");
		articleHeading.setStyleName("screenHeading");
		articleHeading.setSizeUndefined();
		searchLayout.addComponent(articleHeading);
		searchLayout.setSizeUndefined();
		searchLayout.setWidth(100, Unit.PERCENTAGE);
		return searchLayout;
	}
	
	private Panel buildNewsletterInfoUI(final Integer newsletterId){
		NewsletterDto newsletter=null;
		String newsletterName = null;
		String newsletterDescription = null;
		String subject = null;
		if (null!=newsletterId){
			newsletter = newsletterService.findById(newsletterId);
			newsletterName = newsletter.getName();
			newsletterDescription = newsletter.getDescription();
			subject = newsletter.getSubject();
			context.setIdToEdit(newsletterId);
		}
		
		final VerticalLayout infoUI = new VerticalLayout();
		infoUI.setSpacing(true);
		infoUI.setMargin(true);
		
		final Panel panel = new Panel();
		panel.setWidth(100,Unit.PERCENTAGE);
		panel.setHeight(30,Unit.PERCENTAGE);
		
		FormLayout formLayout = buildTextField("Newsletter name","Enter newsletter name",false,newsletterName);
		newsletterForm.setNewsletterName((TextField)formLayout.getComponent(0));
		infoUI.addComponent(formLayout);
		infoUI.setComponentAlignment(formLayout,Alignment.BOTTOM_RIGHT);

		FormLayout descriptionTextAreaForm = buildTextArea("Description","Enter description",newsletterDescription);
		newsletterForm.setDescription((TextArea)descriptionTextAreaForm.getComponent(0));
		infoUI.addComponent(descriptionTextAreaForm);

		FormLayout subjectTextAreaForm = buildTextField("Email Subject","Enter Subject",false,subject);
		newsletterForm.setSubject((TextField)subjectTextAreaForm.getComponent(0));
		infoUI.addComponent(subjectTextAreaForm);

		final HorizontalLayout templateSelectionLayout = new HorizontalLayout();
		
		FormLayout layout = null;
		String templatePath = null;
		if (newsletter!=null){
			final TemplateDto template = newsletter.getTemplate();
			if (template!=null){
				templatePath = template.getTemplatePath()+"/"+template.getTemplateName();
			}
		}
		
		layout = buildTextField("Select Template","template",true,templatePath);
		newsletterForm.setTemplatePath((TextField)layout.getComponent(0));
		templateSelectionLayout.addComponent(layout);

		final Button templateButton = new Button("Select template");
		final Button previewButton = new Button("Preview template");
		
		//creating associated template table
		Table templateTable = new Table();
		final AbstractTableBuilder templateTableBuilder = new PageTemplateTableBuilder(context.getHelper(), templateTable);

		templateButton.addClickListener(new NewsletterTemplatePickerListener("test popup",context.getHelper(), templateTableBuilder,(TextField)layout.getComponent(0)));
		templateSelectionLayout.addComponent(templateButton);
		templateSelectionLayout.setComponentAlignment(templateButton,Alignment.MIDDLE_CENTER);

    	final TemplateDto template = (TemplateDto)UI.getCurrent().getData();

    	if (template==null){
    		template = newsletter.getTemplate();
    	}
    	
		previewButton.addClickListener(new NewsletterTemplatePreviewListener("test popup",context.getHelper(), templateTableBuilder,(TextField)layout.getComponent(0)));
		templateSelectionLayout.addComponent(previewButton);
		templateSelectionLayout.setComponentAlignment(previewButton,Alignment.MIDDLE_CENTER);

		templateSelectionLayout.setSpacing(true);
		
		infoUI.addComponent(templateSelectionLayout);
			
		panel.setContent(infoUI);
		context.setForm(newsletterForm);

		return panel;
	}

	private Panel buildSubscriptionUI(final Integer newsletterId){
		final VerticalLayout infoUI = new VerticalLayout();
		infoUI.setSpacing(true);
		infoUI.setMargin(true);
		
		final Panel panel = new Panel();
		panel.setWidth(100,Unit.PERCENTAGE);
		panel.setHeight(40,Unit.PERCENTAGE);
		
		final HorizontalLayout subscriptionLayout = new HorizontalLayout();
		final Label subscriptionLabel = new Label("Select subscription lists");
		subscriptionLayout.addComponent(subscriptionLabel);
		final Button subscriptionPickerButton = new Button("Add Subcription List");
		subscriptionLayout.addComponent(subscriptionPickerButton);
		subscriptionPickerButton.addClickListener(new SubscriptionListPicker(this.context,newsletterId));

		subscriptionLayout.setComponentAlignment(subscriptionPickerButton, Alignment.MIDDLE_CENTER);
		subscriptionLayout.setSpacing(true);
		subscriptionLayout.setMargin(true);
		
		infoUI.addComponent(subscriptionLayout);
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
		
		final Button backButton = new Button("Back to newsletter listing");
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
		final TextField textField = new TextField(fieldText);
		textField.setColumns(50);
		
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
		textArea.setWidth(100,Unit.PIXELS);
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
		listeners.add(new NewsletterSaveListener(context));
		listeners.add(new NewsletterSendListener(context));

		
		final ScreenToolbarBuilder builder = new ScreenToolbarBuilder(toolbarGridLayout,"newsletter-edit",listeners);
		builder.build();

		return toolbarGridLayout;
	}


	@Override
	public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
		buildUI(null);		
	}

}

