package ru.javaops.topjava2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "restaurant_id", "id"}, name = "uk_restaurant_date_menu")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Menu extends BaseEntity {

    @OneToMany(mappedBy = "menu", fetch = FetchType.EAGER)
    @Size(min = 2, max = 5)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OrderBy("price DESC")
    @JsonManagedReference
    Set<MenuItem> items;

    @Column(name = "date")
    @NotNull
    LocalDate date;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    Restaurant restaurant;

    public Menu(Integer id, Set<MenuItem> items, LocalDate date) {
        super(id);
        this.items = items;
        this.date = date;
    }
}