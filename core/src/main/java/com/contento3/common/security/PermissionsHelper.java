package com.contento3.common.security;

import org.apache.shiro.SecurityUtils;

public class PermissionsHelper {

	public static Boolean isPermitted(final String permission){
		return SecurityUtils.getSubject().isPermitted(permission);
	}

}
