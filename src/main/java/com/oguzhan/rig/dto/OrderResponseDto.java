package com.oguzhan.rig.dto;

import com.oguzhan.rig.dao.Customer;

import java.util.Date;
import java.util.List;

public class OrderResponseDto {
    private Long id;
    private Customer customer;
    private Date orderDate;
    private Double totalPrice;
    private List<OrderDetailResponseDto> orderDetailList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderDetailResponseDto> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetailResponseDto> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
