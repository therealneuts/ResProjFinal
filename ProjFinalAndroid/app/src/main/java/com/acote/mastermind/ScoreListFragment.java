package com.acote.mastermind;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ScoreListFragment extends Fragment {

    private ScoreViewModel mViewModel;
    private RecyclerView rvScoreList;


    public static ScoreListFragment newInstance() {
        return new ScoreListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View screen = inflater.inflate(R.layout.score_list_fragment, container, false);
        rvScoreList = screen.findViewById(R.id.rvScoreList);
        return screen;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ScoreAdapter adapter = new ScoreAdapter(getContext());
        rvScoreList.setAdapter(adapter);
        rvScoreList.setLayoutManager(new LinearLayoutManager(getContext()));

        Log.i("RV", rvScoreList.getAdapter().toString());


        mViewModel = ViewModelProviders.of(this).get(ScoreViewModel.class);
        mViewModel.getTopScores().observe(this, new Observer<List<Score>>() {
            @Override
            public void onChanged(List<Score> scores) {
                adapter.setScores(scores);
            }
        });
    }

}
