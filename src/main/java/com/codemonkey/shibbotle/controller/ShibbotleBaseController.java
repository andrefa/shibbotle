package com.codemonkey.shibbotle.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ShibbotleBaseController {

	private static final Logger LOG = LogManager.getLogger(ShibbotleBaseController.class);
	
	/**
	 * Default error handling for Shibbotle. This should be overloaded to handle other
	 * exceptions.
	 *
	 * @param exception any exception that may occur
	 * @return the exception, in JSON format
	 */
	@ResponseBody
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public Exception handleAllExceptions(Exception exception) {
		LOG.error(exception, exception);
		return exception;
	}
	
}