package com.binodnme.epl.rest.onefootball;

import android.content.Context;
import android.util.Log;

import com.binodnme.epl.constants.ApplicationConstant;
import com.binodnme.epl.model.ClubStanding;
import com.binodnme.epl.model.Fixture;
import com.binodnme.epl.utils.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by binodnme on 2/20/16.
 */
public class OneFootball {
    private static final String COMPETITION_ID = "9";
    private static final String SEASON_ID = "1231";

    private static final String STANDINGS_JSON = "standings.json";
    private static final String MATCH_DAY_JSON = "matchday.json";

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
    private static final String MATCH_STATE = "period";


    private static final String FULLTIME = "fulltime";
    private static final String HALFTIME = "halftime";
    private static final String FIRSTHALF = "firsthalf";
    private static final String SECONDHALF = "secondhalf";
    private static final String PREMATCH = "prematch";



    private static final String TEST_JSON = "liga-bbva-test.json";



    public static List<ClubStanding> getStandings(Context context){
        String json;
        try {
            InputStream is = context.getAssets().open(STANDINGS_JSON);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            JSONObject jsonObject = new JSONObject(json);

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

            return clubStandings;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }


    public static List<Fixture> getFixtures(Context context){

        String json;
        try {
            InputStream is = context.getAssets().open(TEST_JSON);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            JSONObject jsonObject = new JSONObject(json);
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

                        switch (match.getString(MATCH_STATE).toLowerCase()){
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
                        }

                        fixtures.add(fixture);
                    }
                }

            }

            return fixtures;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
