package com.contento3.thymeleaf.dialect.article;

import java.util.ArrayList;
import java.util.List;

import org.thymeleaf.Arguments;
import org.thymeleaf.Template;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.dom.Document;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;

import com.contento3.cms.article.service.ArticleService;
import com.contento3.thymeleaf.common.util.ProcessorUtil;
public class ArticleProcessor extends AbstractMarkupSubstitutionElementProcessor {

	/**
	 * Following are some of the usage scenarios of the <article:simple>
	 * tag to display article content.
	 * The following examples gives you different 
	 * scenarios of using the tag:
	 * 
	 * 
	 * From now article will be rendered by the help of html and thymeleaf code 
	 * in html , so for using article dialect we will just provide template key
	 * to fetch required template and all other code will be in those template
	 *  
	 * 	<article:simple templateKey="templateKey1" />
	 * 	
	 * if there is no template present for any key than it will show default error template 
	 * defined in getMarkupSubstitute method.  
	 */
	
	private ArticleService articleService;

	private ProcessorUtil processorUtil;
	
	protected ArticleProcessor() {
		super("simple");
	}

	@Override
	protected List<Node> getMarkupSubstitutes(final Arguments arguments,
			final Element element) {
		final String defaultTemplateKey = "default"; 	// will be used if required template is missing
		String templateKey = processorUtil.parseString(arguments,element,"templateKey");
		String categoryId  = processorUtil.parseString(arguments,element,"categoryId");
		String templateId  = processorUtil.parseString(arguments,element,"templateId");
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
			htmlToAdd.setNodeLocalVariable("catId", categoryId);
			htmlToAdd.setNodeLocalVariable("templateId", templateId);
			nodes.add((Node)htmlToAdd);
		}

	    return nodes;
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

	public void setProcessorUtil(final ProcessorUtil processorUtil) {
		this.processorUtil = processorUtil;
	}
}
