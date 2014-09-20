package com.smashogl.server.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smashogl.persistence.daos.LimboUserDAO;
import com.smashogl.persistence.entities.LimboUser;

@SuppressWarnings("serial")
public class CleanLimboUsersServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		List<LimboUser> limboUsers = LimboUserDAO.getInstance().asList();
		
		for (LimboUser limboUser : limboUsers) {
			if (limboUser.age() > 5) {
				LimboUserDAO.getInstance().remove(limboUser.getId());
			}
		}
	}
}
