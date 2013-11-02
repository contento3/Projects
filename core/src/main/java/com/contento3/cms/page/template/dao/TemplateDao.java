package com.contento3.cms.page.template.dao;

import java.util.Collection;

import com.contento3.cms.page.template.model.Template;
import com.contento3.common.dao.GenericDao;

public interface TemplateDao extends GenericDao<Template,Integer> {

	Collection<Template> findTemplateByDirectoryName(String name);

	Collection<Template> findTemplateByPathAndAccount(String templateName,String parentDirectory,String templateType,Integer accountId);

	Template findSystemTemplateForAccount(String templateCategory,
			Integer accountId, Boolean isGlobal);

	Template findGlobalSystemTemplate(String templateCategory);

	Collection<Template> findTemplateByDirectoryId(Integer id);


}
