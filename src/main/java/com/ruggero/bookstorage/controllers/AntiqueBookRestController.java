package com.ruggero.bookstorage.controllers;

import com.ruggero.bookstorage.entities.AntiqueBook;
import com.ruggero.bookstorage.repos.BookRepository;
import com.ruggero.bookstorage.service.Util;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/books")
@RestController
@RequiredArgsConstructor
public class AntiqueBookRestController {

    private final BookRepository repository;

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