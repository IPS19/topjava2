package com.github.IPS19.restaurant_voting_app.service;

import com.github.IPS19.restaurant_voting_app.repository.MenuRepository;
import com.github.IPS19.restaurant_voting_app.to.MenuTo;
import com.github.IPS19.restaurant_voting_app.util.MenuUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final MenuRepository menuRepository;

    public List<MenuTo> getRestaurantMenusHistory(int restaurantId) {
        return MenuUtil.createTosFromMenus(menuRepository.getAllByRestaurantId(restaurantId).orElseGet(List::of));
    }
}