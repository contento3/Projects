package com.contento3.thymeleaf.dialect.slider;

import java.util.ArrayList;
import java.util.List;

import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.Template;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.dom.Document;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import com.contento3.cms.article.service.ArticleService;
public class SliderProcessor extends AbstractMarkupSubstitutionElementProcessor {

	/**
	 * Following are some of the usage scenarios of the 
	 * tag to display slider content.
	 * The following examples gives you different 
	 * scenarios of using the tag:
	 * 
	 * 
	 * From now article will be rendered by the help of html and thymeleaf code 
	 * in html , so for using article dialect we will just provide template key
	 * to fetch required template and all other code will be in those template
	 *  
	 * 	<slider templateKey="templateKey1" libraryId="libraryId" />
	 * 	
	 * if there is no template present for any key than it will show default error template 
	 * defined in getMarkupSubstitute method.  
	 */
	
	private ArticleService articleService;

	protected SliderProcessor() {
		super("");
	}

	@Override
	protected List<Node> getMarkupSubstitutes(final Arguments arguments,
			final Element element) {
		final String defaultTemplateKey = "default"; 	// will be used if required template is missing
		String templateKey = parseString(arguments,element,"templateKey");
		String libraryId   = parseString(arguments,element,"libraryId");
		
		if(templateKey == null){
			templateKey = defaultTemplateKey;
		}

		final List<Node> nodes = new ArrayList<Node>();
		/*
		 * Below line will never return null because
		 * on empty result it will return default error template
		 *  i.e. an error occured during geting template etc 
		 */
		Template template = arguments.getTemplateRepository().getTemplate(new TemplateProcessingParameters(arguments.getConfiguration(), "/template_key/"+templateKey, arguments.getContext()));
		Document doc = template.getDocument();
		Element htmlToAdd = doc.getFirstElementChild() ; 
		if(htmlToAdd == null)
	    {
			template = arguments.getTemplateRepository().getTemplate(new TemplateProcessingParameters(arguments.getConfiguration(), "/template/site1/"+defaultTemplateKey, arguments.getContext()));
			doc = template.getDocument();
			htmlToAdd  = doc.getFirstElementChild();
			nodes.add((Node)htmlToAdd);
	    }
		else{
			htmlToAdd.setNodeLocalVariable("libraryId", libraryId);
			nodes.add((Node)htmlToAdd);
		}

	    return nodes;
	   }

	private String parseString(final Arguments arguments,final Element element,final String attributeName){
		String parsedAttribute = null;
		String attributeValue = element.getAttributeValue(attributeName);
			if (null!=element.getAttributeValue(attributeName)){
			final IStandardExpression expression = buildExpression(arguments,element,attributeValue);
			parsedAttribute = (String) expression.execute(arguments.getConfiguration(), arguments);
		}
		return parsedAttribute;
	}
		
	private IStandardExpression buildExpression(final Arguments arguments,final Element element,final String attributeValue){
        final Configuration configuration = arguments.getConfiguration();
 		final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);
		final IStandardExpression expression = parser.parseExpression(configuration, arguments, attributeValue);
		return expression; 
	}
	
	@Override
	public int getPrecedence() {
		return 1000;
	}

	public ArticleService getArticleService() {
		return articleService;
	}

	public void setArticleService(final ArticleService articleService) {
		this.articleService = articleService;
	}

}
