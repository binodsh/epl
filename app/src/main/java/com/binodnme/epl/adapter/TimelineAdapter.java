package com.binodnme.epl.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.binodnme.epl.R;
import com.binodnme.epl.model.CardEvent;
import com.binodnme.epl.model.GoalEvent;
import com.binodnme.epl.model.MatchDetail;
import com.binodnme.epl.model.MatchEvent;
import com.binodnme.epl.model.SubstitutionEvent;

/**
 * Created by binodnme on 2/18/16.
 */
public class TimelineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int GOAL_EVENT = 1;
    private static final int CARD_EVENT = 2;
    private static final int SUBSTITUTION_EVENT = 3;

    private static final int AWAY_TEAM_EVENT = 4;
    private static final int HOME_TEAM_EVENT = 5;
    private MatchDetail matchDetail;

    public TimelineAdapter(MatchDetail md){
        matchDetail = md;
    }

    public static class TimelineViewHolder extends RecyclerView.ViewHolder{
        TextView playerName;
        ImageView imageView;
        TextView eventExtra;
        TextView eventTime;
        public TimelineViewHolder(View itemView) {
            super(itemView);
            playerName = (TextView) itemView.findViewById(R.id.player_name);
            eventExtra = (TextView) itemView.findViewById(R.id.event_extra);
            eventTime = (TextView) itemView.findViewById(R.id.event_time);
            imageView = (ImageView) itemView.findViewById(R.id.event_icon);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_timeline, parent, false);
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
        TimelineViewHolder timelineViewHolder = (TimelineViewHolder) holder;

        MatchEvent matchEvent = matchDetail.getMatchEvents().get(position);

        if(matchEvent instanceof GoalEvent){
            GoalEvent se = (GoalEvent) matchEvent;
            String playerName  = se.getPlayer().getName();
            timelineViewHolder.eventTime.setText(se.getEventTime()+"'");
            timelineViewHolder.playerName.setText(playerName);
            timelineViewHolder.eventExtra.setVisibility(View.VISIBLE);
            timelineViewHolder.eventExtra.setText("( "+se.getScore()+" )");

            if("own".equalsIgnoreCase(se.getType())){
                timelineViewHolder.imageView.setImageResource(R.drawable.own_goal);
            }else if("penalty".equalsIgnoreCase(se.getType())){
                playerName  = se.getPlayer().getName()+" (penalty)";
                timelineViewHolder.playerName.setText(playerName);
                timelineViewHolder.imageView.setImageResource(R.drawable.ball);
            }
            else {
                timelineViewHolder.imageView.setImageResource(R.drawable.ball);
            }

        }else if(matchEvent instanceof SubstitutionEvent){
            SubstitutionEvent se = (SubstitutionEvent) matchEvent;
            timelineViewHolder.eventTime.setText(se.getEventTime()+"'");
            String playerIn  = se.getPlayerIn().getName();
            timelineViewHolder.playerName.setText(playerIn);

            timelineViewHolder.eventExtra.setVisibility(View.VISIBLE);
            timelineViewHolder.eventExtra.setText(se.getPlayerOut().getName());
            timelineViewHolder.imageView.setImageResource(R.drawable.substitution);

        }else if(matchEvent instanceof CardEvent){
            CardEvent se = (CardEvent) matchEvent;

            timelineViewHolder.eventExtra.setVisibility(View.GONE);
            timelineViewHolder.eventTime.setText(se.getEventTime()+"'");

            String playerName  = se.getPlayer().getName();
            timelineViewHolder.playerName.setText(playerName);

            if("yellow".equalsIgnoreCase(se.getType())){
                timelineViewHolder.imageView.setImageResource(R.drawable.yellow_card);
            }else if("secondyellow".equalsIgnoreCase(se.getType())){
                timelineViewHolder.imageView.setImageResource(R.drawable.double_yellow);
            }
            else if("StraightRed".equalsIgnoreCase(se.getType())){
                timelineViewHolder.imageView.setImageResource(R.drawable.red_card);
            }

        }


    }

    @Override
    public int getItemCount() {
        return matchDetail.getMatchEvents().size();
    }

    @Override
    public int getItemViewType(int position) {
        MatchEvent matchEvent = matchDetail.getMatchEvents().get(position);
        return matchEvent.isHomeEvent() ? HOME_TEAM_EVENT: AWAY_TEAM_EVENT;
    }
}
