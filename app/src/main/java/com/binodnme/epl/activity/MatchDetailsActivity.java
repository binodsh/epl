package com.binodnme.epl.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binodnme.epl.R;
import com.binodnme.epl.adapter.MatchInfoPagerAdapter;
import com.binodnme.epl.constants.ApplicationConstant;
import com.binodnme.epl.model.Fixture;
import com.binodnme.epl.model.MatchDetail;
import com.binodnme.epl.model.MatchTeamPlayerDetail;
import com.binodnme.epl.model.PremierLeague;
import com.binodnme.epl.rest.onefootball.OneFootball;
import com.binodnme.epl.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class MatchDetailsActivity extends AppCompatActivity implements OneFootball.MatchDetailInterface{
    private ViewPager mPager;
    private PagerAdapter mAdapter;

    TextView teamHomeName;
    TextView teamAwayName;
    ImageView teamHomeLogo;
    ImageView teamAwayLogo;
    TextView matchScore;
    TextView matchDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Bundle args = getIntent().getExtras();
        Fixture fixture = (Fixture) args.getSerializable("fixture");

        OneFootball.setMatchDetailListener(this);
        PremierLeague.getMatchDetail(this, fixture);

        teamHomeName = (TextView) findViewById(R.id.team_home_name);
        teamAwayName = (TextView) findViewById(R.id.team_away_name);
        teamHomeLogo = (ImageView) findViewById(R.id.team_home_logo);
        teamAwayLogo = (ImageView) findViewById(R.id.team_away_logo);
        matchScore = (TextView) findViewById(R.id.match_score);
        matchDate = (TextView) findViewById(R.id.match_date);


    }

    public int getResourceId(Context context, String name){
        name = name.toLowerCase().replace(' ','_');
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    @Override
    public void onSuccess(MatchDetail matchDetail) {
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        String dateFormat = "EEE, d MMM, HH:mm";
        String dateString = DateUtils.getDateString(matchDetail.getKickoffTime(), dateFormat);

        matchDate.setText(dateString);

        teamHomeName.setText(matchDetail.getHomeTeam().getTeamName());
        teamAwayName.setText(matchDetail.getAwayTeam().getTeamName());

        Context context = teamHomeLogo.getContext();
        int id = getResourceId(context, matchDetail.getHomeTeam().getTeamName());
        teamHomeLogo.setImageResource(id == 0 ? R.mipmap.ic_launcher : id);

        Context context1 = teamAwayLogo.getContext();
        int id1 = getResourceId(context1, matchDetail.getAwayTeam().getTeamName());
        teamAwayLogo.setImageResource(id1 == 0 ? R.mipmap.ic_launcher : id1);

        if(ApplicationConstant.PM.equalsIgnoreCase(matchDetail.getMatchStatus()) || ApplicationConstant.PP.equalsIgnoreCase(matchDetail.getMatchStatus())){
            matchScore.setText("vs");
        }else{

            matchScore.setText(matchDetail.getHomeTeamScore()+" - "+matchDetail.getAwayTeamScore());
        }

        List<List<MatchTeamPlayerDetail>> bothTeamFormation = new ArrayList<>();
        bothTeamFormation.add(matchDetail.getHomeTeam().getStartingLineUps());
        bothTeamFormation.add(matchDetail.getAwayTeam().getStartingLineUps());

        mPager = (ViewPager) findViewById(R.id.match_info_pager);
        mAdapter = new MatchInfoPagerAdapter(getSupportFragmentManager(), matchDetail);

        mPager.setAdapter(mAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.match_info_tabs);
        tabLayout.setupWithViewPager(mPager);

    }

    @Override
    public void onFailure() {

    }
}
