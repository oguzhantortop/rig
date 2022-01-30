package com.oguzhan.rig.service;

import com.oguzhan.rig.dao.Book;
import com.oguzhan.rig.dto.BookDto;
import com.oguzhan.rig.dto.ErrorResponse;
import com.oguzhan.rig.exception.BusinessException;
import com.oguzhan.rig.exception.ResourceNotFound;
import com.oguzhan.rig.repository.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public BookDto getBook(Long bookId) throws Exception {
        return mapToBookDTO(getBookEntity(bookId));
    }

    public Book getBookEntity(Long bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);
        if(book.isPresent())
            return book.get();
        else {
            ErrorResponse errorResponse = new ErrorResponse("BOOK_ERR_002","Book with given id does not exists!");
            throw new ResourceNotFound(errorResponse);
        }
    }

    public void deleteBook(Long bookId) throws Exception {
        validateBookExistence(bookId);
        bookRepository.deleteById(bookId);
    }

    public BookDto createBook(BookDto book) throws Exception {
        if(isIsbnExists(book)) {
            ErrorResponse errorResponse = new ErrorResponse("BOOK_ERR_001","Book with given isbn already exists!");
            throw new BusinessException(errorResponse);
        }
        Book bookEntity = bookRepository.save(mapToBookEntity(book));
        return mapToBookDTO(bookEntity);
    }

    public BookDto updateBook(BookDto book, Long bookId) throws Exception {
        validateBookExistence(bookId);
        Book bookEntity = mapToBookEntity(book);
        bookEntity.setId(bookId);
        bookEntity = bookRepository.save(bookEntity);
        return mapToBookDTO(bookEntity);
    }

    private void validateBookExistence(Long bookId) throws Exception {
        if(!isBookExists(bookId)) {
            ErrorResponse errorResponse = new ErrorResponse("BOOK_ERR_002","Book with given id does not exists!");
            throw new ResourceNotFound(errorResponse);
        }
    }

    private boolean isBookExists(Long bookId) {
        Optional<Book> user = bookRepository.findById(bookId);
        if(!user.isPresent()) {
            return false;
        }
        return true;
    }

    private boolean isIsbnExists(BookDto book) {
        List<Book> blist = bookRepository.findByIsbn(book.getIsbn());
        if(!blist.isEmpty()) {
            return true;
        }
        return false;
    }

    private Book mapToBookEntity(BookDto book) throws Exception {
        Book bookEntity = new Book();
        BeanUtils.copyProperties(book,bookEntity);
        return bookEntity;
    }

    private BookDto mapToBookDTO(Book book) throws Exception {
        BookDto bookDTO = new BookDto();
        BeanUtils.copyProperties(book,bookDTO);
        return bookDTO;
    }


}
