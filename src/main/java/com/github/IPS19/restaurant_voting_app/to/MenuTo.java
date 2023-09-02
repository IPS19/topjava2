package com.github.IPS19.restaurant_voting_app.to;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Value
@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true)
public class MenuTo extends BaseTo {
    @NotNull
    Set<DishItemTo> items;

    @NotNull
    LocalDate date;

    public MenuTo(Integer id, Set<DishItemTo> items, LocalDate date) {
        super(id);
        this.items = items;
        this.date = date;
    }
}