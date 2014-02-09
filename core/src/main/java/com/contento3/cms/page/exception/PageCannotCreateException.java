package com.contento3.cms.page.exception;

public class PageCannotCreateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String EXCEPTION_MSG_PAGE_CANT_CREATED = "The page uri you have used is a reserved contento3 keyword. Please use other value for your page uri.";

	
	/**
	 * Instantiates a new PageCannotCreateException.
	 * 
	 * @param context
	 *            the context
	 */
	public PageCannotCreateException() {
		super();
	}
	
	
	/**
	 * Instantiates a new PageCannotCreateException.
	 * 
	 * @param context
	 *            the context
	 * @param message
	 *            the message
	 */
	public PageCannotCreateException(final String message) {
		super(message);
	}

}
