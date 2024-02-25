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
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class BookIntegrationTest {
    private static final String BOOKS_CREATE_URL = "http://localhost:8080/books/create";
    @Qualifier("bookService")
    @Autowired
    private BookUseCase service;

    @Test
    void shouldSaveBookRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);

        Book book = Book.builder()
                .title("Java A Beginner's Guide")
                .author("Schildt")
                .barcode(11)
                .quantity(10)
                .price(1.0)
                .build();

        HttpEntity<Book> request = new HttpEntity<>(book);
        Book response = restTemplate.postForObject(BOOKS_CREATE_URL, request, Book.class);

        assertThat(response).isNotNull();

        List<Book> books = service.findAll();
        assertThat(books).hasSize(1);
    }
}
