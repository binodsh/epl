package com.binodnme.epl.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binodnme.epl.R;

/**
 * Created by binodnme on 2/16/16.
 */
public class FixtureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String[] dataSet;
    public FixtureAdapter(){

    }

    public static class FixtureViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout linearLayout;
        public TextView textView;

        public FixtureViewHolder(View itemView) {
            super(itemView);
        }
    }

    public FixtureAdapter(String[] ds){
        this.dataSet = ds;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fixture, parent, false);
        FixtureViewHolder fvh = new FixtureViewHolder(v);
        return fvh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FixtureViewHolder fvh = (FixtureViewHolder) holder;
//        fvh.textView.setText(dataSet[position]);
    }

    @Override
    public int getItemCount() {
        return dataSet.length;
    }
}
