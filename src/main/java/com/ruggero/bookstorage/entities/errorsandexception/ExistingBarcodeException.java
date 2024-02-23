package com.ruggero.bookstorage.entities.errorsandexception;

import java.io.Serial;

public class ExistingBarcodeException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1L;
	public static final String REPEATED_BARCODE = "Corrupted data! More than one book with this barcode: %s";

	public ExistingBarcodeException(int barcode) {
		super(String.format(REPEATED_BARCODE, barcode));
	}
}