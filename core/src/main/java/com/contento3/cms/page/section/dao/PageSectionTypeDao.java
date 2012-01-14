package com.contento3.cms.page.section.dao;

import com.contento3.cms.page.section.model.PageSectionType;
import com.contento3.common.dao.GenericDao;

public interface PageSectionTypeDao  extends GenericDao<PageSectionType,Integer>{

	PageSectionType findByName(String name);
}
