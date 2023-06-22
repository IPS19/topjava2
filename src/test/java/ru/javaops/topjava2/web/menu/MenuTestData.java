package ru.javaops.topjava2.web.menu;

import ru.javaops.topjava2.model.Menu;
import ru.javaops.topjava2.to.MenuTo;
import ru.javaops.topjava2.web.MatcherFactory;

import java.time.LocalDate;
import java.util.Map;

public class MenuTestData {

    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingEqualsComparator(Menu.class);

    public static final int MENU1_ID = 1;

    public static final Menu menu1 = new Menu(MENU1_ID, Map.of("burgers", 100, "middle burgers", 120,
            "big burgers", 150), LocalDate.now());
    public static final Menu menu2 = new Menu(MENU1_ID + 1, Map.of("sushi", 101, "middle sushi", 121,
            "big sushi", 151), LocalDate.now());
    public static final Menu menu3 = new Menu(MENU1_ID + 2, Map.of("pizza", 102, "middle pizza", 122,
            "big pizza", 152), LocalDate.now());
    public static final Menu oldMenu1 = new Menu(MENU1_ID + 3, Map.of("restaurant_1 old menu item", 152,
            "restaurant_1 old menu item2", 152, "restaurant_1 old menu item3", 152), LocalDate.now());
    public static final Menu oldMenu2 = new Menu(MENU1_ID + 4, Map.of("restaurant_2 old menu item", 152,
            "restaurant_2 old menu item2", 152, "restaurant_2 old menu item3", 152), LocalDate.now());
    public static final Menu oldMenu3 = new Menu(MENU1_ID + 5, Map.of("restaurant_3 old menu item", 152,
            "restaurant_3 old menu item2", 152, "restaurant_3 old menu item3", 152), LocalDate.now());

    public static final Menu notValidMenu = new Menu(MENU1_ID + 2, Map.of("ONE ITEM", 0), LocalDate.now());

    public static MenuTo getNew() {
        return new MenuTo(Map.of("new item1", 100, "new item2", 200, "new item3", 300));
    }

    public static MenuTo getNotValid() {
        return new MenuTo(Map.of("ONE ITEM", 100));
    }
}
