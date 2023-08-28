package com.github.IPS19.restaurant_voting_app.web.vote;

import com.github.IPS19.restaurant_voting_app.to.VoteTo;
import com.github.IPS19.restaurant_voting_app.web.MatcherFactory;

public class VoteTestData {

    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class);

    public static final MatcherFactory.Matcher<Integer> VOTES_MATCHER = MatcherFactory.usingEqualsComparator(Integer.class);

    public static final VoteTo voteTo1 = new VoteTo(3);
}