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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binodnme.epl.R;
import com.binodnme.epl.adapter.StandingsAdapter;
import com.binodnme.epl.model.ClubStanding;
import com.binodnme.epl.model.PremierLeague;
import com.binodnme.epl.rest.onefootball.OneFootball;

import java.util.List;

/**
 * Created by binodnme on 2/16/16.
 */
public class StandingsFragment extends Fragment implements OneFootball.StandingsInterface {
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout mErrorMessage;

    public StandingsFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_standings, container, false);
        mRecycler = (RecyclerView) rootView.findViewById(R.id.standings_holder_layout);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mLayoutManager);

        OneFootball.setStandingsListener(this);

        PremierLeague.fetchCurrentStanding(getActivity());

        mErrorMessage = (LinearLayout) rootView.findViewById(R.id.error_message);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_standings);
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener(){

                    @Override
                    public void onRefresh() {
                        System.out.println("refreshing");
                        mErrorMessage.setVisibility(View.GONE);
                        PremierLeague.fetchCurrentStanding(getActivity());
                    }
                }
        );

        return rootView;

    }

    @Override
    public void onSuccess(List<ClubStanding> clubStandings) {
        mErrorMessage.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter = new StandingsAdapter(clubStandings);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onFailure() {
        mSwipeRefreshLayout.setRefreshing(false);
        mErrorMessage.setVisibility(View.VISIBLE);
    }
}
