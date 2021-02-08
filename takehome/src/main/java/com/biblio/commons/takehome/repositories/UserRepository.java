package com.biblio.commons.takehome.repositories;

import org.springframework.stereotype.Repository;

import com.biblio.commons.takehome.entities.User;

@Repository
public class UserRepository {

	/**
	 * Not the right way, but returning true or false for testing purpose.
	 */
	public UserRepository() {
	}

	public User getUser() {
		return new User();
	}

	public boolean isValidUser(long userId) {
		if (userId == 50001) {
			return false;
		}
		return true;
	}

	public void update(User user) {
		// update
		return;
	}

}
