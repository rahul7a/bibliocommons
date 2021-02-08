package com.biblio.commons.takehome.request;

public class CheckoutRequest {

	public CheckoutRequest() {
	}

	public Data getData() {
		return new Data();
	}

	public class Data {

		public Data() {
		}

		public BookReq get(int idx) {
			return new BookReq();
		}

		public class BookReq {
			public BookReq() {
			}

			public long getBook() {
				return 10001;
			}
		}

	}

}
