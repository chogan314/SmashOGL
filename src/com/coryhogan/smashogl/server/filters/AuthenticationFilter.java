package com.coryhogan.smashogl.server.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coryhogan.smashogl.persistence.daos.RememberMeUserDAO;
import com.coryhogan.smashogl.persistence.entities.RememberMeUser;
import com.coryhogan.smashogl.server.util.SessionUtils;
import com.coryhogan.smashogl.web.Cookies;

public class AuthenticationFilter implements Filter {

	@Override
	public void destroy() { }

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession(true);
		
		if (!SessionUtils.isLoggedIn(session)) {
			String rmId = Cookies.getCookieValue(request, Cookies.REMEMBER_ME_COOKIE_NAME);
			
			if (rmId != null) {
				RememberMeUser rmUser = RememberMeUserDAO.getInstance().get(Long.parseLong(rmId));
				if (rmUser != null) {
					SessionUtils.softLogin(session, rmUser.getUsername());
				}
			}

			if (!SessionUtils.isLoggedIn(session)) {
				response.sendRedirect("/login.jsp");
			}			
		}
		
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException { }

}
