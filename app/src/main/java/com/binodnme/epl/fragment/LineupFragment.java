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
import com.binodnme.epl.adapter.LineUpAdapter;
import com.binodnme.epl.adapter.MatchInfoPagerAdapter;
import com.binodnme.epl.constants.ApplicationConstant;
import com.binodnme.epl.model.MatchDetail;

/**
 * Created by binodnme on 2/18/16.
 */
public class LineupFragment extends Fragment {
    RecyclerView mRecycler;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v;

        Bundle bundle = getArguments();
        MatchDetail matchDetail = (MatchDetail) bundle.getSerializable(MatchInfoPagerAdapter.MATCH_DETAIL);

        if(ApplicationConstant.PM.equalsIgnoreCase(matchDetail.getMatchStatus())){
            v = inflater.inflate(R.layout.coming_soon, container, false);
            return v;
        }

        v = inflater.inflate(R.layout.lineups, container, false);
        mRecycler = (RecyclerView) v.findViewById(R.id.id_lineups_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mLayoutManager);

        mAdapter = new LineUpAdapter(matchDetail);
        mRecycler.setAdapter(mAdapter);
        return v;
    }
}
