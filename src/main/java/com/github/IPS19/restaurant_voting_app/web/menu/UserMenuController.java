package com.github.IPS19.restaurant_voting_app.web.menu;

import com.github.IPS19.restaurant_voting_app.model.Restaurant;
import com.github.IPS19.restaurant_voting_app.repository.MenuRepository;
import com.github.IPS19.restaurant_voting_app.to.MenuTo;
import com.github.IPS19.restaurant_voting_app.to.RestaurantTo;
import com.github.IPS19.restaurant_voting_app.util.MenuUtil;
import com.github.IPS19.restaurant_voting_app.util.OptionalExceptionUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class UserMenuController {
    static final String REST_URL = "/api/user/menus";

    MenuRepository repository;

    @Operation(summary = "get all today menus")
    @GetMapping
    @Cacheable(value = "menus")
    public List<MenuTo> getAllTodayMenus() {
        log.info("getAll today menus");
        return MenuUtil.createTosFromMenus(repository.getAllByDate(LocalDate.now()).orElseGet(List::of));
    }

    @Operation(summary = "get restaurant by menu id")
    @GetMapping("/{id}/restaurant")
    public RestaurantTo getRestaurantByMenuId(@PathVariable int id) {
        log.info("get restaurant by menu id");
        Restaurant restaurant = OptionalExceptionUtil.getOrThrow(repository.getRestaurantByMenuId(id),
                "no restaurant have menu with id" + id);
        return new RestaurantTo(restaurant.getId(), restaurant.getName());
    }
}