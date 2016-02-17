package com.binodnme.epl.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binodnme.epl.R;
import com.binodnme.epl.activity.MatchDetailsActivity;

/**
 * Created by binodnme on 2/16/16.
 */
public class FixtureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int MATCH_DAY = 0;
    private static final int MATCH = 1;
    private String[] dataSet;
    public FixtureAdapter(){

    }


    public static class MatchDayTitleViewHolder extends RecyclerView.ViewHolder{

        public MatchDayTitleViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class FixtureViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout linearLayout;
        public TextView textView;

        public FixtureViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView;
        }
    }

    public FixtureAdapter(String[] ds){
        this.dataSet = ds;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        RecyclerView.ViewHolder vh ;
        switch (viewType){
            case MATCH_DAY:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fixture_match_day, parent, false);
                vh = new MatchDayTitleViewHolder(v);
                break;

            case MATCH:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fixture, parent, false);
                vh = new FixtureViewHolder(v);
                break;
            default:
                vh = null;
        }

        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        if(position % 4 == 0){
            return MATCH_DAY;
        }else {
            return MATCH;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position%4 != MATCH_DAY){
            FixtureViewHolder fixtureViewHolder = (FixtureViewHolder) holder;
            fixtureViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), MatchDetailsActivity.class);
                    Log.i("tag", "clicked");
                    v.getContext().startActivity(intent);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
//        return dataSet.length;
        return 10;
    }
}
