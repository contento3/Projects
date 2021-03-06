package com.contento3.web.common.helper;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.util.CollectionUtils;

import com.contento3.common.dto.Dto;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;

/**
 * A generic class to load date in combobox.The DTO in the Collection 
 * passed must be implemented by {@link com.contento3.common.dto.Dto} 
 * for this work.
 * @author HAMMAD
 *
 */
public class ComboDataLoader implements IComboDataLoader<Dto> {

	/**
	 * Returns a Container with all the pageLayout.
	 * 
	 * @param pageLayoutList
	 * @return
	 */
	public IndexedContainer loadDataInContainer(final Collection<Dto> dtoList) {
		final IndexedContainer container = new IndexedContainer();
		container.addContainerProperty("name", String.class, null);
		container.addContainerProperty("value", Integer.class, null);

		if (!CollectionUtils.isEmpty(dtoList)){
			final Iterator<Dto> dtoIterator = dtoList.iterator();
	
			while (dtoIterator.hasNext()) {
				final Dto dto = dtoIterator.next();
				final Item pageLayoutItem = container.addItem(dto.getId());
				pageLayoutItem.getItemProperty("name").setValue(dto.getName());
				pageLayoutItem.getItemProperty("value").setValue(dto.getId());
			}
			
			container.sort(new Object[] { "name" }, new boolean[] { true });
		}
		return container;
	}

}
