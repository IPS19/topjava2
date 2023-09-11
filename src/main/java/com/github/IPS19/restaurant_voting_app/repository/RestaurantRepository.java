package com.github.IPS19.restaurant_voting_app.repository;

import com.github.IPS19.restaurant_voting_app.model.Restaurant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Query("""
            SELECT r FROM Restaurant r
            LEFT JOIN FETCH r.menus m
            LEFT JOIN FETCH m.items
            WHERE r.id=?1 and m.date=?2
            """)
    Optional<Restaurant> getWithMenuByDate(int id, LocalDate date);

    @Query("""
            SELECT r FROM Restaurant r
            LEFT JOIN FETCH r.menus m
            LEFT JOIN FETCH m.items
            WHERE m.date=?1
            ORDER BY r.id
            """)
    Optional<List<Restaurant>> getAllWithMenuByDate(LocalDate date);

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.menus")
    Optional<List<Restaurant>> getAllWithAllMenus();
}