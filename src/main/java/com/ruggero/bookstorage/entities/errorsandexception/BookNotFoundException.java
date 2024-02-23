package com.ruggero.bookstorage.entities.errorsandexception;

import java.io.Serial;

public class BookNotFoundException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1L;
	public static final String BOOK_NOT_FOUND = "No book with barcode %s was found.";

	public BookNotFoundException(int barcode) {
		super(String.format(BOOK_NOT_FOUND, barcode));
	}
}