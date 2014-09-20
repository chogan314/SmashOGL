package com.smashogl.persistence.daos;

import com.smashogl.persistence.entities.QueueEntry;

public final class QueueEntryDAO extends DAO<QueueEntry, Long> {
	private static QueueEntryDAO instance;
	
	private QueueEntryDAO() { 
		super(QueueEntry.class);
	}
	
	public static QueueEntryDAO getInstance() {
		if (instance == null) {
			instance = new QueueEntryDAO();
		}
		return instance;
	}	
	
	public void add(String username) {
		QueueEntry entry = new QueueEntry(username);
		add(entry);
	}
}
