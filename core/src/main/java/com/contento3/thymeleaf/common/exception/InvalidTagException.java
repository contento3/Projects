package com.contento3.thymeleaf.common.exception;

public class InvalidTagException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new InvalidTagException.
	 * 
	 * @param context
	 *            the context
	 */
	public InvalidTagException() {
		super();
	}

	/**
	 * Instantiates a new InvalidTagException.
	 * 
	 * @param context
	 *            the context
	 * @param message
	 *            the message
	 */
	public InvalidTagException(final String message) {
		super(message);
	}

	/**
	 * Instantiates a new InvalidTagException.
	 * 
	 * @param context
	 *            the context
	 * @param cause
	 *            the cause
	 */
	public InvalidTagException(final Throwable cause) {
		super(cause);
	}


}
