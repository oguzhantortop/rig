package com.oguzhan.rig.dto;

import java.util.ArrayList;
import java.util.List;

public class OrderResponsePageableDto {
    private int currentPage;
    private long totalItems;
    private int totalPages;
    private List<OrderResponseDto> orderList = new ArrayList<>();


    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<OrderResponseDto> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderResponseDto> orderList) {
        this.orderList = orderList;
    }
}
