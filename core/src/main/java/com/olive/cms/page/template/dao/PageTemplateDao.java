package com.olive.cms.page.template.dao;

import java.util.Collection;

import com.olive.cms.page.template.model.PageTemplate;
import com.olive.cms.page.template.model.PageTemplatePK;
import com.olive.common.dao.GenericDao;

public interface PageTemplateDao extends GenericDao<PageTemplate,PageTemplatePK> {

	Collection<PageTemplate> findByPageAndPageSectionType(Integer page,
			Integer pageSectionType);


}
