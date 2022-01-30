package com.oguzhan.rig.controller;

import com.oguzhan.rig.dto.CustomerDto;
import com.oguzhan.rig.dto.OrderRequestDto;
import com.oguzhan.rig.dto.OrderResponseDto;
import com.oguzhan.rig.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

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
    public ResponseEntity<OrderResponseDto> getCustomerOrderById(@NotNull(message = "orderId can not be null") @PathVariable(value = "orderId") Long orderId) throws Exception {
        OrderResponseDto order = orderService.getOrder(orderId);
        return new ResponseEntity<OrderResponseDto>(order, HttpStatus.OK);
    }

    @GetMapping(path = "/date")
    public ResponseEntity<List<OrderResponseDto>> getCustomerOrders(@RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                                              @RequestParam(value = "end") @DateTimeFormat(pattern = "yyyy-MM-dd")  Date end) throws Exception {
        List<OrderResponseDto> orderList = orderService.getOrdersWithinDate(start, end);
        return new ResponseEntity<List<OrderResponseDto>>(orderList, HttpStatus.OK);
    }

}
