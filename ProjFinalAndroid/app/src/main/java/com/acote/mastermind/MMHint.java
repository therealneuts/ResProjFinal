/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acote.mastermind;

import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author acote
 */
public class MMHint {
    public boolean win;
    public int hardMatches;
    public int softMatches;
    public int guessesLeft;
    public int score;

    public MMHint(boolean win, int hardMatches, int softMatches) {
        this.win = win;
        this.hardMatches = hardMatches;
        this.softMatches = softMatches;
    }

    public MMHint() {
        this.win = false;
        this.hardMatches = 0;
        this.softMatches = 0;
    }

    public static MMHint fromJson(String json) throws JSONException {
        JSONObject jsonObj = new JSONObject(json);
        MMHint returnVal = new MMHint();

        returnVal.win = jsonObj.getBoolean("win");
        returnVal.hardMatches = jsonObj.getInt("hardMatches");
        returnVal.softMatches = jsonObj.getInt("softMatches");
        returnVal.guessesLeft = jsonObj.getInt("guessesLeft");
        returnVal.score = jsonObj.getInt("score");

        return returnVal;
    }

    @Override
    public String toString() {
        return "MMHint{" + "win=" + win + ", hardMatches=" + hardMatches + ", softMatches=" + softMatches + '}';
    }
}
