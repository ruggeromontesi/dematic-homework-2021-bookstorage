package com.ruggero.bookstorage.entities.errorsandexception;

public class RepeatedBarcodeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private int barcode;

	public RepeatedBarcodeException(String exception, int barcode) {
		super(exception);
		this.barcode = barcode;
	}

	public int getBarcode() {
		return barcode;
	}
}