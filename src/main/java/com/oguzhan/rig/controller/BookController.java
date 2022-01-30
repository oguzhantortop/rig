package com.oguzhan.rig.controller;

import com.oguzhan.rig.dto.BookDto;
import com.oguzhan.rig.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/api/v1/Book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping(path = "/{bookId}")
    public ResponseEntity<BookDto> getBook(@NotNull(message = "bookId can not be empty!") @PathVariable(value = "bookId") Long bookId) throws Exception {
        BookDto book = bookService.getBook(bookId);
        return new ResponseEntity<BookDto>(book, HttpStatus.OK);
    }

    @PostMapping(path = "")
    public ResponseEntity<BookDto> addBook(@Valid @RequestBody BookDto book) throws Exception {
        book = bookService.createBook(book);
        return new ResponseEntity<BookDto>(book, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{bookId}")
    public ResponseEntity<BookDto> updateBook(@NotNull(message = "bookId can not be empty!") @RequestBody BookDto book, @PathVariable(value = "bookId") Long bookId) throws Exception {
        book = bookService.updateBook(book,bookId);
        return new ResponseEntity<BookDto>(book, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{bookId}")
    public ResponseEntity deleteBook(@NotNull(message = "bookId can not be empty!") @PathVariable(value = "bookId") Long bookId) throws Exception {
        bookService.deleteBook(bookId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
