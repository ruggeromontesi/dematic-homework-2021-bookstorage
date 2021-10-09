package com.ruggero.bookstorage.entities.errorsandexception;

public class NoSuchPropertyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String propertyName;

	public NoSuchPropertyException(String exception, String propertyName) {
		super(exception);
		this.propertyName = propertyName;
	}

	public String getPropertyName() {
		return propertyName;
	}
}