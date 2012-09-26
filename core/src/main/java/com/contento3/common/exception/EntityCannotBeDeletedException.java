package com.contento3.common.exception;

public class EntityCannotBeDeletedException extends Exception {

	private static final long serialVersionUID = 559529468009405804L;

	public EntityCannotBeDeletedException(String msg) {
	    super(msg);
	}
}