package com.contento3.web.common.helper;

import java.util.Collection;

import com.vaadin.ui.Table;

/**
 * Utility to build display tables.
 * @author HAMMAD
 *
 * @param <T>
 */
public interface TableBuilder<T> {

	/**
	 * Used to build the table
	 * @param dto
	 * @return
	 */
	Table build(Collection <T> dto);
	
	/**
	 * Used to rebuild the table
	 * @param dto
	 * @return
	 */
	Table rebuild(Collection <T> dto);
}
