package com.acote.mastermind;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ScoreDao {
        @Insert
        void insert(Score score);

        @Query("DELETE FROM score")
        void deleteAll();

        @Query("SELECT * from score ORDER BY score DESC LIMIT 10")
        LiveData<List<Score>> getTopScores();

}
