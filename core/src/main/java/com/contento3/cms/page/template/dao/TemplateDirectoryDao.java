package com.contento3.cms.page.template.dao;

import java.util.Collection;

import com.contento3.cms.page.template.model.TemplateDirectory;
import com.contento3.common.dao.GenericDao;

/**
 * Data access layer for a template directory entity
 * @author : Hammad Afridi
 * Created : 10/19/2011
 */

public interface TemplateDirectoryDao extends GenericDao<TemplateDirectory,Integer> {

	Collection<TemplateDirectory> findRootDirectories(boolean isGlobal);

	TemplateDirectory findByName(String name, boolean isGlobal);

	Collection<TemplateDirectory> findChildDirectories(Integer parentId);
	
}
