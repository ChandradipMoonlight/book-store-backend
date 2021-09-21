package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.BookDTO;

import java.util.List;

public interface IBookService {
    String addBook(BookDTO bookDTO);

    List<BookDTO> getBooks();

    String deleteBook(int id);

    String updateBookPrice(int id, BookDTO bookDTO);

    String updateBookQuantity(int id, BookDTO bookDTO);
}