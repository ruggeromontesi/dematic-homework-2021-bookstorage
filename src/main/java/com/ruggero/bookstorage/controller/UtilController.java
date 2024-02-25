package com.ruggero.bookstorage.controller;

import com.ruggero.bookstorage.entities.Book;
import com.ruggero.bookstorage.repos.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class UtilController {
    private final BookRepository repository;

    @GetMapping(value = "")
    public List<Book> getBooks() {
        return repository.findAll();
    }

    @DeleteMapping(value = "/delete/all")
    public void deleteAll() {
        repository.deleteAll();
    }

    @DeleteMapping(value = "/{barcode}")
    public void deleteByBarcode(@PathVariable("barcode") int barcode) {
        repository.deleteById(barcode);
    }
}
