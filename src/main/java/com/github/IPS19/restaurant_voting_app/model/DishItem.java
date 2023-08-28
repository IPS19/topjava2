package com.github.IPS19.restaurant_voting_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DishItem {
    @Column(name = "price")
    @NotNull
    private Integer price;

    @Column(name = "dish_name")
    @NotNull
    private String name;

    public DishItem(@NotNull Integer price, @NotNull String name) {
        this.price = price;
        this.name = name;
    }
}