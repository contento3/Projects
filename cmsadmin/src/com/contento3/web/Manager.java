package com.contento3.web;

public enum Manager {
	Layout("layout"),
	Content("content"),
	Site("site"),
	Module("module"),
	Global("global"),
	Template("template"),
	User("user"),
	Category("category"),
	Article("article"),
	Document("article"),
	Image("article"),
	Dashboard("dashboard"),
	Newsletter("newsletter"),
	Subscription("subscription"),
	PageModules("pageModules"),
	SocialPublish("socialpublish");
	
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
