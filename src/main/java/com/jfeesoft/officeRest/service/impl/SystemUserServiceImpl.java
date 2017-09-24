package com.jfeesoft.officeRest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jfeesoft.officeRest.model.SystemUser;
import com.jfeesoft.officeRest.repository.SystemUserRepository;
import com.jfeesoft.officeRest.service.SystemUserService;

@Component
@Transactional
public class SystemUserServiceImpl extends GenericServiceImpl<SystemUser, Long> implements SystemUserService {

	@Autowired
	public SystemUserServiceImpl(SystemUserRepository systemUserRepository) {
		super(systemUserRepository);

	}
}
