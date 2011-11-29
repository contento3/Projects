package com.olive.cms.page.section.dao;

import com.olive.cms.page.section.model.PageSectionType;
import com.olive.common.dao.GenericDao;

public interface PageSectionTypeDao  extends GenericDao<PageSectionType,Integer>{

	PageSectionType findByName(String name);
}
