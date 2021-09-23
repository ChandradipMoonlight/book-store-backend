package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.BookDTO;

import java.util.List;

public interface IBookService {
    String addBook(BookDTO bookDTO);

    List<BookDTO> getBooks();

    String deleteBook(int bookId);

    String updateBookPrice(int id, double price);

    String updateBookQuantity(int id, int quantity);
}