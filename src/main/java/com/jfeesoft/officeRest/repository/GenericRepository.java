package com.jfeesoft.officeRest.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<T, K extends Serializable> extends CrudRepository<T, K> {

	public Long countRepositoryFilter(Map<String, Object> filters);

	public List<T> findRepositorySortFilterPage(int first, int pageSize, String sortField, Direction sortOrder,
			Map<String, Object> filters);
}
