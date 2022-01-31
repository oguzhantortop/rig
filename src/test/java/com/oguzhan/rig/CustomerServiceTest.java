package com.oguzhan.rig;

import com.oguzhan.rig.dto.CustomerDto;
import com.oguzhan.rig.exception.BusinessException;
import com.oguzhan.rig.exception.ResourceNotFound;
import com.oguzhan.rig.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@EnableAutoConfiguration
@SpringBootTest
public class CustomerServiceTest {
    @Autowired
    CustomerService customerService;

    @Test
    @DisplayName("Create Customer Service Test")
    void testCreateCustomer() throws Exception{
        CustomerDto customerDto = getCustomerDto();
        CustomerDto customer = customerService.createCustomer(customerDto);
        assertAll(
                () -> assertNotNull(customer),
                () -> assertNotNull(customer.getId()),
                () -> assertEquals(customer.getName(),"John"),
                () -> assertEquals(customer.getSurname(),"Doe"),
                () -> assertEquals(customer.getEmail(),"john.doe@gmail.com"),
                () -> assertEquals(customer.getPassword(),"password"),
                () -> assertThrows(BusinessException.class,() -> customerService.createCustomer(customerDto))
        );
    }

    private CustomerDto getCustomerDto() throws Exception {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("John");
        customerDto.setSurname("Doe");
        customerDto.setEmail("john.doe@gmail.com");
        customerDto.setPassword("password");
        return customerDto;
    }


    @Test
    @DisplayName("Delete Customer Service Test")
    void testDeleteCustomer() throws Exception{
        assertThrows(ResourceNotFound.class,() -> customerService.deleteCustomer(-1L));
    }

    @Test
    @DisplayName("Update Customer Service Test")
    void testUpdateCustomer() throws Exception{
        CustomerDto customerDto = getCustomerDto();
        assertThrows(ResourceNotFound.class,() -> customerService.updateCustomer(customerDto,-1L));
    }

    @Test
    @DisplayName("Get Customer Service Test")
    void testGetCustomer() throws Exception{
        CustomerDto customerDto = getCustomerDto();
        customerDto.setEmail("abc@abc.com");
        CustomerDto customer = customerService.createCustomer(customerDto);
        assertAll(
                () -> assertThrows(ResourceNotFound.class,() -> customerService.getCustomer(-1L)),
                () -> assertNotNull(customerService.getCustomer(customer.getId()))
        );
    }

}
