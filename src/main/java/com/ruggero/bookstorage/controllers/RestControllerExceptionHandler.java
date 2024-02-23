package com.ruggero.bookstorage.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ruggero.bookstorage.entities.errorsandexception.BookNotFoundException;
import com.ruggero.bookstorage.entities.errorsandexception.FieldErrorResponse;
import com.ruggero.bookstorage.entities.errorsandexception.IllegalBarcodeCriteriaException;
import com.ruggero.bookstorage.entities.errorsandexception.IllegalBarcodeException;
import com.ruggero.bookstorage.entities.errorsandexception.IllegalReleaseYearException;
import com.ruggero.bookstorage.entities.errorsandexception.IllegalScienceIndexException;
import com.ruggero.bookstorage.entities.errorsandexception.NegativePriceException;
import com.ruggero.bookstorage.entities.errorsandexception.NegativeQuantityException;
import com.ruggero.bookstorage.entities.errorsandexception.NoSuchPropertyException;
import com.ruggero.bookstorage.entities.errorsandexception.NotAScienceJournalException;
import com.ruggero.bookstorage.entities.errorsandexception.NotAnAntiqueBookException;
import com.ruggero.bookstorage.entities.errorsandexception.RepeatedBarcodeException;

/**
 * Exception handler methods > naming policy : handle +
 * ExceptionName-"Exception"
 *
 * @author rugge
 *
 */

@ControllerAdvice
public class RestControllerExceptionHandler {

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, List<FieldErrorResponse>> handleMethodArgumentNotValid(
			MethodArgumentNotValidException exception) {
		return error(exception.getBindingResult().getFieldErrors().stream()
				.map(fieldError -> new FieldErrorResponse(fieldError.getField(), fieldError.getDefaultMessage()))
				.collect(Collectors.toList()));
	}

	private Map<String, List<FieldErrorResponse>> error(List<FieldErrorResponse> errors) {
		return Collections.singletonMap("errors", errors);
	}

	@ExceptionHandler(value = RuntimeException.class)
	public HttpEntity<String> handleException(RuntimeException exception) {
		if (exception instanceof BookNotFoundException)
			return new ResponseEntity<>(
					"No book found with barcode " + ((BookNotFoundException) exception).getBarcode(),
					HttpStatus.NOT_FOUND);
		if (exception instanceof IllegalBarcodeException)
			return new ResponseEntity<>("Barcode  cannot assume a negative value", HttpStatus.BAD_REQUEST);
		if (exception instanceof RepeatedBarcodeException)
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
		if (exception instanceof NoSuchPropertyException)
			return new ResponseEntity<>("The typed  property name: \""
					+ ((NoSuchPropertyException) exception).getPropertyName()
					+ "\" is  wrong. Acceptable property names are : {name, autor, quantity, price, releaseyear, scienceindex} ",
					HttpStatus.BAD_REQUEST);
		if (exception instanceof NumberFormatException)
			return new ResponseEntity<>("Parameter is not a parsable number", HttpStatus.BAD_REQUEST);
		if (exception instanceof NotAnAntiqueBookException)
			return new ResponseEntity<>("Book with barcode :" + ((NotAnAntiqueBookException) exception).getBarcode()
					+ " is not an antique book", HttpStatus.BAD_REQUEST);
		if (exception instanceof NotAScienceJournalException)
			return new ResponseEntity<>("Book with barcode :" + ((NotAScienceJournalException) exception).getBarcode()
					+ " is not a science journal", HttpStatus.BAD_REQUEST);
		if (exception instanceof IllegalBarcodeCriteriaException)
			return new ResponseEntity<>(
					"The typed criteria " + ((IllegalBarcodeCriteriaException) exception).getCriteria()
							+ " is wrong. Acceptable criteria are : {quantity, totalprice}",
					HttpStatus.BAD_REQUEST);
		if (exception instanceof NegativeQuantityException)
			return new ResponseEntity<>(
					"The typed value for quantity: " + ((NegativeQuantityException) exception).getQuantity()
							+ " is not correct. Quantity must be bigger than 0",
					HttpStatus.BAD_REQUEST);
		if (exception instanceof NegativePriceException)
			return new ResponseEntity<>("The typed value for price: " + ((NegativePriceException) exception).getPrice()
					+ " is not correct. Price must be bigger than 0", HttpStatus.BAD_REQUEST);
		if (exception instanceof IllegalScienceIndexException)
			return new ResponseEntity<>(
					"The typed value for science index: " + ((IllegalScienceIndexException) exception).getScienceIndex()
							+ " is not correct. Science index must be an integer between 1 and 10",
					HttpStatus.BAD_REQUEST);
		if (exception instanceof IllegalReleaseYearException)
			return new ResponseEntity<>(
					"The typed value for release year: " + ((IllegalReleaseYearException) exception).getReleaseYear()
							+ " is not correct. Release year must be an integer between 0 and 1900",
					HttpStatus.BAD_REQUEST);

		throw exception;
	}
}