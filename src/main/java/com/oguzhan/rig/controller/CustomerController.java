package com.oguzhan.rig.controller;

import com.oguzhan.rig.dto.CustomerDto;
import com.oguzhan.rig.dto.OrderResponseDto;
import com.oguzhan.rig.dto.OrderResponsePageableDto;
import com.oguzhan.rig.service.CustomerService;
import com.oguzhan.rig.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/Customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @GetMapping(path = "/{customerId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable(value = "customerId") Long customerId) throws Exception {
        CustomerDto customer = customerService.getCustomer(customerId);
        return new ResponseEntity<CustomerDto>(customer, HttpStatus.OK);
    }

    @PostMapping(path = "")
    public ResponseEntity<CustomerDto> addCustomer(@Valid @RequestBody CustomerDto customer) throws Exception {
        customer = customerService.createCustomer(customer);
        return new ResponseEntity<CustomerDto>(customer, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{customerId}")
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto customer, @PathVariable(value = "customerId") Long customerId) throws Exception {
        customer = customerService.updateCustomer(customer,customerId);
        return new ResponseEntity<CustomerDto>(customer, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{customerId}")
    public ResponseEntity deleteCustomer(@PathVariable(value = "customerId") Long customerId) throws Exception {
        customerService.deleteCustomer(customerId);
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping(path = "/{customerId}/Order")
    public ResponseEntity<OrderResponsePageableDto> getCustomerOrders(@PathVariable(value = "customerId") Long customerId,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) throws Exception {
        OrderResponsePageableDto orderResponseDto = orderService.getCustomerOrders(customerId,page,size);
        return new ResponseEntity<OrderResponsePageableDto>(orderResponseDto, HttpStatus.OK);
    }

}
