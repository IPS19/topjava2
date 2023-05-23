package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Query;
import ru.javaops.topjava2.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.menus m WHERE r.id=?1 and m.date=?2")
    Optional<Restaurant> getWithMenuByDate(int id, LocalDate date);

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.menus m WHERE m.date=?1")
    List<Restaurant> getAllWithMenuByDate(LocalDate date);
}