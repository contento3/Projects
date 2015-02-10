package com.contento3.thymeleaf.dialect.seo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;

import com.contento3.cms.seo.dto.MetaTagDto;
import com.contento3.cms.seo.model.MetaTagLevelEnum;
import com.contento3.cms.seo.service.MetaTagService;
import com.contento3.thymeleaf.common.util.ProcessorUtil;

/**
 * Processor that renders meta tag set from the seo settings screen. 
 * The processor gets called when the <seo:simple> tag is called from
 * the template.The template looks for page level seo settings (meta tag)
 * for the provided page if nothing is found then it looks for site level
 * meta tags.The page level always have the priority on site level. 
 * @author hamakhaa
 *
 */
public class SEOProcessor extends AbstractMarkupSubstitutionElementProcessor {

	private MetaTagService metaTagService;
	
	private ProcessorUtil processorUtil;
	
	protected SEOProcessor() {
		super("simple");
	}

	@Override
	protected List<Node> getMarkupSubstitutes(final Arguments arguments,final Element element) {
		final List <Node> node = new ArrayList<Node>();
		
		Integer pageId = processorUtil.parseInteger(arguments, element, "pageId");
		Integer siteId = processorUtil.parseInteger(arguments, element, "siteId");
		Collection <MetaTagDto> pageMetatags = new ArrayList<MetaTagDto>();
		Collection <MetaTagDto> siteMetatags = new ArrayList<MetaTagDto>();
		
		if (siteId!=null){
			if (pageId!=null){
				pageMetatags = metaTagService.findByAssociatedId(pageId, MetaTagLevelEnum.PAGE);
			}
			
			siteMetatags = metaTagService.findByAssociatedId(siteId, MetaTagLevelEnum.SITE);
			
			final Collection <MetaTagDto> combinedMetatags = new ArrayList<MetaTagDto>();
			final Collection <MetaTagDto> siteMetatagsToRemove = new ArrayList<MetaTagDto>();
			
			for (MetaTagDto pageMetaTagDto : pageMetatags){
				for (MetaTagDto siteMetaTagDto : siteMetatags){
					if (siteMetaTagDto.getAttribute().equals("name")){
						if (siteMetaTagDto.getAttributeValue().equals(pageMetaTagDto.getAttributeValue()) && siteMetaTagDto.getAttribute().equals(pageMetaTagDto.getAttribute())){
							siteMetatagsToRemove.add(siteMetaTagDto);
						}
					}
					else if (siteMetaTagDto.getAttribute().equals(pageMetaTagDto.getAttribute())) {
						siteMetatagsToRemove.add(siteMetaTagDto);
					} 
				}
			}
		
			for (MetaTagDto metatag : siteMetatagsToRemove){
				siteMetatags.remove(metatag);
			}
			
			combinedMetatags.addAll(siteMetatags);		
			combinedMetatags.addAll(pageMetatags);		
				
	
			for(MetaTagDto metaTagDto : combinedMetatags){
				Element metaElement = new Element("meta");
				metaElement.setAttribute(metaTagDto.getAttribute(), metaTagDto.getAttributeValue());

				if (!StringUtils.isEmpty(metaTagDto.getAttributeContent())){
					metaElement.setAttribute("content", metaTagDto.getAttributeContent());
				}
				node.add(metaElement);
			}
		}		
		return node;
	}

	@Override
	public int getPrecedence() {
		return 0;
	}

	
	public void setMetaTagService(final MetaTagService metaTagService){
		this.metaTagService = metaTagService;
	}
	
	public void setProcessorUtil(final ProcessorUtil processorUtil){
		this.processorUtil = processorUtil;
	}
}
