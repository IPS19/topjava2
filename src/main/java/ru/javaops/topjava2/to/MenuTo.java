package ru.javaops.topjava2.to;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Map;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class MenuTo {
    @Size(min = 2, max = 5)
    @NotNull
    Map<String, Integer> items;
}