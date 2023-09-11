package com.github.IPS19.restaurant_voting_app.repository;

import com.github.IPS19.restaurant_voting_app.model.DishItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface DishItemRepository extends BaseRepository<DishItem> {

    @Transactional
    @Modifying
    @Query("DELETE DishItem  di WHERE di.menu.id=:menuId")
    void deleteOldItems(int menuId);
}