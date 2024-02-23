package com.ruggero.bookstorage.service;

import com.ruggero.bookstorage.entities.Book;
import com.ruggero.bookstorage.entities.errorsandexception.BookNotFoundException;
import com.ruggero.bookstorage.entities.errorsandexception.ExistingBarcodeException;
import com.ruggero.bookstorage.entities.errorsandexception.IllegalBarcodeException;
import com.ruggero.bookstorage.entities.errorsandexception.RepeatedBarcodeException;
import com.ruggero.bookstorage.repos.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

        repository.findByBarcode(inputBarcode).stream()
                .findAny()
                .ifPresent(book -> {
                    throw new RepeatedBarcodeException(inputBarcode);
                });
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
}
