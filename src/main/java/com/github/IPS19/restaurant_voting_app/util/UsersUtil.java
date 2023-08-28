package com.github.IPS19.restaurant_voting_app.util;

import com.github.IPS19.restaurant_voting_app.model.Role;
import com.github.IPS19.restaurant_voting_app.model.User;
import com.github.IPS19.restaurant_voting_app.to.UserTo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UsersUtil {

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), Role.USER);
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }
}