package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {
    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=?1")
    List<Menu> getAllById(int id);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=?1 and m.date=?2")
    Optional<Menu> getMenuByIdAndDate(int id, LocalDate date);

    @Transactional
    @Query("UPDATE Menu m SET m=?1 WHERE m.restaurant.id=?2 and m.date=?3")
    void updateMenu(Menu menu, int id, LocalDate date);
}