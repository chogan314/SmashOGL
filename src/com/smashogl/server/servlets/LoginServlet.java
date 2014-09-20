package com.smashogl.server.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.smashogl.persistence.daos.RememberMeUserDAO;
import com.smashogl.persistence.daos.UserDAO;
import com.smashogl.persistence.entities.RememberMeUser;
import com.smashogl.persistence.entities.User;
import com.smashogl.persistence.utils.PasswordHash;
import com.smashogl.server.util.SessionUtils;
import com.smashogl.web.Cookies;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		boolean rememberMe = "true".equals(req.getParameter("rememberMe"));
		
		User user = UserDAO.getInstance().get(username);
		
		if (user == null) {
			// send error message
		}
		
		try {
			if (!PasswordHash.validatePassword(password, user.getPasswordHash())) {
				// send error message
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			Logger.getLogger("servlets").log(Level.WARNING, "Failed to validate password:\n" + e.toString());
			throw new ServletException();
		}
		
		HttpSession session = req.getSession(true);
		SessionUtils.hardLogin(session, username);
		
		if (rememberMe) {
			RememberMeUser rmUser = new RememberMeUser(username);
			RememberMeUserDAO.getInstance().add(rmUser);
			Cookie cookie = new Cookie(Cookies.REMEMBER_ME_COOKIE_NAME, rmUser.getId().toString());
			resp.addCookie(cookie);
		}
	}
}
