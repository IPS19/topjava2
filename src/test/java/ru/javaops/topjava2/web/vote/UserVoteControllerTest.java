package ru.javaops.topjava2.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava2.util.VoteUtil;
import ru.javaops.topjava2.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.RESTAURANT1_ID;
import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.restaurant1;
import static ru.javaops.topjava2.web.user.UserTestData.USER4_MAIL;
import static ru.javaops.topjava2.web.user.UserTestData.USER_MAIL;
import static ru.javaops.topjava2.web.vote.UserVoteController.REST_URL;
import static ru.javaops.topjava2.web.vote.VoteTestData.VOTE_TO_MATCHER;

class UserVoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + "/";

    @Test
    @WithUserDetails(value = USER_MAIL)
    void vote() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + RESTAURANT1_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(VoteUtil.voteToTO(restaurant1)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void voteForNotExist() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + 15)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = USER4_MAIL)
    void getToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(VoteUtil.voteToTO(restaurant1)));
    }
}