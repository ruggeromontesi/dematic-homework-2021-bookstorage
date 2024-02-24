package com.ruggero.bookstorage.controller;

import com.ruggero.bookstorage.entities.Book;
import com.ruggero.bookstorage.entities.errorsandexception.IllegalBarcodeCriteriaException;
import com.ruggero.bookstorage.repos.BookRepository;
import com.ruggero.bookstorage.service.BookUseCase;
import com.ruggero.bookstorage.service.Util;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookRepository repository;
    private final BookUseCase bookService;

    Util util = new Util();

    /**
     * CREATE A client can use a REST call to put an ScienceJournal into the system
     * providing name, author, barcode, quantity and unit price
     */
    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@Valid @RequestBody Book book) {
        return bookService.create(book);
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
     * Book Total Price A client can use a REST call to calculate the total price of
     * specific books in the system given the barcode
     */
    @RequestMapping(value = "/{barcode}/totalprice", method = RequestMethod.GET)
    public double getTotalPriceByBarcode(@PathVariable("barcode") int barcode) {
        return util.getBookByBarcode(repository.findAll(), barcode).getTotalPrice();
    }

    /**
     * UPDATE:client can use this REST call to update book attributes providing the
     * barcode and updated field information.
     */
    @PutMapping(value = "/update")
    public ResponseEntity<Book> update(@RequestBody Book book) {
        Book updatedBook = bookService.updateBook(book);
        return ResponseEntity.ok(updatedBook);
    }

    @GetMapping(value = "/barcodelist/{criteria}")
    public TreeMap<Number, List<Integer>> getBarcodeList(@PathVariable("criteria") String criteria) {

        if (!criteria.equals("quantity") && !criteria.equals("totalprice"))
            throw new IllegalBarcodeCriteriaException("The typed criteria " + criteria + "is not allowed", criteria);

        TreeMap<Number, List<Integer>> map = new TreeMap<>();
        if (criteria.equals("quantity")) {
            for (Book book : repository.findAll()) {
                List<Book> bookList = repository.findByQuantity(book.getQuantity());
                List<Integer> barcodeList = new ArrayList<>();
                for (Book bookint : bookList)
                    barcodeList.add(bookint.getBarcode());
                map.put(book.getQuantity(), barcodeList);
            }
        }
        if (criteria.equals("totalprice")) {
            for (Book book : repository.findAll()) {
                double totalPrice = book.getTotalPrice();
                List<Book> bookList = new ArrayList<>();
                List<Integer> barcodeList = new ArrayList<>();
                for (Book bookint : repository.findAll())
                    if (bookint.getTotalPrice() == totalPrice)
                        bookList.add(bookint);
                for (Book bookint : bookList)
                    barcodeList.add(bookint.getBarcode());
                map.put(totalPrice, barcodeList);
            }
        }
        return map;
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

    /**
     * GET ALL Get the complete list of books
     */
    @GetMapping(value = "")
    public List<Book> getBooks() {
        return repository.findAll();
    }

    /**
     * DELETE:client can use a REST call to delete a Book providing barcode
     */
    @DeleteMapping(value = "/{barcode}")
    public void deleteByBarcode(@PathVariable("barcode") int barcode) {
        repository.deleteById(barcode);
    }

    /**
     * DELETE ALL
     */
    @DeleteMapping(value = "")
    public void deleteAll() {
        repository.deleteAll();
    }
}