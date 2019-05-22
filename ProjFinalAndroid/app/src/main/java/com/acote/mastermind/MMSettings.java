/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acote.mastermind;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author acote
 */
public class MMSettings {
    int numGuesses;
    boolean duplicatesAllowed;
    boolean blanksAllowed;

    public MMSettings(int numGuesses, boolean duplicatesAllowed, boolean blanksAllowed) {
        this.numGuesses = numGuesses;
        this.duplicatesAllowed = duplicatesAllowed;
        this.blanksAllowed = blanksAllowed;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();

        json.put("numGuesses", numGuesses);
        json.put("duplicatesAllowed", duplicatesAllowed);
        json.put("blanksAllowed", blanksAllowed);

        return json;
    }
}
