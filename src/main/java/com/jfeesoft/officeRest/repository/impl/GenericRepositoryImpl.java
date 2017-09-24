package com.jfeesoft.officeRest.repository.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Sort.Direction;

public abstract class GenericRepositoryImpl<T> {

	private final String criteriaAlias;
	private final String criteriaAliasId;
	private final Class<T> typeParameterClass;

	public GenericRepositoryImpl(String criteriaAlias, Class<T> typeParameterClass) {
		super();
		this.criteriaAlias = criteriaAlias;
		this.criteriaAliasId = criteriaAlias + ".id";
		this.typeParameterClass = typeParameterClass;
	}

	@PersistenceContext
	private EntityManager em;

	public Long countRepositoryFilter(Map<String, Object> filters) {
		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(typeParameterClass, criteriaAlias);

		createQuery(criteria);
		addWhereCriteria(criteria, filters);

		criteria.setProjection(Projections.countDistinct(criteriaAliasId));
		return (Long) criteria.uniqueResult();
	}

	public List<T> findRepositorySortFilterPage(int first, int pageSize, String sortField, Direction sortOrder,
			Map<String, Object> filters) {
		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(typeParameterClass, criteriaAlias);
		createQuery(criteria);
		addWhereCriteria(criteria, filters);

		if (sortField != null && sortOrder != null) {
			if (sortOrder.equals(Direction.ASC)) {
				criteria.addOrder(Order.asc(sortField));
			} else {
				criteria.addOrder(Order.desc(sortField));
			}
		} else {
			criteria.addOrder(Order.desc(criteriaAliasId));
		}
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);

		return criteria.list();
	}

	abstract void createQuery(Criteria criteria);

	/*
	 * private void createQuery(Criteria criteria) {
	 * criteria.setFetchMode("smsUzytkownik", FetchMode.JOIN);
	 * criteria.setFetchMode("smsStatus", FetchMode.JOIN);
	 * criteria.setFetchMode("smsUzytkownik.puUzytkownik", FetchMode.JOIN);
	 * criteria.createAlias("smsUzytkownik.puUzytkownik", "puUzytkownik");
	 * criteria.createAlias("smsStatus", "smsStatus");
	 * criteria.createAlias("smsUzytkownik", "smsUzytkownik");
	 * criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); }
	 */

	private void addWhereCriteria(Criteria criteria, Map<String, Object> filters) {
		if (filters != null && !filters.isEmpty()) {
			for (Entry<String, Object> entry : filters.entrySet()) {
				if (entry.getValue() instanceof String) {
					criteria.add(Restrictions.like(entry.getKey(), (String) entry.getValue(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.ge(entry.getKey(), entry.getValue()));
				}
			}
		}
	}

}
