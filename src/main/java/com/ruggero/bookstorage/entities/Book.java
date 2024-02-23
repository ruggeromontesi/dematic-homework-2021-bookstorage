package com.ruggero.bookstorage.entities;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Validated
@Data
public class Book {

    @NotBlank
    @Size(min = 5, max = 100, message = "name length between 5 and 100 character")
    private String name;

    @NotBlank
    @Size(min = 5, max = 100, message = "author length between 5 and 100 character")
    @Pattern(regexp = "[^0-9]*")
    private String author;

    @Id
    @Min(value = 1, message = "barcode is required, min value is 1")
    private int barcode;

    @Min(value = 1, message = "the minimum quantity value is 1")
    private int quantity;
    @Min(value = 0, message = "price cannot be negative")
    private double price;

	public double getTotalPrice() {
		return price * quantity;
	}
}