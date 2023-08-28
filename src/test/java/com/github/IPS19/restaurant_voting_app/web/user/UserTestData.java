package com.github.IPS19.restaurant_voting_app.web.user;

import com.github.IPS19.restaurant_voting_app.model.Role;
import com.github.IPS19.restaurant_voting_app.model.User;
import com.github.IPS19.restaurant_voting_app.util.JsonUtil;
import com.github.IPS19.restaurant_voting_app.web.MatcherFactory;

import java.util.Collections;
import java.util.Date;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "password");

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int GUEST_ID = 3;
    public static final int NOT_FOUND = 100;
    public static final String USER_MAIL = "user@yandex.com";
    public static final String USER4_MAIL = "user4@yandex.com";
    public static final String USER5_MAIL = "user5@yandex.com";
    public static final String USER6_MAIL = "user6@yandex.com";
    public static final String ADMIN_MAIL = "admin@gmail.com";
    public static final String GUEST_MAIL = "guest@gmail.com";

    public static final User user = new User(USER_ID, "User", USER_MAIL, "password", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin", Role.ADMIN, Role.USER);
    public static final User guest = new User(GUEST_ID, "Guest", GUEST_MAIL, "guest");

    public static final User user4 = new User(4, "User4", USER4_MAIL, "password4", Role.USER);
    public static final User user5 = new User(5, "User5", USER5_MAIL, "password5", Role.USER);
    public static final User user6 = new User(6, "User6", USER6_MAIL, "password6", Role.USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER_ID, "UpdatedName", USER_MAIL, "newPass", false, new Date(), Collections.singleton(Role.ADMIN));
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}