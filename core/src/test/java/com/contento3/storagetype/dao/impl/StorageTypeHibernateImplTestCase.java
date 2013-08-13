package com.contento3.storagetype.dao.impl;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.contento3.dam.storagetype.dao.impl.StorageTypeHibernateImpl;
import com.contento3.dam.storagetype.model.StorageType;

@ContextConfiguration(locations={"/spring-hibernate-context-test.xml"})
public class StorageTypeHibernateImplTestCase extends AbstractTransactionalJUnit4SpringContextTests {
	@Resource(name="storageDAO")
	private StorageTypeHibernateImpl storageDao;
	
	String expectedName = "html";
	String expectedDesc = "type = text/html";
	Date expectedStartDate = new Date(System.currentTimeMillis());
	Date expectedEndDate = new Date(System.currentTimeMillis()+36000);
	
	@Before
	public void setUp() throws Exception{
		StorageType storage = new StorageType();

		storage.setName(expectedName);
		storage.setStart_date(expectedStartDate);
		storage.setEnd_date(expectedEndDate);
		storage.setDescription(expectedDesc);
		
		storageDao.persist(storage);
	}
	
	@Test
	public void testFindStorageTypeByName(){
		StorageType t = (StorageType) storageDao.findByName(expectedName);
		assertNotNull(t);
	}
}
