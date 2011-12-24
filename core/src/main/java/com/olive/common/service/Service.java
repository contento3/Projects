package com.olive.common.service;

import com.olive.common.exception.EnitiyAlreadyFoundException;

public interface Service<T> {

	void create(T dto) throws EnitiyAlreadyFoundException ;

}
