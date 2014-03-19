package com.contento3.web.site.seo.listener;

import com.contento3.cms.seo.dto.MetaTagDto;

public interface SEOListener {

	public void editAttribute(MetaTagDto dto);
	
	public void deleteAttribute(MetaTagDto dto);
}
