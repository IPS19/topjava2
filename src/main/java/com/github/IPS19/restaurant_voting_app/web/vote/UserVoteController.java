package com.github.IPS19.restaurant_voting_app.web.vote;

import com.github.IPS19.restaurant_voting_app.error.DataConflictException;
import com.github.IPS19.restaurant_voting_app.model.Restaurant;
import com.github.IPS19.restaurant_voting_app.model.Vote;
import com.github.IPS19.restaurant_voting_app.repository.RestaurantRepository;
import com.github.IPS19.restaurant_voting_app.repository.VoteRepository;
import com.github.IPS19.restaurant_voting_app.to.VoteTo;
import com.github.IPS19.restaurant_voting_app.util.OptionalExceptionUtil;
import com.github.IPS19.restaurant_voting_app.web.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class UserVoteController {
    static final String REST_URL = "/api/user/votes";

    private final VoteRepository repository;

    private final RestaurantRepository restaurantRepository;

    public static final LocalTime FORBIDDEN_TO_REVOTE_AFTER = LocalTime.of(11, 0);

    @Operation(summary = "make vote for restaurant by it's id")
    @PostMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public VoteTo vote(@PathVariable int id) {
        Restaurant restaurant = restaurantRepository.getExisted(id);
        return new VoteTo(
                repository.save(new Vote(LocalDate.now(), AuthUser.authUser(), restaurant)).getRestaurant().id());
    }

    @Operation(summary = "revote for restaurant by it's id - only before 11 A.M.")
    @PutMapping(value = "/{id}")
    public VoteTo reVote(@PathVariable int id) {
        Optional<Vote> voteFromBase = repository.getByUserAndDate(AuthUser.authUser(), LocalDate.now());
        Restaurant restaurant = restaurantRepository.getExisted(id);
        if (voteFromBase.isPresent()) {
            if (LocalTime.now().isBefore(FORBIDDEN_TO_REVOTE_AFTER)) {
                Vote vote = voteFromBase.get();
                vote.setRestaurant(restaurant);
                return new VoteTo(repository.save(vote).id());
            } else {
                throw new DataConflictException("can't vote after " + FORBIDDEN_TO_REVOTE_AFTER + " a.m.");
            }
        }
        return new VoteTo(
                repository.save(new Vote(LocalDate.now(), AuthUser.authUser(), restaurant)).getRestaurant().id());
    }

    @Operation(summary = "get id of restaurant that was voted")
    @GetMapping
    public VoteTo getToday() {
        return new VoteTo(OptionalExceptionUtil
                .getOrThrow(repository.getVotedRestaurantByDate(AuthUser.authUser(), LocalDate.now()), "no vote today")
                .id());
    }
}