package com.jfeesoft.officeRest.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort.Direction;

import com.jfeesoft.officeRest.repository.GenericRepository;
import com.jfeesoft.officeRest.service.GenericService;

public abstract class GenericServiceImpl<T, K extends Serializable> implements GenericService<T, K> {

	protected GenericRepository<T, K> repository;

	public GenericServiceImpl(GenericRepository organisationRepository) {
		this.repository = organisationRepository;
	}

	public T save(T entity) {
		return repository.save(entity);
	}

	public void delete(T entity) {
		repository.delete(entity);
	}

	public Iterable<T> findAll() {
		return repository.findAll();
	}

	public Long countRepositoryFilter(Map<String, Object> filters) {
		return repository.countRepositoryFilter(filters);
	}

	public List<T> load(int first, int pageSize, String sortField, Direction sortOrder, Map<String, Object> filters) {
		return repository.findRepositorySortFilterPage(first, pageSize, sortField, sortOrder, filters);
	}
}
