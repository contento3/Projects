package com.contento3.web.pagemodules;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class PageSectionPreviewListener implements ClickListener {

	private static final long serialVersionUID = 1L;

	final Label finalHtmlLabel;
	
	public PageSectionPreviewListener(final Label finalHtmlLabel){
		this.finalHtmlLabel = finalHtmlLabel;
	}
	
	
	@Override
	public void buttonClick(ClickEvent event) {
		finalHtmlLabel.setWidth(600,Unit.PIXELS);
		finalHtmlLabel.setHeight(125,Unit.PIXELS);

		previewAddedTemplates();
	}

	private void previewAddedTemplates(){
		StreamResource.StreamSource source = new StreamResource.StreamSource() {
            public InputStream getStream() {
               byte[] b = null;
                b=finalHtmlLabel.getValue().getBytes();
            return new ByteArrayInputStream(b);
            }
		};
		StreamResource resource = new StreamResource(source, "TestReport.html");
		final Embedded previewHtmlEmbedded = new Embedded();
		previewHtmlEmbedded.setMimeType("text/html");
		previewHtmlEmbedded.setType(Embedded.TYPE_BROWSER);
		previewHtmlEmbedded.setWidth("100%");
		previewHtmlEmbedded.setHeight("400px");
		previewHtmlEmbedded.setSource(resource); 
		
		final VerticalLayout previewTemplateLayout = new VerticalLayout();
		previewTemplateLayout.addComponent(previewHtmlEmbedded);
		
		final Window previewWindow = new Window();
		previewWindow.setContent(previewTemplateLayout);
		UI.getCurrent().addWindow(previewWindow);
	}

}
