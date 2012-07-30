package com.contento3.web.common.helper;

import java.util.Collection;

import com.vaadin.data.util.IndexedContainer;

/**
 * Used to load based in the combo based 
 * on {@link java.util.Collection} passed.
 * 
 * @author HAMMAD
 *
 * @param <T>
 */
public interface IComboDataLoader<T> {

	IndexedContainer loadDataInContainer(Collection <T> type);
}
