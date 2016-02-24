package com.binodnme.epl.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.binodnme.epl.R;
import com.binodnme.epl.model.MatchDetail;

/**
 * Created by binodnme on 2/19/16.
 */
public class LineUpAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private MatchDetail matchDetail;
    private final int VIEW_TYPE_SUB_HEADER = 1;
    private final int VIEW_TYPE_DATA = 2;
    private final int POSITION_SHIFT_FIRST = -1;
    private final int POSITION_SHIFT_SECOND = -13;

    public LineUpAdapter() {
    }

    public LineUpAdapter(MatchDetail ds) {
        this.matchDetail = ds;
    }

    public static class LineUpsViewHolder extends RecyclerView.ViewHolder {
        TextView homeTeamPlayer;
        TextView awayTeamPlayer;

        public LineUpsViewHolder(View itemView) {
            super(itemView);
            homeTeamPlayer = (TextView) itemView.findViewById(R.id.player_home);
            awayTeamPlayer = (TextView) itemView.findViewById(R.id.player_away);
        }
    }

    public static class SubHeaderViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public SubHeaderViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.sub_header);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_DATA) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_lineup, parent, false);
            return new LineUpsViewHolder(v);
        }else{
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lineup_sub_header, parent, false);
            return new SubHeaderViewHolder(v);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == matchDetail.getHomeTeam().getStartingLineUps().size()+1 || position ==0){
            return VIEW_TYPE_SUB_HEADER;
        }else {
            return VIEW_TYPE_DATA;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(position == matchDetail.getHomeTeam().getStartingLineUps().size()+1 || position == 0){
            SubHeaderViewHolder subHeaderViewHolder = (SubHeaderViewHolder) holder;
            if(position ==0){
                subHeaderViewHolder.textView.setText("lineup");
            }else {
                subHeaderViewHolder.textView.setText("Substitutes");
            }

        }else if(position < matchDetail.getHomeTeam().getStartingLineUps().size()+1){
            LineUpsViewHolder lineUpsViewHolder = (LineUpsViewHolder) holder;
            String homePlayerName = matchDetail.getHomeTeam().getStartingLineUps().get(position+POSITION_SHIFT_FIRST).getName();
            String homePlayerNumber = String.valueOf(matchDetail.getHomeTeam().getStartingLineUps().get(position+POSITION_SHIFT_FIRST).getNumber());

            String awayPlayerName = matchDetail.getAwayTeam().getStartingLineUps().get(position+POSITION_SHIFT_FIRST).getName();
            String awayPlayerNumber = String.valueOf(matchDetail.getAwayTeam().getStartingLineUps().get(position+POSITION_SHIFT_FIRST).getNumber());

            lineUpsViewHolder.homeTeamPlayer.setText(String.format("%s %s", homePlayerNumber, homePlayerName));
            lineUpsViewHolder.awayTeamPlayer.setText(String.format("%s %s", awayPlayerName, awayPlayerNumber));

        }else if (position > matchDetail.getHomeTeam().getStartingLineUps().size()+1){
            LineUpsViewHolder lineUpsViewHolder = (LineUpsViewHolder) holder;
            String homePlayerName = matchDetail.getHomeTeam().getSubstitutes().get(position+POSITION_SHIFT_SECOND).getName();
            String homePlayerNumber = String.valueOf(matchDetail.getHomeTeam().getSubstitutes().get(position+POSITION_SHIFT_SECOND).getNumber());

            String awayPlayerName = matchDetail.getAwayTeam().getSubstitutes().get(position+POSITION_SHIFT_SECOND).getName();
            String awayPlayerNumber = String.valueOf(matchDetail.getAwayTeam().getSubstitutes().get(position+POSITION_SHIFT_SECOND).getNumber());

            lineUpsViewHolder.homeTeamPlayer.setText(String.format("%s %s", homePlayerNumber, homePlayerName));
            lineUpsViewHolder.awayTeamPlayer.setText(String.format("%s %s", awayPlayerName, awayPlayerNumber));
        }



    }

    @Override
    public int getItemCount() {
        int startingPlayerNumber = matchDetail.getHomeTeam().getStartingLineUps().size();
        int benchPlayersNumber = matchDetail.getHomeTeam().getSubstitutes().size();
        return startingPlayerNumber+benchPlayersNumber+2 ;
    }

}
