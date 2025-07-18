package com.ruggero.bookstorage.repository;

import com.ruggero.bookstorage.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenericBookRepository<T extends Book> extends JpaRepository<T,Integer> {
    List<Book> findByBarcode(final int barcode);
    void deleteByBarcode(final int barcode);
}
