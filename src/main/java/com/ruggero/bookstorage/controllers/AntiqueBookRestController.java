package com.ruggero.bookstorage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruggero.bookstorage.entities.AntiqueBook;
import com.ruggero.bookstorage.repos.BookRepository;
import com.ruggero.bookstorage.service.Util;

import javax.validation.Valid;

@RequestMapping("/books")
@RestController
public class AntiqueBookRestController {

	@Autowired
	BookRepository repository;

	Util util = new Util();

	/**
	 * CREATE A client can use a REST call to put an AntiqueBook into the system
	 * providing name, author, barcode, quantity and unit price
	 */
	@PostMapping(value = "/antiquebook/")
	public AntiqueBook createAntiqueBook(@Valid @RequestBody AntiqueBook antiqueBook) {
		util.validateBarcode(antiqueBook.getBarcode(), repository.findAll());
		return repository.save(antiqueBook);
	}
}