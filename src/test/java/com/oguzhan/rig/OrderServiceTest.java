package com.oguzhan.rig;

import com.oguzhan.rig.dto.OrderDto;
import com.oguzhan.rig.exception.BusinessException;
import com.oguzhan.rig.exception.ResourceNotFound;
import com.oguzhan.rig.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;EnableAutoConfiguration
@SpringBootTest
public class OrderServiceTest {
    @Autowired
    OrderService orderService;

    @Test
    @DisplayName("Create Order Service Test")
    void testCreateOrder() throws Exception{
        OrderDto orderDto = getOrderDto();
        OrderDto order = orderService.createOrder(orderDto);
        assertAll(
                () -> assertNotNull(order),
                () -> assertNotNull(order.getId()),
                () -> assertEquals(order.getName(),"John"),
                () -> assertEquals(order.getSurname(),"Doe"),
                () -> assertEquals(order.getEmail(),"john.doe@gmail.com"),
                () -> assertEquals(order.getPassword(),"password"),
                () -> assertThrows(BusinessException.class,() -> orderService.createOrder(orderDto))
        );
    }

    private OrderDto getOrderDto() throws Exception {
        OrderDto orderDto = new OrderDto();
        orderDto.setName("John");
        orderDto.setSurname("Doe");
        orderDto.setEmail("john.doe@gmail.com");
        orderDto.setPassword("password");
        return orderDto;
    }


    @Test
    @DisplayName("Delete Order Service Test")
    void testDeleteOrder() throws Exception{
        assertThrows(ResourceNotFound.class,() -> orderService.deleteOrder(-1L));
    }

    @Test
    @DisplayName("Update Order Service Test")
    void testUpdateOrder() throws Exception{
        OrderDto orderDto = getOrderDto();
        assertThrows(ResourceNotFound.class,() -> orderService.updateOrder(orderDto,-1L));
    }

    @Test
    @DisplayName("Get Order Service Test")
    void testGetOrder() throws Exception{
        OrderDto orderDto = getOrderDto();
        orderDto.setEmail("abc@abc.com");
        OrderDto order = orderService.createOrder(orderDto);
        assertAll(
                () -> assertThrows(ResourceNotFound.class,() -> orderService.getOrder(-1L)),
                () -> assertNotNull(orderService.getOrder(order.getId()))
        );
    }

}
