package com.github.IPS19.restaurant_voting_app.to;

import com.github.IPS19.restaurant_voting_app.model.DishItem;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class DishItemTo {
    Integer price;

    String name;

    public DishItemTo(DishItem dishItem) {
        this.price = dishItem.getPrice();
        this.name = dishItem.getName();
    }
}