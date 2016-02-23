package com.binodnme.epl.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.binodnme.epl.R;
import com.binodnme.epl.adapter.FixtureAdapter;
import com.binodnme.epl.adapter.MatchDaySpinnerAdapter;
import com.binodnme.epl.model.Fixture;
import com.binodnme.epl.model.MatchDay;
import com.binodnme.epl.model.PremierLeague;
import com.binodnme.epl.rest.onefootball.OneFootball;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by binodnme on 2/16/16.
 */
public class FixtureFragment extends Fragment implements OneFootball.FixtureInterface,OneFootball.MatchDaysInterface, AdapterView.OnItemSelectedListener{
    private ViewGroup rootView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Spinner mMatchDaySpinner;
    private MatchDaySpinnerAdapter mMatchDaySpinnerAdapter;

    public FixtureFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_fixture, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.fixture_holder_layout);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mMatchDaySpinner = (Spinner) rootView.findViewById(R.id.match_day_spinner);
        mMatchDaySpinner.setOnItemSelectedListener(this);

        OneFootball.setFixtureListener(this);
        OneFootball.setMatchDaysListenter(this);

        PremierLeague.fetchMatchDays();
        rootView.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

        return rootView;
    }

    @Override
    public void onSuccess(List<Fixture> fixtures) {
        mAdapter = new FixtureAdapter(fixtures);
        mRecyclerView.setAdapter(mAdapter);
        rootView.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }


    @Override
    public void onMatchDayFetchSuccess(List<MatchDay> matchDays) {
        mMatchDaySpinnerAdapter = new MatchDaySpinnerAdapter(matchDays);

        mMatchDaySpinner.post(new Runnable() {
            @Override
            public void run() {
                mMatchDaySpinner.setSelection(10);
            }
        });

        int matchDayIndex = 0;
        for (int i=0; i<matchDays.size(); i++){
            if (matchDays.get(i).getCurrentMatchDay()){
                matchDayIndex = i;
                break;
            }
        }

        final int finalMatchDayIndex = matchDayIndex;
        mMatchDaySpinner.post(new Runnable() {
            @Override
            public void run() {
                mMatchDaySpinner.setSelection(finalMatchDayIndex);
            }
        });

        mMatchDaySpinner.setAdapter(mMatchDaySpinnerAdapter);
        PremierLeague.getFixtures(getActivity(), matchDays.get(matchDayIndex).getId());
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        rootView.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        PremierLeague.getFixtures(getActivity(), id);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
