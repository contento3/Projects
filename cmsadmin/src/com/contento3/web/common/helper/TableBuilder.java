package com.contento3.web.common.helper;

import java.util.Collection;

/**
 * Utility to build display tables.
 * @author HAMMAD
 *
 * @param <T>
 */
public interface TableBuilder<K,T> {

	/**
	 * Used to build the table
	 * @param dto
	 * @return
	 */
	K build(Collection <T> dto);
	
	/**
	 * Used to rebuild the table
	 * @param dto
	 * @return
	 */
	K rebuild(Collection <T> dto);
}
