package com.github.IPS19.restaurant_voting_app.service;

import com.github.IPS19.restaurant_voting_app.model.Menu;
import com.github.IPS19.restaurant_voting_app.repository.MenuRepository;
import com.github.IPS19.restaurant_voting_app.repository.RestaurantRepository;
import com.github.IPS19.restaurant_voting_app.to.MenuTo;
import com.github.IPS19.restaurant_voting_app.util.MenuUtil;
import com.github.IPS19.restaurant_voting_app.util.OptionalExceptionUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;

import static com.github.IPS19.restaurant_voting_app.util.validation.ValidationUtil.checkNew;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Menu addNew(MenuTo menuTo, int restaurantId) {
        checkNew(menuTo);
        Menu menu = MenuUtil.createTodayNewFromTo(menuTo);
        menu.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        return menuRepository.save(menu);
    }

    @Transactional
    public void update(@Valid @RequestBody MenuTo menuTo, @PathVariable int restaurantId) {
        Menu menu = MenuUtil.createTodayNewFromTo(menuTo);
        menu.setId(OptionalExceptionUtil.getOrThrow(menuRepository.getByRestaurantIdAndDate(restaurantId, menuTo.getDate()),
                        "restaurant " + restaurantId + "is not exist or doesn't have menu on" + menuTo.getDate())
                .id());
        menu.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        menuRepository.save(menu);
    }

    @Transactional
    public void deleteByRestaurantIdDate(int restaurantId, LocalDate date) {
        Menu toDelete = OptionalExceptionUtil
                .getOrThrow(menuRepository.getByRestaurantIdAndDate(restaurantId, date),
                        "not found menu on restaurant" + restaurantId + "on date " + date);
        menuRepository.delete(toDelete);
    }
}