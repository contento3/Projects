package com.olive.common.exception;

public class ResourceNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new PageNotFoundException.
	 * 
	 * @param context
	 *            the context
	 */
	public ResourceNotFoundException() {
		super();
	}

	/**
	 * Instantiates a new PageNotFoundException.
	 * 
	 * @param context
	 *            the context
	 * @param message
	 *            the message
	 */
	public ResourceNotFoundException(final String message) {
		super(message);
	}

	/**
	 * Instantiates a new PageNotFoundException.
	 * 
	 * @param context
	 *            the context
	 * @param cause
	 * 		 *            the cause
	 */
	public ResourceNotFoundException(final Throwable cause) {
		super(cause);
	}

}
