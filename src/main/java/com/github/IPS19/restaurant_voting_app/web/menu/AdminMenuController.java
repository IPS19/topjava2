package com.github.IPS19.restaurant_voting_app.web.menu;

import com.github.IPS19.restaurant_voting_app.model.Menu;
import com.github.IPS19.restaurant_voting_app.repository.MenuRepository;
import com.github.IPS19.restaurant_voting_app.repository.RestaurantRepository;
import com.github.IPS19.restaurant_voting_app.to.MenuTo;
import com.github.IPS19.restaurant_voting_app.util.MenuUtil;
import com.github.IPS19.restaurant_voting_app.util.OptionalExceptionUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminMenuController {
    static final String REST_URL = "/api/admin/menus";

    private final MenuRepository repository;

    private final RestaurantRepository restaurantRepository;

    @Operation(summary = "get list of menus on a specific date by id",
            description = "example: /api/admin/menus?date=2021-05-05, if url has no parameter - will substitute today date")
    @GetMapping
    public List<Menu> getAllOnDate(@RequestParam(required = false)
                                   @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> date) {
        LocalDate onDate = date.orElseGet(LocalDate::now);
        log.info("get menus on date {}", onDate);
        return repository.getAllByDate(onDate).orElseGet(List::of);
    }

    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "add today restaurant menu")
    @PostMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Caching(
            put = {
                    @CachePut("menus"),
                    @CachePut("restaurantsWithMenu")
            }
    )
    public Menu addNew(@Valid @RequestBody MenuTo menuTo, @PathVariable int restaurantId) {
        log.info("add menu for restaurant {}", restaurantId);
        Menu menu = MenuUtil.createTodayNewFromTo(menuTo);
        menu.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        return repository.save(menu);
    }

    @Transactional
    @Operation(summary = "update today restaurant menu")
    @PutMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Caching(
            evict = {
                    @CacheEvict(value = "menus", key = "#restaurantId"),
                    @CacheEvict(value = "restaurantWithTodayMenu", key = "#restaurantId"),
                    @CacheEvict(value = "restaurantsWithMenu", allEntries = true)
            }
    )
    public void update(@Valid @RequestBody MenuTo menuTo, @PathVariable int restaurantId) {
        log.info("update menu for restaurant {}", restaurantId);
        Menu menu = MenuUtil.createTodayNewFromTo(menuTo);
        menu.setId(OptionalExceptionUtil.getOrThrow(repository.getByRestaurantIdAndDate(restaurantId, menuTo.getDate()),
                        "restaurant " + restaurantId + "is not exist or doesn't have menu" + menuTo.getDate())
                .id());
        menu.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        repository.save(menu);
    }

    @Operation(summary = "get menu of restaurant on a specific date by id",
            description = "example: /api/admin/menus/2?date=2021-05-05, if url has no parameter - will substitute today date")
    @GetMapping("/{restaurantId}")
    public Menu getByIdAndDate(@PathVariable int restaurantId,
                               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> date) {
        log.info("get today menu for restaurant {}", restaurantId);
        return OptionalExceptionUtil
                .getOrThrow(repository.getByRestaurantIdAndDate(restaurantId, date.orElseGet(LocalDate::now)),
                        "Restaurant " + restaurantId + " has no menu on" + date);
    }

    @Transactional
    @Operation(summary = "delete menu of restaurant on a specific date by id",
            description = "example: /api/admin/menus/2?date=2021-05-05, if url has no parameter - will delete today menu")
    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Caching(
            evict = {
                    @CacheEvict(value = "menus", key = "#restaurantId"),
                    @CacheEvict(value = "restaurantWithTodayMenu", key = "#restaurantId"),
                    @CacheEvict(value = "restaurantsWithMenu", allEntries = true)
            }
    )
    public void deleteByIdDate(@PathVariable int restaurantId, @RequestParam(required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> date) {
        log.info("delete menu of restaurant {} on {}", restaurantId, date.orElseGet(LocalDate::now));
        Menu toDelete = OptionalExceptionUtil
                .getOrThrow(repository.getByRestaurantIdAndDate(restaurantId, date.orElseGet(LocalDate::now)),
                        "not found menu on restaurant" + restaurantId);
        repository.delete(toDelete);
    }
}