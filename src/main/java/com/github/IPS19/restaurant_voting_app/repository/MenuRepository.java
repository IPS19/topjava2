package com.github.IPS19.restaurant_voting_app.repository;

import com.github.IPS19.restaurant_voting_app.model.Menu;
import com.github.IPS19.restaurant_voting_app.model.Restaurant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {
    @Query("SELECT m FROM Menu m LEFT JOIN FETCH m.items WHERE m.restaurant.id=?1 ORDER BY m.date desc")
    Optional<List<Menu>> getAllByRestaurantId(int id);

    @Query("SELECT m FROM Menu m LEFT JOIN FETCH m.items WHERE m.restaurant.id=?1 AND m.date=?2")
    Optional<Menu> getByRestaurantIdAndDate(int id, LocalDate date);

    @Query("SELECT m FROM Menu m LEFT JOIN FETCH m.items WHERE m.date=:date")
    Optional<List<Menu>> getAllByDate(LocalDate date);

    @Query("SELECT m.restaurant FROM Menu m WHERE m.id=:id")
    Optional<Restaurant> getRestaurantByMenuId(int id);
}