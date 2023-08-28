package com.github.IPS19.restaurant_voting_app.to;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@Getter
@RequiredArgsConstructor
public class MenuTo {
    @NotNull
    List<DishItemTo> items;

    @Nullable
    LocalDate date;
}