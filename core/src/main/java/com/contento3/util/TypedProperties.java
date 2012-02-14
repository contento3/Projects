package com.contento3.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.util.StringUtils;

/**
 * Extends properties to implement parsing for types other than String.
 */
public class TypedProperties extends java.util.Properties {

  /**
   * Read a required string
   *
   * @param propertyName name of the property to read
   * @return the value of the property
   * @throws NullPointerException if the property is not present
   */
  public String getStringProperty(String propertyName) {

    String returnValue = this.getProperty(propertyName);
    if (null == returnValue) {
      throw new NullPointerException("Parameter " + propertyName +
                                     " not defined!");
    }

    return returnValue;
  }


  /**
   * Read a defaultable string
   *
   * @param propertyName name of the property to read
   * @param defaultValue default value to use if property not set
   * @return the value of the property or the default value if it is not set
   */
  public String getStringProperty(String propertyName, String defaultValue) {

    return this.getProperty(propertyName, defaultValue);
  }


  /**
   * Read a required integer
   *
   * @param propertyName name of the property to read
   * @return the parsed value of the property
   * @throws NumberFormatException if the property cannot be parsed
   * @throws NullPointerException if the property is not present
   */
  public int getIntProperty(String propertyName) {
  	
  	String readVal = this.getProperty(propertyName);
  	
    return Integer.parseInt((readVal != null ? readVal.trim() : readVal));
  }


  /**
   * Read a defaultable integer
   *
   * @param propertyName name of the property to read
   * @param defaultValue default value to use if property not set
   * @return the parsed value of the property or the default value if it is not
   *         set
   * @throws NumberFormatException if the property cannot be parsed
   */
  public int getIntProperty(String propertyName,
                            int    defaultValue) {
    int returnValue;
    String readValue = this.getProperty(propertyName);
    if (null == readValue) {
      returnValue = defaultValue;
    } else {
      returnValue = Integer.parseInt(readValue.trim());
    }

    return returnValue;
  }

  /**
   * Retrieves a list of integer objects from a properties file, where the entry is a comma
   * delimeted list ie 12555,12565
   * @param propertyName
   * @return
   */
  public List<Integer> getDelimetedIntProperty(String propertyName, String delimeter) {
    List<Integer> list = new ArrayList<Integer>();
    List<String> strList = getDelimetedProperty(propertyName, delimeter);
    
    for (String str : strList) {
      try {
        list.add(Integer.parseInt(str));
      } catch (NumberFormatException e) {
        // Do Nothing
      }
    }

    return list;
  }
  
  /**
   * Retrieves a list of integer objects from a properties file, where the entry is a comma
   * delimeted list ie 12555,12565
   * @param propertyName
   * @return
   */
  public List<String> getDelimetedProperty(String propertyName, String delimeter) {
    List<String> list = new ArrayList<String>();
    
    //Test if there is a value
    String rawValue = this.getProperty(propertyName);
//    if (StringUtils.isNullOrEmpty(rawValue)) {
//      return list;
//    }
    
    StringTokenizer st = new StringTokenizer(rawValue, delimeter);
    while (st.hasMoreTokens()) {
      String token = st.nextToken();
      list.add(token.trim());
    }

    return list;
  }

  /**
   * Read a required boolean
   *
   * @param propertyName name of the property to read
   * @return the parsed value of the property
   * @throws NullPointerException if the property is not present
   */
  public boolean getBooleanProperty(String propertyName) {
  	
  	String readVal = this.getProperty(propertyName);

    return (Boolean.valueOf((readVal != null ? readVal.trim() : readVal))).booleanValue();
  }


  /**
   * Read a defaultable boolean
   *
   * @param propertyName name of the property to read
   * @param defaultValue default value to use if property not set
   * @return the parsed value of the property or the default value if it is not
   *         set
   */
  public boolean getBooleanProperty(String  propertyName,
                                    boolean defaultValue) {
    boolean returnValue;
    String readValue = this.getProperty(propertyName);
    if (null == readValue) {
      returnValue = defaultValue;
    } else {
      returnValue = (Boolean.valueOf(readValue.trim())).booleanValue();
    }

    return returnValue;
  }

  
  /**
   * Read a required long
   *
   * @param propertyName name of the property to read
   * @return the parsed value of the property
   * @throws NumberFormatException if the property cannot be parsed
   * @throws NullPointerException if the property is not present
   */
  public long getLongProperty(String propertyName) {
  	
  	String readVal = this.getProperty(propertyName);
  	
    return Long.parseLong((readVal != null ? readVal.trim() : readVal));
  }

  /**
   * Read a defaultable long
   *
   * @param propertyName name of the property to read
   * @param defaultValue default value to use if property not set
   * @return the parsed value of the property or the default value if it is not
   *         set
   * @throws NumberFormatException if the property cannot be parsed
   */
  public long getLongProperty(String propertyName,
                              long   defaultValue) {
    long returnValue;
    String readValue = this.getProperty(propertyName);
    if (null == readValue) {
      returnValue = defaultValue;
    } else {
      returnValue = Long.parseLong(readValue.trim());
    }

    return returnValue;
  }


  
}

