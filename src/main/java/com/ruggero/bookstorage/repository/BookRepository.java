package com.ruggero.bookstorage.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ruggero.bookstorage.entities.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
	List<Book> findByBarcode(final int barcode);
	void deleteByBarcode(final int barcode);
}