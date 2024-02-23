package com.ruggero.bookstorage.service;

import com.ruggero.bookstorage.entities.Book;
import com.ruggero.bookstorage.entities.errorsandexception.IllegalBarcodeException;
import com.ruggero.bookstorage.entities.errorsandexception.RepeatedBarcodeException;
import com.ruggero.bookstorage.repos.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    private static final int BARCODE = 1004588;
    @InjectMocks
    private BookService service;
    @Mock
    private BookRepository repository;

    @Test
    void shouldCreateBook_whenValidationOk() {
        Book book = Book.builder()
                .barcode(BARCODE)
                .build();

        when(repository.findByBarcode(BARCODE)).thenReturn(List.of());

        service.create(book);

        verify(repository, times(1)).save(book);
    }

    @Test
    void shouldThrow_whenNegativeBarcode() {
        Book book = Book.builder()
                .barcode(-BARCODE)
                .build();

        Exception e = assertThrows(IllegalBarcodeException.class, () -> service.create(book));

        assertThat(e.getMessage()).isEqualTo(IllegalBarcodeException.NEGATIVE_BARCODE);
        verifyNoInteractions(repository);
    }

    @Test
    void shouldThrow_whenExistsBookWithSameBarcode() {
        Book book = Book.builder()
                .barcode(BARCODE)
                .build();
        when(repository.findByBarcode(BARCODE)).thenReturn(List.of(Book.builder().build()));

        Exception e = assertThrows(RepeatedBarcodeException.class, () -> service.create(book));

        assertThat(e.getMessage()).isEqualTo(String.format(RepeatedBarcodeException.BARCODE_ALREADY_PRESENT, BARCODE));
        verify(repository, times(1)).findByBarcode(anyInt());
    }
}