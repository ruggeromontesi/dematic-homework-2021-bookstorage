package com.ruggero.bookstorage.entities.errorsandexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "ScienceIndex between 1 and 10")
public class IllegalScienceIndexException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private int scienceIndex;

	public IllegalScienceIndexException(String exception, int scienceIndex) {
		super(exception);
		this.scienceIndex = scienceIndex;
	}

	public int getScienceIndex() {
		return scienceIndex;
	}
}