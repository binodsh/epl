package com.binodnme.epl.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.binodnme.epl.fragment.TimelineFragment;

/**
 * Created by binodnme on 2/17/16.
 */
public class MatchInfoPagerAdapter extends FragmentStatePagerAdapter {
    private String[] tabTitles = new String[]{"timeline", "lineups"};

    public MatchInfoPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new TimelineFragment();
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
