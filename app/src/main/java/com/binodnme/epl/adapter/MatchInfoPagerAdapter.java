package com.binodnme.epl.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.binodnme.epl.fragment.LineupFragment;
import com.binodnme.epl.fragment.TimelineFragment;
import com.binodnme.epl.model.MatchDetail;
import com.binodnme.epl.model.MatchTeamPlayerDetail;

import java.util.List;

/**
 * Created by binodnme on 2/17/16.
 */
public class MatchInfoPagerAdapter extends FragmentStatePagerAdapter {
    private static final int TIMELINE_POSITION = 0;
    private static final int LINEUPS_POSITION = 1;
    public static final String STARTING_LINEUP = "lineup";
    public static final String MATCH_DETAIL = "matchDetail";


//    List<List<MatchTeamPlayerDetail>> bothTeamStartingLineUps;
    MatchDetail matchDetail;

    private String[] tabTitles = new String[]{"timeline", "lineups"};

    public MatchInfoPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MatchInfoPagerAdapter(FragmentManager fm, MatchDetail md) {
        super(fm);
        this.matchDetail = md;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putSerializable(MATCH_DETAIL, this.matchDetail);

        switch (position){
            case TIMELINE_POSITION:
                TimelineFragment timelineFragment = new TimelineFragment();
                timelineFragment.setArguments(args);
                return timelineFragment;
            case LINEUPS_POSITION:
                LineupFragment lineupFragment = new LineupFragment();
                lineupFragment.setArguments(args);
                return lineupFragment;
            default:
                return new TimelineFragment();
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
