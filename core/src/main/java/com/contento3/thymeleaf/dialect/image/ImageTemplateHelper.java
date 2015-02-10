package com.contento3.thymeleaf.dialect.image;

import java.util.Collection;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.ContextLoader;

import com.contento3.cms.article.dto.ArticleImageDto;
import com.contento3.cms.article.service.ArticleImageService;
import com.contento3.cms.content.dto.AssociatedContentScopeDto;
import com.contento3.cms.content.model.AssociatedContentScopeTypeEnum;
import com.contento3.cms.content.service.AssociatedContentScopeService;
import com.contento3.dam.image.dto.ImageDto;

public class ImageTemplateHelper {

	private ArticleImageService articleImageService;
	
	private AssociatedContentScopeService scopeService;
	
	public ImageTemplateHelper() {
		ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		this.scopeService = (AssociatedContentScopeService) ctx.getBean("associatedContentScopeService");
		this.articleImageService = (ArticleImageService) ctx.getBean("articleImageService");
	}
    

	public ImageDto getHeaderImageForArticle(final Integer articleId){
		return getImageForArticle(articleId,"Header");
	}
	
	public ImageDto getTeaserImageForArticle(final Integer articleId){
		return getImageForArticle(articleId,"Teaser");
	}
	
	public ImageDto getImageForArticle(final Integer articleId,final String requiredImageScope){
		Collection<AssociatedContentScopeDto> contentScopes =  scopeService.getContentScopeForType(AssociatedContentScopeTypeEnum.IMAGE);
		List <ArticleImageDto> aiDto = (List<ArticleImageDto>)articleImageService.findAsscArticleImageByArticleIdAndScopeId(articleId, fetchContentScopeId(requiredImageScope,contentScopes));
		if (!CollectionUtils.isEmpty(aiDto)){
			return aiDto.get(0).getImage();
		}
		return null;
	}

	public String getHeaderImageNameForArticle(final Integer articleId){
		if (getImageForArticle(articleId,"Header")==null){
			return null;
		}	
		return getImageForArticle(articleId,"Header").getName();
	}
	
	public String getTeaserImageNameForArticle(final Integer articleId){
		if (getImageForArticle(articleId,"Teaser")==null){
			return null;
		}	
		return getImageForArticle(articleId,"Teaser").getName();
	}

	private Integer fetchContentScopeId(final String imageScope,final Collection<AssociatedContentScopeDto> contentScopes){
		for (AssociatedContentScopeDto dto:contentScopes){
			if (dto.getScope().equalsIgnoreCase(imageScope)){
				return dto.getId();
			}
		}
		return null;
	}
	
}
