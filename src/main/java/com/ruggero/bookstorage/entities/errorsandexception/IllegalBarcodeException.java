package com.ruggero.bookstorage.entities.errorsandexception;

import java.io.Serial;

public class IllegalBarcodeException extends RuntimeException {
	public static final String NEGATIVE_BARCODE = "Negative Barcode";
	@Serial
	private static final long serialVersionUID = 1L;

	public IllegalBarcodeException(int barcode) {
		super(String.format(NEGATIVE_BARCODE, barcode));
	}
}