package com.contento3.common.exception;

public class EntityNotFoundException extends Exception {

	private static final long serialVersionUID = 559529468009405804L;

	public EntityNotFoundException() {
	}

	public EntityNotFoundException(String msg) {
	    super(msg);
	}
}
