package com.jfeesoft.officeRest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jfeesoft.officeRest.model.Permission;
import com.jfeesoft.officeRest.repository.PermissionRepository;
import com.jfeesoft.officeRest.service.PermissionService;

@Component
@Transactional
public class PermissionServiceImpl extends GenericServiceImpl<Permission, Long> implements PermissionService {

	@Autowired
	public PermissionServiceImpl(PermissionRepository permissionRepository) {
		super(permissionRepository);

	}
}
