package com.contento3.web.layout;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.layout.LayoutBuilder;
import com.contento3.cms.page.layout.dto.PageLayoutDto;
import com.contento3.cms.page.layout.dto.PageLayoutTypeDto;
import com.contento3.cms.page.layout.service.PageLayoutService;
import com.contento3.cms.page.layout.service.PageLayoutTypeService;
import com.contento3.cms.page.section.dto.PageSectionDto;
import com.contento3.cms.page.service.PageService;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.common.exception.EnitiyAlreadyFoundException;
import com.contento3.web.UIManager;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.AbstractSplitPanel.SplitterClickEvent;
import com.vaadin.ui.AbstractSplitPanel.SplitterClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class LayoutUIManager implements UIManager {
	private static final Logger logger = Logger.getLogger(LayoutUIManager.class);
	private final IndexedContainer container = new IndexedContainer();


	public LayoutUIManager(final SpringContextHelper helper,final Window parentWindow){
		this.helper = helper;
		this.parentWindow = parentWindow;
//		this.layoutService = (SiteService) helper.getBean("layoutService");
//		this.pageService = (PageService) helper.getBean("pageService");
	}
	
	public void render (){
		System.out.println("Rendering the layout manager");
	}
	
	
	@Override
	public Component render(final String command,final Integer id){
		System.out.println("Rendering the layout manager");
		return null;
	}
	
	private final String HEADER_HTML = "<div id=\"hd\"></div>"; 
	private final String FOOTER_HTML = "<div id=\"ft\"></div>"; 
	private StringBuffer layoutHtml = new StringBuffer();
	private String selectedColumnType;
	
	CustomLayout previewLayout;
	ComboBox bodySizeCombo;
	
	CheckBox footer = new CheckBox();
	CheckBox header = new CheckBox();

	LayoutBuilder layoutBuilder;

    HorizontalSplitPanel horiz;
	
    private SpringContextHelper helper;
    private Window parentWindow;
    private SiteService layoutService;
	private PageService pageService;

	TabSheet layoutManagerTab;
	Label layoutHTMLLabel = new Label();
	/**
	 * 
	 * @return
	 */
	@Override
	public Component render(String command){
		final PageService pageService = (PageService) helper.getBean("pageService");
		final Table table = new Table("Layout pages");
		table.setImmediate(true);
	//	final Collection<PageDto> layoutDtos = pageService.getPageBySiteId(id);
		if (null==layoutManagerTab){ 
    	layoutManagerTab = new TabSheet();
    	layoutManagerTab.setHeight("675");
    	layoutManagerTab.setWidth(100,Sizeable.UNITS_PERCENTAGE);
    	
    	
    	Label layoutTypes = new Label("Layout Type");
    	Label layouts = new Label("Layout");
    	Label layout2 = new Label("list");
    	//Get accountId from the session

        WebApplicationContext ctx = ((WebApplicationContext) parentWindow.getApplication().getContext());

        HttpSession session = ctx.getHttpSession();

        Integer accountId = (Integer)session.getAttribute("accountId");
        final Collection<PageDto> layoutDtos = pageService.getPageBySiteId(accountId);
        if(!CollectionUtils.isEmpty(layoutDtos)){
        	container.addContainerProperty("Layout Type", String.class, null);
			container.addContainerProperty("Layout", String.class, null);
			table.setWidth(100, Sizeable.UNITS_PERCENTAGE);        
			table.setPageLength(25);
			Button link = null;
			for (PageDto page : layoutDtos) {
				System.out.println("Collection: "+page);
				//addPageToPageListTable(page, accountId, layoutManagerTab, link);
				
        }
			table.setContainerDataSource(container);
			layoutManagerTab.addComponent(table);
		} else {
			final Label label = new Label("No layout found for this site.");
			layoutManagerTab.addComponent(label);
		}

        // Add a horizontal SplitPanel for work area and preview area
    	horiz = new HorizontalSplitPanel();
        horiz.setSplitPosition(35);

        horiz.addListener(new SplitterClickListener(){
			public void splitterClick(SplitterClickEvent event){
				
				int splitPosition = horiz.getSplitPosition();
				
		        if (splitPosition==0)
		        	horiz.setSplitPosition(35);
		        else
					horiz.setSplitPosition(0);
        	
			}
    	});
    	
    	VerticalLayout layout = new VerticalLayout();
    	VerticalLayout createNewLayout = new VerticalLayout();
    	createNewLayout.setWidth(100,Sizeable.UNITS_PERCENTAGE);
        horiz.addComponent(createNewLayout);

    	Tab tab1 = layoutManagerTab.addTab(layoutTypes,"Layout Type",null);
    	Tab tab2 = layoutManagerTab.addTab(layout,"Layout",null);
    	Tab tab3 = layoutManagerTab.addTab(horiz,"Create new layout",null);
	
//    	Button createPageButton = new Button("Create new page");
//    	createPageButton.addListener(this); // react to clicks

    	layout.addComponent(layoutTypes);
    	layout.addComponent(layouts);
		layout.addComponent(layout2);
    	Label createNewLayoutLabel = new Label("Create new layout");
    	createNewLayout.addComponent(createNewLayoutLabel);
    	buildNewLayoutComponent(createNewLayout);
		}
		
		return layoutManagerTab;
	}
	
	ComboBox pageLeftSectionCombo;
	FormLayout pageLeftSectionWidthLayout;
    private void buildNewLayoutComponent(VerticalLayout verticalLayout){
    	
    	GridLayout glayout = new GridLayout(4,4);
        bodySizeCombo = new ComboBox("Page Width Size",getBodySize());

        FormLayout pagebodySizeCombo = new FormLayout();
    	pagebodySizeCombo.addComponent(bodySizeCombo);

        pageLeftSectionWidthLayout = new FormLayout();
        pageLeftSectionCombo = new ComboBox("Width:",buildPageLeftSectionCombo());
        pageLeftSectionWidthLayout.addComponent(pageLeftSectionCombo);
        
        OptionGroup leftSection = new OptionGroup("Select navigation section");
    	leftSection.addItem("Left Navigation");
    	leftSection.addItem("Right Navigation");
    	leftSection.setImmediate(true);
    	leftSection.addListener(new ValueChangeListener(){
    	    public void valueChange(ValueChangeEvent event) {
    	    	String selectedValue = (String) event.getProperty().getValue();
	    		pageLeftSectionWidthLayout.removeComponent(pageLeftSectionCombo);
    	    	if (selectedValue.equals("Left Navigation")){
    	    		pageLeftSectionCombo = new ComboBox("Width:",buildPageLeftSectionCombo());
    	    	}
    	    	else {
    	    		pageLeftSectionCombo = new ComboBox("Width:",buildPageRightSectionCombo());
    	    	}
	    		pageLeftSectionWidthLayout.addComponent(pageLeftSectionCombo);
	            pageLeftSectionCombo.setWidth("100px");
			}
    	});

        pageLeftSectionCombo.setWidth("100px");

    	FormLayout pageRightSectionWidthLayout = new FormLayout();
        ComboBox pageRightSectionCombo = new ComboBox("Width:",buildPageRightSectionCombo());
        pageRightSectionWidthLayout.addComponent(pageRightSectionCombo);
        pageRightSectionCombo.setWidth("100px");
        
//    	CheckBox rightSection = new CheckBox();
//    	rightSection.setCaption("Right Section");
    	
    	header.setCaption("Header");
    	header.setImmediate(true);
    	header.setWidth(10,Sizeable.UNITS_PERCENTAGE);
        final TextField headerHeight = new TextField();
        headerHeight.setCaption("Height");
        headerHeight.setColumns(4);
        FormLayout formLayout = new FormLayout();
        formLayout.addComponent(headerHeight);
        
        footer.setCaption("Footer");
    	footer.setImmediate(true);
    	footer.setWidth(10,Sizeable.UNITS_PERCENTAGE);

        final TextField footerHeight = new TextField();
        footerHeight.setCaption("Height");
        footerHeight.setColumns(4);

        FormLayout footerFormLayout = new FormLayout();
        footerFormLayout.addComponent(footerHeight);

        glayout.addComponent(header, 0, 0);
        glayout.addComponent(formLayout, 1,0 );
        glayout.addComponent(footer, 0,1 );
        glayout.addComponent(footerFormLayout, 1,1 );
        
        glayout.addComponent(leftSection, 0,2 );
        glayout.addComponent(pageLeftSectionWidthLayout, 1,2 );

  //      glayout.addComponent(rightSection, 0,3 );
 //       glayout.addComponent(pageRightSectionWidthLayout, 1,3 );

        glayout.setColumnExpandRatio(0, 3);
        glayout.setColumnExpandRatio(1, 10);
        
        glayout.setWidth(100,Sizeable.UNITS_PERCENTAGE);
        glayout.setComponentAlignment(formLayout, Alignment.MIDDLE_LEFT);
        glayout.setComponentAlignment(footerFormLayout, Alignment.MIDDLE_LEFT);

        glayout.setComponentAlignment(footer, Alignment.MIDDLE_LEFT);
        glayout.setComponentAlignment(header, Alignment.MIDDLE_LEFT);

        glayout.setComponentAlignment(pageLeftSectionCombo, Alignment.MIDDLE_LEFT);
        glayout.setComponentAlignment(leftSection, Alignment.MIDDLE_LEFT);

  //      glayout.setComponentAlignment(pageRightSectionCombo, Alignment.MIDDLE_LEFT);
 //       glayout.setComponentAlignment(rightSection, Alignment.MIDDLE_LEFT);

        Label footerHeightLabel = new Label();
        footerHeightLabel.setCaption("Footer height in px:");
       
        final ComboBox mainBodySplit = new ComboBox("Select row",getMainBodySplit());
        mainBodySplit.setWidth(100,Sizeable.UNITS_PERCENTAGE);
    	HorizontalLayout mainBodySplitLayout = new HorizontalLayout();
    	Button link = new Button();
    	link.setCaption("Add new row");
    	link.setWidth(55,Sizeable.UNITS_PERCENTAGE);


		link.addListener(new ClickListener(){

			public void buttonClick(ClickEvent event){
				String selectedBodySize = (String)bodySizeCombo.getValue();
				String selectedBodyStyle=getSelectedBodySize(selectedBodySize);

	    		layoutBuilder =(LayoutBuilder) helper.getBean("layoutBuilder");
				if (null==layoutBuilder){
		    		layoutBuilder.setBodyWidthStyle(selectedBodyStyle);
		    		layoutBuilder.setFooterHeight(footerHeight.getValue().toString());
		    		layoutBuilder.setHeaderHeight(footerHeight.getValue().toString());
		    	}

				String selectedValue = (String)mainBodySplit.getValue();
				layoutBuilder.addNewBodyRow(selectedValue);
				layoutHTMLLabel.setCaption(selectedValue);
				System.out.print(layoutBuilder.getLayoutHTML());
			//	refreshPreview();
			}
    	});
    	
    	mainBodySplitLayout.setWidth(100,Sizeable.UNITS_PERCENTAGE);
        
    	Button saveButton = new Button();
    	saveButton.setCaption("Save");
    	saveButton.setImmediate(true);

    	Button previewLayoutButton = new Button();
    	previewLayoutButton.setCaption("Preview");
    	previewLayoutButton.setImmediate(true);

    	HorizontalLayout horizButtonsLayout = new HorizontalLayout();
    	horizButtonsLayout.addComponent(saveButton);
    	horizButtonsLayout.addComponent(previewLayoutButton);
    	
    	horizButtonsLayout.setSpacing(true);
    	horizButtonsLayout.setMargin(true);
    	horizButtonsLayout.setComponentAlignment(saveButton, Alignment.BOTTOM_RIGHT);
    	horizButtonsLayout.setComponentAlignment(previewLayoutButton, Alignment.BOTTOM_RIGHT);
    	
    	saveButton.addListener(new ClickListener(){
			public void buttonClick(ClickEvent event){
				String selectedBodySize = (String)bodySizeCombo.getValue();
				String selectedBodyStyle=getSelectedBodySize(selectedBodySize);

				if (null==layoutBuilder){
		    		layoutBuilder =(LayoutBuilder) helper.getBean("layoutBuilder");
		    		layoutBuilder.setBodyWidthStyle(selectedBodyStyle);
		    		layoutBuilder.setFooterHeight(footerHeight.getValue().toString());
		    		layoutBuilder.setHeaderHeight(footerHeight.getValue().toString());
		    	}
				else {
					layoutBuilder.addBodyWidth(selectedBodyStyle);
				}
				
				if (header.booleanValue())	{
					layoutBuilder.addHeader();
				}
				if (footer.booleanValue()){
					layoutBuilder.addFooter();
				}	
				if (!header.booleanValue())	{
					layoutBuilder.removeHeader();
				}
				if (!footer.booleanValue()){
					layoutBuilder.removeFooter();
				}	
				
				
//				String selectedValue = (String)mainBodySplit.getValue();
//				layoutBuilder.addNewBodyRow(selectedValue);

				layoutHTMLLabel.setCaption(layoutBuilder.getLayoutHTML());
				//System.out.print(layoutBuilder.getLayoutHTML());
				
				refreshPreview();
				
				

				//And then save it.
	    		PageLayoutService pageLayoutService = (PageLayoutService)helper.getBean("pageLayoutService");
	    		PageLayoutDto layoutDto = new PageLayoutDto();
	    		
	    		PageLayoutTypeService pageLayoutTypeService = (PageLayoutTypeService)helper.getBean("pageLayoutTypeService");
	    		PageLayoutTypeDto layoutTypeDto = pageLayoutTypeService.findByName("Build");
	    		
	    		//TODO this needs to be changes to dynamic 
				//value.This should come from users session
	    		layoutDto.setAccountId(1);
	    		layoutDto.setLayoutTypeDto(layoutTypeDto);
	    		layoutDto.setName("test layout");

	    		//Get the dto representation of the layout
				Collection <PageSectionDto> pageSectionDto = layoutBuilder.convertHtmlToDto();
				layoutDto.setPageSections(pageSectionDto);

	    		try {//TODO
					pageLayoutService.create(layoutDto);
				} catch (EnitiyAlreadyFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

   	        }
    	});
    	
    	FormLayout mainBodySplitFormLayout = new FormLayout();
    	mainBodySplitFormLayout.addComponent(mainBodySplit);
    	mainBodySplitFormLayout.setWidth(100, Sizeable.UNITS_PERCENTAGE);
    	
    	mainBodySplitLayout.setSpacing(true);
    	mainBodySplitLayout.addComponent(mainBodySplitFormLayout);
    	mainBodySplitLayout.addComponent(link);
     	mainBodySplitLayout.setComponentAlignment(link,Alignment.MIDDLE_LEFT);
    	
    	verticalLayout.addComponent(pagebodySizeCombo);
    	verticalLayout.addComponent(glayout);
    	verticalLayout.addComponent(layoutHTMLLabel);
    	
    	verticalLayout.addComponent(mainBodySplitLayout);
    	verticalLayout.addComponent(horizButtonsLayout);
    	    	
    	verticalLayout.setMargin(true);
    	verticalLayout.setSpacing(true);
    }

    private String getSelectedBodySize (String selectedBodySize) {
    	String selectedBodyStyle=null;
    	if (null!=selectedBodySize)
		{
			if (selectedBodySize.equals("950px")) {
				selectedBodyStyle = "doc2";
			}
			else if (selectedBodySize.equals("750px")){
				selectedBodyStyle = "doc";
			}
			else if (selectedBodySize.equals("100%")){
				selectedBodyStyle = "doc3";
			}
			else if (selectedBodySize.equals("974px")){
				selectedBodyStyle = "doc4";
			}
			else if (selectedBodySize.equals("custom")){
				selectedBodyStyle = "doc-custom";
			}
		}
		return selectedBodyStyle;
    }
    
    Label label;
    private void refreshPreview(){

			//previewLayout = new CustomLayout(new ByteArrayInputStream(layoutBuilder.getLayoutHTML().getBytes()));
			Panel panel = new Panel("Layout preview");
			panel.setContent(previewLayout);
		
			if (label==null)
			label = new Label(layoutBuilder.getLayoutHTML());
			
			label.requestRepaint();
						//label.setCaption(layoutBuilder.getLayoutHTML());
			label.setContentMode(Label.CONTENT_RAW);
			label.setImmediate(true);
			
			if (horiz.getComponentCount()<2)
			horiz.addComponent(label);
			label.setValue(layoutBuilder.getLayoutHTML());
    }
    
    private IndexedContainer getBodySize() {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("name", String.class,null);
        container.addContainerProperty("value", String.class,null);
        
        Item px750 = container.addItem("750px");
        px750.getItemProperty("name").setValue("750px");
        px750.getItemProperty("value").setValue("750");

        Item px950 = container.addItem("950px");
        px950.getItemProperty("name").setValue("950px");
        px950.getItemProperty("value").setValue("950");

        Item px974 = container.addItem("974px");
        px974.getItemProperty("name").setValue("974px");
        px974.getItemProperty("value").setValue("974");

        Item percent100 = container.addItem("100%");
        percent100.getItemProperty("name").setValue("100%");
        percent100.getItemProperty("value").setValue("100");

        Item percentCustom = container.addItem("custom");
        percentCustom.getItemProperty("name").setValue("custom");
        percentCustom.getItemProperty("value").setValue("custom");

        container.sort(new Object[] { "name" },new boolean[] { true });
        return container;
    }

    private IndexedContainer getMainBodySplit() {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("name", String.class,null);
        container.addContainerProperty("value", String.class,null);
        
        Item oneCol100 = container.addItem("1 column - 100%");
        oneCol100.getItemProperty("name").setValue("1 column - 100%");
        oneCol100.getItemProperty("value").setValue("1col-100");

        Item twoCol50 = container.addItem("2 column - 50/50%");
        twoCol50.getItemProperty("name").setValue("2 column - 50/50%");
        twoCol50.getItemProperty("value").setValue("2col-50");
        
        Item twoCol6633 = container.addItem("2 column - 66/33%");
        twoCol6633.getItemProperty("name").setValue("2 column - 66/33%");
        twoCol6633.getItemProperty("value").setValue("2col-6633");

        Item twoCol3366 = container.addItem("2 column - 33/66%");
        twoCol3366.getItemProperty("name").setValue("2 column - 33/66%");
        twoCol3366.getItemProperty("value").setValue("2col-3366");

        Item twoCol7525 = container.addItem("2 column - 75/25%");
        twoCol7525.getItemProperty("name").setValue("2 column - 75/25%");
        twoCol7525.getItemProperty("value").setValue("2col-7525");

        Item twoCol2575 = container.addItem("2 column - 25/75%");
        twoCol2575.getItemProperty("name").setValue("2 column - 25/75%");
        twoCol2575.getItemProperty("value").setValue("2col-2575");

        Item threeCol333333 = container.addItem("3 column - 33/33/33%");
        threeCol333333.getItemProperty("name").setValue("3 column - 33/33/33%");
        threeCol333333.getItemProperty("value").setValue("3col-3333333");

        Item threeCol502525 = container.addItem("3 column - 50/25/25%");
        threeCol502525.getItemProperty("name").setValue("3 column - 50/25/25%");
        threeCol502525.getItemProperty("value").setValue("3col-502525");

        Item threeCol252550 = container.addItem("3 column - 25/25/50%");
        threeCol252550.getItemProperty("name").setValue("3 column - 25/25/50%");
        threeCol252550.getItemProperty("value").setValue("3col-252550");

        Item fourCol25252525 = container.addItem("4 column - 25/25/25/25%");
        fourCol25252525.getItemProperty("name").setValue("4 column - 25/25/25/25%");
        fourCol25252525.getItemProperty("value").setValue("3col-25252525");

        container.sort(new Object[] { "name" },new boolean[] { true });
        return container;
    }

    private IndexedContainer buildPageLeftSectionCombo() {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("name", String.class,null);
        container.addContainerProperty("value", String.class,null);

        Item left160 = container.addItem("160px");
        left160.getItemProperty("name").setValue("160px");
        left160.getItemProperty("value").setValue("160px");

        Item left180 = container.addItem("180px");
        left180.getItemProperty("name").setValue("180px");
        left180.getItemProperty("value").setValue("180px");

        Item left300 = container.addItem("300px");
        left300.getItemProperty("name").setValue("300px");
        left300.getItemProperty("value").setValue("300px");

        container.sort(new Object[] { "name" },new boolean[] { true });
        return container;
    }

    private IndexedContainer buildPageRightSectionCombo() {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("name", String.class,null);
        container.addContainerProperty("value", String.class,null);

        Item left180 = container.addItem("180px");
        left180.getItemProperty("name").setValue("180px");
        left180.getItemProperty("value").setValue("180px");

        Item left240 = container.addItem("240px");
        left240.getItemProperty("name").setValue("240px");
        left240.getItemProperty("value").setValue("240px");

        Item left300 = container.addItem("300px");
        left300.getItemProperty("name").setValue("300px");
        left300.getItemProperty("value").setValue("300px");

        container.sort(new Object[] { "name" },new boolean[] { true });
        return container;
    }

	@Override
	public Component render(String command,
			HierarchicalContainer treeItemContainer) {
		// TODO Auto-generated method stub
		return null;
	}


	
}
