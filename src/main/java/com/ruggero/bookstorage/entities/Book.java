package com.ruggero.bookstorage.entities;

import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.validation.annotation.Validated;

@Entity
@Validated
public class Book {

	@NotBlank
	@Size(min = 5, max = 100, message= "name length between 5 and 100 character")
	private String name;

	@NotBlank
	@Size(min = 5, max = 100, message= "author length between 5 and 100 character")
	@Pattern(regexp= "[^0-9]*")
	private String author;

	@Id
	@Min(value = 1, message = "barcode is required, min value is 1")
	private int barcode;

	@Min(value = 1, message = "the minimum quantity value is 1")
	private int quantity;
	@Min(value = 0 , message = "price cannot be negative")
	private double price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getBarcode() {
		return barcode;
	}

	public void setBarcode(int barcode) {
		this.barcode = barcode;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;

	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTotalPrice() {
		return price * quantity;
	}

	@Override
	public String toString() {
		return "Book [name=" + name + ", author=" + author + ", barcode=" + barcode + ", quantity=" + quantity
				+ ", price=" + price + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Book)) {
			return false;
		}
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null) {
				return false;
			}
		} else if (!author.equals(other.author)) {
			return false;
		}
		if (barcode != other.barcode) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price)) {
			return false;
		}
		if (quantity != other.quantity) {
			return false;
		}
		return true;
	}
}