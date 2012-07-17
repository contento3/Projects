package com.contento3.web.common.helper;

import java.util.Collection;

import com.vaadin.data.util.IndexedContainer;

public interface IComboDataLoader<T> {

	IndexedContainer loadDataInContainer(Collection <T> type);
}
