package com.contento3.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class is used as a helper class for xml processes that canbe shared
 * amongst all of the other packages.
 */
public class XMLUtils {
  
  //
  // Some commonly used xml tag names, attribute names, etc....
  //
  public static final String ID_ATTRIBUTE = "id";
  public static final String TYPE_ATTRIBUTE = "type";
  
  /**
   * The XML string to place at the head of an XML file.
   */
  public static final String XML_MARKER =
    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
  
  /**
   * Write an XML document to disk.
   * 
   * <p>This will initially attempt to write the content of the supplied
   * {@link Document} to a temporary file on disk. If the write occurred without
   * error, then the complete file will be renamed to the requested filename.</p>
   * 
   * @param transformer The {@link Transformer} to use when rendering the
   *            <tt>xmlDoc</tt> for writing to disk.
   * @param xmlDoc The {@link Document} to write to a file on disk.
   * @param rootPath The root path for the document.
   * @param fileName The name to use for the final, complete written, file.
   * @param charset The character set (ie "UTF-8") to use for writing the file.
   * 
   * @throws IllegalArgumentException If any of the parameters are null.
   * @throws IOException If a problem occurred writing the document to file.
   * @throws TransformerException If a problem occurred transforming the source
   *             {@link Document} for writing to the output stream.
   */
  public static void writeXmlDocument(Transformer transformer,
                                      Document xmlDoc,
                                      String rootPath,
                                      String fileName,
                                      String charset)
  throws IOException,
          TransformerException {
    Validate.notNull(transformer, "transformer cannot be null");
    Validate.notNull(xmlDoc, "xmlDoc cannot be null");
    Validate.notEmpty(rootPath, "rootPath cannot be empty");
    Validate.notEmpty(fileName, "fileName cannot be empty");
    
    boolean moveLive = false;
    
 //   TempFile xmlFile = new TempFile(rootPath, fileName, charset);
    
//    try {
//      Source xmlSource = new DOMSource(xmlDoc);
//      Result xmlResult = new StreamResult(xmlFile.getWriter());
//      transformer.transform(xmlSource, xmlResult);
//      moveLive = true;
//    } finally {
//      if(xmlFile != null) {
//        xmlFile.close(moveLive);
//      }
//    }
  }
  
  /**
   * Converts an xml document into a file
   * 
   * @param filename the fully qualified filename (including folders)
   *                 e.g. C:\\temp\\xml\\anxmlfile.xml
   * @param document the xml document
   * @return the file
   * @throws Exception
   */
  public static File convertXmlDocToFile(String filename, Document document) throws Exception {
    
    File file = null;
    OutputStreamWriter fileWriter = null;
    
    try {
      
      file = new File(filename);
      fileWriter = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
      
      Source src = new DOMSource(document);
      StreamResult fileResult = new StreamResult(fileWriter);
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer transformer = tf.newTransformer();
      transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
      transformer.transform(src, fileResult);
      
    } finally {
      if(fileWriter != null) {
        fileWriter.close();
      }
    }
    
    return file;
  }
  
  /**
   * Converts a xml document into a string.
   * 
   * @param document the document
   * @return a string version of the xml
   * @throws Exception
   */
  public static String convertXmlDocToString(Document document,
                                             boolean omitXmlDeclaration)
  throws Exception {
    
    StringWriter stringWriter = new StringWriter();
    Source src = new DOMSource(document);      
    StreamResult stringResult = new StreamResult(stringWriter);
    TransformerFactory tf = TransformerFactory.newInstance();
    Transformer transformer = tf.newTransformer();
    if(omitXmlDeclaration) {
      transformer.setOutputProperty("omit-xml-declaration", "yes");
    }
    transformer.transform(src, stringResult);

    return stringWriter.toString();
  }
  
  /**
   * Converts a xml document into a string.
   * 
   * @param document the document
   * @return a string version of the xml
   * @throws Exception
   */
  public static String convertXmlDocToString(Document document)
  throws Exception {
    return convertXmlDocToString(document, false);
  }  

