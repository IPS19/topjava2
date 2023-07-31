package ru.javaops.topjava2.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava2.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava2.web.user.UserTestData.ADMIN_MAIL;
import static ru.javaops.topjava2.web.vote.AdminVoteController.REST_URL;
import static ru.javaops.topjava2.web.vote.VoteTestData.*;

class AdminVoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + "/";

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getUsersVoteByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "/users/6?date=2021-05-05"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(voteTo1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getRestaurantVotesSumByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "/restaurants/3?date=2021-05-05"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTES_MATCHER.contentJson(3));
    }
}