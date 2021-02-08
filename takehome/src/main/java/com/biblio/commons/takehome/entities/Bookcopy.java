package com.biblio.commons.takehome.entities;

import com.biblio.commons.takehome.models.CheckoutStatus;

public class Bookcopy {

	public Bookcopy() {
	}

	public CheckoutStatus getCheckoutStatus(long bookcopyId) {
		return CheckoutStatus.AVAILABLE;
	}

	public long getBookCopyId() {
		return 10001;
	}
}
