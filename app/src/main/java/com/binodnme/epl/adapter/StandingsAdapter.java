package com.binodnme.epl.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binodnme.epl.R;

/**
 * Created by binodnme on 2/17/16.
 */
public class StandingsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String[] dataset;
    public StandingsAdapter(){}

    public StandingsAdapter(String[] ds){
        this.dataset = ds;
    }


    public static class StandingsViewHolder extends RecyclerView.ViewHolder{

        public StandingsViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.standings, parent, false);
        StandingsViewHolder svh = new StandingsViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
//        return dataset.length;
        return 20;
    }
}
