package com.contento3.cms.page.template.dao;

import java.util.Collection;

import com.contento3.cms.page.template.model.Template;
import com.contento3.common.dao.GenericDao;

public interface TemplateDao extends GenericDao<Template,Integer> {

        Collection<Template> findTemplateByDirectoryName(String name);

        Collection<Template> findTemplateByNameAndAccount(String templateName,String parentDirectory,String templateType,Integer accountId);

        Template findTemplateByPathAndAccount(String path,String templateName,Integer accountId);

        Template findSystemTemplateForAccount(String templateCategory,
                        Integer accountId, Boolean isGlobal);

        Template findGlobalSystemTemplate(String templateCategory);

        Collection<Template> findTemplateByDirectoryId(Integer id);

		Template findTemplateByKeyAndAccount(String templateKey,
				String contentType, Integer accountId);

}