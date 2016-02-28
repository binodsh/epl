package com.binodnme.epl.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binodnme.epl.EplApplication;
import com.binodnme.epl.R;
import com.binodnme.epl.activity.MatchDetailsActivity;
import com.binodnme.epl.constants.ApplicationConstant;
import com.binodnme.epl.model.Fixture;
import com.binodnme.epl.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by binodnme on 2/16/16.
 */
public class FixtureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int MATCH_COMPLETE = 0;
    private static final int MATCH_LIVE = 1;
    private static final int MATCH_UPCOMING = 2;
    private static final int MATCH_POSTPONED = 3;
    private static final int MATCH_ABANDONED = 4;

    public static final String FIXTURE = "fixture";

    private List<Fixture> dataSet;

    public FixtureAdapter(List<Fixture> ds){
        this.dataSet = ds;
    }

    public static class FixturePreMatchViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout linearLayout;
        public ImageView homeTeamLogo;
        public TextView homeTeamName;
        public ImageView awayTeamLogo;
        public TextView awayTeamName;
        public TextView matchDate;

        public FixturePreMatchViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView;
            homeTeamLogo = (ImageView) itemView.findViewById(R.id.home_team_logo);
            awayTeamLogo = (ImageView) itemView.findViewById(R.id.away_team_logo);
            homeTeamName = (TextView) itemView.findViewById(R.id.home_team_name);
            awayTeamName = (TextView) itemView.findViewById(R.id.away_team_name);
            matchDate = (TextView) itemView.findViewById(R.id.match_date);
        }
    }

    public static class FixtureCompleteViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public ImageView homeTeamLogo;
        public TextView homeTeamName;
        public ImageView awayTeamLogo;
        public TextView awayTeamName;
        public TextView homeTeamScore;
        public TextView awayTeamScore;
        public TextView matchDate;

        public FixtureCompleteViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView;
            homeTeamLogo = (ImageView) itemView.findViewById(R.id.team_home_logo);
            awayTeamLogo = (ImageView) itemView.findViewById(R.id.team_away_logo);
            homeTeamName = (TextView) itemView.findViewById(R.id.team_home_name);
            awayTeamName = (TextView) itemView.findViewById(R.id.team_away_name);
            homeTeamScore = (TextView) itemView.findViewById(R.id.team_home_score);
            awayTeamScore = (TextView) itemView.findViewById(R.id.team_away_score);
            matchDate = (TextView) itemView.findViewById(R.id.fixture_date);
        }
    }

    public static class FixtureLiveViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public ImageView homeTeamLogo;
        public TextView homeTeamName;
        public ImageView awayTeamLogo;
        public TextView awayTeamName;
        public TextView homeTeamScore;
        public TextView awayTeamScore;
        public TextView matchStatus;

        public FixtureLiveViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView;
            homeTeamLogo = (ImageView) itemView.findViewById(R.id.team_home_logo);
            awayTeamLogo = (ImageView) itemView.findViewById(R.id.team_away_logo);
            homeTeamName = (TextView) itemView.findViewById(R.id.team_home_name);
            awayTeamName = (TextView) itemView.findViewById(R.id.team_away_name);
            homeTeamScore = (TextView) itemView.findViewById(R.id.team_home_score);
            awayTeamScore = (TextView) itemView.findViewById(R.id.team_away_score);
            matchStatus = (TextView) itemView.findViewById(R.id.match_status    );
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        RecyclerView.ViewHolder vh ;

        if(viewType == MATCH_COMPLETE){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fixture_complete, parent, false);
            vh = new FixtureCompleteViewHolder(v);
        }else if(viewType == MATCH_UPCOMING){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fixture_upcoming, parent, false);
            vh = new FixturePreMatchViewHolder(v);
        }else{
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fixture_live, parent, false);
            vh = new FixtureLiveViewHolder(v);
        }

        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        String matchStatus = dataSet.get(position).getMatchStatus().toUpperCase();
        switch (matchStatus){
            case ApplicationConstant.FT:
                return MATCH_COMPLETE;

            case ApplicationConstant.PM:
                return MATCH_UPCOMING;

            case ApplicationConstant.PP:
                return  MATCH_POSTPONED;

            default:
                return MATCH_LIVE;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String matchStatus = dataSet.get(position).getMatchStatus();

        if(matchStatus.equalsIgnoreCase(ApplicationConstant.FT)){
            FixtureCompleteViewHolder fcvh = (FixtureCompleteViewHolder) holder;
            final Fixture fixture = dataSet.get(position);
            fcvh.homeTeamName.setText(fixture.getHomeTeamName());
            fcvh.awayTeamName.setText(fixture.getAwayTeamName());

            int homeTeamScore = fixture.getHomeTeamScore();
            int awayTeamScore = fixture.getAwayTeamScore();
            fcvh.homeTeamScore.setText( homeTeamScore <0 ? "" : String.valueOf(homeTeamScore));
            fcvh.awayTeamScore.setText( awayTeamScore <0 ? "" : String.valueOf(awayTeamScore));
            String requiredDateFormat = "MMM d";
            String matchDate = DateUtils.getDateString(fixture.getMatchDate(), requiredDateFormat);
            fcvh.matchDate.setText("FT\n"+matchDate);

            Context context = fcvh.homeTeamLogo.getContext();
            int id = getResourceId(context, fixture.getHomeTeamName());
            fcvh.homeTeamLogo.setImageResource(id == 0 ? R.mipmap.ic_launcher : id);

            Context context1 = fcvh.awayTeamLogo.getContext();
            int id1 = getResourceId(context1, fixture.getAwayTeamName());
            fcvh.awayTeamLogo.setImageResource(id1 == 0 ? R.mipmap.ic_launcher : id1);

            fcvh.linearLayout.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), MatchDetailsActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable(FIXTURE, fixture);
                    intent.putExtras(args);
                    v.getContext().startActivity(intent);
                }
            });

        }else if(matchStatus.equalsIgnoreCase(ApplicationConstant.PM)){
            FixturePreMatchViewHolder fpmvh = (FixturePreMatchViewHolder) holder;
            final Fixture fixture = dataSet.get(position);
            fpmvh.homeTeamName.setText(fixture.getHomeTeamName());
            fpmvh.awayTeamName.setText(fixture.getAwayTeamName());

            String requiredDateFormat = "EEE, d MMM HH:mm";
            String matchDate = DateUtils.getDateString(fixture.getMatchDate(), requiredDateFormat);

            fpmvh.matchDate.setText(matchDate);

            Context context = fpmvh.homeTeamLogo.getContext();
            int id = getResourceId(context, fixture.getHomeTeamName());
            fpmvh.homeTeamLogo.setImageResource(id == 0 ? R.mipmap.ic_launcher : id);

            Context context1 = fpmvh.awayTeamLogo.getContext();
            int id1 = getResourceId(context1, fixture.getAwayTeamName());
            fpmvh.awayTeamLogo.setImageResource(id1 == 0 ? R.mipmap.ic_launcher : id1);


            fpmvh.linearLayout.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), MatchDetailsActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable(FIXTURE, fixture);
                    intent.putExtras(args);
                    v.getContext().startActivity(intent);
                }
            });

        }else{
            FixtureLiveViewHolder flvh = (FixtureLiveViewHolder) holder;
            final Fixture fixture = dataSet.get(position);
            flvh.homeTeamName.setText(fixture.getHomeTeamName());
            flvh.awayTeamName.setText(fixture.getAwayTeamName());

            int homeTeamScore = fixture.getHomeTeamScore();
            int awayTeamScore = fixture.getAwayTeamScore();
            flvh.homeTeamScore.setText( homeTeamScore <0 ? "" : String.valueOf(homeTeamScore));
            flvh.awayTeamScore.setText( awayTeamScore <0 ? "" : String.valueOf(awayTeamScore));


            Context context = flvh.homeTeamLogo.getContext();
            int id = getResourceId(context, fixture.getHomeTeamName());
            flvh.homeTeamLogo.setImageResource(id == 0 ? R.mipmap.ic_launcher : id);

            Context context1 = flvh.awayTeamLogo.getContext();
            int id1 = getResourceId(context1, fixture.getAwayTeamName());
            flvh.awayTeamLogo.setImageResource(id1 == 0 ? R.mipmap.ic_launcher : id1);


            switch (fixture.getMatchStatus().toUpperCase()){
                case ApplicationConstant.FH:
                    flvh.matchStatus.setText("first half");
                    flvh.matchStatus.setTextColor(ContextCompat.getColor(EplApplication.getContext(), R.color.green));
                    break;

                case ApplicationConstant.SH:
                    flvh.matchStatus.setText("second half");
                    flvh.matchStatus.setTextColor(ContextCompat.getColor(EplApplication.getContext(), R.color.green));
                    break;

                case ApplicationConstant.HT:
                    flvh.matchStatus.setText("half time");
                    flvh.matchStatus.setTextColor(ContextCompat.getColor(EplApplication.getContext(), R.color.green));
                    break;

                case ApplicationConstant.PP:
                    flvh.matchStatus.setText("postponed");
                    flvh.matchStatus.setTextColor(Color.parseColor("#ff0000"));
                    break;
                case ApplicationConstant.AD:
                    flvh.matchStatus.setText("abandoned");
                    flvh.matchStatus.setTextColor(Color.parseColor("#ff0000"));
                    break;
                default:
                    flvh.matchStatus.setText("live");
                    flvh.matchStatus.setTextColor(ContextCompat.getColor(EplApplication.getContext(), R.color.green));
            }

            flvh.linearLayout.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), MatchDetailsActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable(FIXTURE, fixture);
                    intent.putExtras(args);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public int getResourceId(Context context, String name){
        name = name.toLowerCase().replace(' ','_');
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    public List<Fixture> getDataSet() {
        return dataSet;
    }

    public void setDataSet(List<Fixture> dataSet) {
        this.dataSet = dataSet !=  null ? dataSet : new ArrayList<Fixture>();
        notifyDataSetChanged();
    }
}
