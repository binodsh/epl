package com.binodnme.epl.model;

import android.content.Context;


import com.binodnme.epl.rest.onefootball.OneFootball;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by binodnme on 2/19/16.
 */
public class PremierLeague {
    private long id;


    public static List<ClubStanding> getCurrentStanding(Context context){
        return OneFootball.getStandings(context);
    }

    public static List<Fixture> getFixtures(Context context){
        return OneFootball.getFixtures(context);
    }

//    public static List<ClubStanding> getCurrentStanding(Context context){
//        String json;
//        try {
//            InputStream is = context.getAssets().open("standings_football_api.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//
//            JSONObject jsonObject = new JSONObject(json);
//            JSONArray teams = jsonObject.getJSONArray("teams");
//
//            List<ClubStanding> clubStandings  = new ArrayList<>();
//            for(int i=0; i<teams.length(); i++){
//                JSONObject obj   = (JSONObject) teams.get(i);
//                ClubStanding cs = new ClubStanding();
//                cs.setName(obj.getString("stand_team_name"));
//                cs.setPosition(obj.getInt("stand_position"));
//                cs.setMatchPlayed(obj.getInt("stand_overall_gp"));
//                cs.setGoalsFor(obj.getInt("stand_overall_gs"));
//                cs.setGoalsAgainst(obj.getInt("stand_overall_ga"));
//                cs.setPoints(obj.getInt("stand_points"));
//                clubStandings.add(cs);
//            }
//            return clubStandings;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return new ArrayList<>();
//    }

//    public static List<Fixture> getFixtures(Context context){
//
//        String json;
//        try {
//            InputStream is = context.getAssets().open("fixtures.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//
//            JSONObject jsonObject = new JSONObject(json);
//            JSONArray matches = jsonObject.getJSONArray("matches");
//
//            List<Fixture> fixtures = new ArrayList<>();
//            for(int i=0; i<matches.length(); i++){
//                JSONObject obj   = (JSONObject) matches.get(i);
//                Fixture fixture = new Fixture();
//
//                fixture.setHomeTeamName(obj.getString("match_localteam_name"));
//                fixture.setAwayTeamName(obj.getString("match_visitorteam_name"));
//                fixture.setHomeTeamScore(obj.getInt("match_localteam_score"));
//                fixture.setAwayTeamScore(obj.getInt("match_visitorteam_score"));
//                fixture.setMatchStatus(obj.getString("match_status"));
//                fixture.setMatchDate(obj.getString("match_date"));
//
//                fixtures.add(fixture);
//            }
//            return fixtures;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return new ArrayList<>();
//    }
}
