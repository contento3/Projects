package com.contento3.storagetype.dao.impl;

import static org.junit.Assert.*;

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
	
	Integer expectedId;
	String expectedName = "html";
	String expectedDesc = "type = text/html";
	Date expectedStartDate = new Date(System.currentTimeMillis());
	Date expectedEndDate = new Date(System.currentTimeMillis()+36000);
	
	@Before
	public void setUp() throws Exception{
		StorageType storage = new StorageType();

		storage.setName(expectedName);
		storage.setStartDate(expectedStartDate);
		storage.setEndDate(expectedEndDate);
		storage.setDescription(expectedDesc);
		
		expectedId = storageDao.persist(storage);
	}
	
	@Test
	public void testFindStorageTypeByName(){
		StorageType t = (StorageType) storageDao.findByName(expectedName);
		assertNotNull(t);
		assertEquals(expectedId.longValue(), t.getStorageId().longValue());
		assertEquals("Name check", expectedName, t.getName());
		assertEquals("Desc check", expectedDesc, t.getDescription());
		assertEquals("StartDate check", expectedStartDate, t.getStartDate());
		assertEquals("EndDate check", expectedEndDate, t.getEndDate());
	}
	
	@Test
	public void testFindById(){
		StorageType t = storageDao.findById(expectedId);
		assertNotNull(t);
	}
}
