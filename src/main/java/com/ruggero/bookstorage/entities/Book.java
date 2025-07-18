package com.ruggero.bookstorage.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

@Entity
@Validated
@Getter
@RequiredArgsConstructor
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Book {

    @NotBlank
    @Size(min = 5, max = 100, message = "Name length must be between 5 and 100 character")
    private String title;

    @NotBlank
    @Size(min = 5, max = 100, message = "Author length must be between 5 and 100 character")
    @Pattern(regexp = "[^0-9]*")
    private String author;

    @Id
    @Min(value = 1, message = "barcode is required, min value is 1")
    private int barcode;

    @Min(value = 0, message = "the minimum value for quantity is 0")
    private Integer quantity;

    @Min(value = 0, message = "price cannot be negative")
    private Double price;

    public Double getTotalPrice() {
        if (quantity == null || price == null) {
            return 0.0;
        }
        return price * quantity;
    }
}