package com.contento3.dam.document.service.impl;

import static org.junit.Assert.*;

import java.util.Collection;

import javax.annotation.Resource;

import org.junit.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.contento3.account.dao.impl.AccountDaoHibernateImpl;
import com.contento3.account.model.Account;
import com.contento3.dam.document.dao.impl.DocumentDaoHibernateImpl;
import com.contento3.dam.document.dao.impl.DocumentTypeDaoHibernateImpl;
import com.contento3.dam.document.dto.DocumentDto;
import com.contento3.dam.document.model.Document;
import com.contento3.dam.document.model.DocumentType;
import com.contento3.dam.document.service.DocumentService;
import com.contento3.dam.storagetype.dao.impl.StorageTypeHibernateImpl;
import com.contento3.dam.storagetype.model.StorageType;

@ContextConfiguration(locations={"/spring-hibernate-context-test.xml"})
public class DocumentServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Resource(name="documentServiceImpl")
	DocumentService documentService;

	@Resource(name="documentDAO")
	private DocumentDaoHibernateImpl documentDao;
	
	@Resource(name="documentTypeDAO")
	private DocumentTypeDaoHibernateImpl documentTypeDao;
	
	@Resource(name="accountDAO")
	private AccountDaoHibernateImpl accountDao;
	
	@Resource(name="storageDAO")
	private StorageTypeHibernateImpl storageDao;
	
	String  expectedUuid, 
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
		document.setDocumentTitle("Test Document Title");
		document.setDocumentType(documentType);
		document.setStorageType(storage);
		document.setAccount(account);
		expectedDocumentId = documentDao.persist(document);
		
		expectedUuid = document.getDocumentUuid();
	}
	
	@Test
	public void testFindByUuid(){
		DocumentDto documentDto = documentService.findByUuid(expectedAccountId, expectedUuid);
		
		assertNotNull(documentDto);
	}
	
	@Test
	public void testFindByType(){
		Collection<DocumentDto> documentDtos = documentService.findByType(expectedAccountId, DocTypeName);
		
		assertTrue(!documentDtos.isEmpty());
	}
	
	@Test
	public void testFindByAccountId(){
		Collection<DocumentDto> documentDtos = documentService.findByAccountId(expectedAccountId);
		
		assertTrue(!documentDtos.isEmpty());
	}
}
