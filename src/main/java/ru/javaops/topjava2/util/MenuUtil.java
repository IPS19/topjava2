package ru.javaops.topjava2.util;

import lombok.experimental.UtilityClass;
import ru.javaops.topjava2.model.Menu;
import ru.javaops.topjava2.to.MenuTo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class MenuUtil {
    public static Menu createTodayNewFromTo(MenuTo menuTo) {
        return new Menu(null, menuTo.getItems(), LocalDate.now());
    }

    public static MenuTo createToFromMenu(Menu menu) {
        return new MenuTo(menu.getItems(), menu.getDate());
    }

    public static List<MenuTo> createTosFromMenu(List<Menu> menus) {
        return menus.stream().map(MenuUtil::createToFromMenu).collect(Collectors.toList());
    }
}