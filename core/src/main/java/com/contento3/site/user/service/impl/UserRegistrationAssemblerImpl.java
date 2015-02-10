package com.contento3.site.user.service.impl;

import java.util.Collection;

import com.contento3.site.user.dto.UserDto;
import com.contento3.site.user.model.User;
import com.contento3.site.user.service.UserRegistrationAssembler;

public class UserRegistrationAssemblerImpl implements UserRegistrationAssembler {

	@Override
	public User dtoToDomain(final UserDto dto,final User domain) {
		domain.setFirstName(dto.getFirstName());
		domain.setLastName(dto.getLastName());
		domain.setMiddleName(dto.getMiddleName());
		domain.setPassword(dto.getPassword());
		domain.setPasswordReminder(dto.getPasswordReminder());
		domain.setUsername(dto.getUsername());
		return domain;
	}

	@Override
	public UserDto domainToDto(final User domain, final UserDto dto) {
		dto.setFirstName(domain.getFirstName());
		dto.setLastName(domain.getLastName());
		dto.setMiddleName(domain.getMiddleName());
		dto.setPassword(domain.getPassword());
		dto.setPasswordReminder(domain.getPasswordReminder());
		dto.setUsername(domain.getUsername());
		return dto;
	}

	@Override
	public Collection<UserDto> domainsToDtos(final Collection<User> domains) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<User> dtosToDomains(final Collection<UserDto> dtos) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
