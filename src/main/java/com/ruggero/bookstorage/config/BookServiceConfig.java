package com.ruggero.bookstorage.config;

import com.ruggero.bookstorage.repos.BookRepository;
import com.ruggero.bookstorage.service.BookService;
import com.ruggero.bookstorage.service.BookUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookServiceConfig {

    @Bean
    public BookUseCase bookUseCase(BookRepository bookRepository) {
        return new BookService(bookRepository);
    }
}
