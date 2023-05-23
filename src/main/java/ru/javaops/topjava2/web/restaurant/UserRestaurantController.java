package ru.javaops.topjava2.web.restaurant;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class UserRestaurantController {
    static final String REST_URL = "/api/user/restaurants";

    private final RestaurantRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get restaurant with id {}", id);
        return ResponseEntity.of(Optional.of(repository.getExisted(id)));
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("getAll restaurants");
        return repository.findAll();
    }

    @GetMapping("/all-with-menu")
    public List<Restaurant> getAllWithMenu() {
        log.info("getAll restaurants with menu");
        return repository.getAllWithMenuByDate(LocalDate.now());
    }

    @GetMapping("/{id}/with-menu")
    public ResponseEntity<Restaurant> getWithTodayMenu(@PathVariable int id) {
        log.info("get restaurant {} with menu", id);
        return ResponseEntity.of(repository.getWithMenuByDate(id, LocalDate.now()));
    }

}
