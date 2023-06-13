package ru.javaops.topjava2.to;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import ru.javaops.topjava2.model.MenuItem;

import java.util.Set;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class MenuTo {
    @Size(min = 2, max = 5)
    @NotNull
    Set<MenuItem> items;
}