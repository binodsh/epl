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
import com.binodnme.epl.adapter.TimelineAdapter;

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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.timeline, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.id_timeline_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new TimelineAdapter();
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }
}
