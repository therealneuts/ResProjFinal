package com.acote.mastermind;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Score.class}, version = 1, exportSchema = false)
public abstract class ScoreDatabase extends RoomDatabase {

    public abstract ScoreDao scoreDao();

    private static volatile ScoreDatabase INSTANCE;

    static ScoreDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ScoreDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ScoreDatabase.class, "score_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}