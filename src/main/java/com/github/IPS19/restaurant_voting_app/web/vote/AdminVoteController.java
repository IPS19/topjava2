package com.github.IPS19.restaurant_voting_app.web.vote;

import com.github.IPS19.restaurant_voting_app.repository.VoteRepository;
import com.github.IPS19.restaurant_voting_app.util.OptionalExceptionUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminVoteController {
    static final String REST_URL = "/api/admin/votes";

    private final VoteRepository repository;

    @Operation(summary = "get user's vote by user's id and date",
            description = "example: /api/admin/votes/5?date=2021-05-05, if url has no parameter - will substitute today date")
    @GetMapping("/users/{id}")
    public int getUsersVoteByDate(@PathVariable int id, @RequestParam(required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> date) {
        LocalDate onDate = date.orElseGet(LocalDate::now);
        log.info("get user's {} vote by date {}", id, onDate);
        return OptionalExceptionUtil.getOrThrow(repository.getByUserIdAndDate(id, onDate), "no vote on date " + onDate);
    }

    @Operation(summary = "get sum of votes by restaurant's id and date",
            description = "example: /api/admin/votes/3?date=2021-05-05, if url has no parameter - will substitute today date")
    @GetMapping("/restaurants/{id}")
    public int getRestaurantVotesSumByDate(@PathVariable int id,
                                           @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> date) {
        LocalDate onDate = date.orElseGet(LocalDate::now);
        log.info("get count votes for restaurant {} by date {}", id, onDate);
        return repository.getSumRestaurantVotes(id, onDate);
    }
}