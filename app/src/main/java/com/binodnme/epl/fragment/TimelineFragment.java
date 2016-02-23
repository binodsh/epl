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
import com.binodnme.epl.adapter.MatchInfoPagerAdapter;
import com.binodnme.epl.adapter.TimelineAdapter;
import com.binodnme.epl.constants.ApplicationConstant;
import com.binodnme.epl.model.MatchDetail;

/**
 * Created by binodnme on 2/17/16.
 */
public class TimelineFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        MatchDetail matchDetail = (MatchDetail) bundle.getSerializable(MatchInfoPagerAdapter.MATCH_DETAIL);

        ViewGroup rootView;
        if(ApplicationConstant.PM.equalsIgnoreCase(matchDetail.getMatchStatus())){
            rootView = (ViewGroup) inflater.inflate(R.layout.coming_soon, container, false);
            return rootView;
        }


//        rootView = (ViewGroup) inflater.inflate(R.layout.timeline, container, false);
        rootView = (ViewGroup) inflater.inflate(R.layout.coming_soon, container, false);

//        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.id_timeline_view);
//        mLayoutManager = new LinearLayoutManager(getActivity());
//        mRecyclerView.setLayoutManager(mLayoutManager);
//
//        mAdapter = new TimelineAdapter();
//        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }
}
