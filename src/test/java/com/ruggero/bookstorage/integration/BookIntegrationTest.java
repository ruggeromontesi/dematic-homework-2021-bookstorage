package com.ruggero.bookstorage.integration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruggero.bookstorage.controller.BookController;
import com.ruggero.bookstorage.entities.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class BookIntegrationTest {
    private static final String BOOKS_CREATE_URL = "http://localhost:8080/books/create";
    @Autowired
    private BookController controller;
    @Autowired
    private MockMvc mvc;

    @Test
    void shouldSaveBook() throws Exception {

        String url = BOOKS_CREATE_URL;
        Book book = Book.builder()
                .title("Java A Beginner's Guide")
                .author("Schildt")
                .barcode(1)
                .quantity(10)
                .price(1.0)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        byte[] obj = mapper.writeValueAsBytes(book);


        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(obj))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        book = Book.builder()
                .title("Java A Beginner's Guide")
                .author("Schildt")
                .barcode(2)
                .quantity(10)
                .price(1.0)
                .build();
        obj = mapper.writeValueAsBytes(book);


        var a = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(obj));
        System.out.println(a);


    }

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

        List<Book> books = controller.getBooks();
        assertThat(books).isNotEmpty();
    }
}
