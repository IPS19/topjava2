package com.github.IPS19.restaurant_voting_app.util;

import com.github.IPS19.restaurant_voting_app.model.Restaurant;
import com.github.IPS19.restaurant_voting_app.model.Vote;
import com.github.IPS19.restaurant_voting_app.to.VoteRespTo;
import com.github.IPS19.restaurant_voting_app.to.VoteTo;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class VoteUtil {
    public static VoteTo voteToTO(Restaurant restaurant) {
        return new VoteTo(restaurant.id());
    }

    public static VoteRespTo createRespToFromVote(Vote vote) {
        return new VoteRespTo(vote.getRestaurant().id(), vote.getRestaurant().getName(), vote.getDate());
    }

    public static List<VoteRespTo> createRespTos(List<Vote> votes) {
        return votes.stream().map(VoteUtil::createRespToFromVote).collect(Collectors.toList());
    }
}