package com.smashogl.persistence.daos;

import com.smashogl.persistence.entities.Report;

public final class ReportDAO extends DAO<Report, Long> {
	private static ReportDAO instance;
	
	private ReportDAO() {
		super(Report.class);
	}
	
	public static ReportDAO getInstance() {
		if (instance == null) {
			instance = new ReportDAO();
		}
		
		return instance;
	}
}
