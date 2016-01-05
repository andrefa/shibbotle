package com.codemonkey.shibbotle.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;

import com.codemonkey.shibbotle.service.ShibbotleService;

public class ShibbotleServiceImpl implements ShibbotleService {

	protected Long getLoggedUserId() {
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}

		return null;
	}

}