package com.oguzhan.rig.controller;

import com.oguzhan.rig.dto.CustomerDto;
import com.oguzhan.rig.dto.OrderRequestDto;
import com.oguzhan.rig.dto.OrderResponseDto;
import com.oguzhan.rig.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/api/v1/Order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping(path = "")
    public ResponseEntity<OrderResponseDto> addCustomer(@Valid @RequestBody OrderRequestDto order) throws Exception {
        OrderResponseDto orderResponse = orderService.createOrder(order);
        return new ResponseEntity<OrderResponseDto>(orderResponse, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{orderId}")
    public ResponseEntity<OrderResponseDto> getCustomer(@NotNull(message = "orderId can not be null") @PathVariable(value = "orderId") Long orderId) throws Exception {
        OrderResponseDto order = orderService.getOrder(orderId);
        return new ResponseEntity<OrderResponseDto>(order, HttpStatus.OK);
    }
}
