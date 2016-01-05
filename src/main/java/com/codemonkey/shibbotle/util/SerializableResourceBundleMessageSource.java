package com.codemonkey.shibbotle.util;

import java.util.Locale;
import java.util.Properties;

import javax.inject.Named;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Named(value = "messageSource")
public class SerializableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {

	public Properties getAllProperties(Locale locale) {
		clearCacheIncludingAncestors();
		return getMergedProperties(locale).getProperties();
	}

}