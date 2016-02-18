package com.binodnme.epl.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.binodnme.epl.R;

/**
 * Created by binodnme on 2/18/16.
 */
public class TimelineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HOME_TEAM_EVENT = 1;
    private static final int AWAY_TEAM_EVENT = 2;

    public static class TimelineViewHolder extends RecyclerView.ViewHolder{

        public TimelineViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_timeline, parent, false);;
        RecyclerView.ViewHolder vh;
        RelativeLayout relativeLayout = (RelativeLayout) v;
        if(viewType == HOME_TEAM_EVENT){
            relativeLayout.addView(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_team_home, (ViewGroup) v, false));
        }else{
            relativeLayout.addView(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_team_away, (ViewGroup) v, false));
        }

        vh = new TimelineViewHolder(relativeLayout);

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public int getItemViewType(int position) {
        return position%2 == 0 ? HOME_TEAM_EVENT : AWAY_TEAM_EVENT;
    }
}
