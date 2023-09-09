package com.github.IPS19.restaurant_voting_app.service;

import com.github.IPS19.restaurant_voting_app.error.DataConflictException;
import com.github.IPS19.restaurant_voting_app.model.Restaurant;
import com.github.IPS19.restaurant_voting_app.model.Vote;
import com.github.IPS19.restaurant_voting_app.repository.RestaurantRepository;
import com.github.IPS19.restaurant_voting_app.repository.VoteRepository;
import com.github.IPS19.restaurant_voting_app.to.VoteTo;
import com.github.IPS19.restaurant_voting_app.web.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;

    private final RestaurantRepository restaurantRepository;

    public static final LocalTime FORBIDDEN_TO_REVOTE_AFTER = LocalTime.of(11, 0);

    @Transactional
    public VoteTo vote(int id) {
        Restaurant restaurant = restaurantRepository.getExisted(id);
        return new VoteTo(
                voteRepository.save(new Vote(LocalDate.now(), AuthUser.authUser(), restaurant)).getRestaurant().id());
    }

    @Transactional
    public VoteTo reVote(@PathVariable int id) {
        Optional<Vote> voteFromBase = voteRepository.getByUserAndDate(AuthUser.authUser(), LocalDate.now());
        Restaurant restaurant = restaurantRepository.getExisted(id);
        if (voteFromBase.isPresent()) {
            if (LocalTime.now().isBefore(FORBIDDEN_TO_REVOTE_AFTER)) {
                Vote vote = voteFromBase.get();
                vote.setRestaurant(restaurant);
                return new VoteTo(voteRepository.save(vote).id());
            } else {
                throw new DataConflictException("can't vote after " + FORBIDDEN_TO_REVOTE_AFTER + " a.m.");
            }
        }
        return new VoteTo(
                voteRepository.save(new Vote(LocalDate.now(), AuthUser.authUser(), restaurant)).getRestaurant().id());
    }
}