package com.bridgelabz.bookstore.builder;

import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.model.BookModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BookBuilder {
    public BookModel buildDO(BookDTO bookDTO) {
        log.info("Inside buildDO method of BookBuilder Class.");
        BookModel bookModel = new BookModel();
        BeanUtils.copyProperties(bookDTO, bookModel);
        return bookModel;
    }
}
