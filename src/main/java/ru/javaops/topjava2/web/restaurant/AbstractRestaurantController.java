package ru.javaops.topjava2.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaops.topjava2.error.NotFoundException;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class AbstractRestaurantController {

    @Autowired
    protected RestaurantRepository repository;

    public Restaurant getById(int id) {
        log.info("get restaurant with id {}", id);
        return repository.getExisted(id);
    }

    public List<Restaurant> getAll() {
        log.info("getAll restaurants");
        return repository.findAll();
    }

    public Optional<Restaurant> getWithMenuByDate(int id, LocalDate date) {
        log.info("get restaurant {} with menu on {}", id, date);
        return Optional.of(repository.getWithMenuByDate(id, date)
                .orElseThrow(() -> new NotFoundException("restaurant or restaurant's menu on date " + date + " not found")));
    }

    public List<Restaurant> getAllWithMenuByDate(LocalDate date) {
        log.info("get all restaurant with menu on {}", date);
        return repository.getAllWithMenuByDate(date);
    }

    public void delete(int id) {
        log.info("delete restaurant with id {}", id);
        repository.deleteExisted(id);
    }
}