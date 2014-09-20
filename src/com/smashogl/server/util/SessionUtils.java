package com.smashogl.server.util;

import javax.servlet.http.HttpSession;

public final class SessionUtils {	
	public static final String USERNAME = "username";
	public static final String HARD_LOGIN = "hardLogin";
	
	private SessionUtils() { }
	
	public static void softLogin(HttpSession session, String username) {
		session.setAttribute(USERNAME, username);
		session.setAttribute(HARD_LOGIN, false);
	}
	
	public static void hardLogin(HttpSession session, String username) {
		session.setAttribute(USERNAME, username);
		session.setAttribute(HARD_LOGIN, true);
	}
	
	public static boolean isLoggedIn(HttpSession session) {
		return session.getAttribute(USERNAME) != null;
	}
	
	public static boolean isHardLoggedIn(HttpSession session) {
		return session.getAttribute(HARD_LOGIN) != null &&
				(boolean) session.getAttribute(HARD_LOGIN);
	}
	
	public static String getUsername(HttpSession session) {
		return (String) session.getAttribute(USERNAME);
	}
}