  /**
   * Appends the XML text for a new tag with the specified name and
   * attributes to the specified StringBuffer instance.
   *
   * @param sb StringBuffer instance to append the XML text to.
   * @param tagName Name of the new XML tag.
   * @param attributes Arrays with the names and values of
   *        the attributes for the XML tag.
   * @param closeTag if set to true then closes the tag, otherwise leave it open
   * 
   */
  public static void addXMLTag(StringBuffer sb,
                               String tagName,
                               String[][] attributes,
                               boolean closeTag) {

    sb.append(getTagString(tagName, attributes, closeTag));
  }
  
  
  /**
   * Does  the same as  
   * addXMLTag(StringBuffer sb, String tagName, String[][] attributes, boolean closeTag)
   * but using a string builder.
   * @param sb
   * @param tagName 
   * @param attributes
   * @param closeTag
   */
  public static void addXMLTag(StringBuilder sb, String tagName,
      String[][] attributes, boolean closeTag) {
    sb.append(getTagString(tagName, attributes, closeTag));
  }
  
  /**
   * Returns an XML tag based on teh supplied arguments
   * @param tagName Name of the new XML tag.
   * @param attributes Arrays with the names and values of
   *        the attributes for the XML tag.
   * @param closeTag if set to true then closes the tag, otherwise leave it open
   * @return
   */
  private static String getTagString(String tagName,
      String[][] attributes, boolean closeTag) {
    StringBuilder builder = new StringBuilder();
    builder.append("<" + tagName + " ");
    if (attributes != null) {
      for (int i = 0; i < attributes.length; i++) {
        builder.append(" " + attributes[i][0] + "=\"" + attributes[i][1] + "\" ");
      }
    }
    builder.append((closeTag ? " />" : " >"));
    
    return builder.toString();
  }

  /**
   * There are some characters that cannot be used as such in
   * xml specifications. This method looks up for those characters
   * in the string specified and replaces them by the right escaped string.
   *
   * @param s String to escape.
   *
   * @return escaped string.
   */
  public static String escapeIllegalCharacters(String xmlValue) {
    if (xmlValue != null) {
      //
      // first we need to unescape any already escaped characters
      // this is so that "&amp;" does not end up as "&amp;amp;" etc....
      //
      xmlValue = xmlValue.replaceAll("&amp;", "&");
      xmlValue = xmlValue.replaceAll("&lt;", "<");
      xmlValue = xmlValue.replaceAll("&gt;", ">");
      xmlValue = xmlValue.replaceAll("&#39;", "\'");
      xmlValue = xmlValue.replaceAll("&quot;", "\"");
      xmlValue = xmlValue.replaceAll("&#36;", "$");
      xmlValue = xmlValue.replaceAll("&#163;", "£");
      
      //
      // now we escape them all again
      //
      xmlValue = escape(xmlValue, '&', "&amp;");
      xmlValue = escape(xmlValue, '<', "&lt;");
      xmlValue = escape(xmlValue, '>', "&gt;");
      xmlValue = escape(xmlValue, '\'', "&#39;");
      xmlValue = escape(xmlValue, '\"', "&quot;");
      xmlValue = escape(xmlValue, '$', "&#36;");
      xmlValue = escape(xmlValue, '£', "&#163;");
    }
    return xmlValue;
  }

  /**
   * Escape that bad character by replacing it with the given
   * alternative string.
   *
   * @param xmlValue the xml that you are checking
   * @param badChar the bad charachter that needs replacing
   * @param strReplace the string to replace the bad character with.
   * @return the xml without any bad characters
   */
  public static String escape(String xmlValue, char badChar, String strReplace) {
    //Check whether we have at least one occurrence
    int index = xmlValue.indexOf(badChar);
    if (index == -1)
      return xmlValue;

    //Replace each bad char starting from first occurrence
    StringBuffer sb = new StringBuffer(xmlValue.substring(0, index));

    for (int i = index; i < xmlValue.length(); i++) {
      char c = xmlValue.charAt(i);
      if (c == badChar)
        sb.append(strReplace);
      else
        sb.append(c);
    }

    return sb.toString();
  }
  
