package com.biblio.commons.takehome.services;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblio.commons.takehome.entities.Bookcopy;
import com.biblio.commons.takehome.entities.Checkout;
import com.biblio.commons.takehome.entities.User;
import com.biblio.commons.takehome.exceptions.AlreadyAssignedException;
import com.biblio.commons.takehome.exceptions.BookNotfoundexception;
import com.biblio.commons.takehome.exceptions.UserExceptions;
import com.biblio.commons.takehome.exceptions.UserNotfoundException;
import com.biblio.commons.takehome.models.CheckoutModel;
import com.biblio.commons.takehome.models.CheckoutStatus;
import com.biblio.commons.takehome.repositories.BookRepository;
import com.biblio.commons.takehome.repositories.CheckoutRepository;
import com.biblio.commons.takehome.repositories.UserRepository;
import com.biblio.commons.takehome.utilities.CheckoutUtilBuilder;

/**
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
 */
@Service
public class CheckoutServiceImpl implements CheckoutService<CheckoutModel> {

	private static final int CHECKOUT_QUOTA = 10;

	@Autowired
	private CheckoutRepository checkoutRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CheckoutUtilBuilder checkoutUtilBuilder;

	@Override
	public CheckoutModel checkout(long userId, long bookId) {

		if (userId <= 0 || bookId <= 0) {
			throw new IllegalArgumentException("userId or bookId is not valid.");
		}

		/*
		 * User does not exist
		 */
		if (!userRepository.isValidUser(userId)) {
			throw new UserNotfoundException("user not found.");
		}

		/*
		 * Book does not exists
		 */
		if (!bookRepository.found(bookId)) {
			throw new BookNotfoundexception("book not found.");
		}

		/*
		 * Book is unavailable
		 */
		if (!bookRepository.available(bookId)) {
			throw new BookNotfoundexception("book not available.");
		}

		User user = userRepository.getUser();

		/*
		 * User reached their quota
		 */
		if (user.getUserCheckoutCount() > CHECKOUT_QUOTA) {
			throw new UserExceptions("User reached checkout quota");
		}
		if (user.getUserCheckoutCount() == CHECKOUT_QUOTA) {
			// mark quota to full.
		}

		/*
		 * Check the status of the book for the user
		 */
		CheckoutStatus checkoutStatus = bookRepository.getCheckoutStatus(userId, bookId);

		/*
		 * Book is already checked out by user
		 */
		if (checkoutStatus == CheckoutStatus.CHECKEDOUT) {
			throw new AlreadyAssignedException("User already checkedout");
		}

		/*
		 * Book is available, gets an old copy
		 */
		Bookcopy bookcopy = bookRepository.getAvailableBookCopy(bookId);

		/*
		 * Build a relation between user and books.
		 */
		Checkout checkout = checkoutUtilBuilder.buildCheckout(userId, bookcopy);

		/*
		 * Create a relation between user and books into database.
		 */
		checkoutRepository.save(checkout);

		/*
		 * Mark the copy as checked out and update its timestamp at every checkout.
		 */
		checkout = checkoutUtilBuilder.updateCheckout(checkout.getCheckoutId(), CheckoutStatus.CHECKEDOUT,
				Instant.now());

		/*
		 * Update the checkout row
		 */
		checkoutRepository.update(checkout);

		/*
		 * Update the user quota and checkout count
		 */
		user = checkoutUtilBuilder.updateUser(user);

		/*
		 * Update the user row
		 */
		userRepository.update(user);

		/*
		 * Every thing is now validated and data is created and mapped, all it needs is
		 * to do is build and return checkout model
		 */
		return checkoutUtilBuilder.buildCheckoutModel(user, checkout);
	}

	@Override
	public CheckoutModel returnbook(long userId, long bookCopyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CheckoutModel returnbook(long userId, long bookCopyId, long checkoutId) {
		// TODO Auto-generated method stub
		return null;
	}

}
