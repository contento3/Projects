package com.contento3.dam.storagetype.dao;

import com.contento3.common.dao.GenericDao;
import com.contento3.dam.storagetype.model.StorageType;

/**
 * Data access layer for StorageType entity
 * @author Syed Muhammad Ali
 *
 */
public interface StorageTypeDao extends GenericDao<StorageType, Integer>{
	Object findByName(String name);
}
