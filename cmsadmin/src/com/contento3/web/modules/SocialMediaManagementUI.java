package com.contento3.web.modules;

import java.util.HashMap;
import java.util.Map;

import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.modules.listener.SocialMediaItemClickListener;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.Action;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalSplitPanel;

public class SocialMediaManagementUI extends ModuleUI implements Button.ClickListener, Action.Handler {
 
	private static final long serialVersionUID = 1L;

	SpringContextHelper helper;

	final VerticalSplitPanel parentLayout;
	
	public SocialMediaManagementUI(final SpringContextHelper helper,final VerticalSplitPanel parentLayout){
		this.helper = helper;
		this.parentLayout = parentLayout;
	}
	
	@Override
	public Action[] getActions(Object target, Object sender) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handleAction(Action action, Object sender, Object target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		this.parentLayout.replaceComponent(this.parentLayout.getSecondComponent(),buildUI());
	}

	@Override
	public HorizontalSplitPanel buildUI() {
		final HierarchicalContainer hwContainer = new HierarchicalContainer();
        final Tree root = new Tree("",hwContainer);

		final UIManagerBuilder builder = new UIManagerBuilder();
		final HorizontalSplitPanel horiz = new HorizontalSplitPanel();
		
		builder.buildTree(new SocialMediaItemClickListener(root,horiz,helper), horiz, this, buildNavConstantPermissionMap());
		return horiz;
	}

	private Map<String,String> buildNavConstantPermissionMap(){
		final Map <String,String> map = new HashMap<String,String>();
		map.put("Publish", "SOCIAL_PUBLISH:NAVIGATION");
		return map;
	}
}
