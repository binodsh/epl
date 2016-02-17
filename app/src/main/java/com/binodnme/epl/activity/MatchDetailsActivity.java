package com.binodnme.epl.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.binodnme.epl.R;
import com.binodnme.epl.adapter.MatchInfoPagerAdapter;

public class MatchDetailsActivity extends AppCompatActivity {
    private ViewPager mPager;
    private PagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPager = (ViewPager) findViewById(R.id.match_info_pager);
        mAdapter = new MatchInfoPagerAdapter(getSupportFragmentManager());

        mPager.setAdapter(mAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.match_info_tabs);
        tabLayout.setupWithViewPager(mPager);

    }

}
