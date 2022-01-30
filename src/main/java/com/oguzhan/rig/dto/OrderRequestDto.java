package com.oguzhan.rig.dto;

import java.util.Date;
import java.util.List;

public class OrderRequestDto {
    private Long customerId;
    private Date orderDate;
    private List<OrderDetailReqDto> orderDetailList;

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderDetailReqDto> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetailReqDto> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
