package com.codemonkey.shibbotle.security;

import javax.servlet.http.Cookie;

public class ShibbotleAuthenticationHelper {

	public static final String SHIBBOTLE_USER_SESSION_COOKIE_NAME = "shus";

	public static Cookie createShibbotleUserSessionCookie(Long shibbotleUserId, String userAgent, String userIp) {
		String authToken = generateShibbotleUserAuthenticationToken(shibbotleUserId, userAgent, userIp);
		return createCookie(SHIBBOTLE_USER_SESSION_COOKIE_NAME, authToken, 3600 * 2);
	}

	public static Cookie destroyCookie(String cookieName) {
		return createCookie(cookieName, null, 0);
	}
	
	private static String generateShibbotleUserAuthenticationToken(Long shibbotleUserId, String userAgent, String userIp) {
		return Long.toOctalString(shibbotleUserId);
	}

	private static Cookie createCookie(String cookieName, String cookieValue, int maxAge) {
		Cookie usCookie = new Cookie(cookieName, cookieValue);
		usCookie.setPath("/");
		usCookie.setMaxAge(maxAge);
		return usCookie;
	}

}