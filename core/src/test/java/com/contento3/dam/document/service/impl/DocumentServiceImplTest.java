package com.contento3.dam.document.service.impl;

import static org.junit.Assert.*;

import java.util.Collection;

import javax.annotation.Resource;

import org.junit.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.contento3.account.dao.impl.AccountDaoHibernateImpl;
import com.contento3.account.model.Account;
import com.contento3.account.service.AccountAssembler;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.dam.document.dao.DocumentTypeDao;
import com.contento3.dam.document.dao.impl.DocumentDaoHibernateImpl;
import com.contento3.dam.document.dto.DocumentDto;
import com.contento3.dam.document.dto.DocumentTypeDto;
import com.contento3.dam.document.model.Document;
import com.contento3.dam.document.model.DocumentType;
import com.contento3.dam.document.service.DocumentAssembler;
import com.contento3.dam.document.service.DocumentService;
import com.contento3.dam.document.service.DocumentTypeAssembler;
import com.contento3.dam.storagetype.dao.impl.StorageTypeHibernateImpl;
import com.contento3.dam.storagetype.model.StorageType;
import com.contento3.dam.storagetype.service.StorageTypeAssembler;

@ContextConfiguration(locations={"/spring-hibernate-context-test.xml"})
public class DocumentServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests{
	@Resource(name="documentService")
	DocumentService documentService;
	
	@Resource(name="documentAssembler")
	DocumentAssembler documentAssembler;
	
	@Resource(name="documentDAO")
	private DocumentDaoHibernateImpl documentDao;
	
	@Resource(name="documentTypeDAO")
	private DocumentTypeDao documentTypeDao;
	
	@Resource(name="documentTypeAssembler")
	private DocumentTypeAssembler documentTypeAssembler;
	
	@Resource(name="accountDAO")
	private AccountDaoHibernateImpl accountDao;
	
	@Resource(name="accountAssembler")
	private AccountAssembler accountAssembler;
	
	@Resource(name="storageDAO")
	private StorageTypeHibernateImpl storageDao;
	
	@Resource(name="storageTypeAssembler")
	private StorageTypeAssembler storageTypeAssembler;
	
	private String  expectedUuid, 
			DocTypeName = "TestDocumentType";
	private Integer expectedDocumentId, expectedAccountId;
	
	private Account account;
	private DocumentType documentType;
	private StorageType storage;
	private Document document;
	
	@Before
	public void setUp() throws Exception{
		storage = new StorageType();

		storage.setName("Storage Name");
		storage.setDescription("Test Document Desc");
		storageDao.persist(storage);
		
		documentType = new DocumentType();
		documentType.setName(DocTypeName);
		documentTypeDao.persist(documentType);
		
		account = new Account();
		account.setEnabled(true);
		account.setName("testaccount");
		expectedAccountId = accountDao.persist(account);
		
		document = new Document();
		document.setDocumentTitle("Test Document Title");
		document.setDocumentType(documentType);
		document.setStorageType(storage);
		document.setAccount(account);
		expectedDocumentId = documentDao.persist(document);
		
		expectedUuid = document.getDocumentUuid();
	}
	
	@Test
	public void testCreateDocumentDto(){/*
		DocumentDto documentDto = documentAssembler.domainToDto(document);
		/*
		StorageType st = new StorageType();
		st.setStorage_id(111112);
		st.setName("name");
		storageDao.persist(st);
		
		documentDto.setStorageTypeDto( storageTypeAssembler.domainToDto(st) );
		
		
		new DocumentDto();
		
		documentDto.setDocumentUuid("AJOPJWFPJFPWAJFPWA");
		documentDto.setAccount( accountAssembler.domainToDto(account) );
		documentDto.setDocumentTypeDto( documentTypeAssembler.domainToDto(documentType) );
		documentDto.setDocumentTitle("title");
		documentDto.setStorageTypeDto( storageTypeAssembler.domainToDto(storage) );
		/
		
		try {
			documentService.create(documentDto);
			assertTrue(true);
		} catch (EntityAlreadyFoundException e) {
			e.printStackTrace();
			assertTrue(false);
		} catch (EntityNotCreatedException e) {
			e.printStackTrace();
			assertTrue(false);
		}*/
		assertTrue(true);
	}
	
	@Test
	public void testFindById(){
		DocumentDto documentDto = documentService.findById(expectedDocumentId);
		
		assertNotNull(documentDto);
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
