package com.github.IPS19.restaurant_voting_app.web.menu;

import com.github.IPS19.restaurant_voting_app.model.DishItem;
import com.github.IPS19.restaurant_voting_app.model.Menu;
import com.github.IPS19.restaurant_voting_app.to.DishItemTo;
import com.github.IPS19.restaurant_voting_app.to.MenuTo;
import com.github.IPS19.restaurant_voting_app.util.MenuUtil;
import com.github.IPS19.restaurant_voting_app.web.MatcherFactory;

import java.time.LocalDate;
import java.util.Set;

public class MenuTestData {

    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingEqualsComparator(Menu.class);
    public static final MatcherFactory.Matcher<MenuTo> MENU_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(MenuTo.class);

    public static final int MENU1_ID = 1;

    public static final Menu menu1 = new Menu(MENU1_ID, Set.of(new DishItem(100, "burgers"), new DishItem(120, "middle burgers"),
            new DishItem(150, "big burgers")), LocalDate.now());
    public static final Menu menu2 = new Menu(MENU1_ID + 1, Set.of(new DishItem(101, "sushi"), new DishItem(121, "middle sushi"),
            new DishItem(151, "big sushi")), LocalDate.now());
    public static final Menu menu3 = new Menu(MENU1_ID + 2, Set.of(new DishItem(102, "pizza"), new DishItem(122, "middle pizza"),
            new DishItem(152, "big pizza")), LocalDate.now());
    public static final Menu oldMenu1 = new Menu(MENU1_ID + 3, Set.of(new DishItem(152, "restaurant_1 old menu item"),
            new DishItem(152, "restaurant_1 old menu item2"), new DishItem(152, "restaurant_1 old menu item3")),
            LocalDate.of(2021, 5, 5));
    public static final Menu oldMenu2 = new Menu(MENU1_ID + 4, Set.of(new DishItem(152, "restaurant_2 old menu item"),
            new DishItem(152, "restaurant_2 old menu item2"), new DishItem(152, "restaurant_2 old menu item3")), LocalDate.of(2021, 5, 5));
    public static final Menu oldMenu3 = new Menu(MENU1_ID + 5, Set.of(new DishItem(152, "restaurant_3 old menu item"),
            new DishItem(152, "restaurant_3 old menu item2"), new DishItem(152, "restaurant_3 old menu item3")),
            LocalDate.of(2021, 5, 5));

    public static final Menu notValidMenu = new Menu(MENU1_ID + 2, Set.of(new DishItem(0, "ONE ITEM")),
            LocalDate.now());

    public static final MenuTo menuTo1 = MenuUtil.createToFromMenu(menu1);
    public static final MenuTo menuTo2 = MenuUtil.createToFromMenu(menu2);
    public static final MenuTo menuTo3 = MenuUtil.createToFromMenu(menu3);
    public static final MenuTo oldMenuTo1 = MenuUtil.createToFromMenu(oldMenu1);
    public static final MenuTo oldMenuTo2 = MenuUtil.createToFromMenu(oldMenu2);
    public static final MenuTo oldMenuTo3 = MenuUtil.createToFromMenu(oldMenu3);

    public static MenuTo getNew() {
        return new MenuTo(Set.of(new DishItemTo(100, "new item1"), new DishItemTo(200, "new item2"),
                new DishItemTo(300, "new item3")), LocalDate.now());
    }
}