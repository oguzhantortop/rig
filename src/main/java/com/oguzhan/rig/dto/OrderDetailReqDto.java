package com.oguzhan.rig.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderDetailReqDto {
    @NotNull
    private Long bookId;
    @Min(1)
    private Long count;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
