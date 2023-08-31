package com.github.IPS19.restaurant_voting_app.web.menu;

import com.github.IPS19.restaurant_voting_app.model.Menu;
import com.github.IPS19.restaurant_voting_app.repository.MenuRepository;
import com.github.IPS19.restaurant_voting_app.to.MenuTo;
import com.github.IPS19.restaurant_voting_app.util.JsonUtil;
import com.github.IPS19.restaurant_voting_app.util.MenuUtil;
import com.github.IPS19.restaurant_voting_app.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static com.github.IPS19.restaurant_voting_app.web.user.UserTestData.ADMIN_MAIL;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminMenuControllerTest extends AbstractControllerTest {

    @Autowired
    MenuRepository repository;

    private static final String REST_URL_SLASH = AdminMenuController.REST_URL + "/";

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void addToday() throws Exception {
        MenuTo newMenuTo = MenuTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL_SLASH + "4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenuTo)));
        Menu created = MenuTestData.MENU_MATCHER.readFromJson(action);
        int newId = created.id();
        Menu newMenu = MenuUtil.createTodayNewFromTo(newMenuTo);
        newMenu.setId(newId);
        MenuTestData.MENU_MATCHER.assertMatch(created, newMenu);
        MenuTestData.MENU_MATCHER.assertMatch(repository.getExisted(newId), newMenu);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateToday() throws Exception {
        MenuTo newMenuTo = MenuTestData.getNew();
        Menu updated = MenuUtil.createTodayNewFromTo(newMenuTo);
        updated.setId(MenuTestData.MENU1_ID);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + "1").contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MenuTestData.MENU_MATCHER.assertMatch(repository.getByRestaurantIdAndDate(1, LocalDate.now()).get(), updated);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getByIdAndDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "2?date=2021-05-05"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MenuTestData.MENU_MATCHER.contentJson(MenuTestData.oldMenu2));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getTodayById() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "2"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MenuTestData.MENU_MATCHER.contentJson(MenuTestData.menu2));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteByIdDate() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + "3"))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.getByRestaurantIdAndDate(3, LocalDate.now()).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllOnDate() throws Exception {
        perform(MockMvcRequestBuilders.get(AdminMenuController.REST_URL + "?date=2021-05-05"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MenuTestData.MENU_TO_MATCHER.contentJson(MenuTestData.oldMenuTo1, MenuTestData.oldMenuTo2, MenuTestData.oldMenuTo3));
    }
}