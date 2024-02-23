package com.ruggero.bookstorage.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;


@Entity
@Validated
@Data
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class Book {

    @NotBlank
    @Size(min = 5, max = 100, message = "name length between 5 and 100 character")
    private String title;

    @NotBlank
    @Size(min = 5, max = 100, message = "author length between 5 and 100 character")
    @Pattern(regexp = "[^0-9]*")
    private String author;

    @Id
    @Min(value = 1, message = "barcode is required, min value is 1")
    private int barcode;

    @Min(value = 0, message = "the minimum quantity value is 0")
    private Integer quantity;
    @Min(value = 0, message = "price cannot be negative")
    private Double price;

    public double getTotalPrice() {
        return price * quantity;
    }
}