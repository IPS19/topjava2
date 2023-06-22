package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {
    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=?1 ORDER BY m.date desc")
    List<Menu> getAllByRestaurantId(int id);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=?1 AND m.date=?2")
    Optional<Menu> getByRestaurantIdAndDate(int id, LocalDate date);

    @Transactional
    @Query("DELETE FROM Menu m WHERE m.restaurant.id=:id AND m.date=:localDate")
    void deleteByIdDate(int id, LocalDate localDate);
}