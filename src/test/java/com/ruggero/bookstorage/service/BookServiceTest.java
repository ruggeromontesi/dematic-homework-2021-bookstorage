package com.ruggero.bookstorage.service;

import com.ruggero.bookstorage.entities.Book;
import com.ruggero.bookstorage.repos.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    private static final String TEST_TITLE = "test_title";
    private static final String TEST_AUTHOR = "test_author";
    private static final int TEST_BARCODE = 1004588;
    private static final int TEST_QUANTITY = 11;
    private static final double TEST_PRICE = 43.2;
    @InjectMocks
    private BookService service;
    @Mock
    private BookRepository repository;

    @Test
    void shouldCreateBook_whenValidationOk() {
        Book book = getBook();

        Book result = service.create(book);

        verify(repository, times(1)).save(book);
        assertAll(() -> {
            assertThat(result.getTitle()).isEqualTo(book.getTitle());
        });


    }

    private Book getBook() {
        return Book.builder()
                .title(TEST_TITLE)
                .author(TEST_AUTHOR)
                .barcode(TEST_BARCODE)
                .quantity(TEST_QUANTITY)
                .price(TEST_PRICE)
                .build();
    }

}