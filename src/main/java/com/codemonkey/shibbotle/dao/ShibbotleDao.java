package com.codemonkey.shibbotle.dao;

import java.util.List;

import com.codemonkey.shibbotle.domain.ShibbotleEntity;

public interface ShibbotleDao<T extends ShibbotleEntity> {

	List<T> list();

	T findById(Long entityId);

}