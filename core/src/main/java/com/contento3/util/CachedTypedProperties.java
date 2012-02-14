package com.contento3.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * This class automatically finds, loads and caches properties files from the
 * Java class path.
 */
public final class CachedTypedProperties extends TypedProperties {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6777958049074939398L;

	/**
	 * The flush parameter to use to specify which properties to flush
	 */
	public static final String FLUSH_PROPERTIES_NAME = "properties"; //NOPMD - reviewed theresa.forster

  /**
   * The singletons -- one per properties file
   */
  private static Map<String, Object> instances = Collections.synchronizedMap(new HashMap<String, Object>());

  /**
   * Singletons require private constructors
   */
  private CachedTypedProperties() {
    super();
  }

  /**
   * Get an instance, cached if possible
   *
   * @param name  the name of the properties file for which to get an instance
   *              of this class
   * @return  an instance of this class for the specified properties file --
   *          only newly instantiated the first time this method is called
   * @throws ClassNotFoundException  if the properties file could not be found
   */
  public static synchronized CachedTypedProperties getInstance(final String name)throws ClassNotFoundException //NOPMD - reviewed theresa.forster
      {
	  CachedTypedProperties propertiesToReturn = null; 
	  final Object propertyCheck = CachedTypedProperties.instances.get(name);
	  if(propertyCheck instanceof CachedTypedProperties)
	  {
		  propertiesToReturn = (CachedTypedProperties)propertyCheck;
	  }
	  else if (propertyCheck!= null)
	  {
		  throw new ClassNotFoundException(String.format("Could not find %s file in Java class path: %s", name,System.getProperty("java.class.path")));
	  }

    if (null == propertiesToReturn) {
      propertiesToReturn = new CachedTypedProperties();

      try {
        //
        // This loads the requested properties file from the Java class path
        //
        propertiesToReturn.load(
          propertiesToReturn.getClass().getClassLoader().getResourceAsStream(
            name
          )
        );

      } 
     catch (NullPointerException npe){
        throw new ClassNotFoundException(
          "Could not find " + name + " file in Java class path: " +
          System.getProperty("java.class.path"), npe);
     }
      catch (IOException ioe) {
        throw new ClassNotFoundException(
          "Error while reading " + name + " file", ioe  
        );
      }

      CachedTypedProperties.instances.put(name, propertiesToReturn);
    }

    return propertiesToReturn;

  }
  
  /**
   * Get an instance of an XML configuration document, cached if possible
   *
   * @param name  the name of the properties file for which to get an instance
   *              of this class
   * @param orgId    the organisation id within the XML config file to get
   * @return  an instance of this class for the specified properties file --
   *          only newly instantiated the first time this method is called
   * @throws ClassNotFoundException  if the properties file could not be found
   */
  public static synchronized CachedTypedProperties getInstance(final String name,final int orgId) throws ClassNotFoundException 
  {
	  CachedTypedProperties returnValue = null; 
	  final Object propertyCheck = CachedTypedProperties.instances.get(name);
	  if(propertyCheck instanceof Map)
	  {

		  final Map <String,CachedTypedProperties> XMLMap = (Map<String,CachedTypedProperties>)propertyCheck;
		  returnValue = XMLMap.get(String.valueOf(orgId));
	
	  }
	  else if (propertyCheck!= null)
	  {
		  throw new ClassNotFoundException(String.format("Could not find %s file in Java class path: %s", name,System.getProperty("java.class.path")));
	  }
	  
	  if (null == returnValue) {
		  returnValue = new CachedTypedProperties();
	      try {
	    	loadXMLProperties(name);
	    	//now having reloaded all the contained entries we need to reset the property block to the
	    	//one we wanted in the first place
	    	final Map<String, CachedTypedProperties> xmlProps = (Map<String, CachedTypedProperties>)CachedTypedProperties.instances.get(name); 
	    	returnValue = CachedTypedProperties.class.cast(xmlProps.get(String.valueOf(orgId)));
	    	
	       //propertiesToReturn = (CachedTypedProperties)((HashMap)CachedTypedProperties.instances.get(name)).get(orgId);
	      }
	      catch (DOMException de)
	      {
	          throw new ClassNotFoundException(
	                  "DOMException in XML config file " + name + " in Java class path: " +  
	                  System.getProperty("java.class.path"), de    );	      	      	      	  
	      }
	      catch (Exception e)
	      {
	        	throw new ClassNotFoundException("XML conversion error " + name + " file", e);
	      }
	      
	    }

	  return returnValue;

  }

