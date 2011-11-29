package com.olive.util;

import java.util.UUID;

/**
 * Class for generation of Unique ID
 * 
 * @author branislav.strasko, rastislav.lencses
 * 
 */
public final class UniqueIdentifier {

	/**
	 * singleton instance for the class
	 */
	private static UniqueIdentifier identifier = new UniqueIdentifier();

	/**
	 * private constructor
	 */
	private UniqueIdentifier() {
		super();
	}

	/**
	 * access to the singleton instance
	 * 
	 * @return
	 */
	public static UniqueIdentifier getUniqueIdentifier() {
		return identifier;
	}

	/**
	 * gets uniqueId as random value
	 * 
	 * @param paramUniqueId
	 * @return
	 */
	public String generateUniqueId(final String paramUniqueId) {
		/*
		 *  this is a randomly generated string that needs to be generated only
		 *  when the object is added, not when it's edited  
		 *    
		 */ 		
		String uniqueId;
		if (null == paramUniqueId || "".equals(paramUniqueId)) {
			final UUID uuid = UUID.randomUUID();
			uniqueId = Long.toString(Math.abs(uuid.getMostSignificantBits()),
					36)
					+ Long.toString(Math.abs(uuid.getLeastSignificantBits()),
							36);
		} else {
			uniqueId = paramUniqueId;
		}
		return uniqueId;
	}
}
