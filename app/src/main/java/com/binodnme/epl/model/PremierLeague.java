package com.binodnme.epl.model;

import android.content.Context;


import com.binodnme.epl.rest.onefootball.OneFootball;

import java.util.List;

/**
 * Created by binodnme on 2/19/16.
 */
public class PremierLeague {
    private long id;
    private static List<Fixture> fixtures = null;
    private static MatchDetail matchDetail = null;
    private static List<ClubStanding> standings = null;


    public static void fetchMatchDays(){
        OneFootball.fetchMatchDays();
    }

    public static void fetchCurrentStanding(Context context){
        OneFootball.fetchStandings(context);
    }

    public static void getFixtures(Context context, long matchDayId){
        OneFootball.fetchFixtures(context, matchDayId);
    }

    public static void getMatchDetail(Context context, Fixture fixture){
        OneFootball.getMatchDetail(context, fixture);
    }

    public static void setFixtures(List<Fixture> f) {
        fixtures = f;
    }

    public static void setMatchDetail(MatchDetail md) {
        matchDetail = md;
    }

    public static void setStandings(List<ClubStanding> s) {
        standings = s;
    }
}
