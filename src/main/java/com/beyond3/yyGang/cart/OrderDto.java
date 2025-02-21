package com.beyond3.yyGang.cart;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {

    @NotNull
    private Long productId;

    private int count;
}
