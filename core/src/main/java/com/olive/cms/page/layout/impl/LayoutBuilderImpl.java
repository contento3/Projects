package com.olive.cms.page.layout.impl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.olive.cms.page.layout.LayoutBuilder;
import com.olive.cms.page.layout.dto.PageLayoutDto;
import com.olive.cms.page.layout.service.PageLayoutTypeService;
import com.olive.cms.page.section.dto.PageSectionDto;
import com.olive.cms.page.section.dto.PageSectionTypeDto;
import com.olive.cms.page.section.model.PageSectionTypeEnum;
import com.olive.cms.page.section.service.PageSectionTypeService;

public class LayoutBuilderImpl implements LayoutBuilder  {
	
	private PageSectionTypeService pageSectionTypeService;
	
	Document doc;
	
	private String bodyWidthStyle = "doc";
	
	private String headerHeight = "50";
	private String footerHeight = "50";
	
	private boolean isHeader = false;
	private boolean isFooter = false;
	private boolean isLeftNav = false;
	private boolean isRightNav = false;
	
	public LayoutBuilderImpl(final PageSectionTypeService pageSectionTypeService){
		this.pageSectionTypeService = pageSectionTypeService;
		
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
	    DocumentBuilder docBuilder;
		try {
			docBuilder = dbfac.newDocumentBuilder();
		    doc = docBuilder.newDocument();
		    createHtmlHeader();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	public LayoutBuilderImpl(){
		this("doc","50","50");
	}

	public LayoutBuilderImpl(String bodyWidthStyle,String headerHeight,String footerHeight) {
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
	    DocumentBuilder docBuilder;
		try {
			docBuilder = dbfac.newDocumentBuilder();
		    doc = docBuilder.newDocument();
		    this.bodyWidthStyle = bodyWidthStyle;
		    this.headerHeight = headerHeight;
		    this.footerHeight = footerHeight;
		    createHtmlHeader();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Used to add create html header in the html for the layout
	 * This will create <html><head></head><body></body></html> 
	 */
    public void createHtmlHeader(){
    	Element htmlTag = doc.createElement("html");
    	Element headTag = doc.createElement("head");

    	appendDependencies(headTag);
    	Element bodyTag = doc.createElement("body");
    	
    	doc.appendChild(htmlTag);
    	htmlTag.appendChild(headTag);
    	htmlTag.appendChild(bodyTag);
    	
    	Element bodyMainDivTag = doc.createElement("div");
    	
    	bodyMainDivTag.setAttribute("id", this.bodyWidthStyle);
    	bodyMainDivTag.setIdAttribute("id", true);
    	bodyMainDivTag.setAttribute("class", "yui-t1");
    	bodyTag.appendChild(bodyMainDivTag);
    	
    	Element mainContentBody = doc.createElement("div");
    	mainContentBody.setAttribute("id", "bd");
    	mainContentBody.setIdAttribute("id", true);

    	bodyMainDivTag.appendChild(mainContentBody);
    }
    
    /**
     * Used to create header section
     */
    public void addHeader(){
    	if (null == doc.getElementById("hd")){
        	Node bodyMainDiv = getBodyMainDiv();

    		Element headerDiv = doc.createElement("div");
    		headerDiv.setAttribute("id", "hd");
    		headerDiv.setIdAttribute("id", true);
    		headerDiv.setTextContent("header");

    		bodyMainDiv.insertBefore(headerDiv,doc.getElementById("bd"));
        	isHeader = true;
    	}
    }
    
    /**
     * Returns the main root div from the body tag that contain all the dvis.
     * @return
     */
    private Node getBodyMainDiv(){
     return doc.getElementsByTagName("body").item(0).getFirstChild();	
    }
    
    /**
     * Used to create footer section
     */
    public void addFooter(){
    	if (null == doc.getElementById("ft")){
    		Node bodyMainDiv = getBodyMainDiv();

    		Element footerDiv = doc.createElement("div");
    		footerDiv.setAttribute("id", "ft");
    		footerDiv.setIdAttribute("id", true);
    		footerDiv.setTextContent("footer");

    		bodyMainDiv.insertBefore(footerDiv, null);
        	isFooter = true;

    	}
    }
    
    /**
     * Used to remove footer section
     */
    public void removeFooter(){
    	Element bodyMainDiv = doc.getElementById(this.bodyWidthStyle);
    	
    	if (null != doc.getElementById("ft")) {
    		bodyMainDiv.removeChild(doc.getElementById("ft"));
        	isHeader = false;
    	}
    }

    /**
     * Used to remove header
     */
    public void removeHeader(){
    	Element bodyMainDiv = doc.getElementById(bodyWidthStyle);
    	
    	if (null != doc.getElementById("hd")) {
    		bodyMainDiv.removeChild(doc.getElementById("hd"));
        	isHeader = false;
    	}
    }

    /**
     * Used to add left area (mostly used for putting navigation area)
     */
    public void addLeftArea(){}

    /**
     * Used to remove left area (mostly used for putting navigation area)
     * 
     */
    public void removeLeftArea(){}

    /**
     * Used to add right area
     */
    public void addRightArea(){}
    
    /**
     * Used to remove right navigational area
     */
    public void removeRightArea(){}
    
    /**
     * Used to add new body row
     * @param 
     */
    public void addNewBodyRow(String selectedColumn){
    	
    	Element element = doc.getElementById("bd");
    	Element newRow = doc.createElement("div");
    	element.appendChild(newRow);
    	
    	if (selectedColumn.equals("1 column - 100%")){
    		newRow.setAttribute("class","yui-g");
    		addDivLoremIPsumText(newRow);
    	}
    	if (selectedColumn.equals("2 column - 50/50%")){
    		newRow.setAttribute("class","yui-g");
    		addBodyTwoColumn(newRow);
    	}
    	if (selectedColumn.equals("2 column - 66/33%")){
    		newRow.setAttribute("class","yui-gc");
    		addBodyTwoColumn(newRow);
    	}
    	if (selectedColumn.equals("2 column - 33/66%")){
    		newRow.setAttribute("class","yui-gd");
    		addBodyTwoColumn(newRow);
    	}
    	if (selectedColumn.equals("2 column - 75/25%")){
    		newRow.setAttribute("class","yui-ge");
    		addBodyTwoColumn(newRow);
    	}
    	if (selectedColumn.equals("2 column - 25/75%")){
    		newRow.setAttribute("class","yui-gf");
    		addBodyTwoColumn(newRow);
    	}
    	if (selectedColumn.equals("3 column - 33/33/33%")){
    		newRow.setAttribute("class","yui-gb");
    		addBodyThreeColumn(newRow);
    	}
    	if (selectedColumn.equals("3 column - 50/25/25%")){
    		newRow.setAttribute("class","yui-g");

    		Element newRow_yui_u_first = doc.createElement("div");
        	newRow_yui_u_first.setAttribute("class","yui-u first");
        	newRow.appendChild(newRow_yui_u_first);
    		addDivLoremIPsumText(newRow_yui_u_first);

        	
    		Element newRow_yui_g = doc.createElement("div");
    		newRow_yui_g.setAttribute("class","yui-g");
        	newRow.appendChild(newRow_yui_g);
    		addBodyTwoColumn(newRow_yui_g);
    	}
    	if (selectedColumn.equals("3 column - 25/25/50%")){
    		newRow.setAttribute("class","yui-g");

    		Element newRow_yui_g = doc.createElement("div");
    		newRow_yui_g.setAttribute("class","yui-g first");
        	newRow.appendChild(newRow_yui_g);
    		addBodyTwoColumn(newRow_yui_g);
	
    		Element newRow_yui_u_first = doc.createElement("div");
        	newRow_yui_u_first.setAttribute("class","yui-u");
        	newRow.appendChild(newRow_yui_u_first);
        	addDivLoremIPsumText(newRow_yui_u_first);
    	}
    	if (selectedColumn.equals("4 column - 25/25/25/25%")){
    		newRow.setAttribute("class","yui-g");
    		
        	Element newRow_yui_g_first = doc.createElement("div");
        	newRow_yui_g_first.setAttribute("class","yui-g first");
        	newRow.appendChild(newRow_yui_g_first);
    		addBodyTwoColumn(newRow_yui_g_first);

    		
        	Element newRow_yui_g = doc.createElement("div");
        	newRow_yui_g.setAttribute("class","yui-g");
        	newRow.appendChild(newRow_yui_g);
    		addBodyTwoColumn(newRow_yui_g);
    	}
    }
    
    
    /**
     * Used to new add body column
     */
    public void addBodyTwoColumn(Element newRow){
    	Element yui_u_first = doc.createElement("div");
    	yui_u_first.setAttribute("class", "yui-u first");
    	newRow.appendChild(yui_u_first);
    	addDivLoremIPsumText(yui_u_first);
    	
    	Element yui_u = doc.createElement("div");
    	yui_u.setAttribute("class", "yui-u");
    	newRow.appendChild(yui_u);
    	addDivLoremIPsumText(yui_u);
    }
    
    /**
     * Used to new add body column
     */
    public void addBodyThreeColumn(Element newRow){
    	Element yui_u_first = doc.createElement("div");
    	yui_u_first.setAttribute("class", "yui-u first");
    	newRow.appendChild(yui_u_first);
    	addDivLoremIPsumText(yui_u_first);

    	Element yui_u = doc.createElement("div");
    	yui_u.setAttribute("class", "yui-u");
    	newRow.appendChild(yui_u);
    	addDivLoremIPsumText(yui_u);
    	
    	Element yui_u_2 = doc.createElement("div");
    	yui_u_2.setAttribute("class", "yui-u");
    	newRow.appendChild(yui_u_2);
    	addDivLoremIPsumText(yui_u_2);
    }    

    /**
     * Used to remove new body row
     */
    public void removeNewBodyRow(){}
    
    /**
     * Used to remove new body column
     */
    public void removeNewBodyColumn(){}
    
    
    private void appendDependencies(Element headTag){
    	Element styleTag = doc.createElement("style");
    	styleTag.setAttribute("type", "text/css");
    	
    	StringBuilder builder = new StringBuilder();
    	builder.append("/*margin and padding on body element");
    	builder.append("can introduce errors in determining");
    	builder.append("element position and are not recommended;");
    	builder.append("we turn them off as a foundation for YUI");
    	builder.append("CSS treatments. */");
    	builder.append("body {margin:0;padding:0;}");
    	builder.append("#hd, .yui-layout-unit-top {background-color: blue;color: white;}");
    	builder.append("#bd, .yui-layout-unit-center {background-color: #f2f2f2;}");
    	builder.append("#ft, .yui-layout-unit-bottom {background-color: red; color: #fff;}");
    	builder.append("#nav, .yui-layout-unit-left {background-color: #808080;}");
    	styleTag.setTextContent(builder.toString());
    	headTag.appendChild(styleTag);

    	Element linkTag = doc.createElement("link");
    	linkTag.setAttribute("rel", "stylesheet");
    	linkTag.setAttribute("type", "text/css");
    	linkTag.setAttribute("href", "http://yui.yahooapis.com/combo?2.9.0/build/fonts/fonts-min.css&2.9.0/build/grids/grids-min.css&2.9.0/build/layout/assets/layout-core.css");
    	
    	headTag.appendChild(linkTag);
 
       	Element scriptTag = doc.createElement("script");
       	scriptTag.setAttribute("type", "text/javascript");
       	scriptTag.setAttribute("src", "http://yui.yahooapis.com/combo?2.9.0/build/yahoo-dom-event/yahoo-dom-event.js&2.9.0/build/element/element-min.js&2.9.0/build/selector/selector-min.js&2.9.0/build/layout/layout-min.js");
    	headTag.appendChild(scriptTag);

       	Element scriptTwoTag = doc.createElement("script");
       	StringBuilder scriptTagBuilder = new StringBuilder();
       	scriptTagBuilder.append("(function() { var Dom = YAHOO.util.Dom,Event = YAHOO.util.Event;Event.onDOMReady(function() {");
       	scriptTagBuilder.append("var layout = new YAHOO.widget.Layout(\'"+bodyWidthStyle+"\', { height: Dom.getClientHeight(), ");
       	scriptTagBuilder.append("width: Dom.get(\'"+bodyWidthStyle+"\').offsetWidth, minHeight: 150,");
       	scriptTagBuilder.append("units: [");
       	scriptTagBuilder.append("{ position: 'top', height:"+this.headerHeight+", body: 'hd' },");
       	scriptTagBuilder.append("{ position: 'bottom', height:"+this.footerHeight+", body: 'ft' },");
       	scriptTagBuilder.append("{ position: 'center', body: 'bd', grids: true }]}); layout.on('beforeResize', function() {");
       	scriptTagBuilder.append("Dom.setStyle(\'"+bodyWidthStyle+"\', 'height', Dom.getClientHeight() + 'px'); });layout.render();");
       	scriptTagBuilder.append(" Event.on(window, 'resize', layout.resize, layout, true);");
       	scriptTagBuilder.append("});})();");

       	scriptTwoTag.setTextContent(scriptTagBuilder.toString());
    	headTag.appendChild(scriptTwoTag);
    	}
    
    private String appendLayoutScript(){
    	StringBuilder builder = new StringBuilder();
    	builder.append("<script>");
    	builder.append("(function() {");
    	builder.append("var Dom = YAHOO.util.Dom,");
    	builder.append("Event = YAHOO.util.Event;");
    	builder.append("Event.onDOMReady(function() {");
    	builder.append("var layout = new YAHOO.widget.Layout(\'"+this.bodyWidthStyle+"\', {");
    	builder.append("height: Dom.getClientHeight(), //Height of the viewport");
    	builder.append("width: Dom.get(\'"+this.bodyWidthStyle+"\').offsetWidth, //Width of the outer element");
    	builder.append("minHeight: 150, //So it doesn't get too small");
    	builder.append("units: [");
    	builder.append("{ position: 'top', height: 95, body: 'hd' },");
    	builder.append("{ position: 'left', width: 160, body: 'nav', grids: true },");
    	builder.append("{ position: 'bottom', height: 25, body: 'ft' },");
    	builder.append("{ position: 'center', body: 'bd', grids: true }");
    	builder.append("]");
    	builder.append("});");
    	builder.append("layout.on('beforeResize', function() {");
    	builder.append("Dom.setStyle(\'"+this.bodyWidthStyle+"\', 'height', Dom.getClientHeight() + 'px');");
    	builder.append("});");
    	builder.append("layout.render();");
    	builder.append("//Handle the resizing of the window");
    	builder.append("Event.on(window, 'resize', layout.resize, layout, true);");
    	builder.append("});");
    	builder.append("})();");
    	builder.append("</script>");
    	
    	return builder.toString();
    }
    
    /**
     * Used to convert from page layout html to page layout dto.
     * @return
     */
    public Collection<PageSectionDto> convertHtmlToDto(){

    	Collection <PageSectionDto> pageSectionDtoList = new ArrayList<PageSectionDto>(); 
    	
    	String html = this.getLayoutHTML();
    	
    	//Add the header if selected in the UI
    	Element header = doc.getElementById("hd");
    	if (null != header){
			PageSectionTypeDto pageSectionTypeDto  = pageSectionTypeService.findByName(PageSectionTypeEnum.HEADER);
	    	PageSectionDto headerSection = new PageSectionDto();

    		headerSection = new PageSectionDto();
    		headerSection.setName("page header");
    		headerSection.setDescription("Page header for this layout");
    		headerSection.setTemplateMarkup("<div id=\"hd\">header</div>");
    		headerSection.setSectionTypeDto(pageSectionTypeDto);
    		
    		pageSectionDtoList.add(headerSection);
    	}

    	//Add the footer if selected in the UI
    	Element footer = doc.getElementById("ft");

    	if (null != footer){
			PageSectionTypeDto pageSectionTypeDto  = pageSectionTypeService.findByName(PageSectionTypeEnum.FOOTER);
	    	PageSectionDto footerSection = new PageSectionDto();

	    	footerSection = new PageSectionDto();
	    	footerSection.setName("page footer");
	    	footerSection.setDescription("Page footer for this layout");
	    	footerSection.setTemplateMarkup("<div id=\"ft\">footer</div>");
	    	footerSection.setSectionTypeDto(pageSectionTypeDto);
    		
    		pageSectionDtoList.add(footerSection);
    	}

    	//Add the body if selected in the UI
    	Element body = doc.getElementById("bd");
    
    	if (null != body){
			PageSectionTypeDto pageSectionTypeDto  = pageSectionTypeService.findByName(PageSectionTypeEnum.BODY);
	    	PageSectionDto bodySection = new PageSectionDto();

	    	bodySection = new PageSectionDto();
	    	bodySection.setName("page body");
	    	bodySection.setDescription("Page body for this layout");
	    	bodySection.setSectionTypeDto(pageSectionTypeDto);
	    	
	    	if (null == footer){
	    		bodySection.setTemplateMarkup(html.substring(html.indexOf("<div id=\"bd\">")));
	    	}
	    	else {
	    		bodySection.setTemplateMarkup(html.substring(html.indexOf("<div id=\"bd\">"), html.indexOf("<div id=\"ft\">")));	
	    	}
    		pageSectionDtoList.add(bodySection);
    	}

//    	System.out.print(html);
    	return pageSectionDtoList;
    }

    /**
     * Used to convert from page layout html to page layout dto.
     * @return
     */
    public String convertDtoToHtml(PageLayoutDto pageLayoutDto){
    	return "";
    }
   
    public String getLayoutHTML(){
    	
    	try {
	    	TransformerFactory factory = TransformerFactory.newInstance();
	    	Transformer transformer = factory.newTransformer();
	    	transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    	StringWriter writer = new StringWriter();
	    	Result result = new StreamResult(writer);
	    	Source source = new DOMSource(doc);
	    	transformer.transform(source, result);
	    	writer.close();
	    	return appendDocType()+writer.toString();
	    }
    	catch(Exception e){
    		return null;
    	}
    	
    }

    private void addDivLoremIPsumText(Element div){
    	StringBuilder builder = new StringBuilder();
    	builder.append("Donec nec leo et dui adipiscing sodales in vel dui.");
    	builder.append("Aliquam sodales imperdiet diam, et suscipit velit luctus sed.");
    	builder.append("Cras vitae purus in odio egestas mollis et id nunc.");
    	builder.append("Etiam vitae libero erat, a laoreet risus. Vestibulum pulvinar libero non ipsum iaculis ut ornare neque sollicitudin.");
    	builder.append("Quisque ultricies eros sit amet diam congue posuere. Nulla commodo dui ultrices nibh semper quis tristique nisi faucibus.");

    	div.setTextContent(builder.toString());
    }
    
    public void addBodyWidth(String widthStyle){
    	this.bodyWidthStyle = widthStyle;
    	Element node =(Element) doc.getElementsByTagName("body").item(0).getFirstChild();
    	node.setAttribute("id", widthStyle);
    }

    private String appendDocType(){
    	return "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">"; 
    }

	public String getBodyWidthStyle() {
		return bodyWidthStyle;
	}

	public void setBodyWidthStyle(String bodyWidthStyle) {
		this.bodyWidthStyle = bodyWidthStyle;
	}

	public String getHeaderHeight() {
		return headerHeight;
	}

	public void setHeaderHeight(String headerHeight) {
		this.headerHeight = headerHeight;
	}

	public String getFooterHeight() {
		return footerHeight;
	}

	public void setFooterHeight(String footerHeight) {
		this.footerHeight = footerHeight;
	}
}