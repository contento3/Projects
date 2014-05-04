package com.contento3.site.page.pathbuilder;

import java.util.Collection;

/**
 * Builds the page path
 * @author hamakhaa
 *
 * @param <T>
 */
public interface PathBuilder<T> {

	void build(T list);
	
}
