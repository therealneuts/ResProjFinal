package com.acote.mastermind;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ScoreViewModel extends AndroidViewModel {
    private ScoreRepository mRepository;

    private LiveData<List<Score>> mTopScores;

    public ScoreViewModel (Application application) {
        super(application);
        mRepository = new ScoreRepository(application);
        mTopScores = mRepository.getTopScores();
    }

    LiveData<List<Score>> getTopScores() { return mTopScores; }

    public void insert(Score score) { mRepository.insert(score); }
}
