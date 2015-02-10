package com.contento3.account.dto;


public class ModuleDto {

	private Integer moduleId;
	
	private String moduleName;

	private String description;

    private String uuid;
	
	private boolean enabled;
	
	private ModuleCategoryDto category;

	private String iconName;
	
	private String listenerClass;

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(final Integer moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(final String moduleName) {
		this.moduleName = moduleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(final String uuid) {
		this.uuid = uuid;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	public ModuleCategoryDto getCategory() {
		return category;
	}

	public void setCategory(final ModuleCategoryDto category) {
		this.category = category;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(final String iconName) {
		this.iconName = iconName;
	}

	public String getListenerClass() {
		return listenerClass;
	}

	public void setListenerClass(final String listenerClass) {
		this.listenerClass = listenerClass;
	}

}
