package com.oguzhan.rig;

import com.oguzhan.rig.dao.Book;
import com.oguzhan.rig.dao.Customer;
import com.oguzhan.rig.dto.*;
import com.oguzhan.rig.exception.BusinessException;
import com.oguzhan.rig.exception.ResourceNotFound;
import com.oguzhan.rig.service.BookService;
import com.oguzhan.rig.service.CustomerService;
import com.oguzhan.rig.service.OrderService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@EnableAutoConfiguration
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderServiceTest {
    @Autowired
    OrderService orderService;

    @Autowired
    CustomerService customerService;

    @Autowired
    BookService bookService;

    private CustomerDto customer;
    private BookDto book;

    @BeforeAll
    void createOrderTestData() throws Exception{
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("John");
        customerDto.setSurname("Doe");
        customerDto.setEmail("ordertest@gmail.com");
        customerDto.setPassword("password");
        this.customer = customerService.createCustomer(customerDto);

        BookDto bookDto = new BookDto();
        bookDto.setAuthor("John Doe");
        bookDto.setIsbn("123456789");
        bookDto.setTitle("Book of Order");
        bookDto.setStockCount(10L);
        bookDto.setUnitPrice(BigDecimal.valueOf(2.99));
        this.book = bookService.createBook(bookDto);
    }

    @Test
    @DisplayName("Create Order Service Test")
    void testCreateOrder() throws Exception{
        OrderRequestDto orderDto = getOrderRequestDto();
        OrderResponseDto order = orderService.createOrder(orderDto);
        assertAll(
                () -> assertNotNull(order),
                () -> assertNotNull(order.getId()),
                () -> assertEquals(order.getTotalPrice(),book.getUnitPrice().doubleValue()*orderDto.getOrderDetailList().get(0).getCount().longValue()),
                () -> assertNotNull(order.getCustomer()),
                () -> assertNotNull(order.getTotalPrice()),
                () -> assertNotNull(order.getOrderDetailList())
        );
    }

    private OrderRequestDto getOrderRequestDto() throws Exception {
        OrderRequestDto orderDto = new OrderRequestDto();
        orderDto.setOrderDate(Calendar.getInstance().getTime());
        orderDto.setCustomerId(customer.getId());
        List<OrderDetailReqDto> orderDetailList = new ArrayList<>();
        OrderDetailReqDto orderDetailReqDto = new OrderDetailReqDto();
        orderDetailReqDto.setBookId(book.getId());
        orderDetailReqDto.setCount(3L);
        orderDetailList.add(orderDetailReqDto);
        orderDto.setOrderDetailList(orderDetailList);
        return orderDto;
    }

    @Test
    @DisplayName("Get Order Service Test")
    void testGetOrder() throws Exception{
        OrderRequestDto orderDto = getOrderRequestDto();
        OrderResponseDto order = orderService.createOrder(orderDto);
        assertAll(
                () -> assertThrows(ResourceNotFound.class,() -> orderService.getOrder(-1L)),
                () -> assertNotNull(orderService.getOrder(order.getId()))
        );
    }

}
