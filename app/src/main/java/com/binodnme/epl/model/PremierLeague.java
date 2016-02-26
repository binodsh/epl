package com.binodnme.epl.model;

import android.content.Context;

import com.binodnme.epl.rest.onefootball.OneFootball;

import java.util.List;

/**
 * Created by binodnme on 2/19/16.
 */
public class PremierLeague {
    private long id;
    private static List<MatchDay> matchDays = null;
    private static List<Fixture> fixtures = null;
    private static MatchDetail matchDetail = null;
    private static List<ClubStanding> standings = null;


    public static void fetchMatchDays(){
        OneFootball.fetchMatchDays();
    }

    public static void fetchCurrentStanding(Context context){
        OneFootball.fetchStandings();
    }

    public static void fetchFixtures(Context context, long matchDayId){
        OneFootball.fetchFixtures(matchDayId);
    }

    public static void fetchMatchDetail(Context context, Fixture fixture){
        OneFootball.getMatchDetail(fixture);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static List<MatchDay> getMatchDays() {
        return matchDays;
    }

    public static void setMatchDays(List<MatchDay> matchDays) {
        PremierLeague.matchDays = matchDays;
    }

    public static List<Fixture> getFixtures() {
        return fixtures;
    }

    public static void setFixtures(List<Fixture> fixtures) {
        PremierLeague.fixtures = fixtures;
    }

    public static MatchDetail getMatchDetail() {
        return matchDetail;
    }

    public static void setMatchDetail(MatchDetail matchDetail) {
        PremierLeague.matchDetail = matchDetail;
    }

    public static List<ClubStanding> getStandings() {
        return standings;
    }

    public static void setStandings(List<ClubStanding> standings) {
        PremierLeague.standings = standings;
    }
}
