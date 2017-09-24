package com.jfeesoft.officeRest.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort.Direction;

public interface GenericService<T, K extends Serializable> {
	public T save(T entity);

	public void delete(T entity);

	public Iterable<T> findAll();

	List<T> load(int first, int pageSize, String sortField, Direction sortOrder, Map<String, Object> filters);

	Long countRepositoryFilter(Map<String, Object> filters);
}
