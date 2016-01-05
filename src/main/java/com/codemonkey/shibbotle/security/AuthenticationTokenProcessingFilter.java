package com.codemonkey.shibbotle.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

	private static final String USER_PRINCIPAL_KEY = "shibbotle-user-%d";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		validateAuthenticationRequest(httpRequest, (HttpServletResponse) response);
		chain.doFilter(request, response);
	}

	private void validateAuthenticationRequest(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		String authToken = getAuthTokenFromRequest(httpRequest, ShibbotleAuthenticationHelper.SHIBBOTLE_USER_SESSION_COOKIE_NAME);
		
		if (validateToken(authToken, httpRequest.getHeader("User-Agent"), httpRequest.getRemoteAddr())) {
			Long userId = getUserIdFromToken(authToken);
			setUserRequestAuthorization(userId, USER_PRINCIPAL_KEY, findUserGrantedAuths(userId));
			httpResponse.addCookie(ShibbotleAuthenticationHelper.createShibbotleUserSessionCookie(userId, httpRequest.getHeader("User-Agent"), httpRequest.getRemoteAddr()));
			
		} else {
			httpResponse.addCookie(ShibbotleAuthenticationHelper.destroyCookie(ShibbotleAuthenticationHelper.SHIBBOTLE_USER_SESSION_COOKIE_NAME));
		}
	}

	private String getAuthTokenFromRequest(HttpServletRequest httpRequest, String cookieName) {
		if (httpRequest.getCookies() != null) {
			for (Cookie cookie : httpRequest.getCookies()) {
				if (cookie.getName().equals(cookieName)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	private List<GrantedAuthority> findUserGrantedAuths(Long userId) {
		Set<String> userAccessRoles = findAccessRoleKeys(userId);

		List<GrantedAuthority> grantedAuths = new ArrayList<>();
		for (String accessRole : userAccessRoles) {
			grantedAuths.add(new SimpleGrantedAuthority(accessRole));
		}
		
		return grantedAuths;
	}

	private Set<String> findAccessRoleKeys(Long userId) {
		return Collections.singleton("role");
	}

	private Long getUserIdFromToken(String authToken) {
		return 1l;
	}

	private boolean validateToken(String authToken, String header, String remoteAddr) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void setUserRequestAuthorization(Long userId, String principalKey, List<GrantedAuthority> grantedAuths) {
		SecurityContextHolder.getContext().setAuthentication(new RememberMeAuthenticationToken(String.format(principalKey, userId), userId, grantedAuths));
	}
	
}