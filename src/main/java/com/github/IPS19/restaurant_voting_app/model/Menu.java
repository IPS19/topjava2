package com.github.IPS19.restaurant_voting_app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"menu_date", "restaurant_id"}, name = "uk_restaurant_date_menu")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Menu extends BaseEntity {
    @Column(name = "menu_date")
    @NotNull
    private LocalDate date;

    @OneToMany(mappedBy = "menu")
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private Set<DishItem> items = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    @NotNull
    private Restaurant restaurant;

    public Menu(Integer id, @NotNull Set<DishItem> items, @NotNull LocalDate date) {
        super(id);
        this.items = items;
        this.date = date;
    }
}