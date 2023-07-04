package ru.javaops.topjava2.web.vote;

import ru.javaops.topjava2.to.VoteTo;
import ru.javaops.topjava2.web.MatcherFactory;

public class VoteTestData {

    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class);

    public static final MatcherFactory.Matcher<Integer> VOTES_MATCHER = MatcherFactory.usingEqualsComparator(Integer.class);

    public static final VoteTo voteTo1 = new VoteTo(3);
}