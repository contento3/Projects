package com.contento3.account.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "MODULE", schema ="MODULES")
public class Module {

	@Id @GeneratedValue
	@Column(name = "MODULE_ID")
	private Integer moduleId;
	
	@Column(name = "NAME")
	private String moduleName;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(columnDefinition="TEXT", length = 100, name = "UUID",
			unique=true, nullable=false)
    private String uuid = UUID.randomUUID().toString();
	
	@Column(name = "IS_ENABLED")
	private boolean enabled;
	
	@OneToOne
	@JoinColumn(name = "MODULE_CATEGORY_ID")
	private ModuleCategory category;

	@Column(name = "ICON_NAME")
	private String iconName;
	
	@Column(name = "LISTENER_CLASS")
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

	public ModuleCategory getCategory() {
		return category;
	}

	public void setCategory(final ModuleCategory category) {
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
