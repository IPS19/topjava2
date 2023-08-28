package com.github.IPS19.restaurant_voting_app.util;

import com.github.IPS19.restaurant_voting_app.model.Restaurant;
import com.github.IPS19.restaurant_voting_app.to.VoteTo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class VoteUtil {
    public static VoteTo voteToTO(Restaurant restaurant) {
        return new VoteTo(restaurant.id());
    }
}