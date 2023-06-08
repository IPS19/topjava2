package ru.javaops.topjava2.util;

import lombok.experimental.UtilityClass;
import ru.javaops.topjava2.model.Menu;
import ru.javaops.topjava2.to.MenuTo;

import java.time.LocalDate;

@UtilityClass
public class MenuUtil {
    public static Menu createTodayNewFromTo(MenuTo menuTo) {
        return new Menu(null, menuTo.getItems(), LocalDate.now());
    }

    public static MenuTo createToFromMenu(Menu menu) {
        return new MenuTo(menu.getItems());
    }
}