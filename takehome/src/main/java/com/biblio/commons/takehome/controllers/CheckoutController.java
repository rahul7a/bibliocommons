package com.biblio.commons.takehome.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.biblio.commons.takehome.models.CheckoutModel;
import com.biblio.commons.takehome.request.CheckoutRequest;
import com.biblio.commons.takehome.services.CheckoutService;

@Controller
public class CheckoutController {

	@Autowired
	private CheckoutService<CheckoutModel> checkoutService;

	@PostMapping("/users/{userid}/checkouts")
	CheckoutModel newEmployee(@PathVariable long userId, @RequestBody CheckoutRequest request) {

		long bookId = request.getData().get(0).getBook();

		return checkoutService.checkout(userId, bookId);
	}

}
