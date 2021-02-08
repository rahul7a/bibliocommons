package com.biblio.commons.takehome.entities;

public class User {
	private long userId;

	public User() {
		userId = 5000;
	}

	public long getUserId() {
		return this.userId;
	}
	
	public int getUserCheckoutCount() {
		return 10;
	}

}
