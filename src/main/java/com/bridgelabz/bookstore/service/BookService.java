package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.builder.BookBuilder;
import com.bridgelabz.bookstore.builder.MessageProperties;
import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.exception.BookStoreException;
import com.bridgelabz.bookstore.model.BookModel;
import com.bridgelabz.bookstore.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookService implements IBookService{

    @Autowired
    private BookBuilder bookBuilder;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     *  Purpose : Ability to insert book details in Book repository.
     *            This method first check the whether the name of the book is already present
     *            in the bookRepository or not with the help of the Optional findByName.
     *            if bookName entered by the user is already present in the repository then
     *            it will throw the BookStoreException with message "Book already present.",
     *            if not then this method will save the book details which entered by the user
     *            in the bookRepository that is book_model database.
     *
     * @param bookDTO Object of BookDTO gets stored in the Database
     *                .
     * @return String Object to print the message.
     */

    @Override
    public String addBook(BookDTO bookDTO) {
        log.info("Inside the addBook method of BookService Class.");
        Optional<BookModel> bookName = bookRepository.findByBookName(bookDTO.getBookName());

        if (bookName.isPresent()) {
            throw new BookStoreException(MessageProperties.BOOK_ALREADY_EXIST.getMessage());
        } else {
            BookModel book = bookBuilder.buildDO(bookDTO);
            bookRepository.save(book);
            return MessageProperties.BOOK_ADDED_SUCCESSFULLY.getMessage();
        }
    }

    /**
     * Purpose : Ability to get book details from Book repository.
     *           This method is used to retrieve all book details present
     *           from the database.
     *
     * @return String Object to print the message.
     */

    @Override
    public List<BookDTO> getBooks() {
        log.info("Inside getBooks service method.");
        return bookRepository.findAll().stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Purpose : Ability to delete book details from Book repository.
     *           This method used to delete the Book details from the database.
     *           it checks the id entered by the user is matched with database id or not,
     *           if yes then it will delete that details of the book associated with that id
     *           and if not then it will throw BookStore Exception with Message "Book not Found.".
     *
     * @param id On providing ID, it will delete the book details from the database if it matches
     *           with the present database id.
     *
     * @return String Object to print the message.
     */

    @Override
    public String deleteBook(int id) {
        log.info("Inside deleteBook service method.");
        Optional<BookModel> bookModel = bookRepository.findById(id);
        bookModel.orElseThrow(()-> new BookStoreException(MessageProperties.BOOK_NOT_FOUND.getMessage()));
        bookRepository.deleteById(id);
        return MessageProperties.DELETE_BOOK.getMessage();
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

    @Override
    public String updateBookPrice(int id, BookDTO bookDTO) {
        BookModel bookModel = bookRepository.findById(id)
                .orElseThrow(() -> new BookStoreException(MessageProperties.BOOK_NOT_FOUND.getMessage()));

        bookModel.setBookPrice(bookDTO.getBookPrice());
        bookRepository.save(bookModel);
        return MessageProperties.UPDATED_BOOK_PRICE.getMessage();
    }

    /**
     * Purpose : Ability to update book quantity to Book repository.
     *
     * @param id On providing ID, the book-id is matched with the id value of the database.
     *                  If found, it updates the book quantity to Book repository, else returns error message.
     *
     * @param bookDTO If ID is found, Object of BookDTO gets stored in the Database.
     * @return String Object to print the message.
     */

    @Override
    public String updateBookQuantity(int id, BookDTO bookDTO) {
        BookModel bookModel = bookRepository.findById(id)
                .orElseThrow(() -> new BookStoreException(MessageProperties.BOOK_NOT_FOUND.getMessage()));

        bookModel.setBookQuantity(bookDTO.getBookQuantity());
        bookRepository.save(bookModel);
        return MessageProperties.UPDATED_BOOK_QUANTITY.getMessage();
    }
}
