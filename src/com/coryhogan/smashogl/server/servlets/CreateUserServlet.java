package com.coryhogan.smashogl.server.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coryhogan.smashogl.persistence.daos.UserDAO;
import com.coryhogan.smashogl.persistence.utils.PasswordHash;
import com.coryhogan.smashogl.server.util.SessionUtils;

public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// TODO: Real implementation
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String emailAddress = req.getParameter("emailAddress");
		
		if (UserDAO.getInstance().get(username) != null) {
			req.setAttribute("usernameTaken", true);
			req.getRequestDispatcher("/login.jsp").forward(req, resp);
		}
		
		try {
			String passwordHash = PasswordHash.createHash(password);
			UserDAO.getInstance().add(username, passwordHash, emailAddress);
			HttpSession session = req.getSession(true);
			SessionUtils.hardLogin(session, username);
			//resp.sendRedirect("/index.jsp");
			req.getRequestDispatcher("/index.jsp").forward(req, resp);
			
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			Logger.getLogger("servlets").log(Level.WARNING, "Failed to hash password:\n" + e.toString());
			throw new ServletException();
		}
	}
}
