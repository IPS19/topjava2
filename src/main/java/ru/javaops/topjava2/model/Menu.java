package ru.javaops.topjava2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Map;

@Entity
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "restaurant_id", "id"}, name = "uk_restaurant_date_menu")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Menu extends BaseEntity {

    //https://stackoverflow.com/questions/7695831/how-can-i-cascade-delete-a-collection-which-is-part-of-a-jpa-entity/62848296#62848296:~:text=%40JoinColumn().-,Follow%2Dup%20example%3A,-%40Entity%0Apublic%20class
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    Restaurant restaurant;

    public Menu(Integer id, Map<String, Integer> items, LocalDate date) {
        super(id);
        this.items = items;
        this.date = date;
    }
}