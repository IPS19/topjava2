package ru.javaops.topjava2.web.menu;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javaops.topjava2.error.NotFoundException;
import ru.javaops.topjava2.model.Menu;
import ru.javaops.topjava2.repository.MenuRepository;
import ru.javaops.topjava2.service.MenuService;
import ru.javaops.topjava2.to.MenuTo;
import ru.javaops.topjava2.util.MenuUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminMenuController {
    static final String REST_URL = "/api/admin/menus";

    private final MenuRepository repository;
    private final MenuService service;

    @PutMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateTodayMenu(@Valid @RequestBody MenuTo menuTo, @PathVariable int restaurantId) {
        log.info("update menu for restaurant {}", restaurantId);
        Menu menu = MenuUtil.createTodayNewFromTo(menuTo);
        menu.setId(repository.getMenuByIdAndDate(restaurantId, LocalDate.now()).orElseThrow().id());
        service.save(menu, restaurantId);
    }

    @GetMapping("/{id}/all")
    public List<Menu> getRestaurantMenus(@PathVariable int id) {
        log.info("get all menus from restaurant {}", id);
        return repository.getAllById(id)
                .stream()
                .sorted(Comparator.comparing(Menu::getDate))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MenuTo getTodayById(@PathVariable int id) {
        log.info("get today menu for restaurant {}", id);
        return MenuUtil.createToFromMenu(repository.getMenuByIdAndDate(id, LocalDate.now())
                .orElseThrow(() -> new NotFoundException("Restaurant " + id + "has no menu today")));
    }
}