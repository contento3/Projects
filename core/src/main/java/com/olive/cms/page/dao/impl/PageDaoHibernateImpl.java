package com.olive.cms.page.dao.impl;

import java.awt.print.Pageable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.olive.cms.page.dao.PageDao;
import com.olive.cms.page.model.Page;
import com.olive.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class PageDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<Page,Integer>
implements PageDao {

	PageDaoHibernateImpl(){
		super(Page.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Page> findPageBySiteId(Integer siteId,Integer pageNumber,Integer pageSize) {
		Criteria criteria = this.getSession()
		.createCriteria(Page.class)
		.setFirstResult(pageSize * (pageNumber-1 ))
		.setMaxResults(pageSize)
		.addOrder(Order.desc("title"))
		.add(Restrictions
		.eq("site.siteId", siteId));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Page> findPageBySiteId(Integer siteId) {
		Criteria criteria = this.getSession()
		.createCriteria(Page.class)
		.addOrder(Order.desc("title"))
		.add(Restrictions
		.eq("site.siteId", siteId));
		return criteria.list();
	}
	
	public Long findTotalPagesForSite(Integer siteId){
		Criteria criteria = this.getSession()
		.createCriteria(Page.class)
		.setProjection(Projections.rowCount())
		.add(Restrictions
		.eq("site.siteId", siteId));
		
		Long rowCount = 0L;
		
		List result = criteria.list();
		if (!result.isEmpty()) {
		rowCount = (Long) result.get(0);
		System.out.println("Total records: " + rowCount);

	//http://www.kodejava.org/examples/397.html
//	.setProjection(Projections.rowCount());
	//https://forum.hibernate.org/viewtopic.php?t=974802 	 
		}
		return rowCount;
	}
}
