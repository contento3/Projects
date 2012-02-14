package com.contento3.common.exception;

public class EntityAlreadyFoundException extends Exception {

	private static final long serialVersionUID = 559529468009405804L;

	public EntityAlreadyFoundException() {
	
	}

	public EntityAlreadyFoundException(String msg) {
	    super(msg);
	}
}
