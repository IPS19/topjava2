package ru.javaops.topjava2.web.restaurant;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.topjava2.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class UserRestaurantController extends AbstractRestaurantController {
    static final String REST_URL = "/api/user/restaurants";

    @Operation(summary = "get restaurant by id")
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        return ResponseEntity.of(Optional.of(super.getById(id)));
    }

    @Operation(summary = "get list of all restaurants")
    @GetMapping
    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return super.getAll();
    }

    @Operation(summary = "get list of all restaurants with its menus")
    @GetMapping("/all-with-menu")
    @Cacheable("restaurantsWithMenu")
    public List<Restaurant> getAllWithTodayMenu() {
        return super.getAllWithMenuByDate(LocalDate.now());
    }

    @Operation(summary = "get restaurant with menu")
    @GetMapping("/{id}/with-menu")
    @Cacheable("restaurantWithTodayMenu")
    public ResponseEntity<Restaurant> getWithTodayMenu(@PathVariable int id) {
        return ResponseEntity.of(super.getWithMenuByDate(id, LocalDate.now()));
    }
}