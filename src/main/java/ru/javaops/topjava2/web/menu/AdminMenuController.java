package ru.javaops.topjava2.web.menu;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    static final String REST_URL = "/api/admin/menus";

    private final MenuRepository repository;
    private final MenuService service;

    @PostMapping(value = "/restaurant/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Menu addToday(@Valid @RequestBody MenuTo menuTo, @PathVariable int restaurantId) {
        log.info("add menu for restaurant {}", restaurantId);
        Menu menu = MenuUtil.createTodayNewFromTo(menuTo);
        return service.save(menu, restaurantId);
    }

    @PutMapping(value = "/restaurant/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateToday(@Valid @RequestBody MenuTo menuTo, @PathVariable int restaurantId) {
        log.info("update menu for restaurant {}", restaurantId);
        Menu menu = MenuUtil.createTodayNewFromTo(menuTo);
        menu.setId(repository.getByRestaurantIdAndDate(restaurantId, LocalDate.now()).orElseThrow().id());
        service.save(menu, restaurantId);
    }

    @GetMapping("/restaurant/{id}/all")
    public List<Menu> getRestaurantMenusHistory(@PathVariable int id) {
        log.info("get all menus from restaurant {}", id);
        return repository.getAllByRestaurantId(id);
    }

    @GetMapping("/restaurant/{id}")
    public Menu getByIdAndDate(@PathVariable int id,
                               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> date) {
        log.info("get today menu for restaurant {}", id);
        return repository.getByRestaurantIdAndDate(id, date.orElseGet(LocalDate::now))
                .orElseThrow(() -> new NotFoundException("Restaurant " + id + " has no menu on" + date));
    }

    @DeleteMapping("restaurant/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByIdDate(@PathVariable int id, @RequestParam(required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> date) {
        log.info("delete menu of restaurant {} on {}", id, date.orElseGet(LocalDate::now));
        Menu toDelete = repository.getByRestaurantIdAndDate(id, date.orElseGet(LocalDate::now))
                .orElseThrow(() -> new NotFoundException("not found menu on restaurant" + id));
        repository.delete(toDelete);
    }
}