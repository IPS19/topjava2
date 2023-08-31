package com.github.IPS19.restaurant_voting_app.to;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDate;

@Value
@Getter
@EqualsAndHashCode(callSuper = true)
public class VoteRespTo extends NamedTo {
    LocalDate date;

    public VoteRespTo(int id, String name, LocalDate date) {
        super(id, name);
        this.date = date;
    }
}