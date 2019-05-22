package com.acote.mastermind;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {


    private final LayoutInflater mInflater;
    private List<Score> mTopScores;

    public void setScores(List<Score> scores){
        mTopScores = scores;
        notifyDataSetChanged();
    }


    ScoreAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.score_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mTopScores != null) {
            Score current = mTopScores.get(position);
            holder.txName.setText(current.getPlayerName());
            holder.txScore.setText(String.valueOf(current.getScore()));
        } else {
            // Covers the case of data not being ready yet.
            holder.txName.setText("No Scores yet!");
        }
    }

    @Override
    public int getItemCount() {
        if (mTopScores != null) {
            return mTopScores.size();
        }
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txName;
        public TextView txScore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txName = itemView.findViewById(R.id.txName);
            txScore = itemView.findViewById(R.id.tvScore);
        }
    }
}
