package com.contento3.site.template.render.engine;

import java.io.Writer;

import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.site.template.model.TemplateModelMapImpl;

public interface RenderingEngine {

	public void process (TemplateModelMapImpl map,SiteDto siteDto,String pagePath,Writer writer);
	
}
