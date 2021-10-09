package com.ruggero.bookstorage.service;

import java.util.ArrayList;
import java.util.List;

import com.ruggero.bookstorage.entities.*;
import com.ruggero.bookstorage.entities.errorsandexception.*;

public class Util {

	public Book getBookByBarcode(List<Book> list, int barcode) {
		if (barcode < 0)
			throw new IllegalBarcodeException("Barcode " + barcode + "cannot assume such value", barcode);
		List<Book> listByBarcode = new ArrayList<>();

		for (Book book : list)
			if (book.getBarcode() == barcode)
				listByBarcode.add(book);

		if (listByBarcode.size() == 0)
			throw new BookNotFoundException("No book with barcode = " + barcode + " was found", barcode);
		if (listByBarcode.size() == 1)
			return listByBarcode.get(0);
		else
			throw new RepeatedBarcodeInExistingRepositoryException(
					"Corrupted data! More than one book with this barcode: " + barcode, barcode);
	}

	public void validateQuantity(int quantity) {
		if (quantity < 0)
			throw new NegativeQuantityException("negative quantity", quantity);
	}

	public void validatePrice(double price) {
		if (price < 0)
			throw new NegativePriceException("negative price", price);
	}

	public boolean validateBarcode(int inputBarcode, List<Book> list) {

		if (inputBarcode < 0)
			throw new IllegalBarcodeException("Negative Barcode", inputBarcode);

		if (!list.isEmpty())
			for (Book bookint : list)
				if (bookint.getBarcode() == inputBarcode)
					throw new RepeatedBarcodeException("Barcode " + inputBarcode + " is already present", inputBarcode);

		return true;
	}

	public int validateReleaseYear(int releaseYear) {
		if (releaseYear < 0 || releaseYear > 1900)
			throw new IllegalReleaseYearException("Release years smaller than 0 or bigger than 1900 are not accepted",
					releaseYear);
		return releaseYear;
	}

	public int validateScienceIndex(int scienceIndex) {
		if (scienceIndex < 1 || scienceIndex > 10)
			throw new IllegalScienceIndexException("Science index must be an integer included between 1 and 10",
					scienceIndex);
		return scienceIndex;
	}

}