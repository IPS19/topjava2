package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Query;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.model.User;
import ru.javaops.topjava2.model.Vote;

import java.time.LocalDate;
import java.util.Optional;

public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE v.user=:user AND v.date=:date")
    Optional<Vote> getByUserAndDate(User user, LocalDate date);

    @Query("SELECT v.restaurant.id FROM Vote v WHERE v.user.id=:id AND v.date=:date")
    Optional<Integer> getByUserIdAndDate(int id, LocalDate date);

    @Query("SELECT v.restaurant FROM Vote v WHERE v.user=:user AND v.date=:date")
    Optional<Restaurant> getVotedRestaurantByDate(User user, LocalDate date);

    @Query("SELECT count(v.restaurant) FROM Vote v WHERE v.restaurant.id=:id AND v.date=:date")
    int getSumRestaurantVotes(int id, LocalDate date);
}