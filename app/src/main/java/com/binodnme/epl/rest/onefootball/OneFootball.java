package com.binodnme.epl.rest.onefootball;

import com.binodnme.epl.constants.ApplicationConstant;
import com.binodnme.epl.model.CardEvent;
import com.binodnme.epl.model.ClubStanding;
import com.binodnme.epl.model.Fixture;
import com.binodnme.epl.model.GoalEvent;
import com.binodnme.epl.model.MatchDay;
import com.binodnme.epl.model.MatchDetail;
import com.binodnme.epl.model.MatchEvent;
import com.binodnme.epl.model.MatchTeamDetail;
import com.binodnme.epl.model.MatchTeamPlayerDetail;
import com.binodnme.epl.model.PremierLeague;
import com.binodnme.epl.model.SubstitutionEvent;
import com.binodnme.epl.utils.DateUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by binodnme on 2/20/16.
 */
public class OneFootball {
    private static final String COMPETITION_ID = "9";
    private static final String SEASON_ID = "1231";

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
    private static final String SUBSTITUTIONS = "substitutions";
    private static final String PLAYER_IN = "playerIn";
    private static final String PLAYER_OUT = "playerOut";
    private static final String PLAYER = "player";
    private static final String EVENT_ID = "eventId";


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
    private static final String CARDS = "cards";
    private static final String GOALS = "goals";
    private static final String TYPE = "type";
    private static final String SCORE = "score";


    private static final String BASE_PATH = "https://feedmonster.onefootball.com/feeds/il/en/competitions/";


    public static StandingsInterface standingsInterface;
    public static FixtureInterface fixtureInterface;
    public static MatchDetailInterface matchDetailInterface;
    public static MatchDaysInterface matchDaysInterface;

    private static final String MATCHDAYS = "matchdays";
    private static final String IS_CURRENT_MATCHDAY = "isCurrentMatchday";


