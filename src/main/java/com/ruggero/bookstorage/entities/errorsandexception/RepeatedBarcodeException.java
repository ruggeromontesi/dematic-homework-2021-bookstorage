package com.ruggero.bookstorage.entities.errorsandexception;

import java.io.Serial;

public class RepeatedBarcodeException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1L;

	public static final String BARCODE_ALREADY_PRESENT = "Barcode  %s + is already present";


	public RepeatedBarcodeException(int barcode) {
		super(String.format(BARCODE_ALREADY_PRESENT, barcode));
	}
}