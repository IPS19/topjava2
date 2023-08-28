package com.github.IPS19.restaurant_voting_app.web.vote;

import com.github.IPS19.restaurant_voting_app.web.AbstractControllerTest;
import com.github.IPS19.restaurant_voting_app.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.IPS19.restaurant_voting_app.web.vote.AdminVoteController.REST_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminVoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + "/";

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getUsersVoteByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "/users/6?date=2021-05-05"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.VOTE_TO_MATCHER.contentJson(VoteTestData.voteTo1));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getRestaurantVotesSumByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "/restaurants/3?date=2021-05-05"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.VOTES_MATCHER.contentJson(3));
    }
}