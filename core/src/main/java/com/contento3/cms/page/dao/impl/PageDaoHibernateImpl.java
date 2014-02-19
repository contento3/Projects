package com.contento3.cms.page.dao.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.contento3.cms.page.dao.PageDao;
import com.contento3.cms.page.model.Page;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class PageDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<Page,Integer>
implements PageDao {

	private static final String CACHE_REGION = "com.contento3.cms.page.model.Page";

	PageDaoHibernateImpl(){
		super(Page.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Page> findPageBySiteId(final Integer siteId) {
		Validate.notNull(siteId,"siteId cannot be null");
		Criteria criteria = this.getSession()
		.createCriteria(Page.class)
		.addOrder(Order.desc("title"))
		.add(Restrictions
		.eq("site.siteId", siteId));
		return criteria.list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public Page findPageByPathAndSiteId(final String path,final Integer siteId) {
		Validate.notNull(siteId,"siteId cannot be null");
		Validate.notNull(path,"path cannot be null");

		Criteria criteria = this.getSession()
		.createCriteria(Page.class)
		.add(Restrictions
		.eq("site.siteId", siteId)).add(Restrictions
		.eq("uri", path));

		Page page=null;
		if (!CollectionUtils.isEmpty(criteria.list())){
			page = (Page)criteria.list().get(0);
		}else if(path.indexOf(".uncached") != -1){
			String path1 = path.substring(0,path.indexOf(".uncached"));
			criteria = this.getSession()
			.createCriteria(Page.class)
			.add(Restrictions
			.eq("site.siteId", siteId)).add(Restrictions
			.eq("uri", path1));
			if (!CollectionUtils.isEmpty(criteria.list())){
				page = (Page)criteria.list().get(0);
			}
		}
		
		return page;
	}

	@Override
	public Page findPageByTitleAndSiteId(final String title,final Integer siteId){
		Validate.notNull(siteId,"siteId cannot be null");
		Validate.notNull(title,"title cannot be null");

		Criteria criteria = this.getSession()
		.createCriteria(Page.class)
		.add(Restrictions
		.eq("site.siteId", siteId)).add(Restrictions
		.eq("title", title));
		
		Page page=null;
		if (!CollectionUtils.isEmpty(criteria.list())){
			page = (Page)criteria.list().get(0);
		}	
		
		return page;
	}

	public Long findTotalPagesForSite(Integer siteId){
		Validate.notNull(siteId,"title cannot be null");

		Criteria criteria = this.getSession()
		.createCriteria(Page.class)
		.setProjection(Projections.rowCount())
		.add(Restrictions
		.eq("site.siteId", siteId));
		
		Long rowCount = 0L;
		
		List result = criteria.list();
		if (!result.isEmpty()) {
		rowCount = (Long) result.get(0);

		}
		return rowCount;
	}
	
	public Collection<Page> findNavigablePages(Integer siteId){
		Validate.notNull(siteId,"title cannot be null");

		Criteria criteria = this.getSession()
		.createCriteria(Page.class)
		.add(Restrictions
		.eq("site.siteId", siteId)).add(Restrictions
		.eq("isNavigable", 1));
		
		
		Long rowCount = 0L;
		
		return criteria.list();
	}

	@Override
	public Collection<Page> findPagesByCategory(Collection<Integer> categoryId,
			Integer accountId, Integer siteId) {
		
		// account id is available for future use, we might need 
		// multiple sites with same navigation than account id will be 
		// useful...
		Validate.notNull(categoryId,"categoryIds cannot be null");
		Validate.notNull(siteId,"siteIds cannot be null");
		
		Criteria criteria = this.getSession()
		.createCriteria(Page.class)
		.addOrder(Order.desc("title"));
		
		if (! CollectionUtils.isEmpty(categoryId)){
			criteria.createCriteria("categories","c")
			.add(Restrictions.in("c.categoryId", categoryId));
		}
		
		if ( siteId == null ){
			criteria.createCriteria("site","s")
			.add(Restrictions.eq("s.siteId", siteId));
		}
		
		return criteria.list();
	}
}
