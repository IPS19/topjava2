package ru.javaops.topjava2.util;

import lombok.experimental.UtilityClass;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.to.VoteTo;

@UtilityClass
public class VoteUtil {
    public static VoteTo voteToTO(Restaurant restaurant) {
        return new VoteTo(restaurant.id());
    }
}