package com.binodnme.epl.rest.onefootball;

import android.content.Context;

import com.binodnme.epl.constants.ApplicationConstant;
import com.binodnme.epl.model.ClubStanding;
import com.binodnme.epl.model.Fixture;
import com.binodnme.epl.model.MatchDay;
import com.binodnme.epl.model.MatchDetail;
import com.binodnme.epl.model.MatchTeamDetail;
import com.binodnme.epl.model.MatchTeamPlayerDetail;
import com.binodnme.epl.model.PremierLeague;
import com.binodnme.epl.utils.DateUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by binodnme on 2/20/16.
 */
public class OneFootball {
    private static final String COMPETITION_ID = "9";
    private static final String SEASON_ID = "1231";

    private static final String STANDINGS_JSON = "standings.json";
    private static final String MATCH_DAY_JSON = "matchday.json";
    private static final String MATCH_DETAILS_JSON = "match-details.json";
    private static final String CHARACTER_ENCODING = "UTF-8";

    private static final String STANDING_TEAM_NAME = "name";
    private static final String STANDING_POSITION = "index";
    private static final String STANDING_GAME_PLAYED = "played";
    private static final String STANDING_GOAL_SCORED = "goalsShot";
    private static final String STANDING_GOAL_AGAINST = "goalsGot";
    private static final String STANDING_POINTS = "points";

    private static final String GROUPS = "groups";
    private static final String RANKING = "ranking";
    private static final String TEAM = "team";
    private static final String TEAM_STATS = "teamstats";

    private static final String KICK_OFFS = "kickoffs";
    private static final String KICK_OFF = "kickoff";
    private static final String MATCHES = "matches";
    private static final String MATCH_ID = "matchId";
    private static final String TEAM_HOME = "teamhome";
    private static final String TEAM_AWAY = "teamaway";
    private static final String NAME = "name";
    private static final String SCORE_HOME = "scorehome";
    private static final String SCORE_AWAY = "scoreaway";
    private static final String PERIOD = "period";


    private static final String FULLTIME = "fulltime";
    private static final String HALFTIME = "halftime";
    private static final String FIRSTHALF = "firsthalf";
    private static final String SECONDHALF = "secondhalf";
    private static final String PREMATCH = "prematch";
    private static final String POSTPONED = "postponed";

    private static final String MATCH = "match";
    private static final String MINUTE = "minute";
    private static final String STADIUM = "stadium";
    private static final String REFEREE = "referee";

    private static final String ID = "id";
    private static final String FORMATION = "formation";
    private static final String POSITION = "position";
    private static final String NUMBER = "number";
    private static final String BENCH = "bench";



    private static final String TEST_JSON = "liga-bbva-test2.json";


    //test
    public static StandingsInterface standingsInterface;
    public static FixtureInterface fixtureInterface;
    public static MatchDetailInterface matchDetailInterface;
    public static MatchDaysInterface matchDaysInterface;

    private static final String MATCHDAYS = "matchdays";
    private static final String IS_CURRENT_MATCHDAY = "isCurrentMatchday";


