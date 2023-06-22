package ru.javaops.topjava2.web.restaurant;

import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.web.MatcherFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javaops.topjava2.web.menu.MenuTestData.*;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menus");

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_WITH_MENU_MATCHER = MatcherFactory
            .usingAssertions(Restaurant.class, (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields("menus.restaurant").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });
    public static final int RESTAURANT1_ID = 1;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Tokio");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT1_ID + 1, "Ginza");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT1_ID + 2, "Bahroma");
    public static final Restaurant WITHOUT_MENU = new Restaurant(RESTAURANT1_ID + 3, "Without Menu");

    public static final Restaurant restaurantWithOldMenu1 = new Restaurant(RESTAURANT1_ID + 10, "Tokio");
    public static final Restaurant restaurantWithOldMenu2 = new Restaurant(RESTAURANT1_ID + 20, "Ginza");
    public static final Restaurant restaurantWithOldMenu3 = new Restaurant(RESTAURANT1_ID + 30, "Bahroma");

    public static final List<Restaurant> restaurants = List.of(restaurant1, restaurant2, restaurant3, WITHOUT_MENU);

    static {
        restaurant1.setMenus(List.of(menu1));
        restaurant2.setMenus(List.of(menu2));
        restaurant3.setMenus(List.of(notValidMenu));
        restaurantWithOldMenu1.setMenus(List.of(menu1, oldMenu1));
        restaurantWithOldMenu2.setMenus(List.of(menu2, oldMenu2));
        restaurantWithOldMenu3.setMenus(List.of(menu3, oldMenu3));
    }

    public static final List<Restaurant> restaurantsWithMenu = new ArrayList<>(Arrays.asList(restaurant1, restaurant2, restaurant3));

    public static Restaurant getNew() {
        return new Restaurant(null, "New Restaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(1, "Updated Restaurant");
    }
}