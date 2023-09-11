package com.github.IPS19.restaurant_voting_app.web.restaurant;

import com.github.IPS19.restaurant_voting_app.model.Restaurant;
import com.github.IPS19.restaurant_voting_app.repository.RestaurantRepository;
import com.github.IPS19.restaurant_voting_app.util.OptionalExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

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

    public Restaurant getWithMenuByDate(int id, LocalDate date) {
        log.info("get restaurant {} with menu on {}", id, date);
        return OptionalExceptionUtil
                .getOrThrow(repository.getWithMenuByDate(id, date),
                        "restaurant or restaurant's menu on date " + date + " not found");
    }

    public List<Restaurant> getAllWithMenuByDate(LocalDate date) {
        log.info("get all restaurant with menu on {}", date);
        return repository.getAllWithMenuByDate(date).orElseGet(List::of);
    }

    public void delete(int id) {
        log.info("delete restaurant with id {}", id);
        repository.deleteExisted(id);
    }
}