package com.contento3.web.pagemodules;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.contento3.util.CachedTypedProperties;
import com.contento3.web.UIManagerContext;
import com.contento3.web.common.helper.GenricEntityPicker;
import com.contento3.web.content.ContentPickerHelper;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;


/**
 * This is the class that handles the population of content for the layouts. Each row in the 
 * ui has an option to click on the "Content" link this class renders the ui based on type of 
 * layout and its elements.
 * @author hamakhaa
 *
 */
public class PageRowContentPopulatorListener implements ClickListener {

	private static final Logger LOGGER = Logger.getLogger(PageRowContentPopulatorListener.class);

	private static final long serialVersionUID = 1L;

	private final Window popupWindow;
    
	private final String TEXTFIELD = "TEXTFIELD";
	
	private final String CONTENTSELECTOR = "CONTENT-SELECTOR";
	
	private final String IMAGESELECTOR = "IMAGE-SELECTOR";
	
	private final String ARTICLESELECTOR = "ARTICLE-SELECTOR";

	private final String FIELDTYPE_FIELDNAME_FORMAT = "%s::%s";
	
	private final Map <String,String> elementFieldMap;

	private CachedTypedProperties properties;

	private final VerticalLayout mainContentLayout = new VerticalLayout();
	
	private Map <String,Component> fieldsComponentMap;

	String templateKey; 
	
	private final String SOCIALLABEL = "Social";

	private final String NAVIGATIONLABEL = "Navigation";

	private final String LOGOLABEL = "Logo";

	private final String SEARCHLABEL = "Search";

	private final UIManagerContext context;

	private GridLayout containerLayout = null;

	private Button doneButton;
	
	private Panel rowPanel;
	
	//Map contains mapping between an element and 
	//its value that required to be replaced
	final Map <Panel,Map<String,Component>> rowMap = new LinkedHashMap <Panel,Map<String,Component>>();
	
	public PageRowContentPopulatorListener (final String templateKey,final UIManagerContext context,Panel rowPanel) {
		popupWindow = new Window();
		elementFieldMap = new HashMap <String,String>();
		this.context = context;
		this.rowPanel = rowPanel;
		
		try {
			properties = CachedTypedProperties.getInstance("pagemodulesconfig.properties");
			this.templateKey = templateKey;
			fieldsComponentMap = new HashMap <String,Component> ();
		}
		catch (final ClassNotFoundException e) {
			LOGGER.error("Unable to read languages.properties,Reason:"+e);
		}
	}
	
	private void getElementFieldMapping() {
		for (PageContentElementEnum contentElement:PageContentElementEnum.values()){
			try {
				final String fetchedContentElement = properties.getStringProperty(contentElement.toString().toLowerCase());
				if (null!=fetchedContentElement){
					elementFieldMap.put(contentElement.toString(),fetchedContentElement);
				}
			}
			catch (final NullPointerException npe){
				LOGGER.debug(contentElement.toString() + " not found");
			}
		}
	}

	@Override
	public void buttonClick(final ClickEvent event) {
		createWindow();
	}

	private void createWindow () {
		popupWindow.setPositionX(250);
    	popupWindow.setPositionY(100);
    	popupWindow.setHeight(80,Unit.PERCENTAGE);
    	popupWindow.setWidth(60,Unit.PERCENTAGE);
    	popupWindow.setResizable(false);

    	mainContentLayout.setHeight(100,Unit.PERCENTAGE);
    	mainContentLayout.setWidth(100,Unit.PERCENTAGE);
    	mainContentLayout.setSpacing(true);
    	mainContentLayout.setMargin(true);

    	popupWindow.setContent(mainContentLayout);
    	popupWindow.setCaption("Page content");
    	
    	UI.getCurrent().addWindow(popupWindow);
		extractPageLayoutElements(templateKey);
		
		//Button that finally creates the Map that contains all the config for templates
    
		if (null==doneButton){
			doneButton = new Button("Done");
			rowMap.put(rowPanel,fieldsComponentMap);
	    	doneButton.addClickListener(new PageRowContentMapper(context,templateKey,rowMap,popupWindow));
	    	mainContentLayout.addComponent(doneButton);
	    	mainContentLayout.setComponentAlignment(doneButton, Alignment.BOTTOM_RIGHT);
		}
	}
	
	private void extractPageLayoutElements (final String templateKey) {
		getElementFieldMapping();

		final Map <String,Component> elementWithValuesMap = new HashMap<String,Component>();
		
		List <String> layoutInfo = null;
		layoutInfo = properties.getDelimetedProperty(templateKey,"|");
		
		if (!CollectionUtils.isEmpty(layoutInfo) && layoutInfo.size()>=2){
			//social::fb:twitter:linkedin_menu::pagebased:4_logo
			final String[] contentElementsArray = layoutInfo.get(1).split("_");
			
			for (String contentElement : contentElementsArray) {
				processElement(contentElement,elementWithValuesMap);
			}
		}
	}
	