  /**
   * Returns the Hashmap of multiple configuration property files
   * @param name
   * @return
   */

  public static Set<String> getInstancesKeys(final String name)throws ClassNotFoundException
  {  
	  return ((Map<String,CachedTypedProperties>)getInstances(name)).keySet();		
  }  
  
  /**
   * Returns the Hashmap of multiple configuration property files
   * @param name
   * @return
   */
  public static synchronized Map<String,CachedTypedProperties> getInstances(final String name)throws ClassNotFoundException
	{
	  Map<String,CachedTypedProperties> returnValue = null;
	  try 
	  {
		  final Object propertyCheck = CachedTypedProperties.instances.get(name);
		  if(propertyCheck instanceof Map)
		  {

				  returnValue = (Map<String,CachedTypedProperties>)propertyCheck;
		  }
		  else if (propertyCheck!= null)
		  {
			  throw new ClassNotFoundException(String.format("Could not find %s file in Java class path: %s", name,System.getProperty("java.class.path")));
		  }

		  if (null == returnValue) 
		  {
		     		    	
		    	loadXMLProperties(name);
		    	//now having reloaded all the contained entries we need to reset the property block to the
		    	//one we wanted in the first place
		    	returnValue= (Map<String, CachedTypedProperties>)CachedTypedProperties.instances.get(name);
		    	
		       //propertiesToReturn = (CachedTypedProperties)((HashMap)CachedTypedProperties.instances.get(name)).get(orgId);
		      }
	  	  }
	      catch (DOMException de)
	      {
	    	  throw new ClassNotFoundException(
	                  "Could not parse input XML file " + name + " in Java class path: " +
	                  System.getProperty("java.class.path"), de    );	      	   
	      }
	      catch (Exception e)
	      {
	        	throw new ClassNotFoundException("XML conversion error " + name + " file", e);
	      }
      return returnValue;
  }

  /**
   * Get an instance, cached if possible
   * 
   * @param url the url of the properties file
   * @return an instance of this class for the specified properties file --
   *          only newly instantiated the first time this method is called
   * @throws Exception
   */
  public static synchronized CachedTypedProperties getInstance(final URL url) throws ClassNotFoundException {
  
	  CachedTypedProperties returnValue = null;
	  final Object propertyCheck = CachedTypedProperties.instances.get(url.getPath());
	  if(propertyCheck instanceof  CachedTypedProperties|| propertyCheck==null)
	  {
		  returnValue = (CachedTypedProperties)propertyCheck;
	  }
	  else
	  {
		  throw new ClassNotFoundException(
		          "Could not find " + url + " file in Java class path: " +
		          System.getProperty("java.class.path"));
	  }	  
    
    if(url != null) {  //NOPMD - reviewed theresa.forster
  
      if (null == returnValue) {
        
        returnValue = new CachedTypedProperties();
        InputStream inputStream = null;
        try {         
        	inputStream = url.openStream();
          //
          // This loads the requested properties file from the Java class path
          //
          returnValue.load(inputStream);
  
        }
        catch (IOException ioe)
        {
        	 throw new ClassNotFoundException(
       	          "Error while reading property URL " + url , ioe
       	        );
        }
        finally {
          if(inputStream != null) {
        	try
        	{
        		inputStream.close();
        	}
        	catch (IOException ioe)
        	{
        		//This one we shouldn't throw on so just ignore
        		
        	}
          }
        }
  
        CachedTypedProperties.instances.put(url.getPath(), returnValue);
      }
      
    } else {
      throw new ClassNotFoundException("URL passed in was NULL.");
    }
    
    return returnValue;

  }
  
  /**
   * Wipes all of the cached properties 
   */
  public static void flushData() {
  	instances.clear();
  }
  
  /**
   * Removed the properties identified by the {@value #FLUSH_PROPERTIES_NAME} property
   * @param flushParameters the collection of parameters
   */
  public static void flushData(final Properties flushParameters) {
  	
  	final String propertiesName = flushParameters.getProperty(FLUSH_PROPERTIES_NAME);
  	
  	if (null != propertiesName) {
  		instances.remove(propertiesName);
  	}
  }
  
