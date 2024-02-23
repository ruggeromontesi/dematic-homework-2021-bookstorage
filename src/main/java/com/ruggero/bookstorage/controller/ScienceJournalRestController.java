package com.ruggero.bookstorage.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ruggero.bookstorage.entities.ScienceJournal;
import com.ruggero.bookstorage.repos.BookRepository;
import com.ruggero.bookstorage.service.Util;


@RequestMapping("/books")
@RestController
public class ScienceJournalRestController {

	@Autowired
	BookRepository repository;

	Util util = new Util();

	/**
	 * CREATE : a client can use a REST call to put an ScienceJournal into the
	 * system providing its name, author, barcode, quantity and unit price
	 */
	@PostMapping(value = "/sciencejournal")
	public ScienceJournal createScienceJournal(@Valid @RequestBody ScienceJournal scienceJournal) {
		util.validateBarcode(scienceJournal.getBarcode(), repository.findAll());
		return repository.save(scienceJournal);
	}
}