	private void processElement(final String contentElement,final Map <String,Component> elementWithValuesMap) {
		//social
		if (contentElement.trim().startsWith(PageContentElementEnum.SOCIAL.toString().toLowerCase())){
			final String subElementArray[] = contentElement.split("::");
			
			final String elementArray[];
			if (ArrayUtils.isNotEmpty(subElementArray)){
				elementArray = subElementArray[1].split(":");
				
				GridLayout containerLayout = null;
				if (null==fieldsComponentMap.get(this.SOCIALLABEL)){
					containerLayout = new GridLayout(4,1);
					containerLayout.setWidth(75,Unit.PERCENTAGE);
					containerLayout.setSpacing(true);
					
					final Label titleLabel = new Label(PageContentElementEnum.SOCIAL.toString());
					titleLabel.setWidth(175,Unit.PIXELS);
					
					final HorizontalLayout horizontalContainerLayout = new HorizontalLayout();
					horizontalContainerLayout.addComponent(titleLabel);
					horizontalContainerLayout.setSpacing(true);
					horizontalContainerLayout.setMargin(true);
					
					fieldsComponentMap.put(this.SOCIALLABEL, titleLabel);
					
					containerLayout.setSpacing(true);
					containerLayout.setHeight(200,Unit.PERCENTAGE);
					horizontalContainerLayout.addComponent(containerLayout);
					
					mainContentLayout.addComponent(horizontalContainerLayout);
				}
				
				for (String element : elementArray){
					createUIElement(containerLayout,elementFieldMap.get(PageContentElementEnum.SOCIAL.toString()),element,elementWithValuesMap);
				}
			}
		}
		
			//search
			if (contentElement.trim().startsWith(PageContentElementEnum.SEARCH.toString().toLowerCase())){
				if (null==fieldsComponentMap.get(this.SEARCHLABEL)){
					containerLayout = new GridLayout(1,1);
					containerLayout.setWidth(75,Unit.PERCENTAGE);
					containerLayout.setSpacing(true);
					
				final Label titleLabel = new Label(PageContentElementEnum.SEARCH.toString());
				titleLabel.setWidth(175,Unit.PIXELS);
							
				final HorizontalLayout horizontalContainerLayout = new HorizontalLayout();
				horizontalContainerLayout.addComponent(titleLabel);
				horizontalContainerLayout.setSpacing(true);
				horizontalContainerLayout.setMargin(true);
						
				fieldsComponentMap.put(this.SEARCHLABEL, titleLabel);
					
				containerLayout.setSpacing(true);
				containerLayout.setHeight(200,Unit.PERCENTAGE);
				horizontalContainerLayout.addComponent(containerLayout);
							
				mainContentLayout.addComponent(horizontalContainerLayout);
			}
			createUIElement(containerLayout,elementFieldMap.get(PageContentElementEnum.SEARCH.toString()),PageContentElementEnum.SEARCH.toString()+ " SUBMISSION URL",elementWithValuesMap);
		}
			
		//navigation menu
		if (contentElement.trim().startsWith(PageContentElementEnum.NAVIGATION.toString().toLowerCase())){
			final String subElementArray[] = contentElement.split("::");
			
			final String elementArray[];
			if (ArrayUtils.isNotEmpty(subElementArray)){
				elementArray = subElementArray[1].split(":");
				
				if (null==fieldsComponentMap.get(this.NAVIGATIONLABEL)){
					containerLayout = new GridLayout(4,1);
					containerLayout.setWidth(75,Unit.PERCENTAGE);
					containerLayout.setSpacing(true);
					
					final Label titleLabel = new Label(PageContentElementEnum.NAVIGATION.toString());
					titleLabel.setWidth(175,Unit.PIXELS);
					
					final HorizontalLayout horizontalContainerLayout = new HorizontalLayout();
					horizontalContainerLayout.addComponent(titleLabel);
					horizontalContainerLayout.setSpacing(true);
					horizontalContainerLayout.setMargin(true);
					
					fieldsComponentMap.put(this.NAVIGATIONLABEL, titleLabel);
					
					containerLayout.setSpacing(true);
					containerLayout.setHeight(200,Unit.PERCENTAGE);
					horizontalContainerLayout.addComponent(containerLayout);
					
					mainContentLayout.addComponent(horizontalContainerLayout);
				}
				
					createUIElement(containerLayout,elementFieldMap.get(PageContentElementEnum.NAVIGATION.toString()),PageContentElementEnum.NAVIGATION.toString(),elementArray,elementWithValuesMap);
			}
		}
		
		//logo menu
		if (contentElement.trim().startsWith(PageContentElementEnum.LOGO.toString().toLowerCase())){
				if (null==fieldsComponentMap.get(this.LOGOLABEL)){
						containerLayout = new GridLayout(1,1);
						containerLayout.setWidth(75,Unit.PERCENTAGE);
						containerLayout.setSpacing(true);
							
						final Label titleLabel = new Label(PageContentElementEnum.LOGO.toString());
						titleLabel.setWidth(175,Unit.PIXELS);
							
						final HorizontalLayout horizontalContainerLayout = new HorizontalLayout();
						horizontalContainerLayout.addComponent(titleLabel);
						horizontalContainerLayout.setSpacing(true);
						horizontalContainerLayout.setMargin(true);
						
						fieldsComponentMap.put(this.LOGOLABEL, titleLabel);
							
						containerLayout.setSpacing(true);
						containerLayout.setHeight(200,Unit.PERCENTAGE);
						horizontalContainerLayout.addComponent(containerLayout);
							
						mainContentLayout.addComponent(horizontalContainerLayout);
				}
				createUIElement(containerLayout,elementFieldMap.get(PageContentElementEnum.LOGO.toString()),PageContentElementEnum.LOGO.toString(),elementWithValuesMap);
			}
		}
	
