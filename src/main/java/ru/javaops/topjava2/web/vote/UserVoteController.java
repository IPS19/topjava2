package ru.javaops.topjava2.web.vote;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javaops.topjava2.error.DataConflictException;
import ru.javaops.topjava2.error.NotFoundException;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.to.VoteTo;
import ru.javaops.topjava2.web.AuthUser;

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

    @Operation(summary = "make vote for restaurant by it's id")
    @PutMapping(value = "/{id}")
    public VoteTo vote(@PathVariable int id) {
        Optional<Vote> voteFromBase = repository.getByUserAndDate(AuthUser.authUser(), LocalDate.now());
        Restaurant restaurant = restaurantRepository.getExisted(id);
        if (voteFromBase.isPresent()) {
            if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
                Vote vote = voteFromBase.get();
                vote.setRestaurant(restaurant);
                return new VoteTo(repository.save(vote).id());
            } else {
                throw new DataConflictException("can't vote after 11 a.m.");
            }
        }
        return new VoteTo(
                repository.save(new Vote(LocalDate.now(), AuthUser.authUser(), restaurant)).getRestaurant().id());
    }

    @Operation(summary = "get id of restaurant that was voted")
    @GetMapping
    public VoteTo getToday() {
        return new VoteTo(repository.getVotedRestaurantByDate(AuthUser.authUser(), LocalDate.now())
                .orElseThrow(() -> new NotFoundException("no vote today")).id());
    }
}