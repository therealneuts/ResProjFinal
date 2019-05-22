package com.acote.mastermind;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class MMClient extends Thread {
    private final String TAG = this.getClass().getName();

    private Socket clientSocket;
    private String serverIP;
    private int serverPort;

    private Queue<String> guessBuffer = new LinkedBlockingQueue<>();

    private MMSettings settings;

    private MMResponseCallback callback;

    public MMClient(String serverIP, int serverPort, MMSettings settings, MMResponseCallback callback){
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.settings = settings;
        this.clientSocket = new Socket();


        this.callback = callback;
    }

    public interface MMResponseCallback{
        void onConnectionReady();

        void onResponse(String guess, MMHint response);

        void onError(Exception e);
    }

    public void addGuessToBuffer(String guess){
        synchronized(guessBuffer) {
            guessBuffer.offer(guess);
            guessBuffer.notify();
        }
    }

    private String takeGuessFromBuffer(){
       synchronized (guessBuffer) {
           while (guessBuffer.isEmpty()) {
               try {
                   guessBuffer.wait();
               } catch (InterruptedException e) {
                   callback.onError(e);
               }
           }

           return guessBuffer.poll();
       }
    }

    @Override
    public void run() {
        try {
            clientSocket.bind(new InetSocketAddress(0));
            clientSocket.connect(new InetSocketAddress(InetAddress.getByName(serverIP), serverPort));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

            String ack = reader.readLine();

            Log.i(TAG, ack);

            assert ack == "ack";

            callback.onConnectionReady();

            JSONObject jsonSettings = settings.toJson();
            String settingsString = jsonSettings.toString();
            writer.println(settingsString);

            String userGuess;
            while (true){
                userGuess = takeGuessFromBuffer();
                writer.println(userGuess);

                Log.i(TAG, userGuess);

                String responseString = reader.readLine();

                Log.i(TAG, responseString);

                MMHint response = MMHint.fromJson(responseString);
                callback.onResponse(userGuess, response);

                if (response.win || response.guessesLeft == 0){break;}
            }

            clientSocket.close();
        } catch (IOException e) {
            callback.onError(e);
        } catch (JSONException e) {
            callback.onError(e);
        }


    }
}