    public static void fetchMatchDays(){
        String url = "https://feedmonster.onefootball.com/feeds/il/en/competitions/9/1231/matchdaysOverview.json";

        OneFootballClient.get(url, new RequestParams(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    List<MatchDay> matchDays = new ArrayList<MatchDay>();
                    JSONArray matchDayJsonArray = response.getJSONArray(MATCHDAYS);
                    for(int i=0; i<matchDayJsonArray.length(); i++){
                        JSONObject matchDayJson = matchDayJsonArray.getJSONObject(i);
                        MatchDay matchDay = new MatchDay();
                        matchDay.setId(matchDayJson.getLong(ID));
                        matchDay.setName(matchDayJson.getString(NAME));
                        matchDay.setCurrentMatchDay(matchDayJson.getBoolean(IS_CURRENT_MATCHDAY));

                        matchDays.add(matchDay);
                    }

                    matchDaysInterface.onMatchDayFetchSuccess(matchDays);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    public static void fetchStandings(Context context){

        String url = "https://feedmonster.onefootball.com/feeds/il/en/competitions/9/1231/standings.json";

        OneFootballClient.get(url, new RequestParams(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject jsonObject = response;
                    JSONArray groups = jsonObject.getJSONArray(GROUPS);
                    JSONArray ranking = null;

                    for(int i=0; i<groups.length(); i++){
                        try{
                            ranking = groups.getJSONObject(i).getJSONArray(RANKING);
                            break;
                        }catch (JSONException e){}
                    }

                    List<ClubStanding> clubStandings  = new ArrayList<>();
                    if(ranking != null){
                        for(int i=0; i<ranking.length(); i++){
                            JSONObject rank = ranking.getJSONObject(i);
                            JSONObject team = rank.getJSONObject(TEAM);
                            JSONObject teamStats = team.getJSONObject(TEAM_STATS);

                            ClubStanding cs = new ClubStanding();
                            cs.setPosition(rank.getInt(STANDING_POSITION));
                            cs.setName(team.getString(STANDING_TEAM_NAME));
                            cs.setMatchPlayed(teamStats.getInt(STANDING_GAME_PLAYED));
                            cs.setGoalsFor(teamStats.getInt(STANDING_GOAL_SCORED));
                            cs.setGoalsAgainst(teamStats.getInt(STANDING_GOAL_AGAINST));
                            cs.setPoints(teamStats.getInt(STANDING_POINTS));
                            clubStandings.add(cs);
                        }
                    }

                    PremierLeague.setStandings(clubStandings);

                    standingsInterface.onSuccess(clubStandings);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void fetchFixtures(Context context, long matchDayId){
        String url = "https://feedmonster.onefootball.com/feeds/il/en/competitions/9/1231/matchdays/"+matchDayId+".json";
        String json;
        OneFootballClient.get(url, new RequestParams(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    JSONObject jsonObject = response;
                    JSONArray kickOffs = jsonObject.getJSONArray(KICK_OFFS);

                    List<Fixture> fixtures = new ArrayList<>();

                    for(int i=0; i<kickOffs.length(); i++){
                        JSONObject obj = kickOffs.getJSONObject(i);
                        JSONArray groups = obj.getJSONArray(GROUPS);
                        String stringDate = obj.getString(KICK_OFF);
                        JSONArray matches = null;
                        for (int j=0; j<groups.length(); j++){
                            try{
                                matches = groups.getJSONObject(j).getJSONArray(MATCHES);
                                break;
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }

                        if(matches != null){
                            for(int k=0; k<matches.length(); k++){
                                Fixture fixture =  new Fixture();
                                JSONObject match = matches.getJSONObject(k);
                                JSONObject teamHome = match.getJSONObject(TEAM_HOME);
                                JSONObject teamAway = match.getJSONObject(TEAM_AWAY);

                                fixture.setMatchId(match.getLong(MATCH_ID));
                                fixture.setHomeTeamName(teamHome.getString(NAME));
                                fixture.setHomeTeamScore(match.getInt(SCORE_HOME));
                                fixture.setAwayTeamName(teamAway.getString(NAME));
                                fixture.setAwayTeamScore(match.getInt(SCORE_AWAY));

                                String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";
                                Date matchDate = DateUtils.getDateObject(stringDate, dateFormat);
                                fixture.setMatchDate(matchDate);

                                switch (match.getString(PERIOD).toLowerCase()){
                                    case FULLTIME:
                                        fixture.setMatchStatus(ApplicationConstant.FT);
                                        break;

                                    case HALFTIME:
                                        fixture.setMatchStatus(ApplicationConstant.HT);
                                        break;

                                    case FIRSTHALF:
                                        fixture.setMatchStatus(ApplicationConstant.FH);
                                        break;

                                    case SECONDHALF:
                                        fixture.setMatchStatus(ApplicationConstant.SH);
                                        break;

                                    case PREMATCH:
                                        fixture.setMatchStatus(ApplicationConstant.PM);
                                        break;

                                    case POSTPONED:
                                        fixture.setMatchStatus(ApplicationConstant.PP);
                                }
                                fixtures.add(fixture);
                            }
                        }
                    }

                    fixtureInterface.onSuccess(fixtures);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
//        try {
//            InputStream is = context.getAssets().open(TEST_JSON);
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, CHARACTER_ENCODING);
//
//            JSONObject jsonObject = new JSONObject(json);
//            JSONArray kickOffs = jsonObject.getJSONArray(KICK_OFFS);
//
//            List<Fixture> fixtures = new ArrayList<>();
//
//            for(int i=0; i<kickOffs.length(); i++){
//                JSONObject obj = kickOffs.getJSONObject(i);
//                JSONArray groups = obj.getJSONArray(GROUPS);
//                String stringDate = obj.getString(KICK_OFF);
//                JSONArray matches = null;
//                for (int j=0; j<groups.length(); j++){
//                    try{
//                        matches = groups.getJSONObject(j).getJSONArray(MATCHES);
//                        break;
//                    }catch (JSONException e){
//                        e.printStackTrace();
//                    }
//                }
//
//                if(matches != null){
//                    for(int k=0; k<matches.length(); k++){
//                        Fixture fixture =  new Fixture();
//                        JSONObject match = matches.getJSONObject(k);
//                        JSONObject teamHome = match.getJSONObject(TEAM_HOME);
//                        JSONObject teamAway = match.getJSONObject(TEAM_AWAY);
//
//                        fixture.setMatchId(match.getLong(MATCH_ID));
//                        fixture.setHomeTeamName(teamHome.getString(NAME));
//                        fixture.setHomeTeamScore(match.getInt(SCORE_HOME));
//                        fixture.setAwayTeamName(teamAway.getString(NAME));
//                        fixture.setAwayTeamScore(match.getInt(SCORE_AWAY));
//
//                        String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";
//                        Date matchDate = DateUtils.getDateObject(stringDate, dateFormat);
//                        fixture.setMatchDate(matchDate);
//
//                        switch (match.getString(PERIOD).toLowerCase()){
//                            case FULLTIME:
//                                fixture.setMatchStatus(ApplicationConstant.FT);
//                                break;
//
//                            case HALFTIME:
//                                fixture.setMatchStatus(ApplicationConstant.HT);
//                                break;
//
//                            case FIRSTHALF:
//                                fixture.setMatchStatus(ApplicationConstant.FH);
//                                break;
//
//                            case SECONDHALF:
//                                fixture.setMatchStatus(ApplicationConstant.SH);
//                                break;
//
//                            case PREMATCH:
//                                fixture.setMatchStatus(ApplicationConstant.PM);
//                        }
//                        fixtures.add(fixture);
//                    }
//                }
//            }
//
//            return fixtures;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

    }

    public static void getMatchDetail(Context context, final Fixture fixture){
        String url = "https://feedmonster.onefootball.com/feeds/il/en/competitions/9/1231/matches/"+fixture.getMatchId()+".json";
        String json;

        OneFootballClient.get(url, new RequestParams(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try{


                    //declare match detail object
                    MatchDetail matchDetail = new MatchDetail();

                    //build match detail json object from json string
                    JSONObject matchDetailJsonOjb = response;

                    JSONObject matchJsonObj = matchDetailJsonOjb.getJSONObject(MATCH);
                    int minute = matchJsonObj.getInt(MINUTE);
                    JSONObject stadiumJsonObj = matchJsonObj.getJSONObject(STADIUM);
                    JSONObject refereeJsonObj = matchJsonObj.getJSONObject(REFEREE);
                    JSONObject teamHomeJsonObj = matchJsonObj.getJSONObject(TEAM_HOME);
                    JSONObject teamAwayJsonObj = matchJsonObj.getJSONObject(TEAM_AWAY);
                    //assign cards and goals json objects here

                    String stadiumName = stadiumJsonObj.getString(NAME);
                    String refereeName = refereeJsonObj.getString(NAME);

                    //home team details object holds the details on home team including the formation, substitutions, etc
                    MatchTeamDetail homeTeamDetail = new MatchTeamDetail();
                    long homeTeamId = teamHomeJsonObj.getLong(ID);
                    homeTeamDetail.setTeamId(homeTeamId);
                    homeTeamDetail.setTeamName(fixture.getHomeTeamName());

                    JSONArray homeTeamStartingLineUpJsonArray = teamHomeJsonObj.getJSONArray(FORMATION);
                    List<MatchTeamPlayerDetail> homeTeamStartingLineUp = new ArrayList<>();
                    for(int i=0; i<homeTeamStartingLineUpJsonArray.length(); i++){
                        MatchTeamPlayerDetail player = new MatchTeamPlayerDetail();
                        JSONObject playerDetailJsonObj = homeTeamStartingLineUpJsonArray.getJSONObject(i);

                        player.setId(playerDetailJsonObj.getLong(ID));
                        player.setName(playerDetailJsonObj.getString(NAME));
                        player.setPosition(playerDetailJsonObj.getString(POSITION));
                        player.setNumber(playerDetailJsonObj.getLong(NUMBER));

                        homeTeamStartingLineUp.add(player);
                    }

                    homeTeamDetail.setStartingLineUps(homeTeamStartingLineUp);

                    JSONArray homeTeamBenchPlayersJsonArray = teamHomeJsonObj.getJSONArray(BENCH);
                    List<MatchTeamPlayerDetail> homeTeamBenchPlayers = new ArrayList<>();
                    for(int i=0; i<homeTeamBenchPlayersJsonArray.length(); i++){
                        MatchTeamPlayerDetail player = new MatchTeamPlayerDetail();
                        JSONObject playerDetailJsonObj = homeTeamBenchPlayersJsonArray.getJSONObject(i);

                        player.setId(playerDetailJsonObj.getLong(ID));
                        player.setName(playerDetailJsonObj.getString(NAME));
                        player.setPosition(playerDetailJsonObj.getString(POSITION));
                        player.setNumber(playerDetailJsonObj.getLong(NUMBER));

                        homeTeamBenchPlayers.add(player);
                    }

                    homeTeamDetail.setSubstitutes(homeTeamBenchPlayers);



                    //away team details object holds the details on away team including the formation, substitutions, etc
                    MatchTeamDetail awayTeamDetail = new MatchTeamDetail();
                    long awayTeamId = teamAwayJsonObj.getLong(ID);
                    awayTeamDetail.setTeamId(awayTeamId);
                    awayTeamDetail.setTeamName(fixture.getAwayTeamName());

                    JSONArray awayTeamStartingLineUpJsonArray = teamAwayJsonObj.getJSONArray(FORMATION);
                    List<MatchTeamPlayerDetail> awayTeamStartingLineUp = new ArrayList<>();
                    for(int i=0; i<awayTeamStartingLineUpJsonArray.length(); i++){
                        MatchTeamPlayerDetail player = new MatchTeamPlayerDetail();
                        JSONObject playerDetailJsonObj = awayTeamStartingLineUpJsonArray.getJSONObject(i);

                        player.setId(playerDetailJsonObj.getLong(ID));
                        player.setName(playerDetailJsonObj.getString(NAME));
                        player.setPosition(playerDetailJsonObj.getString(POSITION));
                        player.setNumber(playerDetailJsonObj.getLong(NUMBER));

                        awayTeamStartingLineUp.add(player);
                    }

                    awayTeamDetail.setStartingLineUps(awayTeamStartingLineUp);

                    JSONArray awayTeamBenchPlayersJsonArray = teamAwayJsonObj.getJSONArray(BENCH);
                    List<MatchTeamPlayerDetail> awayTeamBenchPlayers = new ArrayList<>();
                    for(int i=0; i<awayTeamBenchPlayersJsonArray.length(); i++){
                        MatchTeamPlayerDetail player = new MatchTeamPlayerDetail();
                        JSONObject playerDetailJsonObj = awayTeamBenchPlayersJsonArray.getJSONObject(i);

                        player.setId(playerDetailJsonObj.getLong(ID));
                        player.setName(playerDetailJsonObj.getString(NAME));
                        player.setPosition(playerDetailJsonObj.getString(POSITION));
                        player.setNumber(playerDetailJsonObj.getLong(NUMBER));

                        awayTeamBenchPlayers.add(player);
                    }

                    awayTeamDetail.setSubstitutes(awayTeamBenchPlayers);



                    //setting field values for match details object
                    matchDetail.setMatchId(fixture.getMatchId());
                    matchDetail.setHomeTeamScore(fixture.getHomeTeamScore());
                    matchDetail.setAwayTeamScore(fixture.getAwayTeamScore());
                    matchDetail.setMatchStatus(fixture.getMatchStatus());
                    matchDetail.setKickoffTime(fixture.getMatchDate());
                    matchDetail.setMinute(minute);
                    matchDetail.setRefereeName(refereeName);
                    matchDetail.setStadiumName(stadiumName);
                    matchDetail.setHomeTeam(homeTeamDetail);
                    matchDetail.setAwayTeam(awayTeamDetail);

                    System.out.println(matchDetail);
                    matchDetailInterface.onSuccess(matchDetail);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

//        try{
//            InputStream is = context.getAssets().open(MATCH_DETAILS_JSON);
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//
//            //get json string
//            json = new String(buffer, CHARACTER_ENCODING);
//
//            //declare match detail object
//            MatchDetail matchDetail = new MatchDetail();
//
//            //build match detail json object from json string
//            JSONObject matchDetailJsonOjb = new JSONObject(json);
//
//            JSONObject matchJsonObj = matchDetailJsonOjb.getJSONObject(MATCH);
//            int minute = matchJsonObj.getInt(MINUTE);
//            JSONObject stadiumJsonObj = matchJsonObj.getJSONObject(STADIUM);
//            JSONObject refereeJsonObj = matchJsonObj.getJSONObject(REFEREE);
//            JSONObject teamHomeJsonObj = matchJsonObj.getJSONObject(TEAM_HOME);
//            JSONObject teamAwayJsonObj = matchJsonObj.getJSONObject(TEAM_AWAY);
//            //assign cards and goals json objects here
//
//            String stadiumName = stadiumJsonObj.getString(NAME);
//            String refereeName = refereeJsonObj.getString(NAME);
//
//            //home team details object holds the details on home team including the formation, substitutions, etc
//            MatchTeamDetail homeTeamDetail = new MatchTeamDetail();
//            long homeTeamId = teamHomeJsonObj.getLong(ID);
//            homeTeamDetail.setTeamId(homeTeamId);
//            homeTeamDetail.setTeamName(fixture.getHomeTeamName());
//
//            JSONArray homeTeamStartingLineUpJsonArray = teamHomeJsonObj.getJSONArray(FORMATION);
//            List<MatchTeamPlayerDetail> homeTeamStartingLineUp = new ArrayList<>();
//            for(int i=0; i<homeTeamStartingLineUpJsonArray.length(); i++){
//                MatchTeamPlayerDetail player = new MatchTeamPlayerDetail();
//                JSONObject playerDetailJsonObj = homeTeamStartingLineUpJsonArray.getJSONObject(i);
//
//                player.setId(playerDetailJsonObj.getLong(ID));
//                player.setName(playerDetailJsonObj.getString(NAME));
//                player.setPosition(playerDetailJsonObj.getString(POSITION));
//                player.setNumber(playerDetailJsonObj.getLong(NUMBER));
//
//                homeTeamStartingLineUp.add(player);
//            }
//
//            homeTeamDetail.setStartingLineUps(homeTeamStartingLineUp);
//
//            JSONArray homeTeamBenchPlayersJsonArray = teamHomeJsonObj.getJSONArray(BENCH);
//            List<MatchTeamPlayerDetail> homeTeamBenchPlayers = new ArrayList<>();
//            for(int i=0; i<homeTeamBenchPlayersJsonArray.length(); i++){
//                MatchTeamPlayerDetail player = new MatchTeamPlayerDetail();
//                JSONObject playerDetailJsonObj = homeTeamBenchPlayersJsonArray.getJSONObject(i);
//
//                player.setId(playerDetailJsonObj.getLong(ID));
//                player.setName(playerDetailJsonObj.getString(NAME));
//                player.setPosition(playerDetailJsonObj.getString(POSITION));
//                player.setNumber(playerDetailJsonObj.getLong(NUMBER));
//
//                homeTeamBenchPlayers.add(player);
//            }
//
//            homeTeamDetail.setSubstitutes(homeTeamBenchPlayers);
//
//
//
//            //away team details object holds the details on away team including the formation, substitutions, etc
//            MatchTeamDetail awayTeamDetail = new MatchTeamDetail();
//            long awayTeamId = teamAwayJsonObj.getLong(ID);
//            awayTeamDetail.setTeamId(awayTeamId);
//            awayTeamDetail.setTeamName(fixture.getAwayTeamName());
//
//            JSONArray awayTeamStartingLineUpJsonArray = teamAwayJsonObj.getJSONArray(FORMATION);
//            List<MatchTeamPlayerDetail> awayTeamStartingLineUp = new ArrayList<>();
//            for(int i=0; i<awayTeamStartingLineUpJsonArray.length(); i++){
//                MatchTeamPlayerDetail player = new MatchTeamPlayerDetail();
//                JSONObject playerDetailJsonObj = awayTeamStartingLineUpJsonArray.getJSONObject(i);
//
//                player.setId(playerDetailJsonObj.getLong(ID));
//                player.setName(playerDetailJsonObj.getString(NAME));
//                player.setPosition(playerDetailJsonObj.getString(POSITION));
//                player.setNumber(playerDetailJsonObj.getLong(NUMBER));
//
//                awayTeamStartingLineUp.add(player);
//            }
//
//            awayTeamDetail.setStartingLineUps(awayTeamStartingLineUp);
//
//            JSONArray awayTeamBenchPlayersJsonArray = teamHomeJsonObj.getJSONArray(BENCH);
//            List<MatchTeamPlayerDetail> awayTeamBenchPlayers = new ArrayList<>();
//            for(int i=0; i<awayTeamBenchPlayersJsonArray.length(); i++){
//                MatchTeamPlayerDetail player = new MatchTeamPlayerDetail();
//                JSONObject playerDetailJsonObj = awayTeamBenchPlayersJsonArray.getJSONObject(i);
//
//                player.setId(playerDetailJsonObj.getLong(ID));
//                player.setName(playerDetailJsonObj.getString(NAME));
//                player.setPosition(playerDetailJsonObj.getString(POSITION));
//                player.setNumber(playerDetailJsonObj.getLong(NUMBER));
//
//                awayTeamBenchPlayers.add(player);
//            }
//
//            awayTeamDetail.setSubstitutes(awayTeamBenchPlayers);
//
//
//
//            //setting field values for match details object
//            matchDetail.setMatchId(fixture.getMatchId());
//            matchDetail.setHomeTeamScore(fixture.getHomeTeamScore());
//            matchDetail.setAwayTeamScore(fixture.getAwayTeamScore());
//            matchDetail.setMatchStatus(fixture.getMatchStatus());
//            matchDetail.setKickoffTime(fixture.getMatchDate());
//            matchDetail.setMinute(minute);
//            matchDetail.setRefereeName(refereeName);
//            matchDetail.setStadiumName(stadiumName);
//            matchDetail.setHomeTeam(homeTeamDetail);
//            matchDetail.setAwayTeam(awayTeamDetail);
//
//            return matchDetail;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }


    private static JSONObject hitUrl(String url, RequestParams params){
        final JSONObject[] output = new JSONObject[1];
        System.out.println("here is url :"+url);
        OneFootballClient.get(url,params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("------------------------response starts here----------------------------");
                output[0] = response;
                System.out.println(response);
                System.out.println("------------------------response ends here----------------------------");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.out.println("--------------------error starts here--------------------");
                System.out.println(errorResponse.toString());
                output[0] = errorResponse;
                System.out.println("--------------------error ends here--------------------");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                System.out.println("--------------------error starts here--------------------");
                System.out.println(errorResponse.toString());
                System.out.println("--------------------error ends here--------------------");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println("--------------------error starts here--------------------");
                System.out.println(responseString);
                System.out.println("--------------------error ends here--------------------");
            }
        });
        if(output[0] == null){
            try {
                output[0] = new JSONObject("{\"name\":\"binod\"}");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return output[0];
    }


    public interface StandingsInterface {
        void onSuccess(List<ClubStanding> clubStandings);
        void onFailure();
    }


    public interface FixtureInterface {
        void onSuccess(List<Fixture> fixtures);
        void onFailure();
    }

    public interface MatchDetailInterface {
        void onSuccess(MatchDetail matchDetail);
        void onFailure();
    }

    public interface MatchDaysInterface {
        void onMatchDayFetchSuccess(List<MatchDay> matchDays);
        void onFailure();
    }


    public static void setStandingsListener(StandingsInterface t){
        standingsInterface = t;
    }

    public static void setFixtureListener(FixtureInterface t){
        fixtureInterface = t;
    }

    public static void setMatchDetailListener(MatchDetailInterface t){
        matchDetailInterface = t;
    }

    public static void setMatchDaysListenter(MatchDaysInterface t){
        matchDaysInterface = t;
    }
}
