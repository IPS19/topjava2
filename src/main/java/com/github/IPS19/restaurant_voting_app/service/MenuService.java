package com.github.IPS19.restaurant_voting_app.service;

import com.github.IPS19.restaurant_voting_app.model.Menu;
import com.github.IPS19.restaurant_voting_app.repository.MenuRepository;
import com.github.IPS19.restaurant_voting_app.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MenuService {
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public Menu save(Menu menu, int restaurantId) {
        menu.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        return menuRepository.save(menu);
    }
}