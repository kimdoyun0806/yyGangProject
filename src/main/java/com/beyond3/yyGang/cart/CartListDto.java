package com.beyond3.yyGang.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartListDto {

    private Long cartOptionID;
    private String itemName;
    private int price;
    private int quantity;

    public CartListDto(Long cartOptionID, String itemName, int price, int quantity) {
        this.cartOptionID = cartOptionID;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
