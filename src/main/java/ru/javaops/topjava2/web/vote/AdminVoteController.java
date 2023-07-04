package ru.javaops.topjava2.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javaops.topjava2.error.NotFoundException;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.VoteRepository;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminVoteController {
    static final String REST_URL = "/api/admin/votes";

    private final VoteRepository repository;

    @GetMapping("/users/{id}")
    public int getUsersVoteByDate(@PathVariable int id, @RequestParam(required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> date) {
        LocalDate onDate = date.orElseGet(LocalDate::now);
        log.info("get user's {} vote by date {}", id, onDate);
        return repository.getByUserIdAndDate(id, onDate)
                .orElseThrow(() -> new NotFoundException("no vote on date " + onDate));
    }

    @GetMapping("/restaurants/{id}")
    public int getRestaurantVotesSumByDate(@PathVariable int id,
                                        @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> date) {
        LocalDate onDate = date.orElseGet(LocalDate::now);
        log.info("get count votes for restaurant {} by date {}", id, onDate);
        return repository.getSumRestaurantVotes(id, onDate);
    }
}