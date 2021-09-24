package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.builder.BookBuilder;
import com.bridgelabz.bookstore.builder.MessageProperties;
import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.exception.BookStoreException;
import com.bridgelabz.bookstore.model.BookModel;
import com.bridgelabz.bookstore.model.UserModel;
import com.bridgelabz.bookstore.repository.BookRepository;
import com.bridgelabz.bookstore.repository.UserRepository;
import com.bridgelabz.bookstore.utils.TokenUtil;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenUtil tokenUtil;

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
    public String addBook(String token, BookDTO bookDTO) {
        log.info("Inside the addBook method of BookService Class.");
        int userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent = userRepository.findById(userId);

        if (isUserPresent.isPresent()) {
            Optional<BookModel> bookName = bookRepository.findByBookName(bookDTO.getBookName());

            if (bookName.isPresent()) {
                throw new BookStoreException(MessageProperties.BOOK_ALREADY_EXIST.getMessage());
            } else {
                BookModel book = bookBuilder.buildDO(bookDTO);
                bookRepository.save(book);
                return MessageProperties.BOOK_ADDED_SUCCESSFULLY.getMessage();
            }
        } else
            throw new BookStoreException(MessageProperties.UNAUTHORISED_USER.getMessage());
    }

    /**
     * Purpose : Ability to get book details from Book repository.
     *           This method is used to retrieve all book details present
     *           from the database.
     *
     * @return String Object to print the message.
     */

    @Override
    public List<BookDTO> getBooks(String token) {
        log.info("Inside getBooks service method.");
        int userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent= userRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            return bookRepository.findAll().stream()
                    .map(book -> modelMapper.map(book, BookDTO.class))
                    .collect(Collectors.toList());
        } else
            throw new BookStoreException(MessageProperties.PLEASE_LOGIN.getMessage());
    }

    /**
     * Purpose : Ability to delete book details from Book repository.
     *           This method used to delete the Book details from the database.
     *           it checks the id entered by the user is matched with database id or not,
     *           if yes then it will delete that details of the book associated with that id
     *           and if not then it will throw BookStore Exception with Message "Book not Found.".
     *
     * @param bookId On providing ID, it will delete the book details from the database if it matches
     *           with the present database id.
     *
     * @return String Object to print the message.
     */

    @Override
    public String deleteBook(String token, int bookId) {
        log.info("Inside deleteBook service method.");
        int userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent= userRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            Optional<BookModel> bookModel = bookRepository.findById(bookId);
            bookModel.orElseThrow(()-> new BookStoreException(MessageProperties.BOOK_NOT_FOUND.getMessage()));
            bookRepository.deleteById(bookId);
            return MessageProperties.DELETE_BOOK.getMessage();
        } else
            throw new BookStoreException(MessageProperties.PLEASE_LOGIN.getMessage());
    }

    /**
     * Purpose : Ability to update book price to Book repository.
     *
     * @param id On providing ID, the book-id is matched with the id value of the database.
     *                  If found, it updates the book price to Book repository, else returns error message.
     *
     * @param price If ID is found, Object of BookDTO gets stored in the Database.
     * @return String Object to print the message.
     */

    @Override
    public String updateBookPrice(String token, int id, double price) {
        log.info("Inside UpdateBook service method.");
        int userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent= userRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            Optional<BookModel> bookModel = bookRepository.findById(id);
            bookModel .orElseThrow(() -> new BookStoreException(MessageProperties.BOOK_NOT_FOUND.getMessage()));
            bookModel.get().setBookPrice(price);
            bookRepository.save(bookModel.get());
            return MessageProperties.UPDATED_BOOK_PRICE.getMessage();
        } else
            throw new BookStoreException(MessageProperties.PLEASE_LOGIN.getMessage());
    }

    /**
     * Purpose : Ability to update book quantity to Book repository.
     *
     * @param id On providing ID, the book-id is matched with the id value of the database.
     *                  If found, it updates the book quantity to Book repository, else returns error message.
     *
     * @param quantity If ID is found, Object of BookDTO gets stored in the Database.
     * @return String Object to print the message.
     */

    @Override
    public String updateBookQuantity(String token, int id, int quantity) {
        log.info("Inside UpdateBook service method.");
        int userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent= userRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            Optional<BookModel> bookModel = bookRepository.findById(id);
            bookModel .orElseThrow(() -> new BookStoreException(MessageProperties.BOOK_NOT_FOUND.getMessage()));
            bookModel.get().setBookQuantity(quantity);
            bookRepository.save(bookModel.get());
            return MessageProperties.UPDATED_BOOK_QUANTITY.getMessage();
        } else
            throw new BookStoreException(MessageProperties.PLEASE_LOGIN.getMessage());
    }
}
