package ru.javaops.topjava2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "menu_item")
public class MenuItem extends BaseEntity {

    @Column(name = "price")
    private Integer price;

    @Column(name = "item")
    private String item;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    Menu menu;
}