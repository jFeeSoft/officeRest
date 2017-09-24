package com.jfeesoft.officeRest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jfeesoft.officeRest.model.Role;
import com.jfeesoft.officeRest.repository.RoleRepository;
import com.jfeesoft.officeRest.service.RoleService;

@Component
@Transactional
public class RoleServiceImpl extends GenericServiceImpl<Role, Long> implements RoleService {

	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository) {
		super(roleRepository);

	}

}
