package com.binodnme.epl.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

/**
 * Created by binodnme on 2/18/16.
 */
public class FixtureAdapterWithStickyHeader implements StickyRecyclerHeadersAdapter {
    @Override
    public long getHeaderId(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
