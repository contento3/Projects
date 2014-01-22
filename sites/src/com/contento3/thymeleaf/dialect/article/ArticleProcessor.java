package com.contento3.thymeleaf.dialect.article;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.Template;
import org.thymeleaf.TemplateRepository;
import org.thymeleaf.dom.Document;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.util.DOMUtils;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.service.ArticleService;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;

import org.thymeleaf.TemplateProcessingParameters;
public class ArticleProcessor extends AbstractMarkupSubstitutionElementProcessor {

	private static final Logger LOGGER = Logger.getLogger(ArticleProcessor.class);

	/**
	 * Following are some of the usage scenarios of the <article:simple>
	 * tag to display article content.
	 * The following examples gives you different 
	 * scenarios of using the tag:
	 * 
	 * Id/siteId is the mandatory attribute. All others have 
	 * default values if values for them are not provided. 
	 * 
	 * Single Article 
	 * 	<article:simple id="" />
	 * 	<article:simple id="" siteId="" />
	 * 	<article:simple id="" siteId="" type="FULL" />
	 * 	<article:simple id="" siteId="" type="HEAD" />
	 * 	<article:simple id="" siteId="" type="TEASER" />
	 * 	<article:simple id="" siteId="" type="BODY" />
	 *  <article:simple id="" siteId="" type="BODY" pTags="off" />
	 * 
	 * 
	 * 	List
	 * 	<article:simple siteId="" type="FULL" count="5"/>
	 * 	<article:simple siteId="" type="HEAD" count="5"/>
	 * 	<article:simple siteId="" type="BODY" count="5"/>
	 * 	<article:simple siteId="" type="TEASER" count="5"/>
	 * 
	 * 	List with Category
	 * 	<article:simple siteId="" categoryIds="" type="FULL" count="5" />
	 * 	<article:simple siteId="" categoryIds="" type="HEAD" count="5" />
	 * 	<article:simple siteId="" categoryIds="" type="BODY" count="5" />
	 * 	<article:simple siteId="" categoryIds="" type="TEASER" count="5" />
	 * 
	 * 	Linked (Applies to all above)
	 * 	<article:simple siteId="" categoryIds="" type="TEASER" isLinked="" count="5" />
	 * 	Include Image (Applies to all above)
	 * 	<article:simple siteId="" categoryIds="" type="TEASER" includeImage="true" count="5" />
	 * 
	 *  DEFAULTS:
	 *  type=FULL
	 *  count=5
	 *  isLinked=true (when it is true,"Click here","More" is hyperlinked)
	 *  includeImage = true;
	 *  
	 */
	
	private final String TEASER="TEASER";
	
	private final String FULL="FULL";

	private final String HEADER="HEADER";

	private final String BODY="BODY";

	private final String HEADERTEASER="HEADERTEASER";

	private ArticleService articleService;

	protected ArticleProcessor() {
		super("simple");
	}

