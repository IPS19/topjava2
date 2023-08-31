package com.github.IPS19.restaurant_voting_app.web.menu;

import com.github.IPS19.restaurant_voting_app.web.AbstractControllerTest;
import com.github.IPS19.restaurant_voting_app.web.restaurant.RestaurantTestData;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.IPS19.restaurant_voting_app.web.user.UserTestData.USER_MAIL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserMenuControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = UserMenuController.REST_URL + "/";

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllTodayMenus() throws Exception {
        perform(MockMvcRequestBuilders.get(UserMenuController.REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MenuTestData.MENU_TO_MATCHER
                        .contentJson(MenuTestData.menuTo1, MenuTestData.menuTo2, MenuTestData.menuTo3));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getRestaurantByMenuId() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + MenuTestData.MENU1_ID + "/restaurant"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_TO_MATCHER
                        .contentJson(RestaurantTestData.restaurantTo1));
    }
}