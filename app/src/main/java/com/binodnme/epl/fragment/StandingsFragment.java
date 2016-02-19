package com.binodnme.epl.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binodnme.epl.R;
import com.binodnme.epl.adapter.StandingsAdapter;
import com.binodnme.epl.model.ClubStanding;
import com.binodnme.epl.model.PremierLeague;

import java.util.List;

/**
 * Created by binodnme on 2/16/16.
 */
public class StandingsFragment extends Fragment {
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public StandingsFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_standings, container, false);
        mRecycler = (RecyclerView) rootView.findViewById(R.id.standings_holder_layout);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mLayoutManager);

        List<ClubStanding> clubStandings = PremierLeague.getCurrentStanding(getActivity());

        mAdapter = new StandingsAdapter(clubStandings);
        mRecycler.setAdapter(mAdapter);
        return rootView;

    }
}
