package com.acote.mastermind;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.LinkedList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {

    private LinkedList<MastermindFeedback> dataset;

    public FeedbackAdapter(LinkedList<MastermindFeedback> dataset) {
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public FeedbackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_panel, parent, false);

        FeedbackAdapter.ViewHolder viewHolder = new FeedbackAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackAdapter.ViewHolder viewHolder, int position) {
        MastermindFeedback feedback = dataset.get(position);

        for (int i = 0; i < feedback.getGuess().length(); i++){
            int colorIndex = feedback.getGuess().charAt(i) - 48;
            GradientDrawable drawable = (GradientDrawable) viewHolder.pegs.get(i).getDrawable();
            drawable.setColor(MastermindPeg.COLORS.get(colorIndex).toArgb());
        }

        String hardMatchesString = String.valueOf(feedback.getResponse().hardMatches);
        String softMatchesString = String.valueOf(feedback.getResponse().softMatches);

        viewHolder.hardMatches.setText(hardMatchesString);
        viewHolder.softMatches.setText(softMatchesString);

        viewHolder.itemView.setBackgroundColor(Color.GRAY);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public LinkedList<MastermindFeedback> getDataset() {
        return dataset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public LinkedList<ImageView> pegs = new LinkedList<>();
        public TextView hardMatches;
        public TextView softMatches;
        public View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;

            pegs.add((ImageView) itemView.findViewById(R.id.feedback_pegOne));
            pegs.add((ImageView) itemView.findViewById(R.id.feedback_pegTwo));
            pegs.add((ImageView) itemView.findViewById(R.id.feedback_pegThree));
            pegs.add((ImageView) itemView.findViewById(R.id.feedback_pegFour));

            hardMatches = itemView.findViewById(R.id.feedback_lbHardMatches);
            softMatches = itemView.findViewById(R.id.feedback_lbSoftMatches);
        }
    }
}
