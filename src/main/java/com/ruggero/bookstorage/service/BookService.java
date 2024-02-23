package com.ruggero.bookstorage.service;

import com.ruggero.bookstorage.entities.Book;
import com.ruggero.bookstorage.entities.errorsandexception.BookNotFoundException;
import com.ruggero.bookstorage.entities.errorsandexception.ExistingBarcodeException;
import com.ruggero.bookstorage.entities.errorsandexception.IllegalBarcodeException;
import com.ruggero.bookstorage.entities.errorsandexception.RepeatedBarcodeException;
import com.ruggero.bookstorage.repos.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;

    public Book create(Book book) {
        validate(book.getBarcode());
        return repository.save(book);
    }

    private void validate(int inputBarcode) {
        validateBarcode(inputBarcode);
        validateBookWithSameBarcodeIsNotPresent(inputBarcode);
    }

    private void validateBarcode(int inputBarcode) {
        if (inputBarcode < 0) {
            throw new IllegalBarcodeException(inputBarcode);
        }
    }

    private void validateBookWithSameBarcodeIsNotPresent(int inputBarcode) {
        repository.findByBarcode(inputBarcode).stream()
                .findAny()
                .ifPresent(book -> {
                    throw new RepeatedBarcodeException(inputBarcode);
                });
    }

    public Book findByBarcode(int barcode) {
        validateBarcode(barcode);
        List<Book> books = repository.findByBarcode(barcode);
        if (books.isEmpty()) {
            throw new BookNotFoundException(barcode);
        }
        if (books.size() > 1) {
            throw new ExistingBarcodeException(barcode);
        }
        return books.stream().findFirst().orElseThrow();
    }

    public Book updateBook(Book book) {
        Book retrievedBook = findByBarcode(book.getBarcode());
        Book updatedBook = Book.builder()
                .title(book.getTitle() == null ? retrievedBook.getTitle() : book.getTitle())
                .author(book.getAuthor() == null ? retrievedBook.getAuthor() : book.getAuthor())
                .barcode(retrievedBook.getBarcode())
                .quantity(book.getQuantity() == null ? retrievedBook.getQuantity() : book.getQuantity())
                .price(book.getPrice() == null ? retrievedBook.getPrice() : book.getPrice())
                .build();

        return repository.save(updatedBook);
    }

    public Map<Integer, Set<Integer>> getBarcodesGroupedByQuantity() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(Book::getQuantity, TreeMap::new,
                        Collectors.mapping(Book::getBarcode, Collectors.toSet()))
                );
    }

    public Map<Integer, Set<Integer>> getBarcodesGroupedByQuantityAndSortedByTotalPrice() {
        Map<Integer, List<Book>> booksByQuantity = repository.findAll().stream()
                .collect(Collectors.groupingBy(Book::getQuantity));

        Map<Integer, Set<Integer>> result = new TreeMap<>();

        for (Integer qty : booksByQuantity.keySet()) {
            TreeSet<Book> sortedByTotalPrice = new TreeSet<>(Comparator.comparingDouble(Book::getTotalPrice));
            sortedByTotalPrice.addAll(booksByQuantity.get(qty));
            result.put(qty, sortedByTotalPrice.stream().map(Book::getBarcode).collect(Collectors.toSet()));
        }

        return result;
    }
}
