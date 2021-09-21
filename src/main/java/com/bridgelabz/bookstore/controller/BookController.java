package com.bridgelabz.bookstore.controller;

import com.bridgelabz.bookstore.builder.MessageProperties;
import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.service.IBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class BookController {

    @Autowired
    private IBookService bookService;

    /**
     *  Purpose : Ability to add book details in Book repository.
     *
     * @param bookDTO Object of BookDTO gets stored in the Database.
     *
     * @return String Object to print the message.
     */

    @PostMapping("/add")
    public ResponseEntity<String> addBook(@RequestBody @Valid BookDTO bookDTO) {
        log.info("Inside addBook controller method.");
        return new ResponseEntity<>(bookService.addBook(bookDTO), HttpStatus.OK);
    }


    /**
     * Purpose : Ability to get book details from Book repository.
     *
     * @return String Object to print the message.
     */

    @GetMapping
    public ResponseEntity<ResponseDTO> getBook() {
        log.info("Inside getBook controller method.");
        List<BookDTO> bookList = bookService.getBooks();
        return new ResponseEntity<>(new ResponseDTO(MessageProperties.GET_BOOKS.getMessage(), bookList), HttpStatus.OK);
    }

    /**
     * Purpose : Ability to delete book details from Book repository.
     *
     * @param id On providing ID, the book-id is matched with the id value of the database.
     *                  If found, it deletes the book details from Book repository, else returns error message.
     * @return String Object to print the message.
     */

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteBook(@RequestParam(name = "id") int id) {
        log.info("Inside deleteBook controller method.");
        return new ResponseEntity<>(bookService.deleteBook(id), HttpStatus.OK);
    }

    /**
     * Purpose : Ability to update book price to Book repository.
     *
     * @param id On providing ID, the book-id is matched with the id value of the database.
     *                  If found, it updates the book price to Book repository, else returns error message.
     *
     * @param bookDTO If ID is found, Object of BookDTO gets stored in the Database.
     * @return String Object to print the message.
     */

    @PutMapping("/update-price")
    public ResponseEntity<String> updateBookPrice(@RequestParam(name = "id") int id,
                                                  @Valid @RequestBody BookDTO bookDTO) {
        log.info("Inside updateBookPrice controller method.");
        return new ResponseEntity<>(bookService.updateBookPrice(id, bookDTO), HttpStatus.OK);
    }


    /**
     * Purpose : Ability to update book quantity to Book repository.
     *
     * @param id On providing ID, the book-id is matched with the id value of the database.
     *                  If found, it updates the book quantity to Book repository, else returns error message.
     *
     * @param bookDTO If ID is found, Object of BookDTO gets stored in the Database.
     *
     * @return String Object to print the message.
     */

    @PutMapping("/update-quantity")
    public ResponseEntity<String> updateBookQuantity(@RequestParam(name = "id") int id,
                                                     @Valid @RequestBody BookDTO bookDTO) {
        log.info("Inside updateBookPrice controller method.");
        return new ResponseEntity<>(bookService.updateBookQuantity(id, bookDTO), HttpStatus.OK);
    }

}
