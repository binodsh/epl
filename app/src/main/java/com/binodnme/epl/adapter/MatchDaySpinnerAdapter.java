package com.binodnme.epl.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binodnme.epl.R;
import com.binodnme.epl.model.MatchDay;

import java.util.List;

/**
 * Created by binodnme on 2/23/16.
 */
public class MatchDaySpinnerAdapter extends BaseAdapter {

    private List<MatchDay> matchDays;

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
            holder.mImageView.setVisibility(View.VISIBLE);
        }else{
            holder.mImageView.setVisibility(View.GONE);
        }
        return view;
    }

    public class SpinnerViewHolder {
        LinearLayout mLinearLayout;
        TextView mTextView;
        ImageView mImageView;

        public SpinnerViewHolder(View itemView) {
            mLinearLayout = (LinearLayout) itemView;
            mTextView = (TextView) itemView.findViewById(R.id.spinner_text);
            mImageView = (ImageView) itemView.findViewById(R.id.current_matchday_ticker);

        }
    }
}
