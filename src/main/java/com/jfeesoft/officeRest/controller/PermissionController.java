package com.jfeesoft.officeRest.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.jfeesoft.officeRest.model.Permission;
import com.jfeesoft.officeRest.service.PermissionService;

@RestController
@RequestMapping("/permission")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Collection<Permission> getAll() {
		Iterable<Permission> permissions = permissionService.findAll();
		return Lists.newArrayList(permissions);
	}
}
