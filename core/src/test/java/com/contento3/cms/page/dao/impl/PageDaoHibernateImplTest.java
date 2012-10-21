package com.contento3.cms.page.dao.impl;

import static org.junit.Assert.assertNotNull;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.contento3.account.dao.AccountDao;
import com.contento3.account.model.Account;
import com.contento3.cms.page.layout.dao.PageLayoutDao;
import com.contento3.cms.page.layout.dao.PageLayoutTypeDAO;
import com.contento3.cms.page.layout.model.PageLayout;
import com.contento3.cms.page.layout.model.PageLayoutType;
import com.contento3.cms.page.model.Page;
import com.contento3.cms.site.structure.dao.SiteDAO;
import com.contento3.cms.site.structure.model.Site;

@ContextConfiguration(locations={"/spring-hibernate-context-test.xml"})
public class PageDaoHibernateImplTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Resource(name="pageDAO")
	private PageDaoHibernateImpl unitUnderTest;

	@Resource(name="siteDAO")
	private SiteDAO siteDao;
	
	@Resource(name="accountDAO")
	private AccountDao accountDao;
		
	@Resource(name="pageLayoutDAO")
	private PageLayoutDao pageLayoutDao;
	
	@Resource(name="pageLayoutTypeDAO")
	private PageLayoutTypeDAO pageLayoutTypeDao;
	
	Integer pageId;
	
	@Before
	public void setUp() throws Exception {
		Account account = new Account();
		account.setEnabled(true);
		account.setName("testaccount");
		Integer accountId = accountDao.persist(account);

		PageLayoutType pageLayoutType = new PageLayoutType();
		pageLayoutType.setName("abc");
		pageLayoutType.setDescription("description test");
		Integer pltId = pageLayoutTypeDao.persist(pageLayoutType);
		
		PageLayout pageLayout = new PageLayout();
		pageLayout.setAccountId(accountId);
		pageLayout.setName("pagelayouttest");
		pageLayout.setLayoutType(pageLayoutTypeDao.findById(pltId));
		Integer pageLayoutId = pageLayoutDao.persist(pageLayout);
		
		Site site=new Site();
		site.setSiteId(99999);
		site.setLanguage("en");
		site.setSiteName("testsite");
		site.setAccount(accountDao.findById(accountId));
		site.setDefaultLayoutId(pageLayoutId);
		siteDao.persist(site);
		
		Page page = new Page();
		page.setTitle("testpage");
		page.setUri("/abc");
		page.setSite(site);
		
		page.setPageLayout(pageLayoutDao.findById(pageLayoutId));
		pageId = unitUnderTest.persist(page);
	}

	@Test
	public void testFindPageBySiteIdInteger() {
		Page page = unitUnderTest.findById(pageId);
		assertNotNull(page);
	}

}
