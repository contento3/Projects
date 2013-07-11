package com.contento3.web.common.helper;

import com.vaadin.ui.TabSheet;

/**
 * A utility class for TabSheet.
 * @author HAMMAD
 *
 */
public class TabSheetHelper {

	public static boolean  isTabLocked(final TabSheet tabSheet){
		boolean flag=false;
		if (tabSheet.getComponentCount()==10){
			flag=true;
		}
		return flag;
	}
}
