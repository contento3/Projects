package com.contento3.dam.image.dao;

import java.util.Collection;

import com.contento3.common.dao.GenericDao;
import com.contento3.dam.image.model.Image;

public interface ImageDao extends GenericDao<Image,Integer>{

	Collection<Image> findByAccountId(Integer accountId);

}
