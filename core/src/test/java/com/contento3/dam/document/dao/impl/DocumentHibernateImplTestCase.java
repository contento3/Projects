package com.contento3.dam.document.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.contento3.account.dao.impl.AccountDaoHibernateImpl;
import com.contento3.account.model.Account;
import com.contento3.dam.document.model.Document;
import com.contento3.dam.document.model.DocumentType;
import com.contento3.dam.storagetype.dao.impl.StorageTypeHibernateImpl;
import com.contento3.dam.storagetype.model.StorageType;

@ContextConfiguration(locations={"/spring-hibernate-context-test.xml"})
public class DocumentHibernateImplTestCase extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Resource(name="documentDAO")
	private DocumentDaoHibernateImpl documentDao;
	
	@Resource(name="documentTypeDAO")
	private DocumentTypeDaoHibernateImpl documentTypeDao;
	
	@Resource(name="accountDAO")
	private AccountDaoHibernateImpl accountDao;
	
	@Resource(name="storageDAO")
	private StorageTypeHibernateImpl storageDao;
	
	String  expectedUuid, expectedTitle = "Test Document Title", 
			DocTypeName = "TestDocumentType";
	Integer expectedDocumentId, expectedAccountId;
	
	@Before
	public void setUp() throws Exception{
		StorageType storage = new StorageType();

		storage.setName("Storage Name");
		storage.setDescription("Test Document Desc");
		storageDao.persist(storage);
		
		DocumentType documentType = new DocumentType();
		documentType.setName(DocTypeName);
		documentTypeDao.persist(documentType);
		
		Account account = new Account();
		account.setEnabled(true);
		account.setName("testaccount");
		expectedAccountId = accountDao.persist(account);
		
		Document document = new Document();
		document.setDocumentTitle(expectedTitle);
		document.setDocumentType(documentType);
		document.setStorageType(storage);
		document.setAccount(account);
		expectedDocumentId = documentDao.persist(document);
		
		expectedUuid = document.getDocumentUuid();
	}
	
	@Test
	public void testFindByAccountId(){
		Collection<Document> docList = documentDao.findByAccountId(expectedAccountId);
		assertTrue(!docList.isEmpty());
	}
	
	@Test
	public void testFindByType(){
		Collection<Document> docList = documentDao.findByType(expectedAccountId, DocTypeName);
		assertTrue(!docList.isEmpty());
	}
	
	@Test
	public void testFindByUuid(){
		Document doc = documentDao.findByUuid(expectedAccountId, expectedUuid);
		assertNotNull(doc);
	}
	
	@Test
	public void documentTypeTestFindByName(){
		DocumentType docType = documentTypeDao.findByName(DocTypeName);
		assertNotNull(docType);
	}
	
	@Test
	public void testFindByTitle(){
		Document doc = documentDao.findByTitle(expectedTitle);
		assertNotNull(doc);
	}
}
