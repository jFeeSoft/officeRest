package com.jfeesoft.officeRest.repository.impl;

import org.hibernate.Criteria;

import com.jfeesoft.officeRest.model.Permission;
import com.jfeesoft.officeRest.repository.PermissionRepositoryCustom;

public class PermissionRepositoryImpl extends GenericRepositoryImpl<Permission>
		implements PermissionRepositoryCustom<Permission> {

	public PermissionRepositoryImpl() {
		super("permission", Permission.class);
	}

	@Override
	void createQuery(Criteria criteria) {

	}

}
