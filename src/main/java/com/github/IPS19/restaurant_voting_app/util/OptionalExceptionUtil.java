package com.github.IPS19.restaurant_voting_app.util;

import com.github.IPS19.restaurant_voting_app.error.NotFoundException;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class OptionalExceptionUtil {
    public static <T> T getOrThrow(Optional<T> optional, String errorMsg) {
        return optional.orElseThrow(() -> new NotFoundException(errorMsg));
    }
}