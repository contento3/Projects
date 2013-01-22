package com.contento3.dam.document.service;

import com.contento3.common.assembler.Assembler;
import com.contento3.dam.document.dto.DocumentTypeDto;
import com.contento3.dam.document.model.DocumentType;

/**
 * @author Syed Muhammad Ali
 * Assembles the entities of type DocumentType.
 * Converts from DocumentType to DocumentTypeDto and vice-versa.
 */

public interface DocumentTypeAssembler extends Assembler<DocumentTypeDto, DocumentType>{

}
