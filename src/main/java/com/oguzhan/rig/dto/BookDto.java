package com.oguzhan.rig.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class BookDto {
    private Long id;
    @NotNull
    @Size(min=2, message="title should have at least 2 characters")
    private String title;
    @NotNull
    @Size(min=2, message="isbn should have at least 2 characters")
    private String isbn;
    @NotNull
    @Size(min=2, message="author should have at least 2 characters")
    private String author;
    @Min(value = 0L)
    private Long stockCount;
    @Min(value = 0, message = "unitPrice should be at least 0.0")
    private BigDecimal unitPrice;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Long getStockCount() {
        return stockCount;
    }

    public void setStockCount(Long stockCount) {
        this.stockCount = stockCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
