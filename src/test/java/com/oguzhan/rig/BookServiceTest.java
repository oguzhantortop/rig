package com.oguzhan.rig;

import com.oguzhan.rig.dto.BookDto;
import com.oguzhan.rig.dto.BookDto;
import com.oguzhan.rig.exception.BusinessException;
import com.oguzhan.rig.exception.ResourceNotFound;
import com.oguzhan.rig.service.BookService;
import com.oguzhan.rig.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@EnableAutoConfiguration
@SpringBootTest
public class BookServiceTest {
    @Autowired
    BookService bookService;

    @Test
    @DisplayName("Create Book Service Test")
    void testCreateBook() throws Exception{
        BookDto bookDto = getBookDto();
        BookDto book = bookService.createBook(bookDto);
        assertAll(
                () -> assertNotNull(book),
                () -> assertNotNull(book.getId()),
                () -> assertEquals(book.getTitle(),"Grapes of Wrath"),
                () -> assertEquals(book.getAuthor(),"John Steinbeck"),
                () -> assertEquals(book.getIsbn(),"2312432423"),
                () -> assertEquals(book.getStockCount(),5L),
                () -> assertEquals(book.getUnitPrice(),BigDecimal.valueOf(5.99)),
                () -> assertThrows(BusinessException.class,() -> bookService.createBook(bookDto))
        );
    }

    private BookDto getBookDto() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setAuthor("John Steinbeck");
        bookDto.setIsbn("2312432423");
        bookDto.setTitle("Grapes of Wrath");
        bookDto.setStockCount(5L);
        bookDto.setUnitPrice(BigDecimal.valueOf(5.99));
        return bookDto;
    }


    @Test
    @DisplayName("Delete Book Service Test")
    void testDeleteBook() throws Exception{
        assertThrows(ResourceNotFound.class,() -> bookService.deleteBook(-1L));
    }

    @Test
    @DisplayName("Update Book Service Test")
    void testUpdateBook() throws Exception{
        BookDto bookDto = getBookDto();
        assertThrows(ResourceNotFound.class,() -> bookService.updateBook(bookDto,-1L));
    }

    @Test
    @DisplayName("Get Book Service Test")
    void testGetBook() throws Exception{
        BookDto bookDto = getBookDto();
        bookDto.setIsbn("423460090999");
        BookDto book = bookService.createBook(bookDto);
        assertAll(
                () -> assertThrows(ResourceNotFound.class,() -> bookService.getBook(-1L)),
                () -> assertNotNull(bookService.getBook(book.getId()))
        );
    }

}
