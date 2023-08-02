package ru.javaops.topjava2.web.menu;

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
import org.springframework.web.bind.annotation.*;
import ru.javaops.topjava2.error.NotFoundException;
import ru.javaops.topjava2.model.Menu;
import ru.javaops.topjava2.repository.MenuRepository;
import ru.javaops.topjava2.service.MenuService;
import ru.javaops.topjava2.to.MenuTo;
import ru.javaops.topjava2.util.MenuUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminMenuController {
    static final String REST_URL = "/api/admin/restaurant-menus";

    private final MenuRepository repository;
    private final MenuService service;

    @Operation(summary = "add today restaurant menu")
    @PostMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CachePut("menus")
    public Menu addToday(@Valid @RequestBody MenuTo menuTo, @PathVariable int restaurantId) {
        log.info("add menu for restaurant {}", restaurantId);
        Menu menu = MenuUtil.createTodayNewFromTo(menuTo);
        return service.save(menu, restaurantId);
    }

    @Operation(summary = "update today restaurant menu")
    @PutMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Caching(
            evict = {
                    @CacheEvict(value = "menus", key = "#restaurantId"),
                    @CacheEvict(value = "restaurantWithTodayMenu", key = "#restaurantId"),
                    @CacheEvict(value = "restaurantsWithMenu", allEntries = true)
            }
    )
    public void updateToday(@Valid @RequestBody MenuTo menuTo, @PathVariable int restaurantId) {
        log.info("update menu for restaurant {}", restaurantId);
        Menu menu = MenuUtil.createTodayNewFromTo(menuTo);
        menu.setId(repository.getByRestaurantIdAndDate(restaurantId, LocalDate.now()).orElseThrow(
                () -> new NotFoundException("restaurant " + restaurantId + "is not exist or doesn't have today menu")
        ).id());
        service.save(menu, restaurantId);
    }

    @Operation(summary = "get menu history by restaurant id")
    @GetMapping("/{restaurantId}/all")
    public List<MenuTo> getRestaurantMenusHistory(@PathVariable int restaurantId) {
        log.info("get all menus from restaurant {}", restaurantId);
        return MenuUtil.createTosFromMenu(repository.getAllByRestaurantId(restaurantId));
    }

    @Operation(summary = "get menu of restaurant on a specific date by id",
            description = "example: /api/admin/restaurant-menus/2?date=2021-05-05, if url has no parameter - will substitute today date")
    @GetMapping("/{restaurantId}")
    public Menu getByIdAndDate(@PathVariable int restaurantId,
                               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> date) {
        log.info("get today menu for restaurant {}", restaurantId);
        return repository.getByRestaurantIdAndDate(restaurantId, date.orElseGet(LocalDate::now))
                .orElseThrow(() -> new NotFoundException("Restaurant " + restaurantId + " has no menu on" + date));
    }

    @Operation(summary = "delete menu of restaurant on a specific date by id",
            description = "example: /api/admin/restaurant-menus/2?date=2021-05-05, if url has no parameter - will delete today menu")
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
        Menu toDelete = repository.getByRestaurantIdAndDate(restaurantId, date.orElseGet(LocalDate::now))
                .orElseThrow(() -> new NotFoundException("not found menu on restaurant" + restaurantId));
        repository.delete(toDelete);
    }
}