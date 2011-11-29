package com.olive.cms.page.template.dao;

import java.util.Collection;

import com.olive.cms.page.template.model.Template;
import com.olive.common.dao.GenericDao;

public interface TemplateDao extends GenericDao<Template,Integer> {

	Collection<Template> findTemplateByDirectoryName(String name);

}