  /**
   * For any one given xml Node, this will retrieve the values of all the
   * text nodes (of type TEXT_NODE) and concatonate them into one long string
   * which it then returns. If there are child Nodes of type ELEMENT_NODE then
   * this will retrieve all the TEXT_NODE children from them too, and so on
   * (recurssion down a tree structure until all TEXT_NODE's have been
   * concatonated together).
   * @param node an xml node of type ELEMENT.
   * @param buffer buffer that will hold all of the TEXT_NODE values.
   * @param logger error logger.
   */
  public static void getTextContents(Node node, StringBuffer buffer, Logger logger) {

    NodeList childNodes = node.getChildNodes();

    for (int ii = 0; ii < childNodes.getLength(); ++ii) {

      if (Node.TEXT_NODE == childNodes.item(ii).getNodeType()) {

        buffer.append(childNodes.item(ii).getNodeValue());

      } else if (Node.ELEMENT_NODE == childNodes.item(ii).getNodeType()) {

        getTextContents(childNodes.item(ii), buffer, logger);

      } else {

        logger.log(
          Level.WARN,
          "We are trying to get all the Nodes of Type TEXT from inside the " +
          "'TextFeed' Node, but we have come accross a type of Node that we " +
          "didn't recognise (it was neither an ELEMENT_NODE or TEXT_NODE)."
        );
      }
    }
  }
  
  /**
   * For any one given xml Node, this will retrieve the contents of one
   * child Text node. This is for use with ONLY very simple Nodes, an example
   * of which would be:<br>
   * <ElementNode><br>
   *   <ChildElementNode attributeOne="1"/><br>
   *   <ChildTextNode> Some Text </TextNode><br>
   * <ElementNode><br>
   * ie where this is only ONE ChildTextNode present.
   * @param node an xml node of type ELEMENT.
   * @param logger error logger.
   * @return String contents of the text node.
   */
  public static String extractNodeText(Node node) {
    String value = null;
    NodeList childNodes = node.getChildNodes();
    for (int ii = 0; ii < childNodes.getLength(); ++ii) {
      if (Node.TEXT_NODE == childNodes.item(ii).getNodeType()) {
        value = childNodes.item(ii).getNodeValue();
        break;
      }
    }
    return value;
  }
  
  /**
   * Append a single attribute value to an XML string builder.
   * 
   * @param xmlBuilder The {@link StringBuilder} to append the attribute details
   *            to.
   * @param attrName The name of the attribute.
   * @param attrValue The attribute value (can be null or empty).
   * 
   * @return The updated <tt>StringBuilder</tt>
   */
  public static StringBuilder appendAttribute(StringBuilder xmlBuilder,
                                              String attrName,
                                              String attrValue) {
    Validate.notEmpty(attrName, "attrName cannot be null or empty");
    
    xmlBuilder.append(' ').append(attrName).append("=\"");
    if(!StringUtils.isEmpty(attrValue)) {
      xmlBuilder.append(StringEscapeUtils.escapeJavaScript(attrValue));
    }
    xmlBuilder.append('"');
    
    return xmlBuilder;
  }
  
  /**
   * Append an array of attribute values to an XML string builder.
   * 
   * @param xmlBuilder The {@link StringBuilder} to append the attribute details
   *            to.
   * @param attributes The array of attribute names and values.
   * 
   * @return The updated <tt>StringBuilder</tt>
   */
  public static StringBuilder appendAttributes(StringBuilder xmlBuilder,
                                               String[][] attributes) {
    
//    if(!ArrayUtils.isEmpty(attributes)) {
//      for(String[] attr: attributes) {
//        if(!ArrayUtils.isEmpty(attr)) {
//          String attrName = attr[0];
//          String attrValue = (attr.length >= 2) ? attr[1] : null;
//          
//          appendAttribute(xmlBuilder, attrName, attrValue);
//        }
//      }
//    }
//    
    return xmlBuilder;
  }
  
