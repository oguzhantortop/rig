package com.oguzhan.rig.dto;

import java.util.List;

public class OrderStatsResponseDto {
    List<MonthlyOrderStatDto> list;

    public List<MonthlyOrderStatDto> getList() {
        return list;
    }

    public void setList(List<MonthlyOrderStatDto> list) {
        this.list = list;
    }
}
