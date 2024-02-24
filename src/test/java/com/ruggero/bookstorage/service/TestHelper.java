package com.ruggero.bookstorage.service;

import com.ruggero.bookstorage.entities.Book;

import java.util.List;

public class TestHelper {
    private static Book getBook1() {
        return Book.builder()
                .title("Java A Beginner's Guide")
                .author("Schildt")
                .barcode(1)
                .quantity(10)
                .price(1.0)
                .build();
    }

    private static Book getBook2() {
        return Book.builder()
                .title("OCA Study Guide")
                .author("Boyarsky Selikoff")
                .barcode(2)
                .quantity(10)
                .price(2.0)
                .build();
    }

    private static Book getBook3() {
        return Book.builder()
                .title("OCA Practice Test")
                .author("Boyarsky Selikoff")
                .barcode(3)
                .quantity(10)
                .price(3.0)
                .build();
    }

    private static Book getBook4() {
        return Book.builder()
                .title("Java The Complete Reference")
                .author("Schildt")
                .barcode(4)
                .quantity(1)
                .price(3.0)
                .build();
    }

        private static Book getBook5() {
        return Book.builder()
                .title("Java OCA JAVA SE 8 Programmer I Certification Guide")
                .author("Mala Gupta")
                .barcode(5)
                .quantity(1)
                .price(2.0)
                .build();
    }

    private static Book getBook6() {
        return Book.builder()
                .title("OCA Java Programmer 8 Fundamentals 1Z0-808: OCAJP 8")
                .author("Hanumant Deshmukh")
                .barcode(6)
                .quantity(1)
                .price(1.0)
                .build();
    }

    public static List<Book> getBooks() {
        return List.of(
                getBook1(),
                getBook2(),
                getBook3(),
                getBook4(),
                getBook5(),
                getBook6()
        );
    }


}