  /**
   * Append a map of attribute name/value pairs to an XML string builder.
   * 
   * @param xmlBuilder The {@link StringBuilder} to append the attribute details
   *            to.
   * @param attributes The {@link Map} of attribute names and values.
   * 
   * @return The updated <tt>StringBuilder</tt>
   */
  public static StringBuilder appendAttributes(StringBuilder xmlBuilder,
                                               Map<String,String> attributes) {
    if((attributes != null) && (attributes.size() > 0)) {
      for(Map.Entry<String,String> attr: attributes.entrySet()) {
        appendAttribute(xmlBuilder, attr.getKey(), attr.getValue());
      }
    }
    return xmlBuilder;
  }
  
  /**
   * Append a simple XML element to a string builder.
   * 
   * @param xmlBuilder The {@link StringBuilder} to update.
   * @param elementName The name of the element.
   * @param attributes A {@link Map} of name/value attribute pairs.
   * @param cdata Set to <code>true</code> if the value should be CDATA wrapped.
   * @param value The value for the element.
   * 
   * @return The updated <tt>xmlBuilder</tt>
   */
  public static StringBuilder appendElement(StringBuilder xmlBuilder,
                                            String elementName,
                                            Map<String,String> attributes,
                                            boolean cdata,
                                            String value) {
    
    xmlBuilder.append('<').append(elementName);
    
    // Append element attributes if available
    appendAttributes(xmlBuilder, attributes);
    
    if(!StringUtils.isEmpty(value)) {
      xmlBuilder.append(">");
      if(cdata) { xmlBuilder.append("<![CDATA["); }
      xmlBuilder.append(value);
      if(cdata) { xmlBuilder.append("]]>"); }
      xmlBuilder.append("</").append(elementName).append(">\n");
    } else {
      xmlBuilder.append("/>\n");
    }
    
    return xmlBuilder;
  }
  
  /**
   * Append a simple XML element to a string builder.
   * 
   * @param xmlBuilder The {@link StringBuilder} to update.
   * @param cdata Set to <code>true</code> if the value should be CDATA wrapped.
   * @param element The name of the element.
   * @param value The value for the element.
   * 
   * @return The updated <tt>xmlBuilder</tt>
   */
  public static StringBuilder appendElement(StringBuilder xmlBuilder,
                                            String element,
                                            boolean cdata,
                                            String value) {
    return appendElement(xmlBuilder, element, null, cdata, value);
  }
  
  /**
   * Append a date based XML element to the string builder.
   * 
   * @param xmlBuilder The {@link StringBuilder} to update.
   * @param element The name of the element.
   * @param date The date to append.
   * 
   * @return The updated <tt>xmlBuilder</tt>
   */
  public static StringBuilder appendElement(StringBuilder xmlBuilder,
                                            String element,
                                            boolean includeTime,
                                            Date date) {
    
//    if(date != null) {
//      Map<String,String> attrs = new LinkedHashMap<String, String>();
//      Calendar cal = Calendar.getInstance();
//      cal.setTime(date);
//      
//      attrs.put("year", Integer.toString(cal.get(Calendar.YEAR)));
//      attrs.put("month", Integer.toString(cal.get(Calendar.MONTH) + 1));
//      attrs.put("day", Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
//      
//      if(includeTime) {
//        attrs.put("hour", Integer.toString(cal.get(Calendar.HOUR_OF_DAY)));
//        attrs.put("minute", Integer.toString(cal.get(Calendar.MINUTE)));
//        attrs.put("second", Integer.toString(cal.get(Calendar.SECOND)));
//        attrs.put("millisecond", Integer.toString(cal.get(Calendar.MILLISECOND)));
//      }
      
//      String dateFormat = includeTime
//              ? DateTimeUtils.ISO_DATE_FULL_FORMAT
//              : DateTimeUtils.ISO_DATE_FORMAT;
//      
//      String dateString = DateTimeUtils.formatDate(dateFormat, date);
//      appendElement(xmlBuilder, element, attrs, true, dateString);
//    } else {
//      // Default to an empty element.
//      appendElement(xmlBuilder, element, false, (String)null);
//    }
//    
    return null;
  }
  
