package com.biblio.commons.takehome.utilities;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.biblio.commons.takehome.entities.Bookcopy;
import com.biblio.commons.takehome.entities.Checkout;
import com.biblio.commons.takehome.entities.User;
import com.biblio.commons.takehome.models.CheckoutModel;
import com.biblio.commons.takehome.models.CheckoutStatus;

@Component
public class CheckoutUtilBuilder {

	public CheckoutUtilBuilder() {
	}

	public Checkout buildCheckout(long userId, Bookcopy bookCopyId) {
		return new Checkout();
	}

	public Checkout updateCheckout(long checkoutId, CheckoutStatus status, Instant currentTime) {
		return new Checkout();
	}

	public User updateUser(User user) {
		return user;
	}

	public CheckoutModel buildCheckoutModel(User user, Checkout checkout) {
		return new CheckoutModel();
	}

}
