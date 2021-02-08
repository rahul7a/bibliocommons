package com.biblio.commons.takehome.repositories;

import com.biblio.commons.takehome.entities.Bookcopy;
import com.biblio.commons.takehome.models.CheckoutStatus;

public class BookRepository {

	public BookRepository() {
	}

	public boolean found(long bookId) {
		return true;
	}

	public boolean available(long bookId) {
		if (bookId == 10001) {
			return false;
		}
		return true;
	}

	public CheckoutStatus getCheckoutStatus(long userId, long bookId) {
		return CheckoutStatus.AVAILABLE;
	}

	public Bookcopy getAvailableBookCopy(long bookId) {
		// logic to figure out the oldest copy and assign it
		return new Bookcopy();
	}

}
