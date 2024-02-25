package com.ruggero.bookstorage.integration;

import com.ruggero.bookstorage.entities.Book;
import com.ruggero.bookstorage.service.BookUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BookIntegrationTest {
    private static final String BOOKS_CREATE_URL = "http://localhost:8080/books/create";
    @Qualifier("bookService")
    @Autowired
    private BookUseCase service;

    @Test
    void shouldCreateBook() {
        RestTemplate restTemplate = new RestTemplate();

        Book book = Book.builder()
                .title("Java A Beginner's Guide")
                .author("Schildt")
                .barcode(11)
                .quantity(10)
                .price(1.0)
                .build();

        HttpEntity<Book> request = new HttpEntity<>(book);
        Book response = restTemplate.postForObject(BOOKS_CREATE_URL, request, Book.class);

        List<Book> books = service.findAll();

        assertThat(books).hasSize(1);
        assertAll(
                () -> assertThat(response).isNotNull(),
                () -> assertThat(books).hasSize(1),
                () -> assertThat(books.get(0)).isEqualTo(book)
        );
    }

}
