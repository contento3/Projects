package com.c3.email.marketing.newsletter.dao;

import java.util.Collection;

import com.c3.email.marketing.newsletter.model.Newsletter;
import com.contento3.common.dao.GenericDao;

public interface NewsletterDao extends GenericDao<Newsletter,Integer> {

	Collection<Newsletter> findByAccountId (Integer accountId);
}
