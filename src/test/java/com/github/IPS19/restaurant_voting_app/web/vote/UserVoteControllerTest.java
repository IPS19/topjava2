package com.github.IPS19.restaurant_voting_app.web.vote;

import com.github.IPS19.restaurant_voting_app.service.VoteService;
import com.github.IPS19.restaurant_voting_app.util.VoteUtil;
import com.github.IPS19.restaurant_voting_app.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalTime;

import static com.github.IPS19.restaurant_voting_app.web.restaurant.RestaurantTestData.RESTAURANT1_ID;
import static com.github.IPS19.restaurant_voting_app.web.restaurant.RestaurantTestData.restaurant1;
import static com.github.IPS19.restaurant_voting_app.web.user.UserTestData.USER4_MAIL;
import static com.github.IPS19.restaurant_voting_app.web.user.UserTestData.USER_MAIL;
import static com.github.IPS19.restaurant_voting_app.web.vote.UserVoteController.REST_URL;
import static com.github.IPS19.restaurant_voting_app.web.vote.VoteTestData.VOTE_TO_MATCHER;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserVoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH_TODAY = REST_URL + "/today";

    @Test
    @WithUserDetails(value = USER_MAIL)
    void vote() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH_TODAY + "/" + RESTAURANT1_ID))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(VoteUtil.voteToTO(restaurant1)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
        //https://www.baeldung.com/java-override-system-time#:~:text=4.2.-,Using%20Mockito,-In%20addition%2C%20if
    void reVote() throws Exception {
        LocalTime notForbiddenRevote = VoteService.FORBIDDEN_TO_REVOTE_AFTER.minusHours(1L);
        try (MockedStatic<LocalTime> theMock = Mockito.mockStatic(LocalTime.class)) {
            theMock.when(LocalTime::now).thenReturn(notForbiddenRevote);
            perform(MockMvcRequestBuilders.post(REST_URL_SLASH_TODAY + "/" + RESTAURANT1_ID))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(VOTE_TO_MATCHER.contentJson(VoteUtil.voteToTO(restaurant1)));
        }
        LocalTime forbiddenToRevote = VoteService.FORBIDDEN_TO_REVOTE_AFTER.plusHours(1L);
        try (MockedStatic<LocalTime> theMock = Mockito.mockStatic(LocalTime.class)) {
            theMock.when(LocalTime::now).thenReturn(forbiddenToRevote);
            perform(MockMvcRequestBuilders.post(REST_URL_SLASH_TODAY + "/" + RESTAURANT1_ID))
                    .andExpect(status().isConflict());
        }
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void voteForNotExist() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH_TODAY + 15)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = USER4_MAIL)
    void getToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH_TODAY))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(VoteUtil.voteToTO(restaurant1)));
    }
}