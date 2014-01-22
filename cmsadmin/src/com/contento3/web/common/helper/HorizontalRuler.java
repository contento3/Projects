package com.contento3.web.common.helper;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

public class HorizontalRuler extends Label {

	private static final long serialVersionUID = -9056448933018196838L;

	public HorizontalRuler() {
        super("<hr/>", ContentMode.HTML);
    }
}
