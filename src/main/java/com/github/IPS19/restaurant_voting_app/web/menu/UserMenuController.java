package com.github.IPS19.restaurant_voting_app.web.menu;

import com.github.IPS19.restaurant_voting_app.model.Menu;
import com.github.IPS19.restaurant_voting_app.repository.MenuRepository;
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

import java.time.LocalDate;

@RestController
@RequestMapping(value = UserMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class UserMenuController {
    static final String REST_URL = "/api/user/menus";

    MenuRepository repository;

    @Operation(summary = "get today menu by restaurant id")
    @GetMapping("/{restaurantId}")
    @Cacheable(value = "menus")
    public ResponseEntity<Menu> getTodayByRestaurantId(@PathVariable int restaurantId) {
        log.info("get today menu for restaurant {}", restaurantId);
        return ResponseEntity.of(repository.getByRestaurantIdAndDate(restaurantId, LocalDate.now()));
    }
}