package com.contento3.security.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


/**
 * Role of a user or a group
 * @author hammad.afridi
 *
 */
public class Role {

	/**
	 * Id of the role
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ROLE_ID", length = 11, nullable = false, updatable = false)
	private Integer roleId;
	
	/**
	 * Name of the role
	 */
	@Column(name = "ROLE_NAME", nullable = false)
	private String roleName;
	
	/**
	 * Description for this role
	 */
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;
	
	/**
	 * Permissions for this role
	 */
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="ROLE_PERMISSION", 
                joinColumns={@JoinColumn(name="ROLE_ID")}, 
                inverseJoinColumns={@JoinColumn(name="PERMISSION_ID")})
    private Set<Permission> permissions = new HashSet<Permission>();
    
    /**
     * Gets the permissions
     * @return Set<Permission>
     */
	public Set<Permission> getPermissions() {
		return permissions;
	}

	/**
	 * Sets the permissions
	 * @param permissions
	 */
	public void setPermissions(final Set<Permission> permissions) {
		this.permissions = permissions;
	}

	/**
	 * Returns the role id
	 * @return Integer
	 */
	public Integer getRoleId() {
		return roleId;
	}

	/**
	 * Sets the role id
	 * @param roleId
	 */
	public void setRoleId(final Integer roleId) {
		this.roleId = roleId;
	}

	/**
	 * Gets the role name
	 * @return String
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * Sets the role name
	 * @param roleName
	 */
	public void setRoleName(final String roleName) {
		this.roleName = roleName;
	}

	/**
	 * Returns the description
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description
	 * @param description
	 */
	public void setDescription(final String description) {
		this.description = description;
	}


}