	private void createUIElement(final Layout containerLayout, final String fieldType,final String fieldName,final Map <String,Component> elementWithValuesMap){
		if (fieldType.equals(TEXTFIELD)){
			TextField component = (TextField)fieldsComponentMap.get(String.format(FIELDTYPE_FIELDNAME_FORMAT, fieldType,fieldName));
			
			if (null==component){
				final TextField textField = new TextField();
				textField.setCaption(fieldName);
				textField.setHeight(20,Unit.PIXELS);
				containerLayout.addComponent(textField);
				fieldsComponentMap.put(String.format(FIELDTYPE_FIELDNAME_FORMAT, fieldType,fieldName),textField);
			}
		}
		else if (fieldType.equals(IMAGESELECTOR)) {
			final Button component = (Button)fieldsComponentMap.get(String.format(FIELDTYPE_FIELDNAME_FORMAT, fieldType,fieldName));
			if (null==component){
				createContentPopulateButton("Populate using image",ContentTypeEnum.IMAGE,String.format(FIELDTYPE_FIELDNAME_FORMAT, fieldType,fieldName),null);
			}	
		}
	}
	
	private void createUIElement(final Layout containerLayout, final String fieldType,final String elementName,final String[] elementConfigArray,final Map <String,Component> elementWithValuesMap){
		
		if (fieldType.equals(CONTENTSELECTOR) && elementName.equalsIgnoreCase(PageContentElementEnum.NAVIGATION.toString())) {
			final String typeOfNavigationContent = elementConfigArray [0];
			final String count = elementConfigArray [1];
			
			
			Button component = null;
			if (typeOfNavigationContent.equals(PageContentElementEnum.PAGEBASED.toString())){
				component = (Button)fieldsComponentMap.get(String.format(FIELDTYPE_FIELDNAME_FORMAT, fieldType,elementName));
				component = createContentPopulateButton("Populate using site pages",ContentTypeEnum.PAGE,String.format(FIELDTYPE_FIELDNAME_FORMAT, fieldType,elementName),getThymeleafTag("navigation","pageIds",elementConfigArray));
			}
			else if (typeOfNavigationContent.equalsIgnoreCase(PageContentElementEnum.CATEGORYBASED.toString())) {
				component = (Button)fieldsComponentMap.get(String.format(FIELDTYPE_FIELDNAME_FORMAT, fieldType,elementName));
				if (null==component){
					component = (Button)fieldsComponentMap.get(String.format(FIELDTYPE_FIELDNAME_FORMAT, fieldType,elementName));
					component = createContentPopulateButton("Populate using category",ContentTypeEnum.CATEGORY,String.format(FIELDTYPE_FIELDNAME_FORMAT, fieldType,elementName),getThymeleafTag("navigation","categoryIds",elementConfigArray));
				}	
			}
			elementWithValuesMap.put(String.format(FIELDTYPE_FIELDNAME_FORMAT, fieldType,elementName), component);
		}
		else if (fieldType.equals(CONTENTSELECTOR) && elementName.equalsIgnoreCase(PageContentElementEnum.ARTICLE.toString())) {
			final String typeOfNavigationContent = elementConfigArray [0];
			final String count = elementConfigArray [1];
			if (typeOfNavigationContent.equalsIgnoreCase(PageContentElementEnum.ARTICLE.toString())) {
				final Button component = (Button)fieldsComponentMap.get(String.format(FIELDTYPE_FIELDNAME_FORMAT, fieldType,elementName));
				if (null==component){
					createContentPopulateButton("Populate using category",ContentTypeEnum.CATEGORY,String.format(FIELDTYPE_FIELDNAME_FORMAT, fieldType,elementName),getThymeleafTag("article","categoryIds",elementConfigArray));
					createContentPopulateButton("Populate using article",ContentTypeEnum.ARTICLE,String.format(FIELDTYPE_FIELDNAME_FORMAT, fieldType,elementName),getThymeleafTag("article","articleIds",elementConfigArray));
				}	
			}
		}
		else if (fieldType.equals(CONTENTSELECTOR) && elementName.equalsIgnoreCase(PageContentElementEnum.VIDEO.toString())) {
			final String typeOfNavigationContent = elementConfigArray [0];
			final String count = elementConfigArray [1];
			if (typeOfNavigationContent.equalsIgnoreCase(PageContentElementEnum.VIDEO.toString())) {
				final Button component = (Button)fieldsComponentMap.get(String.format(FIELDTYPE_FIELDNAME_FORMAT, fieldType,elementName));
				if (null==component){
					createContentPopulateButton("Populate using category",ContentTypeEnum.CATEGORY,String.format(FIELDTYPE_FIELDNAME_FORMAT, fieldType,elementName),getThymeleafTag("video","categoryIds",elementConfigArray));
					createContentPopulateButton("Populate using article",ContentTypeEnum.VIDEO,String.format(FIELDTYPE_FIELDNAME_FORMAT, fieldType,elementName),getThymeleafTag("video","videoIds",elementConfigArray));
				}	
			}
		}
		else if (fieldType.equals(CONTENTSELECTOR) && elementName.equalsIgnoreCase(PageContentElementEnum.IMAGE.toString())) {
			final String typeOfNavigationContent = elementConfigArray [0];
			final String count = elementConfigArray [1];
			if (typeOfNavigationContent.equalsIgnoreCase(PageContentElementEnum.IMAGE.toString())) {
				final Button component = (Button)fieldsComponentMap.get(String.format(FIELDTYPE_FIELDNAME_FORMAT, fieldType,elementName));
				if (null==component){
					createContentPopulateButton("Populate using category",ContentTypeEnum.CATEGORY,String.format(FIELDTYPE_FIELDNAME_FORMAT, fieldType,elementName),getThymeleafTag("image","categoryIds",elementConfigArray));
					createContentPopulateButton("Populate using image",ContentTypeEnum.IMAGE,String.format(FIELDTYPE_FIELDNAME_FORMAT, fieldType,elementName),getThymeleafTag("image","imageIds",elementConfigArray));
				}	
			}
		}
	}

