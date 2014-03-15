package com.contento3.cms.page.template.dao;

import java.util.Collection;

import com.contento3.cms.page.section.model.PageSectionTypeEnum;
import com.contento3.cms.page.template.model.PageTemplate;
import com.contento3.cms.page.template.model.PageTemplatePK;
import com.contento3.common.dao.GenericDao;

public interface PageTemplateDao extends GenericDao<PageTemplate,PageTemplatePK> {

	Collection<PageTemplate> findByPageAndPageSectionType(Integer page,
			Integer pageSectionType);

	Collection<PageTemplate> findByPageId(Integer pageId);

	Collection<PageTemplate> findByTemplateId(Integer templateId);

	Collection<PageTemplate> findByPageAndPageSectionType(Integer pageId,
			PageSectionTypeEnum pageSectionType);


}
