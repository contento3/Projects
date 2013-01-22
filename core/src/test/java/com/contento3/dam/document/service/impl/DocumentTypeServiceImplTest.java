package com.contento3.dam.document.service.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.contento3.dam.document.dao.DocumentTypeDao;
import com.contento3.dam.document.dto.DocumentTypeDto;
import com.contento3.dam.document.model.DocumentType;
import com.contento3.dam.document.service.DocumentTypeService;

@ContextConfiguration(locations={"/spring-hibernate-context-test.xml"})
public class DocumentTypeServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests{
	@Resource(name="documentTypeService")
	DocumentTypeService documentTypeService;
	
	@Resource(name="documentTypeDAO")
	private DocumentTypeDao documentTypeDao;
	
	private String expectedName = "Test DocumentType Name";
	//private Integer expectedDocumentTypeId;
	
	@Before
	public void setUp() throws Exception{
		DocumentType documentType = new DocumentType();
		documentType.setName(expectedName);
		documentTypeDao.persist(documentType);
	}
	
	@Test
	public void testFindByName(){
		DocumentTypeDto documentTypeDto = documentTypeService.findByName(expectedName);
		
		assertNotNull(documentTypeDto);
	}
	
	@Test
	public void testFindAllTypes(){
		ArrayList<DocumentTypeDto> documentTypeList = (ArrayList<DocumentTypeDto>) documentTypeService.findAllTypes();
		
		assertTrue(!documentTypeList.isEmpty());
	}
}
