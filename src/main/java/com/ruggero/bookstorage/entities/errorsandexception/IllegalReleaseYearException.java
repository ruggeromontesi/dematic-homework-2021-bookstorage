package com.ruggero.bookstorage.entities.errorsandexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Release year between 0 and 1900")
public class IllegalReleaseYearException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private int releaseYear;

	public IllegalReleaseYearException(String exception, int releaseYear) {
		super(exception);
		this.releaseYear = releaseYear;
	}

	public int getReleaseYear() {
		return releaseYear;
	}
}