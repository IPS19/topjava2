package ru.javaops.topjava2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(name = "UK_one_vote_per_user_per_day", columnNames = {"date", "user_id", "restautant_id"})})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote {

    @Id
    @Column(name = "date")
    Date date;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    User user;

    @ManyToOne
    @JoinColumn(name = "restautant_id", referencedColumnName = "id")
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    Restaurant restaurant;
}