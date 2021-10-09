package com.ruggero.bookstorage.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.validation.constraints.*;
import org.springframework.validation.annotation.Validated;

@Entity
@Validated
public class AntiqueBook extends Book {
	
	@Min(value = 0, message = "release year cannot be before 0")
	@Max(value = 1900,  message= "release year cannot be later than 1900")
	private int releaseYear;

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		/** year parameter (no more recent than 1900)*/
	    this.releaseYear = releaseYear; 
	}
	
	/*** Total Price = Quantity * Price * (Current Year – Release Year) / 10 */
	public double getTotalPrice(){
		return getQuantity()* getPrice() *((double)(LocalDate.now().getYear()-getReleaseYear())/10) ;
	}
}