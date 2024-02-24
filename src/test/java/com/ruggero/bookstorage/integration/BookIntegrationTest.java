package com.ruggero.bookstorage.integration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruggero.bookstorage.config.BookServiceConfig;
import com.ruggero.bookstorage.controller.BookController;
import com.ruggero.bookstorage.entities.Book;
import com.ruggero.bookstorage.repos.BookRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//
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
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BOOKS_CREATE_URL))
                .header("Content-Type", "application/json")
                .header("User-Agent", "insomnia/8.6.1")
                .method("POST", HttpRequest.BodyPublishers.ofString("{ \n\t\"title\": \"titleA\",\n\t\"author\": \"autorA\",\n\t\"barcode\" : \"10001\",\n\t\"quantity\" : 5,\n\t\"price\": 40.5\n}"))
                .build();
        var client = HttpClient.newHttpClient();
        //HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
//        assertThat(response).isNotNull();

        //var a = restTemplate.postForEntity(url, request, ResponseEntity.class);

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
    void shouldSaveBookRestTemplate() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);

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
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BOOKS_CREATE_URL))
                .header("Content-Type", "application/json")
                .header("User-Agent", "insomnia/8.6.1")
                .method("POST", HttpRequest.BodyPublishers.ofString("{ \n\t\"title\": \"titleA\",\n\t\"author\": \"autorA\",\n\t\"barcode\" : \"10001\",\n\t\"quantity\" : 5,\n\t\"price\": 40.5\n}"))
                .build();


        ResponseEntity<Book> response = null;
        HttpEntity<Book> r = new HttpEntity<>(book);
        var a = restTemplate.postForObject(BOOKS_CREATE_URL, r, Book.class);

        assertThat(a).isNotNull();

        List<Book> books = controller.getBooks();
        assertThat(books).isNotEmpty();
    }
}
