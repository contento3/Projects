package com.contento3.dam.document.service;

import com.contento3.common.assembler.Assembler;
import com.contento3.dam.document.dto.DocumentDto;
import com.contento3.dam.document.model.Document;

/**
 * @author Syed Muhammad Ali
 * Assembles the entities of type Document.
 * Converts from Document to DocumentDto and vice-versa.
 */

public interface DocumentAssembler extends Assembler<DocumentDto, Document>{
	Document dtoToDomain(DocumentDto dto, Document domain);
}
