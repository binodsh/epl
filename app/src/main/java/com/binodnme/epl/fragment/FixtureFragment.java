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
import com.binodnme.epl.adapter.FixtureAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

/**
 * Created by binodnme on 2/16/16.
 */
public class FixtureFragment extends Fragment{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public FixtureFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_fixture, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.fixture_holder_layout);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        String[] testData = new String[]{"a","a","a","a","a"};
        mAdapter = new FixtureAdapter(testData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new StickyRecyclerHeadersDecoration((StickyRecyclerHeadersAdapter) mAdapter));

        return rootView;
    }
}
