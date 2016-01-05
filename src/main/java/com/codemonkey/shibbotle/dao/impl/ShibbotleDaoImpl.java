package com.codemonkey.shibbotle.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;

import com.codemonkey.shibbotle.dao.ShibbotleDao;
import com.codemonkey.shibbotle.domain.ShibbotleEntity;

public class ShibbotleDaoImpl<T extends ShibbotleEntity> implements ShibbotleDao<T> {

	private Class<T> entityClass;

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public ShibbotleDaoImpl() {
		this.entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> list() {
		return getSession().createCriteria(this.entityClass).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T findById(Long entityId) {
		return (T) getSession().get(entityClass, entityId);
	}

	protected Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	protected EntityManager getEntityManager() {
		return this.entityManager;
	}

	protected Class<T> getEntityClass() {
		return entityClass;
	}

}