package com.github.IPS19.restaurant_voting_app.util;

import com.github.IPS19.restaurant_voting_app.model.DishItem;
import com.github.IPS19.restaurant_voting_app.model.Menu;
import com.github.IPS19.restaurant_voting_app.to.DishItemTo;
import com.github.IPS19.restaurant_voting_app.to.MenuTo;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class MenuUtil {
    public static Menu createTodayNewFromTo(MenuTo menuTo) {
        return new Menu(null, createDishItemsFromToS(menuTo.getItems()), menuTo.getDate());
    }

    public static MenuTo createToFromMenu(Menu menu) {
        return new MenuTo(menu.getId(), createDishToS(menu.getItems()), menu.getDate());
    }

    public static List<DishItem> createDishItemsFromToS(List<DishItemTo> dishItemTos) {
        return dishItemTos.stream()
                .map(dishItemTo -> new DishItem(dishItemTo.getPrice(), dishItemTo.getName()))
                .collect(Collectors.toList());
    }

    public static List<DishItemTo> createDishToS(List<DishItem> dishItems) {
        return dishItems.stream()
                .map(DishItemTo::new)
                .collect(Collectors.toList());
    }

    public static List<MenuTo> createTosFromMenus(List<Menu> menus) {
        return menus.stream()
                .map(MenuUtil::createToFromMenu)
                .collect(Collectors.toList());
    }
}