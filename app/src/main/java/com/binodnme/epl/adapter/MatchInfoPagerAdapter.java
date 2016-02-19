package com.binodnme.epl.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.binodnme.epl.fragment.LineupFragment;
import com.binodnme.epl.fragment.TimelineFragment;

/**
 * Created by binodnme on 2/17/16.
 */
public class MatchInfoPagerAdapter extends FragmentStatePagerAdapter {
    private static final int TIMELINE_POSITION = 0;
    private static final int LINEUPS_POSITION = 1;

    private String[] tabTitles = new String[]{"timeline", "lineups"};

    public MatchInfoPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case TIMELINE_POSITION:
                return new TimelineFragment();
            case LINEUPS_POSITION:
                return new LineupFragment();
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
