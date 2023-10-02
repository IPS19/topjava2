package com.github.IPS19.restaurant_voting_app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "dish_item")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DishItem extends BaseEntity {
    @Column(name = "price")
    @NotNull
    private Integer price;

    @Column(name = "dish_name")
    @NotNull
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    @NotNull
    Menu menu;

    public DishItem(@NotNull Integer price, @NotNull String name) {
        this.price = price;
        this.name = name;
    }
}