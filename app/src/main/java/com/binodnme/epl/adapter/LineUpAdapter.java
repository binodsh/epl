package com.binodnme.epl.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binodnme.epl.R;

/**
 * Created by binodnme on 2/19/16.
 */
public class LineUpAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class LineUpsViewHolder extends RecyclerView.ViewHolder {
        public LineUpsViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_lineup, parent, false);
        LineUpsViewHolder lvh = new LineUpsViewHolder(v);
        return lvh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 11;
    }
}
