package com.ruggero.bookstorage.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ruggero.bookstorage.entities.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
	public List<Book> findByBarcode(final int barcode); 
	public List<Book> findByQuantity(final int quantity);
	public List<Book> deleteById(final int barcode);
}