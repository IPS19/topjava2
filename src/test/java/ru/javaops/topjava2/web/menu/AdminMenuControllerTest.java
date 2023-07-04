package ru.javaops.topjava2.web.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava2.model.Menu;
import ru.javaops.topjava2.repository.MenuRepository;
import ru.javaops.topjava2.to.MenuTo;
import ru.javaops.topjava2.util.JsonUtil;
import ru.javaops.topjava2.util.MenuUtil;
import ru.javaops.topjava2.web.AbstractControllerTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava2.web.menu.MenuTestData.*;
import static ru.javaops.topjava2.web.user.UserTestData.ADMIN_MAIL;

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
        Menu created = MENU_MATCHER.readFromJson(action);
        int newId = created.id();
        Menu newMenu = MenuUtil.createTodayNewFromTo(newMenuTo);
        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenu);
        MENU_MATCHER.assertMatch(repository.getExisted(newId), newMenu);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void addTodayNotValid() throws Exception {
        MenuTo newMenuTo = MenuTestData.getNotValid();
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + "4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenuTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateToday() throws Exception {
        MenuTo newMenuTo = MenuTestData.getNew();
        Menu updated = MenuUtil.createTodayNewFromTo(newMenuTo);
        updated.setId(MENU1_ID);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + "1").contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MENU_MATCHER.assertMatch(repository.getByRestaurantIdAndDate(1, LocalDate.now()).get(), updated);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getRestaurantMenusHistory() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "1/all"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(List.of(menuTo1, oldMenuTo1)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getByIdAndDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "2?date=2021-05-05"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(oldMenu2));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getTodayById() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "2"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menu2));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteByIdDate() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + "3"))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.getByRestaurantIdAndDate(3, LocalDate.now()).isPresent());
    }
}