package com.binodnme.epl.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.binodnme.epl.R;
import com.binodnme.epl.adapter.FixtureAdapter;
import com.binodnme.epl.adapter.MatchDaySpinnerAdapter;
import com.binodnme.epl.model.Fixture;
import com.binodnme.epl.model.MatchDay;
import com.binodnme.epl.model.PremierLeague;
import com.binodnme.epl.rest.onefootball.OneFootball;

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

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout mErrorMessage;

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
        OneFootball.setMatchDaysListener(this);

        PremierLeague.fetchMatchDays();

        rootView.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

        mErrorMessage = (LinearLayout) rootView.findViewById(R.id.error_message);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_fixture);

        mAdapter = new FixtureAdapter(new ArrayList<Fixture>());
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener(){
                    @Override
                    public void onRefresh() {
                        PremierLeague.fetchMatchDays();
                    }
                }
        );

        return rootView;
    }

    @Override
    public void onSuccess(List<Fixture> fixtures) {
        ((FixtureAdapter) mAdapter).setDataSet(fixtures);
        rootView.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }


    @Override
    public void onMatchDayFetchSuccess(List<MatchDay> matchDays) {
        mErrorMessage.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);

        mMatchDaySpinnerAdapter = new MatchDaySpinnerAdapter(matchDays);

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
        PremierLeague.fetchFixtures(getActivity(), matchDays.get(matchDayIndex).getId());
    }

    @Override
    public void onMatchDayFetchFailure() {
        mSwipeRefreshLayout.setRefreshing(false);
        mErrorMessage.setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

    @Override
    public void onFailure() {
        mSwipeRefreshLayout.setRefreshing(false);
        mErrorMessage.setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        rootView.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        PremierLeague.fetchFixtures(getActivity(), id);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
