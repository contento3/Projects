package com.contento3.site.template.render.engine;

import java.io.Writer;

import com.olive.cms.site.structure.dto.SiteDto;

public interface RenderingEngine {

	public void process (SiteDto siteDto,String pagePath,Writer writer);
	
}