  private static ByteArrayOutputStream processXMLProperties(NodeList inputNodes,final DocumentBuilder docBuild) throws ClassNotFoundException
  {
	  ByteArrayOutputStream outStream;
	  final Element tmpElem = (Element)inputNodes.item(0).cloneNode(true);
	  final Document tmpDoc2 = docBuild.newDocument();
	  tmpDoc2.adoptNode(tmpElem);
	  tmpDoc2.appendChild(tmpElem);
	  tmpDoc2.normalize();
	  try
	  {
		//  XMLUtils.convertXmlDocToString(tmpDoc2);
		  final Transformer trans = TransformerFactory.newInstance().newTransformer();
		  trans.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://java.sun.com/dtd/properties.dtd");
		  outStream = new ByteArrayOutputStream(); //NOPMD - reviewed theresa.forster
		  trans.transform(new DOMSource(tmpDoc2), new StreamResult(outStream)); //NOPMD - reviewed theresa.forster
	  }
	  catch (Exception e)
	  {
		  throw new ClassNotFoundException("Error in processXMLProperties",e);
	  }
	  return outStream;
  }
  
  private static Map<String, CachedTypedProperties> loadXMLProperties(String name) throws ClassNotFoundException
  {
		    try {
		        //
		        // This loads the requested properties file from the Java class path
		    	// parses it and stores the contents in the system.
		        //
		    	CachedTypedProperties tempProp = new CachedTypedProperties();
		    	final Map <String,CachedTypedProperties> XMLMap = new HashMap<String,CachedTypedProperties>();
		    	final File filePointer = new File(tempProp.getClass().getClassLoader().getResource(name).getPath());  //NOPMD - reviewed theresa.forster
		    	
		    	final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		    	final DocumentBuilder docBuild = dbf.newDocumentBuilder();
		    	final Document doc = docBuild.parse(filePointer);
		    	final NodeList nodelist = doc.getElementsByTagName("client");
		    	for(int a = 0; a<nodelist.getLength();a++)
		    	{
		    		final Element tmpElement = (Element)nodelist.item(a);
		    		final String tmpOrgId = tmpElement.getAttribute("organisation");
		    		//This gets the child nodes which should be the property nodes inside the XML
		    		final NodeList tmpNodes2 = tmpElement.getElementsByTagName(FLUSH_PROPERTIES_NAME);
		    		if(tmpNodes2.getLength() !=0)
		    		{
		    			tempProp = new CachedTypedProperties();
		    			 tempProp.loadFromXML(new ByteArrayInputStream(processXMLProperties(tmpNodes2,docBuild).toByteArray())); 
		    			 XMLMap.put(String.valueOf(tmpOrgId), tempProp);
		    		}
		    	}
		    	CachedTypedProperties.instances.put(name, XMLMap);
		    	//now having reloaded all the contained entries we need to reset the property block to the
		    	//one we wanted in the first place
		    	final Map<String, CachedTypedProperties> xmlProps = (Map<String, CachedTypedProperties>)CachedTypedProperties.instances.get(name); 
		    	return xmlProps;
		    	
		       //propertiesToReturn = (CachedTypedProperties)((HashMap)CachedTypedProperties.instances.get(name)).get(orgId);
		      }
		      catch (DOMException de)
		      {
		          throw new ClassNotFoundException(
		                  "DOMException in XML config file " + name + " in Java class path: " +  
		                  System.getProperty("java.class.path"), de    );	      	      	      	  
		      }
		      catch (SAXException se)
		      {
		          throw new ClassNotFoundException(
		                  "SAXException in XML config file " + name + " in Java class path: " +
		                  System.getProperty("java.class.path"), se    );	      	      	  
		      }
		      catch (ParserConfigurationException pce)
		      {
		          throw new ClassNotFoundException(
		                  "Could not create XML parser for " + name + " file in Java class path: " +
		                  System.getProperty("java.class.path"), pce    );	  
		      }
		    catch (IOException ioe) {
		        throw new ClassNotFoundException(
		          "Error while reading " + name + " file", ioe
		        );
		      }
		      catch (Exception e)
		      {
		        	throw new ClassNotFoundException("XML conversion error " + name + " file", e);
		      }	  
  }
  
}
