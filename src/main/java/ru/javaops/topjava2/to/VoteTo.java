package ru.javaops.topjava2.to;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class VoteTo {
    @NotNull
    Integer restaurantId;
}