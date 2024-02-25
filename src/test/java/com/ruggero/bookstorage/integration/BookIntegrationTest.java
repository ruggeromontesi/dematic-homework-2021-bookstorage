package com.ruggero.bookstorage.integration;

import com.ruggero.bookstorage.entities.Book;
import com.ruggero.bookstorage.service.BookUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.ruggero.bookstorage.service.TestHelper.BARCODE_1;
import static com.ruggero.bookstorage.service.TestHelper.getBook;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BookIntegrationTest {
    private static final String BASE_URL = "http://localhost:8080/books";
    @Qualifier("bookService")
    @Autowired
    private BookUseCase service;

    @Test
    void shouldCreateBook() {
        RestTemplate restTemplate = new RestTemplate();
        Book book = getBook();

        Book response = restTemplate.postForObject(BASE_URL + "/create", new HttpEntity<>(book), Book.class);

        List<Book> books = service.findAll();

        assertAll(
                () -> assertThat(response).isNotNull(),
                () -> assertThat(books).hasSize(1),
                () -> {
                    assert books != null;
                    assertThat(books.get(0)).isEqualTo(book);
                }
        );

        service.deleteAll();
    }

    @Test
    void shouldGetBook() {
        Book book = getBook();
        service.create(book);
        RestTemplate restTemplate = new RestTemplate();

        Book response = restTemplate.getForObject(BASE_URL + "/" + BARCODE_1, Book.class);

        assertAll(
                () -> assertThat(response).isNotNull(),
                () -> assertThat(response).isEqualTo(book)
        );


    }

}
