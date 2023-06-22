package ru.javaops.topjava2.web.restaurant;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.error.NotFoundException;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.RestaurantRepository;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ru.javaops.topjava2.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava2.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminRestaurantController extends AbstractRestaurantController {

    static final String REST_URL = "/api/admin/restaurants";

    private final RestaurantRepository repository;

    @GetMapping
    public List<Restaurant> getAll() {
        return super.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        return ResponseEntity.of(Optional.of(super.getById(id)));
    }

    //http://localhost:8080/api/admin/restaurants/1/with-menu?date=2023-05-31
//https://www.baeldung.com/spring-request-param#:~:text=Using%20Java%208%20Optional
    @GetMapping("/{id}/with-menu")
    public ResponseEntity<Restaurant> getWithMenuByIdDate(@PathVariable int id,
                                                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> date) {
        log.info("get {} with menu on {}", id, date);
        return ResponseEntity.of(super.getWithMenuByDate(id, date.orElseGet(LocalDate::now)));
    }

    @GetMapping("/all-with-menu")
    public List<Restaurant> getAllWithMenuByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> date) {
        log.info("get all with menu on {}", date.orElseGet(LocalDate::now));
        return super.getAllWithMenuByDate(date.orElseGet(LocalDate::now));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update restaurant {}", id);
        assureIdConsistent(restaurant, id);
        repository.save(restaurant);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        log.info("create restaurant {}", restaurant);
        checkNew(restaurant);
        Restaurant newRestaurant = repository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(newRestaurant.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newRestaurant);
    }

    @GetMapping("/all-empty-menu")
    public List<Restaurant> getWithEmptyMenu() {
        log.info("get all with empty menu");
        return repository.getAllWithEmptyMenu().orElseThrow(() -> new NotFoundException("no restaurant without menu"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant {}", id);
        super.delete(id);
    }
}