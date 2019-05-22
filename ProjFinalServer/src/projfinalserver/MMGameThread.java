/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projfinalserver;

import com.google.gson.Gson;
import java.net.Socket;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

/**
 *
 * @author acote
 */
public class MMGameThread extends Thread {
    private int numGuesses;
    private boolean duplicatesAllowed;
    private boolean blanksAllowed;
    private final int NUM_PEGS = 4;
    private final int NUM_COLORS = 6;
    
    List<Character> solution;
    
    BufferedReader reader;
    PrintWriter writer;
    
    Socket gameSocket;

    public MMGameThread(Socket gameSocket) {
        try {
            this.gameSocket = gameSocket;
            reader = new BufferedReader(new InputStreamReader(gameSocket.getInputStream()));
            writer = new PrintWriter(gameSocket.getOutputStream(), true);
        } catch (IOException ex) {
            Logger.getLogger(MMGameThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        Gson gson = new Gson();
        
        System.out.println(gameSocket.isConnected());
        System.out.println(String.format("Server connected to %s", gameSocket.getPort()));
        System.out.println(String.format("Server port is %s", gameSocket.getLocalPort()));
        try {
            writer.println("ack");
            String settingString = reader.readLine();
            
            System.out.println(settingString);
            
            MMSettings settings = gson.fromJson(settingString, MMSettings.class);
                       
            applySettings(settings);
            
            solution = createSolution();
            
            
            String userGuess;
            MMHint response = new MMHint();
            
            
            for (int guessesLeft = numGuesses; guessesLeft != 0 && !response.win; ){
                userGuess = reader.readLine();
                System.out.println(userGuess);
                response = buildResponse(userGuess);
                response.guessesLeft = --guessesLeft;
                
                if (response.win){
                    response.score = calcScore(guessesLeft);
                }
                
                String responseString = gson.toJson(response, MMHint.class);
                writer.println(responseString);
            }
            
            gameSocket.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        
    }

    private void applySettings(MMSettings settings) {
        numGuesses = settings.numGuesses;
        duplicatesAllowed = settings.duplicatesAllowed;
        blanksAllowed = settings.blanksAllowed;
    }

    private List<Character> createSolution() {
        ArrayList<Character> validChars = new ArrayList<>();
        int numColors = NUM_COLORS;
        if (blanksAllowed) {numColors++;}
        
        for (int i = 0; i < numColors; i++){
            validChars.add((char)(i+48));
        }
        
        List<Character> solution = new ArrayList<>();
        
        
        
        for (int i = 0; i < NUM_PEGS; i++){
            int bound = validChars.size();
            double indexdbl = (Math.random() * bound);
            int index = (int)indexdbl;
            char chosenChar = validChars.get(index);
            
            
            //Si les doublons ne sont pas permis, enlever la couleur choisie de l'ensemble.
            if (!duplicatesAllowed){validChars.remove(validChars.indexOf(chosenChar));}
            
            solution.add(chosenChar);
        }
        
        return solution;
    }

    private MMHint buildResponse(String userGuess) {
        MMHint response = new MMHint();
        List<Character> userGuessAsList;
        userGuessAsList = userGuess.chars()
                        .mapToObj(e -> (char)e)
                        .collect(Collectors.toList());
        
        //Calcul du nombre de matchs parfaits.
        for (int i = 0; i < NUM_PEGS; i++){
            if (solution.get(i).equals(userGuessAsList.get(i))){
                response.hardMatches++;
            }
        }
        
        //Calcul du nombre de matchs imparfaits. Quand on trouve un match pour un caractère, on termine la boucle
        //et on enlève ce caractère de l'essai de l'usager (pour bien calculer les doublons).
        for (Character solutionCharacter: solution){
            for (Character guessCharacter: userGuessAsList){
                if (solutionCharacter.equals(guessCharacter)){
                    response.softMatches++;
                    userGuessAsList.remove(guessCharacter);
                    break;
                }
            }
        }
        
        //Puisque chaque match parfait est aussi un match imparfait.
        response.softMatches -= response.hardMatches;
        
        if (response.hardMatches == NUM_PEGS) {response.win = true;}
        
        return response;
    }
    
    public static void responseTest(String solution, String guess){
        List<Character> solAsList = solution.chars().mapToObj(e -> (char)e).collect(Collectors.toList());
        MMGameThread tester = new MMGameThread(null);
        tester.solution = solAsList;
        MMHint test = tester.buildResponse(guess);
        System.out.println(test.toString());
    }

    public static void streamTest() throws UnknownHostException, IOException{
        MMGameThread testServer = new MMGameThread(new Socket(InetAddress.getLocalHost(), 42069));
    }    
    
    private int calcScore(int guessesLeft) {
        int score = 15 - numGuesses;
        score *= guessesLeft + 1;
        if (duplicatesAllowed) {score *= 3;}
        if (blanksAllowed) {score *= 2;}
        
        return score;
    }
}
