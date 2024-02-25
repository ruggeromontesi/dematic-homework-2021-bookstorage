package com.ruggero.bookstorage.service;

import com.ruggero.bookstorage.entities.Book;
import com.ruggero.bookstorage.entities.errorsandexception.BookNotFoundException;
import com.ruggero.bookstorage.entities.errorsandexception.ExistingBarcodeException;
import com.ruggero.bookstorage.entities.errorsandexception.IllegalBarcodeException;
import com.ruggero.bookstorage.entities.errorsandexception.RepeatedBarcodeException;
import com.ruggero.bookstorage.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component("bookService")
@RequiredArgsConstructor
public class BookService implements BookUseCase {
    private final BookRepository repository;

    @Override
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

    @Override
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

    @Override
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

    @Override
    public double getTotalPriceByBarcode(int barcode) {
        Book book = findByBarcode(barcode);
        return book.getQuantity() * book.getPrice();
    }

    @Override
    public Map<Integer, Set<Integer>> getBarcodesGroupedByQuantity() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(Book::getQuantity, TreeMap::new,
                        Collectors.mapping(Book::getBarcode, Collectors.toSet()))
                );
    }

    @Override
    public Map<Integer, List<Integer>> getBarcodesGroupedByQuantityAndSortedByTotalPrice() {
        return repository.findAll().stream().collect(getBookMapMapCollector());
    }

    private static Collector<Book, Map<Integer, Set<Book>>, Map<Integer, List<Integer>>> getBookMapMapCollector() {
        Supplier<Map<Integer, Set<Book>>> supplier = HashMap::new;

        BiConsumer<Map<Integer, Set<Book>>, Book> accumulator = (mapContainer, book) -> {
            mapContainer.computeIfAbsent(book.getQuantity(), qty -> new TreeSet<>(Comparator.comparingDouble(Book::getTotalPrice)));
            mapContainer.get(book.getQuantity()).add(book);
        };

        BinaryOperator<Map<Integer, Set<Book>>> combiner = (m1, m2) -> {
            for (Integer qty : m2.keySet()) {
                m1.computeIfAbsent(qty, missingKey -> {
                    Set<Book> value = new TreeSet<>(Comparator.comparingDouble(Book::getTotalPrice));
                    value.addAll(m2.get(qty));
                    return value;
                });
                m1.get(qty).addAll(m2.get(qty));

            }
            return m1;
        };

        Function<Map<Integer, Set<Book>>, Map<Integer, List<Integer>>> finisher = integerSetMap -> {
            Map<Integer, List<Integer>> sortedBooks = new HashMap<>();
            for (int qty : integerSetMap.keySet()) {
                List<Integer> barcodeList = integerSetMap.get(qty).stream().map(Book::getBarcode).collect(Collectors.toList());
                sortedBooks.put(qty, barcodeList);
            }
            return sortedBooks;
        };
        return Collector.of(supplier, accumulator, combiner, finisher);
    }

    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public void deleteById(int barcode) {
        repository.deleteByBarcode(barcode);
    }
}
