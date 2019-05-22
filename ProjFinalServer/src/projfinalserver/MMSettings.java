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
public class MMSettings {
    int numGuesses;
    boolean duplicatesAllowed;
    boolean blanksAllowed;

    public MMSettings(int numGuesses, boolean duplicatesAllowed, boolean blanksAllowed) {
        this.numGuesses = numGuesses;
        this.duplicatesAllowed = duplicatesAllowed;
        this.blanksAllowed = blanksAllowed;
    }

    @Override
    public String toString() {
        return "MMSettings{" + "numGuesses=" + numGuesses + ", duplicatesAllowed=" + duplicatesAllowed + ", blanksAllowed=" + blanksAllowed + '}';
    }
    
    
}
