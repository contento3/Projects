package com.contento3.site.user.service;

import com.contento3.common.service.Service;
import com.contento3.site.user.dto.UserDto;

public interface UserRegistrationService extends Service <UserDto> {
	
	void create(UserDto userDto,String domain);
}