    public static void fetchMatchDays(){
        String url = BASE_PATH+COMPETITION_ID+"/"+SEASON_ID+"/"+"matchdaysOverview.json";

        OneFootballClient.get(url, new RequestParams(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    List<MatchDay> matchDays = new ArrayList<>();
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

    public static void fetchStandings(){

        String url = "https://feedmonster.onefootball.com/feeds/il/en/competitions/9/1231/standings.json";

        OneFootballClient.get(url, new RequestParams(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray groups = response.getJSONArray(GROUPS);
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

    public static void fetchFixtures(long matchDayId){
        String url = BASE_PATH+COMPETITION_ID+"/"+SEASON_ID+"/matchdays/"+matchDayId+".json";

        OneFootballClient.get(url, new RequestParams(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    JSONArray kickOffs = response.getJSONArray(KICK_OFFS);

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
    }

    public static void getMatchDetail(final Fixture fixture){
        String url = "https://feedmonster.onefootball.com/feeds/il/en/competitions/9/1231/matches/"+fixture.getMatchId()+".json";

        OneFootballClient.get(url, new RequestParams(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try{
                    //declare match detail object
                    MatchDetail matchDetail = new MatchDetail();

                    //build match detail json object from json string

                    JSONObject matchJsonObj = response.getJSONObject(MATCH);
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

                    //build starting line up list of home team
                    JSONArray homeTeamStartingLineUpJsonArray = teamHomeJsonObj.getJSONArray(FORMATION);
                    List<MatchTeamPlayerDetail> homeTeamStartingLineUp = getPlayerList(homeTeamStartingLineUpJsonArray);
                    homeTeamDetail.setStartingLineUps(homeTeamStartingLineUp);

                    //build substitute player list of home team
                    JSONArray homeTeamBenchPlayersJsonArray = teamHomeJsonObj.getJSONArray(BENCH);
                    List<MatchTeamPlayerDetail> homeTeamBenchPlayers = getPlayerList(homeTeamBenchPlayersJsonArray);
                    homeTeamDetail.setSubstitutes(homeTeamBenchPlayers);


                    //away team details object holds the details on away team including the formation, substitutions, etc
                    MatchTeamDetail awayTeamDetail = new MatchTeamDetail();
                    long awayTeamId = teamAwayJsonObj.getLong(ID);
                    awayTeamDetail.setTeamId(awayTeamId);
                    awayTeamDetail.setTeamName(fixture.getAwayTeamName());


                    //build starting line up list of away team
                    JSONArray awayTeamStartingLineUpJsonArray = teamAwayJsonObj.getJSONArray(FORMATION);
                    List<MatchTeamPlayerDetail> awayTeamStartingLineUp = getPlayerList(awayTeamStartingLineUpJsonArray);
                    awayTeamDetail.setStartingLineUps(awayTeamStartingLineUp);

                    //build substitute player list of away team
                    JSONArray awayTeamBenchPlayersJsonArray = teamAwayJsonObj.getJSONArray(BENCH);
                    List<MatchTeamPlayerDetail> awayTeamBenchPlayers = getPlayerList(awayTeamBenchPlayersJsonArray);
                    awayTeamDetail.setSubstitutes(awayTeamBenchPlayers);


                    //event related task here
                    //build substitution list for home team
                    JSONArray homeTeamSubstitutionJsonArray = teamHomeJsonObj.getJSONArray(SUBSTITUTIONS);
                    List<MatchEvent> matchEvents = new ArrayList<>();
                    for(int i=0; i<homeTeamSubstitutionJsonArray.length(); i++){
                        SubstitutionEvent se = new SubstitutionEvent();
                        long eventTime = homeTeamSubstitutionJsonArray.getJSONObject(i).getLong(MINUTE);
                        JSONObject playerInJsonObj = homeTeamSubstitutionJsonArray.getJSONObject(i).getJSONObject(PLAYER_IN);
                        JSONObject playerOutJsonObj = homeTeamSubstitutionJsonArray.getJSONObject(i).getJSONObject(PLAYER_OUT);
                        MatchTeamPlayerDetail playerIn = getPlayer(playerInJsonObj);
                        MatchTeamPlayerDetail playerOut = getPlayer(playerOutJsonObj);

                        se.setEventTime(eventTime);
                        se.setPlayerIn(playerIn);
                        se.setPlayerOut(playerOut);
                        se.setHomeEvent(true);
                        matchEvents.add(se);
                    }

                    //build substitution list for away team
                    JSONArray awayTeamSubstitutionJsonArray = teamAwayJsonObj.getJSONArray(SUBSTITUTIONS);
                    for(int i=0; i<awayTeamSubstitutionJsonArray.length(); i++){
                        SubstitutionEvent se = new SubstitutionEvent();
                        long eventTime = awayTeamSubstitutionJsonArray.getJSONObject(i).getLong(MINUTE);
                        JSONObject playerInJsonObj = awayTeamSubstitutionJsonArray.getJSONObject(i).getJSONObject(PLAYER_IN);
                        JSONObject playerOutJsonObj = awayTeamSubstitutionJsonArray.getJSONObject(i).getJSONObject(PLAYER_OUT);
                        MatchTeamPlayerDetail playerIn = getPlayer(playerInJsonObj);
                        MatchTeamPlayerDetail playerOut = getPlayer(playerOutJsonObj);

                        se.setEventTime(eventTime);
                        se.setPlayerIn(playerIn);
                        se.setPlayerOut(playerOut);
                        se.setHomeEvent(false);
                        matchEvents.add(se);
                    }

                    JSONArray cardEventJsonArray = matchJsonObj.getJSONArray(CARDS);
                    for(int i=0; i<cardEventJsonArray.length(); i++){
                        CardEvent cardEvent = new CardEvent();
                        long eventTime = cardEventJsonArray.getJSONObject(i).getLong(MINUTE);
                        String type = cardEventJsonArray.getJSONObject(i).getString(TYPE);
                        JSONObject playerJsonObject = cardEventJsonArray.getJSONObject(i).getJSONObject(PLAYER);
                        MatchTeamPlayerDetail player = getPlayer(playerJsonObject);


                        cardEvent.setEventTime(eventTime);
                        cardEvent.setType(type);
                        cardEvent.setPlayer(player);

                        long internalTeamId = playerJsonObject.getLong("teamId");

                        if(internalTeamId == homeTeamDetail.getTeamId()){
                            cardEvent.setHomeEvent(true);
                        }else {
                            cardEvent.setHomeEvent(false);
                        }

                        matchEvents.add(cardEvent);
                    }


                    JSONArray goalEventJsonArray = matchJsonObj.getJSONArray(GOALS);
                    for(int i=0; i<goalEventJsonArray.length(); i++){
                        GoalEvent goalEvent = new GoalEvent();
                        long eventTime = goalEventJsonArray.getJSONObject(i).getLong(MINUTE);
                        String type = goalEventJsonArray.getJSONObject(i).getString(TYPE);
                        String score = goalEventJsonArray.getJSONObject(i).getString(SCORE);
                        JSONObject playerJsonObject = goalEventJsonArray.getJSONObject(i).getJSONObject(PLAYER);
                        MatchTeamPlayerDetail player = getPlayer(playerJsonObject);


                        goalEvent.setEventTime(eventTime);
                        goalEvent.setType(type);
                        goalEvent.setPlayer(player);
                        goalEvent.setScore(score);

                        long teamId = goalEventJsonArray.getJSONObject(i).getLong("teamId");

                        if(teamId == homeTeamDetail.getTeamId()){
                            goalEvent.setHomeEvent(true);
                        }else {
                            goalEvent.setHomeEvent(false);
                        }

                        matchEvents.add(goalEvent);
                    }

                    matchDetail.setMatchEvents(matchEvents);
                    Collections.sort(matchEvents);

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

    public static void setMatchDaysListener(MatchDaysInterface t){
        matchDaysInterface = t;
    }


    /**
     * This method takes JSONArray as input, builds the players list and returns the list
     * @param playerDetailJsonArray
     * @return List<MatchTeamPlayerDetail>
     */
    private static List<MatchTeamPlayerDetail> getPlayerList(JSONArray playerDetailJsonArray){
        List<MatchTeamPlayerDetail> playerList = new ArrayList<>();

        try{
            for(int i=0; i<playerDetailJsonArray.length(); i++){
                JSONObject playerDetailJsonObj = playerDetailJsonArray.getJSONObject(i);
                playerList.add(getPlayer(playerDetailJsonObj));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return playerList;
    }

    /**
     * This method takes JSONObject as input, builds the MatchTeamPlayerDetail object and returns the object
     * @param playerDetailJsonObj
     * @return MatchTeamPlayerDetail
     */
    private static MatchTeamPlayerDetail getPlayer(JSONObject playerDetailJsonObj){
        MatchTeamPlayerDetail player = new MatchTeamPlayerDetail();
        try {
            player.setId(playerDetailJsonObj.getLong(ID));
            player.setName(playerDetailJsonObj.getString(NAME));
            player.setPosition(playerDetailJsonObj.getString(POSITION));
            player.setNumber(playerDetailJsonObj.getLong(NUMBER));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return player;
    }
}
