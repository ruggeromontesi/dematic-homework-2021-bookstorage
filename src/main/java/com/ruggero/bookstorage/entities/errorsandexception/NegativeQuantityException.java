package com.ruggero.bookstorage.entities.errorsandexception;

public class NegativeQuantityException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private int quantity;

	public NegativeQuantityException(String exception, int quantity) {
		super(exception);
		this.quantity = quantity;
	}

	public int getQuantity() {
		return this.quantity;
	}
}