	@Override
	protected List<Node> getMarkupSubstitutes(final Arguments arguments,
			final Element element) {

		final List<Node> nodes = new ArrayList<Node>();
		String repo = arguments.getTemplateName();
		//First check the mandatory fields i.e. id, if id is not present then its siteId that needs to be present.
		//If both are not available then this tag is not a valid one.
//		Template template = arguments.getTemplateRepository().getTemplate(new TemplateProcessingParameters(arguments.getConfiguration(), "/template/site1/testin", arguments.getContext()));
//		Template template2 = arguments.getTemplateRepository().getTemplate(new TemplateProcessingParameters(arguments.getConfiguration(), "/template/test/testing", arguments.getContext()));
		final Integer id = element.getAttributeValue("id")!=null? Integer.parseInt(element.getAttributeValue("id")):null;
//		 List<Node> doc = template.getDocument().getChildren();
//		  Element eElement = (Element) doc.get(0);
	    final String uuid = parseString(arguments,element,"uuid");
//	    DOMUtils.extractFragmentByElementAndAttributeValue(doc, "div", arg2, arg3) 
		final String seoKeyword = element.getAttributeValue("seoKeyword");

		final Integer siteId = parseInteger(arguments,element,"siteId");
		
		String wrapperTag = parseString(arguments,element,"wrapperTag");
		
		if ((null==id && null==uuid && null==seoKeyword) && null==siteId){
			LOGGER.info(String.format("<article:simple> tag must atleast have id or siteId to display the article content.Currently,id is %s and siteId is %s on line number %d",id,siteId,element.getLineNumber()));
		}
		else {
			try {
		        //Check if the values for these are passed or not.Otherwise use the default values.
				String type = element.getAttributeValue("type");
		
				Boolean isLinked = element.getAttributeValue("isLinked")==null ? true : Boolean.parseBoolean(element.getAttributeValue("isLinked"));
				Boolean includeImage = Boolean.parseBoolean(element.getAttributeValue("includeImage"));
				Integer count = element.getAttributeValue("count")==null ? null:Integer.parseInt(element.getAttributeValue("count"));
				
				if (null==type){
					type = FULL;
				}
				
				if (null==includeImage)
					includeImage = true;
				
				//Count only works when we have multiple articles.
				//So this does not apply when we try to fetch an article using its id.
				if (null!=siteId && null==count){
					count = 5;
				}
				
				Collection <ArticleDto> articleList = new ArrayList<ArticleDto>();
				
				//CategoryIds 
				final String categoryIds = element.getAttributeValue("categoryIds");
				Element container = null;
				
				if (siteId!=null){
					articleList = fetchArticles(siteId,categoryIds,count);
				}
				else if (id!=null || uuid!=null || seoKeyword!=null){
					ArticleDto articleDto = null;
					if (id!=null){
						articleDto = fetchSingle(id);
					}
					else if (uuid!=null){
						articleDto = fetchSingleByUuid(uuid);
					}
					else if (seoKeyword!=null){
						//TODO Need some more research
					}
					if (null!=articleDto){
						articleList.add(articleDto);
					}
				}
				

				container = buildDOMForArticles(articleList,includeImage,type,isLinked,wrapperTag);
				nodes.add(container);
			}
			catch(Exception e){
				LOGGER.info(String.format("Something went wrong while you tried using the tag <article:simple> on line number %d in %s template",element.getLineNumber(),arguments.getTemplateName()));
			}
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
		
	private Integer parseInteger(final Arguments arguments,final Element element,final String attributeName){
		Integer parsedAttribute = null;
		String attributeValue = element.getAttributeValue(attributeName);
		if (null!=element.getAttributeValue(attributeName)){
			final IStandardExpression expression = buildExpression(arguments,element,attributeValue);
			parsedAttribute = (Integer) expression.execute(arguments.getConfiguration(), arguments);
		}
		return parsedAttribute;
	}

	private IStandardExpression buildExpression(final Arguments arguments,final Element element,final String attributeValue){
        final Configuration configuration = arguments.getConfiguration();
 		final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);
		final IStandardExpression expression = parser.parseExpression(configuration, arguments, attributeValue);
		return expression; 
	}

	private Element buildDOMForArticles(final Collection <ArticleDto> articleList,final Boolean includeImage,final String type,final Boolean isLinked, String wrapperTag){
		Element parentElement = new Element("div");
		for (ArticleDto article : articleList){
			parentElement.addChild(buildDOMForSingleArticle(article,includeImage,type,isLinked,wrapperTag));
		}
		return parentElement;
	}

	private Element buildDOMForSingleArticle(final ArticleDto article,final Boolean includeImage,final String type,final Boolean isLinked,final String wrapperTag){
		/*
	     * Create the DOM structure that will be substituting our custom tag.
	     * The headline will be shown inside a '<div>' tag, and so this must
	     * be created first and then a Text node must be added to it.
	     */
		Element articleContainer = new Element("div");
		articleContainer.setAttribute("class", "articleConainter");
		String containerElementName = wrapperTag;
//		if(pTags.equals("off")){
//			containerElementName = "span";
//		}else{
//			containerElementName = "p";
//		}
		if (type.equalsIgnoreCase(FULL)){
			articleContainer.addChild(buildHeader(article,containerElementName));
			articleContainer.addChild(buildTeaser(article,containerElementName));
			articleContainer.addChild(buildBody(article,containerElementName));
		}
		else if (type.equalsIgnoreCase(TEASER)){
			if (!isLinked){
				articleContainer = buildTeaser(article,containerElementName);
			}
			else {
				articleContainer = buildLinkedTeaser(article);
			}
		}
		else if (type.equalsIgnoreCase(BODY)){
			articleContainer = buildBody(article,containerElementName);
		}
		else if (type.equalsIgnoreCase(HEADER)){
			articleContainer.addChild(buildHeader(article,containerElementName));
		}
		else if (type.equalsIgnoreCase(HEADERTEASER)){
			articleContainer.addChild(buildHeader(article,containerElementName));
			articleContainer.addChild(buildTeaser(article,containerElementName));
		}
		return articleContainer;
	}

	private Element buildTeaser(final ArticleDto article,final String containerElementName){
        final Element teaserContainer = new Element(containerElementName);
        teaserContainer.setAttribute("class", "teaser");

        final Text text = new Text(article.getTeaser());
        teaserContainer.addChild(text);
        return teaserContainer;
	}

	private Element buildLinkedTeaser(final ArticleDto article){
        final Element teaserContainer = new Element("a");
        teaserContainer.setAttribute("href", "/article/"+article.getUuid());
        teaserContainer.setAttribute("class", "teaser");

        final Text text = new Text(article.getTeaser());
        teaserContainer.addChild(text);
        return teaserContainer;
	}

	private Element buildHeader(final ArticleDto article,final String containerElementName){
        final Element headerContainer = new Element(containerElementName);
        headerContainer.setAttribute("class", "head");

        final Text text = new Text(article.getHead());
        headerContainer.addChild(text);
        return headerContainer;
	}

	private Element buildBody(final ArticleDto article,final String containerElementName){
        final Element bodyContainer = new Element(containerElementName);
        bodyContainer.setAttribute("class", "body");

        final Text text = new Text(article.getBody(),false);
        bodyContainer.addChild(text);
        return bodyContainer;
	}

	private Collection <ArticleDto> fetchArticles(final Integer siteId,final String categoryIds,final Integer count){
		final Collection <ArticleDto> articleList = new ArrayList<ArticleDto>();
		
		if (null!=siteId){
			articleList.addAll(fetchMultiple(siteId,categoryIds,count));
		}
		return articleList;
	}
	
	private Collection <ArticleDto> fetchMultiple(final Integer siteId,final String categoryIds,final Integer count){
		final List<Integer> categoryIdList = new ArrayList<Integer>();
		final String[] categoryIdsArray;
		if (null!=categoryIds){
			categoryIdsArray = categoryIds.split(",");
		    for (int index = 0; index < categoryIdsArray.length; index++)
		    {
		    	categoryIdList.add(Integer.parseInt(categoryIdsArray[index]));
		    }
		}
	   return articleService.findLatestArticleByCategory(categoryIdList, count, siteId);
	}

	private ArticleDto fetchSingle(final Integer articleId){
		return articleService.findById(articleId);
	}

	private ArticleDto fetchSingleByUuid(final String articleUuid){
		return articleService.findByUuid(articleUuid);
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
