package com.contento3.web.site;

import java.util.Collection;

import com.contento3.cms.site.structure.domain.dto.SiteDomainDto;
import com.contento3.cms.site.structure.domain.service.SiteDomainService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

public class SiteDomainPopup extends CustomComponent
implements Window.CloseListener{
	private static final long serialVersionUID = 1L;

    Window mainwindow;  // Reference to main window
    Window popupWindow;    // The window to be opened
    Button openbutton;  // Button for opening the window
    Button closebutton; // A button in the window
    Button editButton;
    
    SiteService siteService;
    SiteDomainService siteDomainService;
    boolean isModalWindowClosable = true;
    SpringContextHelper helper;
    final private Integer siteId;
    
    final Table siteDomainTable;
    
    public SiteDomainPopup(final Window main,final SpringContextHelper helper,final Integer siteId,final Table table) {
        mainwindow = main;
        this.helper = helper;
        this.siteService = (SiteService)helper.getBean("siteService");
        this.siteDomainService = (SiteDomainService)helper.getBean("siteDomainService");
        
        this.siteDomainTable = table;
        
        // The component contains a button that opens the window.
        final VerticalLayout layout = new VerticalLayout();
        this.siteId = siteId;
        openbutton = new Button("Add Site Domain", this, "openButtonClick");
        layout.addComponent(openbutton);

        setCompositionRoot(layout);
    }

    /** Handle the clicks for the two buttons. */
    public void openButtonClick(Button.ClickEvent event) {
        /* Create a new window. */
        final Button siteDomainButton = new Button();
		popupWindow = new Window();
    	
		popupWindow.setPositionX(200);
    	popupWindow.setPositionY(100);

    	popupWindow.setHeight(25,Sizeable.UNITS_PERCENTAGE);
    	popupWindow.setWidth(21,Sizeable.UNITS_PERCENTAGE);
       
    	/* Add the window inside the main window. */
        mainwindow.addWindow(popupWindow);
        
        /* Listen for close events for the window. */
        popupWindow.addListener(this);
        popupWindow.setModal(true);
        
        final VerticalLayout popupMainLayout = new VerticalLayout();
        final Label label = new Label("Domain Name");
        final HorizontalLayout inputDataLayout = new HorizontalLayout();
        final TextField textField = new TextField("");
        textField.setInputPrompt("Enter domain name");
        
        inputDataLayout.setSpacing(true);
        inputDataLayout.addComponent(label);
        inputDataLayout.setComponentAlignment(label, Alignment.BOTTOM_RIGHT);
        inputDataLayout.addComponent(textField);
        
        popupMainLayout.addComponent(inputDataLayout);
        popupMainLayout.setSpacing(true);
        
        final HorizontalLayout addButtonLayout = new HorizontalLayout();
        popupMainLayout.addComponent(addButtonLayout);

        addButtonLayout.addComponent(siteDomainButton);
        addButtonLayout.setComponentAlignment(siteDomainButton, Alignment.BOTTOM_RIGHT);
        addButtonLayout.setWidth(100, Sizeable.UNITS_PERCENTAGE);
        
        popupWindow.addComponent(popupMainLayout);
        popupWindow.setResizable(false);
        /* Allow opening only one window at a time. */
        openbutton.setEnabled(false);

        final Integer domainId;
		final SiteDto updatedSiteDto = siteService.findSiteById(siteId);

    	if (event.getButton().getCaption().equals("Edit")){
	        siteDomainButton.setCaption("Save");
	        popupWindow.setCaption("Edit domain");
	        domainId = (Integer)event.getButton().getData();
	        siteDomainButton.addListener(new ClickListener() {
				private static final long serialVersionUID = 1L;
				public void buttonClick(ClickEvent event) {
					handleEditDomain(textField,domainId,updatedSiteDto);
				}	
			});
    	}
    	else
    	{
	        siteDomainButton.setCaption("Add");
	        popupWindow.setCaption("Add new domain");
	        siteDomainButton.addListener(new ClickListener() {
				private static final long serialVersionUID = 1L;
				public void buttonClick(ClickEvent event) {
					handleNewDomain(textField,updatedSiteDto);
				}	
			});
    	}
    }

    /**
     * Handles adding new SiteDomain
     * @param textField
     */
	private void handleNewDomain(final TextField textField,final SiteDto updatedSiteDto){
		SiteDomainDto siteDomainDto = new SiteDomainDto();
		siteDomainDto.setDomainName(textField.getValue().toString());

		updatedSiteDto.getSiteDomainDto().add(siteDomainDto);
		siteService.update(updatedSiteDto);

		resetTable();
    }


    /**
     * Handles adding new SiteDomain
     * @param textField
     */
	private void handleEditDomain(final TextField textField,final Integer domainId,final SiteDto updatedSiteDto){
		Collection <SiteDomainDto> siteDomainDtos = updatedSiteDto.getSiteDomainDto();
		
		for(SiteDomainDto dto:siteDomainDtos){
			if (dto.getDomainId().equals(domainId)){
				dto.setDomainName(textField.getValue().toString());
				siteDomainService.update(dto);
				break;
			}
		}
		resetTable();
    }


    @SuppressWarnings("rawtypes")
	private void resetTable(){
		final AbstractTableBuilder tableBuilder = new SiteDomainTableBuilder(mainwindow,helper,siteDomainTable);
		final SiteDto updatedSiteDto = siteService.findSiteById(siteId);
		tableBuilder.rebuild((Collection)updatedSiteDto.getSiteDomainDto());
		
		mainwindow.removeWindow(popupWindow);
        openbutton.setEnabled(true);
    }
    
    /** Handle Close button click and close the window. */
    public void closeButtonClick(Button.ClickEvent event) {
    	if (!isModalWindowClosable){
        /* Windows are managed by the application object. */
        mainwindow.removeWindow(popupWindow);
        
        /* Return to initial state. */
        openbutton.setEnabled(true);
    	}
    }

    /** In case the window is closed otherwise. */
    public void windowClose(CloseEvent e) {
        /* Return to initial state. */
        openbutton.setEnabled(true);
    }

}
