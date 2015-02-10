package com.contento3.thymeleaf.dialect.navigation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;

import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.service.PageService;
import com.contento3.thymeleaf.common.util.ProcessorUtil;

public class NavigationProcessor extends AbstractMarkupSubstitutionElementProcessor {

	private static final Logger LOGGER = Logger.getLogger(NavigationProcessor.class);

	/**
	 * 
	 * example use 
	 *  <nav:simple />
	 *  further description will be added soon as it converted to complete dynamic functionality
	 */
	private PageService pageService;

	private ProcessorUtil processorUtil;
	
	public PageService getPageService() {
		return pageService;
	}

	public void setPageService(final PageService pageService) {
		this.pageService = pageService;
	}

	public void setProcessorUtil(final ProcessorUtil processorUtil){
		this.processorUtil = processorUtil;
	}
	
	
	protected NavigationProcessor() {
		super("simple");
	}

	@Override
	protected List<Node> getMarkupSubstitutes(final Arguments arguments,final Element element) {
//		String[] titles = {"Home","Features","Blog", "Services", "Contact"};
//		String[] links  = {"home","Features","Blog", "Services", "ContactUs"};
		List<String> titles =  new ArrayList<String>();
		List<String> links  =  new ArrayList<String>();
		final Integer siteId = element.getAttributeValue("siteId")!=null? Integer.parseInt(element.getAttributeValue("siteId")):null;
		final Collection<PageDto> pagess = pageService.findNavigablePagesBySiteId(siteId);
		for (final PageDto page : pagess) {
			titles.add(page.getTitle());
			links.add(page.getUri());
		}
		final List<Node> nodes = new ArrayList<Node>();
		Element parentElement = new Element("span");
		parentElement.addChild(createNav(titles, links));
		nodes.add(parentElement);
		return nodes;		
	}
	
	protected Element createNav(final List<String> titles, final List<String> links ){
		Element ulElement = new Element("ul id='nav'");
		Element tempElement, tempElement2 ;
		for( int loop =0 ; loop < titles.size(); loop++){
			tempElement = new Element("a");
			tempElement.setAttribute("href", links.get(loop));
			tempElement.addChild( new Text(titles.get(loop)));
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
