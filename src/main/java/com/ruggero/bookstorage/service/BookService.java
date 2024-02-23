package com.ruggero.bookstorage.service;

import com.ruggero.bookstorage.entities.Book;
import com.ruggero.bookstorage.entities.errorsandexception.IllegalBarcodeException;
import com.ruggero.bookstorage.entities.errorsandexception.RepeatedBarcodeException;
import com.ruggero.bookstorage.repos.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;

    public Book create(Book book) {
        validateBarcode(book.getBarcode());
        return repository.save(book);
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
}
