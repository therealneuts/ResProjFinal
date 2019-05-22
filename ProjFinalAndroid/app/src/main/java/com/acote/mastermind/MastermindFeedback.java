package com.acote.mastermind;

public class MastermindFeedback {
    private String guess;
    private MMHint response;

    public MastermindFeedback(String guess, MMHint response) {
        this.guess = guess;
        this.response = response;
    }

    public String getGuess() {
        return guess;
    }

    public MMHint getResponse() {
        return response;
    }
}