	private Button createContentPopulateButton(final String buttonText,final ContentTypeEnum contentType,final String componentFieldMapping,final String thymeleafTagData) {
		final Button button = new Button (buttonText);
		button.setStyleName("link");
		//containerLayout.addComponent(contentPickerLayout);

		final VerticalLayout contentPickerLayout = new VerticalLayout ();
		final ContentPickerHelper contentPickerHelper = new ContentPickerHelper(context.getHelper());
	
		button.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(final ClickEvent event) {
				event.getButton().setData(thymeleafTagData);
				final GenricEntityPicker contentPicker = contentPickerHelper.prepareContentPickerData(contentType.toString(),contentPickerLayout,null,false,null,thymeleafTagData);			
				contentPicker.build(contentType);
			}
		});
		containerLayout.addComponent(button);
		fieldsComponentMap.put(componentFieldMapping,button);
		return button;
	}

	private String getThymeleafTag(final String tagName,final String contentIds, final String[] elementConfigArray){
		final String thymeleafTagKey = getIncludingTemplateKey(elementConfigArray);
		
		if (null==thymeleafTagKey){
			return null;
		}
		
		final String thymeleafTag = String.format("<simple:%s %s=\"@ids\" key=\"%s\">", tagName,contentIds,thymeleafTagKey);
		return thymeleafTag;
	}

	private String getIncludingTemplateKey(final String[] elementConfigArray){
		if (elementConfigArray.length>=2){
			final String includedTemplateConfigKey = elementConfigArray [2];
			return properties.getProperty(includedTemplateConfigKey); 
		}
		
		return null;
	}
}
