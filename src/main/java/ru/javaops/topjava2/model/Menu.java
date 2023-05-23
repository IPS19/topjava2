package ru.javaops.topjava2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Map;

@Entity
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "restaurant_id", "id"}, name = "uk_restaurant_date_menu")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseEntity {

    @ElementCollection(fetch = FetchType.EAGER)//???
    @CollectionTable(name = "menu_items", joinColumns = @JoinColumn(name = "menu_id"))//???
// ↑choose the name of the DB table storing the Map<>↑
    @MapKeyColumn(name = "item")// choose the name of the DB column used to store the Map<> key
    @Column(name = "price")     // choose the name of the DB column used to store the Map<> value
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    // ↑menu_id- *fk* to id  table menu<>↑
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Size(min = 2, max = 5)
    @NotNull
    Map<String, Integer> items;

    @Column(name = "date")
    @NotNull
    LocalDate date;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @NotNull
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    Restaurant restaurant;
}