  /**
   * Add a collection of attributes to the specified element.
   *
   * @param element The {@link Element} to set the attribute values for.
   * @param attributes The {@link Map} of attribute names and values.
   * 
   * @return The updated <tt>Element</tt>
   */
  public static Element appendAttributes(Element element,
                                         Map<String,String> attributes) {
    // Ensure required parameters are set
    Validate.notNull(element, "element cannot be null");
    
    if((attributes != null) && (attributes.size() > 0)) {
      for(Map.Entry<String,String> attr: attributes.entrySet()) {
        
        if(StringUtils.isNotEmpty(attr.getKey())) {
          element.setAttribute(attr.getKey(), attr.getValue());
        } else {
          throw new IllegalArgumentException(
              "attributes contains an entry with a null key");
        }
      }
    }
    
    return element;
  }
  
  /**
   * Create a new element for use in an XML {@link Document}.
   * 
   * <p>This will not automatically append the new element to the supplied
   * <tt>xmlDocument</tt> object.
   * 
   * @param xmlDocument The {@link Document} the element 
   * @param elementName The name of the element.
   * @param attributes A {@link Map} of name/value attribute pairs.
   * @param cdata Set to <code>true</code> if the value should be CDATA wrapped.
   * @param value The simple text value to set as the default content of the
   *            {@link Element}
   * 
   * @return The newly created {@link Element}.
   */
  public static Element createElement(Document ownerDoc,
                                      String elementName,
                                      Map<String,String> attributes,
                                      boolean cdata,
                                      String value) {
    // ownerDoc and elementName are mandatory
    Validate.notNull(ownerDoc, "ownerDoc cannot be null");
    Validate.notEmpty(elementName, "elementName cannot be an empty String");
    
    // Create our element and append any required attributes.
    Element element = ownerDoc.createElement(elementName);
    appendAttributes(element, attributes);
    
    // Append the node content, if available.
    if(StringUtils.isNotEmpty(value)) {
      Node nodeValue = null;
      
      if(cdata) {
        nodeValue = ownerDoc.createCDATASection(value);
      } else {
        nodeValue = ownerDoc.createTextNode(value);
      }
      
      element.appendChild(nodeValue);
    }
    
    return element;
  }
  
  /**
   * Get the {@link Document} this {@link Node} represents or, if it is an
   * {@link Element}, get it's owning {@link Document}.
   * 
   * @param node The {@link Node} to get the related {@link Document} for.
   * 
   * @return The {@link Document} relating to the specified {@link Node}.
   */
  public static Document getDocumentFromNode(Node node) {
    // Make sure node is not null and is either a Document or Element instance
    Validate.notNull(node, "node must not be null");
    Validate.isTrue((node instanceof Document) || (node instanceof Element),
        "node must be an instance of Document or Element");
    
    Document ownerDoc;
    
    // Get the owner Document
    if(node instanceof Element) {
      Validate.notNull(node.getOwnerDocument(),
          "parent must have an owner Document if it's an Element instance");
      ownerDoc = node.getOwnerDocument();
    } else {
      ownerDoc = (Document)node;
    }
    
    return ownerDoc;
  }
  
  /**
   * Create a new element for use in an XML {@link Document} and automatically
   * append it to the parent {@link Node}.
   * 
   * @param parent The {@link Node} that will be the parent of this element.
   *            This should be an instance of either {@link Document} or
   *            {@link Element}. 
   * @param elementName The name of the element.
   * @param attributes A {@link Map} of name/value attribute pairs.
   * @param cdata Set to <code>true</code> if the value should be CDATA wrapped.
   * @param value The simple text value to set as the default content of the
   *            {@link Element}
   * 
   * @return The newly created {@link Element}.
   */
  public static Element appendElement(Node parent,
                                      String elementName,
                                      Map<String,String> attributes,
                                      boolean cdata,
                                      String value) {
    // Get the new element and append it to the parent
    Element element = createElement(getDocumentFromNode(parent), elementName,
        attributes, cdata, value);
    parent.appendChild(element);
    
    return element;
  }
  
