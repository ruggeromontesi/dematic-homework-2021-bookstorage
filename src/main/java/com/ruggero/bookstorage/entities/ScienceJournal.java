/**
 * A client also wants to store and access science journals in his system. They are just like other books,
but also have a science index (int between 1 – 10).
 */

package com.ruggero.bookstorage.entities;

import javax.persistence.Entity;
import javax.validation.constraints.*;

import org.springframework.validation.annotation.Validated;

@Entity
@Validated
public class ScienceJournal extends Book {

	@Min(value = 1, message = "Science index cannot be smaller than 1")
	@Max(value = 10, message = "Science index cannot be bigger than 10")
	private int scienceIndex;

	public int getScienceIndex() {
		return scienceIndex;
	}

	public void setScienceIndex(int scienceIndex) {
		this.scienceIndex = scienceIndex;
	}

	/** Science journals Total Price = Quantity * Price * Science Index */
	public double getTotalPrice() {
		return getQuantity() * getPrice() * getScienceIndex();
	}
}
