/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projfinalserver;

import java.net.Socket;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.rmi.transport.tcp.TCPEndpoint;

/**
 *
 * @author acote
 */
public class MMDispatchServer extends Thread {
     ServerSocket server;
     String serverIP;
     int port;

    public MMDispatchServer(String serverIP, int port) throws IOException {
        this.serverIP = serverIP;
        this.port = port;
        
        server = new ServerSocket();
        server.bind(new InetSocketAddress(InetAddress.getByName(serverIP), port));
    }

    @Override
    public void run() {
        System.out.println(String.format("Standy for connections on %s", server.getInetAddress().toString()));
        while (true){
            try {
                Socket gameSocket = server.accept();
                new Thread(new MMGameThread(gameSocket)).start();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
     
     
}
