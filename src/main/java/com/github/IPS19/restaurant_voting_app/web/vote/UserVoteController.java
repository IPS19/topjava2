package com.github.IPS19.restaurant_voting_app.web.vote;

import com.github.IPS19.restaurant_voting_app.repository.VoteRepository;
import com.github.IPS19.restaurant_voting_app.service.VoteService;
import com.github.IPS19.restaurant_voting_app.to.VoteRespTo;
import com.github.IPS19.restaurant_voting_app.to.VoteTo;
import com.github.IPS19.restaurant_voting_app.util.OptionalExceptionUtil;
import com.github.IPS19.restaurant_voting_app.util.VoteUtil;
import com.github.IPS19.restaurant_voting_app.web.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class UserVoteController {
    static final String REST_URL = "/api/user/votes";
    private final VoteRepository repository;

    private final VoteService service;

    @Operation(summary = "get all")
    @GetMapping
    public List<VoteRespTo> getAllUsersVotes() {
        return VoteUtil.createRespTos(repository.getUsersVotes(AuthUser.authUser()).orElseGet(List::of));
    }

    @Operation(summary = "make vote for restaurant by it's id")
    @PostMapping(value = "/today/{id}")//, consumes = MediaType.APPLICATION_JSON_VALUE --UserVoteController#vote - не CSRF защищено.. (должен быть MediaType.APPLICATION_JSON_VALUE) и обычно 201 с Location
    @ResponseStatus(HttpStatus.CREATED)//ResponseEntity<VoteTo>
    public VoteTo vote(@PathVariable int id) {
        return service.vote(id);
    }

    @Operation(summary = "revote for restaurant by it's id - only before 11 A.M.")
    @PutMapping(value = "/today/{id}")
    public VoteTo reVote(@PathVariable int id) {
        return service.reVote(id);
    }

    @Operation(summary = "get id of restaurant that was voted")
    @GetMapping("/today")
    public ResponseEntity<Integer> getToday() {
        return ResponseEntity.of(repository.getVotedRestaurantByDate(AuthUser.authUser(), LocalDate.now()));
    }
}