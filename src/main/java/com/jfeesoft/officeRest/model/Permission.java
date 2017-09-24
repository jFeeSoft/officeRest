package com.jfeesoft.officeRest.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "permission")
@AttributeOverride(name = "id", column = @Column(name = "permission_id", nullable = false))
@SequenceGenerator(name = "default_gen", sequenceName = "permission_seq", allocationSize = 1, initialValue = 100)
public class Permission extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "name", nullable = false, length = 64)
	private String name;

	// TODO Association
	@Column(name = "component", length = 64)
	private String component;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

}
