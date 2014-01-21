package com.contento3.site.user.service;

import com.contento3.common.service.StorableService;
import com.contento3.site.user.dto.UserDto;

public interface UserRegistrationService extends StorableService <UserDto> {
	
	void create(UserDto userDto,String domain);
}
