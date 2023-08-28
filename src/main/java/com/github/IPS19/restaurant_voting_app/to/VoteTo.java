package com.github.IPS19.restaurant_voting_app.to;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class VoteTo {
    @NotNull
    Integer restaurantId;
}