package com.shop.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class RecordDto {

    @NotNull
    private Long itemId;

    @Min(value = 1, message = "최소 주문 수량은 1개 입니다.")
    @Max(value = 9999, message = "최대 주문 수량은 9999개 입니다.")
    private int count;
}
