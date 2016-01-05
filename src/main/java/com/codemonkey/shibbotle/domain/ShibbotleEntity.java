package com.codemonkey.shibbotle.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class ShibbotleEntity implements Serializable {

	public abstract Long getEntityId();

}