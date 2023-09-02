package com.github.IPS19.restaurant_voting_app.web.restaurant;

import com.github.IPS19.restaurant_voting_app.model.Restaurant;
import com.github.IPS19.restaurant_voting_app.repository.MenuRepository;
import com.github.IPS19.restaurant_voting_app.repository.RestaurantRepository;
import com.github.IPS19.restaurant_voting_app.to.MenuTo;
import com.github.IPS19.restaurant_voting_app.to.RestaurantTo;
import com.github.IPS19.restaurant_voting_app.util.MenuUtil;
import com.github.IPS19.restaurant_voting_app.util.RestaurantUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.github.IPS19.restaurant_voting_app.util.validation.ValidationUtil.assureIdConsistent;
import static com.github.IPS19.restaurant_voting_app.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminRestaurantController extends AbstractRestaurantController {

    static final String REST_URL = "/api/admin/restaurants";

    private final RestaurantRepository repository;

    private final MenuRepository menuRepository;

    @Operation(summary = "get list of all restaurants")
    @GetMapping
    public List<Restaurant> getAll() {
        return super.getAll();
    }

    @Operation(summary = "get restaurant by id")
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        return ResponseEntity.of(Optional.of(super.getById(id)));
    }

    @Operation(summary = "get menu history by restaurant id")
    @GetMapping("/{restaurantId}/menus")
    public List<MenuTo> getRestaurantMenusHistory(@PathVariable int restaurantId) {
        log.info("get all menus from restaurant {}", restaurantId);
        return MenuUtil.createTosFromMenus(menuRepository.getAllByRestaurantId(restaurantId).orElseGet(List::of));
    }

    //https://www.baeldung.com/spring-request-param#:~:text=Using%20Java%208%20Optional
    @Operation(summary = "get restaurant with menu by id and date",
            description = "example: /api/admin/restaurants/2/menu?date=2021-05-05, if url has no parameter - will substitute today date")
    @GetMapping("/{restaurantId}/menu")
    public ResponseEntity<Restaurant> getWithMenuByIdDate(@PathVariable int restaurantId, @RequestParam(required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> date) {
        return ResponseEntity.of(super.getWithMenuByDate(restaurantId, date.orElseGet(LocalDate::now)));
    }

    @Operation(summary = "get restaurant with menu by id and date",
            description = "example: /api/admin/restaurants/2?date=2021-05-05/all-with-menu, if url has no parameter - will substitute today date")
    @GetMapping("/with-menu")
    public List<Restaurant> getAllWithMenuByDate(@RequestParam(required = false)
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> date) {
        return super.getAllWithMenuByDate(date.orElseGet(LocalDate::now));
    }

    @Transactional
    @Operation(summary = "update restaurant")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Caching(
            evict = {
                    @CacheEvict(value = "restaurants", allEntries = true),
                    @CacheEvict(value = "restaurantsWithMenu", allEntries = true),
                    @CacheEvict(value = "restaurantWithTodayMenu", key = "#id")
            }
    )
    public void update(@Valid @RequestBody RestaurantTo restaurantTo, @PathVariable int id) {
        log.info("update restaurant {}", id);
        assureIdConsistent(restaurantTo, id);
        repository.save(RestaurantUtil.createFromTo(restaurantTo));
    }

    @Transactional
    @Operation(summary = "create new restaurant")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(value = "restaurants", allEntries = true)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody RestaurantTo restaurantTo) {
        checkNew(restaurantTo);
        log.info("create restaurant {}", restaurantTo.getId());
        Restaurant newRestaurant = repository.save(RestaurantUtil.createFromTo(restaurantTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(newRestaurant.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newRestaurant);
    }

    @Operation(summary = "get list of restaurants with empty menu")
    @GetMapping("/empty-menu")
    public List<Restaurant> getWithEmptyMenu() {
        log.info("get all with empty menu");
        return repository.getAllWithEmptyMenu().orElseGet(List::of);
    }

    @Operation(summary = "delete restaurant by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Caching(
            evict = {
                    @CacheEvict(value = "restaurants", allEntries = true),
                    @CacheEvict(value = "menus", allEntries = true),
                    @CacheEvict(value = "restaurantWithTodayMenu", key = "#id"),
                    @CacheEvict(value = "restaurantsWithMenu", allEntries = true)
            }
    )
    public void delete(@PathVariable int id) {
        super.delete(id);
    }
}