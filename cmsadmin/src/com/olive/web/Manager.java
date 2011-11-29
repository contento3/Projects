package com.olive.web;

public enum Manager {
	Layout("layout"),
	Content("content"),
	Site("site"),
	Module("module"),
	Global("global"),
	Template("template");
	
	private String managerName;
	
	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	Manager(String managerName){
		this.managerName = managerName;
	}
}