  /**
   * Create a new element for use in an XML {@link Document}.
   * 
   * <p>This will not automatically append the new element to the supplied
   * <tt>xmlDocument</tt> object.
   * 
   * @param xmlDocument The {@link Document} the element 
   * @param elementName The name of the element.
   * @param attributes A {@link Map} of name/value attribute pairs.
   * 
   * @return The newly created {@link Element}.
   */
  public static Element createElement(Document xmlDocument,
                                      String elementName,
                                      Map<String,String> attributes) {
    return createElement(xmlDocument, elementName, attributes, false, null);
  }
  
  /**
   * Create a new element for use in an XML {@link Document} and automatically
   * append it to the parent {@link Node}.
   * 
   * @param parent The {@link Node} that will be the parent of this element.
   *            This should be an instance of either {@link Document} or
   *            {@link Element}. 
   * @param elementName The name of the element.
   * @param attributes A {@link Map} of name/value attribute pairs.
   * 
   * @return The newly created {@link Element}.
   */
  public static Element appendElement(Node parent,
                                      String elementName,
                                      Map<String,String> attributes) {
    return appendElement(parent, elementName, attributes, false, null);
  }
  
  /**
   * Create a new element for use in an XML {@link Document}.
   * 
   * <p>This will not automatically append the new element to the supplied
   * <tt>xmlDocument</tt> object.
   * 
   * @param xmlDocument The {@link Document} the element 
   * @param elementName The name of the element.
   * @param cdata Set to <code>true</code> if the value should be CDATA wrapped.
   * @param value The simple text value to set as the default content of the
   *            {@link Element}
   * 
   * @return The newly created {@link Element}.
   */
  public static Element createElement(Document xmlDocument,
                                      String elementName,
                                      boolean cdata,
                                      String value) {
    return createElement(xmlDocument, elementName, null, cdata, value);
  }
  
  /**
   * Create a new element for use in an XML {@link Document} and automatically
   * append it to the parent {@link Node}.
   * 
   * @param parent The {@link Node} that will be the parent of this element.
   *            This should be an instance of either {@link Document} or
   *            {@link Element}. 
   * @param elementName The name of the element.
   * @param cdata Set to <code>true</code> if the value should be CDATA wrapped.
   * @param value The simple text value to set as the default content of the
   *            {@link Element}
   * 
   * @return The newly created {@link Element}.
   */
  public static Element appendElement(Node parent,
                                      String elementName,
                                      boolean cdata,
                                      String value) {
    return appendElement(parent, elementName, null, cdata, value);
  }
  
  /**
   * Create a new element for use in an XML {@link Document}.
   * 
   * <p>This will not automatically append the new element to the supplied
   * <tt>xmlDocument</tt> object.
   * 
   * @param xmlDocument The {@link Document} the element 
   * @param elementName The name of the element.
   * 
   * @return The newly created {@link Element}.
   */
  public static Element createElement(Document xmlDocument, String elementName) {
    return createElement(xmlDocument, elementName, null, false, null);
  }
  
  /**
   * Create a new element for use in an XML {@link Document} and automatically
   * append it to the parent {@link Node}.
   * 
   * @param parent The {@link Node} that will be the parent of this element.
   *            This should be an instance of either {@link Document} or
   *            {@link Element}. 
   * @param elementName The name of the element.
   * 
   * @return The newly created {@link Element}.
   */
  public static Element appendElement(Node parent, String elementName) {
    return appendElement(parent, elementName, null, false, null);
  }
  
