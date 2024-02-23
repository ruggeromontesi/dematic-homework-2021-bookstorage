package com.ruggero.bookstorage.controllers;

import com.ruggero.bookstorage.entities.AntiqueBook;
import com.ruggero.bookstorage.entities.Book;
import com.ruggero.bookstorage.entities.ScienceJournal;
import com.ruggero.bookstorage.entities.errorsandexception.IllegalBarcodeCriteriaException;
import com.ruggero.bookstorage.entities.errorsandexception.NoSuchPropertyException;
import com.ruggero.bookstorage.entities.errorsandexception.NotAScienceJournalException;
import com.ruggero.bookstorage.entities.errorsandexception.NotAnAntiqueBookException;
import com.ruggero.bookstorage.repos.BookRepository;
import com.ruggero.bookstorage.service.BookService;
import com.ruggero.bookstorage.service.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookRestController {
    BookRepository repository;
    private final BookService bookService;

    Util util = new Util();

    /**
     * CREATE A client can use a REST call to put an ScienceJournal into the system
     * providing name, author, barcode, quantity and unit price
     *
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws MethodArgumentNotValidException
     */
    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@Valid @RequestBody Book book) {
        util.validateBarcode(book.getBarcode(), repository.findAll());
        return bookService.create(book);
    }

    /**
     * A client can use a REST call to retrieve book’s information from a system by
     * providing its barcode.
     */
    @GetMapping(value = "/{barcode}")
    public ResponseEntity<Book> fetchBookByBarcode(@Valid @PathVariable("barcode") int barcode) {
        Book book = util.getBookByBarcode(repository.findAll(), barcode);
        return ResponseEntity.ok(book);
    }

    /**
     * Book Total Price A client can use a REST call to calculate the total price of
     * specific books in the system given the barcode
     */
    @RequestMapping(value = "/{barcode}/totalprice", method = RequestMethod.GET)
    public double getTotalPriceByBarcode(@PathVariable("barcode") int barcode) {
        return util.getBookByBarcode(repository.findAll(), barcode).getTotalPrice();
    }

    /**
     * UPDATE:client can use this REST call to update book attributes providing the
     * barcode and updated field information.
     *
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws NumberFormatException
     * @throws MethodArgumentNotValidException
     */
    @RequestMapping(value = "/{barcode}/{propertyName}/{newPropertyValue}", method = RequestMethod.PUT)
    public ResponseEntity<Book> update(@PathVariable("barcode") int barcode,
                                       @PathVariable("propertyName") final String propertyName,
                                       @PathVariable("newPropertyValue") String newPropertyValue) {
        Book book = util.getBookByBarcode(repository.findAll(), barcode);

        if (!propertyName.equals("name") && !propertyName.equals("author") && !propertyName.equals("quantity")
                && !propertyName.equals("price") && !propertyName.equals("releaseyear")
                && !propertyName.equals("scienceindex"))
            throw new NoSuchPropertyException("No property \"" + propertyName + "\" exists", propertyName);
        if (propertyName.equals("name"))
            book.setName(newPropertyValue);
        if (propertyName.equals("author"))
            book.setAuthor(newPropertyValue);
        if (propertyName.equals("quantity")) {
            int quantity = Integer.parseInt(newPropertyValue);
            util.validateQuantity(quantity);
            book.setQuantity(quantity);
        }
        if (propertyName.equals("price")) {
            double price = Double.parseDouble(newPropertyValue);
            util.validatePrice(price);
            book.setPrice(price);
        }
        if (propertyName.equals("releaseyear"))
            if (!(book instanceof AntiqueBook))
                throw new NotAnAntiqueBookException("Book with barcode : " + barcode + " is not an antique book",
                        barcode);
            else
                ((AntiqueBook) book).setReleaseYear(util.validateReleaseYear(Integer.parseInt(newPropertyValue)));
        if (propertyName.equals("scienceindex"))
            if (!(book instanceof ScienceJournal))
                throw new NotAScienceJournalException("Book with barcode : " + barcode + " is not a science journal",
                        barcode);
            else
                ((ScienceJournal) book).setScienceIndex(util.validateScienceIndex(Integer.parseInt(newPropertyValue)));
        repository.save(book);
        return ResponseEntity.ok(book);
    }

    @GetMapping(value = "/barcodelist/{criteria}")
    public TreeMap<Number, List<Integer>> getBarcodeList(@PathVariable("criteria") String criteria) {

        if (!criteria.equals("quantity") && !criteria.equals("totalprice"))
            throw new IllegalBarcodeCriteriaException("The typed criteria " + criteria + "is not allowed", criteria);

        TreeMap<Number, List<Integer>> map = new TreeMap<>();
        if (criteria.equals("quantity")) {
            for (Book book : repository.findAll()) {
                List<Book> bookList = repository.findByQuantity(book.getQuantity());
                List<Integer> barcodeList = new ArrayList<>();
                for (Book bookint : bookList)
                    barcodeList.add(bookint.getBarcode());
                map.put(book.getQuantity(), barcodeList);
            }
        }
        if (criteria.equals("totalprice")) {
            for (Book book : repository.findAll()) {
                double totalPrice = book.getTotalPrice();
                List<Book> bookList = new ArrayList<>();
                List<Integer> barcodeList = new ArrayList<>();
                for (Book bookint : repository.findAll())
                    if (bookint.getTotalPrice() == totalPrice)
                        bookList.add(bookint);
                for (Book bookint : bookList)
                    barcodeList.add(bookint.getBarcode());
                map.put(totalPrice, barcodeList);
            }
        }
        return map;
    }

    /**
     * GET ALL Get the complete list of books
     */
    @GetMapping(value = "")
    public List<Book> getBooks() {
        return repository.findAll();
    }

    /**
     * DELETE:client can use a REST call to delete a Book providing barcode
     */
    @DeleteMapping(value = "/{barcode}")
    public void deleteByBarcode(@PathVariable("barcode") int barcode) {
        repository.deleteById(barcode);
    }

    /**
     * DELETE ALL
     */
    @DeleteMapping(value = "")
    public void deleteAll() {
        repository.deleteAll();
    }
}