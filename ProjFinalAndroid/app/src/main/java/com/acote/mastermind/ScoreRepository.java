package com.acote.mastermind;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ScoreRepository {

    private ScoreDao mScoreDao;
    private LiveData<List<Score>> mTopScores;

    ScoreRepository(Application application) {
        ScoreDatabase db = ScoreDatabase.getDatabase(application);
        mScoreDao = db.scoreDao();
        mTopScores = mScoreDao.getTopScores();
    }

    LiveData<List<Score>> getTopScores() {
        return mTopScores;
    }


    public void insert (Score score) {
        new insertAsyncTask(mScoreDao).execute(score);
    }

    private static class insertAsyncTask extends AsyncTask<Score, Void, Void> {

        private ScoreDao mAsyncTaskDao;

        insertAsyncTask(ScoreDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Score... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
