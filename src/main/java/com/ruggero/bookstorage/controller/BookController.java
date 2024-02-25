package com.ruggero.bookstorage.controller;

import com.ruggero.bookstorage.entities.Book;
import com.ruggero.bookstorage.service.BookUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookUseCase bookService;

    /**
     * CREATE A client can use a REST call to put an ScienceJournal into the system
     * providing name, author, barcode, quantity and unit price
     */
    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        Book savedBook = bookService.create(book);
        return ResponseEntity.ok(savedBook);
    }

    /**
     * A client can use a REST call to retrieve book’s information from a system by
     * providing its barcode.
     */
    @GetMapping(value = "/{barcode}")
    public ResponseEntity<Book> fetchBookByBarcode(@Valid @PathVariable("barcode") int barcode) {
        Book book = bookService.findByBarcode(barcode);
        return ResponseEntity.ok(book);
    }

    /**
     * Book Total Price
     * A client can use a REST call to calculate the total price of specific books in the system given the barcode
     */
    @RequestMapping(value = "/{barcode}/totalprice", method = RequestMethod.GET)
    public ResponseEntity<Double> getTotalPriceByBarcode(@PathVariable("barcode") int barcode) {
        double totalPrice = bookService.getTotalPriceByBarcode(barcode);
        return ResponseEntity.ok(totalPrice);
    }

    /**
     * UPDATE:client can use this REST call to update book attributes providing the barcode and updated field information.
     */
    @PutMapping(value = "/update")
    public ResponseEntity<Book> update(@RequestBody Book book) {
        Book updatedBook = bookService.updateBook(book);
        return ResponseEntity.ok(updatedBook);
    }

    @GetMapping("/barcodes/grouped/quantity")
    public ResponseEntity<Map<Integer, Set<Integer>>> barcodesGroupedByQuantity() {
        var result = bookService.getBarcodesGroupedByQuantity();
        return ResponseEntity.ok(result);
    }

    /* A client can use a REST call to request a list of all barcodes for the books in stock grouped by quantity
      o Optional – barcodes for each group sorted by total price */
    @GetMapping("/barcodes/grouped/quantity/totalprice")
    public ResponseEntity<Map<Integer, Set<Integer>>> barcodesGroupedByQuantityAndSortedByTotalPrice() {
        var result = bookService.getBarcodesGroupedByQuantityAndSortedByTotalPrice();
        return ResponseEntity.ok(result);
    }
}