package com.smashogl.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Report {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String sourceUsername;
	private String targetUsername;
	private String details;

	public Report(String sourceUsername, String targetUsername, String details) {
		this.sourceUsername = sourceUsername;
		this.targetUsername = targetUsername;
		this.details = details;
	}
	
	public Long getId() {
		return id;
	}

	public String getSourceUsername() {
		return sourceUsername;
	}

	public String getTargetUsername() {
		return targetUsername;
	}

	public String getDetails() {
		return details;
	}
}
