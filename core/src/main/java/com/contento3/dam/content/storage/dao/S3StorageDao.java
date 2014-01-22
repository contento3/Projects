package com.contento3.dam.content.storage.dao;

import com.contento3.dam.content.storage.S3Storage;
import com.contento3.common.dao.GenericDao;

public interface S3StorageDao extends GenericDao<S3Storage, Integer> {

	public S3Storage findByAccountId(Integer accountId);
}
