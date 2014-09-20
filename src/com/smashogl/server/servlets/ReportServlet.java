package com.smashogl.server.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smashogl.persistence.daos.ReportDAO;
import com.smashogl.persistence.entities.Report;
import com.smashogl.server.util.SessionUtils;

@SuppressWarnings("serial")
public class ReportServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String username = SessionUtils.getUsername(req.getSession());
		String target = req.getParameter("target");
		String details = req.getParameter("details");
		
		Report report = new Report(username, target, details);
		ReportDAO.getInstance().add(report);
	}
}
