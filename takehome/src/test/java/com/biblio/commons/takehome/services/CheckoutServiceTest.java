package com.biblio.commons.takehome.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.biblio.commons.takehome.exceptions.AlreadyAssignedException;
import com.biblio.commons.takehome.exceptions.BookNotfoundexception;
import com.biblio.commons.takehome.exceptions.UserExceptions;
import com.biblio.commons.takehome.exceptions.UserNotfoundException;
import com.biblio.commons.takehome.models.CheckoutModel;

/**
 * Unit test for {@link CheckoutService}
 * 
 * @author rahul
 *
 */
public class CheckoutServiceTest {

	CheckoutService<CheckoutModel> service = new CheckoutServiceImpl();

	@Test
	public void whenInputsAreIllegal() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			service.checkout(0, 0);
		});
		String expectedMessage = "userId or bookId is not valid.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void whenUserIsNotfound() {
		Exception exception = assertThrows(UserNotfoundException.class, () -> {
			service.checkout(10007, 30003);
		});
		String expectedMessage = "user not found.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void whenBookIsNotfound() {
		Exception exception = assertThrows(BookNotfoundexception.class, () -> {
			service.checkout(10007, 30001);
		});
		String expectedMessage = "book not found.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void whenBookIsUnavailable() {
		Exception exception = assertThrows(BookNotfoundexception.class, () -> {
			service.checkout(10007, 30009);
		});
		String expectedMessage = "book not available.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void whenUserReachedQuota() {
		Exception exception = assertThrows(UserExceptions.class, () -> {
			service.checkout(10007, 30013);
		});
		String expectedMessage = "User reached checkout quota.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void whenUserAlreadyCheckedout() {
		Exception exception = assertThrows(AlreadyAssignedException.class, () -> {
			service.checkout(10007, 30013);
		});
		String expectedMessage = "user already checkedout.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void userSuccessfullyCheckedout() {
		CheckoutModel model = service.checkout(10000, 30008);

		assertNotNull(model);
		// Additional assertions will be followed.
	}
}
