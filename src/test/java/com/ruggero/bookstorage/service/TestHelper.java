package com.ruggero.bookstorage.service;

import com.ruggero.bookstorage.entities.Book;

import java.util.List;

public class TestHelper {

    public static final String JAVA_A_BEGINNER_S_GUIDE = "Java A Beginner's Guide";
    public static final String SCHILDT = "Schildt";
    public static final String OCA_STUDY_GUIDE = "OCA Study Guide";
    public static final String BOYARSKY_SELIKOFF = "Boyarsky Selikoff";
    public static final String JAVA_THE_COMPLETE_REFERENCE = "Java The Complete Reference";
    public static final int QUANTITY_10 = 10;
    public static final int QUANTITY_1 = 1;
    public static final int BARCODE_1 = 1;
    public static final double PRICE_1_0 = 1.0;
    public static final int BARCODE_2 = 2;
    public static final double PRICE_2_0 = 2.0;
    public static final int BARCODE_3 = 3;
    public static final double PRICE_3_0 = 3.0;

    public static Book getBook() {
        return Book.builder()
                .title(JAVA_A_BEGINNER_S_GUIDE)
                .author(SCHILDT)
                .barcode(BARCODE_1)
                .quantity(QUANTITY_10)
                .price(PRICE_1_0)
                .build();
    }

    private static Book getBook2() {
        return Book.builder()
                .title(OCA_STUDY_GUIDE)
                .author(BOYARSKY_SELIKOFF)
                .barcode(BARCODE_2)
                .quantity(QUANTITY_10)
                .price(PRICE_2_0)
                .build();
    }

    private static Book getBook3() {
        return Book.builder()
                .title("OCA Practice Test")
                .author(BOYARSKY_SELIKOFF)
                .barcode(BARCODE_3)
                .quantity(QUANTITY_10)
                .price(PRICE_3_0)
                .build();
    }

    private static Book getBook4() {
        return Book.builder()
                .title(JAVA_THE_COMPLETE_REFERENCE)
                .author(SCHILDT)
                .barcode(4)
                .quantity(QUANTITY_1)
                .price(PRICE_3_0)
                .build();
    }

        private static Book getBook5() {
        return Book.builder()
                .title("Java OCA JAVA SE 8 Programmer I Certification Guide")
                .author("Mala Gupta")
                .barcode(5)
                .quantity(QUANTITY_1)
                .price(PRICE_2_0)
                .build();
    }

    private static Book getBook6() {
        return Book.builder()
                .title("OCA Java Programmer 8 Fundamentals 1Z0-808: OCAJP 8")
                .author("Hanumant Deshmukh")
                .barcode(6)
                .quantity(QUANTITY_1)
                .price(PRICE_1_0)
                .build();
    }

    public static List<Book> getBooks() {
        return List.of(
                getBook(),
                getBook2(),
                getBook3(),
                getBook4(),
                getBook5(),
                getBook6()
        );
    }


}
