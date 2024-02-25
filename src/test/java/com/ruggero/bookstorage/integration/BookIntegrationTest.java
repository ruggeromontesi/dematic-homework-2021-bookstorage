package com.ruggero.bookstorage.integration;

import com.ruggero.bookstorage.entities.Book;
import com.ruggero.bookstorage.service.BookUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.ruggero.bookstorage.service.TestHelper.BARCODE_1;
import static com.ruggero.bookstorage.service.TestHelper.PRICE_1_0;
import static com.ruggero.bookstorage.service.TestHelper.QUANTITY_10;
import static com.ruggero.bookstorage.service.TestHelper.getBook;
import static com.ruggero.bookstorage.service.TestHelper.getBooks;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BookIntegrationTest {
    private static final String BASE_URL = "http://localhost:8080/books";
    private static final double MODIFIED_PRICE = 22.2;
    @Qualifier("bookService")
    @Autowired
    private BookUseCase service;

    @BeforeEach
    public void cleanUp() {
        service.deleteAll();
    }

    @Test
    void shouldCreateBook() {
        RestTemplate restTemplate = new RestTemplate();
        Book book = getBook();

        Book response = restTemplate.postForObject(BASE_URL + "/create", new HttpEntity<>(book), Book.class);

        List<Book> books = service.findAll();

        assertAll(
                () -> assertThat(response).isNotNull(),
                () -> assertThat(response).isEqualTo(book),
                () -> assertThat(books).hasSize(1),
                () -> {
                    assert books != null;
                    assertThat(books.get(0)).isEqualTo(book);
                }
        );
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

    @Test
    void shouldUpdate() {
        Book book = getBook();
        service.create(book);
        Book updateBook = Book.builder()
                .barcode(BARCODE_1)
                .price(MODIFIED_PRICE)
                .build();

        HttpEntity<Book> request = new HttpEntity<>(updateBook);
        RestTemplate restTemplate = new RestTemplate();
        Book response = restTemplate.exchange(BASE_URL + "/update", HttpMethod.PUT, request, Book.class).getBody();

        assertAll(
                () -> assertThat(response.getBarcode()).isEqualTo(BARCODE_1),
                () -> assertThat(response.getPrice()).isEqualTo(MODIFIED_PRICE),
                () -> assertThat(response.getTitle()).isEqualTo(book.getTitle()),
                () -> assertThat(response.getAuthor()).isEqualTo(book.getAuthor()),
                () -> assertThat(response.getQuantity()).isEqualTo(book.getQuantity())
        );
    }

    @Test
    void shouldGetTotalPrice() {
        service.create(getBook());
        RestTemplate restTemplate = new RestTemplate();

        Double response = restTemplate.getForObject(BASE_URL + "/" + BARCODE_1 + "/totalprice", Double.class);

        assertAll(
                () -> assertThat(response).isEqualTo(PRICE_1_0 * QUANTITY_10)
        );
    }

    @Test
    void shouldGetBooksGroupedByQuantitySortedByTotalPrice() {
        getBooks().forEach(service::create);
        RestTemplate restTemplate = new RestTemplate();

        Map<Integer, List<Integer>> response = restTemplate.getForObject(BASE_URL + "/barcodes/grouped/quantity/totalprice", Map.class);
        assertThat(response).isEqualTo(Map.of(
                "1", List.of(6,5,4),
                "10", List.of(1,2,3)
        ));
    }
}
