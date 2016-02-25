package com.binodnme.epl.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.binodnme.epl.R;
import com.binodnme.epl.model.MatchDay;

import java.util.List;

/**
 * Created by binodnme on 2/23/16.
 */
public class MatchDaySpinnerAdapter extends BaseAdapter {

    private List<MatchDay> matchDays;
    private int matchDayPosition;

    public MatchDaySpinnerAdapter() {
    }

    public MatchDaySpinnerAdapter(List<MatchDay> mds) {
        matchDays = mds;
    }

    @Override
    public int getCount() {
        return matchDays.size();
    }

    @Override
    public Object getItem(int position) {
        return matchDays.get(position);
    }

    @Override
    public long getItemId(int position) {
        return matchDays.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        SpinnerViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_day_spinner, parent, false);
            holder = new SpinnerViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (SpinnerViewHolder) view.getTag();
        }

        holder.mTextView.setText(matchDays.get(position).getName());

        if(matchDays.get(position).getCurrentMatchDay()){
            holder.mMatchDayIndicator.setVisibility(View.VISIBLE);
            holder.mMatchDayIndicator.setBackgroundColor(Color.parseColor("#64DD17"));
            matchDayPosition = position;
        }else{
            holder.mMatchDayIndicator.setVisibility(View.VISIBLE);
            if(position > matchDayPosition){
                holder.mMatchDayIndicator.setBackgroundColor(Color.parseColor("#457ad0"));
            }else{
                holder.mMatchDayIndicator.setBackgroundColor(Color.parseColor("#EF6C00"));
            }
        }
        return view;
    }

    public class SpinnerViewHolder {
        RelativeLayout mLinearLayout;
        View mMatchDayIndicator;
        TextView mTextView;

        public SpinnerViewHolder(View itemView) {
            mLinearLayout = (RelativeLayout) itemView;
            mMatchDayIndicator = itemView.findViewById(R.id.match_day_indicator);
            mTextView = (TextView) itemView.findViewById(R.id.spinner_text);
        }
    }
}