  /**
   * Create a date based XML {@link Element}.
   * 
   * <p>This will not automatically append the new element to the supplied
   * <tt>xmlDocument</tt> object.
   * 
   * @param ownerDoc The {@link Document} to use to create the element.
   * @param elementName The name of the element.
   * @param includeTime Set to <code>true</code> to include the time portion of
   *            the date, or <code>false</code> to only include the date.
   * @param date The date to append.
   * 
   * @return The newly created {@link Date} element.
   */
  public static Element createElement(Document ownerDoc,
                                      String elementName,
                                      boolean includeTime,
                                      Date date) {
    // Ensure required values are set
    Validate.notNull(ownerDoc, "ownerDoc cannot be null");
    Validate.notEmpty(elementName, "elementName cannot be a null or empty String");
    
    Element element;
    
//    if(date != null) {
//      Map<String,String> attrs = new LinkedHashMap<String, String>();
//      Calendar cal = Calendar.getInstance();
//      cal.setTime(date);
//      
//      attrs.put("year", Integer.toString(cal.get(Calendar.YEAR)));
//      attrs.put("month", Integer.toString(cal.get(Calendar.MONTH) + 1));
//      attrs.put("day", Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
//      
//      if(includeTime) {
//        attrs.put("hour", Integer.toString(cal.get(Calendar.HOUR_OF_DAY)));
//        attrs.put("minute", Integer.toString(cal.get(Calendar.MINUTE)));
//        attrs.put("second", Integer.toString(cal.get(Calendar.SECOND)));
//        attrs.put("millisecond", Integer.toString(cal.get(Calendar.MILLISECOND)));
//      }
//      
//      String dateFormat = includeTime
//              ? DateTimeUtils.ISO_DATE_FULL_FORMAT
//              : DateTimeUtils.ISO_DATE_FORMAT;
//      
//      String dateString = DateTimeUtils.formatDate(dateFormat, date);
//      
//      element = createElement(ownerDoc, elementName, attrs, true, dateString);
//    } else {
//      // Default to an empty element
//      element = createElement(ownerDoc, elementName);
//    }
//    
    return null;
  }
  
  /**
   * Append a date based XML {@link Element} to a parent {@link Node}.
   * 
   * @param parent The {@link Node} that will be the parent of this element.
   *            This should be an instance of either {@link Document} or
   *            {@link Element}. 
   * @param elementName The name of the element.
   * @param includeTime Set to <code>true</code> to include the time portion of
   *            the date, or <code>false</code> to only include the date.
   * @param date The date to append.
   * 
   * @return The newly created {@link Date} element.
   */
  public static Element appendElement(Node parent,
                                      String elementName,
                                      boolean includeTime,
                                      Date date) {
    // Ensure required parameters are set
    Validate.notNull(parent, "parent cannot be null");
    Validate.notEmpty(elementName, "elementName cannot be null or empty");
    
    // Get the new element and append it to the parent
    Element element = createElement(getDocumentFromNode(parent), elementName,
        includeTime, date);
    parent.appendChild(element);
    
    return element;
  }
  
  /**
   * Normalizes the attribute value.
   * For further info see:<br>
   * <a href="http://www.w3.org/TR/REC-xml/#sec-line-ends">http://www.w3.org/TR/REC-xml/#sec-line-ends</a><br>
   * <a href="http://www.w3.org/TR/REC-xml/#AVNormalize">http://www.w3.org/TR/REC-xml/#AVNormalize</a><br>
   * <br>
   * <strong>Currently used from freemarker templates so be sure to fix it if refactoring.</strong>
   *  
   * @param value value to be normalized
   * @return normalized attribute value
   */
  public static String normalizeAttribute(String value) {	  
	  Validate.notNull(value, "value cannot be null");
	  
	  //
	  // First process new lines
	  // sequence #xD #xA and any #xD that is not followed by #xA to a single #xA character
	  // \n are already changed to ' ' as this would be done anyways in the next step
	  //
	  value = value.replace("\r\n", " ");
	  value = value.replace('\r', ' ');
	  
	  //
	  // The normalization
	  //
	  value = value.replace('\n', ' ');
	  value = value.replace('\t', ' ');
	  
	  //
	  // Replace multiple spaces by one
	  //
	  value = value.replaceAll(" {2,}", " ");
	  
	  //
	  // Ensure entities are encoded
	  //
      value = escapeIllegalCharacters(value);
      
	  //
	  // trim leading/trailing spaces
	  //	  
	  value = value.trim();

	  return value;
  }
  
  
  public static String getAttributeText(NamedNodeMap nodeMap, String attributeName) {
    String text = null;
    
    Node node = nodeMap.getNamedItem(attributeName);
    
    if (node != null) {
      text = node.getTextContent();
    }
        
    return text;
  }
}
