package com.ruggero.bookstorage.entities.errorsandexception;

public class IllegalBarcodeCriteriaException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String criteria;

	public IllegalBarcodeCriteriaException(String exception, String criteria) {
		super(exception);
		this.criteria = criteria;
	}

	public String getCriteria() {
		return criteria;
	}
}