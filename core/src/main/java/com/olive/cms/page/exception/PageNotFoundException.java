package com.olive.cms.page.exception;


public class PageNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new PageNotFoundException.
	 * 
	 * @param context
	 *            the context
	 */
	public PageNotFoundException() {
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
	public PageNotFoundException(final String message) {
		super(message);
	}

	/**
	 * Instantiates a new PageNotFoundException.
	 * 
	 * @param context
	 *            the context
	 * @param cause
	 *            the cause
	 */
	public PageNotFoundException(final Throwable cause) {
		super(cause);
	}

}
