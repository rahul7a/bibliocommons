package com.biblio.commons.takehome.services;

public interface CheckoutService<T> {

	/**
	 * 
	 * The system should be successful if a user checks out an available ebook. •
	 * The system should keep track of which copy is checked out to which user. The
	 * system should always assign the user the oldest available copy. It should
	 * mark the copy as checked out and update its timestamp at every checkout. •
	 * The system should return an error for a user or an ebook that doesn’t exist.
	 * • The system should return an error if the ebook is checked out to the user
	 * already. • The system should keep track of a user’s checkout quota. A user
	 * can only have a maximum of 10 checkouts. The system should return an error if
	 * the user is about to exceed their checkout quota. • The system should return
	 * an error if the ebook is unavailable.
	 *
	 *
	 * @param userId
	 * @param bookId
	 * @return
	 */
	T checkout(long userId, long bookId);

	T returnbook(long userId, long bookCopyId);

	T returnbook(long userId, long bookCopyId, long checkoutId);
}
