package com.ruggero.bookstorage.service;

import com.ruggero.bookstorage.entities.Book;
import com.ruggero.bookstorage.repos.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RuggeroBookService implements BookUseCase {
    private final BookRepository repository;

    @Override
    public Book create(Book book) {
        return null;
    }

    @Override
    public Book findByBarcode(int barcode) {
        return null;
    }

    @Override
    public Book updateBook(Book book) {
        return null;
    }

    @Override
    public double getTotalPriceByBarcode(int barcode) {
        return 0;
    }

    @Override
    public Map<Integer, Set<Integer>> getBarcodesGroupedByQuantity() {

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

        Function<Map<Integer, Set<Book>>, Map<Integer, Set<Integer>>> finisher = integerSetMap -> {
            Map<Integer, Set<Integer>> sortedBooks = new HashMap<>();
            for (int qty : integerSetMap.keySet()) {
                Set<Integer> a = integerSetMap.get(qty).stream().map(Book::getBarcode).collect(Collectors.toSet());
                sortedBooks.put(qty, a);
            }
            return sortedBooks;
        };
        Collector<Book, Map<Integer, Set<Book>>, Map<Integer, Set<Integer>>> collector = Collector.of(
                supplier,
                accumulator,
                combiner,
                finisher);

        return repository.findAll().stream().collect(collector);
    }

    @Override
    public Map<Integer, List<Integer>> getBarcodesGroupedByQuantityAndSortedByTotalPrice() {
        return null;
    }

    @Override
    public List<Book> findAll() {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteById(int barcode) {

    }
}
