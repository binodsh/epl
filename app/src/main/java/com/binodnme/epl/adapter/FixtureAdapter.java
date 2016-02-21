package com.binodnme.epl.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binodnme.epl.R;
import com.binodnme.epl.activity.MatchDetailsActivity;
import com.binodnme.epl.constants.ApplicationConstant;
import com.binodnme.epl.model.Fixture;
import com.binodnme.epl.utils.DateUtils;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.List;

/**
 * Created by binodnme on 2/16/16.
 */
public class FixtureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyRecyclerHeadersAdapter{
    private static final int MATCH_COMPLETE = 0;
    private static final int MATCH_LIVE = 1;
    private static final int MATCH_UPCOMING = 2;

    private List<Fixture> dataSet;

    public FixtureAdapter(){

    }

    public FixtureAdapter(List<Fixture> ds){
        this.dataSet = ds;
    }

    public static class MatchDayTitleViewHolder extends RecyclerView.ViewHolder{

        public MatchDayTitleViewHolder(View itemView) {
            super(itemView);
        }
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


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        RecyclerView.ViewHolder vh ;

        if(viewType == MATCH_COMPLETE){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fixture_complete, parent, false);
            vh = new FixtureCompleteViewHolder(v);
        }else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fixture_upcoming, parent, false);
            vh = new FixturePreMatchViewHolder(v);
        }

        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        String matchStatus = dataSet.get(position).getMatchStatus().toUpperCase();
        switch (matchStatus){
            case "FT":
                return MATCH_COMPLETE;

            case "PM":
                return MATCH_UPCOMING;

            default:
                return MATCH_LIVE;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String matchStatus = dataSet.get(position).getMatchStatus();

        if(matchStatus.equalsIgnoreCase(ApplicationConstant.FT)){
            FixtureCompleteViewHolder fcvh = (FixtureCompleteViewHolder) holder;
            Fixture fixture = dataSet.get(position);
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
                    v.getContext().startActivity(intent);
                }
            });
        }else if(matchStatus.equalsIgnoreCase(ApplicationConstant.PM)){
            FixturePreMatchViewHolder fpmvh = (FixturePreMatchViewHolder) holder;
            Fixture fixture = dataSet.get(position);
            fpmvh.homeTeamName.setText(fixture.getHomeTeamName());
            fpmvh.awayTeamName.setText(fixture.getAwayTeamName());

            String requiredDateFormat = "EEE, d MMM HH:mm";
            String matchDate = DateUtils.getDateString(fixture.getMatchDate(), requiredDateFormat);

            fpmvh.matchDate.setText(matchDate);
        }
    }


    @Override
    public long getHeaderId(int position) {
//        return position%3 == 0 ? position : -1;
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View v;
        RecyclerView.ViewHolder vh ;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fixture_match_day, parent, false);
        vh = new FixturePreMatchViewHolder(v);

        return vh;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
//        return dataSet.length;
        return dataSet.size();
    }


    public int getResourceId(Context context, String name){
        name = name.toLowerCase().replace(' ','_');

        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }
}
