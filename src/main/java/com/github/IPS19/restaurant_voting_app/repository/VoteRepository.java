package com.github.IPS19.restaurant_voting_app.repository;

import com.github.IPS19.restaurant_voting_app.model.Restaurant;
import com.github.IPS19.restaurant_voting_app.model.User;
import com.github.IPS19.restaurant_voting_app.model.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE v.user=:user AND v.date=:date")
    Optional<Vote> getByUserAndDate(User user, LocalDate date);

    @Query("SELECT v.restaurant.id FROM Vote v WHERE v.user=:user AND v.date=:date")
    Optional<Integer> getVotedRestaurantByDate(User user, LocalDate date);

    @Query("SELECT count(v.restaurant) FROM Vote v WHERE v.restaurant.id=:id AND v.date=:date")
    int getSumRestaurantVotes(int id, LocalDate date);

    @Query("SELECT v FROM Vote v WHERE v.user=:user ORDER BY v.date DESC")
    Optional<List<Vote>> getUsersVotes(User user);
}