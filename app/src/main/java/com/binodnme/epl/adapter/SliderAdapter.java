package com.binodnme.epl.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.binodnme.epl.fragment.FixtureFragment;
import com.binodnme.epl.fragment.StandingsFragment;

/**
 * Created by binodnme on 2/16/16.
 */
public class SliderAdapter extends FragmentStatePagerAdapter {
    private static final int POSITION_MATCH = 0;
    private static final int POSITION_TABLE = 1;
    private String[] tabTitles = new String[]{"Matches", "Table"};

    public SliderAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case POSITION_MATCH:
                return new FixtureFragment();
            case POSITION_TABLE:
                return new StandingsFragment();
            default:
                return new FixtureFragment();
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
