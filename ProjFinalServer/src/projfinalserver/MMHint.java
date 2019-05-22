/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projfinalserver;

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

    @Override
    public String toString() {
        return "MMHint{" + "win=" + win + ", hardMatches=" + hardMatches + ", softMatches=" + softMatches + '}';
    }
}
