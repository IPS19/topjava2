package ru.javaops.topjava2.web.menu;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.topjava2.error.NotFoundException;
import ru.javaops.topjava2.model.Menu;
import ru.javaops.topjava2.repository.MenuRepository;

import java.time.LocalDate;

@RestController
@RequestMapping(value = UserMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class UserMenuController {
    static final String REST_URL = "/api/user/menus/";

    MenuRepository repository;

    @GetMapping("restaurant/{id}")
    public Menu getTodayByRestaurantId(@PathVariable int id) {
        return repository.getByRestaurantIdAndDate(id, LocalDate.now())
                .orElseThrow(() -> new NotFoundException("not found menu on restaurant " + id));
    }
}