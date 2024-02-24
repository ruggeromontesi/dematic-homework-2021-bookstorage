package com.ruggero.bookstorage.service;

import com.ruggero.bookstorage.entities.Book;

import java.util.Map;
import java.util.Set;

public interface BookUseCase {
    Book create(Book book);
    Book findByBarcode(int barcode);
    Book updateBook(Book book);
    Map<Integer, Set<Integer>> getBarcodesGroupedByQuantity();
    Map<Integer, Set<Integer>> getBarcodesGroupedByQuantityAndSortedByTotalPrice();
}
