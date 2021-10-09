package com.ruggero.bookstorage.entities.errorsandexception;

public class NegativePriceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private double price;

	public NegativePriceException(String exception, double price) {
		super(exception);
		this.price = price;
	}

	public double getPrice() {
		return this.price;
	}
}