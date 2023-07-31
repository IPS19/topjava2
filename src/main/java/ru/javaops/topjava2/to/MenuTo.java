package ru.javaops.topjava2.to;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.time.LocalDate;
import java.util.Map;

@Value
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class MenuTo {
    @Size(min = 2, max = 5)
    @NotNull
    Map<String, Integer> items;

    @Nullable
    LocalDate date;
}