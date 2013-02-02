package com.contento3.dam.storagetype.service;

import com.contento3.common.assembler.Assembler;
import com.contento3.dam.storagetype.dto.StorageTypeDto;
import com.contento3.dam.storagetype.model.StorageType;

/**
 * @author Syed Muhammad Ali
 * Assembles the entities of type StorageType.
 * Converts from StorageType to StorageType and vice-versa.
 */

public interface StorageTypeAssembler extends Assembler<StorageTypeDto, StorageType>{

}
