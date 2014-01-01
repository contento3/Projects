package com.contento3.thymeleaf.dialect.navigation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.w3c.dom.events.UIEvent;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.service.ArticleService;
import com.sun.org.apache.xalan.internal.xsltc.DOM;

public class NavigationProcessor extends AbstractMarkupSubstitutionElementProcessor {

	private static final Logger LOGGER = Logger.getLogger(NavigationProcessor.class);

	/**
	 * 
	 * example use 
	 *  <nav:simple />
	 *  further description will be added soon as it converted to complete dynamic functionality
	 */
	

	protected NavigationProcessor() {
		super("simple");
	}

	@Override
	protected List<Node> getMarkupSubstitutes(final Arguments arguments,final Element element) {
		String[] titles = {"Home","Features","Blog", "Services", "Contact"};
		String[] links  = {"home","Features","Blog", "Services", "ContactUs"};
		final List<Node> nodes = new ArrayList<Node>();
		Element parentElement = new Element("span");
		parentElement.addChild(CreateNav(titles, links));
		nodes.add(parentElement);
		return nodes;		
	}
	
	protected Element CreateNav(final String[] titles, final String[] links ){
		Element ulElement = new Element("ul id='nav'");
		Element tempElement, tempElement2 ;
		for( int loop =0 ; loop < titles.length ; loop++){
			tempElement = new Element("a");
			tempElement.setAttribute("href", links[loop]);
			tempElement.addChild( new Text(titles[loop]));
			tempElement2 = new Element("li");
			tempElement2.addChild(tempElement);
			ulElement.addChild(tempElement2);
		}
		return ulElement;
	}
	@Override
	public int getPrecedence() {
		return 1000;
	}
}
