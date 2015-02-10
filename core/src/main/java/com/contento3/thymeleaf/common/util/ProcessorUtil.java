package com.contento3.thymeleaf.common.util;

import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.dom.Element;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;

public class ProcessorUtil {

	/**
	 * 
	 * @param arguments
	 * @param element
	 * @param attributeName
	 * @return
	 */
	public String parseString(final Arguments arguments,final Element element,final String attributeName){
		String parsedAttribute = null;
		String attributeValue = element.getAttributeValue(attributeName);
			if (null!=element.getAttributeValue(attributeName)){
			final IStandardExpression expression = buildExpression(arguments,element,attributeValue);
			parsedAttribute = (String) expression.execute(arguments.getConfiguration(), arguments);
		}
		return parsedAttribute;
	}
		
	/**
	 * 
	 * @param arguments
	 * @param element
	 * @param attributeValue
	 * @return
	 */
	public IStandardExpression buildExpression(final Arguments arguments,final Element element,final String attributeValue){
        final Configuration configuration = arguments.getConfiguration();
 		final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);
		final IStandardExpression expression = parser.parseExpression(configuration, arguments, attributeValue);
		return expression; 
	}

	public Integer parseInteger(final Arguments arguments,final Element element,final String attributeName){
		Integer parsedAttribute = null;
		String attributeValue = element.getAttributeValue(attributeName);
		if (null!=element.getAttributeValue(attributeName)){
			final IStandardExpression expression = buildExpression(arguments,element,attributeValue);
			parsedAttribute = (Integer) expression.execute(arguments.getConfiguration(), arguments);
		}
		return parsedAttribute;
	}

}
