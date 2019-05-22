/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projfinalserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 *
 * @author acote
 */
public class ProjFinalServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println(Integer.valueOf(String.valueOf('0')) + 2);
            new Thread(new MMDispatchServer("10.4.131.9", 42069)).start();
//            Socket tester = new Socket();
//            tester.bind(new InetSocketAddress(0));
//            System.out.println(String.format("Created socket with address %s", tester.getLocalAddress()));
//            tester.connect(new InetSocketAddress("192.168.1.114", 42069));
//            BufferedReader reader = new BufferedReader(new InputStreamReader(tester.getInputStream()));
//            PrintWriter writer = new PrintWriter(tester.getOutputStream(), true);
//            System.out.println(String.format("Client connected to %s", tester.getPort()));
//            System.out.println(String.format("Client port is to %s", tester.getLocalPort()));
//            
//            Gson gsonObject = new Gson();
//            
//            System.out.println(reader.readLine());
//            String testSettings = gsonObject.toJson(new MMSettings(8, false, false), MMSettings.class);
//            writer.println(testSettings);
//            
//            while (true){
//                BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));
//                String guess = keyboardInput.readLine();
//                
//                writer.println(guess);
//                
//                String responseString = reader.readLine();
//                
//                System.out.println(responseString);
//                
//                MMHint response = gsonObject.fromJson(responseString, MMHint.class);
//                
//                if (response.win){
//                    System.out.println(String.format("Win! Score: %s", response.score));
//                    break;
//                }
//                else if (response.guessesLeft == 0){
//                    System.out.println(String.format("Loss!"));
//                    break;
//                }
//                else {System.out.println(response);}
//            }
//            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
